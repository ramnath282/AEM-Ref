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
public class GTEmailTNSResponseMessage {

	@JsonProperty
    private String status;
	@JsonProperty
    private String message;
	@JsonProperty
    private String messageId;
	@JsonProperty
    private String correlationId;
	@JsonProperty
    private String timeStamp;
	
	
}
