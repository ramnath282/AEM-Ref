package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;

public interface UpdateDefaultShippingMethod extends BaseService {
	BaseResponse updateDefaultShipping(Map<String, Object> requestMap) throws ServiceException;
}
