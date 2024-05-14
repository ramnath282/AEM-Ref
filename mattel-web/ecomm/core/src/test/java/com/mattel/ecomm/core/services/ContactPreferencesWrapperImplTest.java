package com.mattel.ecomm.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ContactPreferences;
import com.mattel.ecomm.coreservices.core.pojos.ContactPrefSEQMap;
import com.mattel.ecomm.coreservices.core.pojos.ContactPreferencesResponse;
import com.mattel.ecomm.coreservices.core.pojos.ContactPreferencesUIResponse;

public class ContactPreferencesWrapperImplTest {

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Mock
  ContactPreferences contactPreferences;

  @Mock
  ContactPrefSEQMap contactPrefSEQMap;

  @InjectMocks
  ContactPreferencesWrapperImpl ContactPreferencesWrapperImpl;

  private Map<String, Object> requestMap;

  @Test
  public void test_getFormattedContactPreferences() throws ServiceException {
    addList();
    requestMap = new HashMap<>();
    requestMap.put(Constant.PART_NUMBER, "1234");
    requestMap.put(Constant.STORE_KEY, "AG");
    requestMap.put(Constant.DOMAIN_KEY, "AG");
    ContactPreferencesResponse contactPreferencesResponse = Mockito
        .mock(ContactPreferencesResponse.class);
    Mockito.when(contactPreferences.getContactPreferences(requestMap))
        .thenReturn(contactPreferencesResponse);
    Mockito.when(contactPreferencesResponse.getContactPrefSEQMap()).thenReturn(contactPrefSEQMap);
    addList();
    Mockito.when(contactPreferencesResponse.getUserContactPrefCTL())
        .thenReturn("userContactPrefCTLArr");
    Mockito.when(contactPreferencesResponse.getUserContactPrefLOY())
        .thenReturn("userContactPrefLOYArr");
    Mockito.when(contactPreferencesResponse.getUserContactPrefCAT())
        .thenReturn("userContactPrefCATArr");
    Mockito.when(contactPreferencesResponse.getUserContactPrefGL())
        .thenReturn("userContactPrefGLArr");
    System.out.println(contactPreferencesResponse.getUserContactPrefCTL());
    ContactPreferencesUIResponse contactPreferencesUIResponse = ContactPreferencesWrapperImpl
        .getFormattedContactPreferences(requestMap);
    Assert.assertNotNull(contactPreferencesResponse);
    Assert.assertNotNull(contactPreferencesUIResponse);
  }

  public void addList() {
    List<String> mockList1 = new ArrayList<>();
    mockList1.add("ContPrefCtl_1");
    Mockito.when(contactPrefSEQMap.getContPrefCtl()).thenReturn(mockList1);

    List<String> mockList2 = new ArrayList<>();
    mockList2.add("ContPrefLoy_1");
    Mockito.when(contactPrefSEQMap.getContPrefLoy()).thenReturn(mockList2);

    List<String> mockList3 = new ArrayList<>();
    mockList3.add("ContPrefCat_1");
    Mockito.when(contactPrefSEQMap.getContPrefCat()).thenReturn(mockList3);

    List<String> mockList4 = new ArrayList<>();
    mockList4.add("ContPrefCat_1");
    Mockito.when(contactPrefSEQMap.getContPrefCat()).thenReturn(mockList4);
  }
}
