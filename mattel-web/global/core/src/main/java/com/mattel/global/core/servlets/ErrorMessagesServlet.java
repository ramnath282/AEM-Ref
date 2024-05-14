package com.mattel.global.core.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.mattel.global.core.services.GlobalErrorMessages;

/**
 * @author CTS
 */
@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=Error Messages Servlet", "sling.servlet.paths=" + "/bin/getErrorMessages" })
public class ErrorMessagesServlet extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorMessagesServlet.class);
	@Reference
	private transient GlobalErrorMessages globalErrorMessages;

	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws IOException {
		Session session = null;
		LOGGER.info("doGet method of ErrorMessagesServlet start");
		String pagePath = globalErrorMessages.getErrorMessagePath();
		LOGGER.debug("pagePath is{}", pagePath);
		String json = "";
		String pageLocale = null;
		LOGGER.debug("selector locale is:{}", request.getRequestPathInfo().getSelectorString());
		String requestSelector = request.getRequestPathInfo().getSelectorString();
		if (null != requestSelector) {
			pageLocale = requestSelector;
		}
		Locale locale = new Locale("en");
		if (StringUtils.isNotEmpty(pageLocale)) {
			LOGGER.debug("locale is:{}", pageLocale);
			locale = new Locale(pageLocale);
		}
		Resource resource = request.getResourceResolver().getResource(pagePath);
		session = request.getResourceResolver().adaptTo(Session.class);
		if (null != resource) {
			String defaultErrorMessage = resource.getValueMap().get("defaultErrorMessage", String.class);
			Iterable<Resource> itr = resource.getChildren();
			if (itr.iterator().hasNext()) {
				LOGGER.debug("iterator's next value for errormessage:{}", itr.iterator().next().getPath());
				Resource multifieldRes = itr.iterator().next();
				LOGGER.debug("multifield resource is:{} ", multifieldRes.getPath());
				try {

					ResourceBundle resourceBundle = request.getResourceBundle(locale);
					if (null != session) {
						Node multifieldNode = session.getNode(multifieldRes.getPath());

						LOGGER.debug("multifieldNode path is:{}", multifieldNode.getPath());
						Map<String, String> errorMessageList = getErrorMessageList(multifieldNode, resourceBundle);
						errorMessageList.put("Default Error Message", defaultErrorMessage);
						Gson gson = new Gson();
						json = gson.toJson(errorMessageList);
						response.setContentType("application/json");
						response.getWriter().write(json);
					}
				} catch (RepositoryException e) {
					LOGGER.error("RepositoryException:{}", e);
				}
			}
		}

		LOGGER.info("doGet method of ErrorMessagesServlet end");
	}

	private Map<String, String> getErrorMessageList(Node multifieldNode, ResourceBundle resourceBundle)
			throws RepositoryException {
		Map<String, String> errorMessageList = new HashMap<>();
		if (multifieldNode.hasNodes()) {
			LOGGER.debug("multifield node has item nodes");
			NodeIterator itemItr = multifieldNode.getNodes();
			while (itemItr.hasNext()) {
				LOGGER.debug("iterating through item nodes");
				Node itemNode = itemItr.nextNode();
				String errorCode = itemNode.getProperty("errorCode").getString();
				if (null != resourceBundle) {
					String errorMessage = resourceBundle.getString(itemNode.getProperty("errorMessage").getString());
					errorMessageList.put(errorCode, errorMessage);
				}
			}
			LOGGER.debug("error messages list is:{}", errorMessageList);
		}
		return errorMessageList;

	}

	public void setGlobalErrorMessages(GlobalErrorMessages globalErrorMessages) {
		this.globalErrorMessages = globalErrorMessages;
	}
}
