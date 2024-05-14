package com.mattel.ecomm.core.importer.workflow.services;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.day.cq.commons.jcr.JcrUtil;
import com.day.crx.JcrConstants;
import com.mattel.ecomm.core.importer.workflow.interfaces.ProductSaveAndUpdateService;
import com.mattel.ecomm.core.pojos.ProductJsonNode;

@Component(service = ProductSaveAndUpdateService.class)
@Designate(ocd = ProductSaveAndUpdateServiceImpl.Config.class)
public class ProductSaveAndUpdateServiceImpl implements ProductSaveAndUpdateService {
    private static final String PRODUCT_NODE_TYPE = JcrConstants.NT_UNSTRUCTURED;
    private String parentNodePath;

    @Override
    public Node save(ProductJsonNode productJsonNode, Session session, ResourceResolver resourceResolver) throws RepositoryException {
        final Set<Map.Entry<String, String>> properties = productJsonNode.getProperties().entrySet();
        final String absolutePath = parentNodePath + productJsonNode.getPartNumber();
        final Resource resource = resourceResolver.getResource(absolutePath);

        if (Objects.nonNull(resource)) {
            final Node existingProductNode = resource.adaptTo(Node.class);

            if (Objects.nonNull(existingProductNode)) {
                for (final Map.Entry<String, String> property : properties) {
                    existingProductNode.setProperty(property.getKey(), property.getValue());
                }
            } else {
                throw new RepositoryException(String.format("Unable to retrive existing Product node: %s for updation", absolutePath));
            }

            return existingProductNode;
        } else {
            final Node newProductNode = JcrUtil.createPath(absolutePath,
                    ProductSaveAndUpdateServiceImpl.PRODUCT_NODE_TYPE, session);

            for (final Map.Entry<String, String> property : properties) {
                newProductNode.setProperty(property.getKey(), property.getValue());
            }

            return newProductNode;
        }
    }

    @ObjectClassDefinition(name = "Product save and update service", description = "This service saves a product node in repository")
    public @interface Config {
        @AttributeDefinition(name = "Path of parent sling folder", description = "Path of sling folder in repository inside which nodes are saved")
        String parentNodePath() default "/var/commerce/products/ag/en/";
    }

    @Activate
    public void activate(Config config) {
        parentNodePath = config.parentNodePath();
    }
}
