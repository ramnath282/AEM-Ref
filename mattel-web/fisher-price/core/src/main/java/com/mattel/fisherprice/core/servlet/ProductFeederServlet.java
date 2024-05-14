package com.mattel.fisherprice.core.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.AssetManager;
import com.mattel.fisherprice.core.services.GetResourceResolver;

@Component(service = Servlet.class, immediate = true, property = {
        Constants.SERVICE_DESCRIPTION + "=Product Feeder JSON creation and upload to DAM",
        "sling.servlet.paths=" + "/bin/product_feeder"})
@Designate(ocd = ProductFeederServlet.Config.class)
public class ProductFeederServlet extends SlingSafeMethodsServlet {

    /**
     *
     */

    @Reference
    private transient GetResourceResolver getResourceResolver;

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductFeederServlet.class);
    static final String SLASH = "/";
    String rootPath;
    private static String salsifyEndPointUrl;

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.debug("start of doPost method {}", System.currentTimeMillis());
        String feed = req.getParameter("feed");
        String language = req.getParameter("language");
        LOGGER.debug("Before {}", salsifyEndPointUrl);
        LOGGER.debug("After {}", salsifyEndPointUrl);
        if ("delta".equals(feed) || "full".equals(feed)) {
            String sEndPointUrl = StringUtils.isNotBlank(salsifyEndPointUrl) ? salsifyEndPointUrl.replace("X", language)
                    : salsifyEndPointUrl;
            sEndPointUrl = sEndPointUrl.isEmpty() ? "" : sEndPointUrl.replace("Feed", feed);
            URL endPointUrl = new URL(sEndPointUrl);
            getJsonFromEndPointAndUploadToDam(resp, endPointUrl, language);
        } else {
            resp.setContentType("text/plain");
            resp.getWriter().write("Parameter not match with expected........");
        }
        LOGGER.debug("end of doPost method {}", System.currentTimeMillis());
    }

    private void getJsonFromEndPointAndUploadToDam(final SlingHttpServletResponse resp, URL endPointUrl, String lang)
            throws IOException {
        LOGGER.debug("Start getJsonFromEndPointAndUploadToDam Method");
        URLConnection uc = endPointUrl.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        ResourceResolver resourceResolver = getResourceResolver.getResourceResolver();
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        in.close();
        String data = buffer.toString();
        LocalDateTime currentTime = LocalDateTime.now();
        Integer month = currentTime.getMonthValue();
        Integer day = currentTime.getDayOfMonth();
        Integer year = currentTime.getYear();
        lang = lang.replace('_', '-');
        lang = lang.toLowerCase();
        String date = year.toString() + "_" + month.toString() + "_" + day.toString() + "_" + currentTime.getHour()
                + currentTime.getMinute() + currentTime.getSecond();
        String damJsonUploadPath = rootPath + year + SLASH + month + SLASH + day + "/productfeed_" + lang + "_" + date
                + ".json";
        InputStream stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        if (null != resourceResolver) {
            AssetManager assetMgr = resourceResolver.adaptTo(AssetManager.class);
            if (null != assetMgr) {
                assetMgr.createAsset(damJsonUploadPath, stream, "application/json", true);
                resp.setContentType("text/plain");
                resp.getWriter().write(damJsonUploadPath);
            }
        }
        LOGGER.debug("End getJsonFromEndPointAndUploadToDam Method");
    }

    @ObjectClassDefinition(name = "FP Product Feeder Configuration")
    public @interface Config {
        @AttributeDefinition(name = "Dam Path", description = "Please provide the root path where json file will be stored. Default is /content/dam/fp-dam/fisher-price/product-feed/")
        String damRootPath() default "/content/dam/fp-dam/fisher-price/product-feed/";

        @AttributeDefinition(name = "End Point URL for Delta", description = "Please provide the endpoint url for delta/full product feed")
        String salsifyEndPointUrl();
    }

    @Activate
    public void activate(final Config config) {
        rootPath = config.damRootPath();
        ProductFeederServlet.buildConfigs(config);
    }

    private static void buildConfigs(final Config config) {
        salsifyEndPointUrl = config.salsifyEndPointUrl();
    }
}
