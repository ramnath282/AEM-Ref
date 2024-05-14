package com.mattel.ecomm.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.BaseService;
import com.mattel.ecomm.coreservices.core.pojos.ContactPreferencesUIResponse;

public interface ContactPreferencesWrapper extends BaseService {
	ContactPreferencesUIResponse getFormattedContactPreferences(Map<String, Object> requestMap) throws ServiceException;
}
