package com.mattel.ecomm.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import org.json.JSONArray;

public interface StoreInterest {
    JSONArray getStoreInterest() throws ServiceException;
}
