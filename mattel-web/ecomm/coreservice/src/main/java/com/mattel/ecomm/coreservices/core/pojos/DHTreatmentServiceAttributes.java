package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class DHTreatmentServiceAttributes {
  @JsonProperty
  private String identifier;
  @JsonProperty
  private String sequence;
  @JsonProperty
  private Boolean storeDisplay;
  @JsonProperty
  private List<Map<String,String>> values;
  @JsonProperty
  private String usage;
  @JsonProperty
  private String name;
  @JsonProperty
  private Boolean displayable;
  @JsonProperty
  private Boolean facetable;
  @JsonProperty
  private Boolean comparable;
  @JsonProperty
  private Boolean searchable;
  @JsonProperty
  private String uniqueID;
  
  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("DHServiceAttributes [identifier=");
    builder.append(identifier);
    builder.append(", sequence=");
    builder.append(sequence);
    builder.append(", values=");
    builder.append(values);
    builder.append(", usage=");
    builder.append(usage);
    builder.append(", name=");
    builder.append(name);
    builder.append(", displayable=");
    builder.append(displayable);
    builder.append(", facetable=");
    builder.append(facetable);
    builder.append(", comparable=");
    builder.append(comparable);
    builder.append(", searchable=");
    builder.append(searchable);
    builder.append(", uniqueID=");
    builder.append(uniqueID);
    builder.append("]");
    return builder.toString();
  }
}
