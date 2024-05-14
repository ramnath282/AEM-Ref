package com.mattel.ecomm.core.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.interfaces.ContentNavigationService;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.global.core.pojo.GlobalNavigationPojo;
import com.mattel.global.core.pojo.PromoImagePojo;
import com.mattel.global.core.pojo.SiteNavigationPojo;
import com.mattel.global.core.utils.NavigationUtil;

@Component(service = ContentNavigationService.class)
public class ContentNavigationServiceImpl implements ContentNavigationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ContentNavigationService.class);
	@Reference
	private NavigationUtil navigationService;
	private static final String PROMO_IMAGE = "promoImage";
	private static final String NO_IMAGE = "noImage";

	/**
	 * @param deviceType
	 * @param currentresource
	 * @return
	 * @throws IOException
	 */
	@Override
	public JSONObject processNavLinks(String deviceType, Resource currentresource) {
		LOGGER.debug("doGet of  ContentNavigationService Service -> Start");
		JSONObject otherNavJson = new JSONObject();
		GlobalNavigationPojo cnv = new GlobalNavigationPojo();
		Resource resource;
		String device = deviceType;
		try {
			resource = currentresource;
			ValueMap nodeValues = resource.adaptTo(ValueMap.class);
			if (null != nodeValues) {
				SiteNavigationPojo primaryNavPojo = new SiteNavigationPojo();
				fetchNodePropertyValues(nodeValues, cnv);
				cnv.setDevice(device);
				LOGGER.debug("doGet of ContentNavigationService, build nav for device type : {}", device);
				primaryNavPojo = navigationService.setCategoryDetailsForContent(primaryNavPojo, resource, cnv);
				primaryNavPojo = navigationService.getTemplateVariationType(primaryNavPojo, cnv);
				cnv.setSecondaryNavLinks(navigationService.getSecondaryCategoryTitle(resource, cnv));
				if (null != cnv.getSecondaryNavLinks() && !cnv.getSecondaryNavLinks().isEmpty()) {
					primaryNavPojo.setChildPageList(cnv.getSecondaryNavLinks());
				}
				LOGGER.debug("doGet of ContentNavigationService, primary navigation links pojo: {}", primaryNavPojo);
				cnv.getPrimaryNavLinks().add(primaryNavPojo);
				cnv.setImageSectionList(fetchPromoImageListByTemplateType(resource, cnv));
				

				prepareJson(otherNavJson, cnv);
			}

		} catch (NullPointerException e) {
			LOGGER.error("Null PointerException Occured {} ", e);
		} catch (RepositoryException e) {
			LOGGER.error(" RepositoryException Occured {} ", e);
		} catch (JSONException e) {
			LOGGER.error(" JSONException Occured {} ", e);
		}
		LOGGER.debug("doGet of ContentNavigationService Service -> End");
		return otherNavJson;
	}

	/**
	 * @param nodeValues
	 * @param cnv 
	 */
	private void fetchNodePropertyValues(ValueMap nodeValues, GlobalNavigationPojo cnv) {
		LOGGER.info("fetchNodePropertyValues -> Start");
		cnv.setTemplateType(EcommHelper.getValueMapNodeValue(nodeValues, Constant.TEMPLATE_TYPE));
		cnv.setPrimaryNavigationTitle(EcommHelper.getValueMapNodeValue(nodeValues, Constant.PRIMARY_NAV_TITLE));
		cnv.setPrimaryNavTitleLink(EcommHelper.getValueMapNodeValue(nodeValues, Constant.PRIMARY_NAV_TITLE_LINK));
		cnv.setLinkTargetPrimaryNav(EcommHelper.getValueMapNodeValue(nodeValues, Constant.LINK_TARGET_PRIMARY_NAV));
		cnv.setAeForPrimaryNavTitle(EcommHelper.getValueMapNodeValue(nodeValues, Constant.AE_PRIMARY_NAV_TITLE));
		cnv.setNavigationalLinks(nodeValues.get("pages", String[].class));
		cnv.setCategoryTitle(EcommHelper.getValueMapNodeValue(nodeValues, Constant.CATEGORY_TITLE));
		cnv.setColumnLayout(EcommHelper.getValueMapNodeValue(nodeValues, Constant.COLUMN_LAYOUT));
		cnv.setDisplayShopByValue(nodeValues.containsKey(Constant.DISPLAY_SHOP_BY_VALUE)
				? nodeValues.get(Constant.DISPLAY_SHOP_BY_VALUE, Boolean.class)
				: cnv.getDefaultBoolean());
		cnv.setPromoHoverImagePath(EcommHelper.getValueMapNodeValue(nodeValues, Constant.CONTENTNAV_AWING_IMG));
		if (!Constant.BRAND_TEMPLATE.equals(cnv.getTemplateType())) {
			cnv.setColumnLayout(cnv.getTemplateType());
		}		
		LOGGER.debug("fetchNodePropertyValues : Content navigation inputs : templateType: {}, primaryNavigationTitle : {}, primaryNavTitleLink: {}"
				+ ", linkTargetPrimaryNav :{}, aeForPrimaryNavTitle: {}, navigationalLinks :{}"
				+ ", categoryTitle: {}, columnLayout: {}, displayShopByValue: {}", new Object[]{
			cnv.getTemplateType(),
			cnv.getPrimaryNavigationTitle(),
			cnv.getPrimaryNavTitleLink(),
			cnv.getLinkTargetPrimaryNav(),
			cnv.getAeForPrimaryNavTitle(),
			cnv.getNavigationalLinks(),
			cnv.getCategoryTitle(),
			cnv.getColumnLayout(),
			cnv.isDisplayShopByValue()
		});
		LOGGER.info("fetchNodePropertyValues -> End");
	}

	/**
	 * @param resource
	 * @param imageSectionList 
	 * @param templateType
	 * @return 
	 * @throws RepositoryException
	 */
	@Override
	public List<PromoImagePojo> fetchPromoImageListByTemplateType(Resource resource, GlobalNavigationPojo cnv) throws RepositoryException {
		LOGGER.debug("fetchPromoImageListByTemplateType -> Start");
		 List<PromoImagePojo> imageSectionListCopy = new ArrayList<>();
		 String templateType = Optional.ofNullable(cnv.getTemplateType()).orElse(StringUtils.EMPTY);
		if (templateType.equals(Constant.FISHER_PRICE_BRAND_TEMPLATE)) {
			imageSectionListCopy = navigationService.getImageSection(resource, cnv.getImageSectionList(), 4);
			if (imageSectionListCopy.size() > 1) {
				cnv.setImageAlignmentType("blockImage");
			} else if (imageSectionListCopy.size() == 1) {
				cnv.setImageAlignmentType(PROMO_IMAGE);
			}
		} else if (templateType.equals(Constant.FISHER_PRICE_AGE_TEMPLATE)) {
			cnv.setImageAlignmentType(NO_IMAGE);
		} else if (templateType.equals(Constant.FISHER_PRICE_EXPLORE_COLUMN_TWO)
				|| templateType.equals(Constant.FISHER_PRICE_EXPLORE_COLUMN_THREE)
				|| templateType.equals(Constant.FISHER_PRICE_EXPLORE_COLUMN_FOUR)) {
			imageSectionListCopy = navigationService.getImageSection(resource, cnv.getImageSectionList(), 1);
			if (!imageSectionListCopy.isEmpty() && imageSectionListCopy.size() == 1) {
				cnv.setImageAlignmentType(PROMO_IMAGE);
			} else {
				cnv.setImageAlignmentType(NO_IMAGE);
			}
		}
		LOGGER.debug("fetchPromoImageListByTemplateType -> End");
		return imageSectionListCopy;

	}
	
	/**
	 * @param response
	 * @throws JSONException
	 * @throws IOException
	 */
	private void prepareJson(JSONObject otherNavJson, GlobalNavigationPojo cnv) throws JSONException {

		LOGGER.debug("Json Conversion");
		otherNavJson.put("linkHeaderText", cnv.isDisplayShopByValue());
		otherNavJson.put("columnLayout", cnv.getColumnLayout());
		otherNavJson.put("imageAlignmentType", cnv.getImageAlignmentType());
		if (null != cnv.getPrimaryNavLinks()) {
			otherNavJson.put("categoryNavLinks", cnv.getPrimaryNavLinks());
		}
		if (null != cnv.getImageSectionList()) {
			otherNavJson.put("ImageSection", cnv.getImageSectionList());
		}
		LOGGER.debug("EventCreationServlet: doGet end");

	}

	public void setNavigationService(NavigationUtil navigationService) {
		this.navigationService = navigationService;
	}

}
