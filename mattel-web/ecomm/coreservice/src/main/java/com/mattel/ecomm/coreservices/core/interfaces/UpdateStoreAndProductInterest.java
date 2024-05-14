package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.UpdateStoreAndProductInterestResponse;

import java.util.Map;

public interface UpdateStoreAndProductInterest extends BaseService {
    UpdateStoreAndProductInterestResponse updateInterests(Map<String, Object> requestMap) throws ServiceException;
}
