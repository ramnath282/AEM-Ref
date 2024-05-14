package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.GiftCardMessageValidity;

public interface CheckGiftCardMessageValidity extends BaseService {
    GiftCardMessageValidity verify(Map<String, Object> requestMap) throws ServiceException;
}
