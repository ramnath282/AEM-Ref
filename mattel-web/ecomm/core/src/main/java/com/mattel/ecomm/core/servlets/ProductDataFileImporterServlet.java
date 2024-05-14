package com.mattel.ecomm.core.servlets;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
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
        Constants.SERVICE_DESCRIPTION + "=" + "Data importer servlet to consume data from incoming response body", //
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST, //
        ServletResolverConstants.SLING_SERVLET_PATHS + "=" + "/bin/datafileimporthandler" //
})
public class ProductDataFileImporterServlet extends SlingAllMethodsServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDataFileImporterServlet.class);
    private static final long serialVersionUID = 7946398146881502198L;
    private static final ObjectWriter RESP_WRITER = ResourceMapper.getWriterInstance(ProductDataImporterResponse.class);
    @Reference
    private transient ProductDataImporterService productDataImporterService;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        final Map<String, Object> requestAttributes = new HashMap<>();

        try {
            final boolean isMultipart = ServletFileUpload.isMultipartContent(request);

            if (isMultipart) {
                final Map<String, RequestParameter[]> params = request.getRequestParameterMap();
                final RequestParameter[] fileParam = params.get("file");

                if (Objects.nonNull(fileParam) && fileParam.length > 0 && !fileParam[0].isFormField()) {
                    final ProductDataImporterResponse productDataImporterResponse = productDataImporterService
                            .readInputData(fileParam[0].getInputStream(), requestAttributes);

                    response.setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
                    ProductDataFileImporterServlet.RESP_WRITER.writeValue(response.getWriter(),
                            productDataImporterResponse);
                    
                } else {
                    response.setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
                    response.getWriter().write(
                            ResponseFormatGetter.getErrorJson(
                                    new ServiceException(String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST),
                                            DataImporterErrorCode.NO_MULTIPART_FILE_FOUND.getErrorMessage())).toString());
                }
            } else {
                response.setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
                response.getWriter().write(
                        ResponseFormatGetter
                                .getErrorJson(new ServiceException(String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST),
                                        DataImporterErrorCode.NO_MULTIPARTDATA.getErrorMessage())).toString());
            }
        } catch (final ServiceException e) {
            final String errorKey = e.getErrorKey();
            final DataImporterErrorCode dataImporterErrorCode = DataImporterErrorCode.valueOf(errorKey);

            ProductDataFileImporterServlet.LOGGER.error("Unable to save product feed file in respository", e);
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
                                    String.valueOf(HttpURLConnection.HTTP_INTERNAL_ERROR), "Internal Error")).toString());
                    break;
            }
        } catch (final Exception e) {
            ProductDataFileImporterServlet.LOGGER
                    .error("Unknown error encountered while to save product feed file in respository", e);
            response.setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
            response.getWriter().write(
                    ResponseFormatGetter.getErrorJson(new ServiceException(
                            String.valueOf(HttpURLConnection.HTTP_INTERNAL_ERROR), "Internal Error")).toString());
        }
    }
}
