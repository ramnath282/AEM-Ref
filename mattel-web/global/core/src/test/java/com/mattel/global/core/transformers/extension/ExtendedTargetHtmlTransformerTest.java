package com.mattel.global.core.transformers.extension;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.rewriter.ProcessingComponentConfiguration;
import org.apache.sling.rewriter.ProcessingContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.mattel.global.core.constants.Constant;
import com.mattel.global.core.utils.PropertyReaderUtils;

@RunWith(PowerMockRunner.class)
public class ExtendedTargetHtmlTransformerTest {

  @InjectMocks
  private ExtendedTargetHtmlTransformer extendedTargetHtmlTransformer;

  @Mock
  private ProcessingContext processingContext;

  @Mock
  private ProcessingComponentConfiguration processingComponentConfiguration;
  
  @Mock
  private PropertyReaderUtils propertyReaderUtils;

  @Before
  public void setup() throws Exception {
    SlingHttpServletRequest request = Mockito.mock(SlingHttpServletRequest.class);
    Mockito.when(processingContext.getRequest()).thenReturn(request);
    Mockito.when(request.getRequestURI()).thenReturn("/content/experience-fragments/ag/shop/marketing-contents/nanea-accessories-cta.nocloudconfigs.atoffer.html");
    ResourceResolver resolver = Mockito.mock(ResourceResolver.class);
    Mockito.when(request.getResourceResolver()).thenReturn(resolver);
    Resource resource = Mockito.mock(Resource.class);
    Mockito.when(resolver.getResource("/content/experience-fragments/ag/shop/marketing-contents/nanea-accessories-cta.")).thenReturn(resource);
    ValueMap valueMap = Mockito.mock(ValueMap.class);
    Mockito.when(resource.getValueMap()).thenReturn(valueMap);
    Mockito.when(valueMap.get(Constant.SITE_KEY, String.class)).thenReturn("site-key");
    Mockito.when(propertyReaderUtils.getSiteDomain("site-key")).thenReturn("site-domain");
  }

  @Test
  public void testInit() throws IOException {
    extendedTargetHtmlTransformer.init(processingContext, processingComponentConfiguration);
  }
  
  @Test
  public void testExtendedTargetHtmlTransformer() throws IOException, SAXException {
    Attributes attributes = Mockito.mock(Attributes.class);
    Mockito.when(attributes.getLength()).thenReturn(1);
    Mockito.when( attributes.getLocalName(Mockito.anyInt())).thenReturn("attrName-src");
    Mockito.when(attributes.getIndex("attrName-src")).thenReturn(1);
    Mockito.when(attributes.getValue(Mockito.anyInt())).thenReturn("test");
    extendedTargetHtmlTransformer.startElement("srcset", "script", "qName", attributes);
  }
  
  @Test
  public void testExtendedTargetHtmlTransformer1() throws IOException, SAXException, IllegalArgumentException, IllegalAccessException {
    Attributes attributes = Mockito.mock(Attributes.class);
    Mockito.when(attributes.getLength()).thenReturn(1);
    Mockito.when( attributes.getLocalName(Mockito.anyInt())).thenReturn("attrName-srcset");
    Mockito.when(attributes.getIndex("attrName-src")).thenReturn(1);
    Mockito.when(attributes.getValue(Mockito.anyInt())).thenReturn("test");
    MemberModifier.field(ExtendedTargetHtmlTransformer.class, "siteDomain").set(extendedTargetHtmlTransformer,
        "/imageUrl");
    extendedTargetHtmlTransformer.startElement("srcset", "source", "qName", attributes);
  }
  
  @Test
  public void testGetterSetter(){
    extendedTargetHtmlTransformer.dispose();
    extendedTargetHtmlTransformer.setSiteDomain("siteDomain");
    assertEquals("siteDomain", extendedTargetHtmlTransformer.getSiteDomain());
  }

}
