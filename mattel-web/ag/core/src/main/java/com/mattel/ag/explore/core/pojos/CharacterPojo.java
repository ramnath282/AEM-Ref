package com.mattel.ag.explore.core.pojos;

public class CharacterPojo {

	private String imagelink;
	private boolean external;
	private String heading;

	public String getImagelink() {
		return imagelink;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public void setImagelink(String imagelink) {
		this.imagelink = imagelink;
	}

	public boolean isExternal() {
		return external;
	}

	public void setExternal(boolean external) {
		this.external = external;
	}

	@Override
	public String toString() {
		return "CharacterPojo{" + "imagelink='" + imagelink + '\'' + "heading='" + heading + '\'' + ", external='"
				+ external + '\'' + '}';
	}
}
