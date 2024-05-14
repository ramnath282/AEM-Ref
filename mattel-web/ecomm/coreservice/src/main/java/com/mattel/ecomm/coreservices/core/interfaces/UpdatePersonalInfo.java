package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.PersonalInfoResponse;

public interface UpdatePersonalInfo extends BaseService {
    PersonalInfoResponse updatePersonalInfo(Map<String, Object> requestMap) throws ServiceException;
}
