package com.mattel.productvideos.core.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"title", "items"})
public class Content {
  @JsonProperty("title")
  private String title;
  
  @JsonProperty("items")
  private List<Item> items = null;
  
  @JsonProperty("title")
  public String getTitle() {
    return this.title;
  }
  
  @JsonProperty("title")
  public void setTitle(String title) {
    this.title = title;
  }
  
  @JsonProperty("items")
  public List<Item> getItems() {
    return this.items;
  }
  
  @JsonProperty("items")
  public void setItems(List<Item> items) {
    this.items = items;
  }
}
