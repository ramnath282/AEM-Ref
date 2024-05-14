package com.mattel.ecomm.coreservices.core.pojos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InventoryAvailabilityRequest extends BaseRequest{
		private String partNumber;
}
