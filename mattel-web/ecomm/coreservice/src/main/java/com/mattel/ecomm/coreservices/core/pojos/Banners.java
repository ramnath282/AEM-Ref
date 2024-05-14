package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Banners {
    @JsonProperty("top")
    private String top;
    @JsonProperty("bottom")
    private String bottom;
    @JsonProperty("left")
    private String left;
}
