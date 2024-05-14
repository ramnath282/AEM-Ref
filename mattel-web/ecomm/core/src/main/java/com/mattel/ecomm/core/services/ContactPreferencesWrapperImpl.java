package com.mattel.ecomm.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.mattel.ecomm.core.interfaces.ContactPreferencesWrapper;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ContactPreferences;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.ContactPrefSEQMap;
import com.mattel.ecomm.coreservices.core.pojos.ContactPrefSEQMapUI;
import com.mattel.ecomm.coreservices.core.pojos.ContactPreferencesResponse;
import com.mattel.ecomm.coreservices.core.pojos.ContactPreferencesUIResponse;

@Component(service = ContactPreferencesWrapper.class)
public class ContactPreferencesWrapperImpl implements ContactPreferencesWrapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContactPreferencesWrapperImpl.class);

	@Reference
	ContactPreferences contactPreferences;

	/* (non-Javadoc)
	 * @see com.mattel.ecomm.core.interfaces.ContactPreferencesWrapper#getFormattedContactPreferences(java.util.Map)
	 * Method internally calls contactpreferencesipml to get actual response from wcs
	 */
	@Override
	public ContactPreferencesUIResponse getFormattedContactPreferences(Map<String, Object> requestMap)
			throws ServiceException {
		ContactPreferencesResponse contactPreferencesResponce = contactPreferences.getContactPreferences(requestMap);
		long startTime = System.currentTimeMillis();
		LOGGER.info("ContactPreferencesImpl formatResponce Start");
		ContactPreferencesUIResponse contactPreferencesUIResponce = new ContactPreferencesUIResponse();
		contactPreferencesUIResponce.setCookieList(contactPreferencesResponce.getCookieList());
		ContactPrefSEQMap contactPrefSEQMap = contactPreferencesResponce.getContactPrefSEQMap();
		ContactPrefSEQMapUI contactPrefSEQMapUI = new ContactPrefSEQMapUI();

		String[] userContactPrefCTLArr = null != contactPreferencesResponce.getUserContactPrefCTL()
				? contactPreferencesResponce.getUserContactPrefCTL().split(",") : null;
		String[] userContactPrefLOYArr = null != contactPreferencesResponce.getUserContactPrefLOY()
				? contactPreferencesResponce.getUserContactPrefLOY().split(",") : null;
		String[] userContactPrefCATArr = null != contactPreferencesResponce.getUserContactPrefCAT()
				? contactPreferencesResponce.getUserContactPrefCAT().split(",") : null;

		String[] userContactPrefGLArr = null != contactPreferencesResponce.getUserContactPrefGL()
				? contactPreferencesResponce.getUserContactPrefGL().split(",") : null;

		List<String> contPrefLoyLst = null;
		List<String> contPrefCatLst = null;
		List<String> contPrefCtlLst = null;
		List<String> contactPrefGLLst = contactPreferencesResponce.getContactPrefGLList();
		contactPreferencesUIResponce.setContactPrefGLList(checkSetAttributes(userContactPrefGLArr, contactPrefGLLst));

		if (null != contactPrefSEQMap) {
			contactPrefSEQMapUI.setContPrefFrq(contactPrefSEQMap.getContPrefFrq());
			contPrefLoyLst = contactPrefSEQMap.getContPrefLoy();
			contPrefCatLst = contactPrefSEQMap.getContPrefCat();
			contPrefCtlLst = contactPrefSEQMap.getContPrefCtl();

			contactPrefSEQMapUI.setContPrefCtl(checkSetAttributes(userContactPrefCTLArr, contPrefCtlLst));
			contactPrefSEQMapUI.setContPrefLoy(checkSetAttributes(userContactPrefLOYArr, contPrefLoyLst));
			contactPrefSEQMapUI.setContPrefCat(checkSetAttributes(userContactPrefCATArr, contPrefCatLst));
		}
		contactPreferencesUIResponce.setContactPrefSEQMap(contactPrefSEQMapUI);
		LOGGER.debug("ContactPrefSEQMap : {}", contactPrefSEQMapUI);

		contactPreferencesUIResponce.setPartyId(contactPreferencesResponce.getPartyId());
		contactPreferencesUIResponce.setUserContactPrefCTL(contactPreferencesResponce.getUserContactPrefCTL());
		contactPreferencesUIResponce.setLoyaltyRequest(contactPreferencesResponce.getLoyaltyRequest());
		contactPreferencesUIResponce.setUserContactPrefSF(contactPreferencesResponce.getUserContactPrefSF());
		contactPreferencesUIResponce
				.setUserExistingContactPref(contactPreferencesResponce.getUserExistingContactPref());
		contactPreferencesUIResponce.setUserContactPrefFRQ(contactPreferencesResponce.getUserContactPrefFRQ());
		contactPreferencesUIResponce.setHash(contactPreferencesResponce.getHash());
		contactPreferencesUIResponce.setUserContactPrefRTL(contactPreferencesResponce.getUserContactPrefRTL());
		contactPreferencesUIResponce.setLoyaltyEmail(contactPreferencesResponce.getLoyaltyEmail());
		contactPreferencesUIResponce.setUserLocationPrefSL(contactPreferencesResponce.getUserLocationPrefSL());
		contactPreferencesUIResponce.setEmail(contactPreferencesResponce.getEmail());
		contactPreferencesUIResponce.setUserContactPrefGL(contactPreferencesResponce.getUserContactPrefGL());
		contactPreferencesUIResponce.setUserContactPrefLOY(contactPreferencesResponce.getUserContactPrefLOY());
		contactPreferencesUIResponce.setUserContactPrefCAT(contactPreferencesResponce.getUserContactPrefCAT());
		contactPreferencesUIResponce.setLoyaltyStatus(contactPreferencesResponce.getLoyaltyStatus());
		LOGGER.debug("formatted contactPreferencesResponce : {}", contactPreferencesUIResponce);
		long endTime = System.currentTimeMillis();
		LOGGER.info(Constant.EXECUTION_TIME_LOG, "formatResponce", endTime - startTime);
		return contactPreferencesUIResponce;
	}

	private List<Map<String, Object>> checkSetAttributes(String[] setValues, List<String> allValues) {
		LOGGER.info("checkSetAttributes start");
		List<Map<String, Object>> finalList = new ArrayList<>();
		allValues.forEach(value -> {
			Map<String, Object> vm = checkSetValue(value, setValues);
			finalList.add(vm);
		});
		LOGGER.info("checkSetAttributes end");
		return finalList;
	}

	private Map<String, Object> checkSetValue(String value, String[] setValues) {
		Map<String, Object> map = new HashMap<>();
		boolean flag = false;
		if (setValues.length > 0) {
			for (int i = 0; i < setValues.length; i++) {
				flag = !Strings.isNullOrEmpty(setValues[i]) && value.contains(setValues[i]);
				if (flag) {
					break;
				}
			}
		}
		map.put("value", value);
		map.put("selected", flag);
		return map;
	}

	@Override
	public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
		return null;
	}

}
