package com.mattel.fisherprice.core.models;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.fisherprice.core.constants.ProductFeederConstants;
import com.mattel.fisherprice.core.services.GetResourceResolver;
import com.mattel.fisherprice.core.utils.JcrResourceRemove;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = WorkflowProcess.class, property = {
    "process.label=Product Publish Custom Process Step" })
public class ProductPublishProcessStep implements WorkflowProcess {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductPublishProcessStep.class);
  private static final String REP_ERROR = "Unable to replicate product nodes"
      + ", recieved from product feed";
  @Reference
  private GetResourceResolver getResourceResolver;
  private final ObjectMapper mapper = new ObjectMapper();
  @Reference
  private Replicator replicator;

  /**
   * To Activate node given at specified path in publisher.
   *
   * @param session
   *          The {@link Session} to do replication.
   * @param resourcePath
   *          The path of resource to be replicated.
   */
  private void activateNodeOnPublisher(Session session, final String resourcePath) {
    try {
      replicator.replicate(session, ReplicationActionType.ACTIVATE, resourcePath);
    } catch (final ReplicationException re) {
      ProductPublishProcessStep.LOGGER
          .error(String.format("Unable to activate node on publisher: %s", resourcePath), re);
    }
  }

  /**
   * To Deactivate node given at specified path in publisher. Once the node is deactivate at
   * publisher end, delete the node from author.
   *
   * @param resourceResolver
   *          The {@link ResourceResolver} instance to verify node.
   * @param session
   *          The {@link Session} to do replication.
   * @param resourcePath
   *          The path of resource to be replicated.
   */
  private void deactivateNodeOnPubAndDelOnAuth(final ResourceResolver resourceResolver,
      Session session, final String resourcePath) {
    try {
      replicator.replicate(session, ReplicationActionType.DEACTIVATE, resourcePath);
      removeNode(resourcePath, resourceResolver);
    } catch (final ReplicationException re) {
      ProductPublishProcessStep.LOGGER
          .error(String.format("Unable to deactivate node on publisher: %s", resourcePath), re);
    }
  }

  /**
   * To Deactivate node given at specified path in publisher.
   *
   * @param session
   *          The {@link Session} to do replication.
   * @param resourcePath
   *          The path of resource to be replicated.
   */
  private void deactivateNodeOnPublisher(Session session, final String resourcePath) {
    try {
      replicator.replicate(session, ReplicationActionType.DEACTIVATE, resourcePath);
    } catch (final ReplicationException re) {
      ProductPublishProcessStep.LOGGER
          .error(String.format("Unable to deactivate node on publisher: %s", resourcePath), re);
    }
  }

  @Override
  public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
      throws WorkflowException {
    final WorkflowData workflowData = workItem.getWorkflowData();
    final MetaDataMap workflowMetaDataMap = workflowData.getMetaDataMap();
    final ResourceResolver resourceResolver = getResourceResolver.getResourceResolver();
    Map<String, List<String>> nodeDeltas = new HashMap<>();
    List<String> publishNodes;
    List<String> unpublishNodes;
    List<String> unpublishAndDeleteNodes;
    Session session = null;

    try {
      if (Objects.nonNull(
          workflowMetaDataMap.get(ProductFeederConstants.WORKFLOW_NODE_REPLICATION_DATA))) {
        final TypeReference<Map<String, List<String>>> type = //
            new TypeReference<Map<String, List<String>>>() {
            };
        nodeDeltas = mapper.readValue(workflowMetaDataMap
            .get(ProductFeederConstants.WORKFLOW_NODE_REPLICATION_DATA, String.class), type);
        session = resourceResolver.adaptTo(Session.class);

        publishNodes = nodeDeltas.getOrDefault(ProductFeederConstants.NODES_TO_PUBLISH,
            new ArrayList<>());
        unpublishNodes = nodeDeltas.getOrDefault(ProductFeederConstants.NODES_TO_UNPUBLISH,
            new ArrayList<>());
        unpublishAndDeleteNodes = nodeDeltas.getOrDefault(
            ProductFeederConstants.NODES_TO_UNPUBLISH_AND_DELETE_FROM_AUTHOR, new ArrayList<>());

        ProductPublishProcessStep.LOGGER.info("Fisher price product feed replication start");
        ProductPublishProcessStep.LOGGER.debug(
            "Activating following nodes(count: {}) in publisher: {}", publishNodes.size(),
            publishNodes);

        for (final String resourcePath : publishNodes) {
          activateNodeOnPublisher(session, resourcePath);
        }

        if (!unpublishNodes.isEmpty()) {
          ProductPublishProcessStep.LOGGER.debug(
              "Deactivating following nodes(count: {}) in publisher: {}", unpublishNodes.size(),
              unpublishNodes);
        }

        for (final String resourcePath : unpublishNodes) {
          deactivateNodeOnPublisher(session, resourcePath);
        }

        if (!unpublishAndDeleteNodes.isEmpty()) {
          ProductPublishProcessStep.LOGGER.debug(
              "Deactivating nodes(count: {}) in publisher and deleting them from author: {}",
              unpublishAndDeleteNodes.size(), unpublishAndDeleteNodes);
        }

        for (final String resourcePath : unpublishAndDeleteNodes) {
          deactivateNodeOnPubAndDelOnAuth(resourceResolver, session, resourcePath);
        }
        
        ProductPublishProcessStep.LOGGER.info("Fisher price product feed replication end");
      } else {
        ProductPublishProcessStep.LOGGER.error(
            "No product changes to be replicated at publisher end, invalid workflow initiation");
      }
    } catch (final IOException e) {
      ProductPublishProcessStep.LOGGER.error(ProductPublishProcessStep.REP_ERROR, e);
    } finally {
      Optional.ofNullable(session).ifPresent(s -> {
        if (s.isLive()) {
          s.logout();
        }
      });
      Optional.ofNullable(resourceResolver).ifPresent(ResourceResolver::close);
    }
  }

  /**
   * To remove {@link Node node} and node children for the specified path.
   *
   * @param path
   *          The path to the node.
   * @param resourceResolver
   *          The {@link ResourceResolver} instance to verify if the node is present.
   */
  private void removeNode(String path, ResourceResolver resourceResolver) {

    Resource res = resourceResolver.getResource(path);
    if (Objects.nonNull(res)) {
      try {
        final int noOfNodesDeleted = JcrResourceRemove.removeNodeRecursively(
            res.adaptTo(Node.class), ProductFeederConstants.PRODUCT_DELETE_BATCH_SIZE);

        ProductPublishProcessStep.LOGGER.trace("Numbers of nodes deleted inside path ({}) : {}",
            noOfNodesDeleted, path);
      } catch (final RepositoryException re) {
        ProductPublishProcessStep.LOGGER
            .error(String.format("Unable to remove product node from author: %s", path), re);
      }
    } else {
      ProductPublishProcessStep.LOGGER.debug("Node at {} has already been removed", path);
    }
  }
}
