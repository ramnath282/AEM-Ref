package com.mattel.ecomm.core.utils.shopify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.coreservices.core.enums.ProductAssociationMapping;
import com.mattel.ecomm.coreservices.core.pojos.shopify.AssociatedProduct;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Association;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Core;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Inventory;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Option;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Variant;

/**
 * Base Product UI Adapter containing common utiltity methods.
 */
public class BaseProductUIAdapter {
  private static final Logger LOGGER = LoggerFactory.getLogger(BaseProductUIAdapter.class);
  public static final String CLOTHING_SIZE_DEF_ATTRIBUTE = "Clothing Size";
  public static final String CLOTHING_COLOR_DEF_ATTRIBUTE = "Clothing Color";
  public static final String CLOTHING_COLOR_DEF_ATTRIBUTE2 = "Color";

  /**
   * To populate inventory objects in variants.
   *
   * @param variants
   *          Product variants.
   */
  protected static void populateInventory(final List<Variant> variants) {
    BaseProductUIAdapter.LOGGER.info("Populating inventoru in variants");

    for (final Variant variant : variants) {
      Optional.ofNullable(variant.getCore()).ifPresent(vc -> {
        final Inventory inventory = new Inventory();

        inventory.setProduct_type(vc.getVariant_product_type());
        inventory.setInventorystatus(vc.getVariant_inventorystatus());
        inventory.setBackorderdate(vc.getVariant_backorderdate());
        inventory.setPartnumber(
            Optional.ofNullable(vc.getSku()).filter(StringUtils::isNotEmpty).orElse(vc.getVariant_parentpartnumber()));
        variant.setInventory(inventory);
      });
    }

    BaseProductUIAdapter.LOGGER.info("Inventory populated");
    BaseProductUIAdapter.LOGGER.debug("Populated inventory in variants: {}", variants);
  }

  public static void checkForSwatches(List<Option> options, final Core core, List<Variant> variants) {
    if (Objects.nonNull(options) && variants.size() > 1) {
      Optional.ofNullable(options).map(o -> o.get(0)).filter(o -> {
        final String optionName = o.getName();

        return BaseProductUIAdapter.CLOTHING_SIZE_DEF_ATTRIBUTE.equals(optionName)
            || BaseProductUIAdapter.CLOTHING_COLOR_DEF_ATTRIBUTE.equals(optionName)
            || BaseProductUIAdapter.CLOTHING_COLOR_DEF_ATTRIBUTE2.equals(optionName);
      }).ifPresent(o -> core.setProduct_hasSwatches("true"));
    }
  }

  /**
   * To filter the associations based on supported types :
   * {@link ProductAssociationMapping#ASSOCIATIONS}.
   *
   * @param associations
   *          Product associations.
   */
  public static List<Association> filterAssociations(List<Association> associations) {
    final List<Association> associationList = new ArrayList<>();

    BaseProductUIAdapter.LOGGER.info("Filtering supported associations: {}", associations);
    if (Objects.nonNull(associations) && !associations.isEmpty()) {
      associations.forEach(assocation -> {
        if (Objects.nonNull(assocation.getAssociation_type())) {
          final boolean validAssociation = Arrays.stream(ProductAssociationMapping.ASSOCIATIONS.getAssociationTypes())
              .anyMatch(assocation.getAssociation_type()::equalsIgnoreCase);

          if (validAssociation) {
            associationList.add(assocation);
            Optional.ofNullable(assocation.getProduct()).filter(a -> Objects.nonNull(a.getVariants()))
                .map(AssociatedProduct::getVariants).ifPresent(BaseProductUIAdapter::populateInventory);
          }
        }
      });
    }

    BaseProductUIAdapter.LOGGER.info("Association filtering complete");
    BaseProductUIAdapter.LOGGER.debug("Filtered associations: {}", associations);
    return associationList;
  }

  /**
   * Check if the object is single valued or multiple valued. If multiple return
   * the first value.
   *
   * @param obj
   *          The object instance
   * @return The string value
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
   * @param obj
   *          The object instance
   * @return Converts string array into composed String.
   */
  @SuppressWarnings("unchecked")
  public static String getArrayAsString(Object obj) {
    BaseProductUIAdapter.LOGGER.info("getArrayAsString of Product UI adapter - Start");
    String value = "";

    if (Objects.nonNull(obj)) {
      if (obj instanceof String) {
        value = obj.toString();
        BaseProductUIAdapter.LOGGER.debug("value is {}", value);
      } else if (obj instanceof List) {
        final List<String> strArray = (List<String>) obj;
        if (!strArray.isEmpty()) {
          value = String.join(",", strArray);
          BaseProductUIAdapter.LOGGER.debug("string value of array  is {}", value);
        }
      }
    }

    BaseProductUIAdapter.LOGGER.info("getArrayAsString of Product UI adapter - End");
    return value;
  }

  /**
   * Check if product has {@link ProductAssociationMapping#QUICK_TYPE} association.
   *
   * @param associations
   *          The list of product associations.
   * @return True, if product has {@link ProductAssociationMapping#QUICK_TYPE} association.
   */
  public static boolean hasQuickSell(List<Association> associations) {
    if (Objects.nonNull(associations)) {
      return associations.stream().anyMatch(
          association -> ProductAssociationMapping.QUICK_TYPE.equals(association.getAssociation_type()));
    }

    return false;
  }

  private BaseProductUIAdapter() {
    // no-op
  }
}