package com.mattel.ecomm.core.utils.shopify;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.pojos.shopify.GTAddonPojo;
import com.mattel.ecomm.core.pojos.shopify.GTSummaryUIResponse;
import com.mattel.ecomm.core.utils.ProductSizeChartUtils;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAvailability;
import com.mattel.ecomm.coreservices.core.pojos.shopify.AssociatedProduct;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Association;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Core;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Product;
import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Variant;

/**
 * @author CTS
 *
 */
public class GTSummaryPageUIAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(GTSummaryPageUIAdapter.class);
  private static final String KITCOMPONENT = "KITCOMPONENT";
  private static final String KITOPTION = "KitOption";
  private static final String KITDISPLAYSEQUENCE = "KitDisplaySequence";
  private static final String KITDISPLAYABLE = "KitDisplayable";
  private static final String KITDISPLAYABLE_Y = "Y";
  private static final String KITINSTRUCTIONS = "KitInstructions";
  private static final String TRUNKDESCRIPTION = "TrunkDescription";
  private static final String OPTIONALNAME = "optionalName";
  private static final String IMAGELINK = "https://images.mattel.com/scene7/";
  private static final String KITPRODUCTBEAN = "ProductBean";
  private static final String ALT_ATTR = "alt";
  ProductAvailability productAvailability = null;
  private Map<String, Object> requestMap = null;

  /**
   * @param product
   * @param productAvailability
   * @param requestMap
   * @return
   */
  public GTSummaryUIResponse transformProductToSingleSKU(Product product,
      ProductAvailability productAvailability, Map<String, Object> requestMap) {
    LOGGER.info("transformProductToSingleSKU -> Start, requestmap : {}", requestMap);
    this.productAvailability = productAvailability;
    this.requestMap = requestMap;
    GTSummaryUIResponse gtSummaryPageUIResponse = new GTSummaryUIResponse();
    Optional.ofNullable(product).ifPresent(p -> buildResponse(p, gtSummaryPageUIResponse));
    return gtSummaryPageUIResponse;
  }

  /**
   * @param product
   * @param gtSummaryPageUIResponse
   */
  private void buildResponse(Product product, GTSummaryUIResponse gtSummaryPageUIResponse) {
    LOGGER.info("buildResponse -> Start");
    List<GTAddonPojo> gtNonDisplayableItems = new ArrayList<>();
    List<GTAddonPojo> gtDisplayableItems = new ArrayList<>();
    /* set parent trunk attribute */
    Map<String, Object> productAttributes = product.getAttributes();
    LOGGER.debug("Product Attributes : {}", productAttributes);

    Core core = product.getCore();
    List<Map<String, Object>> productImgs = product.getImages();

    if (Objects.nonNull(productImgs) && !productImgs.isEmpty()) {
      Map<String, Object> productImgDetails = productImgs.get(0);
      gtSummaryPageUIResponse.setSeoImageAltText(
          BaseProductUIAdapter.transform(productImgDetails.get(GTSummaryPageUIAdapter.ALT_ATTR)));
    }
    gtSummaryPageUIResponse.setAttributes(productAttributes);
    gtSummaryPageUIResponse.setAuxDescription1(core.getTitle());
    gtSummaryPageUIResponse.setSeoUrlKeyword(core.getHandle());
    gtSummaryPageUIResponse.setBuyable(core.getProduct_buyable());
    gtSummaryPageUIResponse.setSeoMetaDescription(core.getGlobal_description_tag());
    gtSummaryPageUIResponse.setImageLink(core.getProduct_imagelink());

    if (Objects.nonNull(core.getProduct_thumnail())) {
      gtSummaryPageUIResponse.setThumbnail(
          this.getThumnailImageLink(core.getProduct_imagelink(), core.getProduct_thumnail()));
    } else if (Objects.nonNull(core.getProduct_fullimage())) {
      gtSummaryPageUIResponse.setThumbnail(
          this.getThumnailImageLink(core.getProduct_imagelink(), core.getProduct_fullimage()));
    } else {
      gtSummaryPageUIResponse.setThumbnail(core.getProduct_imagelink());
    }

    gtSummaryPageUIResponse.setProductType(core.getProduct_type());
    gtSummaryPageUIResponse.setPartNumber(product.getPartnumber());

    List<Variant> productVariants = product.getVariants();
    setTrunkVarintId(productVariants, gtSummaryPageUIResponse);

    /* get Association= details */
    List<Association> associations = product.getAssociations();
    for (Association association : associations) {
      if (StringUtils.isNotBlank(association.getAssociation_type())
          && KITCOMPONENT.equalsIgnoreCase(association.getAssociation_type())) {

        AssociatedProduct associatedProduct = association.getProduct();
        Map<String, Object> attributes = associatedProduct.getAttributes();

        if (attributes.containsKey(KITDISPLAYABLE)
            && Objects.nonNull(attributes.get(KITDISPLAYABLE)) && BaseProductUIAdapter
                .transform(attributes.get(KITDISPLAYABLE)).equalsIgnoreCase(KITDISPLAYABLE_Y)) {
          gtDisplayableItems.add(this.getAddonDetails(association, associatedProduct, attributes));
        } else {
          gtNonDisplayableItems
              .add(this.getAddonDetails(association, associatedProduct, attributes));
        }
      }
    }
    if (!gtDisplayableItems.isEmpty()) {
      gtDisplayableItems.sort(Comparator.comparing(GTAddonPojo::getKitDisplaySequence));
      gtSummaryPageUIResponse.setGtDisplayableItems(gtDisplayableItems);
    }
    gtSummaryPageUIResponse.setGtNonDisplayableItems(gtNonDisplayableItems);
    LOGGER.debug("gtSummaryPageUIResponse : {}", gtSummaryPageUIResponse);
    LOGGER.info("buildResponse -> End");
  }

  /**
   * @param association
   * @param associatedProduct
   * @param attributes
   * @return
   */
  private GTAddonPojo getAddonDetails(Association association, AssociatedProduct associatedProduct,
      Map<String, Object> attributes) {
    LOGGER.info("getAddonDetails -> Start");
    GTAddonPojo addonDetails = new GTAddonPojo();
    Core associatedProductCore = associatedProduct.getCore();
    List<Variant> assocvariants = associatedProduct.getVariants();
    addonDetails.setAssociationType(association.getAssociation_type());
    addonDetails.setBuyable(associatedProductCore.getProduct_buyable());
    addonDetails.setProductType(associatedProductCore.getProduct_type());
    addonDetails.setName(associatedProductCore.getTitle());
    addonDetails.setPartNumber(associatedProduct.getPartnumber());

    if (Objects.nonNull(associatedProductCore.getProduct_thumnail())) {
      addonDetails
          .setThumbnail(this.getThumnailImageLink(associatedProductCore.getProduct_imagelink(),
              associatedProductCore.getProduct_thumnail()));
    } else if (Objects.nonNull(associatedProductCore.getProduct_fullimage())) {
      addonDetails
          .setThumbnail(this.getThumnailImageLink(associatedProductCore.getProduct_imagelink(),
              associatedProductCore.getProduct_fullimage()));
    } else {
      addonDetails.setThumbnail(associatedProductCore.getProduct_imagelink());
    }

    addonDetails.setFullimage(associatedProductCore.getProduct_fullimage());
    if (Objects.nonNull(attributes.get(KITOPTION))) {
      addonDetails.setKitOption(BaseProductUIAdapter.transform(attributes.get(KITOPTION)));
    }

    if (Objects.nonNull(assocvariants) && !assocvariants.isEmpty() && !KITPRODUCTBEAN.equals(associatedProductCore.getProduct_type())) {
      Variant variant = assocvariants.get(0);
      if (Objects.nonNull(variant)) {
        addonDetails.setVariantId(variant.getId());
      }
    }

    if (Objects.nonNull(associatedProductCore.getProduct_type())
        && associatedProductCore.getProduct_type().equals(KITPRODUCTBEAN)) {
      List<Variant> variants = this.getVariants(associatedProduct.getPartnumber());

      if (variants != null && !variants.isEmpty()) {
        ProductSizeChartUtils.sortVariants(variants);
        addonDetails.setChildProducts(variants);
      }

    }

    if (Objects.nonNull(attributes.get(KITDISPLAYABLE))) {
      String displayString = BaseProductUIAdapter.transform(attributes.get(KITDISPLAYABLE));
      addonDetails.setKitDisplayable(displayString);
    }

    if (Objects.nonNull(attributes.get(KITDISPLAYSEQUENCE))) {
      String kitSequence = BaseProductUIAdapter.transform(attributes.get(KITDISPLAYSEQUENCE));
      addonDetails.setKitDisplaySequence(kitSequence);
    }

    if (Objects.nonNull(attributes.get(KITINSTRUCTIONS))) {
      addonDetails
          .setKitInstructions(BaseProductUIAdapter.transform(attributes.get(KITINSTRUCTIONS)));
    }

    if (Objects.nonNull(attributes.get(TRUNKDESCRIPTION))) {
      addonDetails
          .setTrunkDescription(BaseProductUIAdapter.transform(attributes.get(TRUNKDESCRIPTION)));
    }

    if (Objects.nonNull(attributes.get(OPTIONALNAME))) {
      addonDetails.setOptionalName(BaseProductUIAdapter.transform(attributes.get(OPTIONALNAME)));
    }

    LOGGER.info("getAddonDetails -> End");
    return addonDetails;
  }

  /**
   * @param parentProductSKU
   * @return
   */
  private List<Variant> getVariants(String parentProductSKU) {
    ProductServiceResponse productServiceResponse = null;
    requestMap.put(Constant.PART_NUMBER, parentProductSKU);
    List<Variant> variants = new ArrayList<>();
    try {
      productServiceResponse = this.productAvailability.fetch(requestMap);
      if (Objects.nonNull(productServiceResponse)) {
        Product product = productServiceResponse.getProduct();
        variants = product.getVariants();
      }
    } catch (ServiceException e) {
      LOGGER.error(String.format("Unable to get variants for sku: %s",parentProductSKU), e);
    }
    return variants;
  }

  /**
   * set variant Id to GTSummaryUIResponse
   */
  private void setTrunkVarintId(List<Variant> variants,
      GTSummaryUIResponse gtSummaryPageUIResponse) {
    if (Objects.nonNull(variants) && !variants.isEmpty()) {
      Variant variant = variants.get(0);
      if (Objects.nonNull(variant)) {
        gtSummaryPageUIResponse.setVariantId(variant.getId());
      }
    }
  }

  /**
   * @param imageLink
   * @param thumbnail
   * @return
   */
  private String getThumnailImageLink(String imageLink, String thumbnail) {
    String thumbnailURL = StringUtils.EMPTY;
    String imageURL = StringUtils.EMPTY;
    if (Objects.nonNull(thumbnail) && StringUtils.isNotEmpty(thumbnail)) {

      if (Objects.nonNull(imageLink) && StringUtils.isNotEmpty(imageLink)) {
        imageURL = imageLink.substring(0, imageLink.lastIndexOf('/') + 1);
        thumbnailURL = imageURL + thumbnail;
        LOGGER.debug("thumbnail link for image {} is {}", imageURL, thumbnailURL);
      } else {
        thumbnailURL = IMAGELINK + thumbnail;
      }

    }
    return thumbnailURL;
  }
}
