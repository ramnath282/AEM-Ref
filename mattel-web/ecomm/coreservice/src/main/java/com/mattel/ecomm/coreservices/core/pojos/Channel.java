package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Channel {
	@JsonProperty
	private Map<String, String> channelIdentifer;
	
	@JsonProperty
	private String userData;
	
	@JsonProperty
	private Map<String, String> description;
}
