package com.mattel.ecomm.core.services;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.eval.JcrPropertyPredicateEvaluator;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.interfaces.GetProductTypeService;
import com.mattel.ecomm.core.utils.GiftCardSkuProcessor;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component(service = GetProductTypeService.class)
public class GetProductType implements GetProductTypeService {
    @Reference
    private GetResourceResolver getResourceResolver;
    @Reference
    private PropertyReaderService propertyReaderService;
    @Reference
    private QueryBuilder queryBuilder;
    private static final Logger LOGGER = LoggerFactory.getLogger(GetProductType.class);
    private static final String PRODUCT_TYPE = "product_type";
    private static final String PART_NUMBER = "partNumber";
    private static final String QUERY_LIMIT = "-1";

    public Map<String, String> getProductType(String skewId, String clientIdentifier) {
        ResourceResolver resourceResolver = getResourceResolver.getResourceResolver();
        String productType = null;
        try {
            String productPagePath = propertyReaderService.getProductPath(clientIdentifier);
            LOGGER.debug("Product page path is {}", productPagePath);
            Resource resource = resourceResolver.getResource(productPagePath+"/"+skewId);
            Map<String, String> propertiesMap = new HashMap<>();
            if (null != resource) {
                productType = resource.getValueMap().get(PRODUCT_TYPE, String.class);
                LOGGER.debug("product type is {}", productType);
                String partNumber = resource.getValueMap().get(PART_NUMBER, String.class);
                partNumber = GiftCardSkuProcessor.checkAndEscapeDelimiter(partNumber);
                String seoPageTitle = resource.getValueMap().get(Constant.SEO_PAGE_TITLE,String.class);
                String seoMetaDescription = resource.getValueMap().get(Constant.SEO_META_DESCRIPTION,String.class);
                String seoUrlKeyword = resource.getValueMap().get(Constant.SEO_URL_KEYWORD,String.class);
                String nonIndexTag = resource.getValueMap().get(Constant.SEO_NON_INDEX_TAG,String.class);
                String canonicalTag = resource.getValueMap().get(Constant.SEO_CANONICAL_TAGS,String.class);
                String active = resource.getValueMap().get(Constant.ACTIVE,String.class);
                propertiesMap.put(PRODUCT_TYPE,productType);
                propertiesMap.put(PART_NUMBER,partNumber);
                propertiesMap.put(Constant.SEO_PAGE_TITLE,seoPageTitle);
                propertiesMap.put(Constant.SEO_META_DESCRIPTION,seoMetaDescription);
                propertiesMap.put(Constant.SEO_URL_KEYWORD,seoUrlKeyword);
                propertiesMap.put(Constant.SEO_NON_INDEX_TAG,nonIndexTag);
                propertiesMap.put(Constant.SEO_CANONICAL_TAGS,canonicalTag);
                propertiesMap.put(Constant.BREADCRUMB_CANONICAL_TAG,
                    resource.getValueMap().get(Constant.BREADCRUMB_CANONICAL_TAG, String.class));
                propertiesMap.put(Constant.PRODUCT_NAME_TAG,
                    resource.getValueMap().get(Constant.PRODUCT_NAME_TAG, String.class));
                propertiesMap.put(Constant.ACTIVE, active);
            }
            LOGGER.debug("Properties map of product is {}", propertiesMap);
            return propertiesMap;
        } finally {
            if (resourceResolver.isLive()){
                resourceResolver.close();
            }
        }
    }
    @Override
    public Set<String> getProductTypeDatasource(String siteKey) {
        ResourceResolver resourceResolver = getResourceResolver.getResourceResolver();
        Set<String> productTypes = new HashSet<>();
        Session session = null;

        try {
            session = resourceResolver.adaptTo(Session.class);
            final Map<String, Object> querryMap = new HashMap<>();
            String productPagePath = propertyReaderService.getProductPath(siteKey);
            querryMap.put("path", productPagePath);
            querryMap.put("type", JcrConstants.NT_UNSTRUCTURED);
            querryMap.put("property", PRODUCT_TYPE);
            querryMap.put("p.limit", QUERY_LIMIT);
            querryMap.put("property.operation", JcrPropertyPredicateEvaluator.OP_EXISTS);
            LOGGER.debug("Query map is {}", querryMap);
            Query query = queryBuilder.createQuery(PredicateGroup.create(querryMap), session);
            SearchResult searchResult = query.getResult();
            for (Hit hit : searchResult.getHits()) {
                productTypes.add(hit.getProperties().get(PRODUCT_TYPE, String.class));
            }
            EcommHelper.getLeakingResResolver(searchResult);
            LOGGER.debug("Set of product Types are {}", productTypes);
            return productTypes;
        } catch (RepositoryException e) {
            LOGGER.error("Repository Exception Occured {}",e);
        } finally {
            if (Objects.nonNull(session) && session.isLive()) {
              session.logout();
            }

            if (resourceResolver.isLive()){
                resourceResolver.close();
            }
        }
        return productTypes;
    }


}
