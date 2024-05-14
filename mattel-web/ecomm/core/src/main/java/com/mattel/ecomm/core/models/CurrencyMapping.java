package com.mattel.ecomm.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.interfaces.CurrencyMappingService;
import com.mattel.ecomm.core.interfaces.ProductService;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CurrencyMapping {
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyMapping.class);
	@SlingObject
	private SlingHttpServletRequest request;
	@SlingObject
	private Resource resource;
	@Inject
	private ProductService productService;
	@Inject
	private CurrencyMappingService currencyMappingService;
	@Inject
	private Page currentPage;
	private String currencyType = StringUtils.EMPTY;
	private String currencyCode = StringUtils.EMPTY;

	/**
	 * The init method
	 */
	@PostConstruct
	protected void init() {
		LOGGER.debug("Currency Mapping Model init method Start");
		if (null != resource && !resource.getPath().contains("/conf/")) {
			String productSkuId = EcommHelper.fetchProductSKUId(request);
			if (StringUtils.isNotBlank(productSkuId)) {
				String locale = pageLocaleMappings();
				String commerceProductPath = Constant.COMMERCE_PRODUCTS_PATH + EcommHelper.getBrandName(resource)
						+ Constant.SLASH + locale + Constant.SLASH_PRODUCT_UNDERSCORE + productSkuId;
				ValueMap productProperties = productService.productProperties(commerceProductPath, resource);
				if (null != productProperties) {
					currencyType = EcommHelper.getValueMapNodeValue(productProperties, "listPriceCurrency");
					if (StringUtils.isNotBlank(currencyType)) {
						currencyCode = currencyMappingService.getCurrencyDetails(currencyType, resource);
					} else {
						currencyCode = "&#36;";
					}

				}
			}
		}
		LOGGER.debug("Currency Mapping Model init method End");
	}

	/**
	 * @return Locale of current Page
	 */
	private String pageLocaleMappings(){
		String locale = StringUtils.EMPTY;
		if (resource.getPath().contains("/language-masters/")) {
			locale = productService.getPageLocaleFromMappings(resource.getPath(), locale);
		} else if (resource.getPath().contains(Constant.EX_FRAGMENTS)) {
			locale = EcommHelper.getPageLocale(currentPage.getPath());
		} else {
			locale = EcommHelper.getPageLocale(resource.getPath());
		}

		return locale;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setRequest(SlingHttpServletRequest request) {
		this.request = request;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setCurrencyMappingService(CurrencyMappingService currencyMappingService) {
		this.currencyMappingService = currencyMappingService;
	}

}
