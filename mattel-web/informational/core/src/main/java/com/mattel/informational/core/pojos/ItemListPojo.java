package com.mattel.informational.core.pojos;

public class ItemListPojo {
  private String title;
  private String description;
  private String image;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
  public String getImage() {
      return image;
  }
  
  public void setImage(String image) {
      this.image = image;
  }

  @Override
  public String toString() {
    return "ItemListPojo [title=" + title + ", description=" + description + ", image=" + image + "]";
  }

}
