package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.pojos.DefaultCardIdRequest;
import com.mattel.ecomm.coreservices.core.pojos.DefaultCardIdResponse;

public interface DefaultCardId extends BaseService {

    DefaultCardIdResponse getDefaultCardID(DefaultCardIdRequest defaultCardIdRequest, DefaultCardIdResponse defaultCardIdResponse);
}
