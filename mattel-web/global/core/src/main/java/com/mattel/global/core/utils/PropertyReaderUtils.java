package com.mattel.global.core.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.master.core.model.UrlShorteningConfig;

/**
 * @author CTS.
 * Service for properties configuration of CRM site.
 */
@Component(service = PropertyReaderUtils.class, immediate = true)
@Designate(ocd = PropertyReaderUtils.Config.class)
public class PropertyReaderUtils {
  private static final Logger LOGGER = LoggerFactory.getLogger(PropertyReaderUtils.class);
  private String scriptUrl;
  private String apiBaseUrl;
  private String subscriptionId;
  private String rootPageForFonts;
  private String[] brandMappings;
  private String[] siteDomains;
  private Map<String, UrlShorteningConfig> siteKeyToShorteningConfig;
  private String crmformApiKey;
  private Map<String, String> appNameToNodePaths;
  private Map<String, String> appNameToRootPagePaths;
  private Map<String, String> siteKeyToSnPIndexUrls;

  public void setScriptUrl(String scriptUrl) {
	this.scriptUrl = scriptUrl;
}

public void setApiBaseUrl(String apiBaseUrl) {
	this.apiBaseUrl = apiBaseUrl;
}

public void setSubscriptionId(String subscriptionId) {
	this.subscriptionId = subscriptionId;
}

public void setRootPageForFonts(String rootPageForFonts) {
  this.rootPageForFonts = rootPageForFonts;
}

public void setCrmformApiKey(String crmformApiKey) {
  this.crmformApiKey = crmformApiKey;
}

/**
 * @return storeKeyValueMap
 */
private String getValueFromKeyMappings(String[] idMappings,String key) {
  LOGGER.info("Start of getValueFromKeyMappings method");
  long startTime = System.currentTimeMillis();
  String value = "";
  Map<String, String> configKeyValueMap = new HashMap<>();
      for (String mapping: idMappings) {
          String [] mappingIdSplit = mapping.split(":",2);
          if(mappingIdSplit.length > 1){
              configKeyValueMap.put(mappingIdSplit[0], mappingIdSplit[1]);
          }
      }
      if(configKeyValueMap.containsKey(key)){
        value = configKeyValueMap.get(key);
      }
      long endTime = System.currentTimeMillis();
  LOGGER.debug("Method -> {} , Execution Time : {}ms", "getValueFromKeyMappings", endTime - startTime);
  LOGGER.info("End of getValueFromKeyMappings method");
  return value;
}

private Map<String, String> getValueMap(String[] idMappings) {
	  LOGGER.info("Start of getValueMap method");
	  long startTime = System.currentTimeMillis();
	  Map<String, String> configKeyValueMap = new HashMap<>();
	      for (String mapping: idMappings) {
	          String [] mappingIdSplit = mapping.split(":",2);
	          if(mappingIdSplit.length > 1){
	              configKeyValueMap.put(mappingIdSplit[0], mappingIdSplit[1]);
	          }
	      }

	      long endTime = System.currentTimeMillis();
	  LOGGER.debug("Method -> {} , Execution Time : {}ms", "getValueMap", endTime - startTime);
	  LOGGER.info("End of getValueMap method");
	  return configKeyValueMap;
	}

public void setBrandMappings(String[] brandMappings) {
  this.brandMappings = brandMappings;
}

@Activate
  public void activate(final Config config) {
    scriptUrl = config.scriptUrl();
    LOGGER.debug("Path of script {}", scriptUrl);
    apiBaseUrl = config.apiBaseUrl();
    subscriptionId = config.subscriptionId();
    rootPageForFonts = config.rootPageForFonts();
    brandMappings = config.brandMappings();
	siteDomains = config.siteDomains();
	crmformApiKey = config.crmformApiKey();
	buildSiteKeyToShorteningConfig(config.urlShortenConfig());
	appNameToNodePaths = Optional.ofNullable(config.nodePaths()).map(this::toMap).orElseGet(HashMap::new);
	appNameToRootPagePaths = Optional.ofNullable(config.rootPagePaths()).map(this::toMap).orElseGet(HashMap::new);
	siteKeyToSnPIndexUrls = Optional.ofNullable(config.siteKeyToSnPIndexUrls()).map(this::toMap).orElseGet(HashMap::new);
  }

  private void buildSiteKeyToShorteningConfig(String[] urlShortenConfig) {
	try {
		final Map<String, String> mapping = getValueMap(urlShortenConfig);
		final Map<String, UrlShorteningConfig> configs = new HashMap<>();

		Optional.ofNullable(mapping).ifPresent(mp -> {
		LOGGER.debug("siteKeyToShorteningConfig mapping : {}", mapping);
		mapping.keySet()
		       .stream()
		       .filter(StringUtils::isNotEmpty)
		       .filter(k -> StringUtils.isNotEmpty(mapping.get(k)))
		       .forEach(k -> {
		    	   String v = mapping.get(k);
		    	   String[] values = v.split("\\|");
		    	   final UrlShorteningConfig urlShorteningConfig = new UrlShorteningConfig();
		    	   
		    	   for (String value: values) {
		    		   String[] shorteningConfig = value.split("#");
		    		   
		    		   if (shorteningConfig.length == 2) {
		    			   switch (shorteningConfig[0]) {
		    			   		case "from":  
		    			   			urlShorteningConfig.setFrom(shorteningConfig[1]);
		    			   			break;
		    			   		case "to":  
		    			   			urlShorteningConfig.setTo(shorteningConfig[1]);
		    			   			break;
		    			   		case "removeHtmlSuffix":
		    			   			urlShorteningConfig.setRemoveHtmlSuffix(Boolean.valueOf(shorteningConfig[1]));
		    			   			break;
		    			   		default:
		    			   			LOGGER.error("Invalid config in siteKey to shortening config");
		    			   			break;
		    			   }
		    		   }
		    	   }
		    	   
		    	   configs.put(k, urlShorteningConfig);
		       });
		});
		siteKeyToShorteningConfig = new ConcurrentHashMap<>(configs);
		LOGGER.debug("siteKeyToShorteningConfig build successfully; {}", siteKeyToShorteningConfig);
	} catch (Exception e) {
		LOGGER.error("Error building siteKey to shortening config", e);
	}
  }

