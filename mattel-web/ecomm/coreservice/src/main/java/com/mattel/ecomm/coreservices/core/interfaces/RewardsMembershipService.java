package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.RewardsMembershipResponse;

public interface RewardsMembershipService extends BaseService {
    RewardsMembershipResponse find(Map<String, Object> requestMap) throws ServiceException;
}
