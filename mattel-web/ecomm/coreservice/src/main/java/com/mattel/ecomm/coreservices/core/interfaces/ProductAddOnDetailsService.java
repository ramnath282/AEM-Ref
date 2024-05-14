package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.ProductAddOnResponse;

import java.util.Map;

public interface ProductAddOnDetailsService extends BaseService {
  ProductAddOnResponse fetch(Map<String, Object> requestMap) throws ServiceException;
}
