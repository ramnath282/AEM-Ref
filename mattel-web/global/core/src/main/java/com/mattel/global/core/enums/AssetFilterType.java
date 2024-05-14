package com.mattel.global.core.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.pojo.AssetFilterPojo;

public enum AssetFilterType {
  CHECKBOX ,
  RADIOBUTTON {
    @Override
    public List<AssetFilterPojo> modifyChildren(List<AssetFilterPojo> children , AssetFilterPojo parent) {
      final List<AssetFilterPojo> baseChildren = new ArrayList<>();
      final AssetFilterPojo allAssetFilterPojo = new AssetFilterPojo();
      
      LOGGER.debug("Adding All Option to Tag children list : {}, {}", children, parent);
      
      allAssetFilterPojo.setFilterValue(String.join(",", children.stream().map(AssetFilterPojo::getFilterValue).collect(Collectors.toList())));
      allAssetFilterPojo.setFilterId(AssetFilterPojo.generateFilterId(allAssetFilterPojo.getFilterValue()));
      allAssetFilterPojo.setFilterTitle("All " + parent.getFilterTitle());
      allAssetFilterPojo.setFilterType("ALL");
      allAssetFilterPojo.setName("All " + parent.getFilterTitle());
      baseChildren.add(allAssetFilterPojo);
      baseChildren.addAll(children);
      LOGGER.debug("Added new 'All' child: {}", allAssetFilterPojo);
      return baseChildren;
    }
  };
  
  private static final Logger LOGGER = LoggerFactory.getLogger(AssetFilterType.class);
  
  public List<AssetFilterPojo> modifyChildren(List<AssetFilterPojo> children, AssetFilterPojo parent) {
    LOGGER.trace("Modifying children : {}, {}", children, parent);
    return children;
  }
}
