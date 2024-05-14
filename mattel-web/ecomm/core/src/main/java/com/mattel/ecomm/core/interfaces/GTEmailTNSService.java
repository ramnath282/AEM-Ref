package com.mattel.ecomm.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.pojos.EmailTNSResponse;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.BaseService;

public interface GTEmailTNSService extends BaseService {
	
    EmailTNSResponse sendEmailDetails(Map<String, Object> requestMap) throws ServiceException;
}
