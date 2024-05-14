package com.mattel.ecomm.coreservices.core.pojos.productdetailspojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChildPartnumberList {

    @JsonProperty("FGM98")
    private String fGM98;
    @JsonProperty("DFN61")
    private String dFN61;

}