package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;

import java.util.Map;

public interface DeleteChildInformation extends BaseService {
    BaseResponse deleteChildInfo(Map<String, Object> requestMap) throws ServiceException;

}
