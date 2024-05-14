package com.mattel.global.core.model;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.collections.iterators.TransformIterator;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.mattel.global.core.services.AnalyticsDynamicDropdownService;

/**
 * @author CTS
 *
 *         Model for analytics properties attribute names, which populates
 *         datasource
 *
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class DropdownModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(DropdownModel.class);

	@Self
	private SlingHttpServletRequest request;

	@Inject
	private AnalyticsDynamicDropdownService analyticsDynamicDropdown;

	/**
	 * init method.
	 */
	@PostConstruct
	protected void init() {
		LOGGER.info("DropdownModel Model Init -> Start");
		if (request != null) {
			final ResourceResolver resolver = request.getResourceResolver();
			LOGGER.debug("Resolver -> {}", resolver);
			List<String> attrs = analyticsDynamicDropdown.getAnalyticsProperties();

			LOGGER.debug("Creating Datasource -> Start");
			// Used Transformer anonymous inner class using lambda expression
			@SuppressWarnings("unchecked")
			DataSource ds = new SimpleDataSource(new TransformIterator(attrs.iterator(), object -> {
				String attrDropdownKey = (String) object;
				ValueMap vm = new ValueMapDecorator(new HashMap<String, Object>());
				vm.put("value", attrDropdownKey);
				vm.put("text", attrDropdownKey);
				return new ValueMapResource(resolver, new ResourceMetadata(), "nt:unstructured", vm);
			}));

			LOGGER.debug("Creating Datasource -> End");

			this.request.setAttribute(DataSource.class.getName(), ds);
		}
		LOGGER.info("DropdownModel Model Init -> End");
	}

	public void setRequest(SlingHttpServletRequest request) {
		this.request = request;
	}

	public void setAnalyticsDynamicDropdown(AnalyticsDynamicDropdownService analyticsDynamicDropdown) {
		this.analyticsDynamicDropdown = analyticsDynamicDropdown;
	}

}
