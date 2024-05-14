package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.RegisteredDollsResponse;

/**
 * Service to fetch registered doll details.
 */
public interface RegisteredDolls extends BaseService {
    RegisteredDollsResponse fetch(final Map<String, Object> requestMap) throws ServiceException;
}
