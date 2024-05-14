package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.ConsumerLoyaltyRewardsResponse;

/**
 * @author CTS
 * 
 * PDP Item/Product/Bundle service interface
 *
 */
public interface ConsumerLoyaltyRewardsService extends BaseService {
	ConsumerLoyaltyRewardsResponse fetch(final Map<String, Object> requestMap) throws ServiceException;
}
