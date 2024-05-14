package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.UpdatePasswordResponse;

public interface UpdatePassword extends BaseService {
	UpdatePasswordResponse updatePassword(Map<String, Object> requestMap) throws ServiceException;

}
