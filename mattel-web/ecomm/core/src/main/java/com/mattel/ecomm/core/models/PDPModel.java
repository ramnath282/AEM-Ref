package com.mattel.ecomm.core.models;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;

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
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.interfaces.ProductService;
import com.mattel.ecomm.core.pojos.ProductFeaturePojo;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PDPModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(PDPModel.class);

	@SlingObject
	private SlingHttpServletRequest request;

	@Inject
	Resource resource;
	
	@Inject
	Page currentPage;

	@Inject
	private ProductService productService;

	@Inject
	private MultifieldReader multifieldReader;

	private String productSKUId = StringUtils.EMPTY;
	private Boolean defaultValue = false;
	private Boolean socialIcons = defaultValue;
	private Boolean enableAgeGrade = defaultValue;
	private Boolean enableProductPrice = defaultValue;
	private String readMore = StringUtils.EMPTY;
	private String isCompatible = StringUtils.EMPTY;
	private String warningTitle = StringUtils.EMPTY;
	private String priceSpiderConfigID = StringUtils.EMPTY;
	private String priceSpiderMoreOptionConfigID = StringUtils.EMPTY;
	private String priceSpiderGetItOnSaleConfigID = StringUtils.EMPTY;
	private String compatibleURL = StringUtils.EMPTY;
	private String compatibleUrlTarget = StringUtils.EMPTY;
	private String toolTipDescription = StringUtils.EMPTY;
	private String ageGradeText = StringUtils.EMPTY;
	private Boolean enableCustomerReview = defaultValue;
	private String productTitle = StringUtils.EMPTY;
	private String productPrice = StringUtils.EMPTY;
	private String warningMessage = StringUtils.EMPTY;
	private String productAgeGrade = StringUtils.EMPTY;
	private String ratingAvg = StringUtils.EMPTY;
	private String ratingAvgSepByHyphen = "0";
	private String productReviewCount = StringUtils.EMPTY;
	private String analyticsRatingAvg = StringUtils.EMPTY;
	private String badge = StringUtils.EMPTY;
	private String ratingAvgrage = "ratingAvg";
	private List<ProductFeaturePojo> productDescription = null;
	private StringBuilder grs = new StringBuilder();
	private StringBuilder category = new StringBuilder();
	private StringBuilder subCategory = new StringBuilder();
	private String superCategory = StringUtils.EMPTY;
	private String subBrand = StringUtils.EMPTY;
	private String theme = StringUtils.EMPTY;
	private String allBrands = StringUtils.EMPTY;
	private String region = StringUtils.EMPTY;
	private String language = StringUtils.EMPTY;
	private String listPriceCurrency = StringUtils.EMPTY;
	private String productURL = StringUtils.EMPTY;
	private String analyticsBrand = StringUtils.EMPTY;
	private String productRecall = StringUtils.EMPTY;
	private String productRecallDateTime = StringUtils.EMPTY;
	private String productRecallMessage = StringUtils.EMPTY;
	private boolean flag;

	/**
	 * The init method to fetch the product information from Commerce
	 */
	@PostConstruct
	protected void init() {
		LOGGER.info("PDPModel init method  ----> Start");
		if (null != resource && !resource.getPath().contains("/conf/") && null != currentPage) {
			Page parentProductPage = currentPage.getAbsoluteParent(5);
			productSKUId = EcommHelper.fetchProductSKUId(request);
			LOGGER.debug("productSKUId : {}",productSKUId);
			if (StringUtils.isNotBlank(productSKUId) && null != parentProductPage) {
				getProductPropertiesBasedOnSKUId(parentProductPage);
			}
		}
		LOGGER.info("PDPModel init method  ----> end");
	}

	private void getProductPropertiesBasedOnSKUId(Page parentProductPage) {
		LOGGER.info("PDPModel getProductPropertiesBasedOnSKUId method  ----> Start");
		try {
			ValueMap productProperties = productService.fetchProductProperties(parentProductPage,
          resource, productSKUId);
			if (null != productProperties) {
				getProductProperties(productProperties);
				getParentProductPageProperties(parentProductPage);
				if (StringUtils.isNotBlank(productAgeGrade)
						&& !resource.getPath().contains("/content/experience-fragments/") && !"Yes".equals(productRecall)) {
					fetchAgeToolTipDescription(parentProductPage, productAgeGrade, request);
					fetchNodeProperties(resource);
				}
			}
		} catch (RepositoryException e) {
			LOGGER.error("PDPModel RepositoryException----> {}", e);
		}
		LOGGER.info("PDPModel getProductPropertiesBasedOnSKUId method  ----> End");
	}

	/**
	 * @param resource
	 * @throws ValueFormatException
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 */
	private void fetchNodeProperties(Resource resource) throws RepositoryException {
		LOGGER.info("fetchNodeProperties Method Start");
		Node nodeproperties = resource.adaptTo(Node.class);
		if (null != nodeproperties) {
			isCompatible = EcommHelper.checkForProperty(nodeproperties, "isCompatible");
			LOGGER.debug("isCompatible : {}",isCompatible);
			compatibleUrlTarget = EcommHelper.checkForProperty(nodeproperties, "compatibleUrlTarget");
			LOGGER.debug("compatibleUrlTarget : {}",compatibleUrlTarget);
			compatibleURL = EcommHelper.checkForProperty(nodeproperties, "compatibleURL");
			compatibleURL = EcommHelper.checkLink(compatibleURL, resource);
			LOGGER.debug("compatibleURL :: {} ", compatibleURL);
		}
		LOGGER.info("fetchNodeProperties Method End");
	}

	/**
	 * @param productPage
	 * @throws RepositoryException
	 */
	private void getParentProductPageProperties(Page parentProductPage) {
		LOGGER.info("PDPModel getParentProductPageProperties method  ----> Start");
		if (null != parentProductPage) {
			socialIcons = EcommHelper.checkBooleanProperty(parentProductPage, "socialMediaEnabled", defaultValue);
			enableAgeGrade = EcommHelper.checkBooleanProperty(parentProductPage, "ageGrade", defaultValue);
			enableProductPrice = EcommHelper.checkBooleanProperty(parentProductPage, "productPrice", defaultValue);
			enableCustomerReview = EcommHelper.checkBooleanProperty(parentProductPage, "customerReview", defaultValue);
			readMore = EcommHelper.productPageProperties(parentProductPage, "readMore");
			warningTitle = EcommHelper.productPageProperties(parentProductPage, "warning");
			LOGGER.debug("socialIcons : {}",socialIcons);
			LOGGER.debug("enableAgeGrade : {}",enableAgeGrade);
			LOGGER.debug("enableProductPrice : {}",enableProductPrice);
			LOGGER.debug("enableCustomerReview : {}",enableCustomerReview);
			LOGGER.debug("readMore : {}",readMore);
			LOGGER.debug("warningTitle : {}",warningTitle);
			priceSpiderConfigID = EcommHelper.productPageProperties(parentProductPage, "priceSpiderID");
			LOGGER.debug("priceSpiderConfigID : {}",priceSpiderConfigID);
			priceSpiderGetItOnSaleConfigID = EcommHelper.productPageProperties(parentProductPage,
					"priceSpiderGetItOnSaleConfigID");
			LOGGER.debug("priceSpiderGetItOnSaleConfigID : {}",priceSpiderGetItOnSaleConfigID);
			priceSpiderMoreOptionConfigID = EcommHelper.productPageProperties(parentProductPage,
					"priceSpiderMoreOptionConfigID");
			LOGGER.debug("priceSpiderMoreOptionConfigID : {}",priceSpiderMoreOptionConfigID);
		}
		LOGGER.info("PDPModel getParentProductPageProperties method  ----> End");
	}

	private void getProductProperties(ValueMap prodProperties) {
		LOGGER.info("PDPModel getProductProperties method  ----> Start");
		productTitle = EcommHelper.convertSpecialCharacters(EcommHelper.getValueMapNodeValue(prodProperties, "jcr:title"));
		LOGGER.debug("productTitle : {}",productTitle);
		productPrice = EcommHelper.getValueMapNodeValue(prodProperties, "price");
		LOGGER.debug("productPrice : {}",productPrice);
		badge = EcommHelper.getValueMapNodeValue(prodProperties, "badge");
		LOGGER.debug("badge : {}",badge);
		if (StringUtils.isNotBlank(EcommHelper.getValueMapNodeValue(prodProperties, ratingAvgrage))) {
			ratingAvg = EcommHelper.getValueMapNodeValue(prodProperties, ratingAvgrage);
			LOGGER.debug("ratingAvg : {}",ratingAvg);
			float rating = Float.parseFloat(ratingAvg);
			LOGGER.debug("rating : {}",rating);
			int intPartOfRating = (int)rating;
			LOGGER.debug("intPartOfRating : {}",intPartOfRating);
			int decimalPartOfRating = ((int)(rating*10)) % 10;
			LOGGER.debug("decimalPartOfRating : {}",decimalPartOfRating);
			ratingAvgSepByHyphen = decimalPartOfRating>0 ? (String.valueOf(intPartOfRating) + Constant.HYPHEN + String.valueOf(decimalPartOfRating)) : String.valueOf(intPartOfRating);
			LOGGER.debug("ratingAvgSepByHyphen : {}",ratingAvgSepByHyphen);
			analyticsRatingAvg = EcommHelper.getValueMapNodeValue(prodProperties, ratingAvgrage);
			LOGGER.debug("analyticsRatingAvg : {}",analyticsRatingAvg);
		}
		productReviewCount = EcommHelper.getValueMapNodeValue(prodProperties, "productReviewCount");
		LOGGER.debug("productReviewCount : {}",productReviewCount);
		warningMessage = EcommHelper.getValueMapNodeValue(prodProperties, "warningMessage");
		LOGGER.debug("warningMessage : {}",warningMessage);
		productAgeGrade = EcommHelper.getValueMapNodeValue(prodProperties, "ageGrade");
		LOGGER.debug("productAgeGrade : {}",productAgeGrade);
		if (prodProperties.containsKey("jcr:description")) {
			productDescription = new LinkedList<>();
			productDescription = productService.getProductFeatures(prodProperties, productDescription,
					"jcr:description");
		}
		getProductPropForAnalyticsForPDPPageLoad(prodProperties);
		getRecallPDPProperties(prodProperties);
		LOGGER.info("PDPModel getProductProperties method  ----> End");
	}

	private void getProductPropForAnalyticsForPDPPageLoad(ValueMap prodProperties) {
		LOGGER.info("PDPModel getProductPropForAnalyticsForPDPPageLoad method  ----> Start");
		getFeedArrayFromProductNode(prodProperties, grs, "grs");
		getFeedArrayFromProductNode(prodProperties, category, "category");
		getFeedArrayFromProductNode(prodProperties, subCategory, "subcategory");
		region = EcommHelper.getValueMapNodeValue(prodProperties, "region");
		language = EcommHelper.getValueMapNodeValue(prodProperties, "language");
		listPriceCurrency = EcommHelper.getValueMapNodeValue(prodProperties, "listPriceCurrency");
		productURL = EcommHelper.getValueMapNodeValue(prodProperties, "productURL");
		analyticsBrand = EcommHelper.getValueMapNodeValue(prodProperties, "brand");
		superCategory = EcommHelper.getValueMapNodeValue(prodProperties, "superCategory");
		subBrand = EcommHelper.getValueMapNodeValue(prodProperties, "subBrand");
		theme = EcommHelper.getValueMapNodeValue(prodProperties, "theme");
		allBrands = EcommHelper.getValueMapNodeValue(prodProperties, "allBrands");
		LOGGER.debug("region : {}",region);
		LOGGER.debug("language : {}",language);
		LOGGER.debug("listPriceCurrency : {}",listPriceCurrency);
		LOGGER.debug("productURL : {}",productURL);
		LOGGER.debug("analyticsBrand : {}",analyticsBrand);
		LOGGER.debug("Additional properties are --> superCategory : {}, subBrand : {}",superCategory,subBrand);
		LOGGER.debug("Few Additional properties are --> theme : {}, allBrands : {}",theme,allBrands);
		LOGGER.info("PDPModel getProductPropForAnalyticsForPDPPageLoad method  ----> End");
	}

	private void getRecallPDPProperties(ValueMap prodProperties) {
		LOGGER.info("PDPModel getRecallPDPProperties method  ----> Start");
		productRecall = EcommHelper.getValueMapNodeValue(prodProperties, "productRecall");
		if (StringUtils.isNotBlank(productRecall) && "Yes".equals(productRecall)) {
			productRecallDateTime = EcommHelper.getValueMapNodeValue(prodProperties, "productRecallDateTime");
			productRecallMessage = EcommHelper.getValueMapNodeValue(prodProperties, "productRecallMessage");
			LOGGER.debug("productRecallDateTime : {}",productRecallDateTime);
			LOGGER.debug("productRecallMessage : {}",productRecallMessage);
		}
		LOGGER.debug("productRecall : {}",productRecall);
		LOGGER.info("PDPModel getRecallPDPProperties method  ----> End");
	}

	private StringBuilder getFeedArrayFromProductNode(ValueMap prodProperties, StringBuilder prodFeedProperty,
			String nodeKey) {
    StringBuilder prodFeedPropertyNew = prodFeedProperty;
		LOGGER.info("PDPModel getFeedArrayFromProductNode method  ----> Start");
		String[] prodFeedArray = Optional.ofNullable(prodProperties.get(nodeKey, String[].class)).
		    orElse(new String [] {});

		if (!EcommHelper.isNullOrEmpty(prodFeedArray)) {
			for (int i = 0; i < prodFeedArray.length; i++) {
				if (i == prodFeedArray.length - 1) {
				  prodFeedPropertyNew = prodFeedProperty.append(prodFeedArray[i]);
				} else {
				  prodFeedPropertyNew = prodFeedProperty.append(prodFeedArray[i]).append('|');
				}
			}
		}
		LOGGER.debug("prodFeedProperty :: {} ", prodFeedPropertyNew);
		LOGGER.info("PDPModel getFeedArrayFromProductNode method  ----> End");
		return prodFeedPropertyNew;
	}
	
	/**
     * @param rootResource
     * @return ageGradeResource
     * The method find the ageGradeMapping Resource under page root path and return that resource.
     */
    private Resource getAgeGradeMappingResource(Resource rootResource) {
        LOGGER.info("PDPModel getAgeGradeMappingResource method  ----> Start");
        Iterator<Resource> childrenList = rootResource.listChildren();
        while (childrenList.hasNext() && !flag) {
            Resource childResource = childrenList.next();
            LOGGER.debug("PDPModel getAgeGradeMapping: ChildResources of root  {}", childResource);
            if (childResource.isResourceType(Constant.AGE_GRADE_MAPPING_RESOURCE_PATH)) {
                flag = true;
                LOGGER.debug("Child resource : {} ",childResource.getPath());
                return childResource;
            }
            Resource ageGradeResource = getAgeGradeMappingResource(childResource);
            if (Objects.nonNull(ageGradeResource)) {
            	   LOGGER.debug("ageGradeResource : {} ",ageGradeResource.getPath());
                return ageGradeResource;
            }
        }
        LOGGER.info("PDPModel getAgeGradeMappingResource method  ----> END");
        return null;
    }
	
	
	/**
	 * @param productPage
	 * @param productAgeGrade
	 * @param request
	 * @return
	 * @throws RepositoryException
	 */
    public void fetchAgeToolTipDescription(Page productPage, String productAgeGrade, SlingHttpServletRequest request) {
        LOGGER.info("PDPModel fetchAgeToolTipDescription method  ----> Start");
        ResourceResolver resourceResolver = request.getResourceResolver();
        Resource rootResource = resourceResolver.getResource(currentPage.getParent().getPath() + Constant.JCR_ROOT_URI);
        if (Objects.nonNull(rootResource)) {
            String ageResourcePath;
            Resource ageGradeResource = getAgeGradeMappingResource(rootResource);
            if (Objects.nonNull(ageGradeResource)) {
                ageResourcePath = ageGradeResource.getPath() + Constant.AGE_GRADE_TYPE;
            } else {
                ageResourcePath = productPage.getPath() + Constant.SLASH_JCR_CONTENT+Constant.AGE_GRADE_TYPE;
            }
            Resource parentResource = resourceResolver.getResource(ageResourcePath);
            if (null != parentResource) {
            	LOGGER.debug("PDPModel  fetchAgeToolTipDescription : Age grade mapping resource path {}", parentResource.getPath());
                Node ageToolTipNode = parentResource.adaptTo(Node.class);
                if (null != ageToolTipNode) {
                    Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(ageToolTipNode);
                    if (null != multifieldProperty) {
                        getAgeToolTipFromParentProductPageProperties(productAgeGrade, multifieldProperty);
                    }
                }
            }
        }
        LOGGER.info("PDPModel fetchAgeToolTipDescription method  ----> End");
    }

	private void getAgeToolTipFromParentProductPageProperties(String productAgeGrade,
			Map<String, ValueMap> multifieldProperty) {
		LOGGER.info("PDPModel getAgeToolTipFromParentProductPageProperties method  ----> Start");
		for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
			String ageTypes = null != entry.getValue().get("ageTypes", String.class)
					? entry.getValue().get("ageTypes", String.class)
					: "";
			LOGGER.debug("ageTypes : {} ",ageTypes);		
			if (StringUtils.isNotBlank(productAgeGrade) && StringUtils.isNotBlank(ageTypes)
					&& productAgeGrade.equals(ageTypes)) {
				ageGradeText = null != entry.getValue().get("ageGradeText", String.class)
						? entry.getValue().get("ageGradeText", String.class)
						: "";
				LOGGER.debug("ageGradeText : {} ",ageGradeText);
				toolTipDescription = null != entry.getValue().get("toolTipDescription", String.class)
						? entry.getValue().get("toolTipDescription", String.class)
						: "";
				LOGGER.debug("toolTipDescription : {} ",toolTipDescription);				
				break;
			}
		}
		LOGGER.info("PDPModel getAgeToolTipFromParentProductPageProperties method  ----> End");
	}

	public void setSlingHttpServletRequest(SlingHttpServletRequest request) {
		this.request = request;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public String getReadMore() {
		return readMore;
	}

	public Boolean getSocialIcons() {
		return socialIcons;
	}

	public String getIsCompatible() {
		return isCompatible;
	}

	public String getPriceSpiderConfigID() {
		return priceSpiderConfigID;
	}

	public void setPriceSpiderConfigID(String priceSpiderConfigID) {
		this.priceSpiderConfigID = priceSpiderConfigID;
	}

	public String getCompatibleURL() {
		return compatibleURL;
	}

	public String getCompatibleUrlTarget() {
		return compatibleUrlTarget;
	}

	public String getProductSKUId() {
		return productSKUId;
	}

	public String getToolTipDescription() {
		return toolTipDescription;
	}

	public String getPriceSpiderMoreOptionConfigID() {
		return priceSpiderMoreOptionConfigID;
	}

	public void setPriceSpiderMoreOptionConfigID(String priceSpiderMoreOptionConfigID) {
		this.priceSpiderMoreOptionConfigID = priceSpiderMoreOptionConfigID;
	}

	public String getPriceSpiderGetItOnSaleConfigID() {
		return priceSpiderGetItOnSaleConfigID;
	}

	public void setPriceSpiderGetItOnSaleConfigID(String priceSpiderGetItOnSaleConfigID) {
		this.priceSpiderGetItOnSaleConfigID = priceSpiderGetItOnSaleConfigID;
	}

	public String getAgeGradeText() {
		return ageGradeText;
	}

	public String getWarningTitle() {
		return warningTitle;
	}

	public Boolean getEnableAgeGrade() {
		return enableAgeGrade;
	}

	public Boolean getEnableProductPrice() {
		return enableProductPrice;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public String getProductReviewCount() {
		return productReviewCount;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public String getWarningMessage() {
		return warningMessage;
	}

	public String getProductAgeGrade() {
		return productAgeGrade;
	}

	public String getRatingAvg() {
		return ratingAvg;
	}

	public String getRatingAvgSepByHyphen() {
		return ratingAvgSepByHyphen;
	}

	public String getAnalyticsRatingAvg() {
		return analyticsRatingAvg;
	}

	public Boolean getEnableCustomerReview() {
		return enableCustomerReview;
	}

	public List<ProductFeaturePojo> getProductDescription() {
		return productDescription;
	}

	public String getBadge() {
		return badge;
	}

	public String getGrs() {
		return grs.toString();
	}

	public String getCategory() {
		return category.toString();
	}

	public String getSubCategory() {
		return subCategory.toString();
	}

	public String getRegion() {
		return region;
	}

	public String getLanguage() {
		return language;
	}

	public String getListPriceCurrency() {
		return listPriceCurrency;
	}

	public String getProductURL() {
		return productURL;
	}

	public String getAnalyticsBrand() {
		return analyticsBrand;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public String getProductRecall() {
		return productRecall;
	}

	public String getProductRecallDateTime() {
		return productRecallDateTime;
	}

	public String getProductRecallMessage() {
		return productRecallMessage;
	}

	public String getAllBrands() {
		return allBrands;
	}

	public String getSuperCategory() {
		return superCategory;
	}

	public String getSubBrand() {
		return subBrand;
	}

	public String getTheme() {
		return theme;
	}
	
}