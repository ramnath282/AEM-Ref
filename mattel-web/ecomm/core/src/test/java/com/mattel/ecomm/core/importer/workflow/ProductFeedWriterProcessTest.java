package com.mattel.ecomm.core.importer.workflow;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.mattel.ecomm.core.importer.workflow.interfaces.ProductFeedInboxNotificationService;
import com.mattel.ecomm.core.importer.workflow.interfaces.ProductImportValidatorService;
import com.mattel.ecomm.core.importer.workflow.interfaces.ProductSaveAndUpdateService;
import com.mattel.ecomm.core.pojos.ProductJsonNode;
import com.mattel.ecomm.core.services.GetResourceResolver;
import com.mattel.ecomm.coreservices.core.pojos.ValidationResult;

@RunWith(MockitoJUnitRunner.class)
public class ProductFeedWriterProcessTest {

  @Mock
  WorkItem workItem;
  @Mock
  WorkflowSession workflowSession;
  @Mock
  MetaDataMap metaDataMap;
  @InjectMocks
  ProductFeedWriterProcess productFeedWriterProcess;
  @Mock
  ResourceResolver resourceResolver;
  @Mock
  Resource resource;
  @Mock
  Asset asset;
  @Mock
  ProductImportValidatorService productImportValidatorService;
  @Mock
  ProductSaveAndUpdateService productSaveAndUpdateService;
  @Mock
  ProductFeedInboxNotificationService productFeedInboxNotificationService;
  @Mock
  GetResourceResolver getResourceResolver;
  @Mock
  WorkflowData workflowData;
  @Mock
  Object object;
  @Mock
  Rendition rendition;
  @Mock
  ValidationResult validationResult;
  @Mock
  Session session;
  @Mock
  Node node;
  @Mock
  ProductJsonNode productJsonNode;

  @Test
  public void testExecutewithnullResource() throws WorkflowException {
    Mockito.when(workItem.getWorkflowData()).thenReturn(workflowData);
    Mockito.when(workflowData.getPayload()).thenReturn(object);
    Mockito.when(object.toString()).thenReturn("/content/ag/home.html");
    Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(workflowData.getMetaDataMap()).thenReturn(metaDataMap);
    productFeedWriterProcess.execute(workItem, workflowSession, metaDataMap);
  }

  @Test
  public void testExecutewithnullObject() throws WorkflowException {
    Mockito.when(workItem.getWorkflowData()).thenReturn(workflowData);
    Mockito.when(workflowData.getPayload()).thenReturn(object);
    Mockito.when(object.toString()).thenReturn("/content/ag/home.html");
    Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(resourceResolver.getResource(ArgumentMatchers.any(String.class)))
        .thenReturn(resource);
    Mockito.when(workflowData.getMetaDataMap()).thenReturn(metaDataMap);
    productFeedWriterProcess.execute(workItem, workflowSession, metaDataMap);
  }

  @Test
  public void testExecutewithNullJsonObject()
      throws WorkflowException, JsonProcessingException, IOException {
    Mockito.when(workItem.getWorkflowData()).thenReturn(workflowData);
    Mockito.when(workflowData.getPayload()).thenReturn(object);
    Mockito.when(object.toString()).thenReturn("/content/ag/home.html");
    Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(resourceResolver.getResource(ArgumentMatchers.any(String.class)))
        .thenReturn(resource);
    Mockito.when(resource.getName()).thenReturn("productdata_20201228033009.json");
    Mockito.when(resource.adaptTo(Asset.class)).thenReturn(asset);
    Mockito.when(workflowData.getMetaDataMap()).thenReturn(metaDataMap);
    Mockito.when(asset.getOriginal()).thenReturn(rendition);
    final String jsonText = "{\"title\":\"Home\",\"response\": {\"partNumber\": {\"isMandatory\": \"true\",\"regex\": \"^[^/]+\"},\"pdpLink\": {\"isMandatory\": \"true\"} } }";
    final InputStream inputstream = new ByteArrayInputStream(jsonText.getBytes());
    Mockito.when(rendition.getStream()).thenReturn(inputstream);
    productFeedWriterProcess.execute(workItem, workflowSession, metaDataMap);
  }

  @Test
  public void testExecutewithNullFailedNodes_WorkflowException()
      throws WorkflowException, JsonProcessingException, IOException {
    Mockito.when(workItem.getWorkflowData()).thenReturn(workflowData);
    Mockito.when(workflowData.getPayload()).thenReturn(object);
    Mockito.when(object.toString()).thenReturn("/content/ag/home.html");
    Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(resourceResolver.getResource(ArgumentMatchers.any(String.class)))
        .thenReturn(resource);
    Mockito.when(resource.getName()).thenReturn("productdata_20201228033009.json");
    Mockito.when(resource.adaptTo(Asset.class)).thenReturn(asset);
    Mockito.when(workflowData.getMetaDataMap()).thenReturn(metaDataMap);
    Mockito.when(asset.getOriginal()).thenReturn(rendition);
    final String jsonText = "{\"title\":\"Home\"}";
    final InputStream inputstream = new ByteArrayInputStream(jsonText.getBytes());
    Mockito.when(rendition.getStream()).thenReturn(inputstream);
    final ArrayList<String> validate = new ArrayList<>();
    validate.add("response");
    productFeedWriterProcess.execute(workItem, workflowSession, metaDataMap);
  }

