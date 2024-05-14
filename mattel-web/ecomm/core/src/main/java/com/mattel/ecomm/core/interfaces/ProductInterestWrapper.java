package com.mattel.ecomm.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;

import java.util.Map;

public interface ProductInterestWrapper {
     void getStoreAndProductInterest(Map<String, Object> requestMap, Map<String, Object> responseMap) throws ServiceException;
}
