package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class GeneralPLPPageProperties {
    @JsonProperty("query")
    private String query;
    @JsonProperty("total")
    private String total;
    @JsonProperty("page_lower")
    private String pageLower;
    @JsonProperty("page_upper")
    private String pageUpper;
    @JsonProperty("page_total")
    private String pageTotal;
    @JsonProperty("redirect")
    private String redirect;
    @JsonProperty("seo_search_title")
    private String seoSearchTitle;
    @JsonProperty("seo_search_description")
    private String seoSearchDescription;
    @JsonProperty("seo_search_keywords")
    private String seoSearchKeywords;
    @JsonProperty("seo_browse_title")
    private String seoBrowseTitle;
    @JsonProperty("seo_browse_description")
    private String seoBrowseDescription;
    @JsonProperty("seo_browse_keywords")
    private String seoBrowseKeywords;
    @JsonProperty("seo_item_title")
    private String seoItemTitle;
    @JsonProperty("seo_item_description")
    private String seoItemDescription;
    @JsonProperty("seo_item_keywords")
    private String seoItemKeywords;
}
