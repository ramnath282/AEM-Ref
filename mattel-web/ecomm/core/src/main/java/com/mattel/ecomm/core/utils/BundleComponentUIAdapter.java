package com.mattel.ecomm.core.utils;

import com.mattel.ecomm.core.pojos.BundleComponentUIResponse;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ProductAssociationMapping;
import com.mattel.ecomm.coreservices.core.pojos.ChildProduct;
import com.mattel.ecomm.coreservices.core.pojos.PDPProductComponent;
import com.mattel.ecomm.coreservices.core.pojos.Price;
import com.mattel.ecomm.coreservices.core.pojos.ProductAssociation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BundleComponentUIAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(BundleComponentUIAdapter.class);

  private BundleComponentUIAdapter() {
    // no-op
  }

  public static BundleComponentUIResponse transformComponentToSignleSKU(
      PDPProductComponent component) {
    BundleComponentUIAdapter.LOGGER.info("TransformProductToSignleSKU - Start");
    final BundleComponentUIResponse bundleComponentUIResponse = new BundleComponentUIResponse();
    BundleComponentUIAdapter.buildResponse(component, bundleComponentUIResponse);
    BundleComponentUIAdapter.LOGGER.info("TransformProductToSignleSKU - End");
    return bundleComponentUIResponse;
  }

  private static void buildResponse(PDPProductComponent component,
      BundleComponentUIResponse bundleComponentUIResponse) {
    BundleComponentUIAdapter.LOGGER.info("buildResponse - Start");
    List<ChildProduct> childProducts = component.getChildProducts();
    final String partNumber = component.getPartNumber();

    if (Objects.nonNull(childProducts) && childProducts.size() > 1) {
      childProducts = ProductSizeChartUtils.sortProducts(childProducts);
    }

    bundleComponentUIResponse.setName(BundleComponentUIAdapter.transform(component.getName()));
    bundleComponentUIResponse.setPartNumber(partNumber);
    bundleComponentUIResponse.setChildProducts(childProducts);
    bundleComponentUIResponse
        .setThumbnail(BundleComponentUIAdapter.transform(component.getThumbnail()));
    bundleComponentUIResponse.setProductType(component.getProductType());
    String affrimInEligibleFlag = "";
    if (Objects.nonNull(component.getAffirmIneligible())) {
      affrimInEligibleFlag = component.getAffirmIneligible();
    }

    bundleComponentUIResponse.setAffirmIneligible(affrimInEligibleFlag);

    final Map<String, Price> price = component.getPrice();
    if (Objects.nonNull(price)) {
      bundleComponentUIResponse.setListPrice(price.get(Constant.LIST_PRICE));
    }
    bundleComponentUIResponse.setSeoUrlKeyword(Objects.nonNull(component.getSeoUrlKeyword()) ? component.getSeoUrlKeyword() :"");
    bundleComponentUIResponse.setImageLink(component.getImageLink());
    BundleComponentUIAdapter.addThumbnailImage(bundleComponentUIResponse);

    if (Objects.nonNull(component.getRelatedSizingChart())) {
      final String sizeChartDomainUri = EcommConfigurationUtils.getRootDomainSizeChart()
          + BundleComponentUIAdapter.transform(component.getRelatedSizingChart());
      bundleComponentUIResponse.setSizeChartLink(sizeChartDomainUri);
    }

    final String definingAttribute = ProductSizeAttributeUtils
        .getProductDefiningAttribute(childProducts);
    bundleComponentUIResponse.setDefiningAttribute(definingAttribute);

    final List<ProductAssociation> associationList = BundleComponentUIAdapter
        .filterAssociations(component.getAssociations());
    bundleComponentUIResponse.setAssociations(associationList);

    BundleComponentUIAdapter.setReatilInvetoryCheckFlag(bundleComponentUIResponse,
        component.getIsRetailInventoryCheckEnabled());
    bundleComponentUIResponse.setParentPartnumber(component.getParentPartnumber());

    bundleComponentUIResponse
        .setHasAddOns(String.valueOf(ProductAddOnUtils.isAddOn(component.getAssociations())));

    if (StringUtils.isNotBlank(component.getPrimaryBundleSku())
        && Constant.YES_PLACEHOLDER.equals(component.getPrimaryBundleSku())) {
      bundleComponentUIResponse.setIsDynamicKitPrimaryComponent("true");
    }

    BundleComponentUIAdapter.LOGGER.info("buildResponse - End");
  }

  /**
   * To override the {@link BundleComponentUIResponse#getImageLink()} with
   * {@link BundleComponentUIResponse#getThumbnail() thumbnail} +
   * {@link Constant#IMAGE_PRESET_SMALL_INDEX imagepreset} +
   * {@link Constant#IMAGE_RECOMMENDED_FORMAT imageformat}
   *
   * @param bundleComponentUIResponse
   *          {@link BundleComponentUIResponse} containing details of individual component of
   *          bundled product. Override image link here.
   */
  private static void addThumbnailImage(BundleComponentUIResponse bundleComponentUIResponse) {
    if (StringUtils.isNotBlank(bundleComponentUIResponse.getThumbnail())
        && StringUtils.isNotBlank(bundleComponentUIResponse.getImageLink())) {
      final String imageLink = bundleComponentUIResponse.getImageLink();
      final String prefix = imageLink.substring(0, imageLink.lastIndexOf(Constant.SLASH_CHAR) + 1);

      final String finalImageUrl = new StringBuilder(prefix)
          .append(bundleComponentUIResponse.getThumbnail()).append(Constant.QUESTION)
          .append(Constant.IMAGE_PRESET_SMALL_INDEX).toString();
      bundleComponentUIResponse.setImageLink(finalImageUrl);
    }
  }

  /**
   * This method checks if flag is set to "N". If it set to N then flag value is set to false. If
   * the it null or "Y" then inventory flag is set to true
   *
   * @param bundleComponentUIResponse
   *          mapped to the component attribute in the response for bundle product
   * @param isRetailInventoryCheckEnabled
   *          flag for retailInventory
   */
  private static void setReatilInvetoryCheckFlag(
      BundleComponentUIResponse bundleComponentUIResponse, String isRetailInventoryCheckEnabled) {
    BundleComponentUIAdapter.LOGGER.info("setReatilInvetoryCheckFlag - Start");
    bundleComponentUIResponse
        .setIsRetailInventoryCheckEnabled(!(StringUtils.isNotBlank(isRetailInventoryCheckEnabled)
            && StringUtils.equalsIgnoreCase(isRetailInventoryCheckEnabled, "N")));
    BundleComponentUIAdapter.LOGGER.info("setReatilInvetoryCheckFlag - End");
  }

  /**
   * This method filters the association list across the valid association types
   *
   * @param associations
   *          Initial associations received from WCS service
   * @return associationList filtered association list based on association type
   */
  private static List<ProductAssociation> filterAssociations(
      List<ProductAssociation> associations) {
    BundleComponentUIAdapter.LOGGER.info("filterAssociations - Start");
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
    BundleComponentUIAdapter.LOGGER.info("filterAssociations - End");
    return associationList;
  }

  @SuppressWarnings("unchecked")
  public static String transform(Object obj) {
    BundleComponentUIAdapter.LOGGER.info("transform - Start");
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
    BundleComponentUIAdapter.LOGGER.info("transform - End");
    return value;
  }

}
