package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author CTS
 *
 */
@Getter
@Setter
public class InventoryAvailabilityResponse extends BaseResponse {
	@JsonProperty("InventoryAvailability")
	private ProductInventoryData inventoryAvailability;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("InventoryAvailabilityResponse [inventoryAvailability=");
    builder.append(inventoryAvailability);
    builder.append("]");
    return builder.toString();
  }
}
