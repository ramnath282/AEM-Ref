package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductKeywordSuggestion {
  private String identifier;
  private List<Map<String, Object>> entry;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ProductKeywordSuggestion [identifier=");
    builder.append(identifier);
    builder.append(", entry=");
    builder.append(entry);
    builder.append("]");
    return builder.toString();
  }
}
