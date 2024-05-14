package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.MiniCartResponse;

public interface Minicart extends BaseService {
		MiniCartResponse fetch(Map<String, Object> requestMap) throws ServiceException;
}
