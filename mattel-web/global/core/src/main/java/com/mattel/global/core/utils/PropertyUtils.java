package com.mattel.global.core.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.constants.Constant;

public class PropertyUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtils.class);

	/**
	 * @return storeKeyValueMap
	 */
	public static String getValueFromKeyMappings(String[] idMappings, String key) {
		LOGGER.info("PropertyUtils:Start of getValueFromKeyMappings method");
		long startTime = System.currentTimeMillis();
		String value = StringUtils.EMPTY;
		Map<String, String> configKeyValueMap = new HashMap<>();
		for (String mapping : idMappings) {
			String[] mappingIdSplit = mapping.split(":", 2);
			LOGGER.debug("mappingIdSplit :: {}", mappingIdSplit);
			if (mappingIdSplit.length > 1) {
				configKeyValueMap.put(mappingIdSplit[0], mappingIdSplit[1]);
				LOGGER.debug("configKeyValueMap{}", configKeyValueMap);
			}
		}
		if (configKeyValueMap.containsKey(key)) {
			value = configKeyValueMap.get(key);
			LOGGER.debug("value :: {}", value);

		}
		LOGGER.debug("configKeyValueMap :: {}", configKeyValueMap);
		long endTime = System.currentTimeMillis();
		LOGGER.debug("time :: {}", endTime - startTime);
		LOGGER.info("PropertyUtils :End of getValueFromKeyMappings method");
		return value;
	}

	/**
	 * Private Constructor for Sonar issue.
	 */
	private PropertyUtils() {
	}

	public static Map<String,String> getBrandDetails(String brandSiteMapConfig) {
		LOGGER.info("start getBrandDetails method");	 
		Map<String,String>  map = new HashMap<>();
		if(StringUtils.isNotBlank(brandSiteMapConfig)) { 
			String [] brandDetail = brandSiteMapConfig.split("=");
			map.put("brand",brandDetail[0]);
			LOGGER.debug("details :: {}",brandDetail[1]);
			String [] brandConfig = brandDetail[1].split("_");
			map.put(Constant.ROOT_SITE_MAP_PATH,brandConfig[0]);
			map.put(Constant.SITE_ROOT_PATH,brandConfig[1]);	
			map.put(Constant.SITE_DOMAIN,brandConfig[2]);
			map.put(Constant.SHORT_SITE_DOMAIN,brandConfig[3]);	
			LOGGER.debug("map :: {}", map);
			LOGGER.debug("Fisher Price Sitemap Scheduler :{}", Arrays.asList(map));
		}
		return map;

	}
}
