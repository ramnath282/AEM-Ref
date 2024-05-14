package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;

import java.util.Map;

public interface AddChildInformation extends BaseService {
    BaseResponse addChildInfo(Map<String, Object> requestMap) throws ServiceException;

}
