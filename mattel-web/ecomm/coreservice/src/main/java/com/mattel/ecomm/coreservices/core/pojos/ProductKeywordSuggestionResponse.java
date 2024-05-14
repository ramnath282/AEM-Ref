package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductKeywordSuggestionResponse extends BaseResponse {
  private List<ProductKeywordSuggestion> suggestionView;

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("ProductKeywordSuggestionResponse [suggestionView=");
    builder.append(suggestionView);
    builder.append("]");
    return builder.toString();
  }
}
