package com.mattel.productvideos.core.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.productvideos.core.constants.Constants;
import com.mattel.productvideos.core.services.SearchAPIService;

@Component(service = { Servlet.class }, property = { "service.description=Search API", 
        "sling.servlet.methods=GET", "sling.servlet.paths=/bin/searchAPI",
    "sling.servlet.extensions=json" })
public class SearchAPI extends SlingAllMethodsServlet {
  private static final long serialVersionUID = 2886152895030718534L;

  private static final Logger LOGGER = LoggerFactory.getLogger(SearchAPI.class);

  private int parametersCount = 0;

  private transient Map<String, Object> requestDetailsMap = new HashMap<>();

  @Reference
  private transient SearchAPIService searchAPIServiceObj;

  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
      throws ServletException, IOException {
    LOGGER.info("Start of doGet method");
    JSONObject errorMessage = new JSONObject();
    try {
      response.setContentType("application/json");
      // check the request parameters and set the values
      extractRequestParmeters(request, response, errorMessage, requestDetailsMap);
      String responseValue = searchAPIServiceObj.getData(requestDetailsMap, Integer.toString(parametersCount),
          request);
      LOGGER.debug("response string is : {}", responseValue);
      parametersCount = 0;
      response.getWriter().print(responseValue);
    } catch (Exception e) {
      LOGGER.error("Exception in search API", e);
      parametersCount = 0;
    }
    LOGGER.info("End of doGet method");
  }

  private void extractRequestParmeters(SlingHttpServletRequest request, SlingHttpServletResponse response,
      JSONObject errorMessage, Map<String, Object> requestDetailsMap) throws JSONException, IOException {
    LOGGER.info("Start of extractRequestParmeters method");
    String keyword = "";
    String path = "";
    String filter = "";
    String sort = "";
    String sortorder = "";
    int limit = 0;
    int offset = 0;
    try {
      if (StringUtils.isEmpty(request.getParameter(Constants.KEYWORD_PARAM))) {
        keyword = "";
        parametersCount++;
      } else {
        keyword = request.getParameter(Constants.KEYWORD_PARAM);
      }
      if (StringUtils.isEmpty(request.getParameter(Constants.OFFSET_FIELD))) {
        offset = 0;
        parametersCount++;
      } else {
        offset = Integer.parseInt(request.getParameter(Constants.OFFSET_FIELD));
      }
      if (StringUtils.isEmpty(request.getParameter(Constants.LIMIT_FIELD))) {
        limit = -1;
        parametersCount++;
      } else {
        limit = Integer.parseInt(request.getParameter(Constants.LIMIT_FIELD));
      }
      if (StringUtils.isEmpty(request.getParameter(Constants.PATH_FIELD))) {
        path = "";
        parametersCount++;
      } else {
        path = request.getParameter(Constants.PATH_FIELD);
      }
      if (StringUtils.isEmpty(request.getParameter(Constants.FILTER_FIELD))) {
        filter = "";
        parametersCount++;
      } else {
        filter = request.getParameter(Constants.FILTER_FIELD);
      }
      if (StringUtils.isEmpty(request.getParameter(Constants.SORT_FIELD))) {
        sort = "";
        parametersCount++;
      } else {
        sort = request.getParameter(Constants.SORT_FIELD);
      }
      if (StringUtils.isEmpty(request.getParameter(Constants.SORT_ORDER_FIELD))) {
        sortorder = "desc";
        parametersCount++;
      } else {
        sortorder = request.getParameter(Constants.SORT_ORDER_FIELD);
      }
      requestDetailsMap.put("keyword", keyword);
      requestDetailsMap.put("path", path);
      requestDetailsMap.put("sort", sort);
      requestDetailsMap.put("filter", filter);
      requestDetailsMap.put("sortorder", sortorder);
      requestDetailsMap.put("limit", limit);
      requestDetailsMap.put("offset", offset);
      LOGGER.debug("ParametersCount is: {}", parametersCount);
      LOGGER.debug("path is: {}, Keyword is: {}", path, keyword);
      LOGGER.debug("limit is: {}, offset is: {}", limit, offset);
      LOGGER.debug("sort is: {}, filter is: {}", sort, filter);
    } catch (Exception e) {
      errorMessage.put("status", "200");
      errorMessage.put("Message", " Invalid URL");
      response.getWriter().print(errorMessage);
    }
    LOGGER.info("End of extractRequestParmeters method");
  }

}
