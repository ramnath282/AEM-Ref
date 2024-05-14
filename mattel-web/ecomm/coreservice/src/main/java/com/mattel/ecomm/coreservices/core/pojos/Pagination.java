package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter @Getter
public class Pagination {
    @JsonProperty("name")
    private String name;
    @JsonProperty("previous")
    private String previous;
    @JsonProperty("next")
    private String next;
    @JsonProperty("last")
    private String last;
    @JsonProperty("viewall")
    private String viewall;
    @JsonProperty("pages")
    private List<Page> pages = null;
    @Setter @Getter
    class Page {
        @JsonProperty("page")
        private String pageurl;
        @JsonProperty("link")
        private String link;
        @JsonProperty("selected")
        private String selected;
    }
}
