package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.LoginResponse;

import java.util.Map;

public interface Login extends BaseService {

	LoginResponse login(Map<String, Object> requestMap) throws ServiceException;
}
