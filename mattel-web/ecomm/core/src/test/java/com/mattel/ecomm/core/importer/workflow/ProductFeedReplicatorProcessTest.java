package com.mattel.ecomm.core.importer.workflow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.ecomm.core.importer.workflow.interfaces.ProductFeedInboxNotificationService;
import com.mattel.ecomm.core.services.GetResourceResolver;

@RunWith(MockitoJUnitRunner.class)
public class ProductFeedReplicatorProcessTest {

  @InjectMocks
  ProductFeedReplicatorProcess productFeedReplicatorProcess;
  @Mock
  WorkItem workItem;
  @Mock
  WorkflowSession workflowSession;
  @Mock
  MetaDataMap metaDataMap;
  @Mock
  WorkflowData workflowData;
  @Mock
  GetResourceResolver getResourceResolver;
  @Mock
  ResourceResolver resourceResolver;
  @Mock
  Session session;
  @Mock
  ProductFeedInboxNotificationService productFeedInboxNotificationService;
  @Mock
  ObjectMapper mapper;
  @Mock
  TypeReference<List<String>> type;

  @Test
  public void testExecutewithNullObject() throws WorkflowException {
    Mockito.when(workItem.getWorkflowData()).thenReturn(workflowData);
    Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(workflowData.getMetaDataMap()).thenReturn(metaDataMap);
    productFeedReplicatorProcess.execute(workItem, workflowSession, metaDataMap);
  }

  @Test
  public void testExecutewithObject()
      throws WorkflowException, JsonParseException, JsonMappingException, IOException {
    Mockito.when(workItem.getWorkflowData()).thenReturn(workflowData);
    Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(workflowData.getMetaDataMap()).thenReturn(metaDataMap);
    final List<String> resourcePaths = new ArrayList<>();
    resourcePaths.add("/content/ag/ecomm/home.html");
    resourcePaths.add("/content/ag/ecomm/dolls.html");
    resourcePaths.add("/content/ag/ecomm/dolls.html");
    Mockito.when(metaDataMap.get(ProductFeedConstants.METADATA_KEY_NODE_PATHS_TO_REPLICATE))
        .thenReturn("resourcePaths");
    Mockito.when(
        metaDataMap.get(ProductFeedConstants.METADATA_KEY_NODE_PATHS_TO_REPLICATE, String.class))
        .thenReturn("{ \"type\" : \"doll\", \"store\" : \"AG\" }");
    productFeedReplicatorProcess.execute(workItem, workflowSession, metaDataMap);

  }

}
