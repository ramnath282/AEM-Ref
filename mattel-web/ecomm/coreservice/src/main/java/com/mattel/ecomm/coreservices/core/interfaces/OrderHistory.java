package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.OrderHistoryResponse;

import java.util.Map;

public interface OrderHistory extends BaseService {
    OrderHistoryResponse getOrderHistory(Map<String, Object> requestMap) throws ServiceException;
}
