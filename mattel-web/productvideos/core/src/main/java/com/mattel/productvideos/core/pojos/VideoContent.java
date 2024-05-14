package com.mattel.productvideos.core.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"elements", "metadata"})
public class VideoContent {
  @JsonProperty("elements")
  private Elements elements;
  
  @JsonProperty("metadata")
  private List<Metadatum> metadata = null;
  
  @JsonProperty("elements")
  public Elements getElements() {
    return this.elements;
  }
  
  @JsonProperty("elements")
  public void setElements(Elements elements) {
    this.elements = elements;
  }
  
  @JsonProperty("metadata")
  public List<Metadatum> getMetadata() {
    return this.metadata;
  }
  
  @JsonProperty("metadata")
  public void setMetadata(List<Metadatum> metadata) {
    this.metadata = metadata;
  }
}
