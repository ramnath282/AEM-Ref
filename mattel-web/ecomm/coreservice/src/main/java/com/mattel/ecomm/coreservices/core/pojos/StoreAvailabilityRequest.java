package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author CTS
 *
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreAvailabilityRequest extends BaseRequest {
	@JsonProperty
	private String catalogId;
	@JsonProperty
	private String storeSelected;
	@JsonProperty
	private String partNumber;
	@JsonProperty
	private List<String> productsList;
}
