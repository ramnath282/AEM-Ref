package com.mattel.ecomm.core.importer.workflow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.InboxItem.Priority;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.adobe.granite.workflow.model.WorkflowNode;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.ecomm.core.importer.workflow.interfaces.ProductFeedInboxNotificationService;
import com.mattel.ecomm.core.services.GetResourceResolver;

@Component(service = WorkflowProcess.class, property = {
    "process.label=Product Feeder Replicator Step" })
public class ProductFeedReplicatorProcess implements WorkflowProcess {
  private static final String UNABLE_TO_REPLICATE_PRODUCT_NODES = "Unable to replicate product nodes, recived from product feed";
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductFeedReplicatorProcess.class);
  private final ObjectMapper mapper = new ObjectMapper();
  @Reference
  private ProductFeedInboxNotificationService productFeedInboxNotificationService;
  @Reference
  private GetResourceResolver getResourceResolver;
  @Reference
  private Replicator replicator;

  @Override
  public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
      throws WorkflowException {
    final WorkflowData workflowData = workItem.getWorkflowData();
    final MetaDataMap workflowMetaDataMap = workflowData.getMetaDataMap();
    final ResourceResolver resourceResolver = getResourceResolver.getResourceResolver();
    List<String> resourcePaths = new ArrayList<>();
    Session session = null;

    try {
      if (Objects.nonNull(
          workflowMetaDataMap.get(ProductFeedConstants.METADATA_KEY_NODE_PATHS_TO_REPLICATE))) {
        final TypeReference<List<String>> type = new TypeReference<List<String>>() {
        };
        resourcePaths = mapper.readValue(workflowMetaDataMap
            .get(ProductFeedConstants.METADATA_KEY_NODE_PATHS_TO_REPLICATE, String.class), type);
        session = resourceResolver.adaptTo(Session.class);

        for (final String resourcePath : resourcePaths) {
          replicator.replicate(session, ReplicationActionType.ACTIVATE, resourcePath);
        }
      } else {
        ProductFeedReplicatorProcess.LOGGER.error("Unable to determine workflow status");
        productFeedInboxNotificationService.createNotificationTask(
            ProductFeedReplicatorProcess.UNABLE_TO_REPLICATE_PRODUCT_NODES,
            String.join(",", resourcePaths), null,
            ProductFeedReplicatorProcess.UNABLE_TO_REPLICATE_PRODUCT_NODES, "", Priority.MEDIUM,
            resourceResolver);
      }
    } catch (final ReplicationException | IOException e) {
      ProductFeedReplicatorProcess.LOGGER
          .error(ProductFeedReplicatorProcess.UNABLE_TO_REPLICATE_PRODUCT_NODES, e);
      productFeedInboxNotificationService.createNotificationTask(
          ProductFeedReplicatorProcess.UNABLE_TO_REPLICATE_PRODUCT_NODES,
          String.join(",", resourcePaths), null,
          ProductFeedReplicatorProcess.UNABLE_TO_REPLICATE_PRODUCT_NODES, "", Priority.MEDIUM,
          resourceResolver);
    } finally {

      Optional.ofNullable(session).ifPresent(s -> {
        if (s.isLive()) {
          s.logout();
        }
      });
      Optional.ofNullable(resourceResolver).ifPresent(ResourceResolver::close);
    }
  }

  @SuppressWarnings("unused")
  private void updateWorkFlowDescription(WorkItem workItem) {
    final List<WorkflowNode> nodes = workItem.getWorkflow().getWorkflowModel().getNodes();

    for (final WorkflowNode workflowItem : nodes) {
      if ("New Product nodes imported".equals(workflowItem.getTitle())) {
        workflowItem.setDescription("New Product nodes imported. Kindly approve.");
      }
    }
  }
}
