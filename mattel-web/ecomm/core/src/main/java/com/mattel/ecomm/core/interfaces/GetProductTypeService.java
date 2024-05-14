package com.mattel.ecomm.core.interfaces;

import java.util.Map;
import java.util.Set;

public interface GetProductTypeService {
     Map<String, String> getProductType(String skewId, String clientIdentifier);
     Set<String> getProductTypeDatasource(String siteKey);
}
