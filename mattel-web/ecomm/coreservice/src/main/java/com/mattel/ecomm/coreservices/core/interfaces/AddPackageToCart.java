package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.productdetailspojos.AddAddOnServiceResponse;

public interface AddPackageToCart extends BaseService {

    AddAddOnServiceResponse addPackageService(Map<String, Object> requestMap) throws ServiceException;

}
