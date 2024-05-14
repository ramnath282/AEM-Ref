package com.mattel.global.core.pojo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;


public class GlobalNavigationPojo {
	String templateType = StringUtils.EMPTY;
	String primaryNavigationTitle = StringUtils.EMPTY;
	String[] navigationalLinks;
	private String promoHoverImagePath = StringUtils.EMPTY;
	String columnLayout = StringUtils.EMPTY;
	private List<SiteNavigationPojo> categorySectionNavLinks = new ArrayList<>();
	private List<SiteNavigationPojo> primaryNavLinks = new ArrayList<>();
	private List<SiteNavigationPojo> secondaryNavLinks = new ArrayList<>();
	private List<PromoImagePojo> imageSectionList = new ArrayList<>();
	private String categoryTitle = StringUtils.EMPTY;
	boolean displayShopByValue;
	private Boolean defaultBoolean = false;
	private String primaryNavTitleLink = StringUtils.EMPTY;
	private String linkTargetPrimaryNav = StringUtils.EMPTY;
	private String aeForPrimaryNavTitle = StringUtils.EMPTY;
	private String imageAlignmentType = "noImage";
	private String promoImage = "promoImage";
	private String noImage = "noImage";
	
	private List<SiteNavigationPojo> categoryNavLinks = new ArrayList<>();
	private List<SiteNavigationPojo> parentLinksList = new ArrayList<>();
	private List<SiteNavigationPojo> featuredLinksList = new ArrayList<>();
	CategoryColumnPojo columnOneDetails = new CategoryColumnPojo();
	CategoryColumnPojo columnTwoDetails = new CategoryColumnPojo();
	CategoryColumnPojo columnThreeDetails = new CategoryColumnPojo();
	CategoryColumnPojo columnFourDetails = new CategoryColumnPojo();
	String categoryTitleLink = StringUtils.EMPTY;
	String aeForCategoryTitle = StringUtils.EMPTY;
	String linkTargetCategory = StringUtils.EMPTY;
	String featuredTitle;
	String device = StringUtils.EMPTY;
	Resource resource;
	String defaultTitle = "Title Here";
	String desktop = "desktop";

	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public String getPrimaryNavigationTitle() {
		return primaryNavigationTitle;
	}
	public void setPrimaryNavigationTitle(String primaryNavigationTitle) {
		this.primaryNavigationTitle = primaryNavigationTitle;
	}
	public String[] getNavigationalLinks() {
		return navigationalLinks;
	}
	public void setNavigationalLinks(String[] navigationalLinks) {
		this.navigationalLinks = navigationalLinks;
	}
	public String getColumnLayout() {
		return columnLayout;
	}
	public void setColumnLayout(String columnLayout) {
		this.columnLayout = columnLayout;
	}
	public List<SiteNavigationPojo> getCategorySectionNavLinks() {
		return categorySectionNavLinks;
	}
	public void setCategorySectionNavLinks(List<SiteNavigationPojo> categorySectionNavLinks) {
		this.categorySectionNavLinks = categorySectionNavLinks;
	}
	public List<SiteNavigationPojo> getPrimaryNavLinks() {
		return primaryNavLinks;
	}
	public void setPrimaryNavLinks(List<SiteNavigationPojo> primaryNavLinks) {
		this.primaryNavLinks = primaryNavLinks;
	}
	public List<SiteNavigationPojo> getSecondaryNavLinks() {
		return secondaryNavLinks;
	}
	public void setSecondaryNavLinks(List<SiteNavigationPojo> secondaryNavLinks) {
		this.secondaryNavLinks = secondaryNavLinks;
	}
	public List<PromoImagePojo> getImageSectionList() {
		return imageSectionList;
	}
	public void setImageSectionList(List<PromoImagePojo> imageSectionList) {
		this.imageSectionList = imageSectionList;
	}
	public String getCategoryTitle() {
		return categoryTitle;
	}
	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}
	public boolean isDisplayShopByValue() {
		return displayShopByValue;
	}
	public void setDisplayShopByValue(boolean displayShopByValue) {
		this.displayShopByValue = displayShopByValue;
	}
	public Boolean getDefaultBoolean() {
		return defaultBoolean;
	}
	public void setDefaultBoolean(Boolean defaultBoolean) {
		this.defaultBoolean = defaultBoolean;
	}
	public String getPrimaryNavTitleLink() {
		return primaryNavTitleLink;
	}
	public void setPrimaryNavTitleLink(String primaryNavTitleLink) {
		this.primaryNavTitleLink = primaryNavTitleLink;
	}
	public String getLinkTargetPrimaryNav() {
		return linkTargetPrimaryNav;
	}
	public void setLinkTargetPrimaryNav(String linkTargetPrimaryNav) {
		this.linkTargetPrimaryNav = linkTargetPrimaryNav;
	}
	public String getAeForPrimaryNavTitle() {
		return aeForPrimaryNavTitle;
	}
	public void setAeForPrimaryNavTitle(String aeForPrimaryNavTitle) {
		this.aeForPrimaryNavTitle = aeForPrimaryNavTitle;
	}
	public String getImageAlignmentType() {
		return imageAlignmentType;
	}
	public void setImageAlignmentType(String imageAlignmentType) {
		this.imageAlignmentType = imageAlignmentType;
	}
	public String getPromoImage() {
		return promoImage;
	}
	public void setPromoImage(String promoImage) {
		this.promoImage = promoImage;
	}
	public String getNoImage() {
		return noImage;
	}
	public void setNoImage(String noImage) {
		this.noImage = noImage;
	}
	public List<SiteNavigationPojo> getCategoryNavLinks() {
		return categoryNavLinks;
	}
	public void setCategoryNavLinks(List<SiteNavigationPojo> categoryNavLinks) {
		this.categoryNavLinks = categoryNavLinks;
	}
	public List<SiteNavigationPojo> getParentLinksList() {
		return parentLinksList;
	}
	public void setParentLinksList(List<SiteNavigationPojo> parentLinksList) {
		this.parentLinksList = parentLinksList;
	}
	public List<SiteNavigationPojo> getFeaturedLinksList() {
		return featuredLinksList;
	}
	public void setFeaturedLinksList(List<SiteNavigationPojo> featuredLinksList) {
		this.featuredLinksList = featuredLinksList;
	}
	public CategoryColumnPojo getColumnOneDetails() {
		return columnOneDetails;
	}
	public void setColumnOneDetails(CategoryColumnPojo columnOneDetails) {
		this.columnOneDetails = columnOneDetails;
	}
	public CategoryColumnPojo getColumnTwoDetails() {
		return columnTwoDetails;
	}
	public void setColumnTwoDetails(CategoryColumnPojo columnTwoDetails) {
		this.columnTwoDetails = columnTwoDetails;
	}
	public CategoryColumnPojo getColumnThreeDetails() {
		return columnThreeDetails;
	}
	public void setColumnThreeDetails(CategoryColumnPojo columnThreeDetails) {
		this.columnThreeDetails = columnThreeDetails;
	}
	public CategoryColumnPojo getColumnFourDetails() {
		return columnFourDetails;
	}
	public void setColumnFourDetails(CategoryColumnPojo columnFourDetails) {
		this.columnFourDetails = columnFourDetails;
	}
	public String getCategoryTitleLink() {
		return categoryTitleLink;
	}
	public void setCategoryTitleLink(String categoryTitleLink) {
		this.categoryTitleLink = categoryTitleLink;
	}
	public String getAeForCategoryTitle() {
		return aeForCategoryTitle;
	}
	public void setAeForCategoryTitle(String aeForCategoryTitle) {
		this.aeForCategoryTitle = aeForCategoryTitle;
	}
	public String getLinkTargetCategory() {
		return linkTargetCategory;
	}
	public void setLinkTargetCategory(String linkTargetCategory) {
		this.linkTargetCategory = linkTargetCategory;
	}
	public String getFeaturedTitle() {
		return featuredTitle;
	}
	public void setFeaturedTitle(String featuredTitle) {
		this.featuredTitle = featuredTitle;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public String getDefaultTitle() {
		return defaultTitle;
	}
	public void setDefaultTitle(String defaultTitle) {
		this.defaultTitle = defaultTitle;
	}
	public String getDesktop() {
		return desktop;
	}
	public void setDesktop(String desktop) {
		this.desktop = desktop;
	}
	public String getPromoHoverImagePath() {
	    return promoHoverImagePath;
	}
	public void setPromoHoverImagePath(String promoHoverImagePath) {
	    this.promoHoverImagePath = promoHoverImagePath;
	}
}
