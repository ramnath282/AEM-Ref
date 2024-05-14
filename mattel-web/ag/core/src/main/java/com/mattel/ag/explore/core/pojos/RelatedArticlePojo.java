package com.mattel.ag.explore.core.pojos;

import java.util.List;

/**
 * @author CTS RelatedArticlePojo.
 *
 */
public class RelatedArticlePojo {

	private String articleDate;

	private String articleDisplayDate;

	private String articleCreatedDate;

	private String articleUpdatedDate;

	private String articleTitle;

	private String articleHeroImage;

	private String articleGridImage;

	private String articleLandingHero;

	private String articleDescription;

	private String articleID;

	private String displayhomepage;

	private String displaysitehome;

	private List<TagsPojo> primaryTag;

	private List<TagsPojo> secondaryTag;

	private List<TagsPojo> cqTag;

	private String enableSocialMediaSharing;

	private String pagePath;

	private String keywords;

	private boolean hideArticleDate;

	private String vanityUrl;

	public String getArticleCreatedDate() {
		return articleCreatedDate;
	}

	public void setArticleCreatedDate(String articleCreatedDate) {
		this.articleCreatedDate = articleCreatedDate;
	}

	public String getArticleUpdatedDate() {
		return articleUpdatedDate;
	}

	public void setArticleUpdatedDate(String articleUpdatedDate) {
		this.articleUpdatedDate = articleUpdatedDate;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getArticleDate() {
		return articleDate;
	}

	public void setArticleDate(String articleDate) {
		this.articleDate = articleDate;
	}

	public List<TagsPojo> getCqTag() {
		return cqTag;
	}

	public void setCqTag(List<TagsPojo> cqTag) {
		this.cqTag = cqTag;
	}

	public String getArticleDisplayDate() {
		return articleDisplayDate;
	}

	public String getDisplayhomepage() {
		return displayhomepage;
	}

	public void setDisplayhomepage(String displayhomepage) {
		this.displayhomepage = displayhomepage;
	}

	public String getDisplaysitehome() {
		return displaysitehome;
	}

	public void setDisplaysitehome(String displaysitehome) {
		this.displaysitehome = displaysitehome;
	}

	public void setArticleDisplayDate(String articleDisplayDate) {
		this.articleDisplayDate = articleDisplayDate;
	}

	public void setArticleHeroImage(String articleHeroImage) {
		this.articleHeroImage = articleHeroImage;
	}

	public String getArticleHeroImage() {
		return articleHeroImage;
	}

	public String getArticleID() {
		return articleID;
	}

	public void setArticleID(String articleID) {
		this.articleID = articleID;
	}

	public void setArticleGridImage(String articleGridImage) {
		this.articleGridImage = articleGridImage;
	}

	public String getArticleGridImage() {
		return articleGridImage;
	}

	public void setArticleLandingHero(String articleLandingHero) {
		this.articleLandingHero = articleLandingHero;
	}

	public String getArticleLandingHero() {
		return articleLandingHero;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getPagePath() {
		return pagePath;
	}

	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}

	public String getArticleDescription() {
		return articleDescription;
	}

	public void setArticleDescription(String articleDescription) {
		this.articleDescription = articleDescription;
	}

	public String getEnableSocialMediaSharing() {
		return enableSocialMediaSharing;
	}

	public void setEnableSocialMediaSharing(String enableSocialMediaSharing) {
		this.enableSocialMediaSharing = enableSocialMediaSharing;
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

	public boolean isHideArticleDate() {
		return hideArticleDate;
	}

	public void setHideArticleDate(boolean hideArticleDate) {
		this.hideArticleDate = hideArticleDate;
	}

	public void setVanityUrl(String vanityUrl) {
		this.vanityUrl = vanityUrl;
	}

	public String getVanityUrl() {
		return vanityUrl;
	}

	@Override
	public String toString() {
		return "RelatedArticlePojo{" +
				"articleDate='" + articleDate + '\'' +
				", articleDisplayDate='" + articleDisplayDate + '\'' +
				", articleCreatedDate='" + articleCreatedDate + '\'' +
				", articleUpdatedDate='" + articleUpdatedDate + '\'' +
				", articleTitle='" + articleTitle + '\'' +
				", articleHeroImage='" + articleHeroImage + '\'' +
				", articleGridImage='" + articleGridImage + '\'' +
				", articleLandingHero='" + articleLandingHero + '\'' +
				", articleDescription='" + articleDescription + '\'' +
				", articleID='" + articleID + '\'' +
				", displayhomepage='" + displayhomepage + '\'' +
				", displaysitehome='" + displaysitehome + '\'' +
				", primaryTag=" + primaryTag +
				", secondaryTag=" + secondaryTag +
				", cqTag=" + cqTag +
				", enableSocialMediaSharing='" + enableSocialMediaSharing + '\'' +
				", pagePath='" + pagePath + '\'' +
				", keywords='" + keywords + '\'' +
				", hideArticleDate=" + hideArticleDate +
				", vanityUrl='" + vanityUrl + '\'' +
				'}';
	}
}
