package com.mattel.ecomm.core.pojos;

import java.util.List;

/**
 * @author CTS
 *
 */
public class GiftCardResponse extends PDPProductUIResponse {
  private String giftDisclamer;
  private List<GiftCardMessage> messages;

  public String getGiftDisclamer() {
    return giftDisclamer;
  }

  public void setGiftDisclamer(String giftDisclamer) {
    this.giftDisclamer = giftDisclamer;
  }

  public List<GiftCardMessage> getMessages() {
    return messages;
  }

  public void setMessages(List<GiftCardMessage> messages) {
    this.messages = messages;
  }

  @Override
  public String toString() {
    return "GiftCardResponse [giftDisclamer=" + giftDisclamer + ", messages=" + messages + "]";
  }

}
