package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.SessionStatusResponse;

public interface SessionStatus extends BaseService {
    SessionStatusResponse get(Map<String, Object> requestMap) throws ServiceException;
}
