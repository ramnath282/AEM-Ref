package com.mattel.fisherprice.core.pojos;

/**
 * @author CTS TagsPojo.
 */
public class TagsPojo {

    private String tagTitle;

    private String tagID;

    private String tagName;
    
    private String localeBasedTitle;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagTitle() {
        return tagTitle;
    }

    public void setTagTitle(String tagTitle) {
        this.tagTitle = tagTitle;
    }

    public String getTagID() {
        return tagID;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }
    
    public String getLocaleBasedTitle() {
      return localeBasedTitle;
    }

    public void setLocaleBasedTitle(String localeBasedTitle) {
      this.localeBasedTitle = localeBasedTitle;
    }

    @Override
    public String toString() {
      return "TagsPojo [tagTitle=" + tagTitle + ", tagID=" + tagID + ", tagName=" + tagName
          + ", localeBasedTitle=" + localeBasedTitle + "]";
    }
}
