package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.ValidateAddressResponse;

public interface AddressValidation extends BaseService {
    ValidateAddressResponse verify(Map<String, Object> requestMap) throws ServiceException;
}
