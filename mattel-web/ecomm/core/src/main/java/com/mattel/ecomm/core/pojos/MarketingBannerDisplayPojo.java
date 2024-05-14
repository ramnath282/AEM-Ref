package com.mattel.ecomm.core.pojos;

/**
 * @author CTS
 *
 */
public class MarketingBannerDisplayPojo {

  private String mediaType;
  private String promoImage;
  private String promoImageUrl;
  private String imageAltText;
  private String promoText;
  private String videoUrl;
  private String videoType;
  private String playVidInModal;
  private String autoPlayVideo;

  public String getPlayVidInModal() {
    return playVidInModal;
  }

  public void setPlayVidInModal(String playVidInModal) {
    this.playVidInModal = playVidInModal;
  }

  public String getAutoPlayVideo() {
    return autoPlayVideo;
  }

  public void setAutoPlayVideo(String autoPlayVideo) {
    this.autoPlayVideo = autoPlayVideo;
  }

  public String getMediaType() {
    return mediaType;
  }

  public void setMediaType(String mediaType) {
    this.mediaType = mediaType;
  }

  public String getPromoImage() {
    return promoImage;
  }

  public void setPromoImage(String promoImage) {
    this.promoImage = promoImage;
  }

  public String getImageAltText() {
    return imageAltText;
  }

  public void setImageAltText(String imageAltText) {
    this.imageAltText = imageAltText;
  }

  public String getPromoText() {
    return promoText;
  }

  public void setPromoText(String promoText) {
    this.promoText = promoText;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }

  public String getVideoType() {
    return videoType;
  }

  public void setVideoType(String videoType) {
    this.videoType = videoType;
  }

  public String getPromoImageUrl() {
	return promoImageUrl;
  }

  public void setPromoImageUrl(String promoImageUrl) {
	this.promoImageUrl = promoImageUrl;
  }

@Override
  public String toString() {
    return "MarketingBannerDisplayPojo [mediaType=" + mediaType + ", promoImage=" + promoImage
        + ", imageAltText=" + imageAltText + ", promoText=" + promoText + ", videoUrl="
        + videoUrl + ", videoType=" + videoType + ", promoImageUrl=" + promoImageUrl +
        "playVidInModal="+playVidInModal + "autoPlayVideo="+autoPlayVideo
        + "]";
}
}