package com.mattel.ecomm.core.utils;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.pojos.ProductAssociation;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Association;

import java.util.List;
import java.util.Objects;

public class ProductAddOnUtils {
  private ProductAddOnUtils() {
    // no-op
  }

  /**
   *
   * @param associations
   * @return
   */
  public static boolean isAddOn(List<ProductAssociation> productAssociations) {
    if (Objects.nonNull(productAssociations)) {
      return productAssociations.stream().anyMatch(
          association -> Constant.ADD_ON_ASSOCIATION_TYPE.equals(association.getAssociationType()));
    }

    return false;
  }

  public static boolean hasAddOn(List<Association> associations) {
    if (Objects.nonNull(associations)) {
      return associations.stream().anyMatch(
          association -> Constant.ADD_ON_ASSOCIATION_TYPE.equals(association.getAssociation_type()));
    }

    return false;
  }
}
