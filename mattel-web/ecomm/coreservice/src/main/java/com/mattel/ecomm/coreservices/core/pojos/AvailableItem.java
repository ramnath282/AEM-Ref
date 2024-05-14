package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvailableItem {

	@JsonProperty
	private int quantityAvailable;
	@JsonProperty
	private String itemName;
	@JsonProperty
	private String storeLocation;
	@JsonProperty
	private String itemCode;
	@JsonProperty
	private String availabilityStatus;
	@JsonProperty
	private int catEntryId;
}
