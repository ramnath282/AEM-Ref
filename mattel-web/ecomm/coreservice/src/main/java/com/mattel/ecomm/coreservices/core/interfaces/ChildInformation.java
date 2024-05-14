package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.ChildInformationResponse;
import java.util.Map;

public interface ChildInformation extends BaseService {

    ChildInformationResponse getChildrenInfo(Map<String, Object> requestMap) throws ServiceException;

}
