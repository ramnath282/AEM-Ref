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
public class GTEmailTNSPayloadDetails {

	@JsonProperty
    private String source;
	@JsonProperty
    private String target;
	@JsonProperty
    private String correlationID;
	@JsonProperty
    private String timeStamp;
	@JsonProperty
    private String environment;
	@JsonProperty
    private String hostName;
	@JsonProperty
    private String user;
}
