package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;

import java.util.Map;

public interface UpdateChildInfo extends BaseService {
    BaseResponse updateChildInfo(Map<String, Object> requestMap) throws ServiceException;

}

