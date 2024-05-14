package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter@Getter
public class SearchAndPromoteResponse extends BaseResponse {
    @JsonProperty("facets")
    private List<Facets> facets;
    @JsonProperty("resultsets")
    private List<ResultsSets> resultsSets;
    @JsonProperty("general")
    private GeneralPLPPageProperties generalPLPPageProperties;
    @JsonProperty("banners")
    private List<Banners> banners;
    @JsonProperty("menus")
    private List<Menus> menus;
    @JsonProperty("breadcrumbs")
    private List<BreadCrumbs> breadCrumbs;
    @JsonProperty("pagination")
    private List<Pagination> pagination;
    @JsonProperty("resultcount")
    private List<ResultCounts> resultcount;
@Getter@Setter
    class ResultsSets {
        @JsonProperty("name")
        private String name;
        @JsonProperty("results")
        private List<SearchResults> results;

    }
    @Setter@Getter
    class ResultCounts {
    @JsonProperty("total")
    private String total;
    @JsonProperty("pagelower")
    private String pagelower;
    @JsonProperty("pageupper")
    private String pageupper;
    }
}
