package com.mattel.ecomm.core.utils.shopify;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.pojos.GiftCardUIResponse;
import com.mattel.ecomm.core.pojos.shopify.ComponentUIResponse;
import com.mattel.ecomm.core.pojos.shopify.ProductUIResponse;
import com.mattel.ecomm.core.utils.EcommConfigurationUtils;
import com.mattel.ecomm.core.utils.ProductAddOnUtils;
import com.mattel.ecomm.core.utils.ProductSizeChartUtils;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ProductAssociationMapping;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Association;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Core;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Product;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Variant;
import com.mattel.ecomm.coreservices.core.utilities.DateUtil;

/**
 * Adapter class to transform Product availability api response to UI compatible
 * response.
 */
public class ProductUIAdapter {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductUIAdapter.class);
  private static final String UNAVAILABLE = "Unavailable";
  private static final String NO_LONGER_AVAILABLE = "noLongerAvailable";
  private static final Map<String, String> AVAILABILITY_STATUS = new HashMap<>();

  static {
    ProductUIAdapter.AVAILABILITY_STATUS.put(ProductUIAdapter.UNAVAILABLE, Constant.SEO_OUT_OF_STOCK);
    ProductUIAdapter.AVAILABILITY_STATUS.put(ProductUIAdapter.NO_LONGER_AVAILABLE, Constant.SEO_OUT_OF_STOCK);
  }

  private ProductUIAdapter() {
    // no-op
  }

  /**
   * Transform the fetched {@link Product} details from service to output UI
   * compatible {@link ProductUIResponse response} object.
   *
   * @param product
   *          The incoming {@link Product product} details.
   * @param experienceFragmentPaths
   *          The {@link List} of experience fragment paths to be part of
   *          {@link ProductUIResponse response} object.
   * @return The {@link ProductUIResponse response} object.
   */
  public static ProductUIResponse transformProduct(Product product, List<String> experienceFragmentPaths) {
    ProductUIAdapter.LOGGER.info("TransformProduct - Start");
    final ProductUIResponse shopifyProductUIResponse = new ProductUIResponse();
    ProductUIAdapter.buildResponse(product, shopifyProductUIResponse, experienceFragmentPaths);
    ProductUIAdapter.LOGGER.info("TransformProduct- End");
    return shopifyProductUIResponse;
  }

  /**
   * Transform the fetched {@link Product} details from service to output UI
   * compatible {@link GiftCardUIResponse response} object.
   *
   * @param product
   *          The incoming {@link Product product} details.
   * @return The {@link GiftCardUIResponse response} object.
   */
  public static GiftCardUIResponse transformProductToGiftCard(Product product) {
    ProductUIAdapter.LOGGER.info("transformProductToGiftCard - Start");
    final GiftCardUIResponse giftCardNewResponse = new GiftCardUIResponse();
    ProductUIAdapter.buildResponse(product, giftCardNewResponse, null);
    ProductUIAdapter.LOGGER.info("transformProductToGiftCard - End");
    return giftCardNewResponse;
  }

  /**
   * Adapt the Product availability api response to UI compatible response.
   *
   * @param product
   *          The Product availability api response.
   * @param shopifyProductUIResponse
   *          The UI compatible product response.
   * @param experienceFragmentPaths
   *          The paths of marketing content experience fragments.
   */
  private static void buildResponse(Product product, ProductUIResponse shopifyProductUIResponse,
      List<String> experienceFragmentPaths) {
    final Map<String, Object> attributes = product.getAttributes();
    final Core core = Optional.ofNullable(product.getCore()).orElse(new Core());
    List<Variant> variants = product.getVariants();
    List<Association> associations = product.getAssociations();

    ProductUIAdapter.LOGGER.info("buildResponse - Start");

    if (Objects.nonNull(variants) && !variants.isEmpty()) {
      ProductUIAdapter.LOGGER.debug("Populating inventory and sorting variants: {}", variants);
      BaseProductUIAdapter.checkForSwatches(product.getOptions(), core, variants);
      BaseProductUIAdapter.populateInventory(variants);
      variants = ProductSizeChartUtils.sortVariants(variants);
    }

    if (Objects.nonNull(associations) && !associations.isEmpty()) {
      ProductUIAdapter.LOGGER.debug("Populating components and filtering associations: {}", associations);
      core.setProduct_hasAddOns(String.valueOf(ProductAddOnUtils.hasAddOn(associations)));
      core.setProduct_hasQuickSell(String.valueOf(BaseProductUIAdapter.hasQuickSell(associations)));
      associations = BaseProductUIAdapter.filterAssociations(associations);
      Optional.ofNullable(ProductUIAdapter.transformComponentList(core, product.getPartnumber(), associations))
          .filter(comps -> !comps.isEmpty()).ifPresent(shopifyProductUIResponse::setComponents);
    }

    if (Objects.nonNull(attributes) && !attributes.isEmpty()) {
      ProductUIAdapter.LOGGER.debug("Product attributes: {}", attributes);
      final String productGtin = BaseProductUIAdapter.transform(attributes.get(Constant.PRODUCT_GTIN));

      if (StringUtils.isNotBlank(productGtin)) {
        core.setProduct_productGTIN(productGtin);
      }

      if (Objects.nonNull(attributes.get(Constant.RELATEDSIZINGCHART))) {
        final String sizeChartDomainUri = EcommConfigurationUtils.getRootDomainSizeChart()
            + BaseProductUIAdapter.transform(attributes.get(Constant.RELATEDSIZINGCHART));

        ProductUIAdapter.LOGGER.debug("Fetching size chart link for style headers: {}", sizeChartDomainUri);
        core.setProduct_sizeChartLink(sizeChartDomainUri);
      }

      ProductUIAdapter.setRetailInventoryCheckFlag(core);
      core.setProduct_safetyMessage(BaseProductUIAdapter.transform(attributes.get(Constant.SAFETY_MESSAGE)));
      core.setProduct_productcallout(BaseProductUIAdapter.transform(attributes.get(Constant.MARKETING_CALLOUT))); //
      core.setProduct_marketingAge(BaseProductUIAdapter.transform(attributes.get(Constant.MARKETING_AGE)));
      core.setProduct_reviewEnabled(BaseProductUIAdapter.transform(attributes.get(Constant.PRODUCT_REVIEW_ENABLED)));
      core.setProduct_reviewRating(
          BaseProductUIAdapter.transform(attributes.get(Constant.PRODUCT_REVIEW_RATING)).replace(".", "-"));
      core.setProduct_reviewRatingOriginal(
          BaseProductUIAdapter.transform(attributes.get(Constant.PRODUCT_REVIEW_RATING)));
      core.setProduct_reviewCount(BaseProductUIAdapter.transform(attributes.get(Constant.PRODUCT_REVIEW_COUNT)));
      core.setProduct_largeTrunkLowPrice(
          BaseProductUIAdapter.transform(attributes.get(Constant.LARGE_TRUNK_LOW_PRICE)));
      core.setProduct_smallTrunkLowPrice(
          BaseProductUIAdapter.transform(attributes.get(Constant.SMALL_TRUNK_LOW_PRICE)));
      core.setProduct_viewerVideo(BaseProductUIAdapter.getArrayAsString(attributes.get(Constant.VIEWER_VIDEO)));
      core.setProduct_productCategory(BaseProductUIAdapter.transform(attributes.get(Constant.AG_CATEGORY)));
      core.setProduct_character(BaseProductUIAdapter.transform(attributes.get(Constant.CHARACTER)));
      core.setProduct_promoCategory(BaseProductUIAdapter.transform(attributes.get(Constant.PROMO_CATEGORY)));
      core.setProduct_subCategory(BaseProductUIAdapter.transform(attributes.get(Constant.AEM_CANONICAL)));
      core.setProduct_subBrand(BaseProductUIAdapter.transform(attributes.get(Constant.PLA_GOOGLE_CUSTOM_LABEL2)));
      core.setProduct_giftGuide(BaseProductUIAdapter.transform(attributes.get(Constant.GIFTGUIDE)));
      core.setProduct_excludeShippingCountriesFlag(
          BaseProductUIAdapter.transform(attributes.get(Constant.EXCLUDE_SHIPPING_COUNTRIES)));
      core.setProduct_isAltCanadaSKU(
          Objects.nonNull(attributes.get(Constant.ALT_CANADA_SKU)) ? BaseProductUIAdapter.transform(attributes.get(Constant.ALT_CANADA_SKU)) : StringUtils.EMPTY);
      Optional.ofNullable(BaseProductUIAdapter.transform(attributes.get(Constant.IS_DYNAMIC_KIT_INDICATOR)))
          .filter(Constant.YES_PLACEHOLDER::equalsIgnoreCase).ifPresent(str -> core.setProduct_isDynamicKit("true"));
      Optional.ofNullable(BaseProductUIAdapter.transform(attributes.get("ReleaseDateWeb")))
          .filter(StringUtils::isNotBlank).ifPresent(core::setProduct_releaseDateWeb);
      Optional.ofNullable(BaseProductUIAdapter.transform(attributes.get(Constant.DISCLAIMERCOPY)))
          .filter(StringUtils::isNotBlank).ifPresent(core::setProduct_disclaimer);
      Optional.ofNullable(BaseProductUIAdapter.transform(attributes.get(Constant.DISABLE_QUICK_VIEW)))
          .filter(StringUtils::isNotBlank).ifPresent(core::setProduct_disableQuickView);
    }

    shopifyProductUIResponse.setExperienceFragmentPath(experienceFragmentPaths);
    shopifyProductUIResponse.setId(product.getId());
    shopifyProductUIResponse.setProduct_id(product.getProduct_id());
    shopifyProductUIResponse.setPartnumber(product.getPartnumber());
    shopifyProductUIResponse.setVariants(variants);
    shopifyProductUIResponse.setCore(core);
    shopifyProductUIResponse.setAttributes(attributes);
    shopifyProductUIResponse.setImages(product.getImages());
    shopifyProductUIResponse.setOptions(product.getOptions());
    shopifyProductUIResponse.setAssociations(associations);

    if ("DynamicKitBean".equals(core.getProduct_type())) {
      ProductUIAdapter.setBittyTwinsProperties(product, shopifyProductUIResponse);
    }

    ProductUIAdapter.LOGGER.info("buildResponse - End");
  }
  /**
   * This method sets hasBittyTwinAssociations flag in Product UI Response
   *
   * @param product Product Service Response
   * @param shopifyProductUIResponse Product UI Response
   */
  private static void setBittyTwinsProperties(Product product, ProductUIResponse shopifyProductUIResponse) {
    ProductUIAdapter.LOGGER.info("setBittyTwinsProperties - Start");
    shopifyProductUIResponse
        .setHasBittyTwinAssociations(ProductUIAdapter.validateAssociations(product.getAssociations()));
    if (shopifyProductUIResponse.isHasBittyTwinAssociations()) {
      BittyTwinsProductUIAdapter.transformProductToBittyTwins(product, shopifyProductUIResponse);
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
  private static boolean validateAssociations(List<Association> associations) {
    ProductUIAdapter.LOGGER.info("validateAssociations - Start");
    boolean hasBittyTwins = false;
    if (Objects.nonNull(associations) && !associations.isEmpty()) {
      ProductUIAdapter.LOGGER.debug("Checking the associations type");
      hasBittyTwins = associations.stream()
          .anyMatch(association -> Constant.BITTY_TWIN_ASSOCIATION_TYPE.equals(association.getAssociation_type()));
    }
    ProductUIAdapter.LOGGER.debug("Value of hasBittyTwins is {} ", hasBittyTwins);
    ProductUIAdapter.LOGGER.info("validateAssociations - End");
    return hasBittyTwins;
  }

  /**
   * Builds component list.
   *
   * @param core
   *          Core payload of parent product.
   * @param partnumber
   *          Part number of parent product.
   * @param associations
   *          Parent product associations.
   * @return The component list.
   */
  private static List<ComponentUIResponse> transformComponentList(Core core, String partnumber,
      List<Association> associations) {
    final String product_type = core.getProduct_type();

    if (Constant.PRODUCT_TYPE_BUNDLE_BEAN.equals(product_type)
        || Constant.PRODUCT_TYPE_PACKAGE_BEAN.equals(product_type)
            || Constant.PRODUCT_TYPE_GIFT_CARD.equals(product_type)) {
      ProductUIAdapter.LOGGER.info("Building component list");
      final List<ComponentUIResponse> components = new ArrayList<>();
      final List<Association> bundleAssociations = associations.stream()
          .filter(at -> ProductAssociationMapping.COMPONENT_TYPE.equalsIgnoreCase(at.getAssociation_type()))
          .collect(Collectors.toList());

      bundleAssociations.stream().filter(assoc -> Objects.nonNull(assoc.getProduct()))
          .forEach(assoc -> components.add(BundledComponentUIAdpater.transform(assoc, partnumber)));
      Collections.sort(components,
          Comparator.comparingDouble(value -> Optional.ofNullable(value.getAssociation_sequence()).orElse(0d)));
      ProductUIAdapter.LOGGER.info("Component build up complete");
      ProductUIAdapter.LOGGER.debug("Components : {}", components);
      return components;
    }

    return Arrays.asList();
  }

  /**
   * Sets value of retail inventory check flag. If "true", store availability
   * check enabled for product.
   *
   * @param core
   *          The core payload of parent product
   */
  private static void setRetailInventoryCheckFlag(Core core) {
    final String retailInventoryFlag = BaseProductUIAdapter.transform(core.getProduct_isretailinventorycheckenabled());
    core.setProduct_isretailinventorycheckenabled(String.valueOf(
        !(StringUtils.isNotBlank(retailInventoryFlag) && StringUtils.equalsIgnoreCase(retailInventoryFlag, "N"))));
  }

  /**
   * Build the marketing product callout string
   *
   * @param attributes
   *          The product attributes.
   * @param newOverrideFlag
   *          If "Y", hide the "New" prefix in product callout.
   * @return The product callout
   */
  public static String buildProductCallout(final Map<String, Object> attributes, String newOverrideFlag) {
    String productCalloutAttribute = BaseProductUIAdapter.transform(attributes.get(Constant.MARKETING_CALLOUT));

    if (Objects.nonNull(attributes.get(Constant.RELEASE_DATE_WEB))) {
      ProductUIAdapter.LOGGER.info("Building product callout");
      try {
        final String releaseDateWeb = BaseProductUIAdapter.transform(attributes.get(Constant.RELEASE_DATE_WEB));
        final LocalDate todayDate = LocalDate.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtil.STANDARD_CALENDAR_DATE);
        final LocalDate formattedReleaseDateWeb = LocalDate.parse(releaseDateWeb, formatter);
        final Period period = Period.between(formattedReleaseDateWeb, todayDate);
        final long months = period.toTotalMonths();
        if (months < Constant.MARKETING_CALL_OUT_PERIOD
            && !(Constant.NEW_OVERRIDE_FLAG.equalsIgnoreCase(newOverrideFlag))) {
          if (StringUtils.isNotBlank(productCalloutAttribute)) {
            productCalloutAttribute = Constant.NEW_MARKETING_CALLOUT + " - " + productCalloutAttribute.trim();
          } else {
            productCalloutAttribute = Constant.NEW_MARKETING_CALLOUT;
          }
        }
      } catch (final DateTimeParseException dtpe) {
        ProductUIAdapter.LOGGER.error("Unable to build product callout", dtpe);
      }
    }

    ProductUIAdapter.LOGGER.info("Product callout: {}", productCalloutAttribute);
    return productCalloutAttribute;
  }
}
