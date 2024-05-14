package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.pojos.CheckoutRequest;
import com.mattel.ecomm.coreservices.core.pojos.CheckoutResponse;

public interface Checkout extends BaseService {

    CheckoutResponse checkout(CheckoutRequest checkoutRequest, CheckoutResponse checkoutResponse);
}
