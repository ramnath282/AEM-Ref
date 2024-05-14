package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.UpdateDefaultAddressResponse;

public interface UpdateDefaultAddress extends BaseService {
	UpdateDefaultAddressResponse updateDefaultAddress(Map<String, Object> requestMap) throws ServiceException;
}
