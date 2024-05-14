package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.RegistrationResponse;

import java.util.Map;

public interface Registration extends BaseService {
    RegistrationResponse makeRegistration(Map<String, Object> requestHeader) throws ServiceException;

}
