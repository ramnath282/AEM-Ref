package com.mattel.ecomm.core.models;

import com.mattel.ecomm.core.pojos.ProductTypeTemplateConfigPojo;
import com.mattel.ecomm.core.services.MultifieldReader;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductTypeTemplateModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductTypeTemplateModel.class);
    @Inject
    private Node productTypeMapping;
    @Inject
    private MultifieldReader multifieldReader;
    private List<ProductTypeTemplateConfigPojo> pagePathMappingList;

    @PostConstruct
    protected void init(){
        LOGGER.info("Start of Init method for product template Mapping Class");
        Map<String, ValueMap> stringValueMapMap = multifieldReader.propertyReader(productTypeMapping);
        pagePathMappingList = new ArrayList<>();
        for (Map.Entry<String,ValueMap> entry: stringValueMapMap.entrySet()) {
            ProductTypeTemplateConfigPojo templateConfigPojo = new ProductTypeTemplateConfigPojo();
            templateConfigPojo.setProductType(entry.getValue().get("productType", String.class));
            templateConfigPojo.setPagePath(entry.getValue().get("pageUrl", String.class));
            LOGGER.debug("Template Config Pojo is {}", templateConfigPojo);
            pagePathMappingList.add(templateConfigPojo);
        }
        LOGGER.debug("Final List Returned is {}", pagePathMappingList);

        LOGGER.info("End of Init method for product template Mapping Class");
    }

    public List<ProductTypeTemplateConfigPojo> getPagePathMappingList() {
        return pagePathMappingList;
    }
}
