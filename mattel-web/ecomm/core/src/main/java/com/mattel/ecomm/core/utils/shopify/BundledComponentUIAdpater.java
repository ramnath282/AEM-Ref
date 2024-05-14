package com.mattel.ecomm.core.utils.shopify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.pojos.shopify.ComponentUIResponse;
import com.mattel.ecomm.core.utils.EcommConfigurationUtils;
import com.mattel.ecomm.core.utils.ProductAddOnUtils;
import com.mattel.ecomm.core.utils.ProductSizeChartUtils;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ProductAssociationMapping;
import com.mattel.ecomm.coreservices.core.pojos.shopify.AssociatedProduct;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Association;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Core;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Variant;

/**
 * Adapter to build Bundle Bean components.
 */
public class BundledComponentUIAdpater {
  private static final Logger LOGGER = LoggerFactory.getLogger(BundledComponentUIAdpater.class);

  private BundledComponentUIAdpater() {
    // no-op
  }

  public static ComponentUIResponse transform(Association association, String parentPartNumber) {
    BundledComponentUIAdpater.LOGGER.info("transform - Start");
    final ComponentUIResponse componentUIResponse = BundledComponentUIAdpater.buildComponent(association,
        parentPartNumber);
    BundledComponentUIAdpater.LOGGER.info("transform - End");
    return componentUIResponse;
  }

  /**
   * Builds the component object
   *
   * @param comp
   *          The association of type
   *          {@link ProductAssociationMapping#COMPONENT_TYPE}
   * @param parentPartNumber
   *          The parent part number.
   * @return The UI compatible component object
   */
  private static ComponentUIResponse buildComponent(Association assoc, String parentPartNumber) {
    final AssociatedProduct comp = assoc.getProduct();
    final ComponentUIResponse componentUIResponse = new ComponentUIResponse();
    final Map<String, Object> attributes = Optional.ofNullable(comp.getAttributes()).orElse(new HashMap<>());
    final Core core = Optional.ofNullable(comp.getCore()).orElse(new Core());
    List<Variant> variants = comp.getVariants();
    List<Association> associations = comp.getAssociations();
    String affrimInEligibleFlag = StringUtils.EMPTY;

    BundledComponentUIAdpater.LOGGER.info("buildComponent - Start");

    if (Objects.nonNull(variants) && !variants.isEmpty()) {
      BaseProductUIAdapter.checkForSwatches(comp.getOptions(), core, variants);
      componentUIResponse.setProduct_hasSwatches(core.getProduct_hasSwatches());
      BaseProductUIAdapter.populateInventory(variants);
      variants = ProductSizeChartUtils.sortVariants(variants);
    }

    componentUIResponse.setProduct_id(assoc.getProduct_id());
    componentUIResponse.setAssociation_sequence(assoc.getAssociation_sequence());
    componentUIResponse.setTitle(BaseProductUIAdapter.transform(core.getTitle()));
    componentUIResponse.setProduct_auxdescription(core.getProduct_auxdescription());
    componentUIResponse.setHandle(Objects.nonNull(core.getHandle()) ? core.getHandle() : StringUtils.EMPTY);
    componentUIResponse.setProduct_type(core.getProduct_type());
    componentUIResponse.setProduct_partnumber(comp.getPartnumber());
    componentUIResponse.setProduct_parentPartNumber(parentPartNumber);
    componentUIResponse.setProduct_fullimage(core.getProduct_fullimage());
    componentUIResponse.setProduct_thumnail(BaseProductUIAdapter.transform(core.getProduct_thumnail()));
    componentUIResponse.setProduct_imagelink(core.getProduct_imagelink());
    componentUIResponse.setProduct_buyable(core.getProduct_buyable());
    componentUIResponse.setProduct_isretailinventorycheckenabled(
        String.valueOf(!(StringUtils.isNotBlank(core.getProduct_isretailinventorycheckenabled())
            && StringUtils.equalsIgnoreCase(core.getProduct_isretailinventorycheckenabled(), "N"))));
    BundledComponentUIAdpater.addThumbnailImage(componentUIResponse);
    
    if (StringUtils.isNotEmpty(core.getProduct_relatedSizingChart())) {
      final String sizeChartDomainUri = EcommConfigurationUtils.getRootDomainSizeChart()
          + core.getProduct_relatedSizingChart();

      BundledComponentUIAdpater.LOGGER.debug("Fetching size chart link for style headers: {}", sizeChartDomainUri);
      componentUIResponse.setProduct_sizeChartLink(sizeChartDomainUri);
    }

    populateVariantPricing(comp, componentUIResponse);

    if (Objects.nonNull(core.getProduct_affirmIneligible())) {
      affrimInEligibleFlag = core.getProduct_affirmIneligible();
    }
    componentUIResponse.setProduct_affirmInEligibleFlag(affrimInEligibleFlag);

    if (Objects.nonNull(associations) && !associations.isEmpty()) {
      componentUIResponse.setProduct_hasAddOns(String.valueOf(ProductAddOnUtils.hasAddOn(associations)));
      componentUIResponse.setProduct_hasQuickSell(String.valueOf(BaseProductUIAdapter.hasQuickSell(associations)));
      associations = BundledComponentUIAdpater.filterAssociations(associations);
    }

    if (StringUtils.isNotBlank(core.getProduct_primarybundlesku())
        && Constant.YES_PLACEHOLDER.equals(core.getProduct_primarybundlesku())) {
      componentUIResponse.setProduct_isDynamicKit("true");
    }

    componentUIResponse.setOptions(comp.getOptions());
    componentUIResponse.setAttributes(attributes);
    componentUIResponse.setAssociations(associations);
    componentUIResponse.setVariants(variants);
    BundledComponentUIAdpater.LOGGER.info("buildComponent - end");
    return componentUIResponse;
  }

