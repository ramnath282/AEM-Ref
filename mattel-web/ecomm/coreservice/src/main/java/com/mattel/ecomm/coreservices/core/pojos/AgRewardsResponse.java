package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AgRewardsResponse extends BaseResponse {

	private LoyaltyDetails loyaltyDetails;
	private Map<String, Object> rewardSegmentsMap;

	@Override
	public String toString() {
		return loyaltyDetails.toString();
	}

}
