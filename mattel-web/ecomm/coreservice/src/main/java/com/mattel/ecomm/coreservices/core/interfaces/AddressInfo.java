package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.AddressInfoResponse;

public interface AddressInfo extends BaseService {
	
	AddressInfoResponse getAddressInfo(Map<String, Object> requestMap) throws ServiceException;

}
