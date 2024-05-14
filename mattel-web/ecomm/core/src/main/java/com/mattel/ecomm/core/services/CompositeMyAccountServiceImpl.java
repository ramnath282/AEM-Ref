package com.mattel.ecomm.core.services;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.AddressInfo;
import com.mattel.ecomm.coreservices.core.interfaces.AgRewards;
import com.mattel.ecomm.coreservices.core.interfaces.CompositeMyAccountService;
import com.mattel.ecomm.coreservices.core.interfaces.PaymentInfo;
import com.mattel.ecomm.coreservices.core.pojos.AddressInfoResponse;
import com.mattel.ecomm.coreservices.core.pojos.AgRewardsResponse;
import com.mattel.ecomm.coreservices.core.pojos.PaymentInfoResponse;
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
@Component(service = CompositeMyAccountService.class)
public class CompositeMyAccountServiceImpl implements CompositeMyAccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CompositeMyAccountServiceImpl.class);
	
	@Reference
	AddressInfo addressInfo;
	@Reference
	AgRewards agRewards;
	@Reference
	PaymentInfo paymentInfo;

	/**
	 * Method takes header requestMap and returns responseMap containing response body and cookies.
	 * 
	 * @param requestMap
	 * @return responseMap
	 * @throws ServiceException
	 */
	public Map<String, Object> getCompositeMyAccountServiceResponse(Map<String, Object> requestMap, Map<String, Object> responseMap)
			throws ServiceException {
		long startTime = System.currentTimeMillis();
		JSONObject response = new JSONObject();
		JSONObject addressInfoJson = new JSONObject();
		JSONObject agRewardsResJson = new JSONObject();
		JSONObject paymentInfoResJson = new JSONObject();
		try {
			response.put("userInfo", addressInfoJson);
			response.put("agRewards", agRewardsResJson);
			response.put("defaultPayment", paymentInfoResJson);
			String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
			
			addressInfoJson = callAddressService(requestMap, addressInfoJson, cookieNames);
			
			agRewardsResJson = callAgRewardsService(requestMap, agRewardsResJson, cookieNames);
			
			paymentInfoResJson = callPaymentInfoService(requestMap, responseMap, paymentInfoResJson);
			
			response.put("userInfo", addressInfoJson);
			response.put("agRewards", agRewardsResJson);
			response.put("defaultPayment", paymentInfoResJson);
		} catch (JSONException e) {
			LOGGER.error("JSONException occured in getCombinedAccountServiceResponse response {}", e);
			throw new ServiceException("1004", "JSONException thrown from message body");
		}
		responseMap.put(Constant.RESPONSE_BODY, response.toString());
		long endTime = System.currentTimeMillis();
		LOGGER.debug(Constant.EXECUTION_TIME_LOG, "getCombinedAccountServiceResponse", endTime - startTime);
		return responseMap;

	}

	/**
	 * @param requestMap
	 * @param responseMap
	 * @param paymentInfoResJson
	 * @return
	 */
	private JSONObject callPaymentInfoService(Map<String, Object> requestMap, Map<String, Object> responseMap,
			JSONObject paymentInfoResJson) {
		try{
		PaymentInfoResponse paymentInfoResponse = paymentInfo.getPaymentInformation(requestMap);
		if(null!=paymentInfoResponse){
			responseMap.put(Constant.RESPONSE_COOKIES_KEY, paymentInfoResponse.getCookieList());
			paymentInfoResponse.setCookieList(null);
			paymentInfoResJson = paymentInfo.getResponseValueAsJson(paymentInfoResponse);
		}
		} catch (ServiceException se) {
			LOGGER.error("ServiceException occured in Payment Information Service {}",se);
			paymentInfoResJson = ResponseFormatGetter.getErrorJson(se);
		}
		return paymentInfoResJson;
	}

	/**
	 * @param requestMap
	 * @param agRewardsResJson
	 * @param cookieNames
	 * @return
	 */
	private JSONObject callAgRewardsService(Map<String, Object> requestMap, JSONObject agRewardsResJson,
			String[] cookieNames) {
		try {
		AgRewardsResponse agRewardsResponce = agRewards.getAgReward(requestMap);
		if(null!=agRewardsResponce){
			CookieUtils.updateRequestMap(requestMap, agRewardsResponce.getCookieList(),cookieNames);
			agRewardsResponce.setCookieList(null);
			agRewardsResJson = agRewards.getResponseValueAsJson(agRewardsResponce);
		}
		} catch (ServiceException se) {
			LOGGER.error("ServiceException occured in AG Rewards Service {}",se);
			agRewardsResJson = ResponseFormatGetter.getErrorJson(se);
		}
		return agRewardsResJson;
	}

	/**
	 * @param requestMap
	 * @param addressInfoJson
	 * @param cookieNames
	 * @return
	 */
	private JSONObject callAddressService(Map<String, Object> requestMap, JSONObject addressInfoJson,
			String[] cookieNames) {
		try {
			AddressInfoResponse addressInfoResponse = addressInfo.getAddressInfo(requestMap);
			if(null!=addressInfoResponse){
				CookieUtils.updateRequestMap(requestMap, addressInfoResponse.getCookieList(),cookieNames);
				addressInfoResponse.setCookieList(null);
				addressInfoJson = addressInfo.getResponseValueAsJson(addressInfoResponse);
			}
		} catch (ServiceException se) {
			LOGGER.error("ServiceException occured in AddressInfo Service {}",se);
			addressInfoJson = ResponseFormatGetter.getErrorJson(se);
		}
		return addressInfoJson;
	}
}
