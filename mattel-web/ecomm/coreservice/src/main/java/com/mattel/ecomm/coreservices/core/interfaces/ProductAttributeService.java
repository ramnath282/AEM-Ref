package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.ProductAttributesResponse;

import java.util.Map;

public interface ProductAttributeService extends BaseService {
  ProductAttributesResponse fetch(Map<String, Object> requestMap) throws ServiceException;
}
