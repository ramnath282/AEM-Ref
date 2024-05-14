package com.mattel.fisherprice.core.models;

import com.adobe.agl.impl.Assert;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.fisherprice.core.constants.ProductFeederConstants;
import com.mattel.fisherprice.core.services.GetResourceResolver;
import com.mattel.fisherprice.core.utils.JcrResourceRemove;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ JcrResourceRemove.class })
public class ProductPublishProcessStepTest {
  @Mock
  private GetResourceResolver getResourceResolver;
  @Mock
  private MetaDataMap metaDataMap;
  @InjectMocks
  private ProductPublishProcessStep productPublishProcessStep;
  @Mock
  private Replicator replicator;
  @Mock
  private WorkflowData workflowData;
  @Mock
  private WorkflowSession workflowSession;
  @Mock
  private WorkItem workItem;

  @Test
  public void testExecute() throws Exception {
    final ResourceResolver rr = Mockito.mock(ResourceResolver.class);
    final Session session = Mockito.mock(Session.class);
    final List<String> published = Arrays.asList("/var/products/fisher-price/en-us/product_FXC37",
        "/var/products/fisher-price/en-us/product_FXX47",
        "/var/products/fisher-price/en-us/product_FXT63",
        "/var/products/fisher-price/en-us/product_CGM35",
        "/var/products/fisher-price/en-us/product_FDV55",
        "/var/products/fisher-price/en-us/product_FXC06",
        "/var/products/fisher-price/en-us/product_GBN16",
        "/var/products/fisher-price/en-us/product_FYL50");
    final List<String> unpublished = Arrays.asList(
        "/var/commerce/products/fisher-price/en-us/product_GBN45",
        "/var/commerce/products/fisher-price/en-us/product_FYL45",
        "/var/commerce/products/fisher-price/en-us/product_GBG90",
        "/var/commerce/products/fisher-price/en-us/product_FYK55",
        "/var/commerce/products/fisher-price/en-us/product_FYK56",
        "/var/commerce/products/fisher-price/en-us/product_FYL46",
        "/var/commerce/products/fisher-price/en-us/product_GBJ55",
        "/var/commerce/products/fisher-price/en-us/product_GCJ29");
    final List<String> unpublishAndDeleteNodes = Arrays
        .asList("/var/commerce/products/fisher-price/en-us/product_CBV48");
    final Map<String, List<String>> nodeDeltas = new HashMap<>();
    final String deltaStr;

    nodeDeltas.put("publishNodes", published);
    nodeDeltas.put("unpublishNodes", unpublished);
    nodeDeltas.put("unpublishAndDeleteNodes", unpublishAndDeleteNodes);
    deltaStr = new ObjectMapper().writeValueAsString(nodeDeltas);
    Mockito.when(workItem.getWorkflowData()).thenReturn(workflowData);
    Mockito.when(workflowData.getMetaDataMap()).thenReturn(metaDataMap);
    Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(rr);
    Mockito.when(rr.adaptTo(Session.class)).thenReturn(session);
    Mockito.when(metaDataMap.get("nodeDelta")).thenReturn(deltaStr);
    Mockito.when(metaDataMap.get("nodeDelta", String.class)).thenReturn(deltaStr);
    published.stream().forEach(path -> {
      try {
        Mockito.doNothing().when(replicator).replicate(session, ReplicationActionType.ACTIVATE,
            path);
      } catch (final ReplicationException e) {
        Assert.fail(e);
      }
    });
    unpublished.stream().forEach(path -> {
      try {
        Mockito.doNothing().when(replicator).replicate(session, ReplicationActionType.DEACTIVATE,
            path);
      } catch (final ReplicationException e) {
        Assert.fail(e);
      }
    });
    unpublishAndDeleteNodes.stream().forEach(path -> {
      try {
        final Resource resource = Mockito.mock(Resource.class);
        final Node node = Mockito.mock(Node.class);

        Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
        Mockito.when(rr.getResource(path)).thenReturn(resource);
        PowerMockito.mockStatic(JcrResourceRemove.class);
        PowerMockito.when(JcrResourceRemove.removeNodeRecursively(node,
            ProductFeederConstants.PRODUCT_DELETE_BATCH_SIZE)).thenReturn(1);
        Mockito.doNothing().when(replicator).replicate(session, ReplicationActionType.DEACTIVATE,
            path);
      } catch (final ReplicationException | RepositoryException e) {
        Assert.fail(e);
      }
    });
    productPublishProcessStep.execute(workItem, workflowSession, metaDataMap);
  }
  
  
  @Test
  public void testExecute_1() throws Exception {
    final ResourceResolver rr = Mockito.mock(ResourceResolver.class);
    final Session session = Mockito.mock(Session.class);
    final List<String> published = Arrays.asList("/var/products/fisher-price/en-us/product_FXC37",
        "/var/products/fisher-price/en-us/product_FXX47",
        "/var/products/fisher-price/en-us/product_FXT63",
        "/var/products/fisher-price/en-us/product_CGM35",
        "/var/products/fisher-price/en-us/product_FDV55",
        "/var/products/fisher-price/en-us/product_FXC06",
        "/var/products/fisher-price/en-us/product_GBN16",
        "/var/products/fisher-price/en-us/product_FYL50");
    final List<String> unpublished = Arrays.asList(
        "/var/commerce/products/fisher-price/en-us/product_GBN45",
        "/var/commerce/products/fisher-price/en-us/product_FYL45",
        "/var/commerce/products/fisher-price/en-us/product_GBG90",
        "/var/commerce/products/fisher-price/en-us/product_FYK55",
        "/var/commerce/products/fisher-price/en-us/product_FYK56",
        "/var/commerce/products/fisher-price/en-us/product_FYL46",
        "/var/commerce/products/fisher-price/en-us/product_GBJ55",
        "/var/commerce/products/fisher-price/en-us/product_GCJ29");
    final List<String> unpublishAndDeleteNodes = Arrays
        .asList("/var/commerce/products/fisher-price/en-us/product_CBV48");
    final Map<String, List<String>> nodeDeltas = new HashMap<>();
    final String deltaStr;

    nodeDeltas.put("publishNodes", published);
    nodeDeltas.put("unpublishNodes", unpublished);
    nodeDeltas.put("unpublishAndDeleteNodes", unpublishAndDeleteNodes);
    deltaStr = new ObjectMapper().writeValueAsString(nodeDeltas);
    Mockito.when(workItem.getWorkflowData()).thenReturn(workflowData);
    Mockito.when(workflowData.getMetaDataMap()).thenReturn(metaDataMap);
    Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(rr);
    Mockito.when(rr.adaptTo(Session.class)).thenReturn(session);
    Mockito.when(metaDataMap.get("nodeDelta")).thenReturn(deltaStr);
    Mockito.when(metaDataMap.get("nodeDelta", String.class)).thenReturn(deltaStr);
    published.stream().forEach(path -> {
      try {
        Mockito.doThrow(ReplicationException.class).when(replicator).replicate(session, ReplicationActionType.ACTIVATE,
            path);
      } catch (final ReplicationException e) {
        Assert.fail(e);
      }
    });
    unpublished.stream().forEach(path -> {
      try {
        Mockito.doThrow(ReplicationException.class).when(replicator).replicate(session, ReplicationActionType.DEACTIVATE,
            path);
      } catch (final ReplicationException e) {
        Assert.fail(e);
      }
    });
    unpublishAndDeleteNodes.stream().forEach(path -> {
      try {
        final Resource resource = Mockito.mock(Resource.class);
        final Node node = Mockito.mock(Node.class);

        Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
        Mockito.when(rr.getResource(path)).thenReturn(resource);
        PowerMockito.mockStatic(JcrResourceRemove.class);
        PowerMockito.when(JcrResourceRemove.removeNodeRecursively(node,
            ProductFeederConstants.PRODUCT_DELETE_BATCH_SIZE)).thenReturn(1);
        Mockito.doThrow(ReplicationException.class).when(replicator).replicate(session, ReplicationActionType.DEACTIVATE,
            path);
      } catch (final ReplicationException | RepositoryException e) {
        Assert.fail(e);
      }
    });
    productPublishProcessStep.execute(workItem, workflowSession, metaDataMap);
  }
}