  private static void populateVariantPricing(final AssociatedProduct comp,
      final ComponentUIResponse componentUIResponse) {
    try {
    Optional.ofNullable(comp.getVariants()).filter(l -> !l.isEmpty()).map(l -> l.get(0))
        .filter(l -> Objects.nonNull(l.getPricing()))
        .ifPresent(v -> componentUIResponse.setPrice(v.getPricing().getPrice()));
    } catch (Exception e) {
      LOGGER.error("Unable to pricing of association", e);
    }
  }

  private static List<Association> filterAssociations(List<Association> associations) {
    final List<Association> associationList = new ArrayList<>();

    if (Objects.nonNull(associations) && !associations.isEmpty()) {
      associations.forEach(assocation -> {
        if (Objects.nonNull(assocation.getAssociation_type())) {
          final boolean validAssociation = Arrays.stream(ProductAssociationMapping.ASSOCIATIONS.getAssociationTypes())
              .anyMatch(assocation.getAssociation_type()::equalsIgnoreCase);
          if (validAssociation) {
            associationList.add(assocation);
          }
        }
      });
    }

    return associationList;
  }

  /**
   * To populate thumbnail image of individual components.
   */
  private static void addThumbnailImage(ComponentUIResponse componentUIResponse) {
    String imageLink = componentUIResponse.getProduct_imagelink();

    if (StringUtils.isBlank(imageLink) || !StringUtils.contains("http", imageLink)) {
      imageLink = Constant.PLACEHOLDER_IMAGE;
    }

    if (StringUtils.isNotBlank(componentUIResponse.getProduct_thumnail())) {
      final String prefix = imageLink.substring(0, imageLink.lastIndexOf(Constant.SLASH_CHAR) + 1);

      final String finalImageUrl = new StringBuilder(prefix).append(componentUIResponse.getProduct_thumnail())
          .append(Constant.QUESTION).append(Constant.IMAGE_PRESET_SMALL_INDEX).toString();
      componentUIResponse.setProduct_imagelink(finalImageUrl);
    }
  }
}
