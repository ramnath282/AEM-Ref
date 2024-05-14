package com.mattel.global.core.model;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.utils.GlobalPropertyReaderUtils;

@Model(adaptables = {
	    Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SearchResultModel {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchResultModel.class);
	
	@OSGiService
	  private GlobalPropertyReaderUtils globalPropertyReaderUtils;
	
	@Inject
	@Default(values="corp")
	private String siteName;
	
	private String snpUrl;

	@PostConstruct
	  protected void init() {
	    LOGGER.info("SearchModel init method  :: Start");	    
	    Optional.ofNullable(globalPropertyReaderUtils).ifPresent(propertyReader -> {
	    	 snpUrl = propertyReader.getSnpUrl(siteName);	    	
	    	LOGGER.debug("snpUrl : {} ", snpUrl);	    
	    });
	    LOGGER.info("SearchModel init method  :: End");
	  }

	public String getSnpUrl() {
		return snpUrl;
	} 

}





