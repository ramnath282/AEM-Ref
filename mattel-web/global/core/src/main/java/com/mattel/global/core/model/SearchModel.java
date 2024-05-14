package com.mattel.global.core.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.services.UrlShorteningService;
import com.mattel.global.core.utils.GlobalPropertyReaderUtils;
import com.mattel.global.core.utils.GlobalUtils;
import com.mattel.global.core.utils.PropertyReaderUtils;
import com.mattel.global.master.core.model.UrlShorteningConfig;

@Model(adaptables = {
	    Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SearchModel {
	
	  private static final Logger LOGGER = LoggerFactory.getLogger(SearchModel.class);
	  
	  @OSGiService
	  private GlobalPropertyReaderUtils globalPropertyReaderUtils;
	  
	  @OSGiService
	  private PropertyReaderUtils propertyReaderUtils;
	  
	  @OSGiService
	  private UrlShorteningService urlShorteningService;
	  
	  @Inject
	  private String placeholderText;
	  
	  @Inject
	  @Default(values = "corp")
	  private String siteName;
	  
	  @Inject
	  private String searchResultPageLink;
	  
	  @Inject
	  private Integer characterLimit;
	  
	  @Inject
	  private Integer suggestionLimit;
	  
	  @Inject
	  private Resource resource;
	  
	  private String typeAheadUrl;    
	  
	  private Map<String, String> siteDomain;
	  
	  private Map<String, String> siteKeyToShortSearchUrl;

	@PostConstruct
	  protected void init() {
	    LOGGER.info("SearchModel init method  :: Start");	    
	    Optional.of(globalPropertyReaderUtils).ifPresent(propertyReader -> {
	    	typeAheadUrl = propertyReader.getTypeAheadUrl(siteName);	    	
	    	LOGGER.debug("typeAheadUrl : {} ", typeAheadUrl);	    
	    });
	    
	    siteDomain = new HashMap<>();
	    buildShortenedSearchUrls();
	    Optional.ofNullable(propertyReaderUtils.getSiteDomainMapping()).ifPresent(siteDomain::putAll);
	    LOGGER.info("SearchModel init method  :: End");
	  } 

	private void buildShortenedSearchUrls() {
		try {
			final Map<String, UrlShorteningConfig> configs = propertyReaderUtils.getShortneningConfigs();
			final Map<String, String> siteKeyToShortSearchUrls = new HashMap<>();
			
			Optional.ofNullable(configs).ifPresent(cs -> 
				configs.keySet()
				       .stream()
				       .filter(k -> StringUtils.isNotEmpty(searchResultPageLink))
				       .filter(k -> Objects.nonNull(configs.get(k)))
				       .forEach(k -> 
				    	   siteKeyToShortSearchUrls.put(k, urlShorteningService.transform(searchResultPageLink, configs.get(k)))));	       
			siteKeyToShortSearchUrl = new HashMap<>(siteKeyToShortSearchUrls);
		} catch (Exception e) {
			LOGGER.info("Unable to build shortened search url", e);
		}
	}

	public Map<String, String> getSiteKeyToShortSearchUrl() {
		return siteKeyToShortSearchUrl;
	}

	public String getPlaceholderText() {
		return placeholderText;
	}	

	public String getSearchResultPageLink() {
		return GlobalUtils.checkLink(searchResultPageLink, resource);
	}
	
	public Integer getCharacterLimit() {
		return characterLimit;
	}

	public Integer getSuggestionLimit() {
		return suggestionLimit;
	}
	
	public String getTypeAheadUrl() {
		return typeAheadUrl;
	}	
	
	public Map<String, String> getSiteDomain() {
		return siteDomain;
	}

}
