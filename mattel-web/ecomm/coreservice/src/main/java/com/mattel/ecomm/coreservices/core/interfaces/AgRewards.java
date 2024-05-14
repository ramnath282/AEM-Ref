package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.AgRewardsResponse;

public interface AgRewards extends BaseService {
	
	AgRewardsResponse getAgReward(Map<String, Object> requestMap) throws ServiceException;

}
