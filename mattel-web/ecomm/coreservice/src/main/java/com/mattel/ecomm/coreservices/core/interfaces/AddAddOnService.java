package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.productdetailspojos.AddAddOnServiceResponse;

import java.util.Map;

public interface AddAddOnService extends BaseService {
    AddAddOnServiceResponse addAddOnService(Map<String, Object> requestMap) throws ServiceException;
}
