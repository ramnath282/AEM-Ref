package com.mattel.ecomm.core.models;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import com.mattel.ecomm.core.interfaces.GetProductTypeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.*;

@Model(adaptables = SlingHttpServletRequest.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductTypeDatasourceModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductTypeDatasourceModel.class);
    @SlingObject
    SlingHttpServletRequest slingHttpServletRequest;
    @OSGiService
    private GetProductTypeService getProductType;
    List<Resource> resourceList = new ArrayList<>();
    @PostConstruct
    protected void init() {
        LOGGER.info("Start ProductTypeDatasourceModel of Init method");
        String requestPath = slingHttpServletRequest.getPathInfo();
        LOGGER.debug("Request Path is {}", requestPath);
        String requestPathWithoutDialog = StringUtils.substringAfter(requestPath,"cq_dialog.html");
        String adminPagePath = StringUtils.substringBefore(requestPathWithoutDialog,JcrConstants.JCR_CONTENT);
        LOGGER.debug("Admin Page path is {}", adminPagePath);
        Resource resource = slingHttpServletRequest.getResourceResolver().getResource(adminPagePath);
        String clientIdentifier = null;
        if (null != resource) {
            Page page = resource.adaptTo(Page.class);
            if (null != page) {
                clientIdentifier = page.getProperties().get("siteKey", String.class);
                LOGGER.debug("Client identifier is {}", clientIdentifier);
            }
        }
        Set<String> productTypes = getProductType.getProductTypeDatasource(clientIdentifier);
        ResourceResolver resolver = slingHttpServletRequest.getResourceResolver();
        Iterator<String> iterator = productTypes.iterator();
        while (iterator.hasNext()) {
            String productType = iterator.next();
            ValueMap valueMap = new ValueMapDecorator(new HashMap<>());
            valueMap.put("value", productType);
            valueMap.put("text", productType);
            resourceList.add(new ValueMapResource(resolver, new ResourceMetadata(),
                    JcrConstants.NT_UNSTRUCTURED,valueMap));
        }
        LOGGER.debug("Resource List {}", resourceList);
        DataSource dataSource = new SimpleDataSource(resourceList.iterator());
        slingHttpServletRequest.setAttribute(DataSource.class.getName(),dataSource);
        LOGGER.info("End ProductTypeDatasourceModel of Init method");
    }

}

