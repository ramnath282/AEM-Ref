package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import org.json.JSONObject;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;

public interface ProductAddonsService {
  JSONObject fetch(final Map<String, Object> requestMap) throws ServiceException;
}
