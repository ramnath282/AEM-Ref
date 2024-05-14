package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter@Getter
public class SearchResults {
    @JsonProperty("prod_id")
    private String prodId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("desc")
    private String desc;
    @JsonProperty("url")
    private String url;
    @JsonProperty("auxDesc1")
    private String auxDesc1;
    @JsonProperty("availability")
    private String availability;
    @JsonProperty("backorder_date")
    private String backorderDate;
    @JsonProperty("buyable")
    private String buyable;
    @JsonProperty("category")
    private String category;
    @JsonProperty("custSegExcl")
    private String custSegExcl;
    @JsonProperty("fullimage")
    private String fullimage;
    @JsonProperty("imageLink")
    private String imageLink;
    @JsonProperty("language")
    private String language;
    @JsonProperty("pdpLink")
    private String pdpLink;
    @JsonProperty("product_status")
    private String productStatus;
    @JsonProperty("promoMsg")
    private String promoMsg;
    @JsonProperty("published")
    private String published;
    @JsonProperty("ratingAvg")
    private String ratingAvg;
    @JsonProperty("thumbnail")
    private String thumbnail;
    @JsonProperty("tables")
    private List<Table> tables = null;
}
