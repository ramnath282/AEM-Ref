package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.ContactPreferencesResponse;

/**
 * @author CTS
 *
 */
public interface ContactPreferences extends BaseService {
	ContactPreferencesResponse getContactPreferences(Map<String, Object> requestMap) throws ServiceException;
}
