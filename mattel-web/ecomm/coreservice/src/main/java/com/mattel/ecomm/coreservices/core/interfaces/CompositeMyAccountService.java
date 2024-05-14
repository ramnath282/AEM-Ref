package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;

import java.util.Map;

public interface CompositeMyAccountService {

	Map<String, Object> getCompositeMyAccountServiceResponse(Map<String, Object> requestMap,Map<String, Object> responseMap) throws ServiceException;
}
