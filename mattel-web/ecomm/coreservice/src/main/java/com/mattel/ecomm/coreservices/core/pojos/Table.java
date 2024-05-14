package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter@Getter
public class Table {
    @JsonProperty("name")
    private String name;
    @JsonProperty("results")
    private List<Object> results = null;
}
