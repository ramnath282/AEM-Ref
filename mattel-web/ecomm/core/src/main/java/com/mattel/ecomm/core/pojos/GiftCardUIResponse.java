package com.mattel.ecomm.core.pojos;

import java.util.List;

import com.mattel.ecomm.core.pojos.shopify.ProductUIResponse;

public class GiftCardUIResponse extends ProductUIResponse {
  private List<GiftCardMessage> messages;

  public List<GiftCardMessage> getMessages() {
    return messages;
  }

  public void setMessages(List<GiftCardMessage> messages) {
    this.messages = messages;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("GiftCardNewResponse [messages=");
    builder.append(messages);
    builder.append("]");
    return builder.toString();
  }
}
