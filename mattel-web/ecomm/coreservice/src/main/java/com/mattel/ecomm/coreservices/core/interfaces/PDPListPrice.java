package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.PDPListPriceResponse;

public interface PDPListPrice extends BaseService{

	PDPListPriceResponse getListPrice(Map<String, Object> requestMap) throws ServiceException;
	
}
