package com.mattel.productvideos.core.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"title", "value", ":type"})
public class PropertyPojo {
  @JsonProperty("title")
  private String title;
  
  @JsonProperty("value")
  private String value;
  
  @JsonProperty(":type")
  private String type;
  
  @JsonProperty("title")
  public String getTitle() {
    return this.title;
  }
  
  @JsonProperty("title")
  public void setTitle(String title) {
    this.title = title;
  }
  
  @JsonProperty("value")
  public String getValue() {
    return this.value;
  }
  
  @JsonProperty("value")
  public void setValue(String value) {
    this.value = value;
  }
  
  @JsonProperty(":type")
  public String getType() {
    return this.type;
  }
  
  @JsonProperty(":type")
  public void setType(String type) {
    this.type = type;
  }
}