  @Test
  public void testExecutewithException()
      throws WorkflowException, JsonProcessingException, IOException {
    Mockito.when(workItem.getWorkflowData()).thenReturn(workflowData);
    Mockito.when(workflowData.getPayload()).thenReturn(object);
    Mockito.when(object.toString()).thenReturn("/content/ag/home.html");
    Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(resourceResolver.getResource(ArgumentMatchers.any(String.class)))
        .thenReturn(resource);
    Mockito.when(resource.getName()).thenReturn("productdata_20201228033009.json");
    Mockito.when(resource.adaptTo(Asset.class)).thenReturn(asset);
    Mockito.when(workflowData.getMetaDataMap()).thenReturn(metaDataMap);
    Mockito.when(asset.getOriginal()).thenReturn(rendition);
    Mockito.when(rendition.getStream()).thenThrow(Exception.class);
    productFeedWriterProcess.execute(workItem, workflowSession, metaDataMap);
  }

  @Test
  public void testExecutewithFailedNodes() throws WorkflowException, JsonProcessingException,
      IOException, RepositoryException, IllegalArgumentException, IllegalAccessException {
    Mockito.when(workItem.getWorkflowData()).thenReturn(workflowData);
    Mockito.when(workflowData.getPayload()).thenReturn(object);
    Mockito.when(object.toString()).thenReturn("/content/ag/home.html");
    Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(resourceResolver.getResource(ArgumentMatchers.any(String.class)))
        .thenReturn(resource);
    Mockito.when(resource.getName()).thenReturn("productdata_20201228033009.json");
    Mockito.when(resource.adaptTo(Asset.class)).thenReturn(asset);
    Mockito.when(workflowData.getMetaDataMap()).thenReturn(metaDataMap);
    Mockito.when(asset.getOriginal()).thenReturn(rendition);
    final File initialFile = new File(
        "src/test/resources/com/mattel/ecomm/core/models/productfeedwriter_response.json");
    final InputStream inputstream = new FileInputStream(initialFile);
    Mockito.when(rendition.getStream()).thenReturn(inputstream);
    Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
    Mockito.when(session.isLive()).thenReturn(true);
    productFeedWriterProcess.execute(workItem, workflowSession, metaDataMap);
  }

  @Test
  public void testExecutewithObject_NotAliveSession()
      throws WorkflowException, JsonProcessingException, IOException {
    Mockito.when(workItem.getWorkflowData()).thenReturn(workflowData);
    Mockito.when(workflowData.getPayload()).thenReturn(object);
    Mockito.when(object.toString()).thenReturn("/content/ag/home.html");
    Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(resourceResolver.getResource(ArgumentMatchers.any(String.class)))
        .thenReturn(resource);
    Mockito.when(resource.getName()).thenReturn("productdata_20201228033009.json");
    Mockito.when(resource.adaptTo(Asset.class)).thenReturn(asset);
    Mockito.when(workflowData.getMetaDataMap()).thenReturn(metaDataMap);
    Mockito.when(asset.getOriginal()).thenReturn(rendition);
    final File initialFile = new File(
        "src/test/resources/com/mattel/ecomm/core/models/productfeedwriter_response.json");
    final InputStream inputstream = new FileInputStream(initialFile);
    Mockito.when(rendition.getStream()).thenReturn(inputstream);
    Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
    Mockito.when(session.isLive()).thenReturn(false);
    productFeedWriterProcess.execute(workItem, workflowSession, metaDataMap);
  }

  @Test
  public void testExecutewithObject()
      throws WorkflowException, JsonProcessingException, IOException {
    Mockito.when(workItem.getWorkflowData()).thenReturn(workflowData);
    Mockito.when(workflowData.getPayload()).thenReturn(object);
    Mockito.when(object.toString()).thenReturn("/content/ag/home.html");
    Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(resourceResolver.getResource(ArgumentMatchers.any(String.class)))
        .thenReturn(resource);
    Mockito.when(resource.getName()).thenReturn("productdata_20201228033009.json");
    Mockito.when(resource.adaptTo(Asset.class)).thenReturn(asset);
    Mockito.when(workflowData.getMetaDataMap()).thenReturn(metaDataMap);
    Mockito.when(asset.getOriginal()).thenReturn(rendition);
    final File initialFile = new File(
        "src/test/resources/com/mattel/ecomm/core/models/productfeedwriter_response.json");
    final InputStream inputstream = new FileInputStream(initialFile);
    Mockito.when(rendition.getStream()).thenReturn(inputstream);
    final ArrayList<String> validate = new ArrayList<>();
    validate.add("response");
    validate.add("isMandatory");
    validate.add("regex");
    validate.add("partNumber");
    validate.add("pdpLink");
    Mockito
        .when(productImportValidatorService.validate(ArgumentMatchers.any(String.class),
            ArgumentMatchers.any(JsonNode.class), ArgumentMatchers.any()))
        .thenReturn(validationResult);
    Mockito.when(productImportValidatorService.fieldToValidate()).thenReturn(validate);
    Mockito.when(validationResult.isSuccess()).thenReturn(false);
    productFeedWriterProcess.execute(workItem, workflowSession, metaDataMap);
  }

}
