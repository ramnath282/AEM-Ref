package com.mattel.ecomm.coreservices.core.pojos;

public class Metatag {
  private String charset;
  private String content;
  private String httpEquiv;
  private String name;

  public Metatag(String name, String content) {
    super();
    this.name = name;
    this.content = content;
  }

  public String getCharset() {
    return charset;
  }

  public String getContent() {
    return content;
  }

  public String getHttpEquiv() {
    return httpEquiv;
  }

  public String getName() {
    return name;
  }

  public void setCharset(String charset) {
    this.charset = charset;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setHttpEquiv(String httpEquiv) {
    this.httpEquiv = httpEquiv;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Metatag [charset=");
    builder.append(charset);
    builder.append(", content=");
    builder.append(content);
    builder.append(", httpEquiv=");
    builder.append(httpEquiv);
    builder.append(", name=");
    builder.append(name);
    builder.append("]");
    return builder.toString();
  }
}
