package com.mattel.fisherprice.core.constants;

import org.apache.commons.lang3.StringUtils;

import com.day.cq.wcm.api.Page;

/**
 * This enum is used to get the page type for the respective template for Analytic purpose
 * @author 759808
 *
 */
public enum ParentPageType {
	HOME(Constants.HOMEPAGE_TEMPLATE,"home"), //
	CONTENT(Constants.CONTENTPAGE_TEMPLATE,"content"), //
	PRODUCT_INDEX(Constants.PDP_TEMPLATE,"Product Index"), //
	EMAIL_SIGN_UP(Constants.SUBSCRIPTION_PAGE,"Email Sign Up"), //
	ARTICLE_DETAIL_PAGE(Constants.ARTICLE_DETAILS_PAGE_TEMPLATE,"article detail"), //
	ARTICLE_CATEGORY_PAGE(Constants.CATEGORY_LANDING_PAGE_TEMPLATE,"article category"), //
	ARTICLE_LANDING_PAGE(Constants.LANDING_PAGE_TEMPLATE,"article landing"), //
	CATEGORY_INDEX(Constants.PLP_TEMPLATE,"Category Index") {
		@Override
		boolean test(String pageResourceType, Page currentPage) {
			return pageResourceType.contains(getPageTemplateType()) && !(currentPage.getPath().contains("search-results"));
		}
	}, //
	PRODUCT_SEARCH_RESULT(Constants.PLP_TEMPLATE,"Product Search Results"){
		@Override
		boolean test(String pageResourceType, Page currentPage) {
			return pageResourceType.contains(getPageTemplateType()) && currentPage.getPath().contains("search-results");
		}
	};

	String pageTemplateType;
	String pageType;

	private ParentPageType(String pageTemplateType, String pageType) {
		this.pageTemplateType = pageTemplateType;
		this.pageType = pageType;
	}

	boolean test(String pageResourceType, Page currentPage) {
		return pageResourceType.contains(getPageTemplateType());
	}
	/**
	 * This method will fetch the pageType value of the respective template
	 * @param pageResourceType
	 * @param currentPage
	 * @return
	 */
	public static String fetch(String pageResourceType, Page currentPage){
		ParentPageType[] types = ParentPageType.values();
		for (ParentPageType type: types) {
			if (type.test(pageResourceType, currentPage)) {
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
