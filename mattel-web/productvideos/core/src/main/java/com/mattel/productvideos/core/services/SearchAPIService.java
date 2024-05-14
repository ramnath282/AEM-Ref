package com.mattel.productvideos.core.services;

import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;

public interface SearchAPIService {
  String getData(Map<String, Object> requestDetailsMap, String paramString6, SlingHttpServletRequest paramSlingHttpServletRequest);
}
