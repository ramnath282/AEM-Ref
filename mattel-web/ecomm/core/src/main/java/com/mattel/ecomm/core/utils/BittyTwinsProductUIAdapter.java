package com.mattel.ecomm.core.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.pojos.DollsPojo;
import com.mattel.ecomm.core.pojos.KitPojo;
import com.mattel.ecomm.core.pojos.PDPProductUIResponse;
import com.mattel.ecomm.core.pojos.SleepersPojo;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.pojos.Product;
import com.mattel.ecomm.coreservices.core.pojos.ProductAssociation;

public class BittyTwinsProductUIAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(BittyTwinsProductUIAdapter.class);

  private BittyTwinsProductUIAdapter() {
    // no-op
  }

  /**
   * Transform the fetched {@link Product} details of from service to output
   * UI compatible {@link PDPProductUIResponse response} object for Bitty
   * Twins.
   *
   * @param product
   *            The incoming {@link Product product} details.
   * @param pdpProductUIResponse
   *            {@link PDPProductUIResponse response} object.
   */
  public static void transformProductToBittyTwins(Product product, PDPProductUIResponse pdpProductUIResponse) {
    BittyTwinsProductUIAdapter.LOGGER.info("TransformProductToSignleSKU - Start");
    BittyTwinsProductUIAdapter.buildResponse(product, pdpProductUIResponse);
    BittyTwinsProductUIAdapter.LOGGER.info("TransformProductToSignleSKU - End");
  }

  /**
   * Filter the product association based on association Type and process
   * those associations individually
   * 
   * @param product
   *            The incoming {@link Product product} details
   * @param pdpProductUIResponse
   *            {@link PDPProductUIResponse response} object.
   */
  private static void buildResponse(Product product, PDPProductUIResponse pdpProductUIResponse) {
    BittyTwinsProductUIAdapter.LOGGER.info("buildResponse - Start");
    final List<ProductAssociation> kitAssociations = filterAssociations(product.getAssociations(),
        Constant.KITCOMPONENT_ASSOCIATION_TYPE);
    final List<ProductAssociation> bittyTwinAssociations = filterAssociations(product.getAssociations(),
        Constant.BITTY_TWIN_ASSOCIATION_TYPE);
    final StringBuilder hiddenSkuStr = new StringBuilder();

    kitAssociations.forEach(association -> {
      String kitDisplyable = StringUtils
          .isNotBlank(getSpecificAttribute(association.getAttributes(), Constant.KIT_DISPLAYABLE))
              ? getSpecificAttribute(association.getAttributes(), Constant.KIT_DISPLAYABLE) : "Y";
      if (!association.getAttributes().isEmpty() && "N".equals(kitDisplyable)) {
        hiddenSkuStr.append(association.getPartNumber());
        hiddenSkuStr.append(",");
      }
    });

    hiddenSkuStr.deleteCharAt(hiddenSkuStr.length() - 1);
    pdpProductUIResponse.setKitComponentHiddenPartNumbers(hiddenSkuStr.toString());

    final Map<String, List<ProductAssociation>> associationMap = bittyTwinAssociations.stream()
          .filter(association -> Objects.nonNull(association.getAttributes())
                  && Objects.nonNull(association.getAttributes().get(Constant.BB_SLEEPER)))
              .collect(Collectors.groupingBy(association -> ProductUIAdapter.transform(association.getAttributes().get(Constant.BB_SLEEPER)),LinkedHashMap::new,Collectors.toList()));
    
    LOGGER.debug("associationMap is {}", associationMap);

    readBittyTwinsData(associationMap, pdpProductUIResponse);
    BittyTwinsProductUIAdapter.LOGGER.info("buildResponse - End");
  }

  /**
   * Reads Bitty Twins Associations and run the transformation on them to
   * build final UI object
   * 
   * @param associationMap
   *            map of unique sleeper types across respective associations
   * @param pdpProductUIResponse
   *            {@link PDPProductUIResponse response} object.
   */
  private static void readBittyTwinsData(Map<String, List<ProductAssociation>> associationMap,
      PDPProductUIResponse pdpProductUIResponse) {
    BittyTwinsProductUIAdapter.LOGGER.info("readBittyTwinsData - Start");

    List<SleepersPojo> sleeperList = new ArrayList<>();
    for (final Map.Entry<String, List<ProductAssociation>> entrySet : associationMap.entrySet()) {
      final String sleeperType = entrySet.getKey();
      final List<ProductAssociation> associationsList = entrySet.getValue();
      SleepersPojo sleepersPojo = new SleepersPojo();
      sleepersPojo.setSleeperType(sleeperType);

      if (Objects.nonNull(associationsList) && !associationsList.isEmpty()) {
        Optional<ProductAssociation> result = associationsList.stream().findFirst();
        if (result.isPresent()) {
          String sleeperImage = getSpecificAttribute(result.get().getAttributes(),
              Constant.BB_SLEEPER_THUMBNAIL);
          sleepersPojo.setImageURL(sleeperImage);
        }
        
       sleepersPojo.setHasFirstSequenceDoll(associationsList.stream()
            .filter(association -> Objects
                .nonNull(association.getAttributes().get(Constant.KIT_DEFAULT_SEQUENCE)))
            .anyMatch(association -> "1".equals(
                getSpecificAttribute(association.getAttributes(), Constant.KIT_DEFAULT_SEQUENCE))));
       
       sleepersPojo.setHasSecondSequenceDoll(associationsList.stream()
               .filter(association -> Objects
                   .nonNull(association.getAttributes().get(Constant.KIT_DEFAULT_SEQUENCE)))
               .anyMatch(association -> "2".equals(
                   getSpecificAttribute(association.getAttributes(), Constant.KIT_DEFAULT_SEQUENCE))));
        
        List<DollsPojo> dollsList = setDollsList(associationsList);
        dollsList.sort(Comparator.comparing(DollsPojo::getKitDisplaySequence));
        sleepersPojo.setDolls(dollsList);
      }
      sleeperList.add(sleepersPojo);
    }

    KitPojo kitPojo = new KitPojo();
    kitPojo.setSleepers(sleeperList);
    pdpProductUIResponse.setPrimaryKit(kitPojo);
    pdpProductUIResponse.setSecondaryKit(kitPojo);
    LOGGER.debug("final transformed kit pojo is: {}", kitPojo);
    BittyTwinsProductUIAdapter.LOGGER.info("readBittyTwinsData - End");

  }

  /**
   * Generates the List of Doll Pojo objects by reading the filtered
   * association across a certain Sleeper type
   * 
   * @param associationsList product Association List
   * @return dollsList
   */
  private static List<DollsPojo> setDollsList(final List<ProductAssociation> associationsList) {
    List<DollsPojo> dollsList = new ArrayList<>();
    associationsList.forEach(association -> {
      if (Objects.nonNull(association.getAttributes()) && !association.getAttributes().isEmpty()) {
        String kitDisplyable = StringUtils
            .isNotBlank(getSpecificAttribute(association.getAttributes(), Constant.KIT_DISPLAYABLE))
                ? getSpecificAttribute(association.getAttributes(), Constant.KIT_DISPLAYABLE) : "Y";
        if (!"N".equals(kitDisplyable)) {
          DollsPojo dollsPojo = new DollsPojo();
          dollsPojo.setDollType(getSpecificAttribute(association.getAttributes(), Constant.BB_DOLLTYPE));
          dollsPojo.setImageURL(getDollImageURL(association));
          dollsPojo.setKitDisplaySequence(
                  getSpecificAttribute(association.getAttributes(), Constant.KIT_DISPLAY_SEQUENCE));
          dollsPojo.setKitDefaultSequence(getSpecificAttribute(association.getAttributes(), Constant.KIT_DEFAULT_SEQUENCE));
          dollsPojo.setPartNumber(association.getPartNumber());
          dollsList.add(dollsPojo);
        }
      }
    });
    return dollsList;
  }
  
  /**
   * This method reads the imageLink and thumbnail attribute 
   * and forms the final imageURL
   * 
   * @param association product association
   * @return imageURL doll image URL
   */
  private static String getDollImageURL(ProductAssociation association){
  String imageURL = StringUtils.EMPTY;
  if(StringUtils.isNotBlank(association.getImageLink()) && StringUtils.isNotBlank(association.getThumbnail())){
    String str = association.getImageLink().substring(0,association.getImageLink().lastIndexOf('/')+1);
    imageURL = str.concat(association.getThumbnail());
  }
  return imageURL;
  }

  /**
   * This methods check of the attributes map has a certain key If key is
   * present and not null then returns the transformed string value
   * 
   * @param attributes
   *            product attributes map
   * @param attributeName
   *            Attribute Name
   * @return
   */
  private static String getSpecificAttribute(Map<String, Object> attributes, String attributeName) {
    String attributeValue = StringUtils.EMPTY;
    if (attributes.containsKey(attributeName) && Objects.nonNull(attributes.get(attributeName))) {
      attributeValue = ProductUIAdapter.transform(attributes.get(attributeName));
    }
    return attributeValue;
  }

  /**
   * This method filters the association list across the valid association
   * types.
   *
   * @param associations
   *            Initial associations received from WCS service
   * @param associationType
   *            Specific Association type across which filtering should be
   *            done.
   * @return associationList filtered association list based on association
   *         type
   */
  private static List<ProductAssociation> filterAssociations(List<ProductAssociation> associations,
      String associationType) {
    final List<ProductAssociation> associationList = new ArrayList<>();
    if (Objects.nonNull(associations) && !associations.isEmpty()) {
      associations.forEach(association -> {
        if (Objects.nonNull(association.getAssociationType())) {
          final boolean validAssociation = associationType.equals(association.getAssociationType());

          if (validAssociation) {
            associationList.add(association);
          }
        }
      });
    }
    return associationList;
  }

}
