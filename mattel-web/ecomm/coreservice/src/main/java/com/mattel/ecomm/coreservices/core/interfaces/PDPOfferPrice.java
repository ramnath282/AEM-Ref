package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.PDPOfferPriceResponse;

public interface PDPOfferPrice extends BaseService{
	
	PDPOfferPriceResponse getOffertPrice(Map<String, Object> requestMap) throws ServiceException;
	
}
