package com.mattel.ecomm.core.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.interfaces.ProductService;
import com.mattel.ecomm.core.pojos.ProductFeaturePojo;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductFeaturesModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductFeaturesModel.class);
	@Inject
	Resource resource;
	private String productSKUId = StringUtils.EMPTY;

	private String productFeaturesTitle = StringUtils.EMPTY;

	@SlingObject
	private SlingHttpServletRequest request;

	@Inject
	private ProductService productService;

	String productFeatureTitle = StringUtils.EMPTY;
	private List<ProductFeaturePojo> productDescription = null;
	private String region = StringUtils.EMPTY;
	private String productImage = StringUtils.EMPTY;
	private String scene7SKUID = StringUtils.EMPTY;

	@PostConstruct
	protected void init() {
		LOGGER.debug("ProductFeaturesModel init method  ----> Start");
		if (null != resource && !resource.getPath().contains("/conf/")) {
			PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
			if (null != pageManager) {
				Page page = pageManager.getContainingPage(resource);
				productSKUId = EcommHelper.fetchProductSKUId(request);
				if (StringUtils.isNotBlank(productSKUId)) {
					fetchPriopertiesBasedOnLocaleAndProductSKUID();
				}
				if (null != page) {
					fetchProductFeaturesTitle(page.getAbsoluteParent(5));
				}
			}
			LOGGER.debug("ProductFeaturesModel init method  ----> End");
		}
	}

	private void fetchPriopertiesBasedOnLocaleAndProductSKUID() {
		LOGGER.debug("ProductFeaturesModel fetchPriopertiesBasedOnLocaleAndProductSKUID method  ----> Start");
		String pageLocale = StringUtils.EMPTY;
		if (resource.getPath().contains("/language-masters/")) {
			pageLocale = productService.getPageLocaleFromMappings(resource.getPath(), pageLocale);
		} else {
			pageLocale = EcommHelper.getPageLocale(resource.getPath());
		}
		String commerceProductPath = Constant.COMMERCE_PRODUCTS_PATH + EcommHelper.getBrandName(resource) + '/'
				+ pageLocale + "/product_" + productSKUId;
		ValueMap productProperties = productService.productProperties(commerceProductPath, resource);
		if (null != productProperties) {
			getProductFeatures(productProperties);
			getProductImage(resource,commerceProductPath);
		}
		LOGGER.debug("ProductFeaturesModel fetchPriopertiesBasedOnLocaleAndProductSKUID method  ----> End");
	}

	private void getProductImage(Resource resource, String commerceProductPath) {
		ResourceResolver resolver = resource.getResourceResolver();
		Resource commerceResource = resolver.getResource(commerceProductPath);
		if (null != commerceResource) {
			Node varProductNode = commerceResource.adaptTo(Node.class);
			try {
				if(Objects.nonNull(varProductNode) && varProductNode.hasNode("assets")) {
					varProductNode = varProductNode.getNode("assets");
					if(varProductNode.hasNode("asset0"))
					{
						varProductNode = varProductNode.getNode("asset0");
						if(varProductNode.hasProperty("fileReference"))
						{
							productImage= EcommHelper.checkForProperty(varProductNode, "fileReference");
							getSceneSevenSKUID();
						}
					}
				}
			} catch (RepositoryException e) {
				LOGGER.error("ProductFeaturesModel getProductImage() RepositoryException {}",e);
			}
		}
	}

	private void getSceneSevenSKUID() {
		if (StringUtils.isNotBlank(productImage)) {
			String[] prodImageArray = productImage.split("/");
			LOGGER.info("ProductFeaturesModel getProductImage() RepositoryException {}",prodImageArray);
			if (!EcommHelper.isNullOrEmpty(prodImageArray)) {
				scene7SKUID = prodImageArray[prodImageArray.length - 1];
				LOGGER.debug("scene7SKUID :: {} ",scene7SKUID);
			}
		}
	}

	private void getProductFeatures(ValueMap productProperties) {
		LOGGER.debug("ProductFeaturesModel getProductFeatures method  ----> Start");
		if (productProperties.containsKey("jcr:description")) {
			productDescription = new LinkedList<>();
			productDescription = productService.getProductFeatures(productProperties, productDescription,
					"jcr:description");
		}
		region = EcommHelper.getValueMapNodeValue(productProperties, "region");
		LOGGER.debug("ProductFeaturesModel getProductFeatures method  ----> End");
	}

	private void fetchProductFeaturesTitle(Page parentProductPage) {
		productFeaturesTitle = null != parentProductPage.getProperties().get("productFeaturesTitle", String.class)
				? parentProductPage.getProperties().get("productFeaturesTitle", String.class)
				: "";
	}

	public String getProductSKUId() {
		return productSKUId;
	}

	public String getProductFeaturesTitle() {
		return productFeaturesTitle;
	}

	public List<ProductFeaturePojo> getProductDescription() {
		return productDescription;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public String getRegion() {
		return region;
	}

	public String getProductImage() {
		return productImage;
	}

	public String getScene7SKUID() {
		return scene7SKUID;
	}
}
