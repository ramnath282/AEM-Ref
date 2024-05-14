package com.mattel.informational.core.pojos;

public class HrefLangPojo {
	private String locale;
	private String url;

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "HrefLangPojo [Locale" + locale + ", URL " + url + "]";
	}

}