  /**
   * To retrieve properties for each site and store it in a map.
   *
   * @param properties
   *         The properties array.
   * @return The map connecting siteKey to a property.
   */
  private Map<String, String> toMap(
      String[] properties) {
    final Map<String, String> appNameToProps = new HashMap<>();

    try {
      if (Objects.nonNull(properties)) {
        for (final String str : properties) {
          final String[] mappingIdSplit = str.split(":", 2);

          if (mappingIdSplit.length > 1) {
            appNameToProps.put(mappingIdSplit[0], mappingIdSplit[1]);
          }
        }
      }
    } catch (final Exception e) {
      LOGGER
          .error("Unknown error encountered while mapping properties", e);
    }

    return appNameToProps;
  }

@ObjectClassDefinition(name = "CRM Properties Configuration")
  public @interface Config {
    @AttributeDefinition(name = "Analytics Script Url", description = "Please enter script URL "
        + "for analytics tracking ")
    String scriptUrl() default "//assets.adobedtm.com/launch-EN0da3c2b58c4447ce8120b878cfd02d10-development.min.js" ;

    @AttributeDefinition(name = "API Base URL", description = "Please provide the base url for customer info component. Default is //beta.user.mattel.com/")
    String apiBaseUrl() default "//beta.user.mattel.com/";

    @AttributeDefinition(name = "Subscription Id", description = "Please provide the Subscription Id for customer info component. Default is 351")
    String subscriptionId() default "351";
    
    @AttributeDefinition(name = "Root Page Path for Fonts", description = "Please provide the root page path for loading brand specific fonts. Default is /content/crm/language-masters/brand-specific-fonts/jcr:content/root/brandspecificfonts")
    String rootPageForFonts() default "/content/crm/language-masters/brand-specific-fonts/jcr:content/root/brandspecificfonts";
    
    @AttributeDefinition(name = "Brand Mappings", description = "Please provide the mappings of the Brandnames to the corresponding Base path separeted by ':'. For exa : fp:/content/fisher-price/")
    String[] brandMappings() default "";

    @AttributeDefinition(name = "Domain for specific site key", description = "Please enter domain for specific site key"
        + "sitekey:domain")
    String[] siteDomains() default StringUtils.EMPTY;
    
    @AttributeDefinition(name = "Url Shortening Config", description = "Url shortening config"
            + "sitekey:shorteningconfig")
    String[] urlShortenConfig() default StringUtils.EMPTY;
    
    @AttributeDefinition(name = "CRM Form Api Key", description = "Please provide CRM form Api Key")
    String crmformApiKey();
	
	@AttributeDefinition(name = "Data Node Paths", description = "Data Node Paths"
        + "sitekey/appname:nodepath")    
    String[] nodePaths() default StringUtils.EMPTY;
	
	@AttributeDefinition(name = "Root Page Paths for News Xml", description = "Root News Page Paths for News Xml"
	        + "sitekey/appname:rootPagePaths")    
	String[] rootPagePaths() default StringUtils.EMPTY;

    @AttributeDefinition(name = "SnP Index Urls", description = "SnP Index Urls"
        + "sitekey/appname:snpindexurls")    
    String[] siteKeyToSnPIndexUrls() default StringUtils.EMPTY;
  }

  public String getScriptUrl() {
    return scriptUrl;
  }
  
  public String getSiteDomain(String clientkey) {
    return getValueFromKeyMappings(siteDomains, clientkey);
  }
  
  public UrlShorteningConfig getShortneningConfig(String clientkey) {
	    return siteKeyToShorteningConfig.get(clientkey);
   }

  public Map<String,UrlShorteningConfig> getShortneningConfigs() {
	    return siteKeyToShorteningConfig;
  }
  

  public Map<String, String> getSiteDomainMapping() {
	return getValueMap(siteDomains);
  }

  /**
   * @return apiBaseUrl
   */
  public String getApiBaseUrl() {
    LOGGER.debug("apiBaseUrl configured is{}", apiBaseUrl);
    return apiBaseUrl;
  }

  /**
   * @return subscriptionId
   */
  public String getSubscriptionId() {
    LOGGER.debug("subscriptionId configured is{}", subscriptionId);
    return subscriptionId;
  }
  
  /**
   * @return rootPageForFonts
   */
  public String getRootPageForFonts() {
    LOGGER.debug("rootPageForFonts configured is{}", rootPageForFonts);
    return rootPageForFonts;
  }

  /**
   * @return brandMappings
   */
  public String[] getBrandMappings() {
    LOGGER.debug("brandMappings configured is{}", brandMappings);
    return brandMappings;
  }

  /**
   * @return crmformApiKey
   */
  public String getCrmformApiKey() {
    return crmformApiKey;
  }
  
  public String getNodeDataPath(String appName) {
    return appNameToNodePaths.get(appName);
  }
  
  public String getRootPagePath(String appName) {
    return appNameToRootPagePaths.get(appName);
  }
  
  public String getSnpIndexUrl(String siteKey) {
    return siteKeyToSnPIndexUrls.get(siteKey);
  }
  
}
