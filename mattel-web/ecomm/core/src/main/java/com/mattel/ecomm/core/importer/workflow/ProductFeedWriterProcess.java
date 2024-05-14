package com.mattel.ecomm.core.importer.workflow;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.InboxItem.Priority;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.adobe.granite.workflow.model.WorkflowNode;
import com.day.cq.dam.api.Asset;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.ecomm.core.importer.workflow.interfaces.ProductFeedInboxNotificationService;
import com.mattel.ecomm.core.importer.workflow.interfaces.ProductImportValidatorService;
import com.mattel.ecomm.core.importer.workflow.interfaces.ProductSaveAndUpdateService;
import com.mattel.ecomm.core.pojos.ProductJsonNode;
import com.mattel.ecomm.core.services.GetResourceResolver;
import com.mattel.ecomm.coreservices.core.pojos.ValidationResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = WorkflowProcess.class, property = {
    "process.label=Product PDP Feeder Custom Process Step" })
public class ProductFeedWriterProcess implements WorkflowProcess {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductFeedWriterProcess.class);
  private final ObjectMapper mapper = new ObjectMapper();
  @Reference
  private ProductImportValidatorService productImportValidatorService;
  @Reference
  private ProductSaveAndUpdateService productSaveAndUpdateService;
  @Reference
  private ProductFeedInboxNotificationService productFeedInboxNotificationService;
  @Reference
  private GetResourceResolver getResourceResolver;

  @Override
  public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
      throws WorkflowException {
    final WorkflowData workflowData = workItem.getWorkflowData();
    final String damFeedFilePath = workflowData.getPayload().toString();
    final List<String> errors = new ArrayList<>();
    ResourceResolver resourceResolver = null;

    ProductFeedWriterProcess.LOGGER.info("Product feed file recieved: {}", damFeedFilePath);

    try {
      resourceResolver = getResourceResolver.getResourceResolver();
      final Resource resource = resourceResolver.getResource(damFeedFilePath);
      final Asset asset;

      if (Objects.isNull(resource)) {
        errors.add("Product feed file resource not found at given dam path");
        workflowData.getMetaDataMap().put(ProductFeedConstants.METADATA_KEY_STATUS,
            ProductFeedConstants.STATUS_FAILURE);
        workflowData.getMetaDataMap().put(ProductFeedConstants.METADATA_KEY_ERRORS, errors);
        productFeedInboxNotificationService.createNotificationTask(
            "Product feed file resource not found at given dam path", damFeedFilePath, null,
            String.join(". ", errors), "", Priority.MEDIUM, resourceResolver);
        return;
      }
      
      String feedType = resource.getName().startsWith("delete_productfeed") ? "delete" : "full";
      LOGGER.debug("Processing the feed of type: {}",feedType);
     
      asset = resource.adaptTo(Asset.class);
      if (Objects.isNull(asset)) {
        errors.add("Unable to adapt product feed file resource to asset");
        workflowData.getMetaDataMap().put(ProductFeedConstants.METADATA_KEY_STATUS,
            ProductFeedConstants.STATUS_FAILURE);
        workflowData.getMetaDataMap().put(ProductFeedConstants.METADATA_KEY_ERRORS, errors);
        productFeedInboxNotificationService.createNotificationTask(
            "Unable to adapt product feed file resource to asset", damFeedFilePath, null,
            String.join(". ", errors), "", Priority.MEDIUM, resourceResolver);
        return;
      }

      try (InputStream content = asset.getOriginal().getStream()) {
        final JsonNode root = mapper.readTree(content);
        final JsonNode products = root.get(ProductFeedConstants.PRODUCT_FEED_JSON_ROOT_NODE);
        final List<String> fieldsToValidate = productImportValidatorService.fieldToValidate();
        final Map<ProductJsonNode, List<String>> failedNodes = new HashMap<>();
        List<ProductJsonNode> passedNodes;

        ProductFeedWriterProcess.LOGGER.info("Validating product feed file : {}", damFeedFilePath);

        if (Objects.isNull(products)) {
          errors.add(String.format("Node %s missing in product feed",
              ProductFeedConstants.PRODUCT_FEED_JSON_ROOT_NODE));
          workflowData.getMetaDataMap().put(ProductFeedConstants.METADATA_KEY_STATUS,
              ProductFeedConstants.STATUS_FAILURE);
          workflowData.getMetaDataMap().put(ProductFeedConstants.METADATA_KEY_ERRORS, errors);
          productFeedInboxNotificationService.createNotificationTask(
              "Product feed file failed validation", damFeedFilePath, null,
              String.join(". ", errors), "", Priority.MEDIUM, resourceResolver);
          return;
        }

        passedNodes = Stream.of(products).flatMap(t -> {
          final Iterable<JsonNode> iterable = t::iterator;
          return StreamSupport.stream(iterable.spliterator(), false);
        }).filter(product -> {
          final List<String> failureReasons = new ArrayList<>();
          final boolean passed = validateProduct(product, fieldsToValidate, failureReasons);

          if (!passed) {
            mapFailedNode(failedNodes, product, failureReasons);
          }

          return passed;
        }).map(product -> mapValidNode(product, failedNodes)).filter(Objects::nonNull)
            .collect(Collectors.toList());

        if (!failedNodes.isEmpty()) {
          final List<String> validationErrors = failedNodes.keySet().stream().map(failedNodes::get)
              .flatMap(l -> {
                final Iterable<String> iterable = l::iterator;
                return StreamSupport.stream(iterable.spliterator(), false);
              }).collect(Collectors.toList());

          generateFailureReport(failedNodes);
          workflowData.getMetaDataMap().put(ProductFeedConstants.METADATA_KEY_STATUS,
              ProductFeedConstants.STATUS_FAILURE);
          workflowData.getMetaDataMap().put(ProductFeedConstants.METADATA_KEY_ERRORS,
              validationErrors);
          productFeedInboxNotificationService.createNotificationTask(
              "Product feed file failed validation", damFeedFilePath, null,
              String.join(". ", new HashSet<>(validationErrors)), "", Priority.MEDIUM,
              resourceResolver);
        } else {
          saveAndUpdateProducts(passedNodes, workflowData, errors, resourceResolver);

          if (!errors.isEmpty()) {
            // Special case, check if resource resolver is alive or not. may be closed if
            // saveProducts method has thrown an error.
            if (!resourceResolver.isLive()) {
              resourceResolver = getResourceResolver.getResourceResolver();
            }

            productFeedInboxNotificationService.createNotificationTask(
                "Unable to save products recieved from Product feed file", damFeedFilePath, null,
                String.join(". ", errors), "", Priority.MEDIUM, resourceResolver);
          }

          passedNodes = null;
        }
      }
    } catch (IOException | NullPointerException e) {
      final String errorMessage = "Error while reading product feed file";

      ProductFeedWriterProcess.LOGGER.error(errorMessage, e);
      errors.add(errorMessage);
      workflowData.getMetaDataMap().put(ProductFeedConstants.METADATA_KEY_STATUS,
          ProductFeedConstants.STATUS_FAILURE);
      workflowData.getMetaDataMap().put(ProductFeedConstants.METADATA_KEY_ERRORS, errors);

      productFeedInboxNotificationService.createNotificationTask(errorMessage, damFeedFilePath,
          null, String.join(". ", errors), "", Priority.MEDIUM, resourceResolver);
    } catch (final Exception e) {
      final String errorMessage = "Unknown error encountered while reading product feed file";

      ProductFeedWriterProcess.LOGGER.error(errorMessage, e);
      errors.add(errorMessage);
      errors.add(e.getMessage());
      workflowData.getMetaDataMap().put(ProductFeedConstants.METADATA_KEY_STATUS,
          ProductFeedConstants.STATUS_FAILURE);
      workflowData.getMetaDataMap().put(ProductFeedConstants.METADATA_KEY_ERRORS, errors);
      productFeedInboxNotificationService.createNotificationTask(errorMessage, damFeedFilePath,
          null, String.join(". ", errors), "", Priority.MEDIUM, resourceResolver);
    } finally {
      if (Objects.nonNull(resourceResolver)) {
        resourceResolver.close();
      }
    }
  }

  /**
   * Generate failure report.
   *
   * @param failedNodes
   *          List of failed nodes.
   * @throws JsonProcessingException
   */
  private void generateFailureReport(final Map<ProductJsonNode, List<String>> failedNodes)
      throws JsonProcessingException {
    try {
      final StringBuilder sb = new StringBuilder();

      for (final Map.Entry<ProductJsonNode, List<String>> faileNode : failedNodes.entrySet()) {
        sb.append("JSON: ").append(mapper.writeValueAsString(faileNode.getKey().getJsonNode()))
            .append(",").append(" Errors: ").append(faileNode.getValue()).append("\n");
      }

      ProductFeedWriterProcess.LOGGER.error("Product Import failed nodes:\n {}", sb);
    } catch (final Exception e) {
      ProductFeedWriterProcess.LOGGER.error("Product Import report generation failed", e);
    }
  }

  @SuppressWarnings("unused")
  private void updateWorkFlowDescription(WorkItem workItem, final WorkflowData workflowData,
      List<ProductJsonNode> passedNodes) {
    final List<WorkflowNode> nodes = workItem.getWorkflow().getWorkflowModel().getNodes();

    for (final WorkflowNode workflowItem : nodes) {
      if ("New Product nodes imported".equals(workflowItem.getTitle())) {
        workflowData.getMetaDataMap().put("oldDescription", workflowItem.getDescription());
        workflowItem
            .setDescription(
                new StringBuilder("New Product nodes imported. Kindly approve.").append("\n")
                    .append(String.join("\n", passedNodes.stream()
                        .map(ProductJsonNode::getPartNumber).collect(Collectors.toList())))
                    .toString());
      }
    }
  }

  private void saveAndUpdateProducts(List<ProductJsonNode> passedNodes,
      final WorkflowData workflowData, final List<String> errors,
      ResourceResolver resourceResolver) {
    try {
      saveProducts(passedNodes, resourceResolver, workflowData);
      workflowData.getMetaDataMap().put(ProductFeedConstants.METADATA_KEY_STATUS,
          ProductFeedConstants.STATUS_SUCCESS);
      workflowData.getMetaDataMap().put(ProductFeedConstants.METADATA_KEY_ERRORS, errors);
      ProductFeedWriterProcess.LOGGER.info("Passed nodes size: {}", passedNodes.size());
    } catch (RepositoryException | WorkflowException e) {
      ProductFeedWriterProcess.LOGGER.error("Error while saving product nodes in repository", e);
      errors.add("Error while saving product nodes in repository");
      errors.add(e.getMessage());
      workflowData.getMetaDataMap().put(ProductFeedConstants.METADATA_KEY_STATUS,
          ProductFeedConstants.STATUS_FAILURE);
      workflowData.getMetaDataMap().put(ProductFeedConstants.METADATA_KEY_ERRORS, errors);
    }
  }

  /**
   * @param product
   * @param fieldsToValidate
   * @param passed
   * @param failureReasons
   * @return
   */
  private boolean validateProduct(JsonNode product, final List<String> fieldsToValidate,
      final List<String> failureReasons) {
    boolean passed = true;

    for (final String fieldName : fieldsToValidate) {
      final JsonNode value = product.get(fieldName);

      if (Objects.nonNull(value)) {
        final ValidationResult validationResult = productImportValidatorService.validate(fieldName,
            value, null);

        if (!validationResult.isSuccess()) {
          passed = false;
          failureReasons.addAll(validationResult.getErrorMessages());
        }
      } else if (productImportValidatorService.isMandatory(fieldName)) {
        passed = false;
        failureReasons.add(String.format("Missing mandatory field: %s", fieldName));
      }
    }
    return passed;
  }

  /**
   * @param session
   * @param rootNode
   * @param passedNodes
   * @param workflowData
   * @throws RepositoryException
   * @throws WorkflowException
   * @throws AccessDeniedException
   * @throws ItemExistsException
   * @throws ReferentialIntegrityException
   * @throws ConstraintViolationException
   * @throws InvalidItemStateException
   * @throws VersionException
   * @throws LockException
   * @throws NoSuchNodeTypeException
   * @throws ValueFormatException
   */
  private void saveProducts(final List<ProductJsonNode> passedNodes,
      final ResourceResolver resourceResolver, WorkflowData workflowData)
      throws RepositoryException, WorkflowException {
    int successCounter = 0;
    int timedOutCounter = 0;
    Session session = null;
    List<String> resourcePaths;

    try {
      session = resourceResolver.adaptTo(Session.class);

      if (Objects.isNull(session)) {
        throw new WorkflowException("Unable to save nodes, session unavaliable");
      }

      resourcePaths = new ArrayList<>(passedNodes.size());

      for (final ProductJsonNode productJsonNode : passedNodes) {
        if (session.isLive()) {
          final Node node = productSaveAndUpdateService.save(productJsonNode, session,
              resourceResolver);

          resourcePaths.add(node.getPath());
          successCounter++;
        } else {
          timedOutCounter++;
          ProductFeedWriterProcess.LOGGER.error("Sesssion time out before saving product: {}",
              productJsonNode.getPartNumber());
          throw new WorkflowException("Unable to save nodes, session timed out");
        }
      }

      session.save();
      workflowData.getMetaDataMap().put(ProductFeedConstants.METADATA_KEY_NODE_PATHS_TO_REPLICATE,
          mapper.writeValueAsString(resourcePaths));
      ProductFeedWriterProcess.LOGGER.info("########### Products successCounter: {}",
          successCounter);
      ProductFeedWriterProcess.LOGGER.info("########### Products timedOutCounter: {}",
          timedOutCounter);
    } catch (final JsonProcessingException e) {
      throw new WorkflowException(
          "Nodes saved in author, unable to persist node resourcepath in workflow metadata", e);
    } finally {
      if (session != null && session.isLive()) {
        session.logout();
      }
    }
  }

  /**
   * @param rootNode
   * @param passedProduct
   * @return
   */
  private ProductJsonNode mapValidNode(JsonNode passedProduct,
      final Map<ProductJsonNode, List<String>> failedNodes) {
    try {
      return mapJsonNodeToJcrNode(passedProduct, false);
    } catch (JsonProcessingException e) {
      ProductFeedWriterProcess.LOGGER.error("Unable to map valid node in product feed file", e);
      mapFailedNode(failedNodes, passedProduct, Arrays.asList(e.getMessage()));
      return null;
    }
  }

  /**
   * @param failedNodes
   * @param rootNode
   * @param product
   * @param failureReasons
   */
  private void mapFailedNode(final Map<ProductJsonNode, List<String>> failedNodes, JsonNode product,
      List<String> failureReasons) {
    try {
      failedNodes.put(mapJsonNodeToJcrNode(product, true), failureReasons);
    } catch (JsonProcessingException e) {
      ProductFeedWriterProcess.LOGGER.error("Unable to map invalid node in product feed file", e);
    }
  }

  private ProductJsonNode mapJsonNodeToJcrNode(JsonNode jsonNode, boolean isFailedNode)
      throws JsonProcessingException {
    ProductJsonNode newProductNode;
    Iterator<String> fieldNames;

    if (isFailedNode) {
      final String uuid = UUID.randomUUID().toString().toLowerCase();
      newProductNode = new ProductJsonNode(ProductFeedConstants.FAILED_NODE_NAME_PREFIX + uuid,
          jsonNode);
      newProductNode.setProperty(ProductFeedConstants.PROPERTY_IDENTIFIER, uuid);
    } else {
      final String skuId = jsonNode.get(ProductFeedConstants.PRODUCT_PRIMARY_KEY).asText().trim();
      newProductNode = new ProductJsonNode(skuId, jsonNode);
      newProductNode.setProperty(ProductFeedConstants.PROPERTY_IDENTIFIER,
          jsonNode.get(ProductFeedConstants.PRODUCT_IDENTIFIER).asText().trim());
      newProductNode.setProperty("cq:commerceType", "product");
      newProductNode.setProperty("jcr:title", skuId);
      newProductNode.setProperty("sling:resourceType", "commerce/components/product");
    }

    fieldNames = jsonNode.fieldNames();

    while (fieldNames.hasNext()) {
      final String fieldName = fieldNames.next();
      final JsonNode fieldValue = jsonNode.get(fieldName);

      if (fieldValue.isContainerNode()) {
        newProductNode.setProperty(fieldName, mapper.writeValueAsString(fieldValue));
      } else {
        newProductNode.setProperty(fieldName, fieldValue.asText());
      }
    }

    return newProductNode;
  }
}
