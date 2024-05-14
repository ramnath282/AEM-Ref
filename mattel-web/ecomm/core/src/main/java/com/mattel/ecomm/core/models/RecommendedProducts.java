package com.mattel.ecomm.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class RecommendedProducts {

	@Inject
	private SlingHttpServletRequest request;

	private String componentId;

	@PostConstruct
	public void postConstruct() {

		String tempId = (String) request.getAttribute(Constant.COMPONENT_ID);
		if (tempId != null) {
			int temp = Integer.parseInt(tempId);
			temp++;
			componentId = Integer.toString(temp);
			request.setAttribute(Constant.COMPONENT_ID, tempId);
		} else {
			request.setAttribute(Constant.COMPONENT_ID, "0");
			componentId = "0";
		}

	}

	public String getComponentId() {
		return componentId;
	}

}