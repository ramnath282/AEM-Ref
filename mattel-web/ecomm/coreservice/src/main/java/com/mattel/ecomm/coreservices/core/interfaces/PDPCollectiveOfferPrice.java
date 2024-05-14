package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.PDPCollectiveOfferPriceResponse;

/**
 * @author CTS
 *
 */
public interface PDPCollectiveOfferPrice extends BaseService{
  /**
   * @param requestMap
   * @return
   * @throws ServiceException
   */
  PDPCollectiveOfferPriceResponse getOfferPrice(Map<String, Object> requestMap)
      throws ServiceException;
}
