package com.mattel.ecomm.core.models;

import java.io.IOException;
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
import com.mattel.ecomm.core.pojos.GiftCardResponse;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.core.utils.EcomUtil;
import com.mattel.ecomm.core.utils.EcommConfigurationUtils;
import com.mattel.ecomm.core.utils.GiftCardSkuProcessor;
import com.mattel.ecomm.core.utils.ProductSchemaBuilder;
import com.mattel.ecomm.core.utils.ProductUIAdapter;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.PDPProduct;
import com.mattel.ecomm.coreservices.core.pojos.PDPResponse;
import com.mattel.ecomm.coreservices.core.pojos.Product;

@Model(adaptables = { Resource.class,
    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GiftCardFormModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(GiftCardFormModel.class);

  @Inject
  PDPProduct pdpProduct;

  @SlingObject
  SlingHttpServletRequest request;

  @Inject
  private MultifieldReader multifieldReader;

  @Inject
  @Via("resource")
  private Node giftMessage;

  private String giftCardSeoSchema;

  GiftCardResponse giftCardResponse;
  private String atPropertyTarget;

  @PostConstruct
  protected void init() {
    GiftCardFormModel.LOGGER.info("GiftCardFormModel Init Start");

    try {
      final String[] selectors = request.getRequestPathInfo().getSelectors();
      final String storeDomainKey;
      String partNumber;

            if (selectors.length >= 2) {
                storeDomainKey = selectors[0];
                partNumber = selectors[1];

                if (!StringUtils.isEmpty(storeDomainKey) && !StringUtils.isEmpty(partNumber)) {
                    partNumber = GiftCardSkuProcessor.checkAndUnescapeDelimiter(partNumber);
                    GiftCardFormModel.LOGGER.debug("Domain and Store selector : {}", storeDomainKey);
                    GiftCardFormModel.LOGGER.debug("Part Number : {}", partNumber);
                    final Map<String, Object> requestMap = new HashMap<>();
                    requestMap.put(Constant.STORE_KEY, storeDomainKey);
                    requestMap.put(Constant.DOMAIN_KEY, storeDomainKey);
                    requestMap.put(Constant.PART_NUMBER, partNumber);
                    final PDPResponse pdpResponse = pdpProduct.fetch(requestMap);
                    formatProduct(pdpResponse);
                    giftCardResponse.setCanonicalUrl(EcomUtil.buildCanonicalTag(new HashMap<>(), request));
                    buildGiftCardSchema(giftCardResponse);
                    GiftCardFormModel.LOGGER.debug("Gift card: {} attributes : {}",
                            new Object[] { partNumber, giftCardResponse });
                    GiftCardFormModel.LOGGER.info("GiftCardFormModel Init End");
                }
            } else {
                GiftCardFormModel.LOGGER.error("Invalid gift card details request encountered");
                giftCardResponse = new GiftCardResponse();
            }
    } catch (final RuntimeException e) {
      GiftCardFormModel.LOGGER.error("Unknown exception occured while building gift card model", e);
      giftCardResponse = new GiftCardResponse();
    } catch (final ServiceException e) {
      GiftCardFormModel.LOGGER.error("Internal service failure encountered", e);
      giftCardResponse = new GiftCardResponse();
    }
  }

  private void buildGiftCardSchema(GiftCardResponse giftCardResponse) {
    try {
      GiftCardFormModel.LOGGER.debug("Building gift card seo schema for product: {}",
          giftCardResponse);
      giftCardSeoSchema = ProductSchemaBuilder.buildCompositeProductSchema(giftCardResponse);
    } catch (final IOException e) {
      GiftCardFormModel.LOGGER.error("Exception occured while building gift card seo schema", e);
    }
  }

  private void formatProduct(PDPResponse pdpResponse) {
    GiftCardFormModel.LOGGER.info("GiftCardFormModel formatProduct Start");
    final Map<String, Product> catalogView = pdpResponse.getCatalogEntryView().get(0);
    final Product product = catalogView.get("product");
    if (Objects.nonNull(product)) {
      giftCardResponse = ProductUIAdapter.transformProductToGiftCard(product);
    }

    if (Objects.nonNull(giftCardResponse) && Objects.nonNull(giftMessage)) {
      final Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(giftMessage);
      GiftCardFormModel.LOGGER.info("multifieldProperty : {}", multifieldProperty);
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

      giftCardResponse.setMessages(messages);
      setAtTargetLink();
    }

    GiftCardFormModel.LOGGER.info("GiftCardFormModel formatProduct End");
  }

  private void setAtTargetLink() {
    GiftCardFormModel.LOGGER.debug("GiftCardFormModel setAtTargetLink Start");
    atPropertyTarget = EcommConfigurationUtils.getAtPropertyTarget();
    GiftCardFormModel.LOGGER.debug("GiftCardFormModel setAtTargetLink End ");
  }

  public GiftCardResponse getGiftCardResponse() {
    return giftCardResponse;
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
