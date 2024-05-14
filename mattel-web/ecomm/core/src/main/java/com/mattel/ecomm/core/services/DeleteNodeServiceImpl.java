package com.mattel.ecomm.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.Filter;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.Page;
import com.mattel.ecomm.coreservices.core.constants.Constant;

/**
 * @author CTS
 *
 */
@Component(service = DeleteNodeServiceImpl.class)
@Designate(ocd = DeleteNodeServiceImpl.Config.class)
public class DeleteNodeServiceImpl {

  private static final Logger LOGGER = LoggerFactory.getLogger(DeleteNodeServiceImpl.class);

  private boolean enableDeletion = false;

  @Reference
  private GetResourceResolver getResourceResolver;

  @Reference
  private Replicator replicator;

  private static final String RESOURCE_TYPE_PROPERTY = "sling:resourceType";
  private static final String REPLICATION_ACTION_PROPERTY = "cq:lastReplicationAction";
  private static final String REPLICATION_ACTION_ACTIVATE = "Activate";
  private static final String PLP_RESOURCE_TYPE = "mattel/ecomm/ag/components/structure/ecomm-plp-page";
  private static final String BACK_TO_TOP_RESOURCETYPE = "mattel/ecomm/ag/components/content/backToTop";
  private static final String ECOMM_EXPFRG_RESOURCETYPE = "cq/experience-fragments/editor/components/experiencefragment";
  private static final String FRAGMENT_PATH_PROPERTY = "fragmentPath";
  private static final String FRAGMENT_PATH_PROPERTY_VALUE = Constant.CONTENT
      + "/experience-fragments/ag/shop/add-on-modal/master";
  private static final String ENABLE_DELETION_TRUE = "true";

  /**
   * this method will be called to start execution of the Node deletion which
   * are having specified props
   */
  public Map<String, Object> deleteNodes(String contentPath) {
    LOGGER.info("Method deleteNodes -> Start");
    Map<String, Object> responseMap = new HashMap<>();
    List<String> activePageList = new ArrayList<>();
    List<String> distinctPages = new ArrayList<>();
    List<String> allPageList = new ArrayList<>();
    
    if (isEnableDeletion()) {
      LOGGER.debug("Delete content flag is enabled");
      ResourceResolver resolver = getResourceResolver.getResourceResolver();
      try {
        Session session = null;
        if (Objects.nonNull(resolver)) {
          Resource resource = resolver.resolve(contentPath);
          Node resourceNode = resource.adaptTo(Node.class);
          session = getRepositorySession(resourceNode);
          Page plpRootPage = resource.adaptTo(Page.class);
          iteratePages(plpRootPage, activePageList, allPageList, resolver);
          if (session != null) {
            session.save();
          }
          distinctPages = activePageList.stream().distinct().collect(Collectors.toList());
          List<String> distinctAllPageList = allPageList.stream().distinct().collect(Collectors.toList());
          responseMap.put("Total Pages",distinctAllPageList.size());
          distinctAllPageList.removeAll(distinctPages);
          responseMap.put("Activated Pages", distinctPages.size());
          responseMap.put("Activated Page Paths", distinctPages);
          responseMap.put("Deactivated Page Paths",distinctAllPageList);
        }
        replicateUpdatedPages(distinctPages, session);
      } catch (Exception e) {
        LOGGER.error("Exception ", e);
      } finally {
        if (Objects.nonNull(resolver) && resolver.isLive()) {
          resolver.close();
        }
      }
    }
    LOGGER.info("Method deleteNodes -> End : {}", distinctPages);

    return responseMap;
  }

  /**
   * Method includes logic to get repository session
   * 
   * @param resourceNode
   * @return
   */
  private Session getRepositorySession(Node resourceNode) {
    return Optional.ofNullable(resourceNode).map(node -> {
      Session session = null;
      try {
        session = node.getSession();
      } catch (RepositoryException e) {
        LOGGER.error("Exception occured while getting repository session ", e);
      }
      return session;
    }).orElse(null);
  }

  /**
   * @param plpRootPage
   *          root pages
   * @param resolver
   *          resource resolver instance
   */
  private void iteratePages(Page plpRootPage, List<String> activePageList, List<String> allPageList,
      ResourceResolver resolver) {
    LOGGER.info("Method iteratePages -> Start");
    Filter<Page> plpResourceTypeFilter = page -> page.getProperties()
        .get(DeleteNodeServiceImpl.RESOURCE_TYPE_PROPERTY, "")
        .equals(DeleteNodeServiceImpl.PLP_RESOURCE_TYPE);

    Iterator<Page> plpRootPageItr = plpRootPage.listChildren(plpResourceTypeFilter, true);
    plpRootPageItr.forEachRemaining(childPage -> {
      if (updateChildNode(childPage, resolver)) {
        String replicationAction = childPage.getProperties()
            .get(DeleteNodeServiceImpl.REPLICATION_ACTION_PROPERTY, String.class);
        allPageList.add(childPage.getPath());
        if (StringUtils.isNotBlank(replicationAction)
            && REPLICATION_ACTION_ACTIVATE.equalsIgnoreCase(replicationAction)) {
          activePageList.add(childPage.getPath());
        }
      }
    });

    LOGGER.info("Method iteratePages -> End");
  }

