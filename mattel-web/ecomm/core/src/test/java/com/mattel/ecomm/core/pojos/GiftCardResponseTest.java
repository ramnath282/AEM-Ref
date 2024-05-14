package com.mattel.ecomm.core.pojos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GiftCardResponseTest {

  GiftCardResponse giftCardResponse;

  @Before
  public void setUp() {
    giftCardResponse = new GiftCardResponse();
    giftCardResponse.setGiftDisclamer("giftDisclamer");
    GiftCardMessage giftCardMessage = new GiftCardMessage();
    giftCardMessage.setMessage1("Gift Card Message1");
    giftCardMessage.setMessage2("Gift Card Message2");
    giftCardMessage.setMessage3("Gift Card Message3");
    giftCardMessage.setMessageTitle("Gift Card Title");
    giftCardMessage.setSequence("2");
    List<GiftCardMessage> messagesList = new ArrayList<GiftCardMessage>();
    messagesList.add(giftCardMessage);
    giftCardResponse.setMessages(messagesList);
  }

  @Test
  public void testGetMethods() {
    Assert.assertEquals("giftDisclamer", giftCardResponse.getGiftDisclamer());
    Assert.assertNotNull(giftCardResponse.getMessages());
    Assert.assertEquals("Gift Card Message1", giftCardResponse.getMessages().get(0).getMessage1());
    Assert.assertEquals("Gift Card Message2", giftCardResponse.getMessages().get(0).getMessage2());
    Assert.assertEquals("Gift Card Message3", giftCardResponse.getMessages().get(0).getMessage3());
    Assert.assertEquals("Gift Card Title", giftCardResponse.getMessages().get(0).getMessageTitle());
    Assert.assertEquals("2", giftCardResponse.getMessages().get(0).getSequence());
    Assert.assertNotNull(giftCardResponse.toString());
  }

}
