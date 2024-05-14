package com.mattel.ecomm.core.interfaces;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.json.JSONObject;

public interface CategoryNavigationService {
	JSONObject processNavLinks(String deviceType, Resource currentresource, SlingHttpServletRequest request);
}
