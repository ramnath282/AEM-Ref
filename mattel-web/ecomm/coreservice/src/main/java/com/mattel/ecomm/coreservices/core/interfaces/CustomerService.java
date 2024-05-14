package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.CustomerResponse;

/**
 * @author CTS
 * 
 * PDP Item/Product/Bundle service interface
 *
 */
public interface CustomerService extends BaseService {
	CustomerResponse fetch(final Map<String, Object> requestMap) throws ServiceException;
}
