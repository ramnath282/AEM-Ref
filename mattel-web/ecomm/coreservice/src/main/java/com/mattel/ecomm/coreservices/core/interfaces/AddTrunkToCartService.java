package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.TrunkCartResponse;

import java.util.Map;

public interface AddTrunkToCartService extends BaseService {
    TrunkCartResponse addToCart(Map<String, Object> requestMap) throws ServiceException;
}
