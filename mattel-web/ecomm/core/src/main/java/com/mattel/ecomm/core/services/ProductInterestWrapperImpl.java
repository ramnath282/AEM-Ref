package com.mattel.ecomm.core.services;

import com.mattel.ecomm.core.interfaces.ProductInterestWrapper;
import com.mattel.ecomm.core.interfaces.StoreInterest;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ProductInterest;
import com.mattel.ecomm.coreservices.core.pojos.ProductInterestResponse;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author CTS.
 * Service Implementation for Store and Product Interest.
 */
@Component(service = ProductInterestWrapper.class)
public class ProductInterestWrapperImpl implements ProductInterestWrapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductInterestWrapperImpl.class);
    @Reference
    private ProductInterest productInterest;
    @Reference
    private StoreInterest storeInterest;

    @Override
    public void getStoreAndProductInterest(Map<String, Object> requestMap, Map<String, Object> responseMap) throws ServiceException {
        ProductInterestResponse productInterestResponse = productInterest.getProductInterest(requestMap);
        responseMap.put(Constant.RESPONSE_COOKIES_KEY, productInterestResponse.getCookieList());
        productInterestResponse.setCookieList(null);
        JSONObject wcsJson = productInterest.getResponseValueAsJson(productInterestResponse);
        responseMap.put(Constant.RESPONSE_BODY,buildUiSpecificJson(wcsJson));
    }

    /**
     * This Method will Build UI Compatible JSON.
     *
     * @param wcsJson
     * @throws ServiceException
     */

    private JSONObject buildUiSpecificJson(JSONObject wcsJson) throws ServiceException {
        JSONObject jsonObject = new JSONObject();
        try {
            if (null != wcsJson && wcsJson.isNull("errors")) {
                String userPreviousInterest = StringUtils.EMPTY;
                if (!wcsJson.isNull("userPreviousProductInterest")) {
                    userPreviousInterest = wcsJson.getString("userPreviousProductInterest");
                }
                String[] splitPreviousInterest = null;
                if (!StringUtils.isBlank(userPreviousInterest)) {
                    LOGGER.debug("User previous interest is {}", userPreviousInterest);
                    splitPreviousInterest = userPreviousInterest.split("\\|");
                }
                JSONArray productInterestList = wcsJson.getJSONArray("productInterestList");
                JSONArray locationInterestList = wcsJson.getJSONArray("locationInterestList");
                jsonObject.put("eventInterestList", matchPreviousInterest(storeInterest.getStoreInterest(), splitPreviousInterest));
                jsonObject.put("productInterestList", matchPreviousInterest(productInterestList, splitPreviousInterest));
                jsonObject.put("locationInterestList", matchPreviousInterest(locationInterestList, splitPreviousInterest));

            } else {
                jsonObject = wcsJson;
            }
        } catch (JSONException je) {
            LOGGER.error("JSON Exception {}", je);
            throw new ServiceException("1004", "JSON Exception Occured");
        }
        LOGGER.debug("JSON Object from product interest Wrapper is {}", jsonObject);
        return jsonObject;


    }

    /**
     * This Method will Accept Values of Json Array Match with previous interest and build a new Json Array.
     *
     * @param interest
     * @param splitPreviousInterest
     * @return
     */
    private JSONArray matchPreviousInterest(JSONArray interest, String[] splitPreviousInterest) throws ServiceException {
        JSONArray finalProductInterestArray = new JSONArray();
        try {
            for (int i = 0; i < interest.length(); i++) {
                JSONObject interestJson = new JSONObject();
                if (null != splitPreviousInterest) {
                for (String string : splitPreviousInterest) {
                    String[] productInterestSplit = interest.getString(i).split(":");
                    if (string.equalsIgnoreCase(productInterestSplit[0])) {
                        interestJson.put(Constant.VALUE, interest.get(i));
                        interestJson.put(Constant.SELECTED, true);
                        finalProductInterestArray.put(i, interestJson);
                        break;

                    } else {
                        interestJson.put(Constant.VALUE, interest.get(i));
                        interestJson.put(Constant.SELECTED, false);
                        finalProductInterestArray.put(i, interestJson);

                    }
                }
                } else {
                    interestJson.put(Constant.VALUE, interest.get(i));
                    interestJson.put(Constant.SELECTED, false);
                    finalProductInterestArray.put(i, interestJson);

                }

            }
        } catch (JSONException je) {
            LOGGER.error("JSON Exception {}", je);
            throw new ServiceException("1004", "JSON Exception");
        }

        return finalProductInterestArray;


    }
}
