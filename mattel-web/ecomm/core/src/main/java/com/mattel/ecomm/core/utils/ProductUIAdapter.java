package com.mattel.ecomm.core.utils;

import com.mattel.ecomm.core.pojos.BundleComponentUIResponse;
import com.mattel.ecomm.core.pojos.GiftCardMessage;
import com.mattel.ecomm.core.pojos.GiftCardResponse;
import com.mattel.ecomm.core.pojos.PDPProductUIResponse;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ProductAssociationMapping;
import com.mattel.ecomm.coreservices.core.pojos.ChildProduct;
import com.mattel.ecomm.coreservices.core.pojos.PDPProductComponent;
import com.mattel.ecomm.coreservices.core.pojos.Price;
import com.mattel.ecomm.coreservices.core.pojos.Product;
import com.mattel.ecomm.coreservices.core.pojos.ProductAssociation;
import com.mattel.ecomm.coreservices.core.pojos.ProductAttributes;
import com.mattel.ecomm.coreservices.core.utilities.DateUtil;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductUIAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductUIAdapter.class);
  private static final String UNAVAILABLE = "Unavailable";
  private static final String NO_LONGER_AVAILABLE = "noLongerAvailable";
  private static final Map<String, String> AVAILABILITY_STATUS = new HashMap<>();
  /** To retrieve additional descriptive attributes and apply transformation on them if needed. */
  private static final Map<String, Function<String, String>> ADD_ATTR_TO_MAPPER = new HashMap<>();

  static {
    ProductUIAdapter.AVAILABILITY_STATUS.put(ProductUIAdapter.UNAVAILABLE,
        Constant.SEO_OUT_OF_STOCK);
    ProductUIAdapter.AVAILABILITY_STATUS.put(ProductUIAdapter.NO_LONGER_AVAILABLE,
        Constant.SEO_OUT_OF_STOCK);
    // Set name of attribute as key and function to transform the attribute value if needed.
    ProductUIAdapter.ADD_ATTR_TO_MAPPER.put(Constant.DISABLE_QUICK_VIEW, attr -> attr);
  }

  private ProductUIAdapter() {
    // no-op
  }

  /**
   * Transform the fetched {@link Product} details from service to output UI compatible
   * {@link PDPProductUIResponse response} object.
   *
   * @param product
   *          The incoming {@link Product product} details.
   * @param experienceFragmentPaths
   *          The {@link List} of experience fragment paths to be part of
   *          {@link PDPProductUIResponse response} object.
   * @return The {@link PDPProductUIResponse response} object.
   */
  public static PDPProductUIResponse transformProductToSignleSKU(Product product,
      List<String> experienceFragmentPaths) {
    ProductUIAdapter.LOGGER.info("TransformProductToSignleSKU - Start");
    final PDPProductUIResponse pdpProductUIResponse = new PDPProductUIResponse();
    ProductUIAdapter.buildResponse(product, pdpProductUIResponse, experienceFragmentPaths);
    ProductUIAdapter.LOGGER.info("TransformProductToSignleSKU - End");
    return pdpProductUIResponse;
  }

  private static void buildResponse(Product product, PDPProductUIResponse pdpProductUIResponse,
      List<String> experienceFragmentPaths) {
    ProductUIAdapter.LOGGER.info("buildResponse - Start");
    final Map<String, Object> desciptiveAttributes = Optional.ofNullable(product.getAttributes())
        .map(ProductAttributes::getDescripitiveAttributes).orElse(new HashMap<>());
    final List<ChildProduct> childProducts = Optional.ofNullable(product.getChildProducts())
        .filter(cps -> cps.size() > 1).map(ProductSizeChartUtils::sortProducts)
        .orElse(product.getChildProducts());
    final String productGtin;

    pdpProductUIResponse.setDesciptiveAttributes(desciptiveAttributes);

    pdpProductUIResponse.setPartNumber(product.getPartNumber());
    pdpProductUIResponse.setSkuName(ProductUIAdapter.transform(product.getAuxDescription1()));
    pdpProductUIResponse.setChildProducts(childProducts);
    pdpProductUIResponse.setProductType(product.getProductType());
    pdpProductUIResponse.setCustomerSegment(product.getCustSegExcl());
    pdpProductUIResponse.setFullimage(product.getFullimage());

    final String backOrderDate = ProductUIAdapter.transform(product.getBackorderDate());
    final Date parseBackOrderDate = DateUtil.getParsedDate(DateUtil.BACK_ORDER_ORIGINAL_DATE_FORMAT,
        backOrderDate);
    final String backOrderFormattedDate = Optional.ofNullable(parseBackOrderDate)
        .map(dt -> DateUtil.getFormattedDate(DateUtil.BACK_ORDER_REQUIRED_DATE_FORMAT, dt))
        .orElse(null);

    pdpProductUIResponse.setBackordarableText(backOrderFormattedDate);

    final Map<String, Price> price = product.getPrice();

    if (Objects.nonNull(price)) {
      pdpProductUIResponse.setListPrice(price.get(Constant.LIST_PRICE));
      pdpProductUIResponse.setOfferPrice(ProductUIAdapter.getOfferPriceValue(price));
    }

    pdpProductUIResponse.setAvailability(Optional.ofNullable(product.getInvStatus())
        .map(invStatus -> ProductUIAdapter.AVAILABILITY_STATUS.getOrDefault(invStatus,
            Constant.SEO_IN_STOCK))
        .orElse(product.getInvStatus()));

    final String availabilityStatus = product.getAvailability();
    pdpProductUIResponse.setAvailabilityStatus(Constant.SEO_IN_STOCK);

    ProductUIAdapter.setProductAvailabilityStatus(pdpProductUIResponse, availabilityStatus);

    pdpProductUIResponse.setImageLink(product.getImageLink());

    pdpProductUIResponse.setProductStatus(product.getProductStatus());
    pdpProductUIResponse.setSafetyMessage(
        ProductUIAdapter.transform(desciptiveAttributes.get(Constant.SAFETY_MESSAGE)));

    if (Objects.nonNull(desciptiveAttributes.get(Constant.RELATEDSIZINGCHART))) {
      final String sizeChartDomainUri = EcommConfigurationUtils.getRootDomainSizeChart()
          + ProductUIAdapter.transform(desciptiveAttributes.get(Constant.RELATEDSIZINGCHART));
      pdpProductUIResponse.setSizeChartLink(sizeChartDomainUri);
    }

    pdpProductUIResponse.setMarketingAge(
        ProductUIAdapter.transform(desciptiveAttributes.get(Constant.MARKETING_AGE)));
    pdpProductUIResponse.setProductReviewEnabled(
        ProductUIAdapter.transform(desciptiveAttributes.get(Constant.PRODUCT_REVIEW_ENABLED)));
    pdpProductUIResponse.setProductReviewRating(ProductUIAdapter
        .transform(desciptiveAttributes.get(Constant.PRODUCT_REVIEW_RATING)).replace(".", "-"));
    pdpProductUIResponse.setReviewRating(
        ProductUIAdapter.transform(desciptiveAttributes.get(Constant.PRODUCT_REVIEW_RATING)));
    pdpProductUIResponse.setProductReviewCount(
        ProductUIAdapter.transform(desciptiveAttributes.get(Constant.PRODUCT_REVIEW_COUNT)));
    pdpProductUIResponse.setLargeTrunkLowPrice(
        ProductUIAdapter.transform(desciptiveAttributes.get(Constant.LARGE_TRUNK_LOW_PRICE)));
    pdpProductUIResponse.setSmallTrunkLowPrice(
        ProductUIAdapter.transform(desciptiveAttributes.get(Constant.SMALL_TRUNK_LOW_PRICE)));

    pdpProductUIResponse.setViewerVideo(
        ProductUIAdapter.getArrayAsString(desciptiveAttributes.get(Constant.VIEWER_VIDEO)));
    pdpProductUIResponse.setProductCategory(
            ProductUIAdapter.transform(desciptiveAttributes.get(Constant.AG_CATEGORY)));
    pdpProductUIResponse.setProductCharacter(
            ProductUIAdapter.transform(desciptiveAttributes.get(Constant.CHARACTER)));
    pdpProductUIResponse.setProductPromoCategory(
            ProductUIAdapter.transform(desciptiveAttributes.get(Constant.PROMO_CATEGORY)));
    pdpProductUIResponse.setProductSubCategory(
            ProductUIAdapter.transform(desciptiveAttributes.get(Constant.AEM_CANONICAL)));
    pdpProductUIResponse.setProductSubBrand(
            ProductUIAdapter.transform(desciptiveAttributes.get(Constant.PLA_GOOGLE_CUSTOM_LABEL2)));
    pdpProductUIResponse.setProductGiftGuide(
            ProductUIAdapter.transform(desciptiveAttributes.get(Constant.GIFTGUIDE)));
    ProductUIAdapter.extractAndSetAdditionalDescriptiveAttrs(pdpProductUIResponse,
        desciptiveAttributes);

    productGtin = ProductUIAdapter.transform(desciptiveAttributes.get(Constant.PRODUCT_GTIN));
    if (StringUtils.isNotBlank(productGtin)) {
      pdpProductUIResponse.setProductGTIN(productGtin);
    }

    final String productCalloutAttribute = ProductUIAdapter
        .buildProductCallout(desciptiveAttributes, product.getNewOverrideFlag());

    pdpProductUIResponse.setProductCalloutAttribute(productCalloutAttribute);
    pdpProductUIResponse.setSeoMetaDescription(product.getSeoMetaDescription());

    final String definingAttribute = ProductSizeAttributeUtils.getProductDefiningAttribute(childProducts);
    pdpProductUIResponse.setDefiningAttribute(definingAttribute);

    final List<ProductAssociation> associationList = ProductUIAdapter
        .filterAssociations(product.getAssociations());
    pdpProductUIResponse.setAssociationList(associationList);

    ProductUIAdapter.setReatilInvetoryCheckFlag(pdpProductUIResponse, desciptiveAttributes);

    if (Objects.nonNull(product.getComponents()) && !product.getComponents().isEmpty()) {
      final List<PDPProductComponent> components = product.getComponents();
      final List<BundleComponentUIResponse> bundleComponentList = ProductUIAdapter
          .transformComponentList(components);
      pdpProductUIResponse.setComponents(bundleComponentList);
    }

    pdpProductUIResponse
        .setHasAddOns(String.valueOf(ProductAddOnUtils.isAddOn(product.getAssociations())));
    pdpProductUIResponse.setAffirmIneligible(Objects.nonNull(product.getAffirmIneligible())
        ? product.getAffirmIneligible() : StringUtils.EMPTY);

    pdpProductUIResponse.setNewOverrideFlag(Objects.nonNull(product.getNewOverrideFlag())
        ? product.getNewOverrideFlag() : StringUtils.EMPTY);

    // If excludeShippingCountries is set to CA and If ALT_CANADA_SKU is present, do not show the
    // "Cannot ship to Canada"
    // If excludeShippingCountries is set to CA and If ALT_CANADA_SKU is not present, then the
    // message should appear.
    pdpProductUIResponse.setExcludeShippingCountriesFlag(
        ProductUIAdapter.transform(desciptiveAttributes.get(Constant.EXCLUDE_SHIPPING_COUNTRIES)));
    pdpProductUIResponse
        .setIsAltCanadaSKU(Objects.nonNull(desciptiveAttributes.get(Constant.ALT_CANADA_SKU))
            ? Constant.ALT_CANADA_SKU : StringUtils.EMPTY);
    pdpProductUIResponse.setThumbnail(product.getThumbnail());
    Optional
        .ofNullable(
            ProductUIAdapter.transform(desciptiveAttributes.get(Constant.IS_DYNAMIC_KIT_INDICATOR)))
        .filter(Constant.YES_PLACEHOLDER::equalsIgnoreCase)
        .ifPresent(str -> pdpProductUIResponse.setIsDynamicKit("true"));
    Optional.ofNullable(ProductUIAdapter.transform(desciptiveAttributes.get("ReleaseDateWeb")))
        .filter(StringUtils::isNotBlank).ifPresent(pdpProductUIResponse::setReleaseDateWeb);
    if (Objects.nonNull(experienceFragmentPaths)) {
      pdpProductUIResponse.setExperienceFragmentPath(experienceFragmentPaths);
    }
    
    if("DynamicKitBean".equals(product.getProductType())){
    	ProductUIAdapter.LOGGER.debug("product is of type DynamickitBean");
    	setBittyTwinsProperties(product,pdpProductUIResponse);
    }
    ProductUIAdapter.LOGGER.info("buildResponse - End");
  }

  /**
   * This method sets hasBittyTwinAssociations flag in Product UI Response
   * 
   * @param product Product Service Response
   * @param pdpProductUIResponse Product UI Response
   */
  private static void setBittyTwinsProperties(Product product, PDPProductUIResponse pdpProductUIResponse) {
	ProductUIAdapter.LOGGER.info("setBittyTwinsProperties - Start");
	pdpProductUIResponse.setHasBittyTwinAssociations(validateAssociations(product.getAssociations()));
	if(pdpProductUIResponse.isHasBittyTwinAssociations()){
		BittyTwinsProductUIAdapter.transformProductToBittyTwins(product, pdpProductUIResponse);
	}
	ProductUIAdapter.LOGGER.info("setBittyTwinsProperties - End");
  }

  /**
   * This method check if product has associations of type BITTY_TWIN_COMP
   * and returns the flag accordingly.
   * 
   * @param associations product associations
   * @return hasBittyTwins flag
   */
  private static boolean validateAssociations(List<ProductAssociation> associations) {
	ProductUIAdapter.LOGGER.info("validateAssociations - Start");
	boolean hasBittyTwins = false;
	if (Objects.nonNull(associations) && !associations.isEmpty()) {
		hasBittyTwins = associations.stream().anyMatch(association -> 
		Constant.BITTY_TWIN_ASSOCIATION_TYPE.equals(association.getAssociationType()));
	}
	ProductUIAdapter.LOGGER.info("validateAssociations - End");
	return hasBittyTwins;
 }

