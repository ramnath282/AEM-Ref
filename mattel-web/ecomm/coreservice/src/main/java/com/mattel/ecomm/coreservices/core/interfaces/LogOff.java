package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.LogOffResponse;

import java.util.Map;

public interface LogOff extends BaseService {

    LogOffResponse logOff(Map<String, Object> requestMap) throws ServiceException;
}
