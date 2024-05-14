package com.mattel.ecomm.core.utils;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.pojos.ChildProduct;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProductSizeAttributeUtils {
  private ProductSizeAttributeUtils() {
    //no-op
  }

  /**
   * This method checks if product has defining attributes related to size and color and returns the
   * string representation of the key.
   *
   * @param childProducts
   *          Child products associated with the products
   * @return definingAttr Defining attribute key for that product
   */
  public static String getProductDefiningAttribute(List<ChildProduct> childProducts) {
    final StringBuilder definingAttr = new StringBuilder("");
    if (Objects.nonNull(childProducts)) {
      childProducts.stream().findFirst().ifPresent(childProduct -> {
        final Map<String, Object> definingAttributes = childProduct.getDefiningAttributes();
        if (Objects.nonNull(definingAttributes)) {
          if (definingAttributes.containsKey(Constant.CLOTHING_SIZE_DEF_ATTRIBUTE)) {
            definingAttr.append(Constant.CLOTHING_SIZE_DEF_ATTRIBUTE);
          } else if (definingAttributes.containsKey(Constant.CLOTHING_COLOR_DEF_ATTRIBUTE)) {
            definingAttr.append(Constant.CLOTHING_COLOR_DEF_ATTRIBUTE);
          }
        }
      });
    }

    return definingAttr.toString();
  }
}
