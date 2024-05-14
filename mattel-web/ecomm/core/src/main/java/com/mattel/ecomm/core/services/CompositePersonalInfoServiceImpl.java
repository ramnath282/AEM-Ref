package com.mattel.ecomm.core.services;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ChildInformation;
import com.mattel.ecomm.coreservices.core.interfaces.CompositePersonalInfoService;
import com.mattel.ecomm.coreservices.core.interfaces.PersonalInfo;
import com.mattel.ecomm.coreservices.core.pojos.ChildInformationResponse;
import com.mattel.ecomm.coreservices.core.pojos.PersonalInfoResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.ResponseFormatGetter;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
@author CTS.
 */
@Component(service = CompositePersonalInfoService.class)
public class CompositePersonalInfoServiceImpl implements CompositePersonalInfoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CompositePersonalInfoServiceImpl.class);

	@Reference
	ChildInformation childInformation;
	@Reference
	PersonalInfo personalInfo;

	/**
	 * Method takes header requestMap and returns responseMap containing
	 * response body and cookies.
	 *
	 * @param requestMap
	 * @return responseMap
	 * @throws ServiceException
	 */
	public Map<String, Object> getCompositePersonalInfoServiceResponse(Map<String, Object> requestMap,
			Map<String, Object> responseMap) throws ServiceException {
		long startTime = System.currentTimeMillis();
		JSONObject response = new JSONObject();
		JSONObject personalInfoJson = new JSONObject();
		JSONObject defaultAddressInfoJson = new JSONObject();
		JSONObject childInfoJson = new JSONObject();

		try {
			response.put("personalInfo", personalInfoJson);
			response.put("addressInfo", defaultAddressInfoJson);
			response.put("childInfo", childInfoJson);

			String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();

			personalInfoJson = callPersonalInfoService(requestMap, personalInfoJson, cookieNames);

			childInfoJson = callChildInfoService(requestMap, childInfoJson, responseMap);

			response.put("personalInfo", personalInfoJson);
			response.put("childInfo", childInfoJson);

		} catch (JSONException e) {
			LOGGER.error("JSONException occured in getCombinedPersonalInfoServiceResponse response {}", e);
			throw new ServiceException("1004", "JSONException thrown from message body");
		}
		responseMap.put(Constant.RESPONSE_BODY, response.toString());
		long endTime = System.currentTimeMillis();
		LOGGER.debug(Constant.EXECUTION_TIME_LOG, "getCombinedPersonalInfoServiceResponse", endTime - startTime);
		return responseMap;

	}

	/**
	 * @param requestMap
	 * @param childInfoJson
	 * @param cookieNames
	 * @return
	 */
	private JSONObject callChildInfoService(Map<String, Object> requestMap, JSONObject childInfoJson,
			Map<String, Object> responseMap) {
		long startTime = System.currentTimeMillis();
		LOGGER.info("Start of callChildInfoService method");
		try {
			ChildInformationResponse childInformationResponse = childInformation.getChildrenInfo(requestMap);
			if (null != childInformationResponse) {
				responseMap.put(Constant.RESPONSE_COOKIES_KEY, childInformationResponse.getCookieList());
				childInformationResponse.setCookieList(null);
				childInfoJson = childInformation.getResponseValueAsJson(childInformationResponse);
			}
		} catch (ServiceException se) {
			LOGGER.error("ServiceException occured in Child Information Service {}", se);
			childInfoJson = ResponseFormatGetter.getErrorJson(se);
		}
		long endTime = System.currentTimeMillis();
		LOGGER.debug(Constant.EXECUTION_TIME_LOG, "callChildInfoService", endTime - startTime);
		LOGGER.info("End of callChildInfoService method");
		return childInfoJson;
	}

	/**
	 * @param requestMap
	 * @param personalInfoJson
	 * @param cookieNames
	 * @return
	 */
	private JSONObject callPersonalInfoService(Map<String, Object> requestMap, JSONObject personalInfoJson,
			String[] cookieNames) {
		long startTime = System.currentTimeMillis();
		LOGGER.info("Start of callPersonalInfoService method");
		try {
			PersonalInfoResponse personalInfoResponse = personalInfo.getPersonalInfo(requestMap);
			if (null != personalInfoResponse) {
			  CookieUtils.updateRequestMap(requestMap, personalInfoResponse.getCookieList(), cookieNames);
				personalInfoResponse.setCookieList(null);
				personalInfoJson = personalInfo.getResponseValueAsJson(personalInfoResponse);
			}
		} catch (ServiceException se) {
			LOGGER.error("ServiceException occured in Personal Information Service {}", se);
			personalInfoJson = ResponseFormatGetter.getErrorJson(se);
		}
		long endTime = System.currentTimeMillis();
		LOGGER.debug(Constant.EXECUTION_TIME_LOG, "callPersonalInfoService", endTime - startTime);
		LOGGER.info("End of callPersonalInfoService method");
		return personalInfoJson;
	}
}
