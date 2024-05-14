package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;

public interface DeleteDefaultAddress extends BaseService {
    BaseResponse delete(Map<String, Object> requestMap)  throws ServiceException;
}
