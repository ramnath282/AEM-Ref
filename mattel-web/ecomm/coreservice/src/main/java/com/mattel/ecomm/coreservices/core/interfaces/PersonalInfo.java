package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.PersonalInfoResponse;

public interface PersonalInfo extends BaseService {

    PersonalInfoResponse getPersonalInfo(Map<String, Object> requestMap) throws ServiceException;
}


