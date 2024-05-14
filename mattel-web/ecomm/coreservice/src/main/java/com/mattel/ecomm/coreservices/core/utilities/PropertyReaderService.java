package com.mattel.ecomm.coreservices.core.utilities;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.coreservices.core.constants.Constant;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component(service = PropertyReaderService.class)
@Designate(ocd = PropertyReaderService.Config.class)
public class PropertyReaderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyReaderService.class);

	private String[] storeIds;
	private String[] brandIds;
	private String[] cookieDomians;
	private String[] productPagePaths;
	private String[] adminPagePaths;
	private String[] sessionTimeout;
	private String[] bvPassKeys;
	private String[] experienceFragmentRootPath;
	private String[] tagRootPaths;
	private String[] productCategoryPaths;
	private String[] productCategoryPathsForGT;
	private String[] productDetailPaths;
	private String[] siteErrorPages;
	private String[] shopHomePages;
	private String[] snpAccountURLs;
	private String[] snpArticleAccountURLs;
	private ConcurrentHashMap<String, Integer> httpServiceDefConnectTimeouts;
	private String[] tileFallbackImages;
	private String[] siteDomains;
	private String[] sessionStorageTimeout;
	private String[] shopifyDomain;
	private String consumerInfoAPIEndpoint;
    private String consumerInfoAPIKey;
    private String  scene7Url;

	public String getSnpArticleAccountURLs(String clientkey) {
		return getValueFromKeyMappings(snpArticleAccountURLs, clientkey);
	}

	public String getStoreId(String storeKey) {
		return getValueFromKeyMappings(storeIds, storeKey);
	}

	public String getCookieDomain(String domainKey) {
		return getValueFromKeyMappings(cookieDomians, domainKey);
	}

	public String getProductPath(String clientkey) {
		return getValueFromKeyMappings(productPagePaths, clientkey);
	}

	public String getadminPagePath(String clientkey) {
		return getValueFromKeyMappings(adminPagePaths, clientkey);
	}

	public String getSessionTimeout(String clientkey) {
		return getValueFromKeyMappings(sessionTimeout, clientkey);
	}

	public String getBvPassKey(String storeKey) {
		return getValueFromKeyMappings(bvPassKeys, storeKey);
	}

	public String getExperienceFragmentRootPath(String clientkey) {
		return getValueFromKeyMappings(experienceFragmentRootPath, clientkey);
	}

	public String getTagRootPaths(String clientkey) {
		return getValueFromKeyMappings(tagRootPaths, clientkey);

    }

    public String getProductCategoryPath(String clientkey){
      return getValueFromKeyMappings(productCategoryPaths,clientkey);
    }

	public String getProductCategoryPathForGT(String clientkey){
		return getValueFromKeyMappings(productCategoryPathsForGT,clientkey);
	}

    public String getProductDetailPath(String clientkey) {
      return getValueFromKeyMappings(productDetailPaths, clientkey);
    }

    public String getSiteErrorPage(String clientkey){
        return getValueFromKeyMappings(siteErrorPages,clientkey);
    }
    
    public String getShopHomePage(String clientkey){
        return getValueFromKeyMappings(shopHomePages,clientkey);
    }

    public String getSnpAccountURL(String clientkey) {
      return getValueFromKeyMappings(snpAccountURLs, clientkey);
    }
    
    public String getBrandId(String clientkey) {
      return getValueFromKeyMappings(brandIds, clientkey);
    }

    /**
     * To get default HTTP service connection timeout for siteKey/clientKey.
     *
     * @param clientkey
     *          The client specific key. For e.g. ag_en.
     * @return The default site wide HTTP service connection timeout.
     */
     public Integer getHttpServiceDefConnectTimeout(String clientkey) {
       return httpServiceDefConnectTimeouts.getOrDefault(clientkey, -1);
     }
	    
    public String getTileFallbackImage(String clientkey) {
        return getValueFromKeyMappings(tileFallbackImages, clientkey);
      }
    
      public String getSiteDomain(String clientkey) {
        return getValueFromKeyMappings(siteDomains, clientkey);
      }
      
      public String getSessionStorageTimeout(String clientkey) {
         return getValueFromKeyMappings(sessionStorageTimeout, clientkey);
      }
      
      public String getShopifyDomain(String clientkey) {
        return getValueFromKeyMappings(shopifyDomain, clientkey);
      }
      
      public String getConsumerInfoAPIEndpoint() {
        LOGGER.debug("Consumer Info API Endpoint {}", consumerInfoAPIEndpoint);
        return consumerInfoAPIEndpoint;
      }
    
      public String getConsumerInfoAPIKey() {
        LOGGER.debug("Consumer Info API Key {}", consumerInfoAPIKey);
        return consumerInfoAPIKey;
      }
      
      /**
	 * @return the scene7Url
	 */
	public String getScene7Url() {
		return scene7Url;
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
		LOGGER.debug(Constant.EXECUTION_TIME_LOG, "getValueFromKeyMappings", endTime - startTime);
		LOGGER.info("End of getValueFromKeyMappings method");
		return value;
	}

	@Activate
	public void activate(Config config) {
		storeIds = config.storeId();
		brandIds= config.brandId();
		cookieDomians = config.cookieDomians();
		productPagePaths = config.productpagePath();
		adminPagePaths = config.adminPagePathConfiguration();
		sessionTimeout = config.sessionTimeout();
		bvPassKeys = config.bvPassKeys();
		experienceFragmentRootPath = config.experienceFragmentRootPath();
		tagRootPaths = config.tagrootPath();
		productCategoryPaths = config.productCategoryPaths();
		productCategoryPathsForGT = config.productCategoryPathsForGT();
		productDetailPaths = config.productDetailPaths();
		siteErrorPages = config.siteErrorPages();
		shopHomePages = config.shopHomePages();
		snpAccountURLs = config.snpAccountURLs();
		snpArticleAccountURLs = config.snpArticleAccountURLs();
		httpServiceDefConnectTimeouts = mapHttpServiceConnectTimeouts(config.httpServiceDefConnectTimeouts());
		tileFallbackImages = config.tileFallbackImages();
		siteDomains = config.siteDomains();
		shopifyDomain = config.shopifyDomain();
		sessionStorageTimeout = config.sessionStorageTimeout();
		consumerInfoAPIEndpoint = config.consumerInfoAPIEndpoint();
        consumerInfoAPIKey = config.consumerInfoAPIKey();
        scene7Url=config.scene7Url();
	}

 /**
  * To retrieve connection timeouts for each site and store it in a map.
  *
  * @param httpServiceDefConnectTimeouts
  *         The HTTP service default connection timeout configuration.
  * @return The map connecting siteKey to default connection timeout mapping.
  */
 private ConcurrentHashMap<String, Integer> mapHttpServiceConnectTimeouts(
     String[] httpServiceDefConnectTimeouts) {
   final ConcurrentHashMap<String, Integer> siteKeyToConnTimeout = new ConcurrentHashMap<>();

   try {
     if (Objects.nonNull(httpServiceDefConnectTimeouts)) {
       for (final String str : httpServiceDefConnectTimeouts) {
         final String[] mappingIdSplit = str.split(":", 2);

         if (mappingIdSplit.length > 1) {
           mapValue(siteKeyToConnTimeout, str, mappingIdSplit);
         }
       }
     }
   } catch (final Exception e) {
     PropertyReaderService.LOGGER
         .error("Unknown error encountered while mapping connection timeouts", e);
   }

   return siteKeyToConnTimeout;
 }

 /**
  * To retrieve connection timeouts for each site and store it in a map.
  *
  * @param siteKeyToConnTimeout
  *         The map to store siteKey to default connection timeout mapping.
  * @param mapping
  *         The property string received from ODGi configuration.
  * @param mappingIdSplit
  *         The siteKey and timeout value array.
  */
  private void mapValue(final ConcurrentHashMap<String, Integer> siteKeyToConnTimeout,
      final String mapping, final String[] mappingIdSplit) {
    try {
       siteKeyToConnTimeout.put(mappingIdSplit[0], Integer.parseInt(mappingIdSplit[1]));
     } catch (final NumberFormatException nfe) {
       PropertyReaderService.LOGGER.error(String.format(
           "Invalid default http service connection timeout value for mapping : %s",
           mapping), nfe);
       siteKeyToConnTimeout.put(mappingIdSplit[0], -1);
     }
  }
  
	@ObjectClassDefinition(name = "Mattel Ecomm Global Configuration", description = "This service contains "
			+ "all Ecomm related GLobal OSGI Configuration")
	public @interface Config {
		@AttributeDefinition(name = "Store Id", description = "Please Enter the Store Id for Specific Brand in the format of"
				+ "sitekey:storeId")
		String[] storeId() default StringUtils.EMPTY;
		
		@AttributeDefinition(name = "Brand Id", description = "Please Enter the Brand Id for Specific Brand in the format of"
				+ "sitekey:brandId")
		String[] brandId() default StringUtils.EMPTY;

		@AttributeDefinition(name = "Cookie Domains", description = "Please Enter the Cookie Domain for Specific Brand in the format of"
				+ "sitekey:domain")
		String[] cookieDomians() default StringUtils.EMPTY;

		@AttributeDefinition(name = "Product Storage Path", description = "Please enter product storage path in format"
				+ "sitekey:domain")
		String[] productpagePath() default StringUtils.EMPTY;

		@AttributeDefinition(name = "Admin page Configuration", description = "Please enter admin page path in format"
				+ "sitekey:domain")
		String[] adminPagePathConfiguration() default StringUtils.EMPTY;

		@AttributeDefinition(name = "Session Timeout Configuration", description = "Please enter session timeout values here in format"
				+ "sitekey:domain")
		String[] sessionTimeout() default StringUtils.EMPTY;

		@AttributeDefinition(name = "Bazar Voice Pass Key", description = "Please Enter Bazar Voice Pass Key for Specific Brand in the format of"
				+ "sitekey:bvPassKey")
		String[] bvPassKeys() default StringUtils.EMPTY;

		@AttributeDefinition(name = "Experience Fragment Root Path", description = "Please enter Experience Fragment"
				+ "Root path in form sitekey:path")
		String[] experienceFragmentRootPath() default StringUtils.EMPTY;

		@AttributeDefinition(name = "Root Path for Tags", description = "Please enter Tags"
				+ "Root path in form sitekey:path")
		String[] tagrootPath() default StringUtils.EMPTY;

		@AttributeDefinition(name = "Relative Path for Product Categories", description = "Please enter relative path for product categories in format. For e.g.: PLP relative path"
				+ "sitekey:path")
		String[] productCategoryPaths() default StringUtils.EMPTY;

		@AttributeDefinition(name = "Relative Path for Product Categories for Ecomm Template", description = "Please enter relative path for product categories for Ecomm Template flow in format. For e.g.: relative path"
				+ "sitekey:path")
		String[] productCategoryPathsForGT() default "ag_en:/shop";

		@AttributeDefinition(name = "Relative Path for Product Detail Pages", description = "Please enter relative path for product detail pages in format. For e.g.: PDP relative path"
				+ "sitekey:path")
		String[] productDetailPaths() default StringUtils.EMPTY;

		@AttributeDefinition(name = "Error Page for the site", description = "Please enter error page path for the site in format. For e.g.: Site Error Page"
				+ "sitekey:path")
		String[] siteErrorPages() default StringUtils.EMPTY;
		
		@AttributeDefinition(name = "Shop Home Page for the site", description = "Please enter Shop Home page path for the site in format. For e.g.: Shop Home Page"
                + "sitekey:path")
        String[] shopHomePages() default StringUtils.EMPTY;
		
		@AttributeDefinition(name = "SNP Account Endpoint URLs for the site", description = "Please enter SNP Account Endpoint URLs for the site in format. For e.g.: SNP Account Endpoint URL"
				+ "sitekey:URL")
		String[] snpAccountURLs() default StringUtils.EMPTY;

		@AttributeDefinition(name = "SNP Article Account Endpoint URLs for the site", description = "Please enter SNP Article Account Endpoint URLs for the site in format. For e.g.: SNP Account Endpoint URL"
				+ "sitekey:URL")
		String[] snpArticleAccountURLs() default StringUtils.EMPTY;
		
		@AttributeDefinition(name = "Product/Article Tile fallback image", description = "Please enter Product/Article Tile fallback image"
				+ "sitekey:imagePath")
		String[] tileFallbackImages() default StringUtils.EMPTY;
		
		@AttributeDefinition(name = "Domain for specific site key", description = "Please enter domain for specific site key"
				+ "sitekey:domain")
		String[] siteDomains() default StringUtils.EMPTY;
		
		@AttributeDefinition(name = "Timeout for session stoarge", description = "Please enter timeout for session stoarge in minutes"
				+ "sitekey:time in minutes")
		String[] sessionStorageTimeout() default StringUtils.EMPTY;
		
		@AttributeDefinition(name = "Domain for Shopify APi endpoint", description = "Please enter shopify domain for specific site key"
				+ "sitekey:domain")
		String[] shopifyDomain() default StringUtils.EMPTY;

       /** Default connection timeout for HTTP service calls for the site in milliseconds */
          @AttributeDefinition(name = "Default connection timeout for http service calls for the site"
              + " in milliseconds", description = "Please enter default connection timeout for http "
                  + "service calls for site/clientkey. For e.g. clientId:15000"
                  + "sitekey:Timeout in milliseconds")
          String[] httpServiceDefConnectTimeouts() default StringUtils.EMPTY;
          
          @AttributeDefinition(name = "Consumer Info API Endpoint", description = "Email Light Box - Consumer Info API Endpoint")
          String consumerInfoAPIEndpoint() default "https://api.sdn.mattel.com/preprod/consumerinfo";
          
          @AttributeDefinition(name = "Consumer Info API Key", description = "Email Light Box - Consumer Info API Key")
          String consumerInfoAPIKey() default "hysu29c5n4m69nrcvg3p4dr7";
          
          @AttributeDefinition(name = "Scene7 domain url", description = "Please enter scene7 domain url")
		  String scene7Url() default StringUtils.EMPTY;

	}
}
