package com.mattel.ecomm.core.services;

import java.io.IOException;

import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.interfaces.CategoryNavigationService;
import com.mattel.global.core.pojo.GlobalNavigationPojo;
import com.mattel.global.core.pojo.SiteNavigationPojo;
import com.mattel.global.core.utils.NavigationUtil;

@Component(service = CategoryNavigationService.class)
public class CategoryNavigationServiceImpl implements CategoryNavigationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryNavigationServiceImpl.class);
	@Reference
	private NavigationUtil navigationUtil;

	@Override
	public JSONObject processNavLinks(String deviceType, Resource currentresource, SlingHttpServletRequest request) {
		LOGGER.debug("doGet of CategoryNavigationLinks Servlet -> Start");
		JSONObject categoryNavJson = new JSONObject();
		String device = deviceType;
		try {
			Resource resource = currentresource;
			ValueMap nodeValues = resource.adaptTo(ValueMap.class);
			if (null != nodeValues) {
				GlobalNavigationPojo cnv = new GlobalNavigationPojo();
				fetchPropertyValues(nodeValues, cnv);
				cnv.setDevice(device);
				LOGGER.debug("doGet of CategoryNavigationLinks, build nav for device type : {}", device);
				SiteNavigationPojo categoryLinksPojo = new SiteNavigationPojo();
				categoryLinksPojo = navigationUtil.setCategoryDetailsForPage(categoryLinksPojo, resource, cnv);
				categoryLinksPojo = setCategoryOtherProperties(categoryLinksPojo, cnv);
				cnv.setParentLinksList(navigationUtil.fetchColumnLinks(cnv.getColumnOneDetails(), cnv.getParentLinksList(), resource, device));
				cnv.setParentLinksList(navigationUtil.fetchColumnLinks(cnv.getColumnTwoDetails(), cnv.getParentLinksList(), resource, device));
				cnv.setParentLinksList(navigationUtil.fetchColumnLinks(cnv.getColumnThreeDetails(), cnv.getParentLinksList(), resource,
						device));
				cnv.setParentLinksList(navigationUtil.fetchColumnLinks(cnv.getColumnFourDetails(), cnv.getParentLinksList(), resource, device));
				if (!cnv.getParentLinksList().isEmpty()) {
					categoryLinksPojo.setChildPageList(cnv.getParentLinksList());
				}
				LOGGER.debug("doGet of CategoryNavigationLinks, primary category navigation links pojo: {}", categoryLinksPojo);
				cnv.getCategoryNavLinks().add(categoryLinksPojo);
				cnv.setFeaturedLinksList(navigationUtil.checkFeaturedNode(resource, cnv.getFeaturedLinksList()));
				cnv.setImageSectionList(navigationUtil.getImageSection(resource, cnv.getImageSectionList(), 4));
				cnv.setColumnLayout(categoryLinksPojo.getChildPageList().size() > 3 && cnv.getImageSectionList().isEmpty()
						? "column-4"
						: "column-3");
				prepareJson(categoryNavJson, cnv);

			}
		} catch (NullPointerException e) {
			LOGGER.error("Null PointerException Occured {} ", e);
		} catch (JSONException e) {
			LOGGER.error("JSON Exception Occured {} ", e);
		} catch (RepositoryException e) {
			LOGGER.error("Repository Exception Occured {} ", e);
		}
		LOGGER.debug("doGet of CategoryNavigationLinks Servlet -> End");
		return categoryNavJson;
	}

	private SiteNavigationPojo setCategoryOtherProperties(SiteNavigationPojo categoryLinksPojo, GlobalNavigationPojo cnv) {
		LOGGER.debug("setCategoryOtherProperties -> Start");
		categoryLinksPojo.setShopAllClass("shopAll");
		categoryLinksPojo.setSubLinkClass("shop-by-category");
		categoryLinksPojo.setAdobeTrackingNameForPage(cnv.getAeForCategoryTitle());
		LOGGER.debug("setCategoryOtherProperties -> End");
		return categoryLinksPojo;
	}

	private void fetchPropertyValues(ValueMap nodeValues, GlobalNavigationPojo cnv) {
		LOGGER.debug("fetchPropertyValues -> Start");
		cnv.setDisplayShopByValue(nodeValues.containsKey("displayShopByValue")
				? nodeValues.get("displayShopByValue", Boolean.class)
				: cnv.getDefaultBoolean());
		cnv.setCategoryTitle(nodeValues.get("categoryTitle", String.class));
		cnv.setCategoryTitleLink(nodeValues.get("categoryTitleLink", String.class));
		cnv.setLinkTargetCategory(nodeValues.get("linkTargetCategory", String.class));
		cnv.setAeForCategoryTitle(nodeValues.get("aeForCategoryTitle", String.class));
		cnv.setColumnOneDetails(navigationUtil.fetchColumnDetails(nodeValues, "One"));
		cnv.setColumnTwoDetails(navigationUtil.fetchColumnDetails(nodeValues, "Two"));
		cnv.setColumnThreeDetails(navigationUtil.fetchColumnDetails(nodeValues, "Three"));
		cnv.setColumnFourDetails(navigationUtil.fetchColumnDetails(nodeValues, "Four"));
		cnv.setFeaturedTitle(nodeValues.get("featuredSetionTitleForMobile", String.class));
		LOGGER.debug("fetchNodePropertyValues : Category navigation inputs : displayShopByValue:{}, categoryTitle : {}, categoryTitleLink: {}"
				+ ", linkTargetCategory :{}, aeForCategoryTitle: {}, columnOneDetails :{}"
				+ ", columnTwoDetails: {}, columnThreeDetails: {}, columnFourDetails: {}, featuredTitle: {}", new Object[]{
			cnv.isDisplayShopByValue(),
			cnv.getCategoryTitle(),
			cnv.getCategoryTitleLink(),
			cnv.getLinkTargetCategory(),
			cnv.getAeForCategoryTitle(),
			cnv.getColumnOneDetails(),
			cnv.getColumnTwoDetails(),
			cnv.getColumnThreeDetails(),
			cnv.getColumnFourDetails(),
			cnv.getFeaturedTitle()
		});
		LOGGER.debug("fetchPropertyValues -> End");
	}

	/**
	 * Method to prepare the JSON Response
	 * 
	 * @param categoryNavJson
	 * 
	 * @param response
	 * @param tagLength
	 * @throws JSONException
	 * @throws IOException
	 */
	private void prepareJson(JSONObject categoryNavJson, GlobalNavigationPojo cnv) throws JSONException {
		LOGGER.debug("prepareJson of Category Navigation Links---> start");
		categoryNavJson.put("columnLayout", cnv.getColumnLayout());
		categoryNavJson.put("linkHeaderText", cnv.isDisplayShopByValue());
		categoryNavJson.put("categoryNavLinks", cnv.getCategoryNavLinks());
		categoryNavJson.put("ImageSection", cnv.getImageSectionList());
		categoryNavJson.put("featuredTitle", cnv.getFeaturedTitle());
		categoryNavJson.put("featured", cnv.getFeaturedLinksList());
		LOGGER.debug("prepareJson of Category Navigation Links---> end");
	}
}
