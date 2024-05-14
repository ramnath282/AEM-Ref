package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GiftCard extends BaseRequest  {
    private String giftPartNumber;
    @JsonProperty("quantity_1")
    private String quantity1;
    private String price;
    private GiftMessageTemplate messageJson;
    private String giftMsgText;
    private String giftMsgTextFrom;
    private String giftMsgTextTo;
    private String giftMsgText1;
    private String giftMsgText2;
    private String giftMsgText3;
    private String validateType;
    private String requesttype;
}
