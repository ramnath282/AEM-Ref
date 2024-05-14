package com.mattel.productvideos.core.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"thumbNailImage", "Video", "description","videoDuration","videoId"})
public class Elements {
  @JsonProperty("thumbNailImage")
  private PropertyPojo thumbNailImage;
  
  @JsonProperty("Video")
  private PropertyPojo video;
  
  @JsonProperty("description")
  private PropertyPojo description;
  
  @JsonProperty("videoDuration")
  private PropertyPojo videoDuration;
  
  @JsonProperty("videoId")
  private PropertyPojo videoId;
  
  @JsonProperty("thumbNailImage")
  public PropertyPojo getThumbNailImage() {
    return this.thumbNailImage;
  }
  
  @JsonProperty("thumbNailImage")
  public void setThumbNailImage(PropertyPojo thumbNailImage) {
    this.thumbNailImage = thumbNailImage;
  }
  
  @JsonProperty("Video")
  public PropertyPojo getVideo() {
    return this.video;
  }
  
  @JsonProperty("Video")
  public void setVideo(PropertyPojo video) {
    this.video = video;
  }
  
  @JsonProperty("description")
  public PropertyPojo getDescription() {
    return this.description;
  }
  
  @JsonProperty("description")
  public void setDescription(PropertyPojo description) {
    this.description = description;
  }
  
  @JsonProperty("videoDuration")
  public PropertyPojo getVideoDuration() {
    return this.videoDuration;
  }
  
  @JsonProperty("videoDuration")
  public void setVideoDuration(PropertyPojo videoDuration) {
    this.videoDuration = videoDuration;
  }
  
  @JsonProperty("videoId")
  public PropertyPojo getVideoId() {
    return this.videoId;
  }
  
  @JsonProperty("videoId")
  public void setVideoId(PropertyPojo videoId) {
    this.videoId = videoId;
  }
}
