package com.mattel.ecomm.core.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.services.DeleteNodeServiceImpl;

@Component(service = Servlet.class, immediate = true, property = {
    Constants.SERVICE_DESCRIPTION + "=" + "Content Node Deletion", //
    ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET, //
    ServletResolverConstants.SLING_SERVLET_PATHS + "=" + "/bin/deleteextranodes" //
})
public class ContentNodeDeletionServlet extends SlingSafeMethodsServlet {
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(ContentNodeDeletionServlet.class);
  private static final String ATHENTICATION_KEY = "deleteprop";
  private static final String ATHENTICATION_VALUE = "true";
  private static final String CONTENT_ROOT_PATH = "contentpath";
  private static final String RESPONSE_KEY = "PLP Content Update Status";

  @Reference
  transient DeleteNodeServiceImpl deleteNodeServiceImpl;

  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
      throws ServletException, IOException {
    LOGGER.info("ContentNodeDeletionServlet : doPost -> Start");
    JSONObject responseJson = new JSONObject();
    String authVal = request.getParameter(ContentNodeDeletionServlet.ATHENTICATION_KEY);
    String contentPath = request.getParameter(ContentNodeDeletionServlet.CONTENT_ROOT_PATH);
    if (StringUtils.isNotEmpty(authVal) && StringUtils.isNotEmpty(contentPath)
        && ContentNodeDeletionServlet.ATHENTICATION_VALUE.equalsIgnoreCase(authVal)) {
      Map<String, Object> serviceResponse = deleteNodeServiceImpl.deleteNodes(contentPath);
      try {
        if (null != serviceResponse && !serviceResponse.isEmpty()) {
          responseJson.put(ContentNodeDeletionServlet.RESPONSE_KEY, serviceResponse);
        } else {
          responseJson.put(ContentNodeDeletionServlet.RESPONSE_KEY,
              "feature not enabled / no content to update");
        }
      } catch (JSONException e) {
        LOGGER.error("Exception: {} ", e);

      }

    } else {
      try {
        responseJson.put(ContentNodeDeletionServlet.RESPONSE_KEY, "invalid authentication key");
      } catch (JSONException e) {
        LOGGER.error("Exception: {} ", e);

      }
    }
    response.setContentType("application/json");
    response.getWriter().print(responseJson);
    LOGGER.info("ContentNodeDeletionServlet : doPost -> End");
  }
  public void setDeleteNodeServiceImpl(DeleteNodeServiceImpl deleteNodeServiceImpl) {
    this.deleteNodeServiceImpl = deleteNodeServiceImpl;
  }
}
