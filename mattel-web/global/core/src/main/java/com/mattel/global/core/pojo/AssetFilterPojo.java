package com.mattel.global.core.pojo;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssetFilterPojo {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetFilterPojo.class);
    private String filterTitle;
    private String filterValue;
    private String filterId;
    private String name;
    private String filterType;
    private List<AssetFilterPojo> children;
    
    public String getFilterType() {
      return filterType;
    }

    public void setFilterType(String filterType) {
      this.filterType = filterType;
    }

    public String getFilterTitle() {
	  return filterTitle;
    }

    public void setFilterTitle(String filterTitle) {
	  this.filterTitle = filterTitle;
    }

    public String getFilterValue() {
	  return filterValue;
    }

    public void setFilterValue(String filterValue) {
	  this.filterValue = filterValue;
    }

    public String getFilterId() {
	  return filterId;
    }

    public void setFilterId(String filterId) {
	  this.filterId = filterId;
    }
    
    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

  	public List<AssetFilterPojo> getChildren() {
      return children;
    }

    public void setChildren(List<AssetFilterPojo> children) {
      this.children = children;
    }

    public static String generateFilterId(String filterTag) {
      LOGGER.info("Start of generateFilterId method of AssetListingModel");
      String tagId = StringUtils.EMPTY;
      if(StringUtils.isNotBlank(filterTag)){
        tagId = filterTag.replace(":", "-").replace("/", "_");
        LOGGER.debug("generated tagId is {}",tagId);
      }
      LOGGER.info("Start of generateFilterId method of AssetListingModel");
      return tagId;
      }
    
  @Override
    public String toString() {
        return "AssetFilterPojo [filterTitle=" + filterTitle + ", filterValue=" + filterValue + ", filterId="
                + filterId + ", children=" +  children + "]";
    }
}