/**
   * Set product availability status to pdpProductUIResponse response using product availability
   * status
   *
   * @param pdpProductUIResponse
   *          pdpProductUIResponse object to set availability status
   * @param availabilityStatus
   *          Availability status for part number
   */
  private static void setProductAvailabilityStatus(PDPProductUIResponse pdpProductUIResponse,
      final String availabilityStatus) {
    if (Objects.nonNull(availabilityStatus)) {
      if (availabilityStatus.equalsIgnoreCase(ProductUIAdapter.UNAVAILABLE)
          || availabilityStatus.equalsIgnoreCase(ProductUIAdapter.NO_LONGER_AVAILABLE)) {
        pdpProductUIResponse.setAvailabilityStatus(Constant.SEO_OUT_OF_STOCK);
      } else if (availabilityStatus.equalsIgnoreCase(Constant.AVAILABILITY_BACKORDERABLE)
          || availabilityStatus.equalsIgnoreCase(Constant.AVAILABILITY_BACKORDERED)) {
        pdpProductUIResponse.setAvailabilityStatus(Constant.SEO_PRE_ORDER);
      } else if (availabilityStatus.equalsIgnoreCase(Constant.AVAILABILITY_LIMITED)) {
        pdpProductUIResponse.setAvailabilityStatus(Constant.SEO_LIMITED);
      }
    }
  }

  /**
   * To extract additional descriptive attributes and perform transformation(mapping) if needed on
   * the attributes.
   *
   * @param pdpProductUIResponse
   *          The output {@link PDPProductUIResponse} containing productDetails.
   * @param desciptiveAttributes
   *          The {@link Map} containing all the descriptive attributes.
   */
  private static void extractAndSetAdditionalDescriptiveAttrs(
      PDPProductUIResponse pdpProductUIResponse, final Map<String, Object> desciptiveAttributes) {
    final Map<String, String> additionalDescriptiveAttributes = new HashMap<>();
    final Set<String> attributesName = ProductUIAdapter.ADD_ATTR_TO_MAPPER.keySet();

    for (final String attributeName : attributesName) {
      Optional.ofNullable(ProductUIAdapter.transform(desciptiveAttributes.get(attributeName)))
          .filter(StringUtils::isNotBlank)
          .map(ProductUIAdapter.ADD_ATTR_TO_MAPPER.get(attributeName))
          .ifPresent(value -> additionalDescriptiveAttributes.put(attributeName, value));
    }

    pdpProductUIResponse.setAdditionalDescriptiveAttributes(additionalDescriptiveAttributes);
  }

  /**
   * To get offer-price from PDP Response.
   *
   * @param price
   *          The {@link Map} encapsulating both offer price and list price.
   * @return offerPrice The offer price value.
   */
  private static String getOfferPriceValue(Map<String, Price> price) {
    String offerPrice = "";
    if (!Objects.isNull(price.get(Constant.OFFER_PRICE))) {
      offerPrice = price.get(Constant.OFFER_PRICE).getValue();
    } else if (!Objects.isNull(price.get(Constant.LIST_PRICE))) {
      offerPrice = price.get(Constant.LIST_PRICE).getValue();
    }
    return offerPrice;
  }

  /**
   * To build product callout.
   *
   * @param desciptiveAttributes
   *          {@link Map} containing product marketing callout data.
   * @param newOverrideFlag
   *          {@link String} contains flag to override the 'New' marketing callout status of
   *          product.
   * @return the product callout.
   */
  private static String buildProductCallout(final Map<String, Object> desciptiveAttributes,
      String newOverrideFlag) {
    String productCalloutAttribute = ProductUIAdapter
        .transform(desciptiveAttributes.get(Constant.MARKETING_CALLOUT));

    if (Objects.nonNull(desciptiveAttributes.get(Constant.RELEASE_DATE_WEB))) {
      try {
        final String releaseDateWeb = ProductUIAdapter
            .transform(desciptiveAttributes.get(Constant.RELEASE_DATE_WEB));
        final LocalDate todayDate = LocalDate.now();
        final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern(DateUtil.STANDARD_CALENDAR_DATE);
        final LocalDate formattedReleaseDateWeb = LocalDate.parse(releaseDateWeb, formatter);
        final Period period = Period.between(formattedReleaseDateWeb, todayDate);
        final long months = period.toTotalMonths();
        if (months < Constant.MARKETING_CALL_OUT_PERIOD
            && !(Constant.NEW_OVERRIDE_FLAG.equalsIgnoreCase(newOverrideFlag))) {
          if (StringUtils.isNotBlank(productCalloutAttribute)) {
            productCalloutAttribute = Constant.NEW_MARKETING_CALLOUT + " - "
                + productCalloutAttribute.trim();
          } else {
            productCalloutAttribute = Constant.NEW_MARKETING_CALLOUT;
          }
        }
      } catch (final DateTimeParseException dtpe) {
        ProductUIAdapter.LOGGER.error("Unable to build product callout", dtpe);
      }
    }

    return productCalloutAttribute;
  }

  /**
   * This method accepts the list of component objects and transforms it to list of modified list of
   * UI transformed component objects.
   *
   * @param components
   *          The individual bundle product component.
   * @return updatedComponentList modified list of UI representation (single SKU) of component
   *         objects
   */
  private static List<BundleComponentUIResponse> transformComponentList(
      List<PDPProductComponent> components) {
    final List<BundleComponentUIResponse> updatedComponentList = new ArrayList<>();
    components.forEach(component -> {
      final BundleComponentUIResponse compUiRes = BundleComponentUIAdapter
          .transformComponentToSignleSKU(component);
      updatedComponentList.add(compUiRes);
    });
    return updatedComponentList;
  }

  /**
   * This method checks if flag is set to "N". If it set to N then flag value is set to false. If
   * the it null or "Y" then inventory flag is set to true
   *
   * @param pdpProductUIResponse
   *          main product UI response object
   * @param desciptiveAttributes
   *          descriptive attributes for that product
   */
  private static void setReatilInvetoryCheckFlag(PDPProductUIResponse pdpProductUIResponse,
      Map<String, Object> desciptiveAttributes) {
    final String retailInventoryFlag = ProductUIAdapter
        .transform(desciptiveAttributes.get(Constant.ISRETAILINVENTORYCHECKENABLED));

    pdpProductUIResponse
        .setRetailInventoryCheckEnabled(!(StringUtils.isNotBlank(retailInventoryFlag)
            && StringUtils.equalsIgnoreCase(retailInventoryFlag, "N")));
  }

  /**
   * This method filters the association list across the valid association types.
   *
   * @param associations
   *          Initial associations received from WCS service
   * @return associationList filtered association list based on association type
   */
  private static List<ProductAssociation> filterAssociations(
      List<ProductAssociation> associations) {
    final List<ProductAssociation> associationList = new ArrayList<>();
    if (Objects.nonNull(associations) && !associations.isEmpty()) {
      associations.forEach(assocation -> {
        if (Objects.nonNull(assocation.getAssociationType())) {
          final boolean validAssociation = Arrays
              .stream(ProductAssociationMapping.ASSOCIATIONS.getAssociationTypes())
              .anyMatch(assocation.getAssociationType()::equals);
          if (validAssociation) {
            associationList.add(assocation);
          }
        }
      });
    }
    return associationList;
  }

  /**
   * To transform incoming {@link Product} into {@link GiftCardResponse} for gift cards.
   *
   * @param product
   *          The {@link Product gift card} fetched from service.
   * @return The {@link GiftCardResponse} instance.
   */
  public static GiftCardResponse transformProductToGiftCard(Product product) {
    final GiftCardResponse giftCardResponse = new GiftCardResponse();

    ProductUIAdapter.LOGGER.info("Transform gift card - Start");
    ProductUIAdapter.buildResponse(product, giftCardResponse, null);
    Optional.ofNullable(product.getAttributes()).ifPresent(attributes -> Optional
        .of(attributes.getDescripitiveAttributes()).ifPresent(descriptiveAttributes -> {
          if (Objects.nonNull(descriptiveAttributes.get(Constant.DISCLAIMERCOPY))
              && Objects.nonNull(product.getProductType())
              && Constant.GIFT_CARD.equals(product.getProductType())) {
            giftCardResponse.setGiftDisclamer(String.valueOf(
                ProductUIAdapter.transform(descriptiveAttributes.get(Constant.DISCLAIMERCOPY))));
          }
        }));
    Optional.ofNullable(product.getGiftCardMessages()).ifPresent(giftCardMessages -> {
      final List<GiftCardMessage> messages = new ArrayList<>();
      for (final Map<String, String> giftCardMessage : giftCardMessages) {
        final GiftCardMessage message = new GiftCardMessage();

        message
            .setMessageTitle(ProductUIAdapter.transform(giftCardMessage.get(Constant.GIFTMESSAGE)));
        message.setMessage1(ProductUIAdapter.transform(giftCardMessage.get(Constant.LINE1)));
        message.setMessage2(ProductUIAdapter.transform(giftCardMessage.get(Constant.LINE2)));
        message.setMessage3(ProductUIAdapter.transform(giftCardMessage.get(Constant.LINE3)));
        message.setSequence(ProductUIAdapter.transform(giftCardMessage.get(Constant.SEQUENCE)));
        messages.add(message);
      }
      giftCardResponse.setMessages(messages);
    });
    ProductUIAdapter.LOGGER.info("Transform gift card - End");
    return giftCardResponse;
  }

  /**
   * Transform objects of type {@link String} and {@link List} into string. For {@link List} type we
   * only pick the first value.
   *
   * @param obj
   *          The input object.
   * @return The extracted {@link String} value.
   */
  @SuppressWarnings("unchecked")
  public static String transform(Object obj) {
    String value = "";
    if (Objects.nonNull(obj)) {
      if (obj instanceof String) {
        value = obj.toString();
      } else if (obj instanceof List) {
        final List<String> strArray = (List<String>) obj;
        if (!strArray.isEmpty()) {
          value = strArray.get(0);
        }
      }
    }
    return value;
  }

  /**
   * Transform objects of type {@link String} and {@link List} into string.
   * @param obj The input object.
   * @return The extracted {@link String} value.
   */
  @SuppressWarnings("unchecked")
  public static String getArrayAsString(Object obj) {
    ProductUIAdapter.LOGGER.info("getArrayAsString of Product UI adapter - Start");
    String value = "";
    if (Objects.nonNull(obj)) {
      if (obj instanceof String) {
        value = obj.toString();
        ProductUIAdapter.LOGGER.debug("value is {}", value);
      } else if (obj instanceof List) {
        final List<String> strArray = (List<String>) obj;
        if (!strArray.isEmpty()) {
          value = String.join(",",strArray);
          ProductUIAdapter.LOGGER.debug("string value of array  is {}", value);
        }
      }
    }
    ProductUIAdapter.LOGGER.info("getArrayAsString of Product UI adapter - End");
    return value;
  }
}
