package com.mattel.productvideos.core.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"name", "value"})
public class Metadatum {
  @JsonProperty("name")
  private String name;
  
  @JsonProperty("value")
  private String value;
  
  @JsonProperty("name")
  public String getName() {
    return this.name;
  }
  
  @JsonProperty("name")
  public void setName(String name) {
    this.name = name;
  }
  
  @JsonProperty("value")
  public String getValue() {
    return this.value;
  }
  
  @JsonProperty("value")
  public void setValue(String value) {
    this.value = value;
  }
}
