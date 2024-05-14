package com.mattel.global.core.servlets;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.services.HttpXmlNodeDataService;

/** Generic servlet to fetch AEM repository node data in XML or JSON format. */
@Component(service = Servlet.class, immediate = true, property = {
    Constants.SERVICE_DESCRIPTION + "=" + "Get node data in form of XML or JSON format", //
    ServletResolverConstants.SLING_SERVLET_PATHS + "=" + "/bin/nodewriterservice" //
})
public class HttpNodeWriterServlet extends SlingAllMethodsServlet {
  private static final Logger LOGGER = LoggerFactory.getLogger(HttpNodeWriterServlet.class);
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 3489576524854197695L;
  @Reference
  transient HttpXmlNodeDataService xmlNodeDataService;

  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
      throws ServletException, IOException {
    final String appName = request.getParameter("appName");
    final String contentType = request.getParameter("type");
    final String locale = request.getParameter("locale");
    final ResourceResolver resolver = request.getResourceResolver();

    HttpNodeWriterServlet.LOGGER.info(
        "doGet method, Fetch node data for following query: appName: {}, type: {}, locale: {}",
        new Object[] { appName, contentType, locale });

    // Checking if content-type supported.
    if (StringUtils.isNotBlank(appName) && StringUtils.isNotBlank(contentType)) {
      switch (contentType) {
        case "json":
        case "xml":
          xmlNodeDataService.write(appName, locale, resolver, response);
          break;
        default:
          HttpNodeWriterServlet.LOGGER
              .error("Content type not supported, unable to fetch node data");
          response.sendError(HttpURLConnection.HTTP_NOT_FOUND);
          break;
      }
      return;
    }

    HttpNodeWriterServlet.LOGGER.error(
        "doGet method, Invalid query parameters appName or contentType encountered, unable to fetch node data");
    response.sendError(HttpURLConnection.HTTP_NOT_FOUND);
  }
}
