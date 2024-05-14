package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;

public interface UpdateDollRegistration extends BaseService {
    BaseResponse save(Map<String, Object> requestMap) throws ServiceException;
}
