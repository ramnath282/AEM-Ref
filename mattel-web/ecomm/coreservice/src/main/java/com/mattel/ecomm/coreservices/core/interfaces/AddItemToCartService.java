package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.productdetailspojos.ItemCartResponse;

import java.util.Map;

public interface AddItemToCartService extends BaseService {
  ItemCartResponse addToCart(Map<String, Object> requestMap) throws ServiceException;
}
