package com.mattel.ecomm.core.pojos;

public class EntityParametersPojo {

  private String entityKey;
  private String entityValue;
  private String articleId;
  private String atProperty;

  public String getArticleId() {
    return articleId;
  }

  public void setArticleId(String articleId) {
    this.articleId = articleId;
  }

  public String getAtProperty() {
    return atProperty;
  }

  public void setAtProperty(String atProperty) {
    this.atProperty = atProperty;
  }

  public String getEntityKey() {
    return entityKey;
  }

  public void setEntityKey(String entityKey) {
    this.entityKey = entityKey;
  }

  public String getEntityValue() {
    return entityValue;
  }

  public void setEntityValue(String entityValue) {
    this.entityValue = entityValue;
  }

  @Override public String toString() {
    return "EntityParametersPojo [Entity Key =" + entityKey + ", Entity Value=" + entityValue + "]";
  }
}
