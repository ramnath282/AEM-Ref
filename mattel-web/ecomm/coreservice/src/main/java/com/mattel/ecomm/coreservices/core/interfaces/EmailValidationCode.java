package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.EmailValidationCodeResponse;

public interface EmailValidationCode extends BaseService {
	
	EmailValidationCodeResponse emailValidationCode(Map<String, Object> requestMap) throws ServiceException;
}
