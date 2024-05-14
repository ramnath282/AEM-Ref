package com.mattel.ecomm.core.utils.shopify;

import com.mattel.ecomm.core.pojos.shopify.DollsPojo;
import com.mattel.ecomm.core.pojos.shopify.KitPojo;
import com.mattel.ecomm.core.pojos.shopify.ProductUIResponse;
import com.mattel.ecomm.core.pojos.shopify.SleepersPojo;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Association;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Product;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class BittyTwinsProductUIAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(BittyTwinsProductUIAdapter.class);
    private BittyTwinsProductUIAdapter() {
        // no-op
    }

    /**
     * Transform the fetched {@link Product} details of from service to output
     * UI compatible {@link ProductUIResponse response} object for Bitty
     * Twins.
     *
     * @param product
     *            The incoming {@link Product product} details.
     * @param shopifyProductUIResponse
     *            {@link ProductUIResponse response} object.
     */

    public static ProductUIResponse transformProductToBittyTwins(Product product, ProductUIResponse shopifyProductUIResponse) {
        BittyTwinsProductUIAdapter.LOGGER.info("transformProductToBittyTwins - Start");
        BittyTwinsProductUIAdapter.buildResponse(product,shopifyProductUIResponse);
        BittyTwinsProductUIAdapter.LOGGER.info("transformProductToBittyTwins - End");
        return shopifyProductUIResponse;
    } 

    /**
     * Filter the product association based on association Type and process
     * those associations individually
     *
     * @param product
     *            The incoming {@link Product product} details
     * @param shopifyProductUIResponse
     *            {@link ProductUIResponse response} object.
     */
    private static void buildResponse(Product product, ProductUIResponse shopifyProductUIResponse){
        BittyTwinsProductUIAdapter.LOGGER.info("buildResponse - Start");
        final List<Association> kitAssociations = filterAssociations(product.getAssociations(),
            Constant.KITCOMPONENT_ASSOCIATION_TYPE);
        final List<Association> bittyTwinAssociations = filterAssociations(product.getAssociations(),Constant.BITTY_TWIN_ASSOCIATION_TYPE);
        LOGGER.debug("bittyTwinAssociations length is {} and kitAssociations length is {} ",bittyTwinAssociations.size(),kitAssociations.size());
        List<DollsPojo> dollsHiddenDataList = new ArrayList<>();
        kitAssociations.forEach(association -> {
          DollsPojo kitComponentProperties = new DollsPojo();
          if (Objects.nonNull(association.getProduct().getPartnumber())){
            kitComponentProperties.setPartNumber(association.getProduct().getPartnumber());
          }
          if (Objects.nonNull(association.getProduct().getVariants()) && association.getProduct().getVariants().size()>0){
            kitComponentProperties.setVariantId(association.getProduct().getVariants().get(0).getId());
          }
          if (Objects.nonNull(association.getAssociation_type())){
            kitComponentProperties.setAssociationType(association.getAssociation_type());
          }
          if (Objects.nonNull(association.getProduct().getCore()) && Objects.nonNull(association.getProduct().getCore().getProduct_type())) {
            kitComponentProperties.setProductType(association.getProduct().getCore().getProduct_type());
          }
          dollsHiddenDataList.add(kitComponentProperties);
        });
        shopifyProductUIResponse.setKitComponentHiddenParts(dollsHiddenDataList);
 
        Map<String, List<Association>> associationMap = bittyTwinAssociations.stream().filter(association -> Objects
            .nonNull(association.getProduct()) && Objects.nonNull(association.getProduct().getAttributes().get("BB_Sleeper"))).collect
            (Collectors.groupingBy(association -> BaseProductUIAdapter.transform(association.getProduct().getAttributes().get("BB_Sleeper")),LinkedHashMap::new,Collectors.toList()));
        readBittyTwinsData(associationMap,shopifyProductUIResponse);
        BittyTwinsProductUIAdapter.LOGGER.debug("associationMap is {}",associationMap.toString());
        BittyTwinsProductUIAdapter.LOGGER.info("buildResponse - End");
    }

    /**
     * Reads Bitty Twins Associations and run the transformation on them to
     * build final UI object
     *
     * @param associationMap
     *            map of unique sleeper types across respective associations
     * @param shopifyProductUIResponse
     *            {@link ProductUIResponse response} object.
     */
    private static void readBittyTwinsData(Map<String, List<Association>> associationMap,
        ProductUIResponse shopifyProductUIResponse){
        BittyTwinsProductUIAdapter.LOGGER.info("readBittyTwinsData - Start");
        List<SleepersPojo> sleeperList = new ArrayList<>();
        for (final Map.Entry<String, List<Association>> entrySet : associationMap.entrySet()) {
            final String sleeperType = entrySet.getKey();
            final List<Association> associationsList = entrySet.getValue();
            SleepersPojo sleepersPojo = new SleepersPojo();
            sleepersPojo.setSleeperType(sleeperType);
            Optional<Association> result = associationsList.stream().findFirst();
            if(result.isPresent()){
                String sleeperImage = BaseProductUIAdapter.transform(result.get().getProduct().getAttributes().get(Constant.BB_SWATCH_IMAGE));
                sleepersPojo.setImageURL(sleeperImage);
            }
            sleepersPojo.setHasFirstSequenceDoll(associationsList.stream()
                .filter(association -> Objects
                    .nonNull(association.getProduct().getAttributes().get(Constant.KIT_DEFAULT_SEQUENCE)))
                .anyMatch(association -> "1".equals(
                    getSpecificAttribute(association.getProduct().getAttributes(), Constant.KIT_DEFAULT_SEQUENCE))));

            sleepersPojo.setHasSecondSequenceDoll(associationsList.stream()
                .filter(association -> Objects
                    .nonNull(association.getProduct().getAttributes().get(Constant.KIT_DEFAULT_SEQUENCE)))
                .anyMatch(association -> "2".equals(
                    getSpecificAttribute(association.getProduct().getAttributes(), Constant.KIT_DEFAULT_SEQUENCE))));

            List<DollsPojo> dollsList = setDollsList(associationsList);
            dollsList.sort(Comparator.comparing(DollsPojo::getKitDisplaySequence));
            sleepersPojo.setDolls(dollsList);
            sleeperList.add(sleepersPojo);
        }
        KitPojo kitPojo = new KitPojo();
        kitPojo.setSleepers(sleeperList);
        shopifyProductUIResponse.setPrimaryKit(kitPojo);
        shopifyProductUIResponse.setSecondaryKit(kitPojo);
        LOGGER.debug("final transformed kit pojo is "+kitPojo);
        BittyTwinsProductUIAdapter.LOGGER.info("readBittyTwinsData - End");
    }
    /**
     * Generates the List of Doll Pojo objects by reading the filtered
     * association across a certain Sleeper type
     *
     * @param associationsList product Association List
     * @return dollsList
     */
    private static List<DollsPojo> setDollsList(List<Association> associationsList) {
        BittyTwinsProductUIAdapter.LOGGER.info("setDollsList - Start");
        List<DollsPojo> dollsList = new ArrayList<>();
        associationsList.forEach(association -> {
            if (Objects.nonNull(association.getProduct().getAttributes()) && !association.getProduct().getAttributes().isEmpty()) {
                String kitDisplyable = StringUtils
                    .isNotBlank(getSpecificAttribute(association.getProduct().getAttributes(), Constant.KIT_DISPLAYABLE))
                    ? getSpecificAttribute(association.getProduct().getAttributes(), Constant.KIT_DISPLAYABLE) : "Y";
                if (!"N".equals(kitDisplyable)) {
                    DollsPojo dollsPojo = new DollsPojo();
                    dollsPojo.setDollType(getSpecificAttribute(association.getProduct().getAttributes(), Constant.BB_DOLLTYPE));
                    dollsPojo.setImageURL(getDollImageURL(association));
                    if (Objects.nonNull(association.getProduct().getVariants()) && association.getProduct().getVariants().size()>0){
                        dollsPojo.setVariantId(association.getProduct().getVariants().get(0).getId());
                    }
                    dollsPojo.setKitDisplaySequence(
                        getSpecificAttribute(association.getProduct().getAttributes(), Constant.KIT_DISPLAY_SEQUENCE));
                    dollsPojo.setKitDefaultSequence(getSpecificAttribute(association.getProduct().getAttributes(), Constant.KIT_DEFAULT_SEQUENCE));
                    dollsPojo.setPartNumber(association.getProduct().getPartnumber());
                    dollsPojo.setProduct_buyable(association.getProduct().getCore().getProduct_buyable());
                    if (Objects.nonNull(association.getAssociation_type())) {
                      dollsPojo.setAssociationType(association.getAssociation_type());
                    }
                    if (Objects.nonNull(association.getProduct().getCore().getProduct_type())) {
                      dollsPojo.setProductType(association.getProduct().getCore().getProduct_type());
                    }
                    dollsList.add(dollsPojo);
                }
            }
        });
        BittyTwinsProductUIAdapter.LOGGER.info("setDollsList - End");
        return dollsList;
    }
    /**
     * This method reads the imageLink and thumbnail attribute
     * and forms the final imageURL
     *
     * @param association product association
     * @return imageURL doll image URL
     */
    private static String getDollImageURL(Association association) {
        BittyTwinsProductUIAdapter.LOGGER.info("getDollImageURL - Start");
        String imageURL = StringUtils.EMPTY;
        String kitThumbnail = getSpecificAttribute(association.getProduct().getAttributes(), Constant.KIT_THUMBNAIL);
        if(StringUtils.isNotBlank(association.getProduct().getCore().getProduct_imagelink()) && StringUtils.isNotBlank(kitThumbnail)){
            String imageLink = association.getProduct().getCore().getProduct_imagelink();
            LOGGER.debug("Image link is {}",imageLink);
            String relativeImagePath = imageLink.substring(0,imageLink.lastIndexOf('/')+1);
            imageURL = relativeImagePath.concat(kitThumbnail);
            LOGGER.debug("Image Url Is {}",imageLink);
        }
        BittyTwinsProductUIAdapter.LOGGER.info("getDollImageURL - End");
        return imageURL;
    }

    /**
     * This method filters the association list across the valid association types.
     *
     * @param associations
     *          Initial associations received from WCS service
     * @return associationList filtered association list based on association type for Bitty Twins
     */
    private static List<Association> filterAssociations(
        List<Association> associations, String associationType) {
        LOGGER.debug("filterAssociations start");
        final List<Association> associationList = new ArrayList<>();
        if (Objects.nonNull(associations) && !associations.isEmpty()) {
            associations.forEach(assocation -> {
                if (Objects.nonNull(assocation.getAssociation_type())) {
                    final boolean validAssociation = associationType.equals(assocation.getAssociation_type());
                    if (validAssociation) {
                        associationList.add(assocation);
                    }
                }
            });
        }
        return associationList;
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
            attributeValue = BaseProductUIAdapter.transform(attributes.get(attributeName));
        }
        return attributeValue;
    }
    
}
