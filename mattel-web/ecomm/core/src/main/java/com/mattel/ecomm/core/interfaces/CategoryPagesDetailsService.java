package com.mattel.ecomm.core.interfaces;

import java.io.IOException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.json.JSONObject;

public interface CategoryPagesDetailsService {
  JSONObject getCategoryPagesJson(String pagePath, String productgridResourcePath,
      String agPlpTemplatePath);

  void getCategories(final SlingHttpServletRequest request, final SlingHttpServletResponse response,
      int parentDepth, String productgridResourcePath, String plpTemplatePath) throws IOException;
}
