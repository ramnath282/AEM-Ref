package com.mattel.productvideos.core.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"video_content"})
public class Item {
  @JsonProperty("video_content")
  private VideoContent videoContent;
  
  @JsonProperty("video_content")
  public VideoContent getVideoContent() {
    return this.videoContent;
  }
  
  @JsonProperty("video_content")
  public void setVideoContent(VideoContent videoContent) {
    this.videoContent = videoContent;
  }
}
