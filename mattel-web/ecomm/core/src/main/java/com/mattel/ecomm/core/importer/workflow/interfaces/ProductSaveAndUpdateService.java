package com.mattel.ecomm.core.importer.workflow.interfaces;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;

import com.mattel.ecomm.core.pojos.ProductJsonNode;

public interface ProductSaveAndUpdateService {
    Node save(ProductJsonNode productJsonNode, Session session, ResourceResolver resourceResolver) throws RepositoryException ;
}
