package com.mattel.ecomm.core.pojos;

public class EmailLightboxPojo {

	private String pagePath;

	private String genderOption;

	private String relationOption;

	private String pageKeyword;

	public String getPagePath() {
		return pagePath;
	}

	public void setPageKeyword(String pageKeyword) {
		this.pageKeyword = pageKeyword;
	}

	public String getPageKeyword() {
		return pageKeyword;
	}

	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}

	public String getGenderOption() {
		return genderOption;
	}

	public void setGenderOption(String genderOption) {
		this.genderOption = genderOption;
	}

	public String getRelationOption() {
		return relationOption;
	}

	public void setRelationOption(String relationOption) {
		this.relationOption = relationOption;
	}

}