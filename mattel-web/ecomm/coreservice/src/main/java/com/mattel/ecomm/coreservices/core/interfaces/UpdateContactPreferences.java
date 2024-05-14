package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.UpdateContactPreferencesResponse;

public interface UpdateContactPreferences extends BaseService {
	UpdateContactPreferencesResponse updateContactPreferences(Map<String, Object> requestMap) throws ServiceException;
}
