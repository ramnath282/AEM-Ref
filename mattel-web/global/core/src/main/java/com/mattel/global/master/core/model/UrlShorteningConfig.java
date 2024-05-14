package com.mattel.global.master.core.model;

/**
 * POJO to hold Url shortening configuration.
 */
public class UrlShorteningConfig {
	private String from;
	private String to;
	private boolean removeHtmlSuffix;

	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public boolean isRemoveHtmlSuffix() {
		return removeHtmlSuffix;
	}
	public void setRemoveHtmlSuffix(boolean removeHtmlSuffix) {
		this.removeHtmlSuffix = removeHtmlSuffix;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UrlShorteningConfig [from=");
		builder.append(from);
		builder.append(", to=");
		builder.append(to);
		builder.append(", removeHtmlSuffix=");
		builder.append(removeHtmlSuffix);
		builder.append("]");
		return builder.toString();
	}	
}
