package com.mattel.ecomm.core.models;

import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.request.RequestParameterMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class EmailLightboxModelTest {

  @InjectMocks
  EmailLightboxModel emailLightboxModel;

  @Self
  private Resource resource;

  @Mock
  Node excludedPages;

  @Mock
  Node excludedPageKeywords;

  @Mock
  Node genderOptions;

  @Mock
  Node relationOptions;

  @Mock
  MultifieldReader multifieldReader;

  @Mock
  PropertyReaderService propertyReaderService;

  Map<String, ValueMap> excludeMap;
  ValueMap valMap;
  Map.Entry<String, ValueMap> entry;
  RequestParameterMap requestParam;

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws Exception {
    MemberModifier.field(EmailLightboxModel.class, "resource").set(emailLightboxModel, resource);
    MemberModifier.field(EmailLightboxModel.class, "excludedPages").set(emailLightboxModel,
        excludedPages);
    MemberModifier.field(EmailLightboxModel.class, "excludedPageKeywords").set(emailLightboxModel,
        excludedPageKeywords);
    MemberModifier.field(EmailLightboxModel.class, "genderOptions").set(emailLightboxModel,
        genderOptions);
    MemberModifier.field(EmailLightboxModel.class, "relationOptions").set(emailLightboxModel,
        relationOptions);
    MemberModifier.field(EmailLightboxModel.class, "multifieldReader").set(emailLightboxModel,
        multifieldReader);

    excludeMap = new HashMap<>();
    valMap = Mockito.mock(ValueMap.class);
    excludeMap.put("", valMap);
    entry = Mockito.mock(Map.Entry.class);
    requestParam = Mockito.mock(RequestParameterMap.class);

    Mockito.when(multifieldReader.propertyReader(excludedPages)).thenReturn(excludeMap);
    Mockito.when(multifieldReader.propertyReader(genderOptions)).thenReturn(excludeMap);
    Mockito.when(multifieldReader.propertyReader(relationOptions)).thenReturn(excludeMap);
    Mockito.when(entry.getValue()).thenReturn(valMap);
    Mockito.when(entry.getValue().get("pagePath", String.class)).thenReturn("excludepath");
    Mockito.when(multifieldReader.propertyReader(excludedPageKeywords)).thenReturn(excludeMap);
    Mockito.when(propertyReaderService.getConsumerInfoAPIEndpoint()).thenReturn("/test/uri");
    Mockito.when(propertyReaderService.getConsumerInfoAPIKey()).thenReturn("user_key");
  }

  @Test
  public void testInit() throws ServiceException, JSONException, RepositoryException {
    emailLightboxModel.init();
  }

  @Test
  public void testGetterSetters() throws IllegalArgumentException, IllegalAccessException {
    Assert.assertEquals(multifieldReader, emailLightboxModel.getMultifieldReader());

    Map<String,String> dummyList = null;
    emailLightboxModel.setGenderOptionsList(dummyList);
    emailLightboxModel.setRelationOptionsList(dummyList);
    Assert.assertEquals(dummyList, emailLightboxModel.getGenderOptionsList());
    Assert.assertEquals(dummyList, emailLightboxModel.getRelationOptionsList());
  }
}
