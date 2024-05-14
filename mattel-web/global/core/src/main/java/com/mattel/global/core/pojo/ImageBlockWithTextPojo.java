package com.mattel.global.core.pojo;

public class ImageBlockWithTextPojo {

    private String imageUrl;
    private String imageAltText;
    private String title;
    private String subTitle;
    private String description;
    private String ctaLabel;
    private String ctaUrl;
    private String targetUrl;
    private String imageLink;
    private Boolean external;
	
	public Boolean getExternal() {
		return external;
	}

	public void setExternal(Boolean external) {
		this.external = external;
	}
    
    public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageAltText() {
        return imageAltText;
    }

    public void setImageAltText(String imageAltText) {
        this.imageAltText = imageAltText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCtaLabel() {
        return ctaLabel;
    }

    public void setCtaLabel(String ctaLabel) {
        this.ctaLabel = ctaLabel;
    }

    public String getCtaUrl() {
        return ctaUrl;
    }

    public void setCtaUrl(String ctaUrl) {
        this.ctaUrl = ctaUrl;
    }


    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @Override
    public String toString() {
      return "ImageBlockWithTextPojo [imageUrl=" + imageUrl + ", imageAltText=" + imageAltText
          + ", title=" + title + ", subTitle=" + subTitle + ", description=" + description
          + ", ctaLabel=" + ctaLabel + ", ctaUrl=" + ctaUrl + ", targetUrl=" + targetUrl
          + ", imageLink=" + imageLink + ", external=" + external + "]";
    }

   


}
