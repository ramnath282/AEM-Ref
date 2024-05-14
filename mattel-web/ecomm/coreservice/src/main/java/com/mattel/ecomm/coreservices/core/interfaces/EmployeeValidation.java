package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.EmployeeValidationResponse;

public interface EmployeeValidation extends BaseService {
	EmployeeValidationResponse validateEmployee(Map<String, Object> requestMap) throws ServiceException;
}
