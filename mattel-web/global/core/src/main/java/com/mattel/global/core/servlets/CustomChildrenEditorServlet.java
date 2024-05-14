package com.mattel.global.core.servlets;

import java.io.IOException;
import java.util.Objects;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CTS
 *
 */
@Component(service = Servlet.class, immediate = true, property = {
    Constants.SERVICE_DESCRIPTION
        + "=Custom Servlet for Child Node Ordering and deletion from multifields",
    "sling.servlet.resourceTypes=" + "sling/servlet/default",
    "sling.servlet.selectors=" + "container", "sling.servlet.extensions=" + "html",
    "sling.servlet.methods=" + HttpConstants.METHOD_POST })
public class CustomChildrenEditorServlet extends SlingAllMethodsServlet {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomChildrenEditorServlet.class);
  private static final String PARAM_DELETED_CHILDREN = "delete";
  private static final String PARAM_ORDERED_CHILDREN = "order";

  /**
   * doPost method call
   *
   */
  @Override
  protected void doPost(SlingHttpServletRequest request, final SlingHttpServletResponse response)
      throws ServletException, IOException {
    LOGGER.info("CustomChildrenEditorServlet  doPost Start :::::::");
    ResourceResolver resolver = request.getResourceResolver();
    Resource container = request.getResource();
    String[] deletedChildrenNames = request.getParameterValues(PARAM_DELETED_CHILDREN);
    String[] orderedChildrenNames = request.getParameterValues(PARAM_ORDERED_CHILDREN);
    if (ArrayUtils.isNotEmpty(deletedChildrenNames)) {
      updateChildrenDeletion(resolver, container, deletedChildrenNames, response);
    }
    if (ArrayUtils.isNotEmpty(orderedChildrenNames)) {
      updateChildOrder(resolver, container, orderedChildrenNames, response);
    }
    LOGGER.info("CustomChildrenEditorServlet  doPost End :::::::");
  }

  /**
   * updateChildOrder method call to update child node ordering based on
   * authoring changes
   * 
   * @param resolver
   * @param container
   * @param orderedChildrenNames
   * @param response
   *
   */
  private void updateChildOrder(ResourceResolver resolver, Resource container,
      String[] orderedChildrenNames, final SlingHttpServletResponse response) {
    try {

      LOGGER.debug("CustomChildrenEditorServlet  updateChildOrder orderedChildrenNames :-{}",
          orderedChildrenNames);
      final Node containerNode = container.adaptTo(Node.class);
      if (Objects.nonNull(containerNode)) {
        for (int i = 0; i < orderedChildrenNames.length; i++) {
          if (!containerNode.hasNode(orderedChildrenNames[i])) {
            containerNode.addNode(orderedChildrenNames[i]);
          }
        }
        for (int i = orderedChildrenNames.length - 1; i >= 0; i--) {
          if (i == orderedChildrenNames.length - 1) {
            containerNode.orderBefore(orderedChildrenNames[i], null);
          } else {
            containerNode.orderBefore(orderedChildrenNames[i], orderedChildrenNames[i + 1]);
          }
        }
        resolver.commit();
      }

    } catch (RepositoryException | PersistenceException e) {
      LOGGER.error(
          "CustomChildrenEditorServlet->updateChildOrder : Could not reorder the items of the container at {}: {}",
          container.getPath(), e);
      try {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      } catch (IOException e1) {
        LOGGER.error(
            "CustomChildrenEditorServlet->updateChildOrder :updateChildOrder Error at {}: {}",
            container.getPath(), e);
      }
    }
  }

  /**
   * updateChildrenDeletion method call to remove child node from page component
   * based on authoring changes
   * 
   * @param resolver
   * @param container
   * @param deletedChildrenNames
   * @param response
   *
   */
  private void updateChildrenDeletion(ResourceResolver resolver, Resource container,
      String[] deletedChildrenNames, final SlingHttpServletResponse response) {

    LOGGER.debug("CustomChildrenEditorServlet  updateChildrenDeletion deletedChildrenNames :-{}",
        deletedChildrenNames);
    for (String childName : deletedChildrenNames) {
      LOGGER.debug("CustomChildrenEditorServlet  updateChildrenDeletion childName :-{}", childName);
      Resource child = container.getChild(childName);
      if (Objects.nonNull(child)) {
        LOGGER.debug("CustomChildrenEditorServlet  updateChildrenDeletion child :-{}", child);
        try {
          resolver.delete(child);
          resolver.commit();
        } catch (PersistenceException e) {
          LOGGER.error(
              "CustomChildrenEditorServlet updateChildrenDeletion: Could not delete the items of the container at {}: {}",
              container.getPath(), e);
          try {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
          } catch (IOException e1) {
            LOGGER.error("CustomChildrenEditorServlet :updateChildrenDeletion Error at {}: {}",
                container.getPath(), e);
          }
        }
      }
    }
  }

}