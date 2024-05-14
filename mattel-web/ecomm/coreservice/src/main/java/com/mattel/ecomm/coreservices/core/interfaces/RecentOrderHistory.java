package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.RecentOrderHistoryResponse;

import java.util.Map;

public interface RecentOrderHistory extends BaseService {
    RecentOrderHistoryResponse getRecentOrderHistory(Map<String, Object> requestMap) throws ServiceException;
}
