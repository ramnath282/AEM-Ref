package com.mattel.global.core.sitemap;

import java.util.Map;

public class SiteMapGeneratorSchema {
  private Map<String, SiteConfig> schema;

  public Map<String, SiteConfig> getSchema() {
    return schema;
  }

  public void setSchema(Map<String, SiteConfig> schema) {
    this.schema = schema;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("SiteMapGeneratorSchema [schema=");
    builder.append(schema);
    builder.append("]");
    return builder.toString();
  }
}
