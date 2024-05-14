package com.mattel.ecomm.core.models;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
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
import com.mattel.ecomm.core.pojos.PDPProductUIResponse;
import com.mattel.ecomm.core.utils.EcomUtil;
import com.mattel.ecomm.core.utils.EcommConfigurationUtils;
import com.mattel.ecomm.core.utils.ProductSchemaBuilder;
import com.mattel.ecomm.core.utils.ProductUIAdapter;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.PDPProduct;
import com.mattel.ecomm.coreservices.core.pojos.PDPResponse;
import com.mattel.ecomm.coreservices.core.pojos.Product;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PDPProductModel {
  @Inject
  PDPProduct pdpProduct;

  @Inject
  StoreInterest storeInterest;

  @SlingObject
  SlingHttpServletRequest request;

  @Inject
  PropertyReaderService propertyReaderService;

  @Inject
  GetProductTypeService getProductTypeService;

  PDPProductUIResponse pdpProductUIResponse;

  private String affirmInEligibleKey;
  private String bazarVoicePassKey;
  private String canonicalUrl;
  private String atPropertyTarget;

  @Inject
  MarketingContentProviderService marketingContentProviderService;

  private String productSeoSchema;

  private static final Logger LOGGER = LoggerFactory.getLogger(PDPProductModel.class);

  @PostConstruct
  protected void init() {
    PDPProductModel.LOGGER.info("PDPProductModel Init Start");

    try {
      final String[] selectors = request.getRequestPathInfo().getSelectors();
      final String siteKey;
      final String partNumber;

      if (selectors.length >= 2) {
        siteKey = selectors[0];
        partNumber = selectors[1];

        if(!StringUtils.isEmpty(siteKey) && !StringUtils.isEmpty(partNumber)) {
        final Map<String, Object> requestMap = new HashMap<>();
        final PDPResponse pdpResponse;
        final Map<String, String> seoPropertiesMap = getProductTypeService
            .getProductType(partNumber, siteKey);
        PDPProductModel.LOGGER.debug("Domain and Store selector : {}", siteKey);
        PDPProductModel.LOGGER.debug("Part Number : {}", partNumber);
        requestMap.put(Constant.STORE_KEY, siteKey);
        requestMap.put(Constant.DOMAIN_KEY, siteKey);
        requestMap.put(Constant.PART_NUMBER, partNumber);
        pdpResponse = pdpProduct.fetch(requestMap);
        formatProduct(pdpResponse);
        getBazaarVoiceKeyValue(siteKey);
        canonicalUrl = EcomUtil.buildCanonicalTag(seoPropertiesMap, request);
        pdpProductUIResponse.setCanonicalUrl(canonicalUrl);
        buildProductSeoSchema(pdpProductUIResponse);
        PDPProductModel.LOGGER.debug("Product : {}, detailed response : {}",
            new Object[] { partNumber, pdpProductUIResponse });
        PDPProductModel.LOGGER.info("PDPProductModel Init End");
        }
      } else {
        PDPProductModel.LOGGER.error("Invalid product details request encountered");
        pdpProductUIResponse = new PDPProductUIResponse();
      }
    } catch (final RuntimeException | JSONException e) {
      PDPProductModel.LOGGER.error("Unknown exception occured while building product details model",
          e);
      pdpProductUIResponse = new PDPProductUIResponse();
    } catch (final ServiceException e) {
      PDPProductModel.LOGGER.error("Internal service failure encountered", e);
      pdpProductUIResponse = new PDPProductUIResponse();
    }
  }

  private void buildProductSeoSchema(PDPProductUIResponse pdpProductUIResponse) {
    try {
      PDPProductModel.LOGGER.debug("Building product seo schema for porduct: {}",
          pdpProductUIResponse);
      productSeoSchema = ProductSchemaBuilder.buildCompositeProductSchema(pdpProductUIResponse);
    } catch (final IOException e) {
      PDPProductModel.LOGGER.error("Exception occured while building product seo schema", e);
    }
  }

  private void formatProduct(PDPResponse pdpResponse)
      throws ServiceException, JSONException {
    PDPProductModel.LOGGER.info("PDPProductModel formatProduct Start");
    final Map<String, Product> catalogView = pdpResponse.getCatalogEntryView().get(0);
    final Product product = catalogView.get(Constant.PRODUCT);
    if (Objects.nonNull(product)) {
      pdpProductUIResponse = ProductUIAdapter.transformProductToSignleSKU(product,
          null);
      setRetailStoreToResponse(pdpProductUIResponse);
      setAffirmInEligiblity(product);
      setAffirmLink();
      setAtTargetLink();
    }
    PDPProductModel.LOGGER.info("PDPProductModel formatProduct End");
  }

  private void setAffirmInEligiblity(Product product) {
    PDPProductModel.LOGGER.info("PDPProductModel setAffirmEligibleKey Start");
    affirmInEligibleKey = "";
    if (Objects.nonNull(product.getProductType())
        && Objects.nonNull(product.getAffirmIneligible())) {
      affirmInEligibleKey = product.getAffirmIneligible();
    }
    PDPProductModel.LOGGER.debug("Affirm InEligible Key : {}", affirmInEligibleKey);
    PDPProductModel.LOGGER.info("PDPProductModel setAffirmEligibleKey End");
  }

  private void setAffirmLink() {
    PDPProductModel.LOGGER.info("PDPProductModel setAffirmLink Start");
    PDPProductModel.LOGGER.info("PDPProductModel setAffirmLink End");
  }

  private void setAtTargetLink() {
    PDPProductModel.LOGGER.info("PDPProductModel setAtTargetLink Start");
    atPropertyTarget = EcommConfigurationUtils.getAtPropertyTarget();
    PDPProductModel.LOGGER.info("PDPProductModel setAtTargetLink End ");
  }

  private void setRetailStoreToResponse(PDPProductUIResponse pdpProductUIResponse)
      throws ServiceException, JSONException {
    PDPProductModel.LOGGER.info("PDPProductModel setRetailStoreToResponse Start");
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
        PDPProductModel.LOGGER.debug("Retail Stores Name : {}", storeName);
      }
    }
    pdpProductUIResponse.setStoreMap(storeMap);
    PDPProductModel.LOGGER.info("PDPProductModel setRetailStoreToResponse End");
  }

  private void getBazaarVoiceKeyValue(String siteKey) {
    PDPProductModel.LOGGER.info("PDPProductModel getBazaarVoiceKeyValue Start");
    bazarVoicePassKey = propertyReaderService.getBvPassKey(siteKey);
    PDPProductModel.LOGGER.debug("bazarVoicePassKey is {}", bazarVoicePassKey);
    PDPProductModel.LOGGER.info("PDPProductModel getBazaarVoiceKeyValue End");
  }

  public PDPProductUIResponse getPdpProductUIResponse() {
    return pdpProductUIResponse;
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
}
