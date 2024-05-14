package com.mattel.play.core.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.xfa.ut.StringUtils;
import com.mattel.play.core.helper.PlayHelper;
import com.mattel.play.core.utils.PropertyReaderUtils;

@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=Rescue Heroes Header and Footer Servlet",
		"sling.servlet.paths=" + "/bin/getHeadernFooter" })
public class RescueHeaderFooterServlet extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(RescueHeaderFooterServlet.class);

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		try (CloseableHttpClient client = HttpClients.createDefault();) {
			LOGGER.info("Start of doGet method RescueHeaderFooterServlet");
			response.setContentType("text/html");
			String currentPagePath = request.getParameter("currentPagePath");
			String[] pagePathStrArray = currentPagePath.split("/");
			String pageLocale = null;
			if(pagePathStrArray[2].equalsIgnoreCase("rescue-heroes")) {
				pageLocale = pagePathStrArray[1];
			} else {
				pageLocale = PlayHelper.getPageLocale(currentPagePath);
			}			
			pageLocale = pageLocale.replace("-", "_");
			String requestFor = request.getParameter("requestFor");
			String url = PropertyReaderUtils.getRescueHeaderFooterURL();
			if (!StringUtils.isEmpty(requestFor)) {
				url = url.replace("#pageLocale", pageLocale);
				url = url.replace("#requestFor", requestFor).trim();
				HttpGet getMethod = new HttpGet(url);
				HttpResponse httpResponse = client.execute(getMethod);
				int status = httpResponse.getStatusLine().getStatusCode();
				boolean isError = isError(status);
				if (!isError) {
					InputStream inputStream = httpResponse.getEntity().getContent();
					String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
					response.setStatus(status);
					response.getWriter().print(result);
				} else {
					response.setStatus(500);
					response.getWriter().print("Server Error");
				}
			}
		}
	}

	public boolean isError(int status) {
		if (status == 200 || status == 201 || status == 204 || status == 400 || status == 401) {
			return false;
		} else {
			return true;
		}
	}
}
