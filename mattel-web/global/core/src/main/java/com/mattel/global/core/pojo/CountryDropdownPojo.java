package com.mattel.global.core.pojo;

public class CountryDropdownPojo {
	private String countryName;
	private String countrySiteUrl;

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountrySiteUrl() {
		return countrySiteUrl;
	}

	public void setCountrySiteUrl(String countrySiteUrl) {
		this.countrySiteUrl = countrySiteUrl;
	}

  @Override
  public String toString() {
    return "CountryDropdownPojo [countryName=" + countryName + ", countrySiteUrl=" + countrySiteUrl
        + "]";
  }

}
