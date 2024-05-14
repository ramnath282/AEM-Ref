package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.DHAddCartResponse;

public interface DHAddToCartService extends BaseService {
    DHAddCartResponse addToCart(Map<String, Object> requestMap) throws ServiceException;
}
