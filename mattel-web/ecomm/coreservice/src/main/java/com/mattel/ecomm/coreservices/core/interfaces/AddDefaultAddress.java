package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.AddDefaultAddressResponse;

public interface AddDefaultAddress extends BaseService {

	AddDefaultAddressResponse addDefaultAddress(Map<String, Object> requestMap) throws ServiceException;
}
