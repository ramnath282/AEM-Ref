package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.StoreAvailabilityResponse;

/**
 * @author CTS
 *
 */
public interface StoreAvailability extends BaseService {
	StoreAvailabilityResponse getStoreAvailability(Map<String, Object> requestMap) throws ServiceException;
}
