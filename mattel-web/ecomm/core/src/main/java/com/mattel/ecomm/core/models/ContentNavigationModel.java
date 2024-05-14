package com.mattel.ecomm.core.models;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.interfaces.ContentNavigationService;
import com.mattel.global.core.pojo.GlobalNavigationPojo;
import com.mattel.global.core.pojo.PromoImagePojo;
import com.mattel.global.core.pojo.SiteNavigationPojo;
import com.mattel.global.core.utils.NavigationUtil;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContentNavigationModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(ContentNavigationModel.class);

	@Inject
	Resource resource;
	@Inject
	@Via("resource")
	@Optional
	private NavigationUtil navigationService;
	@Inject
	@Via("resource")
	@Optional
	private ContentNavigationService contentNavigationService;

	String templateType = StringUtils.EMPTY;
	String primaryNavigationTitle = StringUtils.EMPTY;
	String[] navigationalLinks;
	String columnLayout = StringUtils.EMPTY;
	private List<SiteNavigationPojo> categorySectionNavLinks = new LinkedList<>();
	private List<SiteNavigationPojo> primaryNavLinks = new LinkedList<>();
	private List<SiteNavigationPojo> secondaryNavLinks = new LinkedList<>();
	private List<PromoImagePojo> imageSectionList = new LinkedList<>();
	private String categoryTitle = StringUtils.EMPTY;
	boolean displayShopByValue;
	private String primaryNavTitleLink = StringUtils.EMPTY;
	private String linkTargetPrimaryNav = StringUtils.EMPTY;
	private String aeForPrimaryNavTitle = StringUtils.EMPTY;
	private String imageAlignmentType = "noImage";
	private String promoHoverImgPath = StringUtils.EMPTY;

	@PostConstruct
	protected void init() throws RepositoryException {
		LOGGER.debug("ContentNavigationService init() ");
		categorySectionNavLinks.clear();
		primaryNavLinks.clear();
		secondaryNavLinks.clear();
		imageSectionList.clear();
		try {
			if (null!= resource) {
				ValueMap nodeValues = resource.adaptTo(ValueMap.class);
				if (null != nodeValues) {
					String device = "desktop";
					fetchNodePropertyValues(nodeValues);
					SiteNavigationPojo primaryNavPojo = new SiteNavigationPojo();
					primaryNavPojo = navigationService.setCategoryDetails(primaryNavPojo, resource, displayShopByValue,
							primaryNavTitleLink, primaryNavigationTitle, linkTargetPrimaryNav, device);
					navigationService.getTemplateVariationType(primaryNavPojo, templateType, aeForPrimaryNavTitle,displayShopByValue,device);

					LOGGER.debug("ContentNavigationService promoHoverImgPath {} ",promoHoverImgPath);
					navigationService.getSecondaryCategoryTitle(resource, navigationalLinks, device, categoryTitle,
							categorySectionNavLinks, secondaryNavLinks, templateType, promoHoverImgPath);

					

					if (null != secondaryNavLinks && !secondaryNavLinks.isEmpty()) {
						primaryNavPojo.setChildPageList(secondaryNavLinks);
						LOGGER.debug("ContentNavigationService primaryNavPojo.getChildPageList {} ",primaryNavPojo.getChildPageList());
					}
					LOGGER.debug("ContentNavigationService primaryNavPojo.End {} ",primaryNavPojo.getChildPageList());
					primaryNavLinks.add(primaryNavPojo);
					
					GlobalNavigationPojo cnv = new GlobalNavigationPojo();
					cnv.setTemplateType(templateType);
					cnv.setImageSectionList(imageSectionList);
					imageSectionList = contentNavigationService.fetchPromoImageListByTemplateType(resource,
							cnv);
				}
			}
		} catch (NullPointerException | RepositoryException e) {
			LOGGER.error("Null PointerException |  RepositoryException Occured {}  ", e);
		} finally {
			LOGGER.debug("end point of init() ");
		}

	}

	/**
	 * @param nodeValues
	 */
	private void fetchNodePropertyValues(ValueMap nodeValues) {
        LOGGER.info("Start fetchNodePropertyValues Method");

        templateType = EcommHelper.getValueMapNodeValue(nodeValues,  Constant.TEMPLATE_TYPE);
		LOGGER.debug("templateType :::{}",templateType);

		primaryNavigationTitle = EcommHelper.getValueMapNodeValue(nodeValues, Constant.PRIMARY_NAV_TITLE);
		primaryNavTitleLink = EcommHelper.getValueMapNodeValue(nodeValues, Constant.PRIMARY_NAV_TITLE_LINK);
		linkTargetPrimaryNav = EcommHelper.getValueMapNodeValue(nodeValues, Constant.LINK_TARGET_PRIMARY_NAV);
		aeForPrimaryNavTitle = EcommHelper.getValueMapNodeValue(nodeValues, Constant.AE_PRIMARY_NAV_TITLE);
		navigationalLinks = nodeValues.get("pages", String[].class);
		columnLayout = EcommHelper.getValueMapNodeValue(nodeValues,  Constant.COLUMN_LAYOUT);
		categoryTitle = EcommHelper.getValueMapNodeValue(nodeValues, Constant.CATEGORY_TITLE);
		displayShopByValue = nodeValues.containsKey(Constant.DISPLAY_SHOP_BY_VALUE)
				? nodeValues.get(Constant.DISPLAY_SHOP_BY_VALUE, Boolean.class)
				: Boolean.FALSE;
		if (!templateType.equals(Constant.BRAND_TEMPLATE)) {
			columnLayout = templateType;
		}
		promoHoverImgPath = EcommHelper.getValueMapNodeValue(nodeValues, Constant.CONTENTNAV_AWING_IMG);
        LOGGER.debug("fetchNodePropertyValues() promoHoverImgPath {}", promoHoverImgPath);
        LOGGER.info("End fetchNodePropertyValues Method");
	}

	public List<SiteNavigationPojo> getPrimaryNavLinks() {
		return primaryNavLinks;
	}

	public void setPrimaryNavLinks(List<SiteNavigationPojo> primaryNavLinks) {
		this.primaryNavLinks = primaryNavLinks;
	}

	public List<PromoImagePojo> getImageSectionList() {
		return imageSectionList;
	}

	public void setImageSectionList(List<PromoImagePojo> imageSectionList) {
		this.imageSectionList = imageSectionList;
	}

	public String getImageAlignmentType() {
		return imageAlignmentType;
	}

	public void setNavigationService(NavigationUtil navigationService) {
		this.navigationService = navigationService;
	}

	public void setContentNavigationServiceImpl(ContentNavigationService contentNavigationService) {
		this.contentNavigationService = contentNavigationService;
	}

}
