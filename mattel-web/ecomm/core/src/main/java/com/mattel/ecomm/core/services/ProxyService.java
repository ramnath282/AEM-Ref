package com.mattel.ecomm.core.services;

import java.util.HashMap;
import java.util.Map;

import com.mattel.ecomm.coreservices.core.interfaces.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.Optional;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.interfaces.ContactPreferencesWrapper;
import com.mattel.ecomm.core.interfaces.GTEmailTNSService;
import com.mattel.ecomm.core.interfaces.ProductInterestWrapper;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.EmailValidationCodeResponse;
import com.mattel.ecomm.coreservices.core.pojos.RegistrationResponse;
import com.mattel.ecomm.coreservices.core.pojos.ResetPasswordResponse;
import com.mattel.ecomm.coreservices.core.pojos.RewardsMembershipResponse;
import com.mattel.ecomm.coreservices.core.utilities.ResponseFormatGetter;

@Component(service = ProxyService.class)
public class ProxyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyService.class);
    private static final String NO_RESOURCE_FOUND_ERROR = "No resource found: {}, HTTP method type: {}";

    @Reference
    @Optional
    AddChildInformation addChildInformation;

    @Reference
    @Optional
    AddressInfo addressInfo;

    @Reference
    @Optional
    AgRewards agRewards;

    @Reference
    @Optional
    ChildInformation childInformation;

    @Reference
    @Optional
    CompositeMyAccountService compositeMyAccountService;

    @Reference
    @Optional
    CompositePersonalInfoService compositePersonalInfoService;

    @Reference
    @Optional
    CompositeProductQuickViewService compositeProductQuickViewService;

    @Reference
    @Optional
    ContactPreferencesWrapper contactPreferences;

    @Reference
    @Optional
    DeleteChildInformation deleteChildInformation;

    @Reference
    Login login;

    @Reference
    @Optional
    OrderHistoryDetail orderHistoryDetail;

    @Reference
    @Optional
    PaymentInfo paymentInfo;

    @Reference
    @Optional
    PersonalInfo personalInfo;

    @Reference
    @Optional
    ProductInterestWrapper productInterest;

    @Reference
    @Optional
    RecentOrderHistory recentOrderHistory;

    @Reference
    @Optional
    RegisteredDolls registeredDolls;

    @Reference
    @Optional
    ShippingModes shippingModes;

    @Reference
    @Optional
    AddDefaultAddress addDefaultAddress;

    @Reference
    @Optional
    UpdateChildInfo updateChildInfo;

    @Reference
    @Optional
    UpdateContactPreferences updateContactPreferences;

    @Reference
    @Optional
    UpdateDefaultAddress updateDefaultAddress;

    @Reference
    @Optional
    UpdateDefaultShippingMethod updateDefaultShippingMethod;

    @Reference
    @Optional
    UpdateDollRegistration updateDollRegistration;

    @Reference
    @Optional
    UpdatePaymentInfo updatePaymentInfo;

    @Reference
    @Optional
    UpdatePassword updatePassword;

    @Reference
    @Optional
    UpdatePersonalInfo updatePersonalInfo;

    @Reference
    @Optional
    UpdateStoreAndProductInterest updateStoreAndProductInterest;

    @Reference
    @Optional
    EmailValidationCode emailValidationCode;

    @Reference
    @Optional
    ResetPassword resetPassword;

    @Reference
    @Optional
    DeleteDefaultAddress deleteDefaultAddress;

    @Reference
    @Optional
    Registration registration;

    @Reference
    @Optional
    RewardsMembershipService rewardsMembershipService;

    @Reference
    @Optional
    AddressValidation addressValidation;

    @Reference
    @Optional
    SessionStatus sessionStatus;
    
    @Reference
    @Optional
    Minicart minicart;
    
    @Reference
    @Optional
    PDPProduct pdpProduct;
    
    @Reference
    @Optional
    PDPListPrice pdpListPrice;
    
    @Reference
    @Optional
    PDPOfferPrice pdpOfferPrice;
    
    @Reference
    @Optional
    StoreAvailability storeAvailability;
    
    @Reference
    @Optional
    LogOff logOff;

    @Reference
    @Optional
    AddGiftCardService addGiftCardService;

    @Reference
    @Optional
    CheckGiftCardMessageValidity checkGiftCardMessageValidity;
    
    @Reference
    @Optional
    GuestIdentityService guestIdentityService;

    @Reference
    @Optional
    AddPackageToCart addPackageToCart;

    @Reference
    @Optional
    ProductAttributeService productAttributeService;

    @Reference
    @Optional
    AddItemToCartService addItemToCartService;
	
	@Reference
    @Optional
    AddTrunkToCartService addTrunkToCartService;
	
	@Reference
    @Optional
	DHAddToCartService dhAddToCartService;
	
	@Reference
    @Optional
	GTEmailTNSService gtEmailTNSService;
    
    @Reference
    @Optional
    PDPCollectiveOfferPrice pdpCollectiveOfferPrice;

    @Reference
    @Optional
    ProductInventoryCheckService productInventoryCheckService;

    @Reference
    @Optional
    ProductAddOnDetailsService productAddOnDetailsService;

    @Reference
    @Optional
    AddAddOnService addAddOnService;

    @Reference
    @Optional
    ProductKeywordSuggestionService productKeywordSuggestionService;
    
    @Reference
    @Optional
    EmployeeValidation employeeValidation;
    
    @Reference
    @Optional
    ConsumerLoyaltyRewardsService consumerLoyaltyRewardsService;
    
    @Reference
    @Optional
    ApplyRewards applyRewards;
    
    @Reference
    @Optional
    RemoveReward removeReward;

    @Reference
    @Optional
    ProductAvailability productAvailability;
    
    @Reference
    @Optional
    ProductQuickViewService productQuickViewService;

    @Reference
    @Optional
    ProductAddonsService productAddonsService;
    
    @Reference
    @Optional
    CustomerService customerService;

    public Map<String, Object> makeServiceCalls(Map<String, Object> requestMap, String[] selector) {

        long startTime = System.currentTimeMillis();
        Map<String, Object> responseMap = new HashMap<>();
        JSONObject response = null;
        String[] allSelectors = getTouchPointSelectors(selector);
        String touchPoint = allSelectors[0];
        String serviceName = allSelectors[1];

        if (StringUtils.equals(touchPoint, Constant.WEB_SELECTOR)) {
            ProxyService.LOGGER.debug("Web Module Services Called");
            handleWebModuleServices(requestMap, responseMap, serviceName);
        } else {
            ProxyService.LOGGER.debug("Mobile Module Services Called");
            handleMobileModuleServices(requestMap, responseMap, serviceName, response);
        }

        ProxyService.LOGGER.debug("Final responseMap value is {}", responseMap);
        long endTime = System.currentTimeMillis();
        ProxyService.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "makeServiceCalls", endTime - startTime);
        return responseMap;

    }

    public Map<String, Object> sessionService(Map<String, Object> requestMap, String serviceName) {

        long startTime = System.currentTimeMillis();
        Map<String, Object> responseMap = new HashMap<>();

        handleWebModuleServices(requestMap, responseMap, serviceName);
        ProxyService.LOGGER.debug("Final responseMap value is {}", responseMap);

        long endTime = System.currentTimeMillis();
        ProxyService.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "makeServiceCalls", endTime - startTime);
        return responseMap;

    }

    private JSONObject handleWebDeleteServices(Map<String, Object> requestMap, Map<String, Object> responseMap,
        String serviceName) throws ServiceException {
      JSONObject response = null;

        switch (serviceName) { 
          case Constant.DELETE_CHILD_INFORMATION:
            logServiceCall(deleteChildInformation);
            response = buildResponse(deleteChildInformation.deleteChildInfo(requestMap), deleteChildInformation,
                    responseMap);
            break;
        case Constant.DELETE_DEFAULT_ADDRESS_SERVICE:
            logServiceCall(deleteDefaultAddress);
            response = buildResponse(deleteDefaultAddress.delete(requestMap), deleteDefaultAddress,
                    responseMap);
            break;
        default:
            ProxyService.LOGGER.error(NO_RESOURCE_FOUND_ERROR, serviceName, HttpConstants.METHOD_DELETE);
            break;
        }

        return response;
    }

    private JSONObject handleWebPutServices(Map<String, Object> requestMap, Map<String, Object> responseMap,
        String serviceName) throws ServiceException {
      JSONObject response = null;

      switch (serviceName) { 
        case Constant.UPDATE_CHILD_INFORMATION:
          logServiceCall(updateChildInfo);
          response = buildResponse(updateChildInfo.updateChildInfo(requestMap), updateChildInfo, responseMap);
          break;
      case Constant.UPDATE_CONTACTPREFERENCES_SERVICE:
          ProxyService.LOGGER.debug("Update contact preference Service Called");
          final BaseResponse baseResponse = updateContactPreferences.updateContactPreferences(requestMap);
          baseResponse.setCookieList(null);
          response = updateContactPreferences.getResponseValueAsJson(baseResponse);
          break;
      case Constant.UPDATE_DEFAULT_ADDRESS_SERVICE:
          logServiceCall(updateDefaultAddress);
          response = buildResponse(updateDefaultAddress.updateDefaultAddress(requestMap), updateDefaultAddress,
                  responseMap);
          break;
      case Constant.UPDATE_DEFAULT_SHIPPING_SERVICE:
          logServiceCall(updateDefaultShippingMethod);
          response = buildResponse(updateDefaultShippingMethod.updateDefaultShipping(requestMap),
                  updateDefaultShippingMethod, responseMap);
          break;
      case Constant.UPDATE_PD:
          logServiceCall(updatePassword);
          response = buildResponse(updatePassword.updatePassword(requestMap), updatePassword, responseMap);
          break;
      case Constant.UPDATE_PERSONAL_INFO_SERVICE:
          logServiceCall(updatePersonalInfo);
          response = buildResponse(updatePersonalInfo.updatePersonalInfo(requestMap), updatePersonalInfo,
                  responseMap);
          break;
      case Constant.UPDATE_PRODUCT_INTEREST_SERVICE:
          logServiceCall(updateStoreAndProductInterest);
          response = buildResponse(updateStoreAndProductInterest.updateInterests(requestMap),
                  updateStoreAndProductInterest, responseMap);
          break;
      default:
        ProxyService.LOGGER.error(NO_RESOURCE_FOUND_ERROR, serviceName, HttpConstants.METHOD_PUT);
        break;
      }

      return response;
    }

    private JSONObject handleWebPostServices(Map<String, Object> requestMap, Map<String, Object> responseMap,
        String serviceName) throws ServiceException {
      JSONObject response = null;

      switch (serviceName) { 
        /** POST Login service */
        case Constant.LOGIN_SERVICE:
            logServiceCall(login);
            response = buildResponse(login.login(requestMap), login, responseMap);
            break;
        /** POST Services */
        case Constant.ADD_DEFAULT_ADDRESS_SERVICE:
          logServiceCall(addDefaultAddress);
          response = buildResponse(addDefaultAddress.addDefaultAddress(requestMap), addDefaultAddress,
                  responseMap);
          break;
        case Constant.ADD_CHILD_INFORMATION:
            logServiceCall(addChildInformation);
            response = buildResponse(addChildInformation.addChildInfo(requestMap), addChildInformation,
                    responseMap);
            break;
        case Constant.COLLECTIVE_OFFER_SERVICE:
            logServiceCall(pdpCollectiveOfferPrice, requestMap);
            response = buildResponse(pdpCollectiveOfferPrice.getOfferPrice(requestMap), pdpCollectiveOfferPrice,
                    responseMap);
            break;    
        /** Add to Cart Services */
        case Constant.ADD_GIFT_CARD_TO_CART_SERVICE:
            logServiceCall(addGiftCardService);
            response = buildResponse(addGiftCardService.addToCart(requestMap), addGiftCardService,
                    responseMap);
            break;
        case Constant.ADD_ITEM_TO_CARD_SERVICE:
            logServiceCall(addItemToCartService);
            response = buildResponse(addItemToCartService.addToCart(requestMap), addItemToCartService,
                    responseMap);
            break;
        case Constant.ADD_PACKAGE_TO_CART_SERVICE:
            logServiceCall(addPackageToCart);
            response = buildResponse(addPackageToCart.addPackageService(requestMap), addPackageToCart,
                    responseMap);
            break;
        case Constant.ADD_PRODUCT_ADD_ON_TO_CART:
            logServiceCall(addAddOnService);
            response = buildResponse(addAddOnService.addAddOnService(requestMap), addAddOnService,
                    responseMap);
            break;
case Constant.GT_ADD_TRUNK_TO_CARD_SERVICE:
            logServiceCall(addTrunkToCartService);
            response = buildResponse(addTrunkToCartService.addToCart(requestMap), addTrunkToCartService,
                    responseMap);
            break;
            case Constant.DH_ADD_TO_CARD_SERVICE:
                    logServiceCall(dhAddToCartService);
                    response = buildResponse(dhAddToCartService.addToCart(requestMap), dhAddToCartService,
                            responseMap);
                    break;
				
case Constant.GT_EMAIL_TNS_SERVICE:
            logServiceCall(gtEmailTNSService);
            response = buildResponse(gtEmailTNSService.sendEmailDetails(requestMap), gtEmailTNSService,
                    responseMap);
            break;
        /** */
        case Constant.ADDRESS_VALIDATION_SERVICE:
            logServiceCall(addressValidation);
            response = buildResponse(addressValidation.verify(requestMap), addressValidation,
                    responseMap);
            break;
        case Constant.CHECK_GIFT_CARD_MESSAGE_VALIDITY_SERVICE:
            logServiceCall(checkGiftCardMessageValidity);
            response = buildResponse(checkGiftCardMessageValidity.verify(requestMap), checkGiftCardMessageValidity,
                    responseMap);
            break;
        case Constant.COLLECTIVE_INVENTORY_SERVICE:
            logServiceCall(productInventoryCheckService, requestMap);
            response = buildResponse(productInventoryCheckService.fetch(requestMap), productInventoryCheckService,
                    responseMap);
            break;
        case Constant.EMAILVALIDATIONCODE_SERVICE:
            ProxyService.LOGGER.debug("Email Validation Code Service Called");
            final EmailValidationCodeResponse emailValidationCodeResponse = emailValidationCode
                    .emailValidationCode(requestMap);
            response = emailValidationCode.getResponseValueAsJson(emailValidationCodeResponse);
            break;
        case Constant.REGISTRATION_SERVICE:
            ProxyService.LOGGER.debug("User Registration Service Called");
            final RegistrationResponse registrationResponse = registration.makeRegistration(requestMap);
            response = buildResponse(registrationResponse, registration,responseMap);
            break;
        case Constant.RESETPD_SERVICE:
            ProxyService.LOGGER.debug("Reset Password Service Called");
            final ResetPasswordResponse resetPasswordResponse = resetPassword.resetPassword(requestMap);
            response = resetPassword.getResponseValueAsJson(resetPasswordResponse);
            break;
        case Constant.REWARDS_MEMBERSHIP_SERVICE:
            ProxyService.LOGGER.debug("Reward Membership Service Called");
            final RewardsMembershipResponse rewardsMembershipResponse = rewardsMembershipService.find(requestMap);
            response = rewardsMembershipService.getResponseValueAsJson(rewardsMembershipResponse);
            break;
        case Constant.UPDATE_DOLL_REG_SERVICE:
            logServiceCall(updateDollRegistration);
            response = buildResponse(updateDollRegistration.save(requestMap), updateDollRegistration,
                    responseMap);
            break;
        case Constant.UPDATE_PAYMENT_INFO_SERVICE:
            logServiceCall(updatePaymentInfo);
            response = buildResponse(updatePaymentInfo.updateCardDetails(requestMap), updatePaymentInfo,
                    responseMap);
            break;
        case Constant.STORE_AVAILABILITY_SERVICE:
            logServiceCall(storeAvailability, requestMap);
            response = buildResponse(storeAvailability.getStoreAvailability(requestMap), storeAvailability,
                    responseMap);
            break; 
        case Constant.GUEST_IDENTITY:
            logServiceCall(guestIdentityService);
            response = buildResponse(guestIdentityService.guestIdentity(requestMap), guestIdentityService,
                    responseMap);
            break;  
        case Constant.APPLY_REWARDS:
            logServiceCall(applyRewards);
            response = buildResponse(applyRewards.applyReward(requestMap), applyRewards,
                    responseMap);
            break;
        case Constant.REMOVE_REWARDS:
            logServiceCall(removeReward);
            response = buildResponse(removeReward.remove(requestMap), removeReward,
                    responseMap);
            break;
        case Constant.CUSTOMER_DETAILS:
            logServiceCall(customerService);
            response = buildResponse(customerService.fetch(requestMap), customerService,
                    responseMap);
            break;
        default:
          ProxyService.LOGGER.error(NO_RESOURCE_FOUND_ERROR, serviceName, HttpConstants.METHOD_POST);
          break;
      }

      return response;
    }

    private JSONObject handleWebGetServices(Map<String, Object> requestMap, Map<String, Object> responseMap,
        String serviceName) throws ServiceException {
      JSONObject response = null;
    
      switch (serviceName) { 
        /** GET Services */
        case Constant.ADDRESS_INFO_SERVICE:
            logServiceCall(addressInfo);
            response = buildResponse(addressInfo.getAddressInfo(requestMap), addressInfo, responseMap);
            break;
        case Constant.AGREWARDS_SERVICE:
            logServiceCall(agRewards);
            response = buildResponse(agRewards.getAgReward(requestMap), agRewards, responseMap);
            break;
        case Constant.CONTACT_PREFERENCES_SERVICE:
            logServiceCall(contactPreferences);
            response = buildResponse(contactPreferences.getFormattedContactPreferences(requestMap), contactPreferences,
                    responseMap);
            break;
        case Constant.GET_CHILD_INFORMATION:
            logServiceCall(childInformation);
            response = buildResponse(childInformation.getChildrenInfo(requestMap), childInformation,
                    responseMap);
            break;
        case Constant.GET_DOLL_REG_SERVICE:
            logServiceCall(registeredDolls);
            response = buildResponse(registeredDolls.fetch(requestMap), registeredDolls, responseMap);
            break;
        case Constant.ORDER_HISTORY_DETAIL:
            logServiceCall(orderHistoryDetail);
            response = buildResponse(orderHistoryDetail.getOrderHistoryDetail(requestMap), orderHistoryDetail,
                    responseMap);
            break;
        case Constant.PAYMENT_INFO_SERVICE:
            logServiceCall(paymentInfo);
            response = buildResponse(paymentInfo.getPaymentInformation(requestMap), paymentInfo, responseMap);
            break;
        case Constant.PERSONAL_INFO_SERVICE:
            logServiceCall(personalInfo);
            response = buildResponse(personalInfo.getPersonalInfo(requestMap), personalInfo, responseMap);
            break;
        case Constant.PRODUCT_KEYWORD_SUGGESTION_SERVICE:
            logServiceCall(productKeywordSuggestionService);
            response = buildResponse(productKeywordSuggestionService.search(requestMap),
                productKeywordSuggestionService, responseMap);
            break;
        case Constant.PRODUCTINTEREST_SERVICE:
            ProxyService.LOGGER.debug("Web Module Product Interest Service Called");
            productInterest.getStoreAndProductInterest(requestMap, responseMap);
            break;
        case Constant.RECENT_ORDER_HISTORY:
            logServiceCall(recentOrderHistory);
            response = buildResponse(recentOrderHistory.getRecentOrderHistory(requestMap), recentOrderHistory,
                    responseMap);
            break;
        case Constant.SESSION_STATUS_SERVICE:
            logServiceCall(sessionStatus);
            response = buildResponse(sessionStatus.get(requestMap), sessionStatus,
                    responseMap);
            break;
        case Constant.SHIPPINGMODES_SERVICE:
            logServiceCall(shippingModes);
            response = buildResponse(shippingModes.getShippingModes(requestMap), shippingModes, responseMap);
            break;
        case Constant.MINICART_SERVICE:
            logServiceCall(minicart);
            response = buildResponse(minicart.fetch(requestMap), minicart, responseMap);
            break;
        case Constant.PDPPRODUCT_SERVICE:
            logServiceCall(pdpProduct, requestMap);
            response = buildResponse(pdpProduct.fetch(requestMap), pdpProduct, responseMap);
            break;
        case Constant.PRODUCT_ADD_ON_DETAILS_SERVICE:
            logServiceCall(productAddOnDetailsService, requestMap);
            response = buildResponse(productAddOnDetailsService.fetch(requestMap), productAddOnDetailsService,
                    responseMap);
            break;
        case Constant.PDP_LISTPRICE_SERVICE:
            logServiceCall(pdpListPrice, requestMap);
            response = buildResponse(pdpListPrice.getListPrice(requestMap), pdpListPrice, responseMap);
            break;  
        case Constant.PDP_OFFERPRICE_SERVICE:
            logServiceCall(pdpOfferPrice, requestMap);
            response = buildResponse(pdpOfferPrice.getOffertPrice(requestMap), pdpOfferPrice, responseMap);
            break;
        case Constant.PRODUCT_ATTRIBUTE_SERVICE:
            logServiceCall(productAttributeService, requestMap);
            response = buildResponse(productAttributeService.fetch(requestMap), productAttributeService, responseMap);
            break;
        case Constant.EMPLOYEE_VALIDATION:
            logServiceCall(employeeValidation, requestMap);
            response = buildResponse(employeeValidation.validateEmployee(requestMap), employeeValidation, responseMap);
            break;    
        /** GET Composite Services */
        case Constant.COMPOSITE_MY_ACCOUNT_SERVICE:
            ProxyService.LOGGER.debug("Web Module Composite My Account Service Called");
            compositeMyAccountService.getCompositeMyAccountServiceResponse(requestMap, responseMap);
            break;
        case Constant.COMPOSITE_PERSONAL_INFO_SERVICE:
            ProxyService.LOGGER.debug("Web Module Composite Personal Info Service Called");
            compositePersonalInfoService.getCompositePersonalInfoServiceResponse(requestMap,
                    responseMap);
            break;
        case Constant.COMPOSITE_PRODUCT_QUICK_VIEW_SERVICE:
            ProxyService.LOGGER.debug("Composite Product Quick View Service, request : {}", requestMap);
            compositeProductQuickViewService.getQuickViewProductData(requestMap, responseMap);
            ProxyService.LOGGER.debug("Web Module Composite Product Quick View Service Called");
            break;
        case Constant.LOGGOFF_SERVICE:
          logServiceCall(logOff);
          response = buildResponse(logOff.logOff(requestMap),logOff,responseMap);
          break;
        case Constant.CONSUMER_LOYALTY_REWARDS:
          logServiceCall(consumerLoyaltyRewardsService);
          response = buildResponse(consumerLoyaltyRewardsService.fetch(requestMap),consumerLoyaltyRewardsService,responseMap);
          break;
        case Constant.PRODUCT_AVAILABILITY_SERVICE:
          logServiceCall(productAvailability);
          long productApiStartTime = System.currentTimeMillis();
          response = buildResponse(productAvailability.fetch(requestMap),productAvailability,responseMap);
          ProxyService.LOGGER.debug("Product Availability API delta time : {}", System.currentTimeMillis() - productApiStartTime);
          break;
        case Constant.PRODUCT_QUICKVIEW_SERVICE:
          productQuickViewService.getQuickViewProductData(requestMap,responseMap);
          break;
        case Constant.NEW_PRODUCT_ADD_ON:
          response = productAddonsService.fetch(requestMap);
          break;
        default:
          ProxyService.LOGGER.error(NO_RESOURCE_FOUND_ERROR, serviceName, HttpConstants.METHOD_GET);
          break;
      }
      return response;
    }
    /**
     *
     * @param requestMap
     * @param responseMap
     * @param serviceName
     */
    private void handleWebModuleServices(Map<String, Object> requestMap, Map<String, Object> responseMap,
                                         String serviceName) {
        long startTime = System.currentTimeMillis();
        JSONObject response = null;
        String methodType = String.valueOf(requestMap.get(Constant.METHOD_TYPE));

        try {
            switch (methodType) { // case are listed alphabetically.
                case HttpConstants.METHOD_GET: 
                    response = handleWebGetServices(requestMap, responseMap, serviceName);
                    break;
                case HttpConstants.METHOD_POST:
                    response = handleWebPostServices(requestMap, responseMap, serviceName);
                    break;
                case HttpConstants.METHOD_PUT:
                    response = handleWebPutServices(requestMap, responseMap, serviceName);
                    break;
                case HttpConstants.METHOD_DELETE:
                    response = handleWebDeleteServices(requestMap, responseMap, serviceName);
                    break;
                default:
                    ProxyService.LOGGER.error("Invalid method type: {}", methodType);
                    break;
            }
        } catch (ServiceException se) {
            long endTime = System.currentTimeMillis();
            boolean isPropagateError = false;
            LOGGER.debug(Constant.EXECUTION_TIME_LOG, "ServiceException in makeServiceCalls method",
                    endTime - startTime);

            if (se.isPropagateError() && StringUtils.isNumeric(se.getErrorKey())) {
              LOGGER.error(String.format("WCS service failure, serviceName: %s, http error code: %s", serviceName, se.getErrorKey()), se);
              isPropagateError = true;
              responseMap.put(Constant.SERVICE_ERROR_MESSAGE, se.getErrorMessage());
              responseMap.put(Constant.ERROR_HTTP_STATUS, se.getErrorKey());
              responseMap.put(Constant.SERVICE_ERROR_BODY, se.getResponseBody());
            } else {
              response = ResponseFormatGetter.getErrorJson(se);
            }

            responseMap.put(Constant.PROPAGATE_SERVICE_ERRORS, isPropagateError);
        }
        if (null != response) {
            responseMap.put(Constant.RESPONSE_BODY, response.toString());
        }
        long endTime = System.currentTimeMillis();
        LOGGER.debug(Constant.EXECUTION_TIME_LOG, "handleWebModuleServices", endTime - startTime);
    }

    /**
     *
     * @param requestMap
     * @param responseMap
     * @param serviceName
     * @param response
     */
    private void handleMobileModuleServices(Map<String, Object> requestMap, Map<String, Object> responseMap,
                                            String serviceName, JSONObject response) {
        long startTime = System.currentTimeMillis();
        LOGGER.debug("requestMap is {} for service {}", requestMap, serviceName);

        if (null != response) {
            responseMap.put(Constant.RESPONSE_BODY, response.toString());
        }
        long endTime = System.currentTimeMillis();
        LOGGER.debug(Constant.EXECUTION_TIME_LOG, "handleMobileModuleServices", endTime - startTime);
    }

    /**
     * Returns Array of Selectors
     *
     * @param selectors
     * @return
     */
    private String[] getTouchPointSelectors(String[] selectors) {
        String[] selectorsToReturn = new String[2];

        if (selectors.length >= 2) {
            selectorsToReturn[0] = selectors[0];
            selectorsToReturn[1] = selectors[1];
        }

        return selectorsToReturn;
    }

    /**
     * Build {@link JSONObject} response.
     *
     * @param serviceResponse
     * @param service
     * @param responseMap
     * @return
     * @throws ServiceException
     */
    private JSONObject buildResponse(final BaseResponse serviceResponse, final BaseService service,
            final Map<String, Object> responseMap) throws ServiceException {
        responseMap.put(Constant.RESPONSE_COOKIES_KEY, serviceResponse.getCookieList());
        responseMap.put(Constant.RESPONSE_HEADERS, serviceResponse.getResponseHeaders());
        ProxyService.LOGGER.debug("Service : {}, Response : {}", new Object [] {
            service.getClass().getSimpleName(), serviceResponse});
        serviceResponse.setCookieList(null);
        return service.getResponseValueAsJson(serviceResponse);
    }

    /**
     * Log the service calls.
     *
     * @param service
     */
    private void logServiceCall(final BaseService service) {
        ProxyService.LOGGER.debug("Invoking service: {}", service.getClass().getSimpleName());
    }

    /**
     * Log the service and request calls.
     *
     * @param service
     */
    private void logServiceCall(final BaseService service, final Map<String, Object> requestMap) {
      ProxyService.LOGGER.debug("Invoking service: {}, request : {}",
          service.getClass().getSimpleName(), requestMap);
    }
}
