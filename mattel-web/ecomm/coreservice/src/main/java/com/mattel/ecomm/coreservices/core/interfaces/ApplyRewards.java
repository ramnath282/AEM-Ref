package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.ApplyRewardsResponse;

public interface ApplyRewards extends BaseService {

	ApplyRewardsResponse applyReward(Map<String, Object> requestMap) throws ServiceException;
}
