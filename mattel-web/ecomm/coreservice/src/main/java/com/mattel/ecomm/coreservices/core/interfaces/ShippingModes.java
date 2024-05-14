package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.ShippingModesResponse;

public interface ShippingModes extends BaseService {
	
	ShippingModesResponse getShippingModes(Map<String, Object> requestMap) throws ServiceException;
}
