package com.mattel.global.master.core.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.wcm.core.components.commons.editor.dialog.childreneditor.Item;
import com.day.cq.wcm.api.components.Component;
import com.day.cq.wcm.api.components.ComponentManager;

@Model(adaptables = { SlingHttpServletRequest.class })
public class CustomEditor {
  private static final Logger LOGGER = LoggerFactory.getLogger(CustomEditor.class);
  private static final String CONTENT = "content_";
  private static final String CTA = "cta_";
  @Self
  private SlingHttpServletRequest request;

  private Resource container;

  private List<Item> contentItems;

  private List<Item> ctaItems;

  /**
   * post construct method to initialize model object
   *
   */
  @PostConstruct
  protected void init() {

    readChildren();

  }

  /**
   * processChildren method to process node details
   *
   */
  private void processChildren(Iterator<Resource> itr, ComponentManager componentManager) {
    LOGGER.info("CustomEditor :processChildren Start ");
    while (itr.hasNext()) {
      Resource res = itr.next();
      if (Objects.nonNull(res)) {
        LOGGER.debug("resource  : {}", res);
        LOGGER.debug("resource name  : {}", res.getName());
        LOGGER.debug("resource path  : {}", res.getPath());
        Component component = componentManager.getComponentOfResource(res);
        if (Objects.nonNull(component)) {
          if (StringUtils.contains(res.getName(), CONTENT)) {
            LOGGER.debug("Content component  node details: {}", component);
            contentItems.add(new Item(request, res));
          } else if (StringUtils.contains(res.getName(), CTA)) {
            LOGGER.debug("CTA component node details  : {}", component);
            ctaItems.add(new Item(request, res));
          }
        }
      }
    }
    LOGGER.info("CustomEditor :processChildren End ");
  }

  private void readChildren() {
    LOGGER.info("CustomEditor :readChildren Start ");
    contentItems = new ArrayList<>();
    ctaItems = new ArrayList<>();
    String containerPath = request.getRequestPathInfo().getSuffix();
    LOGGER.debug("container Path : {}", containerPath);
    if (StringUtils.isNotEmpty(containerPath)) {
      ResourceResolver resolver = request.getResourceResolver();
      container = resolver.getResource(containerPath);
      if (Objects.nonNull(container)) {
        ComponentManager componentManager = request.getResourceResolver()
            .adaptTo(ComponentManager.class);
        if (Objects.nonNull(componentManager)) {
          Iterator<Resource> itr = container.getChildren().iterator();
          processChildren(itr, componentManager);
        }

      }
    }
    LOGGER.info("CustomEditor :readChildren End ");

  }

  /**
   * Retrieves the child items associated with this children editor.
   *
   * @return a list of child items
   */
  public List<Item> getContentItems() {
    return Collections.unmodifiableList(contentItems);
  }

  /**
   * Retrieves the child items associated with this children editor.
   *
   * @return a list of child items
   */
  public List<Item> getCtaItems() {
    return Collections.unmodifiableList(ctaItems);
  }

  /**
   * Retrieves the container resource associated with this children editor.
   *
   * @return the container resource, or {@code null} if no container can be
   *         found
   */
  public Resource getContainer() {
    return container;
  }
}