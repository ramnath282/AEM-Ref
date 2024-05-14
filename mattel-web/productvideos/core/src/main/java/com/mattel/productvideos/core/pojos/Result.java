package com.mattel.productvideos.core.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"title", "ImageURL", "VideoURL", "description", "publish_date", "featured_video","videoDuration","videoId"})
public class Result {
  @JsonProperty("title")
  private String title;
  
  @JsonProperty("VideoURL")
  private String videoURL;
  
  @JsonProperty("ImageURL")
  private String imageURL;
  
  @JsonProperty("description")
  private String description;
  
  @JsonProperty("publish_date")
  private String publishDate;
  
  @JsonProperty("featured_video")
  private String featuredVideo;
  
  @JsonProperty("videoDuration")
  private String videoDuration;
  
  @JsonProperty("videoId")
  private String videoId;
  
  public Result(String title, String videoURL, String imageURL, String description,String publishDate,String featuredVideo,String videoDuration, String videoId){
      this.title = title;
      this.videoURL = videoURL;
      this.imageURL = imageURL;
      this.description = description;
      this.publishDate = publishDate;
      this.featuredVideo = featuredVideo;
      this.videoDuration = videoDuration;
      this.videoId = videoId;
  }
  
  public Result (){
      
  }
  
  @JsonProperty("title")
  public String getTitle() {
    return this.title;
  }
  
  @JsonProperty("title")
  public void setTitle(String title) {
    this.title = title;
  }
  
  @JsonProperty("VideoURL")
  public String getVideoURL() {
    return this.videoURL;
  }
  
  @JsonProperty("VideoURL")
  public void setVideoURL(String videoURL) {
    this.videoURL = videoURL;
  }
  
  @JsonProperty("ImageURL")
  public String getImageURL() {
    return this.imageURL;
  }
  
  @JsonProperty("ImageURL")
  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }
  
  @JsonProperty("description")
  public String getDescription() {
    return this.description;
  }
  
  @JsonProperty("description")
  public void setDescription(String description) {
    this.description = description;
  }
  
  @JsonProperty("publish_date")
  public String getPublishDate() {
    return this.publishDate;
  }
  
  @JsonProperty("publish_date")
  public void setPublishDate(String publishDate) {
    this.publishDate = publishDate;
  }
  
  @JsonProperty("featured_video")
  public String getFeaturedVideo() {
    return this.featuredVideo;
  }
  
  @JsonProperty("featured_video")
  public void setFeaturedVideo(String featuredVideo) {
    this.featuredVideo = featuredVideo;
  }
  
  @JsonProperty("videoDuration")
  public String getVideoDuration() {
    return this.videoDuration;
  }
  
  @JsonProperty("videoDuration")
  public void setVideoDuration(String videoDuration) {
    this.videoDuration = videoDuration;
  }
  
  @JsonProperty("videoId")
  public String getVideoId() {
    return this.videoId;
  }
  
  @JsonProperty("videoId")
  public void setVideoId(String videoId) {
    this.videoId = videoId;
  }
}
