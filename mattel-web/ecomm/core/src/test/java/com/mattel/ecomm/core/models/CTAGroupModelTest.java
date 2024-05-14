package com.mattel.ecomm.core.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.core.services.MultifieldReader;

@RunWith(PowerMockRunner.class)
public class CTAGroupModelTest {
  @InjectMocks
  CTAGroupModel ctaGroupModel;
  @Mock
  private Resource resource;
  @Mock
  private ResourceResolver resolver;
  MultifieldReader multifieldReader;
  Node ctaGroup;
  Map<String, ValueMap> multifieldProperty;
  Map.Entry<String, ValueMap> entry;
  ValueMap valueMap;

  @SuppressWarnings("unchecked")
  @Before
  public void setup() throws Exception {
    entry = Mockito.mock(Entry.class);
    valueMap = Mockito.mock(ValueMap.class);
    multifieldReader = Mockito.mock(MultifieldReader.class);
    ctaGroup = Mockito.mock(Node.class);
    multifieldProperty = new HashMap<>();
    multifieldProperty.put("document", valueMap);
    multifieldProperty.put("document", valueMap);
    Mockito.when(entry.getValue()).thenReturn(valueMap);

  }

  @Test
  public void testInit() throws Exception {
    MemberModifier.field(CTAGroupModel.class, "multifieldReader").set(ctaGroupModel,
        multifieldReader);
    MemberModifier.field(CTAGroupModel.class, "ctaGroup").set(ctaGroupModel, ctaGroup);
    Mockito.when(entry.getValue()).thenReturn(valueMap);
    Mockito.when(multifieldReader.propertyReader(ctaGroup)).thenReturn(multifieldProperty);
    ctaGroupModel.init();
  }

  @Test
  public void testInitWithNullctaGroup() throws Exception {
    MemberModifier.field(CTAGroupModel.class, "multifieldReader").set(ctaGroupModel,
        multifieldReader);
    MemberModifier.field(CTAGroupModel.class, "ctaGroup").set(ctaGroupModel, null);
    ctaGroupModel.init();
  }

}
