package com.mattel.ag.explore.core.pojos;

/**
 * @author CTS TagsPojo.
 *
 */
public class TagsPojo {

	private String tagTitle;

	private String tagID;

	private String tagName;

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

	@Override
	public String toString() {
		return "TagsPojo [tagTitle=" + tagTitle + ", tagID=" + tagID + ", TagName=" + tagName + "]";
	}

}
