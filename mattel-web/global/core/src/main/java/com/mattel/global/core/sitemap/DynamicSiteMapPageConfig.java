package com.mattel.global.core.sitemap;

import java.util.List;

public class DynamicSiteMapPageConfig extends BaseSiteMapConfig {
  private DataSourceType dataSourceType;
  private String repoPath;
  private String filePath;
  private String nodePrimaryId;
  private List<String> excludeNodes;
  private String lastModifiedProperty;
  private String excludeProperty;
  private String isActiveProperty;

  public String getExcludeProperty() {
    return excludeProperty;
  }

  public void setExcludeProperty(String excludeProperty) {
    this.excludeProperty = excludeProperty;
  }

  public String getLastModifiedProperty() {
    return lastModifiedProperty;
  }

  public void setLastModifiedProperty(String lastModifiedProperty) {
    this.lastModifiedProperty = lastModifiedProperty;
  }

  public DataSourceType getDataSourceType() {
    return dataSourceType;
  }

  public void setDataSourceType(DataSourceType dataSourceType) {
    this.dataSourceType = dataSourceType;
  }

  public String getRepoPath() {
    return repoPath;
  }

  public void setRepoPath(String repoPath) {
    this.repoPath = repoPath;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String getIsActiveProperty() {
    return isActiveProperty;
  }

  public void setIsActiveProperty(String isActiveProperty) {
    this.isActiveProperty = isActiveProperty;
  }
  
  public String getNodePrimaryId() {
    return nodePrimaryId;
  }

  public void setNodePrimaryId(String nodePrimaryId) {
    this.nodePrimaryId = nodePrimaryId;
  }

  public List<String> getExcludeNodes() {
    return excludeNodes;
  }

  public void setExcludeNodes(List<String> excludeNodes) {
    this.excludeNodes = excludeNodes;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("DynamicSiteMapPageConfig [dataSourceType=");
    builder.append(dataSourceType);
    builder.append(", repoPath=");
    builder.append(repoPath);
    builder.append(", filePath=");
    builder.append(filePath);
    builder.append(", nodePrimaryId=");
    builder.append(nodePrimaryId);
    builder.append(", excludeNodes=");
    builder.append(excludeNodes);
    builder.append(", lastModifiedProperty=");
    builder.append(lastModifiedProperty);
    builder.append(", excludeProperty=");
    builder.append(excludeProperty);
    builder.append(", isActiveProperty=");
    builder.append(isActiveProperty);
    builder.append(", isExtensionlessUrls()=");
    builder.append(isExtensionlessUrls());
    builder.append(", isIncludeLastModified()=");
    builder.append(isIncludeLastModified());
    builder.append(", getDefaultPriority()=");
    builder.append(getDefaultPriority());
    builder.append(", getStartsWith()=");
    builder.append(getStartsWith());
    builder.append(", getEndsWith()=");
    builder.append(getEndsWith());
    builder.append("]");
    return builder.toString();
  }
}
