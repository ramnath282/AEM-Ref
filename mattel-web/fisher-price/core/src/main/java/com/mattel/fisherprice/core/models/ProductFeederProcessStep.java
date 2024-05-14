package com.mattel.fisherprice.core.models;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.taskmanagement.Task;
import com.adobe.granite.taskmanagement.TaskManager;
import com.adobe.granite.taskmanagement.TaskManagerException;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.dam.api.Asset;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mattel.fisherprice.core.constants.ProductFeederConstants;
import com.mattel.fisherprice.core.constants.ProductRespositoryFilter;
import com.mattel.fisherprice.core.services.GetResourceResolver;

@Component(service = WorkflowProcess.class, property = { "process.label=Product Feeder Custom Process Step" })

public class ProductFeederProcessStep implements WorkflowProcess {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductFeederProcessStep.class);
	static final String FILEREFERENCE = "fileReference";
	private final ObjectMapper mapper = new ObjectMapper();

	@Reference
	private GetResourceResolver getResourceResolver;

	ResourceResolver resourceResolver = null;

	String jsonAssetPath = null;

	String title = null;
	String locale = null;

	public void execute(WorkItem item, WorkflowSession wfsession, MetaDataMap args) throws WorkflowException {

		try {
			LOGGER.debug("execute method starts");
			resourceResolver = getResourceResolver.getResourceResolver();
			WorkflowData workflowData = item.getWorkflowData();
			jsonAssetPath = workflowData.getPayload().toString();
			Session session = wfsession.adaptTo(Session.class);
			if (null != session) {
				JSONObject products = readJsonFromDamm(jsonAssetPath, resourceResolver);
				String[] splitValues = jsonAssetPath.split("_");
				locale = splitValues[1];
				if (null != products) {
					JSONArray productProperties = products.getJSONArray("FP");
					Node productNode = session.getNode(ProductFeederConstants.FP_VAR_PRODUCT_PATH + locale);
					if (null != productNode) {
						fetchProductsFromFeed(productProperties, productNode, session, workflowData);
					}
				}
			}
		} catch (Exception e) {
			String error = e.getMessage();
			try {
				LOGGER.info("inside try of failure notification {}", e);
				productFeedFailureNotification(error, resourceResolver, jsonAssetPath, title);
			} catch (TaskManagerException e1) {
				LOGGER.info("Execption due to : {}", e1);
			}
			LOGGER.error("Exception Occured {} ", e.getMessage());
		} finally {
			if (Objects.nonNull(resourceResolver)) {
				resourceResolver.close();
			}
			LOGGER.debug("execute method ends");
		}
	}

	private void fetchProductsFromFeed(JSONArray productProperties, Node rootNode, Session session,
									   WorkflowData workflowData)
			throws JSONException, RepositoryException, JsonProcessingException {
		final List<String> publishNodes = new ArrayList<>();
		final List<String> unpublishNodes = new ArrayList<>();
		final List<String> unpublishAndDeleteNodes = new ArrayList<>();
		final List<String> authorOnlyNodes = new ArrayList<>();
		final Map<String, List<String>> nodeDelta = new HashMap<>();

		ProductFeederProcessStep.LOGGER.debug("Fetching products from imported feed");

		for (int i = 0; i < productProperties.length(); i++) {
			final JSONObject productJson = productProperties.getJSONObject(i).getJSONObject("product");

			saveAndUpdateProduct(rootNode, productJson, publishNodes, unpublishNodes,
					unpublishAndDeleteNodes, authorOnlyNodes, session);
		}

		ProductFeederProcessStep.LOGGER.debug("Nodes(count: {}) to be published: {}",
				publishNodes.size(), publishNodes);
		ProductFeederProcessStep.LOGGER.debug("Nodes(count: {}) to be unpublished: {}",
				unpublishNodes.size(), unpublishNodes);
		ProductFeederProcessStep.LOGGER.debug(
				"Nodes(count: {}) to be deleted from author and unpublished: {}",
				unpublishAndDeleteNodes.size(), unpublishAndDeleteNodes);
		ProductFeederProcessStep.LOGGER.debug(
				"Nodes(count: {}) newly created on author and not to be published: {}",
				authorOnlyNodes.size(), authorOnlyNodes);

		nodeDelta.put(ProductFeederConstants.NODES_TO_PUBLISH, publishNodes);
		nodeDelta.put(ProductFeederConstants.NODES_TO_UNPUBLISH, unpublishNodes);
		nodeDelta.put(ProductFeederConstants.NODES_TO_UNPUBLISH_AND_DELETE_FROM_AUTHOR,
				unpublishAndDeleteNodes);
		workflowData.getMetaDataMap().put(ProductFeederConstants.WORKFLOW_NODE_REPLICATION_DATA,
				mapper.writeValueAsString(nodeDelta));
	}

	/**
	 * To save or update product in repository.
	 *
	 * @param rootNode
	 *          The root node of products for a language type.
	 * @param productJson
	 *          The incoming product feed.
	 * @param publishNodes
	 *          To store the nodes to be activated on publisher.
	 * @param unpublishNodes
	 *          To store the nodes to be deactivated on publisher.
	 * @param deleteAndUnplishNodes
	 *          To store the nodes to be deactivated on publisher and to be deleted from author.
	 * @param authorOnlyNodes
	 *          To store the nodes created on author, but not activated on publisher.
	 * @param session
	 *          The {@link Session} instance .
	 * @throws RepositoryException
	 *          Unable to save or update changes in repository.
	 * @throws JSONException
	 *          Invalid JSON encountered.
	 */
	private void saveAndUpdateProduct(Node rootNode, final JSONObject productJson,
									  final List<String> publishNodes, final List<String> unpublishNodes,
									  final List<String> deleteAndUnplishNodes, final List<String> authorOnlyNodes, Session session)
			throws RepositoryException, JSONException {
		title = "product_" + productJson.getString("partNumber");

		if (rootNode.hasNode(title)) {
			if (ProductRespositoryFilter.IS_ACTIVE.test(productJson)) {
				if (ProductRespositoryFilter.IS_UNPUBLISH.test(productJson)) {
					unpublishNodes.add(rootNode.getPath() + "/" + title);
				} else {
					// publish flag == "1" and status quo nodes.
					publishNodes.add(rootNode.getPath() + "/" + title);
				}

				updateVarProductProperties(title, session, productJson);
			} else if (ProductRespositoryFilter.IS_DEACTIVE.test(productJson)) {
				deleteAndUnplishNodes.add(rootNode.getPath() + "/" + title);
				updateVarProductProperties(title, session, productJson);
			}
		} else if (ProductRespositoryFilter.IS_ACTIVE.test(productJson)) {
			// For creating new nodes in author, we would only consider IS_ACTIVE flag.
			// Publish only those for which IS_PUBLISH flag set.
			if (ProductRespositoryFilter.IS_PUBLISH.test(productJson)) {
				publishNodes.add(rootNode.getPath() + "/" + title);
			} else {
				authorOnlyNodes.add(rootNode.getPath() + "/" + title);
			}

			createVarProductWithProperties(rootNode, session, title, productJson);
		}
	}

	private void productFeedFailureNotification(String error, ResourceResolver resourceResolver, String jsonAssetPath,
												String title) throws TaskManagerException {
		LOGGER.debug("productFeedFailureNotification method starts----->>>>>>>");
		final TaskManager taskManager = resourceResolver.adaptTo(TaskManager.class);
		if (Objects.nonNull(taskManager)) {
			final Task newTask = taskManager.getTaskManagerFactory().newTask("Notification");
			newTask.setName("Notification for FisherPrice ProductFeedFailure");
			newTask.setContentPath(jsonAssetPath);
			newTask.setCurrentAssignee("content-authors");
			String regex = "\"";
			String[] errorVal = error.split(regex);
			if (errorVal.length > 1) {
				newTask.setDescription("FisherPrice Product Feed failed due to " + errorVal[1]
						+ " attributes miss matched." + " product feed completed till " + title);
			}
			taskManager.createTask(newTask);
		} else {
			LOGGER.debug("Unable to send notification, task manager unavailable");
		}
	}

	public JSONObject readJsonFromDamm(String jsonAssetPath, ResourceResolver resourceResolver) throws JSONException {
		final Resource resource = resourceResolver.getResource(jsonAssetPath);
		JSONObject jsonObject = null;
		if (null != resource) {
			Asset asset = resource.adaptTo(Asset.class);
			if (null != asset) {
				InputStream content = asset.getOriginal().getStream();
				JsonElement element = new JsonParser().parse(new InputStreamReader(content));
				jsonObject = new JSONObject(element.getAsJsonObject().toString());
			}
		}
		LOGGER.debug("readJsonFromDamm method ends : {}", jsonObject);
		return jsonObject;
	}


	private void createVarProductWithProperties(Node productNode, Session session, String title,
												JSONObject productChild) throws RepositoryException, JSONException {
		LOGGER.info("createVarProductWithProperties method starts for product: {}", title);

		try {
			final Node newProductNode = productNode.addNode(title,
					ProductFeederConstants.NT_UNSTRUCTURED);
			setBasicProductProperties(productChild, newProductNode);
			setProductTitleDescriptionBrandAndCategory(productChild, newProductNode);
			setProductSubCategoryAndPLAGoogleItems(productChild, newProductNode);
			setProductCareInstructionLegalAgeGrsItems(productChild, newProductNode);
			setProductWhatInBoxandBadgeItems(productChild, newProductNode);
			setProductWarningMessageAndSeoItems(productChild, newProductNode);
			setProductImageAssets(session, title, productChild);
			LOGGER.info("createVarProductWithProperties method ends for product: {}", title);
		} catch (RepositoryException | JSONException e) {
			LOGGER.error(
					String.format("createVarProductWithProperties unable to create product: %s", title), e);
			throw e;
		}
	}

	private void updateVarProductProperties(String title, Session session, JSONObject productChild)
			throws RepositoryException, JSONException {
		LOGGER.info("updateVarProductProperties method starts for product: {}", title);

		try {
			final Node newProductNode = session.getNode(ProductFeederConstants.FP_VAR_PRODUCT_PATH
					+ locale + ProductFeederConstants.SLASH + title);
			setProductTitleDescriptionBrandAndCategory(productChild, newProductNode);
			setProductSubCategoryAndPLAGoogleItems(productChild, newProductNode);
			setProductCareInstructionLegalAgeGrsItems(productChild, newProductNode);
			setProductWhatInBoxandBadgeItems(productChild, newProductNode);
			setProductWarningMessageAndSeoItems(productChild, newProductNode);
			updateProductImageAssets(title, session, productChild);
			LOGGER.info("updateVarProductProperties method ends for product: {}", title);
		} catch (RepositoryException | JSONException e) {
			LOGGER.error(
					String.format("createVarProductWithProperties unable to update product: %s", title), e);
			throw e;
		}
	}

	private void updateProductImageAssets(String title, Session session, JSONObject productChild)
			throws RepositoryException, JSONException {
		LOGGER.info("updateProductImageAssets method starts for product: {}", title);

		Node productChildNode = session.getNode(ProductFeederConstants.FP_VAR_PRODUCT_PATH + locale
				+ ProductFeederConstants.SLASH + title + "/" + "assets");
		if (null != productChildNode) {
			NodeIterator ni = productChildNode.getNodes();
			LOGGER.debug("Node Iterator Object---->>>>>>>> {}", ni);
			while (ni.hasNext()) {
				Node childNode = ni.nextNode();
				if ("asset".equals(childNode.getName())) {
					childNode.setProperty(FILEREFERENCE, productChild.getString("fullImage"));
				} else {
					childNode.setProperty(FILEREFERENCE, productChild.getString("imageCollection"));
				}
			}
		}
		LOGGER.info("updateProductImageAssets method ends for product: {}", title);

	}

	private void setProductImageAssets(Session session, String title, JSONObject productChild)
			throws RepositoryException, JSONException {
		LOGGER.info("setProductImageAssets method starts for product: {}", title);
		Node productChildNode = session
				.getNode(ProductFeederConstants.FP_VAR_PRODUCT_PATH + locale + ProductFeederConstants.SLASH + title);
		Node newProductChildNode = productChildNode.addNode("assets", ProductFeederConstants.NT_UNSTRUCTURED);
		productChildNode = session.getNode(ProductFeederConstants.FP_VAR_PRODUCT_PATH + locale
				+ ProductFeederConstants.SLASH + title + "/" + newProductChildNode.getName());
		newProductChildNode = productChildNode.addNode("asset", "nt:unstructured");
		newProductChildNode.setProperty(FILEREFERENCE, productChild.getString("fullImage"));
		newProductChildNode.setProperty("sling:resourceType", "commerce/components/product/image");
		newProductChildNode = productChildNode.addNode("asset0", "nt:unstructured");
		newProductChildNode.setProperty(FILEREFERENCE, productChild.getString("imageCollection"));
		newProductChildNode.setProperty("sling:resourceType", "commerce/components/product/image");
		LOGGER.info("setProductImageAssets method ends for product: {}", title);
	}

	private void setProductWarningMessageAndSeoItems(JSONObject productChild, Node newProductNode)
			throws RepositoryException, JSONException {
		LOGGER.info("setProductWarningMessageAndSeoItems method starts for product: {}", title);
		newProductNode.setProperty(ProductFeederConstants.WARNINGMESSAGE,
				productChild.getString(ProductFeederConstants.WARNINGMESSAGE));
		newProductNode.setProperty(ProductFeederConstants.PRODUCTURL,
				productChild.getString(ProductFeederConstants.PRODUCTURL));
		newProductNode.setProperty(ProductFeederConstants.SEO_PAGETITLE,
				productChild.getString(ProductFeederConstants.SEO_PAGETITLE));
		newProductNode.setProperty(ProductFeederConstants.SEO_METADESCRIPTION,
				productChild.getString(ProductFeederConstants.SEO_METADESCRIPTION));
		newProductNode.setProperty(ProductFeederConstants.SEO_KEYWORDS,
				productChild.getString(ProductFeederConstants.SEO_KEYWORDS));
		LOGGER.info("setProductWarningMessageAndSeoItems method ends for product: {}", title);
	}

	private void setProductWhatInBoxandBadgeItems(JSONObject productChild, Node newProductNode)
			throws RepositoryException, JSONException {
		LOGGER.debug("setProductWhatInBoxandBadgeItems method starts");
		newProductNode.setProperty(ProductFeederConstants.ASSEMBLYINSTRUCTIONS_LINK,
				productChild.getString(ProductFeederConstants.ASSEMBLYINSTRUCTIONS_LINK));
		newProductNode.setProperty(ProductFeederConstants.USERMANUALLINK,
				productChild.getString(ProductFeederConstants.USERMANUALLINK));
		JSONArray whatsInBox = productChild.getJSONArray(ProductFeederConstants.WHATSINBOX);
		String[] whatSinBox = new String[whatsInBox.length()];
		for (int i = 0; i < whatSinBox.length; i++) {
			whatSinBox[i] = whatsInBox.getJSONObject(i).toString();
		}
		newProductNode.setProperty(ProductFeederConstants.WHATSINBOX, whatSinBox);
		newProductNode.setProperty(ProductFeederConstants.BADGE, productChild.getString(ProductFeederConstants.BADGE));
		LOGGER.debug("setProductWhatInBoxandBadgeItems method ends");
	}

	private void setProductCareInstructionLegalAgeGrsItems(JSONObject productChild, Node newProductNode)
			throws JSONException, RepositoryException {
		LOGGER.debug("setProductCareInstructionLegalAgeGrsItems method starts");
		JSONArray careInstructions = productChild.getJSONArray("careInstructions");
		String[] careInstruction = new String[careInstructions.length()];
		for (int i = 0; i < careInstruction.length; i++) {
			careInstruction[i] = careInstructions.getJSONObject(i).toString();
		}
		newProductNode.setProperty("careInstructions", careInstruction);
		JSONArray grs = productChild.getJSONArray(ProductFeederConstants.GRS);
		String[] gRS = new String[grs.length()];
		for (int i = 0; i < gRS.length; i++) {
			gRS[i] = grs.getString(i);
		}
		newProductNode.setProperty(ProductFeederConstants.GRS, gRS);
		newProductNode.setProperty(ProductFeederConstants.MARKETINGDESCRIPTION,
				productChild.getString(ProductFeederConstants.MARKETINGDESCRIPTION));
		JSONArray marketingAge = productChild.getJSONArray(ProductFeederConstants.MARKETINGAGE);
		String[] marketingAGE = new String[marketingAge.length()];
		for (int i = 0; i < marketingAGE.length; i++) {
			marketingAGE[i] = marketingAge.getString(i);
		}
		newProductNode.setProperty(ProductFeederConstants.MARKETINGAGE, marketingAGE);
		newProductNode.setProperty(ProductFeederConstants.SAFETYMESSAGE,
				productChild.getString(ProductFeederConstants.SAFETYMESSAGE));
		newProductNode.setProperty(ProductFeederConstants.PLAGOOGLEPRODCAT,
				productChild.getString(ProductFeederConstants.PLAGOOGLEPRODCAT));
		newProductNode.setProperty(ProductFeederConstants.CHARACTER,
				productChild.getString(ProductFeederConstants.CHARACTER));
		newProductNode.setProperty("ageGrade", productChild.getString("legalAge"));
		LOGGER.debug("setProductCareInstructionLegalAgeGrsItems method ends");
	}

	private void setProductSubCategoryAndPLAGoogleItems(JSONObject productChild, Node newProductNode)
			throws JSONException, RepositoryException {
		LOGGER.debug("setProductSubCategoryAndPLAGoogleItems method starts");
		JSONArray subCat = productChild.getJSONArray(ProductFeederConstants.SUBCATEGORY);
		String[] subCategory = new String[subCat.length()];
		for (int i = 0; i < subCategory.length; i++) {
			subCategory[i] = subCat.getString(i);
		}
		newProductNode.setProperty(ProductFeederConstants.SUBCATEGORY, subCategory);
		newProductNode.setProperty(ProductFeederConstants.ACTIVE,
				productChild.getString(ProductFeederConstants.ACTIVE));
		newProductNode.setProperty(ProductFeederConstants.PUBLISHED_START_DATE,
				productChild.getString(ProductFeederConstants.PUBLISHED_START_DATE));
		newProductNode.setProperty(ProductFeederConstants.CREATED_DATE,
				productChild.getString(ProductFeederConstants.CREATED_DATE));
		newProductNode.setProperty(ProductFeederConstants.PLAGOOGLEBRAND,
				productChild.getString(ProductFeederConstants.PLAGOOGLEBRAND));
		newProductNode.setProperty(ProductFeederConstants.RATINGAVG,
				productChild.getString(ProductFeederConstants.RATINGAVG));
		newProductNode.setProperty(ProductFeederConstants.PRODUCTREVIEWCOUNT,
				productChild.getString(ProductFeederConstants.PRODUCTREVIEWCOUNT));
		newProductNode.setProperty("price", productChild.getString("listPrice"));
		newProductNode.setProperty(ProductFeederConstants.LIST_PRICE_CURRENCY,
				productChild.getString(ProductFeederConstants.LIST_PRICE_CURRENCY));
		newProductNode.setProperty(ProductFeederConstants.PLAGOOGLEPRODTYPE,
				productChild.getString(ProductFeederConstants.PLAGOOGLEPRODTYPE));
		newProductNode.setProperty(ProductFeederConstants.CANONICAL_CATEGORY,
				productChild.getString(ProductFeederConstants.CANONICAL_CATEGORY));
		newProductNode.setProperty(ProductFeederConstants.PRODUCT_RECALL,
				productChild.getString(ProductFeederConstants.PRODUCT_RECALL));
		newProductNode.setProperty(ProductFeederConstants.PRODUCT_RECALL_DATETIME,
				productChild.getString(ProductFeederConstants.PRODUCT_RECALL_DATETIME));
		newProductNode.setProperty(ProductFeederConstants.PRODUCT_RECALL_MESSAGE,
				productChild.getString(ProductFeederConstants.PRODUCT_RECALL_MESSAGE));
		LOGGER.debug("setProductSubCategoryAndPLAGoogleItems method ends");
	}

	private void setProductTitleDescriptionBrandAndCategory(JSONObject productChild, Node newProductNode)
			throws RepositoryException, JSONException {
		LOGGER.info("setProductTitleDescriptionBrandAndCategory method starts");
		newProductNode.setProperty("jcr:title", productChild.getString("auxDesc1"));
		JSONArray productDesc = productChild.getJSONArray("productDesc");
		String[] productDescription = new String[productDesc.length()];
		for (int i = 0; i < productDescription.length; i++) {
			productDescription[i] = productDesc.getJSONObject(i).toString();
		}
		newProductNode.setProperty("jcr:description", productDescription);
		newProductNode.setProperty(ProductFeederConstants.BRAND, productChild.getString(ProductFeederConstants.BRAND));
		newProductNode.setProperty(ProductFeederConstants.BRANDLINKURL,
				productChild.getString(ProductFeederConstants.BRANDLINKURL));
		newProductNode.setProperty(ProductFeederConstants.BUYABLE,
				productChild.getString(ProductFeederConstants.BUYABLE));
		newProductNode.setProperty(ProductFeederConstants.PUBLISHED,
				productChild.getString(ProductFeederConstants.PUBLISHED));
		newProductNode.setProperty(ProductFeederConstants.LANGUAGE,
				productChild.getString(ProductFeederConstants.LANGUAGE));
		newProductNode.setProperty(ProductFeederConstants.REGION,
				productChild.getString(ProductFeederConstants.REGION));
		JSONArray cat = productChild.getJSONArray("category");
		String[] category = new String[cat.length()];
		for (int i = 0; i < category.length; i++) {
			category[i] = cat.getString(i);
		}
		newProductNode.setProperty(ProductFeederConstants.CATEGORY, category);
		newProductNode.setProperty(ProductFeederConstants.SUPERCATEGORY,
				productChild.getString(ProductFeederConstants.SUPERCATEGORY));
		newProductNode.setProperty(ProductFeederConstants.SUBBRAND,
				productChild.getString(ProductFeederConstants.SUBBRAND));
		newProductNode.setProperty(ProductFeederConstants.THEME,
				productChild.getString(ProductFeederConstants.THEME));
		newProductNode.setProperty(ProductFeederConstants.ALLBRANDS,
				productChild.getString(ProductFeederConstants.ALLBRANDS));
		LOGGER.info("setProductTitleDescriptionBrandAndCategory method ends");
	}

	private void setBasicProductProperties(JSONObject productChild, Node newProductNode)
			throws RepositoryException, JSONException {
		LOGGER.debug("setBasicProductProperties method starts");
		newProductNode.setProperty("cq:commerceType", "product");
		newProductNode.setProperty(ProductFeederConstants.SLING_RESOURCETYPE, "commerce/components/product");
		String[] textRich = { "true", "true", "true" };
		newProductNode.setProperty("textRich", textRich);
		newProductNode.setProperty("identifier", productChild.getString("partNumber"));
		LOGGER.debug("setBasicProductProperties method ends");
	}

	public void setGetResourceResolver(GetResourceResolver getResourceResolver) {
		this.getResourceResolver = getResourceResolver;
	}

	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}

}
