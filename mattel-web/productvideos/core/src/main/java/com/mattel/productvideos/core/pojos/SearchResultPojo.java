package com.mattel.productvideos.core.pojos;

public class SearchResultPojo {
  private String cqtag = "N";
  
  private String descValue = "";
  
  private String videoValue= "";
  
  private String thumbnailValue= "";
  
  private String scene7ImageUrl= "";
  
  private String scene7Url= "";
  
  private String publishedDate= "";
  
  private String assetName= "";
  
  private String videoDuration= "";
  
  private String videoId= "";
  
  public String getCqtag() {
    return cqtag;
  }

  public void setCqtag(String cqtag) {
    this.cqtag = cqtag;
  }

  public String getDescValue() {
    return descValue;
  }

  public void setDescValue(String descValue) {
    this.descValue = descValue;
  }

  public String getVideoValue() {
    return videoValue;
  }

  public void setVideoValue(String videoValue) {
    this.videoValue = videoValue;
  }

  public String getThumbnailValue() {
    return thumbnailValue;
  }

  public void setThumbnailValue(String thumbnailValue) {
    this.thumbnailValue = thumbnailValue;
  }

  public String getScene7ImageUrl() {
    return scene7ImageUrl;
  }

  public void setScene7ImageUrl(String scene7ImageUrl) {
    this.scene7ImageUrl = scene7ImageUrl;
  }

  public String getScene7Url() {
    return scene7Url;
  }

  public void setScene7Url(String scene7Url) {
    this.scene7Url = scene7Url;
  }

  public String getPublishedDate() {
    return publishedDate;
  }

  public void setPublishedDate(String publishedDate) {
    this.publishedDate = publishedDate;
  }

  public String getAssetName() {
    return assetName;
  }

  public void setAssetName(String assetName) {
    this.assetName = assetName;
  }

  public String getVideoDuration() {
    return this.videoDuration;
  }
  
  public void setVideoDuration(String videoDuration) {
    this.videoDuration = videoDuration;
  }
  
  public String getVideoId() {
    return this.videoId;
  }
  
  public void setVideoId(String videoId) {
    this.videoId = videoId;
  }
  
  @Override
  public String toString() {
      return "SearchResultPojo{" + "cqtag='" + cqtag + '\'' + "descValue='" + descValue + '\'' + ", videoValue='"
              + videoValue + '\'' + "thumbnailValue='" + thumbnailValue + '\'' + "scene7ImageUrl='" + scene7ImageUrl + 
              '\'' + "scene7Url='" + scene7Url + '\'' + "publishedDate='" + publishedDate + '\'' + "assetName='" + assetName
              + '\'' + "videoDuration='" + videoDuration + '\'' + "videoId='" + videoId + '}';
  }
}
