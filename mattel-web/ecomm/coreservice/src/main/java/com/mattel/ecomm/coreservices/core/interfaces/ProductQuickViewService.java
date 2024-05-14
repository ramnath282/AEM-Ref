package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;

public interface ProductQuickViewService {
    Map<String, Object> getQuickViewProductData(Map<String, Object> requestMap, Map<String, Object> responseMap)
            throws ServiceException;
}
