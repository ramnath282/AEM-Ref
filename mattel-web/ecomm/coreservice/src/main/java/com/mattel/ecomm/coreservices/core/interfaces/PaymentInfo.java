package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.PaymentInfoResponse;

/**
 * Service to fetch card details.
 */
public interface PaymentInfo extends BaseService {
	PaymentInfoResponse getPaymentInformation(final Map<String, Object> requestMap) throws ServiceException;
}
