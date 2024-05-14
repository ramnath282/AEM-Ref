package com.mattel.ecomm.core.servlets;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.jcr.Node;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.interfaces.ProductService;
import com.mattel.ecomm.core.pojos.ProductBadgePojo;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Component(service = Servlet.class, immediate = true, property = {

		Constants.SERVICE_DESCRIPTION + "= Product Badge Json", "sling.servlet.methods=" + HttpConstants.METHOD_GET,

		"sling.servlet.paths=" + "/bin/getProductBadge" })
public class ProductBadge extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductBadge.class);
	transient Resource resource;
	transient ResourceResolver resolver;
	@Reference
	transient MultifieldReader multifieldReader;
	@Reference
	transient ProductService productService;
	private transient List<ProductBadgePojo> productBageList = new LinkedList<>();

	String productSKUId;
	String badgeColour;
	String badgeDisplayValue;
	String badge;
	String badgeIcon;
	String textColour;
	static final String BADGETITLE = "badgeTitle";
	static final String TEXTCOLOR = "textColour";
	static final String PRODBADGE = "productBadge";

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("Product Badge Servlet doGet method  ----> Start");
		productSKUId = StringUtils.EMPTY;
		badgeColour = StringUtils.EMPTY;
		badgeIcon = StringUtils.EMPTY;
		badgeDisplayValue = StringUtils.EMPTY;
		badge = StringUtils.EMPTY;
		textColour = StringUtils.EMPTY;
		productBageList.clear();
        String plpPage = request.getParameter("plp");
		String currentPagePath = request.getParameter("currentPath");
		String sessionStorageValue = request.getParameter("sessionStorageValue");
		String isFull = (request.getParameter("isFull") != null) ? request.getParameter("isFull") : "";
		resolver = request.getResourceResolver();
		if (!request.getParameterMap().isEmpty() && currentPagePath.contains("productfinder")) {
				int lastIndex = currentPagePath.lastIndexOf('/');
				currentPagePath = currentPagePath.substring(0, lastIndex);
		}
		resource = resolver.getResource(currentPagePath);
		if (null != resource && !resource.getPath().contains("/conf/")) {
			PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
			if (Objects.nonNull(pageManager) && Objects.nonNull(pageManager.getPage(currentPagePath))) {
				Page page = pageManager.getPage(currentPagePath);
				Page absoluteParent = page;
				if (page.getDepth()>5) {
					absoluteParent = page.getAbsoluteParent(5);
				}
				executePLPorPDPLogicBasedOnInputRequest(request, plpPage, sessionStorageValue, absoluteParent, isFull);
			}
			LOGGER.debug("Session StorageValue  ----> Start");
		}
		try {
			prepareJson(response);
		} catch (NullPointerException e) {
			LOGGER.error("Null PointerException Occured {} ", e);
		} catch (JSONException e) {
			LOGGER.error("JSONException Occured {} ", e);
		}
		LOGGER.debug("doGet Method of ProductBadge ----> end here");

	}

	/**
	 * @param request
	 * @param plpPage
	 * @param sessionStorageValue
	 * @param page
	 * @param absoluteParent
	 */
	private void executePLPorPDPLogicBasedOnInputRequest(SlingHttpServletRequest request, String plpPage,
			String sessionStorageValue, Page absoluteParent, String isFull) {
		LOGGER.debug("executePLPorPDPLogicBasedOnInputRequest method  ----> Start");
		if (isFull.equals("true")) {
			if (absoluteParent.getName().equals("product")) {
				getParentProductProperties(absoluteParent);
			} else {
				getProductRootPathForPLP(absoluteParent);				
			}
		} else if(null != absoluteParent && StringUtils.isNotBlank(plpPage)){
			getProductRootPathForPLP(absoluteParent);
		} else {
			if (null != sessionStorageValue && Objects.nonNull(absoluteParent)) {
				setPageBadgeFromCurrentPageProperties(sessionStorageValue, absoluteParent);
			}
			if (Objects.nonNull(absoluteParent) && StringUtils.isEmpty(badgeColour)) {
				getProductBadgePropBasedOnSKUID(request, absoluteParent);
			}
		}
		LOGGER.debug("executePLPorPDPLogicBasedOnInputRequest method  ----> End");
	}


	/**
	 * @param request
	 * @param absoluteParent
	 */
	private void getProductBadgePropBasedOnSKUID(SlingHttpServletRequest request, Page absoluteParent) {
		productSKUId = EcommHelper.fetchProductSKUId(request);
		if (StringUtils.isNotBlank(productSKUId)) {
			LOGGER.debug("getProductBadgePropBasedOnSKUID method  ----> Start");
			try {
				String pageLocale = StringUtils.EMPTY;
				if (absoluteParent.getPath().contains("/language-masters/")) {
					pageLocale = productService.getPageLocaleFromMappings(absoluteParent.getPath(), pageLocale);
				} else {
					pageLocale = EcommHelper.getPageLocale(absoluteParent.getPath());
				}
				String commerceProductPath = Constant.COMMERCE_PRODUCTS_PATH
						+ EcommHelper.getBrandName(resource) + '/'+ pageLocale +"/product_" + productSKUId;
				ValueMap productProperties = productService.productProperties(commerceProductPath,
						resource);
				if (Objects.nonNull(productProperties)) {
					String badgeTempValue = productProperties.get("badge", String.class);
					if (null != badgeTempValue) {
						setPageBadgeFromCurrentPageProperties(badgeTempValue, absoluteParent);
					}
				}
			} catch (Exception e) {
				LOGGER.error("Exception Occured in getProductBadgePropBasedOnSKUID method {}", e);
			}
		}
		LOGGER.debug("getProductBadgePropBasedOnSKUID method  ----> End");
	}

	private void getProductRootPathForPLP(Page absoluteParent) {
		LOGGER.debug("getProductRootPathForPLP method  ----> Start");
		Page homePage = absoluteParent.getDepth()>5 ? absoluteParent.getAbsoluteParent(4) : absoluteParent;
		Iterator<Page> rootPageIterator = homePage.listChildren();
		while (rootPageIterator.hasNext()) {
			Page childPage = rootPageIterator.next();
			String prodTemplateType = childPage.getProperties().get("cq:template", String.class);
			if (StringUtils.isNotBlank(prodTemplateType) && prodTemplateType
						.equals("/conf/fisher-price/settings/wcm/templates/fisher-price-pdp-landing-template")) {
					getParentProductProperties(childPage);
					break;
				}
			}
		LOGGER.debug("getProductRootPathForPLP method  ----> End");
	}

	private void setPageBadgeFromCurrentPageProperties(String sessionStorageValue, Page parentPage) {
		Resource parentBadgeResource = parentPage.getContentResource().getChild(PRODBADGE);
		if (null != parentBadgeResource) {
			Node productBadgeNode = parentBadgeResource.adaptTo(Node.class);
			if (null != productBadgeNode) {
				getParentProductBadgeForCurrentPDP(sessionStorageValue, productBadgeNode);
			}
		}
	}

	/**
	 * @param sessionStorageValue
	 * @param productBadgeNode
	 */
	private void getParentProductBadgeForCurrentPDP(String sessionStorageValue, Node productBadgeNode) {
		LOGGER.debug("getParentProductBadgeForCurrentPDP method  ----> Start");
		Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(productBadgeNode);
		if (null != multifieldProperty) {
			for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
				String prodBadgeTitle = entry.getValue().get(BADGETITLE, String.class);
				if (prodBadgeTitle != null && prodBadgeTitle.equals(sessionStorageValue)) {
					badgeDisplayValue = entry.getValue().get("badgeDispalyValue", String.class);
					badgeColour = entry.getValue().get("badgeColour", String.class);
					badgeIcon = entry.getValue().get("badgeIcon", String.class);
					badge = entry.getValue().get(BADGETITLE, String.class);
					textColour = entry.getValue().get(TEXTCOLOR, String.class);
					break;
				}
			}
		}
		LOGGER.debug("getParentProductBadgeForCurrentPDP method  ----> End");
	}

	private void getParentProductProperties(Page parentPage) {
		LOGGER.debug("getParentProductProperties method  ----> Start");
		Resource parentBadgeResource = parentPage.getContentResource().getChild(PRODBADGE);
		if (null != parentBadgeResource) {
			Node productBadgeNode = parentBadgeResource.adaptTo(Node.class);
			if (null != productBadgeNode) {
				Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(productBadgeNode);
				if (null != multifieldProperty) {
					for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
						ProductBadgePojo badgeLink = new ProductBadgePojo();
						badgeLink.setBadgeColour(entry.getValue().get("badgeColour", String.class));
						badgeLink.setBadgeDisplayValue(entry.getValue().get("badgeDispalyValue", String.class));
						badgeLink.setBadgeIcon(entry.getValue().get("badgeIcon", String.class));
						badgeLink.setBadge(entry.getValue().get(BADGETITLE, String.class));
						badgeLink.setTextColour(entry.getValue().get(TEXTCOLOR, String.class));
						productBageList.add(badgeLink);
					}
				}
			}
		}
		LOGGER.debug("getParentProductProperties method  ----> End");
	}

	private void prepareJson(SlingHttpServletResponse response) throws JSONException, IOException {
		LOGGER.debug("prepareJson of Category Navigation Links---> start");
		if (StringUtils.isNotBlank(badge) || null != productBageList && !productBageList.isEmpty()) {
			JSONObject obj = new JSONObject();
			if (StringUtils.isNotBlank(badge)) {
				obj.put("Badge", badge);
				obj.put("BadgeDisplayValue", badgeDisplayValue);
				obj.put("BadgeColour", badgeColour);
				obj.put("BadgeIcon", badgeIcon);
				obj.put(TEXTCOLOR, textColour);
			} else {
				if (null != productBageList && !productBageList.isEmpty()) {
					obj.put("productBageList", productBageList);
				}
			}
			response.setContentType("application/json");
			response.getWriter().print(obj);
		}
		LOGGER.debug("prepareJson of Product Badge ---> end");
	}

}