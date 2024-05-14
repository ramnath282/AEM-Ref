package com.mattel.fisherprice.core.pojos;

import java.util.List;
import java.util.Locale;

public class ArticlePojo {

	private String id;

	private String name;

	private String categoryId;

	private String message;

	private String thumbnailUrl;

	private String value;

	private String pageUrl;

	private String vanityUrl;

	private String inventory;

	private String margin;
	
	private String categoryName;
	
	private String subCategoryName;

	private String categoryTitle;

	private String subCategoryTitle;
	
	private String categoryLink;

	private String language;

	private String region;

	private String created;

	private String lastPublished;

	private String articleAge = "-1";

	private String isPublished = "0";

	private Locale locale;

	private String mobileLink;

	private String parentPageTitle;

	private String parentPageName;

	private List<TagsPojo> primaryTag;

	private List<TagsPojo> secondaryTag;

	private String brandLinkUrl;
	
	private boolean toBeShown = Boolean.TRUE;
	
	private List<String> missingMandatoryFields;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getVanityUrl() {
		return vanityUrl;
	}

	public void setVanityUrl(String vanityUrl) {
		this.vanityUrl = vanityUrl;
	}

	public String getInventory() {
		return inventory;
	}

	public void setInventory(String inventory) {
		this.inventory = inventory;
	}

	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public String getSubCategoryTitle() {
		return subCategoryTitle;
	}

	public void setSubCategoryTitle(String subCategoryTitle) {
		this.subCategoryTitle = subCategoryTitle;
	}

	public String getCategoryLink() {
		return categoryLink;
	}

	public void setCategoryLink(String categoryLink) {
		this.categoryLink = categoryLink;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getLastPublished() {
		return lastPublished;
	}

	public void setLastPublished(String lastPublished) {
		this.lastPublished = lastPublished;
	}

	public String getArticleAge() {
		return articleAge;
	}

	public void setArticleAge(String articleAge) {
		this.articleAge = articleAge;
	}

	public String getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(String isPublished) {
		this.isPublished = isPublished;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getMobileLink() {
		return mobileLink;
	}

	public void setMobileLink(String mobileLink) {
		this.mobileLink = mobileLink;
	}

	public String getParentPageTitle() {
		return parentPageTitle;
	}

	public void setParentPageTitle(String parentPageTitle) {
		this.parentPageTitle = parentPageTitle;
	}

	public String getParentPageName() {
		return parentPageName;
	}

	public void setParentPageName(String parentPageName) {
		this.parentPageName = parentPageName;
	}

	public List<TagsPojo> getPrimaryTag() {
		return primaryTag;
	}

	public void setPrimaryTag(List<TagsPojo> primaryTag) {
		this.primaryTag = primaryTag;
	}

	public List<TagsPojo> getSecondaryTag() {
		return secondaryTag;
	}

	public void setSecondaryTag(List<TagsPojo> secondaryTag) {
		this.secondaryTag = secondaryTag;
	}

	public String getBrandLinkUrl() {
		return brandLinkUrl;
	}

	public void setBrandLinkUrl(String brandLinkUrl) {
		this.brandLinkUrl = brandLinkUrl;
	}

	public boolean isToBeShown() {
		return toBeShown;
	}

	public void setToBeShown(boolean toBeShown) {
		this.toBeShown = toBeShown;
	}

	public List<String> getMissingMandatoryFields() {
		return missingMandatoryFields;
	}

	public void setMissingMandatoryFields(List<String> missingMandatoryFields) {
		this.missingMandatoryFields = missingMandatoryFields;
	}

	@Override
	public String toString() {
		return "ArticlePojo [id=" + id + ", name=" + name + ", categoryId=" + categoryId + ", message=" + message
				+ ", thumbnailUrl=" + thumbnailUrl + ", value=" + value + ", pageUrl=" + pageUrl + ", vanityUrl="
				+ vanityUrl + ", inventory=" + inventory + ", margin=" + margin + ", categoryName=" + categoryName
				+ ", subCategoryName=" + subCategoryName + ", categoryTitle=" + categoryTitle + ", subCategoryTitle="
				+ subCategoryTitle + ", categoryLink=" + categoryLink + ", language=" + language + ", region=" + region
				+ ", created=" + created + ", lastPublished=" + lastPublished + ", articleAge=" + articleAge
				+ ", isPublished=" + isPublished + ", locale=" + locale + ", mobileLink=" + mobileLink
				+ ", parentPageTitle=" + parentPageTitle + ", parentPageName=" + parentPageName + ", primaryTag="
				+ primaryTag + ", secondaryTag=" + secondaryTag + ", brandLinkUrl=" + brandLinkUrl + ", toBeShown="
				+ toBeShown + ", missingMandatoryFields=" + missingMandatoryFields + "]";
	}
}
