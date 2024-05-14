package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter @Getter
public class Menus {
    @JsonProperty("name")
    private String name;
    @JsonProperty("label")
    private String label;
    @JsonProperty("type")
    private String type;
    @JsonProperty("items")
    private List<Item> items = null;
@Setter @Getter
    class Item {
        @JsonProperty("selected")
        private Boolean selected;
        @JsonProperty("value")
        private String value;
        @JsonProperty("label")
        private String label;
        @JsonProperty("path")
        private String path;
    }
}
