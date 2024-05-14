package com.mattel.ecomm.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.Servlet;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
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

import com.adobe.xfa.ut.Base64;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.FedExService;
import com.mattel.ecomm.coreservices.core.pojos.ErrorResponse;
import com.mattel.ecomm.coreservices.core.pojos.FedExResponse;

@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "FedEx Data Servlet", //
		ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST, //
		ServletResolverConstants.SLING_SERVLET_PATHS + "=" + "/bin/fedex" //
})
public class FedExDataServlet extends SlingAllMethodsServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(FedExDataServlet.class);

	@Reference
	private transient FedExService fedExService;

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		FedExDataServlet.LOGGER.info("FedEx Servlet POST -> Start");
		FedExResponse fedexResponse = getData(request);
		readPDFData(response, fedexResponse);
		FedExDataServlet.LOGGER.info("FedEx Servlet POST -> End");
	}

	/**
	 * @param request
	 * @return
	 */
	private FedExResponse getData(SlingHttpServletRequest request) {
		FedExDataServlet.LOGGER.info("FedEx getData -> Start");
		long startTime = System.currentTimeMillis();
		FedExResponse fedExResponse = null;
		try {
			BufferedReader bufferedReader = request.getReader();
			String storeKey = request.getParameter(Constant.REQUEST_STOREID);
			String domainKey = request.getParameter(Constant.REQUEST_DOMAIN_ID);
			String requestBody = IOUtils.toString(bufferedReader);
			Map<String, Object> requestMap = new HashMap<>();
			requestMap.put(Constant.REQUEST_BODY, requestBody);
			requestMap.put(Constant.STORE_KEY, storeKey);
			requestMap.put(Constant.DOMAIN_KEY, domainKey);

			fedExResponse = fedExService.fetch(requestMap);
			long endTime = System.currentTimeMillis();
			LOGGER.info(Constant.EXECUTION_TIME_LOG, "FedEx getData -> End", endTime - startTime);
		} catch (ServiceException | IOException e) {
			long endTime = System.currentTimeMillis();
			FedExDataServlet.LOGGER.error("Exception occured in FedEx Service {}", e);
			LOGGER.debug(Constant.EXECUTION_TIME_LOG, "ServiceException in makeServiceCalls method",
					endTime - startTime);
		}
		return fedExResponse;

	}

	/**
	 * @param response
	 * @param fedexResponse
	 * @throws IOException
	 * 
	 *             method contains logic to read bytecode from service response
	 *             and set decoded bytearray to response.
	 */
	private void readPDFData(SlingHttpServletResponse response, FedExResponse fedexResponse) throws IOException {
		LOGGER.info("FedEx readPDFData -> Start");
		if (Objects.nonNull(fedexResponse)) {
			if (!StringUtils.isEmpty(fedexResponse.getImage())) {
				OutputStream os = response.getOutputStream();
				response.setStatus(SlingHttpServletResponse.SC_OK);
				response.setContentType("application/pdf");   
				String fedexlabel = "fedexlabel";
				response.setHeader("Content-Disposition","attachment; filename=" + fedexlabel);   
				String imageBiteCode = fedexResponse.getImage();
				byte[] decodedBytes = Base64.decode(imageBiteCode);
				os.write(decodedBytes);
				os.close();
			} else if (Objects.nonNull(fedexResponse.getErrors())) {
				List<ErrorResponse> errors = fedexResponse.getErrors();
				ErrorResponse errorResponse = errors.get(0);
				String errorMessage = errorResponse.getErrorMessage();
				response.sendError(SlingHttpServletResponse.SC_BAD_REQUEST, errorMessage);
			} else {
				response.sendError(SlingHttpServletResponse.SC_NOT_FOUND);
			}
		} else {
			response.sendError(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		LOGGER.info("FedEx readPDFData -> End");
	}
}
