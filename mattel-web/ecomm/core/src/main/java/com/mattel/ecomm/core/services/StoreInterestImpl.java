package com.mattel.ecomm.core.services;

import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;
import com.mattel.ecomm.core.interfaces.StoreInterest;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author CTS.
 * Service Implementation to get Store Interest.
 */
@Component(service = StoreInterest.class)
public class StoreInterestImpl implements StoreInterest {
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreInterestImpl.class);
    @Reference
    private ResourceResolverFactory resourceResolverFactory;
    private String storePageRootPath;

    @Override
    public JSONArray getStoreInterest() throws ServiceException {
        LOGGER.info("Start of get store interest");
        long startTime = System.currentTimeMillis();
        JSONArray jsonArray = new JSONArray();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
        try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(userMap)) {
            LOGGER.debug("Store Page Path from config is {}", storePageRootPath);
            Resource resource = resourceResolver.getResource(storePageRootPath);
            Page retailPage = null;
            Iterator<Page> childPages = null;
            if (null != resource) {
                retailPage = resource.adaptTo(Page.class);
            }
            if (null != retailPage) {
                childPages = retailPage.listChildren();
            }
            int i=0;
            if (null != childPages) {
                while (childPages.hasNext()) {
                    Page page = childPages.next();
                    if (StringUtils.equals(page.getProperties().get(NameConstants.NN_TEMPLATE, String.class),
                            Constant.RETAIL_STORE_PAGE_TEMPLATE_PATH)) {
                        LOGGER.debug("Page matched are {}", page.getTitle());
                        if (page.getProperties().containsKey("storeKey")) {
                            jsonArray.put(i, page.getProperties().get("storeKey", String.class) + ":" + page.getTitle() + ":" +page.getProperties().get("storeLocation", String.class));
                            i++;

                        }
                    }
                }
            }
            LOGGER.debug("JSON Array is {}", jsonArray);

        } catch (LoginException | JSONException e) {
            LOGGER.error("Login Exception occured {}", e);
            long endTime = System.currentTimeMillis();
            LOGGER.debug(Constant.EXECUTION_TIME_LOG , "get store interest method " , endTime-startTime);
            throw new ServiceException("1010","Generic Exception occured");
        }
        long endTime = System.currentTimeMillis();
        LOGGER.debug(Constant.EXECUTION_TIME_LOG , "get store interest method " , endTime-startTime);
        LOGGER.info("End of Get Store Interest Method");
        return jsonArray;
    }
    @ObjectClassDefinition(name = "Store Interest Service Configurations", description = "Store Interest Service Configurations")
    public @interface Config{
        @AttributeDefinition(name = "Store Page Root Path", description = "Give the root path under which all store pages are kept")
        String storePageRootPath() default "/content/ag/en/retail";
    }
    @Activate
    public void activate(Config config){
        storePageRootPath = config.storePageRootPath();
    }
}
