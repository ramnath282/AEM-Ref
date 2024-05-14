package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;

import java.util.Map;

public interface CompositePersonalInfoService {

	Map<String, Object> getCompositePersonalInfoServiceResponse(Map<String, Object> requestMap,Map<String, Object> responseMap) throws ServiceException;
}
