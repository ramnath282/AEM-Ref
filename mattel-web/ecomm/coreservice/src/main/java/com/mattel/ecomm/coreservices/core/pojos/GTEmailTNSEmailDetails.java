package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GTEmailTNSEmailDetails {

	@JsonProperty
    private String senderName;
	@JsonProperty
    private GTEmailTNSRecipientDetails recipientDetails;
	@JsonProperty
    private String emailSubject;
	@JsonProperty
    private String emailMessage;
	
}
