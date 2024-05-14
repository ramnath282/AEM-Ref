package com.mattel.ecomm.core.pojos;

/**
 * @author CTS
 *
 */
public class GiftCardMessage {
  private String message1;
  private String message2;
  private String message3;
  private String sequence;
  private String messageTitle;

  public String getMessage1() {
    return message1;
  }

  public void setMessage1(String message1) {
    this.message1 = message1;
  }

  public String getMessage2() {
    return message2;
  }

  public void setMessage2(String message2) {
    this.message2 = message2;
  }

  public String getMessage3() {
    return message3;
  }

  public void setMessage3(String message3) {
    this.message3 = message3;
  }

  public String getSequence() {
    return sequence;
  }

  public void setSequence(String sequence) {
    this.sequence = sequence;
  }

  public String getMessageTitle() {
    return messageTitle;
  }

  public void setMessageTitle(String messageTitle) {
    this.messageTitle = messageTitle;
  }

  @Override
  public String toString() {
    return "GiftCardMessage [message1=" + message1 + ", message2=" + message2 + ", message3="
        + message3 + ", sequence=" + sequence + ", messageTitle=" + messageTitle + "]";
  }
}
