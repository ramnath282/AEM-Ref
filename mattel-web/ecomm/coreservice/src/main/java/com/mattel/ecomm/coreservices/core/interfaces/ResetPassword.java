package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.ResetPasswordResponse;

public interface ResetPassword extends BaseService {
	
	ResetPasswordResponse resetPassword(Map<String, Object> requestMap) throws ServiceException;
}
