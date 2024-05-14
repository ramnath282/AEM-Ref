package com.mattel.ecomm.core.services;

import com.mattel.ecomm.core.interfaces.GetTemplateTypeService;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import java.util.HashMap;
import java.util.Map;

@Component(service = GetTemplateTypeService.class)
public class GetTemplateType implements GetTemplateTypeService {
    @Reference
    private PropertyReaderService propertyReaderService;
    @Reference
    private GetResourceResolver getResourceResolver;
    @Reference
    private MultifieldReader multifieldReader;

    private static final Logger LOGGER = LoggerFactory.getLogger(GetTemplateType.class);
    private static final String PRODUCT_TYPE_COMPONENT_PATH = "/jcr:content/root"+ Constant.SLASH +"producttypetemplatec" +
            "/productTypeMapping";
    public String getTemplatePath(String productType, String clientIdentifier) {
        LOGGER.info("Start of get Template Path Method");
        long startTime = System.currentTimeMillis();
        ResourceResolver resourceResolver = getResourceResolver.getResourceResolver();
        try {
            String adminPage = propertyReaderService.getadminPagePath(clientIdentifier);
            LOGGER.debug("Admin page path is {}", adminPage);
            Resource resource = resourceResolver.getResource(adminPage + PRODUCT_TYPE_COMPONENT_PATH );
            Node multifieldNode;
            Map<String, String> productTemplateMap = new HashMap<>();
            if (null != resource) {
                multifieldNode = resource.adaptTo(Node.class);
                Map<String, ValueMap> stringValueMapMap = multifieldReader.propertyReader(multifieldNode);
                LOGGER.debug("Multifield Map is {}", stringValueMapMap);
                for (Map.Entry<String, ValueMap> entry : stringValueMapMap.entrySet()) {
                    productTemplateMap.put(entry.getValue().get("productType", String.class),
                            entry.getValue().get("pageUrl", String.class));
                }
            }
            LOGGER.debug("product template map is {}", productTemplateMap);
            long endTime = System.currentTimeMillis();
            LOGGER.debug("{} Get Template Type method {}",Constant.EXECUTION_TIME_LOG ,endTime-startTime);
            return productTemplateMap.get(productType);
        } finally {
            if(resourceResolver.isLive()){
                resourceResolver.close();
            }
        }
    }

}
