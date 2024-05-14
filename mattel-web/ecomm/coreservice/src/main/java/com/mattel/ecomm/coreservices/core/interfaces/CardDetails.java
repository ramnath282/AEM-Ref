package com.mattel.ecomm.coreservices.core.interfaces;


import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.CardDetailsResponse;

public interface CardDetails extends BaseService {

    CardDetailsResponse getCardDetails(Map<String, Object> requestMap) throws ServiceException;
}
