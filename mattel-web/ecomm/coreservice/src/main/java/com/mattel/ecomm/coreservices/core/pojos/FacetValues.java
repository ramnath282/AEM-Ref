package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class FacetValues {
    @JsonProperty("value")
    private String value;
    @JsonProperty("selected")
    private String selected;
    @JsonProperty("count")
    private String count;
    @JsonProperty("link")
    private String link;
    @JsonProperty("undolink")
    private String undolink;
    @JsonProperty("threshold")
    private Boolean threshold;
}
