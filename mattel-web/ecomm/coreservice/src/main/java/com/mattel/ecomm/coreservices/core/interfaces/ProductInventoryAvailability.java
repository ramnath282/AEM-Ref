package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.InventoryAvailabilityResponse;

import java.util.Map;

/**
 * @author CTS
 * @deprecated
 * @see {@link ProductInventoryCheckService}
 */
public interface ProductInventoryAvailability extends BaseService {
	InventoryAvailabilityResponse getInventoryAvailabilityStatus(Map<String, Object> requestMap)
			throws ServiceException;
}