  /**
   * method include logic to delete component node
   * 
   * @param childPage
   *          child page
   * @param resolver
   *          resource resolver instance
   * @return
   */
  private boolean updateChildNode(Page childPage, ResourceResolver resolver) {
    LOGGER.info("Method checkChildNodesOfPage -> Start");
    boolean isComponentRemoved = false;
    try {
      Resource childPageRootResource = resolver
          .resolve(childPage.getPath() + Constant.SLASH_JCR_CONTENT + "/root");
      Node childPageRootNode = childPageRootResource.adaptTo(Node.class);
      LOGGER.debug("Child page path : {} ", childPageRootNode);
      if (Objects.nonNull(childPageRootNode)) {
        NodeIterator childPageNodesItr = childPageRootNode.getNodes();
        while (childPageNodesItr.hasNext()) {
          Node componentNode = childPageNodesItr.nextNode();
          if (checkForComponentDeletion(componentNode)) {
            LOGGER.debug("Content node to be deleted : {}", componentNode.getPath());
            componentNode.remove();
            isComponentRemoved = true;
          }
        }
      }
    } catch (RepositoryException e) {
      LOGGER.error("Repository Exception ", e);
    }
    LOGGER.info("Method checkChildNodesOfPage -> End : Is Component Removed : {}",
        isComponentRemoved);

    return isComponentRemoved;
  }

  /**
   * check whether component deletion is required or not based on properties
   * 
   * @param componentNode
   * @return flag true if component node needs to be deleted
   */
  private boolean checkForComponentDeletion(Node componentNode) {
    LOGGER.info("Method checkForComponentDeletion -> Start");
    boolean deleteNodeFlag = false;
    try {
      String componentNodeResourceType = componentNode
          .hasProperty(DeleteNodeServiceImpl.RESOURCE_TYPE_PROPERTY)
              ? componentNode.getProperty(DeleteNodeServiceImpl.RESOURCE_TYPE_PROPERTY).getValue()
                  .getString()
              : "";

      if (componentNodeResourceType
          .equalsIgnoreCase(DeleteNodeServiceImpl.BACK_TO_TOP_RESOURCETYPE)) {
        deleteNodeFlag = true;
        LOGGER.debug("Back to Top resource present, Deletion flag: {}", deleteNodeFlag);
      } else if (componentNodeResourceType
          .equalsIgnoreCase(DeleteNodeServiceImpl.ECOMM_EXPFRG_RESOURCETYPE)) {
        String fragmentPath = componentNode
            .hasProperty(DeleteNodeServiceImpl.FRAGMENT_PATH_PROPERTY)
                ? componentNode.getProperty(DeleteNodeServiceImpl.FRAGMENT_PATH_PROPERTY).getValue()
                    .getString()
                : "";
        deleteNodeFlag = fragmentPath
            .equalsIgnoreCase(DeleteNodeServiceImpl.FRAGMENT_PATH_PROPERTY_VALUE) ? true : false;
        LOGGER.debug("Experice fragment with exp fragment present, Deletion flag: {}",
            deleteNodeFlag);
      }
    } catch (IllegalStateException | RepositoryException e) {
      LOGGER.error("Exception ", e);
    }
    LOGGER.info("Method checkForComponentDeletion -> End, {}", deleteNodeFlag);

    return deleteNodeFlag;
  }

  /**
   * replicate all paths
   * 
   * @param distintPagePaths
   *          list of paths to replicate
   * @param session
   *          jcr session instance
   */
  private void replicateUpdatedPages(List<String> distintPagePaths, Session session) {
    LOGGER.info("Method replicateUpdatedPages -> Start, Number of Pages to replicate : {}",
        distintPagePaths.size());
    try {
      replicator.replicate(session, ReplicationActionType.ACTIVATE,
          distintPagePaths.stream().toArray(String[]::new), null);
    } catch (ReplicationException e) {
      LOGGER.error("ReplicationException Exception ", e);
    }
    LOGGER.info("Method replicateUpdatedPages -> End");

  }

  public boolean isEnableDeletion() {
    return enableDeletion;
  }

  @ObjectClassDefinition(name = "Delete Content Node Service Configurations", description = "Delete Content Node Service Configurations")
  public @interface Config {
    @AttributeDefinition(name = "Enable Deletion Functioanlity", description = "If you want to enable Delete Node Service functionality set this value to true")
    String enableDeletion() default "true";
  }

  @Activate
  public void activate(Config config) {
    if (StringUtils.isNotBlank(config.enableDeletion())) {
      enableDeletion = config.enableDeletion()
          .equalsIgnoreCase(DeleteNodeServiceImpl.ENABLE_DELETION_TRUE) ? true : false;
    }
  }

}
