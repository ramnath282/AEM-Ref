package com.mattel.ecomm.core.servlets;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.ecomm.core.services.ProductAPIValidatorService;
import com.mattel.ecomm.coreservices.core.constants.Constant;

/**
 * Endpoint url of API validation service.
 */
@Component(service = Servlet.class, immediate = true, property = {
    Constants.SERVICE_DESCRIPTION + "=" + "Shopify Product API Validator url", //
    ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST, //
    ServletResolverConstants.SLING_SERVLET_PATHS + "=" + "/bin/productapivalidator" //
})
public class ProductAPIValidatorServlet extends SlingSafeMethodsServlet {
  private static final long serialVersionUID = 6330779456186080215L;
  @Reference
  private transient ProductAPIValidatorService productAPIValidatorService;
  private static final String INVALID_REQUEST_ERROR = "Invalid value of request parameter for validateType partial";
  private static final String INVALID_REQUEST = "Invalid Request";
  private static final ObjectMapper MAPPER = new ObjectMapper();

  /**
   * User can validate full set of products or partial based on query parameters.
   */
  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
      throws ServletException, IOException {
    String validateType = request.getParameter("validateType");
    Map<String, String> respObject = new HashMap<>();

    if ("full".equals(validateType)) {
       respObject = productAPIValidatorService.getFullReportFile();
    } else if ("partial".equals(validateType)) {
      String partNumbers = request.getParameter("partNumbers");
      String productType = request.getParameter("productType");

      if (StringUtils.isNotEmpty(partNumbers)) {
        respObject = productAPIValidatorService.getPartialReportFile(Arrays.asList(partNumbers.split(",")));
      } else if (StringUtils.isNotEmpty(productType)) {
        respObject = productAPIValidatorService.getPartialReportFile(productType);
      } else {
        response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
        respObject.put("error", INVALID_REQUEST_ERROR);
      }
    } else {
      response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
      respObject.put("error", INVALID_REQUEST);
    }

    response.setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
    response.getWriter().write(MAPPER.writeValueAsString(respObject));
  }
}
