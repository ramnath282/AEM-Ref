package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter@Setter
public class BreadCrumbs {
    @JsonProperty("name")
    private String name;
    @JsonProperty("values")
    private List<Object> values = null;
}
