package com.mattel.ecomm.core.models;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.interfaces.GetProductTypeService;
import com.mattel.ecomm.core.interfaces.MarketingContentProviderService;
import com.mattel.ecomm.core.interfaces.StoreInterest;
import com.mattel.ecomm.core.pojos.shopify.ProductUIResponse;
import com.mattel.ecomm.core.utils.EcomUtil;
import com.mattel.ecomm.core.utils.EcommConfigurationUtils;
import com.mattel.ecomm.core.utils.ProductSchemaBuilder;
import com.mattel.ecomm.core.utils.shopify.ProductUIAdapter;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAvailability;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Core;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Product;
import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Model(adaptables = { Resource.class,
    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductInfoModel {

  @Inject
  Resource resource;

  @Inject
  ProductAvailability productAvailability;

  @Inject
  StoreInterest storeInterest;
  @SlingObject
  SlingHttpServletRequest request;

  @Inject
  PropertyReaderService propertyReaderService;

  @Inject
  GetProductTypeService getProductTypeService;

  ProductUIResponse productUIResponse;

  private String affirmInEligibleKey;
  private String bazarVoicePassKey;
  private String canonicalUrl;
  private String atPropertyTarget;
  private String affirmInfoPagePath;

  @Inject
  MarketingContentProviderService marketingContentProviderService;

  private String productSeoSchema;

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductInfoModel.class);

  @PostConstruct
  protected void init() {
    final long startTime = System.currentTimeMillis();
    String affirmInfoPageProperty = StringUtils.EMPTY;
    ProductInfoModel.LOGGER.info("ProductInfoModel Init Start");

    try {
      if(resource != null) {
        //Get valuemap properties from resource
        ValueMap properties = resource.adaptTo(ValueMap.class);
        if(properties != null) {
          affirmInfoPageProperty = properties.get("affirmLearnMoreLink", String.class);
          LOGGER.debug("affirmInfoPageProperty in ProductInfoModel is {}", affirmInfoPageProperty);
        }
      }
      final String[] selectors = request.getRequestPathInfo().getSelectors();
      final String siteKey;
      final String partNumber;

      if (selectors.length >= 2) {
        siteKey = selectors[0];
        partNumber = selectors[1];

        if (!StringUtils.isEmpty(siteKey) && !StringUtils.isEmpty(partNumber)) {
          final Map<String, Object> requestMap = new HashMap<>();
          final ProductServiceResponse productServiceResponse;
          final Map<String, String> seoPropertiesMap = getProductTypeService.getProductType(partNumber, siteKey);
          final long apiStartTime;

          ProductInfoModel.LOGGER.debug("Domain and Store selector : {}", siteKey);
          ProductInfoModel.LOGGER.debug("Part Number : {}", partNumber);
          requestMap.put(Constant.STORE_KEY, siteKey);
          requestMap.put(Constant.DOMAIN_KEY, siteKey);
          requestMap.put(Constant.PART_NUMBER, partNumber.toLowerCase());
          apiStartTime = System.currentTimeMillis();
          productServiceResponse = productAvailability.fetch(requestMap);
          ProductInfoModel.LOGGER.debug("ProductInfoModel - delta time product api :{}",
              System.currentTimeMillis() - apiStartTime);
          formatProduct(productServiceResponse);
          getBazaarVoiceKeyValue(siteKey);
          canonicalUrl = EcomUtil.buildCanonicalTag(seoPropertiesMap, request);
          affirmInfoPagePath = EcomUtil.getPageLink(affirmInfoPageProperty,resource);
          productUIResponse.setCanonicalUrl(canonicalUrl);
          productUIResponse.setAffirmInfoPagePath(affirmInfoPagePath);
          buildProductSeoSchema(productUIResponse);
          ProductInfoModel.LOGGER.debug("Product : {}, detailed response : {}",
              new Object[] { partNumber, productUIResponse });
          ProductInfoModel.LOGGER.info("ProductInfoModel Init End");
        }
      } else {
        ProductInfoModel.LOGGER.error("Invalid product details request encountered");
        productUIResponse = new ProductUIResponse();
      }
    } catch (final RuntimeException | JSONException e) {
      ProductInfoModel.LOGGER.error("Unknown exception occured while building product details model", e);
      productUIResponse = new ProductUIResponse();
    } catch (final ServiceException e) {
      ProductInfoModel.LOGGER.error("Internal service failure encountered", e);
      productUIResponse = new ProductUIResponse();
    }

    ProductInfoModel.LOGGER.debug("ProductInfoModel - delta time init method :{}",
        System.currentTimeMillis() - startTime);
  }

  private void buildProductSeoSchema(ProductUIResponse productUIResponse) {
    try {
      ProductInfoModel.LOGGER.debug("Building product seo schema for porduct: {}", productUIResponse);
      productSeoSchema = ProductSchemaBuilder.buildCompositeProductSchema(productUIResponse);
    } catch (final Exception e) {
      ProductInfoModel.LOGGER.error("Exception occured while building product seo schema", e);
    }
  }

  private void formatProduct(ProductServiceResponse productServiceResponse) throws ServiceException, JSONException {
    ProductInfoModel.LOGGER.info("ProductInfoModel formatProduct Start");
    final Product product = productServiceResponse.getProduct();

    if (Objects.nonNull(product)) {
      productUIResponse = ProductUIAdapter.transformProduct(product, null);
      setRetailStoreToResponse(productUIResponse);
      setAffirmInEligiblity(product);
      setAffirmLink();
      setAtTargetLink();
    }
    ProductInfoModel.LOGGER.info("ProductInfoModel formatProduct End");
  }

  private void setRetailStoreToResponse(ProductUIResponse productUIResponse) throws ServiceException, JSONException {
    ProductInfoModel.LOGGER.info("ProductInfoModel setRetailStoreToResponse Start");
    final Map<String, String[]> storeMap = new LinkedHashMap<>();
    final JSONArray retailStores = storeInterest.getStoreInterest();
    if (retailStores != null) {
      for (int i = 0; i < retailStores.length(); i++) {
        final String storeName = retailStores.getString(i);
        final String[] storeNameKey = storeName.split(":");
        final String[] valueList = new String[2];
        valueList[0] = storeNameKey[0];
        valueList[1] = storeNameKey[2];
        storeMap.put(storeNameKey[1], valueList);
        ProductInfoModel.LOGGER.debug("Retail Stores Name : {}", storeName);
      }
    }
    productUIResponse.setStoreMap(storeMap);
    ProductInfoModel.LOGGER.info("ProductInfoModel setRetailStoreToResponse End");
  }

  private void setAffirmInEligiblity(Product product) {
    final Core core = product.getCore();
    ProductInfoModel.LOGGER.info("ProductInfoModel setAffirmEligibleKey Start");
    affirmInEligibleKey = "";
    if (Objects.nonNull(core) && Objects.nonNull(core.getProduct_affirmIneligible())) {
      affirmInEligibleKey = core.getProduct_affirmIneligible();
    }
    ProductInfoModel.LOGGER.debug("Affirm InEligible Key : {}", affirmInEligibleKey);
    ProductInfoModel.LOGGER.info("ProductInfoModel setAffirmEligibleKey End");

  }

  private void setAffirmLink() {
    ProductInfoModel.LOGGER.info("ProductInfoModel setAffirmLink Start");
    ProductInfoModel.LOGGER.info("ProductInfoModel setAffirmLink End");
  }

  private void setAtTargetLink() {
    ProductInfoModel.LOGGER.info("ProductInfoModel setAtTargetLink Start");
    atPropertyTarget = EcommConfigurationUtils.getAtPropertyTarget();
    ProductInfoModel.LOGGER.info("ProductInfoModel setAtTargetLink End ");
  }

  private void getBazaarVoiceKeyValue(String siteKey) {
    ProductInfoModel.LOGGER.info("ProductInfoModel getBazaarVoiceKeyValue Start");
    bazarVoicePassKey = propertyReaderService.getBvPassKey(siteKey);
    ProductInfoModel.LOGGER.debug("bazarVoicePassKey is {}", bazarVoicePassKey);
    ProductInfoModel.LOGGER.info("ProductInfoModel getBazaarVoiceKeyValue End");
  }

  public ProductUIResponse getProductUIResponse() {
    return productUIResponse;
  }

  public String getAffirmInEligibleKey() {
    return affirmInEligibleKey;
  }

  public String getBazarVoicePassKey() {
    return bazarVoicePassKey;
  }

  public void setBazarVoicePassKey(String bazarVoicePassKey) {
    this.bazarVoicePassKey = bazarVoicePassKey;
  }

  public String getCanonicalUrl() {
    return canonicalUrl;
  }

  public String getAtPropertyTarget() {
    return atPropertyTarget;
  }

  public String getProductSeoSchema() {
    return productSeoSchema;
  }

  public void setProductSeoSchema(String productSeoSchema) {
    this.productSeoSchema = productSeoSchema;
  }

  public String getAffirmInfoPagePath() {
    return affirmInfoPagePath;
  }
}
