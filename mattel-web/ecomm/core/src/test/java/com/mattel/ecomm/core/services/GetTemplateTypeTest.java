package com.mattel.ecomm.core.services;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.osgi.service.component.annotations.Reference;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@RunWith(PowerMockRunner.class)
public class GetTemplateTypeTest {

  @InjectMocks
  GetTemplateType getTemplateType;
  @Mock
  private GetResourceResolver getResourceResolver;
  @Mock
  ResourceResolver resourceResolver;
  @Mock
  private PropertyReaderService propertyReaderService;
  private static final String PRODUCT_TYPE= "doll";
  private String clientIdentifier="testClientIdentifier";
  private String adminPagePath="/content/AG/en";
  @Mock
  private Resource resource;
  @Mock
  private Node node;
  @Mock
  private MultifieldReader multifieldReader;
  @Mock
  private ValueMap propertyMap;
  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testGetTemplatePath() {
    Map<String, ValueMap> valueMap=new HashMap<String, ValueMap>();
    valueMap.put("productType", propertyMap);
    Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(propertyReaderService.getadminPagePath(Mockito.anyString())).thenReturn(adminPagePath);
    Mockito.when(resourceResolver.getResource(Mockito.anyString())).thenReturn(resource);
    Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
    Mockito.when(propertyMap.get("pageUrl",String.class)).thenReturn(adminPagePath);
    Mockito.when(propertyMap.get("productType",String.class)).thenReturn("doll");
    Mockito.when(multifieldReader.propertyReader(node)).thenReturn(valueMap);
    Mockito.when(resourceResolver.isLive()).thenReturn(true);
    String templatePath = getTemplateType.getTemplatePath(PRODUCT_TYPE, clientIdentifier); 
    Assert.assertNotNull(templatePath);
    Assert.assertEquals(adminPagePath, templatePath);
  }

}
