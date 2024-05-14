package com.mattel.ecomm.core.models;

import java.util.LinkedList;
import java.util.List;

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
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.interfaces.ProductService;
import com.mattel.ecomm.core.pojos.ProductFeaturePojo;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PDPSeoModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(PDPSeoModel.class);
	@SlingObject
	private SlingHttpServletRequest request;
	@SlingObject
	private Resource resource;
	@Inject
	private ProductService productService;
	private String title = StringUtils.EMPTY;
	private String metaDescription = StringUtils.EMPTY;
	private String canonicalUrl = StringUtils.EMPTY;
	private String seoKeywords = StringUtils.EMPTY;
	private static final String PROPERTY_SEO_PAGE_TITLE = "seoPageTitle";
	private String locale = StringUtils.EMPTY;

	@PostConstruct
	protected void init() {
		LOGGER.debug("PDPSeoModel init method Start");
		if (null != resource && !resource.getPath().contains("/conf/")) {
			String productSkuId = EcommHelper.fetchProductSKUId(request);
			if (StringUtils.isNotBlank(productSkuId)) {
				if (resource.getPath().contains("/language-masters/")) {
					locale = productService.getPageLocaleFromMappings(resource.getPath(), locale);
				} else {
					locale = EcommHelper.getPageLocale(resource.getPath());
				}
				String commerceProductPath = Constant.COMMERCE_PRODUCTS_PATH + EcommHelper.getBrandName(resource)
						+ Constant.SLASH + locale + Constant.SLASH_PRODUCT_UNDERSCORE + productSkuId;
				ValueMap productProperties = productService.productProperties(commerceProductPath, resource);
				if (null != productProperties) {
					fetchSEOProperties(productProperties);
				}
			}
		}
		LOGGER.debug("PDPSeoModel init method End");
	}

	private void fetchSEOProperties(ValueMap productProperties) {
		LOGGER.debug("PDPSeoModel init method Start");
		String productTitle = EcommHelper.getValueMapNodeValue(productProperties, "jcr:title");
		String tempSeoTitle = EcommHelper.getValueMapNodeValue(productProperties, PROPERTY_SEO_PAGE_TITLE);
		title = StringUtils.isNotBlank(tempSeoTitle) ? tempSeoTitle : productTitle;
		metaDescription = EcommHelper.getValueMapNodeValue(productProperties, "seoMetaDescription");
		if (productProperties.containsKey("jcr:description") && StringUtils.isBlank(metaDescription)) {
			List<ProductFeaturePojo> productDescription = new LinkedList<>();
			productDescription = productService.getProductFeatures(productProperties, productDescription,
					"jcr:description");
			if (!productDescription.isEmpty()) {
				metaDescription = productDescription.get(0).getProdFeatureDescription();
			}
		}
		canonicalUrl = EcommHelper.checkLink(EcommHelper.getValueMapNodeValue(productProperties, "productURL"),
				resource);
		canonicalUrl = StringUtils.isBlank(canonicalUrl) ? request.getPathInfo() : canonicalUrl;
		seoKeywords = EcommHelper.checkLink(EcommHelper.getValueMapNodeValue(productProperties, "seoKeywords"),
				resource);
		LOGGER.debug("PDPSeoModel init method End");
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getCanonicalUrl() {
		return canonicalUrl;
	}

	public void setCanonicalUrl(String canonicalUrl) {
		this.canonicalUrl = canonicalUrl;
	}

	public String getSeoKeywords() {
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
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
	public String getLocale() {
		return locale;
	}
}
