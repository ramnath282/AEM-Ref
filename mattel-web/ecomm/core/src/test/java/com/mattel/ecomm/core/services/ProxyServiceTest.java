package com.mattel.ecomm.core.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.ecomm.core.interfaces.ContactPreferencesWrapper;
import com.mattel.ecomm.core.interfaces.ProductInterestWrapper;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.AddChildInformation;
import com.mattel.ecomm.coreservices.core.interfaces.AddDefaultAddress;
import com.mattel.ecomm.coreservices.core.interfaces.AddGiftCardService;
import com.mattel.ecomm.coreservices.core.interfaces.AddressInfo;
import com.mattel.ecomm.coreservices.core.interfaces.AddressValidation;
import com.mattel.ecomm.coreservices.core.interfaces.AgRewards;
import com.mattel.ecomm.coreservices.core.interfaces.ApplyRewards;
import com.mattel.ecomm.coreservices.core.interfaces.CheckGiftCardMessageValidity;
import com.mattel.ecomm.coreservices.core.interfaces.ChildInformation;
import com.mattel.ecomm.coreservices.core.interfaces.CompositeMyAccountService;
import com.mattel.ecomm.coreservices.core.interfaces.CompositePersonalInfoService;
import com.mattel.ecomm.coreservices.core.interfaces.CompositeProductQuickViewService;
import com.mattel.ecomm.coreservices.core.interfaces.ConsumerLoyaltyRewardsService;
import com.mattel.ecomm.coreservices.core.interfaces.DeleteChildInformation;
import com.mattel.ecomm.coreservices.core.interfaces.DeleteDefaultAddress;
import com.mattel.ecomm.coreservices.core.interfaces.EmailValidationCode;
import com.mattel.ecomm.coreservices.core.interfaces.GuestIdentityService;
import com.mattel.ecomm.coreservices.core.interfaces.LogOff;
import com.mattel.ecomm.coreservices.core.interfaces.Login;
import com.mattel.ecomm.coreservices.core.interfaces.Minicart;
import com.mattel.ecomm.coreservices.core.interfaces.OrderHistoryDetail;
import com.mattel.ecomm.coreservices.core.interfaces.PDPListPrice;
import com.mattel.ecomm.coreservices.core.interfaces.PDPOfferPrice;
import com.mattel.ecomm.coreservices.core.interfaces.PDPProduct;
import com.mattel.ecomm.coreservices.core.interfaces.PaymentInfo;
import com.mattel.ecomm.coreservices.core.interfaces.PersonalInfo;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAddOnDetailsService;
import com.mattel.ecomm.coreservices.core.interfaces.RecentOrderHistory;
import com.mattel.ecomm.coreservices.core.interfaces.RegisteredDolls;
import com.mattel.ecomm.coreservices.core.interfaces.Registration;
import com.mattel.ecomm.coreservices.core.interfaces.RemoveReward;
import com.mattel.ecomm.coreservices.core.interfaces.ResetPassword;
import com.mattel.ecomm.coreservices.core.interfaces.RewardsMembershipService;
import com.mattel.ecomm.coreservices.core.interfaces.SessionStatus;
import com.mattel.ecomm.coreservices.core.interfaces.ShippingModes;
import com.mattel.ecomm.coreservices.core.interfaces.StoreAvailability;
import com.mattel.ecomm.coreservices.core.interfaces.UpdateChildInfo;
import com.mattel.ecomm.coreservices.core.interfaces.UpdateContactPreferences;
import com.mattel.ecomm.coreservices.core.interfaces.UpdateDefaultAddress;
import com.mattel.ecomm.coreservices.core.interfaces.UpdateDefaultShippingMethod;
import com.mattel.ecomm.coreservices.core.interfaces.UpdateDollRegistration;
import com.mattel.ecomm.coreservices.core.interfaces.UpdatePassword;
import com.mattel.ecomm.coreservices.core.interfaces.UpdatePaymentInfo;
import com.mattel.ecomm.coreservices.core.interfaces.UpdatePersonalInfo;
import com.mattel.ecomm.coreservices.core.interfaces.UpdateStoreAndProductInterest;
import com.mattel.ecomm.coreservices.core.pojos.AddDefaultAddressResponse;
import com.mattel.ecomm.coreservices.core.pojos.AddressInfoResponse;
import com.mattel.ecomm.coreservices.core.pojos.AgRewardsResponse;
import com.mattel.ecomm.coreservices.core.pojos.ApplyRewardsResponse;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.ChildInformationResponse;
import com.mattel.ecomm.coreservices.core.pojos.ConsumerLoyaltyRewardsResponse;
import com.mattel.ecomm.coreservices.core.pojos.ContactPreferencesUIResponse;
import com.mattel.ecomm.coreservices.core.pojos.EmailValidationCodeResponse;
import com.mattel.ecomm.coreservices.core.pojos.GiftCardMessageValidity;
import com.mattel.ecomm.coreservices.core.pojos.LogOffResponse;
import com.mattel.ecomm.coreservices.core.pojos.LoginResponse;
import com.mattel.ecomm.coreservices.core.pojos.MiniCartResponse;
import com.mattel.ecomm.coreservices.core.pojos.OrderHistoryDetailResponse;
import com.mattel.ecomm.coreservices.core.pojos.PDPListPriceResponse;
import com.mattel.ecomm.coreservices.core.pojos.PDPOfferPriceResponse;
import com.mattel.ecomm.coreservices.core.pojos.PDPResponse;
import com.mattel.ecomm.coreservices.core.pojos.PaymentInfoResponse;
import com.mattel.ecomm.coreservices.core.pojos.PersonalInfoResponse;
import com.mattel.ecomm.coreservices.core.pojos.ProductAddOnResponse;
import com.mattel.ecomm.coreservices.core.pojos.RecentOrderHistoryResponse;
import com.mattel.ecomm.coreservices.core.pojos.RegisteredDollsResponse;
import com.mattel.ecomm.coreservices.core.pojos.RegistrationResponse;
import com.mattel.ecomm.coreservices.core.pojos.RemoveRewardResponse;
import com.mattel.ecomm.coreservices.core.pojos.ResetPasswordResponse;
import com.mattel.ecomm.coreservices.core.pojos.RewardsMembershipResponse;
import com.mattel.ecomm.coreservices.core.pojos.SessionStatusResponse;
import com.mattel.ecomm.coreservices.core.pojos.ShippingModesResponse;
import com.mattel.ecomm.coreservices.core.pojos.StoreAvailabilityResponse;
import com.mattel.ecomm.coreservices.core.pojos.UpdateContactPreferencesResponse;
import com.mattel.ecomm.coreservices.core.pojos.UpdateDefaultAddressResponse;
import com.mattel.ecomm.coreservices.core.pojos.UpdatePasswordResponse;
import com.mattel.ecomm.coreservices.core.pojos.UpdateStoreAndProductInterestResponse;
import com.mattel.ecomm.coreservices.core.pojos.ValidateAddressResponse;
import com.mattel.ecomm.coreservices.core.pojos.productdetailspojos.AddAddOnServiceResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.sling.api.servlets.HttpConstants;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProxyServiceTest {
    private final ObjectMapper mapper = new ObjectMapper();
    // Get Services
    @Mock
    private AddDefaultAddress addDefaultAddress;
    @Mock
    private AddressInfo addressInfo;
    @Mock
    private AgRewards agRewards;
    @Mock
    private ContactPreferencesWrapper contactPreferences;
    @Mock
    private ChildInformation childInformation;
    @Mock
    private RegisteredDolls registeredDolls;
    @Mock
    private OrderHistoryDetail orderHistoryDetail;
    @Mock
    private PaymentInfo paymentInfo;
    @Mock
    private PersonalInfo personalInfo;
    @Mock
    private ProductInterestWrapper productInterest;
    @Mock
    private RecentOrderHistory recentOrderHistory;
    @Mock
    private ShippingModes shippingModes;
    @Mock
    private CompositeMyAccountService compositeMyAccountService;
    @Mock
    private CompositePersonalInfoService compositePersonalInfoService;
    @Mock
    private SessionStatus sessionStatus;
    @Mock
    private Minicart minicart;
    @Mock
    private PDPProduct pdpProduct;
    @Mock
    private ProductAddOnDetailsService productAddOnDetailsService;
    @Mock
    private PDPListPrice pdpListPrice;
    @Mock
    private PDPOfferPrice pdpOfferPrice;
    @Mock
    private CompositeProductQuickViewService compositeProductQuickViewService;
    @Mock
    private CheckGiftCardMessageValidity checkGiftCardMessageValidity;
    
    // POST Services
    @Mock
    private Login login;
    @Mock
    private AddChildInformation addChildInformation;
    @Mock
    private EmailValidationCode emailValidationCode;
    @Mock
    private Registration registration;
    @Mock
    private ResetPassword resetPassword;
    @Mock
    private RewardsMembershipService rewardsMembershipService;
    @Mock
    private UpdateDollRegistration updateDollRegistration;
    @Mock
    private UpdatePaymentInfo updatePaymentInfo;
    @Mock
    private AddGiftCardService addGiftCardService;
    @Mock
    private AddressValidation addressValidation;
    @Mock
    private StoreAvailability storeAvailability;
    @Mock
    private GuestIdentityService guestIdentityService;
    @Mock
    private UpdateContactPreferences updateContactPreferences;
    @Mock
    private UpdateDefaultAddress updateDefaultAddress;
    @Mock
    private UpdateDefaultShippingMethod updateDefaultShippingMethod;
    @Mock
    private UpdatePassword updatePassword;
    @Mock
    private UpdatePersonalInfo updatePersonalInfo;
    @Mock
    private UpdateStoreAndProductInterest updateStoreAndProductInterest;
    // PUT Services
    @Mock
    private UpdateChildInfo updateChildInfo;
    // DELETE Services
    @Mock
    private DeleteChildInformation deleteChildInformation;
    @Mock
    private DeleteDefaultAddress deleteDefaultAddress;
    @Mock
    private LogOff logOff;
    @Mock
    private ConsumerLoyaltyRewardsService consumerLoyaltyRewardsService;
    @Mock
    private ApplyRewards applyRewards;
    @Mock
    private RemoveReward removeReward;
    @InjectMocks
    private ProxyService impl;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testMakeServiceCallsForAddDefaultAddress() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.ADD_DEFAULT_ADDRESS_SERVICE };
        final Map<String, Object> requestMap = new HashMap<>();
        final AddDefaultAddressResponse addDefaultAddressResponse = new AddDefaultAddressResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(addDefaultAddressResponse));
        final List<Cookie> cookies = addCookieList(addDefaultAddressResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_POST);
        Mockito.when(addDefaultAddress.addDefaultAddress(requestMap)).thenReturn(addDefaultAddressResponse);
        Mockito.when(addDefaultAddress.getResponseValueAsJson(addDefaultAddressResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }

    @Test
    public void testMakeServiceCallsForAddressInfoService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.ADDRESS_INFO_SERVICE };
        final Map<String, Object> requestMap = new HashMap<>();
        final AddressInfoResponse addressInfoResponse = new AddressInfoResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(addressInfoResponse));
        final List<Cookie> cookies = addCookieList(addressInfoResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(addressInfo.getAddressInfo(requestMap)).thenReturn(addressInfoResponse);
        Mockito.when(addressInfo.getResponseValueAsJson(addressInfoResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }

    @Test
    public void testMakeServiceCallsForAgRewardsService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.AGREWARDS_SERVICE };
        final Map<String, Object> requestMap = new HashMap<>();
        final AgRewardsResponse agRewardsResponse = new AgRewardsResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(agRewardsResponse));
        final List<Cookie> cookies = addCookieList(agRewardsResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(agRewards.getAgReward(requestMap)).thenReturn(agRewardsResponse);
        Mockito.when(agRewards.getResponseValueAsJson(agRewardsResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }

    @Test
    public void testMakeServiceCallsForGetContactPreferences() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.CONTACT_PREFERENCES_SERVICE };
        final Map<String, Object> requestMap = new HashMap<>();
        final ContactPreferencesUIResponse contactPreferencesUIResponse = new ContactPreferencesUIResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(contactPreferencesUIResponse));
        final List<Cookie> cookies = addCookieList(contactPreferencesUIResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(contactPreferences.getFormattedContactPreferences(requestMap)).thenReturn(contactPreferencesUIResponse);
        Mockito.when(contactPreferences.getResponseValueAsJson(contactPreferencesUIResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }

    @Test
    public void testMakeServiceCallsForGetChildrenInfo() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.GET_CHILD_INFORMATION };
        final Map<String, Object> requestMap = new HashMap<>();
        final ChildInformationResponse childInformationResponse = new ChildInformationResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(childInformationResponse));
        final List<Cookie> cookies = addCookieList(childInformationResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(childInformation.getChildrenInfo(requestMap)).thenReturn(childInformationResponse);
        Mockito.when(childInformation.getResponseValueAsJson(childInformationResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }

    @Test
    public void testMakeServiceCallsForRegisteredDollsService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.GET_DOLL_REG_SERVICE };
        final Map<String, Object> requestMap = new HashMap<>();
        final RegisteredDollsResponse registeredDollsResponse = new RegisteredDollsResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(registeredDollsResponse));
        final List<Cookie> cookies = addCookieList(registeredDollsResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(registeredDolls.fetch(requestMap)).thenReturn(registeredDollsResponse);
        Mockito.when(registeredDolls.getResponseValueAsJson(registeredDollsResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }

    @Test
    public void testMakeServiceCallsForOrderHistoryDetail() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.ORDER_HISTORY_DETAIL };
        final Map<String, Object> requestMap = new HashMap<>();
        final OrderHistoryDetailResponse orderHistoryDetailResponse = new OrderHistoryDetailResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(orderHistoryDetailResponse));
        final List<Cookie> cookies = addCookieList(orderHistoryDetailResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(orderHistoryDetail.getOrderHistoryDetail(requestMap)).thenReturn(orderHistoryDetailResponse);
        Mockito.when(orderHistoryDetail.getResponseValueAsJson(orderHistoryDetailResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }

    @Test
    public void testMakeServiceCallsForPaymentInfoService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.PAYMENT_INFO_SERVICE };
        final Map<String, Object> requestMap = new HashMap<>();
        final PaymentInfoResponse paymentInfoResponse = new PaymentInfoResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(paymentInfoResponse));
        final List<Cookie> cookies = addCookieList(paymentInfoResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(paymentInfo.getPaymentInformation(requestMap)).thenReturn(paymentInfoResponse);
        Mockito.when(paymentInfo.getResponseValueAsJson(paymentInfoResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }

    @Test
    public void testMakeServiceCallsForPersonalInfoService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.PERSONAL_INFO_SERVICE };
        final Map<String, Object> requestMap = new HashMap<>();
        final PersonalInfoResponse personalInfoResponse = new PersonalInfoResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(personalInfoResponse));
        final List<Cookie> cookies = addCookieList(personalInfoResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(personalInfo.getPersonalInfo(requestMap)).thenReturn(personalInfoResponse);
        Mockito.when(personalInfo.getResponseValueAsJson(personalInfoResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }

    @Test
    public void testMakeServiceCallsForProductInterestService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.PRODUCTINTEREST_SERVICE };
        final Map<String, Object> requestMap = new HashMap<>();
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.doNothing().when(productInterest).getStoreAndProductInterest(Mockito.any(), Mockito.any());
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
    }

    @Test
    public void testMakeServiceCallsForRecentOrderHistory() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.RECENT_ORDER_HISTORY };
        final Map<String, Object> requestMap = new HashMap<>();
        final RecentOrderHistoryResponse orderHistoryResponse = new RecentOrderHistoryResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(orderHistoryResponse));
        final List<Cookie> cookies = addCookieList(orderHistoryResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(recentOrderHistory.getRecentOrderHistory(requestMap)).thenReturn(orderHistoryResponse);
        Mockito.when(recentOrderHistory.getResponseValueAsJson(orderHistoryResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }

    @Test
    public void testMakeServiceCallsForShippingModesService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.SHIPPINGMODES_SERVICE };
        final Map<String, Object> requestMap = new HashMap<>();
        final ShippingModesResponse shippingModesResponse = new ShippingModesResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(shippingModesResponse));
        final List<Cookie> cookies = addCookieList(shippingModesResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(shippingModes.getShippingModes(requestMap)).thenReturn(shippingModesResponse);
        Mockito.when(shippingModes.getResponseValueAsJson(shippingModesResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }

    @Test
    public void testMakeServiceCallsForCompositeMyAccountService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.COMPOSITE_MY_ACCOUNT_SERVICE };
        final Map<String, Object> requestMap = new HashMap<>();
        Map<String, Object> responseMap = new HashMap<>();

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(compositeMyAccountService.getCompositeMyAccountServiceResponse(Mockito.any(), Mockito.any())).thenReturn(responseMap);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
    }

    @Test
    public void testMakeServiceCallsForCompositePersonalInfoService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.COMPOSITE_PERSONAL_INFO_SERVICE };
        final Map<String, Object> requestMap = new HashMap<>();
        Map<String, Object> responseMap = new HashMap<>();

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(compositePersonalInfoService.getCompositePersonalInfoServiceResponse(Mockito.any(), Mockito.any())).thenReturn(responseMap);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
    }

    @Test
    public void testMakeServiceCallsForLoginService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.LOGIN_SERVICE };
        final Map<String, Object> requestMap = new HashMap<>();
        final LoginResponse loginResponse = new LoginResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(loginResponse));
        final List<Cookie> cookies = addCookieList(loginResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_POST);
        Mockito.when(login.login(requestMap)).thenReturn(loginResponse);
        Mockito.when(login.getResponseValueAsJson(loginResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }

    @Test
    public void testMakeServiceCallsForAddChildInformation() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.ADD_CHILD_INFORMATION };
        final Map<String, Object> requestMap = new HashMap<>();
        final BaseResponse baseResponse = new BaseResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_POST);
        Mockito.when(addChildInformation.addChildInfo(requestMap)).thenReturn(baseResponse);
        Mockito.when(addChildInformation.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }

    @Test
    public void testMakeServiceCallsForEmailValidationCodeService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.EMAILVALIDATIONCODE_SERVICE };
        final Map<String, Object> requestMap = new HashMap<>();
        final EmailValidationCodeResponse emailValidationCodeResponse = new EmailValidationCodeResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(emailValidationCodeResponse));
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_POST);
        Mockito.when(emailValidationCode.emailValidationCode(requestMap)).thenReturn(emailValidationCodeResponse);
        Mockito.when(emailValidationCode.getResponseValueAsJson(emailValidationCodeResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
    }

    @Test
    public void testMakeServiceCallsForUserRegistrationService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.REGISTRATION_SERVICE };
        final Map<String, Object> requestMap = new HashMap<>();
        final RegistrationResponse registrationResponse = new RegistrationResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(registrationResponse));
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_POST);
        Mockito.when(registration.makeRegistration(requestMap)).thenReturn(registrationResponse);
        Mockito.when(registration.getResponseValueAsJson(registrationResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
    }

    @Test
    public void testMakeServiceCallsForResetPasswordService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.RESETPD_SERVICE };
        final Map<String, Object> requestMap = new HashMap<>();
        final ResetPasswordResponse emailValidationCodeResponse = new ResetPasswordResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(emailValidationCodeResponse));
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_POST);
        Mockito.when(resetPassword.resetPassword(requestMap)).thenReturn(emailValidationCodeResponse);
        Mockito.when(resetPassword.getResponseValueAsJson(emailValidationCodeResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
    }

    @Test
    public void testMakeServiceCallsForRewardMembershipService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.REWARDS_MEMBERSHIP_SERVICE };
        final Map<String, Object> requestMap = new HashMap<>();
        final RewardsMembershipResponse rewardsMembershipResponse = new RewardsMembershipResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(rewardsMembershipResponse));
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_POST);
        Mockito.when(rewardsMembershipService.find(requestMap)).thenReturn(rewardsMembershipResponse);
        Mockito.when(rewardsMembershipService.getResponseValueAsJson(rewardsMembershipResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
    }

    @Test
    public void testMakeServiceCallsForUpdateDollService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.UPDATE_DOLL_REG_SERVICE };
        final Map<String, Object> requestMap = new HashMap<>();
        final BaseResponse baseResponse = new BaseResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_POST);
        Mockito.when(updateDollRegistration.save(requestMap)).thenReturn(baseResponse);
        Mockito.when(updateDollRegistration.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }

    @Test
    public void testMakeServiceCallsForUpdatePaymentInfoService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.UPDATE_PAYMENT_INFO_SERVICE };
        final Map<String, Object> requestMap = new HashMap<>();
        final BaseResponse baseResponse = new BaseResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_POST);
        Mockito.when(updatePaymentInfo.updateCardDetails(requestMap)).thenReturn(baseResponse);
        Mockito.when(updatePaymentInfo.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }

    @Test
    public void testMakeServiceCallsForUpdateChildInformation() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.UPDATE_CHILD_INFORMATION };
        final Map<String, Object> requestMap = new HashMap<>();
        final BaseResponse baseResponse = new BaseResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_PUT);
        Mockito.when(updateChildInfo.updateChildInfo(requestMap)).thenReturn(baseResponse);
        Mockito.when(updateChildInfo.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }

    @Test
    public void testMakeServiceCallsForDeleteChildInformation() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.DELETE_CHILD_INFORMATION };
        final Map<String, Object> requestMap = new HashMap<>();
        final BaseResponse baseResponse = new BaseResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_DELETE);
        Mockito.when(deleteChildInformation.deleteChildInfo(requestMap)).thenReturn(baseResponse);
        Mockito.when(deleteChildInformation.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }
    
    @Test
    public void testMakeServiceCallsForSessionStatus() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.SESSION_STATUS_SERVICE};
        final Map<String, Object> requestMap = new HashMap<>();
        final SessionStatusResponse baseResponse = new SessionStatusResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(sessionStatus.get(requestMap)).thenReturn(baseResponse);
        Mockito.when(sessionStatus.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }

    @Test
    public void testMakeServiceCallsForMinicart() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.MINICART_SERVICE};
        final Map<String, Object> requestMap = new HashMap<>();
        final MiniCartResponse baseResponse = new MiniCartResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(minicart.fetch(requestMap)).thenReturn(baseResponse);
        Mockito.when(minicart.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }
    @Test
    public void testMakeServiceCallsForPDPProduct() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.PDPPRODUCT_SERVICE};
        final Map<String, Object> requestMap = new HashMap<>();
        final PDPResponse baseResponse = new PDPResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;


        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(pdpProduct.fetch(requestMap)).thenReturn(baseResponse);
        Mockito.when(pdpProduct.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }

    @Test
    public void testMakeServiceCallsForProductAddon() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.PRODUCT_ADD_ON_DETAILS_SERVICE};
        final Map<String, Object> requestMap = new HashMap<>();
        final ProductAddOnResponse baseResponse = new ProductAddOnResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;


        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(productAddOnDetailsService.fetch(requestMap)).thenReturn(baseResponse);
        Mockito.when(productAddOnDetailsService.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }

    @Test
    public void testMakeServiceCallsForPDPListPrice() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.PDP_LISTPRICE_SERVICE};
        final Map<String, Object> requestMap = new HashMap<>();
        final PDPListPriceResponse baseResponse = new PDPListPriceResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;


        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(pdpListPrice.getListPrice(requestMap)).thenReturn(baseResponse);
        Mockito.when(pdpListPrice.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }
    @Test
    public void testMakeServiceCallsForPDPOfferPrice() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.PDP_OFFERPRICE_SERVICE};
        final Map<String, Object> requestMap = new HashMap<>();
        final PDPOfferPriceResponse baseResponse = new PDPOfferPriceResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;


        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(pdpOfferPrice.getOffertPrice(requestMap)).thenReturn(baseResponse);
        Mockito.when(pdpOfferPrice.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }
    @Test
    public void testMakeServiceCallsForAddGiftCardService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.ADD_GIFT_CARD_TO_CART_SERVICE};
        final Map<String, Object> requestMap = new HashMap<>();
        final AddAddOnServiceResponse baseResponse = new AddAddOnServiceResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_POST);
        Mockito.when(addGiftCardService.addToCart(requestMap)).thenReturn(baseResponse);
        Mockito.when(addGiftCardService.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }
    @Test
    public void testMakeServiceCallsForCOMPOSITE_PRODUCT_QUICK_VIEW_SERVICE() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.COMPOSITE_PRODUCT_QUICK_VIEW_SERVICE };
        final Map<String, Object> requestMap = new HashMap<>();
        Map<String, Object> responseMap = new HashMap<>();

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(compositeProductQuickViewService.getQuickViewProductData(Mockito.any(), Mockito.any())).thenReturn(responseMap);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
    }
    @Test
    public void testMakeServiceCallsForAddressValidation() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.ADDRESS_VALIDATION_SERVICE};
        final Map<String, Object> requestMap = new HashMap<>();
        final ValidateAddressResponse baseResponse = new ValidateAddressResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_POST);
        Mockito.when(addressValidation.verify(requestMap)).thenReturn(baseResponse);
        Mockito.when(addressValidation.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }
    @Test
    public void testMakeServiceCallsForCheckGiftCardMessageValidity() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.CHECK_GIFT_CARD_MESSAGE_VALIDITY_SERVICE};
        final Map<String, Object> requestMap = new HashMap<>();
        final GiftCardMessageValidity baseResponse = new GiftCardMessageValidity();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_POST);
        Mockito.when(checkGiftCardMessageValidity.verify(requestMap)).thenReturn(baseResponse);
        Mockito.when(checkGiftCardMessageValidity.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }
    @Test
    public void testMakeServiceCallsForStoreAvailability() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.STORE_AVAILABILITY_SERVICE};
        final Map<String, Object> requestMap = new HashMap<>();
        final StoreAvailabilityResponse baseResponse = new StoreAvailabilityResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_POST);
        Mockito.when(storeAvailability.getStoreAvailability(requestMap)).thenReturn(baseResponse);
        Mockito.when(storeAvailability.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }
    @Test
    public void testMakeServiceCallsForGuestIdentityService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.GUEST_IDENTITY};
        final Map<String, Object> requestMap = new HashMap<>();
        final LoginResponse baseResponse = new LoginResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_POST);
        Mockito.when(guestIdentityService.guestIdentity(requestMap)).thenReturn(baseResponse);
        Mockito.when(guestIdentityService.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }
    
    @Test
    public void testMakeServiceCallsForConsumerLoyaltyRewardsService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.CONSUMER_LOYALTY_REWARDS};
        final Map<String, Object> requestMap = new HashMap<>();
        final ConsumerLoyaltyRewardsResponse baseResponse = new ConsumerLoyaltyRewardsResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(consumerLoyaltyRewardsService.fetch(requestMap)).thenReturn(baseResponse);
        Mockito.when(consumerLoyaltyRewardsService.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }
    
    @Test
    public void testMakeServiceCallsForApplyRewardsService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.APPLY_REWARDS};
        final Map<String, Object> requestMap = new HashMap<>();
        final ApplyRewardsResponse baseResponse = new ApplyRewardsResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_POST);
        Mockito.when(applyRewards.applyReward(requestMap)).thenReturn(baseResponse);
        Mockito.when(applyRewards.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }
    
    @Test
    public void testMakeServiceCallsForRemoveRewardService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.REMOVE_REWARDS};
        final Map<String, Object> requestMap = new HashMap<>();
        final RemoveRewardResponse baseResponse = new RemoveRewardResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_POST);
        Mockito.when(removeReward.remove(requestMap)).thenReturn(baseResponse);
        Mockito.when(removeReward.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }
    
    @Test
    public void testMakeServiceCallsForUpdateContactPreferences() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.UPDATE_CONTACTPREFERENCES_SERVICE};
        final Map<String, Object> requestMap = new HashMap<>();
        final UpdateContactPreferencesResponse baseResponse = new UpdateContactPreferencesResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_PUT);
        Mockito.when(updateContactPreferences.updateContactPreferences(requestMap)).thenReturn(baseResponse);
        Mockito.when(updateContactPreferences.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
       
    }
    
    @Test
    public void testMakeServiceCallsForUpdateDefaultAddress() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.UPDATE_DEFAULT_ADDRESS_SERVICE};
        final Map<String, Object> requestMap = new HashMap<>();
        final UpdateDefaultAddressResponse baseResponse = new UpdateDefaultAddressResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_PUT);
        Mockito.when(updateDefaultAddress.updateDefaultAddress(requestMap)).thenReturn(baseResponse);
        Mockito.when(updateDefaultAddress.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
       
    }
    
    @Test
    public void testMakeServiceCallsForUpdateDefaultShippingMethod() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.UPDATE_DEFAULT_SHIPPING_SERVICE};
        final Map<String, Object> requestMap = new HashMap<>();
        final UpdateContactPreferencesResponse baseResponse = new UpdateContactPreferencesResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_PUT);
        Mockito.when(updateDefaultShippingMethod.updateDefaultShipping(requestMap)).thenReturn(baseResponse);
        Mockito.when(updateDefaultShippingMethod.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
       
    }
    
    @Test
    public void testMakeServiceCallsForUpdatePassword() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.UPDATE_PD};
        final Map<String, Object> requestMap = new HashMap<>();
        final UpdatePasswordResponse baseResponse = new UpdatePasswordResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_PUT);
        Mockito.when(updatePassword.updatePassword(requestMap)).thenReturn(baseResponse);
        Mockito.when(updatePassword.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
       
    }
    
    @Test
    public void testMakeServiceCallsForUpdatePersonalInfo() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.UPDATE_PERSONAL_INFO_SERVICE};
        final Map<String, Object> requestMap = new HashMap<>();
        final PersonalInfoResponse baseResponse = new PersonalInfoResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_PUT);
        Mockito.when(updatePersonalInfo.updatePersonalInfo(requestMap)).thenReturn(baseResponse);
        Mockito.when(updatePersonalInfo.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
       
    }
    
    @Test
    public void testMakeServiceCallsForUpdateStoreAndProductInterest() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.UPDATE_PRODUCT_INTEREST_SERVICE};
        final Map<String, Object> requestMap = new HashMap<>();
        final UpdateStoreAndProductInterestResponse baseResponse = new UpdateStoreAndProductInterestResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_PUT);
        Mockito.when(updateStoreAndProductInterest.updateInterests(requestMap)).thenReturn(baseResponse);
        Mockito.when(updateStoreAndProductInterest.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
       
    }
    
    @Test
    public void testMakeServiceCallsForDeleteDefaultAddress() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.DELETE_DEFAULT_ADDRESS_SERVICE};
        final Map<String, Object> requestMap = new HashMap<>();
        final UpdateContactPreferencesResponse baseResponse = new UpdateContactPreferencesResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_DELETE);
        Mockito.when(deleteDefaultAddress.delete(requestMap)).thenReturn(baseResponse);
        Mockito.when(deleteDefaultAddress.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
       
    }
    @Test
    public void testMakeServiceCallsForLogOffService() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.LOGGOFF_SERVICE};
        final Map<String, Object> requestMap = new HashMap<>();
        final LogOffResponse baseResponse = new LogOffResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
        final List<Cookie> cookies = addCookieList(baseResponse);
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(logOff.logOff(requestMap)).thenReturn(baseResponse);
        Mockito.when(logOff.getResponseValueAsJson(baseResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selector);

        Assert.assertNotNull(responseMap);
        Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
        Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
       
    }
    @Test
    public void testMakeServiceCallsForDefault() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.WEB_SELECTOR, "default"};
        final Map<String, Object> requestMap = new HashMap<>();
        Map<String, Object> responseMap = impl.makeServiceCalls(requestMap, selector);  
        Assert.assertNotNull(responseMap);
    }
    @Test
    public void testMakeServiceCallsWithServiceException() throws ServiceException, IOException, JSONException {
      final String[] selector = new String[] { Constant.WEB_SELECTOR, Constant.LOGGOFF_SERVICE};
      final Map<String, Object> requestMap = new HashMap<>();
      final LogOffResponse baseResponse = new LogOffResponse();
      addCookieList(baseResponse);       
      requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
      Mockito.when(logOff.logOff(requestMap)).thenReturn(baseResponse);
      Mockito.when(logOff.getResponseValueAsJson(baseResponse)).thenThrow(ServiceException.class);
      impl.makeServiceCalls(requestMap, selector);
    
    }
    @Test
    public void testMakeServiceCallsForMobileServices() throws ServiceException, IOException, JSONException {
        final String[] selector = new String[] { Constant.MOBILE_SELECTOR, "accountservices"};
        final Map<String, Object> requestMap = new HashMap<>();                
        Map<String, Object> responseMap;      
        responseMap = impl.makeServiceCalls(requestMap, selector);
        final String[] login_selector = new String[] { Constant.MOBILE_SELECTOR, "login"};
        responseMap = impl.makeServiceCalls(requestMap, login_selector);
        final String[] registration_selector = new String[] { Constant.MOBILE_SELECTOR, "registration"};
        responseMap = impl.makeServiceCalls(requestMap, registration_selector);
        final String[] productinterest_selector = new String[] { Constant.MOBILE_SELECTOR, "productinterest"};
        responseMap = impl.makeServiceCalls(requestMap, productinterest_selector);
        final String[] agrewards_selector = new String[] { Constant.MOBILE_SELECTOR, "agrewards"};
        responseMap = impl.makeServiceCalls(requestMap, agrewards_selector);
        final String[] shippingmodes_selector = new String[] { Constant.MOBILE_SELECTOR, "shippingmodes"};
        responseMap = impl.makeServiceCalls(requestMap, shippingmodes_selector);
        final String[] emailvalidationcode_selector = new String[] { Constant.MOBILE_SELECTOR, "emailvalidationcode"};
        responseMap = impl.makeServiceCalls(requestMap, emailvalidationcode_selector);
        final String[] resetpassword_selector = new String[] { Constant.MOBILE_SELECTOR, "resetpassword"};
        responseMap = impl.makeServiceCalls(requestMap, resetpassword_selector);
        Assert.assertNotNull(responseMap);
    }
    @Test
    public void testSessionService() throws ServiceException, IOException, JSONException {    
      final Map<String, Object> requestMap = new HashMap<>();
      final LogOffResponse baseResponse = new LogOffResponse();
      final JSONObject value = new JSONObject(mapper.writeValueAsString(baseResponse));
      final List<Cookie> cookies = addCookieList(baseResponse);
      Map<String, Object> responseMap;

      requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
      Mockito.when(logOff.logOff(requestMap)).thenReturn(baseResponse);
      Mockito.when(logOff.getResponseValueAsJson(baseResponse)).thenReturn(value);
      responseMap = impl.sessionService(requestMap, Constant.LOGGOFF_SERVICE);

      Assert.assertNotNull(responseMap);
      Assert.assertEquals(value.toString(), responseMap.get(Constant.RESPONSE_BODY));
      Assert.assertEquals(cookies, responseMap.get(Constant.RESPONSE_COOKIES_KEY));
    }
    private List<Cookie> addCookieList(BaseResponse baseResponse) {
        final Cookie cookie = new Cookie("JSESSIONID", "0000Ai-NPQF6j4iYMwCVf_brFbR:1b7o43dnq");
        final List<Cookie> cookies = new ArrayList<>();
        cookie.setMaxAge(12345678);
        cookie.setSecure(true);
        cookie.setDomain(".mattel.com");
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setComment("; HttpOnly;");
        cookies.add(cookie);
        baseResponse.setCookieList(cookies);
        return cookies;
    }
}
