package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.ProductInterestResponse;

import java.util.Map;

public interface ProductInterest extends BaseService {

    ProductInterestResponse getProductInterest(Map<String, Object> requestMap) throws ServiceException;
}
