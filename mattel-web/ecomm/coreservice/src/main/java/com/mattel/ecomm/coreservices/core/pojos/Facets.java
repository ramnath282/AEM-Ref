package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter@Getter
public class Facets {
    @JsonProperty("label")
    private String label;
    @JsonProperty("long")
    private Boolean longWrapper;
    @JsonProperty("values")
    private List<FacetValues> values = null;
}
