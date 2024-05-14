package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.InventoryCheckResponse;

import java.util.Map;

public interface ProductInventoryCheckService extends BaseService {
  InventoryCheckResponse fetch(Map<String, Object> requestMap) throws ServiceException;
}
