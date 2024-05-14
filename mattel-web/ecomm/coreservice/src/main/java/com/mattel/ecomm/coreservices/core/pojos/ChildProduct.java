package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildProduct extends BaseChildProduct {
  @JsonProperty
  private String parentPartnumber;
  @JsonProperty
  private String language;
  @JsonProperty
  private String region;
  @JsonProperty
  private Map<String, Object> definingAttributes;
  @JsonProperty
  private String name;
  @JsonProperty
  private double sequence = 0;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ChildProduct [parentPartnumber=");
    builder.append(parentPartnumber);
    builder.append(", language=");
    builder.append(language);
    builder.append(", region=");
    builder.append(region);
    builder.append(", definingAttributes=");
    builder.append(definingAttributes);
    builder.append(", name=");
    builder.append(name);
    builder.append(", sequence=");
    builder.append(sequence);
    builder.append(", toString()=");
    builder.append(super.toString());
    builder.append("]");
    return builder.toString();
  }
}
