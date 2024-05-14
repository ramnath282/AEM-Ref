package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;

public interface ProductAvailability extends BaseService {
  ProductServiceResponse fetch(final Map<String, Object> requestMap) throws ServiceException;
}
