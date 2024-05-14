package com.mattel.ecomm.coreservices.core.pojos;

import org.apache.sling.models.annotations.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentInfoRequest extends BaseRequest {
    @JsonProperty("billing_address_id") @Optional
    private String billingAddressId;
    @JsonProperty @Optional
    private String cardNum;
    @JsonProperty @Optional
    private String creditCardId;
    @JsonProperty @Optional
    private String month;
    @JsonProperty @Optional
    private String nameOnCard;
    @JsonProperty 
    private String operation;
    @JsonProperty @Optional
    private String saveDefCard;
    @JsonProperty @Optional
    private String viewName;
    @JsonProperty @Optional
    private String year;
    @JsonProperty @Optional
    private String defaultChange;
    
	@Override
	public String toString() {
		return "PaymentInfoRequest [billingAddressId=" + billingAddressId + ", cardNum=" + cardNum + ", creditCardId="
				+ creditCardId + ", month=" + month + ", nameOnCard=" + nameOnCard + ", operation=" + operation
				+ ", saveDefCard=" + saveDefCard + ", viewName=" + viewName + ", year=" + year + ", defaultChange="
				+ defaultChange + "]";
	}

}
