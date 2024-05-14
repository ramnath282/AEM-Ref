package com.mattel.global.core.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.jcr.Node;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.QueryBuilder;
import com.google.common.base.Strings;

/**
 * @author CTS
 *
 *         Service for Analytics properties Dropdown.
 *
 */
@Component(service = AnalyticsDynamicDropdownService.class, immediate = true)
@Designate(ocd = AnalyticsDynamicDropdownService.Config.class)
public class AnalyticsDynamicDropdownService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsDynamicDropdownService.class);

	@Reference
	ResourceResolverFactory resolverFactory;

	@Reference
	QueryBuilder queryBuilder;

	@Reference
	MultifieldReader multifieldReader;

	private String analyticsPropertisPath;

	/**
	 * @param fieldTypeValue
	 * @param path
	 * @return
	 * 
	 * 		Method includes logic to get previously selected value for passed
	 *         analytics attribute.
	 */
	private String getDefaultSelect(String fieldTypeValue, String path) {
		LOGGER.info("getDefaultSelect -> Start");
		LOGGER.debug("FieldType : {} , Pagepath : {}", fieldTypeValue, path);
		String defaultVal = "";
		ResourceResolver resolver = null;
		try {
			Map<String, Object> map = new HashMap<>();
			map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
			if (resolverFactory != null) {
				resolver = resolverFactory.getServiceResourceResolver(map);
				Resource resource = resolver.resolve(path);
				Node analyticsPropNode = resource.adaptTo(Node.class);
				if (analyticsPropNode != null) {
					Map<String, ValueMap> analyticsValueMap = multifieldReader.propertyReader(analyticsPropNode);
					LOGGER.debug("Analytics attrname : value map : {} ", analyticsValueMap);
					for (Map.Entry<String, ValueMap> entry : analyticsValueMap.entrySet()) {
						String propName = entry.getValue().get("propertyName", String.class);
						if (propName != null && propName.equalsIgnoreCase(fieldTypeValue)) {
							defaultVal = entry.getValue().get("propertyValue", String.class);
						}
					}
				}
			}
		} catch (LoginException e) {
			LOGGER.error("Error", e);
		} finally {
			if (resolver != null && resolver.isLive()) {
				resolver.close();
			}
		}
		LOGGER.debug("Default Value for : {} ", defaultVal);
		LOGGER.info("getDefaultSelect -> End");
		return defaultVal;
	}

	/**
	 * @return
	 * 
	 * 		Method returns all analytics fields attribute.
	 */
	public List<String> getAnalyticsProperties() {
		LOGGER.info("getAnalyticsAttr -> Start");
		Set<String> analyticsPropertyAttr = readAnalyticsNodes().keySet();
		LOGGER.debug("Keys : {} ", analyticsPropertyAttr);
		List<String> analyticsPropertyAttrLst = analyticsPropertyAttr.stream().collect(Collectors.toList());
		LOGGER.info("getAnalyticsAttr -> End");
		return analyticsPropertyAttrLst;
	}

	/**
	 * @param fieldTypeValue
	 * @param path
	 * @return
	 * 
	 * 		Method includes logic to get all values for particular analytics
	 *         field attribute.
	 */
	public Map<String, String> getAnalyticsPropertyValue(String fieldTypeValue, String path) {
		LOGGER.info("getAnalyticsAttrValue -> Start");
		String defaultVal = getDefaultSelect(fieldTypeValue, path);
		Map<String, String> analyticsPropertyAttrValuesMap = new LinkedHashMap<>();
		if (!Strings.isNullOrEmpty(defaultVal)) {
			analyticsPropertyAttrValuesMap.put(defaultVal, defaultVal);
		}
		String val = readAnalyticsNodes().get(fieldTypeValue);
		LOGGER.debug("Attr value : {} ", val);
		if (val != null) {
			List<String> lst = Arrays.asList(val.split("~"));
			lst.forEach(item -> {
				if (Strings.isNullOrEmpty(defaultVal) || !item.equalsIgnoreCase(defaultVal)) {
					analyticsPropertyAttrValuesMap.put(item, item);
				}
			});
		}
		LOGGER.debug("property name : {} , property values : {} ", fieldTypeValue, analyticsPropertyAttrValuesMap);
		LOGGER.info("getAnalyticsAttrValue -> End");
		return analyticsPropertyAttrValuesMap;
	}

	/**
	 * Method added to read all attribute and there values from mentioned path
	 * 
	 * @return
	 */
	private Map<String, String> readAnalyticsNodes() {
		LOGGER.info("ReadAnalyticsNodes -> Start");
		Map<String, String> analyticsProperyMap = new LinkedHashMap<>();
		ResourceResolver resolver = null;
		String pagePath = getAnalyticsPropertisPath();
		LOGGER.debug("Analytics Node Path: {} ", pagePath);
		try {
			Map<String, Object> map = new HashMap<>();
			map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
			if (resolverFactory != null) {
				resolver = resolverFactory.getServiceResourceResolver(map);
				Resource resource = resolver.getResource(pagePath);
				if (resource != null) {
					Node analyticsPropNode = resource.adaptTo(Node.class);
					Map<String, ValueMap> analyticsValueMap = multifieldReader.propertyReader(analyticsPropNode);
					for (Map.Entry<String, ValueMap> entry : analyticsValueMap.entrySet()) {
						analyticsProperyMap.put(entry.getValue().get("propName", String.class),
								entry.getValue().get("propValue", String.class));
					}
				}
				LOGGER.debug("End -> readAnalyticsNodes");
			}
		} catch (LoginException e) {
			LOGGER.error("Error : ", e);
		}
		LOGGER.debug("Attr -> AttrValues Map : {} ", analyticsProperyMap);
		LOGGER.info("ReadAnalyticsNodes -> End");
		return analyticsProperyMap;
	}

	@ObjectClassDefinition(name = "Analytics properties")
	public @interface Config {

		@AttributeDefinition(name = "Analytics properties Page Path", description = "Please provide the rootpath of Analytics properties page component.")
		String analyticsPropertisPath() default "/content/fisher-price/language-masters/analytics-properties/jcr:content/root/analyticsprop/analyticsProperties";
	}

	/**
	 * @param config
	 */
	@Activate
	public void activate(final Config config) {
		analyticsPropertisPath = config.analyticsPropertisPath();
	}

	public String getAnalyticsPropertisPath() {
		return analyticsPropertisPath;
	}

	public void setResolverFactory(ResourceResolverFactory resolverFactory) {
		this.resolverFactory = resolverFactory;
	}

	public void setAnalyticsPropertisPath(String analyticsPropertisPath) {
		this.analyticsPropertisPath = analyticsPropertisPath;
	}

	public void setMultifieldReader(MultifieldReader multifieldReader) {
		this.multifieldReader = multifieldReader;
	}
	
}
