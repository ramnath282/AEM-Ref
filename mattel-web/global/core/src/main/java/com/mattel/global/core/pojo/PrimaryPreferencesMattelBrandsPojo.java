package com.mattel.global.core.pojo;

/**
 * Pojo for Primary Preferences Mattel Brands.
 */
public class PrimaryPreferencesMattelBrandsPojo {

  private String title;

  private String description;

  private String preferenceID;

  private String alwaysEnglish;

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

  public String getPreferenceID() {
    return preferenceID;
  }

  public void setPreferenceID(String preferenceID) {
    this.preferenceID = preferenceID;
  }

  public String getAlwaysEnglish() {
    return alwaysEnglish;
  }

  public void setAlwaysEnglish(String alwaysEnglish) {
    this.alwaysEnglish = alwaysEnglish;
  }

  @Override public String toString() {
    return "PrimaryPreferencesMattelBrandsPojo [title=" + title + ", description=" + description
        + ", preferenceID=" + preferenceID + ", alwaysEnglish=" + alwaysEnglish + "]";
  }

}
