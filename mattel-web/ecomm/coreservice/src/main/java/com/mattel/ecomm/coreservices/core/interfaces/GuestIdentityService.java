/**
 * 
 */
package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.LoginResponse;

/**
 * @author 743025
 *
 */
public interface GuestIdentityService extends BaseService {

	LoginResponse guestIdentity(Map<String, Object> requestMap) throws ServiceException;
}
