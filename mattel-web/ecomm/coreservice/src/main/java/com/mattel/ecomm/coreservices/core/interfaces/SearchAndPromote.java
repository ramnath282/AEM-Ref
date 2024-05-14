package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.SearchAndPromoteResponse;

import java.util.Map;

public interface SearchAndPromote extends BaseService {
    SearchAndPromoteResponse getSearchResults(Map<String, Object> requestMap) throws ServiceException;
}
