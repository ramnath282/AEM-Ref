package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.FedExResponse;

public interface FedExService extends BaseService {
	
	FedExResponse fetch(Map<String, Object> requestMap) throws ServiceException;
	
}
