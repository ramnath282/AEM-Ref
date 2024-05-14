package com.mattel.informational.core.enums;

import org.apache.commons.lang3.StringUtils;

import com.mattel.informational.core.constants.Constants;

/**
 * This enum is used to get the page type for the respective template for Analytic purpose
 * @author CTS
 *
 */
public enum ParentPageType {
	HOME(Constants.HOMEPAGE_TEMPLATE,"home"), //
	CONTENT(Constants.CONTENTPAGE_TEMPLATE,"content");

	String pageTemplateType;
	String pageType;

	private ParentPageType(String pageTemplateType, String pageType) {
		this.pageTemplateType = pageTemplateType;
		this.pageType = pageType;
	}

	boolean test(String pageResourceType) {
		return pageResourceType.contains(getPageTemplateType());
	}
	/**
	 * This method will fetch the pageType value of the respective template
	 * @param pageResourceType
	 * @return
	 */
	public static String fetch(String pageResourceType){
		ParentPageType[] types = ParentPageType.values();
		for (ParentPageType type: types) {
			if (type.test(pageResourceType)) {
				return type.getPageType();
			}
		}
		return StringUtils.EMPTY;

	}

	public String getPageTemplateType() {
		return pageTemplateType;
	}

	public String getPageType() {
		return pageType;
	}


}
