package com.mattel.ecomm.core.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.interfaces.ProductService;
import com.mattel.ecomm.core.pojos.ProductFeaturePojo;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductAccordionFeedModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductAccordionFeedModel.class);

	@SlingObject
	private SlingHttpServletRequest request;

	@Inject
	Resource resource;

	@Inject
	@Optional
	@Via("resource")
	private Node productAccordian;

	@Inject
	private ProductService productService;

	@Inject
	private MultifieldReader multifieldReader;

	private List<ProductFeaturePojo> productPropertiesList = null;

	@PostConstruct
	protected void init() {
		LOGGER.info("ProductAccordionFeedModel init() --> start ");
	}

	/**
	 * @param productProperties
	 * @throws JSONException
	 */
	private void getNodeValueFromMultifieldValue(ValueMap productProperties) {

		if (null != productAccordian && null != resource) {
			LOGGER.info("getNodeValueFromMultifieldValue start here ---");
			Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(productAccordian);
			productPropertiesList = new LinkedList<>();
			productPropertiesList.clear();
			for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
				String productAccordTitle = entry.getValue().get("productAccordTitle", String.class);
				LOGGER.info("productAccordTitle ---{}", productAccordTitle);
				if (StringUtils.isNotBlank(productAccordTitle)) {
					productPropertiesList = productService.getProductFeatures(productProperties, productPropertiesList,
							productAccordTitle);
				}
			}
			LOGGER.info("getNodeValueFromMultifieldValue ends here ---");
		}
	}

	public List<ProductFeaturePojo> getProductPropertiesList() {
		if (null != resource && !resource.getPath().contains("/conf/")) {
			LOGGER.info("ProductFeedModel getProductPropertiesList method  ----> Start");
			String productSKUId = EcommHelper.fetchProductSKUId(request);
			if (StringUtils.isNotBlank(productSKUId)) {
				String pageLocale = StringUtils.EMPTY;
				if (resource.getPath().contains("/language-masters/")) {
					pageLocale = productService.getPageLocaleFromMappings(resource.getPath(), pageLocale);
				} else {
					pageLocale = EcommHelper.getPageLocale(resource.getPath());
				}
				String commerceProductPath = Constant.COMMERCE_PRODUCTS_PATH + EcommHelper.getBrandName(resource)
						+ Constant.SLASH + pageLocale + "/product_" + productSKUId;
				ValueMap productProperties = productService.productProperties(commerceProductPath, resource);
				if (null != productProperties) {
					getNodeValueFromMultifieldValue(productProperties);
				}
			}
		}
		return productPropertiesList;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

}
