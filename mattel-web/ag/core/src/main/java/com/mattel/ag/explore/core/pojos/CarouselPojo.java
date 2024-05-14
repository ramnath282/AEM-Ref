package com.mattel.ag.explore.core.pojos;

public class CarouselPojo {

	private String imgUrl;

	private String imageAlttext;

	private String description;

	private String slideScroll;

	private String centerMode;

	private String autoPlay;

	private String arrows;

	private String slideShow;

	private String dots;

	private boolean external;

	public boolean isExternal() {
		return external;
	}

	public void setExternal(boolean external) {
		this.external = external;
	}

	public String getDots() {
		return dots;
	}

	public void setDots(String dots) {
		this.dots = dots;
	}

	public String getSlideShow() {
		return slideShow;
	}

	public void setSlideShow(String slideShow) {
		this.slideShow = slideShow;
	}

	public String getSlideScroll() {
		return slideScroll;
	}

	public void setSlideScroll(String slideScroll) {
		this.slideScroll = slideScroll;
	}

	public String getCenterMode() {
		return centerMode;
	}

	public void setCenterMode(String centerMode) {
		this.centerMode = centerMode;
	}

	public String getAutoPlay() {
		return autoPlay;
	}

	public void setAutoPlay(String autoPlay) {
		this.autoPlay = autoPlay;
	}

	public String getArrows() {
		return arrows;
	}

	public void setArrows(String arrows) {
		this.arrows = arrows;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImageAlttext() {
		return imageAlttext;
	}

	public void setImageAlttext(String imageAlttext) {
		this.imageAlttext = imageAlttext;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
