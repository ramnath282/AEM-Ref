package com.mattel.ecomm.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.pojos.GiftCardMessage;
import com.mattel.ecomm.core.pojos.GiftCardUIResponse;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.core.utils.EcomUtil;
import com.mattel.ecomm.core.utils.EcommConfigurationUtils;
import com.mattel.ecomm.core.utils.GiftCardSkuProcessor;
import com.mattel.ecomm.core.utils.ProductSchemaBuilder;
import com.mattel.ecomm.core.utils.shopify.ProductUIAdapter;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAvailability;
import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Product;

@Model(adaptables = { Resource.class,
    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GiftCardInfoModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(GiftCardInfoModel.class);

  @Inject
  ProductAvailability productAvailability;

  @SlingObject
  SlingHttpServletRequest request;

  @Inject
  private MultifieldReader multifieldReader;

  @Inject
  @Via("resource")
  private Node giftMessage;

  private String giftCardSeoSchema;

  GiftCardUIResponse giftCardUIResponse;
  private String atPropertyTarget;

  @PostConstruct
  protected void init() {
    final long startTime = System.currentTimeMillis();

    GiftCardInfoModel.LOGGER.info("GiftCardInfoModel Init Start");

    try {
      final String[] selectors = request.getRequestPathInfo().getSelectors();
      final String storeDomainKey;
      String partNumber;

            if (selectors.length >= 2) {
                storeDomainKey = selectors[0];
                partNumber = selectors[1];

                if (!StringUtils.isEmpty(storeDomainKey) && !StringUtils.isEmpty(partNumber)) {
                    final long apiStartTime;
                    partNumber = GiftCardSkuProcessor.checkAndUnescapeDelimiter(partNumber);
                    GiftCardInfoModel.LOGGER.debug("Domain and Store selector : {}", storeDomainKey);
                    GiftCardInfoModel.LOGGER.debug("Part Number : {}", partNumber);
                    final Map<String, Object> requestMap = new HashMap<>();
                    requestMap.put(Constant.STORE_KEY, storeDomainKey);
                    requestMap.put(Constant.DOMAIN_KEY, storeDomainKey);
                    requestMap.put(Constant.PART_NUMBER, partNumber);
                    apiStartTime = System.currentTimeMillis();
                    final ProductServiceResponse productServiceResponse = productAvailability.fetch(requestMap);
                    GiftCardInfoModel.LOGGER.debug("GiftCardInfoModel - delta time product api :{}",
                        System.currentTimeMillis() - apiStartTime);
                    formatProduct(productServiceResponse);
                    giftCardUIResponse.setCanonicalUrl(EcomUtil.buildCanonicalTag(new HashMap<>(), request));
                    buildGiftCardSchema(giftCardUIResponse);
                    GiftCardInfoModel.LOGGER.debug("Gift card: {} attributes : {}",
                            new Object[] { partNumber, giftCardUIResponse });
                    GiftCardInfoModel.LOGGER.info("GiftCardInfoModel Init End");
                }
            } else {
                GiftCardInfoModel.LOGGER.error("Invalid gift card details request encountered");
                giftCardUIResponse = new GiftCardUIResponse();
            }
    } catch (final RuntimeException e) {
      GiftCardInfoModel.LOGGER.error("Unknown exception occured while building gift card model", e);
      giftCardUIResponse = new GiftCardUIResponse();
    } catch (final ServiceException e) {
      GiftCardInfoModel.LOGGER.error("Internal service failure encountered", e);
      giftCardUIResponse = new GiftCardUIResponse();
    }

    GiftCardInfoModel.LOGGER.debug("GiftCardInfoModel - delta time init method :{}",
        System.currentTimeMillis() - startTime);
  }

  private void buildGiftCardSchema(GiftCardUIResponse giftCardNewResponse) {
    try {
      GiftCardInfoModel.LOGGER.debug("Building gift card seo schema for product: {}",
          giftCardNewResponse);
      giftCardSeoSchema = ProductSchemaBuilder.buildCompositeProductSchema(giftCardNewResponse);
    } catch (final Exception e) {
      GiftCardInfoModel.LOGGER.error("Exception occured while building gift card seo schema", e);
    }
  }

  private void formatProduct(ProductServiceResponse productServiceResponse) {
    GiftCardInfoModel.LOGGER.info("GiftCardInfoModel formatProduct Start");
    final Product product = productServiceResponse.getProduct();

    if (Objects.nonNull(product)) {
      giftCardUIResponse = ProductUIAdapter.transformProductToGiftCard(product);
    }

    if (Objects.nonNull(giftCardUIResponse) && Objects.nonNull(giftMessage)) {
      final Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(giftMessage);
      GiftCardInfoModel.LOGGER.info("multifieldProperty : {}", multifieldProperty);
      final List<GiftCardMessage> messages = new ArrayList<>();
      int sequence = 1;

      for (final Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
        final GiftCardMessage message = new GiftCardMessage();

        message.setMessageTitle(entry.getValue().get("messageTitle", String.class));
        message.setMessage1(entry.getValue().get("message1", String.class));
        message.setMessage2(entry.getValue().get("message2", String.class));
        message.setMessage3(entry.getValue().get("message3", String.class));
        message.setSequence(String.valueOf(sequence));
        sequence++;
        messages.add(message);
      }

      giftCardUIResponse.setMessages(messages);
      setAtTargetLink();
    }

    GiftCardInfoModel.LOGGER.info("GiftCardInfoModel formatProduct End");
  }

  private void setAtTargetLink() {
    GiftCardInfoModel.LOGGER.debug("GiftCardInfoModel setAtTargetLink Start");
    atPropertyTarget = EcommConfigurationUtils.getAtPropertyTarget();
    GiftCardInfoModel.LOGGER.debug("GiftCardInfoModel setAtTargetLink End ");
  }

  public GiftCardUIResponse getGiftCardUIResponse() {
    return giftCardUIResponse;
  }

  public void setMultifieldReader(MultifieldReader multifieldReader) {
    this.multifieldReader = multifieldReader;
  }

  public String getAtPropertyTarget() {
    return atPropertyTarget;
  }

  public String getGiftCardSeoSchema() {
    return giftCardSeoSchema;
  }

  public void setGiftCardSeoSchema(String giftCardSeoSchema) {
    this.giftCardSeoSchema = giftCardSeoSchema;
  }
}
