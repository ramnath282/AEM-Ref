package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.OrderHistoryDetailResponse;

import java.util.Map;

public interface OrderHistoryDetail extends BaseService {
    OrderHistoryDetailResponse getOrderHistoryDetail(Map<String, Object> requestMap) throws ServiceException;
}
