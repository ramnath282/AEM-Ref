package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.ProductKeywordSuggestionResponse;

import java.util.Map;

public interface ProductKeywordSuggestionService extends BaseService {
  ProductKeywordSuggestionResponse search(Map<String, Object> requestMap) throws ServiceException;
}
