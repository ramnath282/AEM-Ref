package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author CTS
 *
 */
@Getter
@Setter
public class StoreAvailabilityResponse extends BaseResponse {

	@JsonProperty("itemAvailabilityDetailsList")
	private List<AvailableItem> itemAvailabilityDetailsList;
}
