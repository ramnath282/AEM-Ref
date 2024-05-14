package com.mattel.ecomm.core.servlets;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.mattel.ecomm.core.interfaces.ProductDataImporterService;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.DataImporterErrorCode;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.ProductDataImporterResponse;
import com.mattel.ecomm.coreservices.core.utilities.ResponseFormatGetter;

@Component(service = Servlet.class, immediate = true, property = {
        Constants.SERVICE_DESCRIPTION + "=" + "Data importer servlet to consume data from incoming response body or from remote feed endpoint URL", //
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST, //
        ServletResolverConstants.SLING_SERVLET_PATHS + "=" + "/bin/dataimporthandler" //
})
public class ProductDataImporterServlet extends SlingAllMethodsServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDataImporterServlet.class);
    private static final long serialVersionUID = 7946398146881502198L;
    private static final ObjectWriter RESP_WRITER = ResourceMapper.getWriterInstance(ProductDataImporterResponse.class);
    @Reference
    private transient ProductDataImporterService productDataImporterService;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        final Map<String, Object> requestAttributes = new HashMap<>();

        try {
            if (request.getContentLength() != 0) {
                final ProductDataImporterResponse productDataImporterResponse = productDataImporterService
                        .readInputData(request.getInputStream(), requestAttributes);

                response.setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
                ProductDataImporterServlet.RESP_WRITER.writeValue(response.getWriter(), productDataImporterResponse);
            } else {
                response.setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
                response.getWriter().write(
                        ResponseFormatGetter
                                .getErrorJson(new ServiceException(String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST),
                                        DataImporterErrorCode.REQUEST_BODY_EMPTY.getErrorMessage())).toString());
            }
        } catch (final ServiceException e) {
            final String errorKey = e.getErrorKey();
            final DataImporterErrorCode dataImporterErrorCode = DataImporterErrorCode.valueOf(errorKey);

            ProductDataImporterServlet.LOGGER.error("Unable to save product feed file in respository", e);
            response.setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);

            switch (dataImporterErrorCode) {
                case REQUEST_BODY_EMPTY:
                case REQUEST_BODY_NULL:
                    response.getWriter().write(
                            ResponseFormatGetter.getErrorJson(
                                    new ServiceException(String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST),
                                            dataImporterErrorCode.getErrorMessage())).toString());
                    break;
                case ASSERT_MANAGER_UNAVAILABLE:
                    response.getWriter().write(
                            ResponseFormatGetter.getErrorJson(new ServiceException(
                                    String.valueOf(HttpURLConnection.HTTP_UNAVAILABLE), "Service Unavailable")).toString());
                    break;
                default:
                    response.getWriter().write(
                            ResponseFormatGetter.getErrorJson(new ServiceException(
                                    String.valueOf(HttpURLConnection.HTTP_INTERNAL_ERROR), DataImporterErrorCode.INTERNAL_ERROR.getErrorMessage())).toString());
                    break;
            }
        } catch (final Exception e) {
            ProductDataImporterServlet.LOGGER
                    .error("Unknown error encountered while to save product feed file in respository", e);
            response.setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
            response.getWriter().write(ResponseFormatGetter.getErrorJson(
                    new ServiceException(String.valueOf(HttpURLConnection.HTTP_INTERNAL_ERROR), DataImporterErrorCode.INTERNAL_ERROR.getErrorMessage())).toString());
        }
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Start of doGet method");
        String feed = request.getParameter("feed");
        if ("delta".equals(feed) || "full".equals(feed) || "delete".equals(feed)) {
        	LOGGER.debug("Feed Type is {}", feed);
        	try{
        		URL endPointUrl = null;
        		if("full".equals(feed)){
        		    endPointUrl = productDataImporterService.getProductFeedEndPointURL();
        		} else if("delete".equals(feed)){
        		    endPointUrl = productDataImporterService.getDeleteProductFeedEndPointURL();
        		}
        		if(Objects.nonNull(endPointUrl)){
                	LOGGER.debug("Feed endpoint URL is {}", endPointUrl);
                    getJsonFromEndPointAndUploadToDam(response, endPointUrl,feed);
                }
        	} catch(MalformedURLException e){
        		LOGGER.error("Malformed URL configured", e);
        		response.sendError(HttpURLConnection.HTTP_INTERNAL_ERROR, e.getMessage());
        	}
        } else {
            response.setContentType("text/plain");
            response.getWriter().write("Parameter does not match with required.");
        }
        LOGGER.info("End of doGet method");
    }

    /**
     * this method reads the JSON from endpoint and uploads the same in DAM
     * @param response Sling HTTP Servlet Response
     * @param endPointUrl 
     * @param feedType 
     * */
    private void getJsonFromEndPointAndUploadToDam(final SlingHttpServletResponse response, URL endPointUrl, String feedType)
            throws IOException {
        LOGGER.info("Start of getJsonFromEndPointAndUploadToDam method");

        URLConnection uc = endPointUrl.openConnection();
        final Map<String, Object> requestAttributes = new HashMap<>();
        requestAttributes.put("feedType", feedType);
        try {
            if (uc.getContentLength() != 0) {
                final ProductDataImporterResponse productDataImporterResponse = productDataImporterService
                        .readInputData(uc.getInputStream(), requestAttributes);

                response.setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
                ProductDataImporterServlet.RESP_WRITER.writeValue(response.getWriter(), productDataImporterResponse);
            } else {
                response.setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
                response.getWriter().write(
                        ResponseFormatGetter
                                .getErrorJson(new ServiceException(String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST),
                                        DataImporterErrorCode.NO_FILE_DATA.getErrorMessage())).toString());
            }
        } catch (final ServiceException e) {
            final String errorKey = e.getErrorKey();
            final DataImporterErrorCode dataImporterErrorCode = DataImporterErrorCode.valueOf(errorKey);

            ProductDataImporterServlet.LOGGER.error("Unable to save product feed file in respository", e);
            response.setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);

            switch (dataImporterErrorCode) {
                case NO_FILE_DATA:
                    response.getWriter().write(
                            ResponseFormatGetter.getErrorJson(
                                    new ServiceException(String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST),
                                            dataImporterErrorCode.getErrorMessage())).toString());
                    break;
                case ASSERT_MANAGER_UNAVAILABLE:
                    response.getWriter().write(
                            ResponseFormatGetter.getErrorJson(new ServiceException(
                                    String.valueOf(HttpURLConnection.HTTP_UNAVAILABLE), "Service Unavailable")).toString());
                    break;
                default:
                    response.getWriter().write(
                            ResponseFormatGetter.getErrorJson(new ServiceException(
                                    String.valueOf(HttpURLConnection.HTTP_INTERNAL_ERROR), DataImporterErrorCode.INTERNAL_ERROR.getErrorMessage())).toString());
                    break;
            }
        } catch (final Exception e) {
            ProductDataImporterServlet.LOGGER
                    .error("Unknown error encountered while to save product feed file in respository", e);
            response.setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
            response.getWriter().write(ResponseFormatGetter.getErrorJson(
                    new ServiceException(String.valueOf(HttpURLConnection.HTTP_INTERNAL_ERROR), DataImporterErrorCode.INTERNAL_ERROR.getErrorMessage())).toString());
        }
    }
}
