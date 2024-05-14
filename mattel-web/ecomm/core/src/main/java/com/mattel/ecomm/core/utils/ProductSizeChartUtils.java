package com.mattel.ecomm.core.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.mattel.ecomm.coreservices.core.pojos.ChildProduct;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Variant;
import com.mattel.ecomm.coreservices.core.pojos.shopify.VariantCore;

public class ProductSizeChartUtils {
  private ProductSizeChartUtils() {
    // no-op
  }

  public static List<ChildProduct> sortProducts(List<ChildProduct> products) {
    final List<ChildProduct> sortedChildProducts = new ArrayList<>(products);

    Collections.sort(sortedChildProducts, Comparator.comparingDouble(ChildProduct::getSequence));
    return sortedChildProducts;
  }

  public static List<Variant> sortVariants(List<Variant> variants) {
    if (variants.size() > 1) {
      final List<Variant> sortedVariants = new ArrayList<>(variants);

      Collections.sort(sortedVariants, Comparator
          .comparingDouble(value -> Optional.ofNullable(value.getCore()).map(VariantCore::getPosition).orElse(0d)));
      return sortedVariants;
    }

    return variants;
  }
}
