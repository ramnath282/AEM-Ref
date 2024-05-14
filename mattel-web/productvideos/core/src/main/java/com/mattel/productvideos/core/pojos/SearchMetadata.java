package com.mattel.productvideos.core.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"title", "number_of_hits", "query", "page_number", "page_size", "facets", "results"})
public class SearchMetadata {
  @JsonProperty("title")
  private String title;
  
  @JsonProperty("number_of_hits")
  private String numberOfHits;
  
  @JsonProperty("query")
  private String query;
  
  @JsonProperty("page_number")
  private String pageNumber;
  
  @JsonProperty("page_size")
  private String pageSize;
  
  @JsonProperty("facets")
  private List<String> facets = null;
  
  @JsonProperty("results")
  private List<Result> results = null;
  
  @JsonProperty("title")
  public String getTitle() {
    return this.title;
  }
  
  @JsonProperty("title")
  public void setTitle(String title) {
    this.title = title;
  }
  
  @JsonProperty("number_of_hits")
  public String getNumberOfHits() {
    return this.numberOfHits;
  }
  
  @JsonProperty("number_of_hits")
  public void setNumberOfHits(String numberOfHits) {
    this.numberOfHits = numberOfHits;
  }
  
  @JsonProperty("query")
  public String getQuery() {
    return this.query;
  }
  
  @JsonProperty("query")
  public void setQuery(String query) {
    this.query = query;
  }
  
  @JsonProperty("page_number")
  public String getPageNumber() {
    return this.pageNumber;
  }
  
  @JsonProperty("page_number")
  public void setPageNumber(String pageNumber) {
    this.pageNumber = pageNumber;
  }
  
  @JsonProperty("page_size")
  public String getPageSize() {
    return this.pageSize;
  }
  
  @JsonProperty("page_size")
  public void setPageSize(String pageSize) {
    this.pageSize = pageSize;
  }
  
  @JsonProperty("facets")
  public List<String> getFacets() {
    return this.facets;
  }
  
  @JsonProperty("facets")
  public void setFacets(List<String> facets) {
    this.facets = facets;
  }
  
  @JsonProperty("results")
  public List<Result> getResults() {
    return this.results;
  }
  
  @JsonProperty("results")
  public void setResults(List<Result> results) {
    this.results = results;
  }
}
