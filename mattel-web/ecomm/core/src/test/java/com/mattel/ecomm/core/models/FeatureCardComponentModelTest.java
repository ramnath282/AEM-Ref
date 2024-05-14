package com.mattel.ecomm.core.models;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jcr.Node;

import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.core.pojos.CTAPojo;
import com.mattel.ecomm.core.services.MultifieldReader;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@RunWith(PowerMockRunner.class)
public class FeatureCardComponentModelTest {

  @InjectMocks
  FeatureCardComponentModel featureCardComponentModel;

  List<LinkComponent> buttonList;

  @Mock
  Node buttonsList;

  ValueMap valueMap;
  Map.Entry<String, ValueMap> mapEntry;
  List<CTAPojo> ctaPojos;
  Map<String, ValueMap> map = Mockito.mock(Map.class);
  Iterator<Object> iterator;
  List<Object> list;
  CTAPojo ctaPojo;
  MultifieldReader multifieldReader;
  Set<Map.Entry<String, ValueMap>> entryset = new HashSet<>();

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws Exception {
    buttonList = Mockito.mock(List.class);
    valueMap = Mockito.mock(ValueMap.class);
    mapEntry = Mockito.mock(Map.Entry.class);
    iterator = Mockito.mock(Iterator.class);
    ctaPojos = Mockito.mock(List.class);
    ctaPojo = Mockito.mock(CTAPojo.class);
    list = Mockito.mock(List.class);
    multifieldReader = Mockito.mock(MultifieldReader.class);
    buttonsList = Mockito.mock(Node.class);
    when(list.iterator()).thenReturn(iterator);
    when(iterator.hasNext()).thenReturn(true, false);
    when(valueMap.get(Mockito.anyString(), any(String.class))).thenReturn("Any String");
    when(iterator.next()).thenReturn(map);
    when(mapEntry.getValue()).thenReturn(valueMap);


  }

  @Test
  public void testGetterSetters() throws IllegalArgumentException, IllegalAccessException {
    MemberModifier.field(FeatureCardComponentModel.class, "description")
        .set(featureCardComponentModel, "description");
    Assert.assertEquals("<p>description</p>", featureCardComponentModel.getDescription());

    MemberModifier.field(FeatureCardComponentModel.class, "title").set(featureCardComponentModel,
        "title");
    Assert.assertEquals("title", featureCardComponentModel.getTitle());

    MemberModifier.field(FeatureCardComponentModel.class, "titleTag").set(featureCardComponentModel,
            "h1");
        Assert.assertEquals("h1", featureCardComponentModel.getTitleTag());

    MemberModifier.field(FeatureCardComponentModel.class, "cardVariant")
        .set(featureCardComponentModel, "cardVariant");
    Assert.assertEquals("cardVariant", featureCardComponentModel.getCardVariant());

    MemberModifier.field(FeatureCardComponentModel.class, "imgAltText")
        .set(featureCardComponentModel, "imgAltText");
    Assert.assertEquals("imgAltText", featureCardComponentModel.getImgAltText());

    MemberModifier.field(FeatureCardComponentModel.class, "smallImage")
        .set(featureCardComponentModel, "smallImage");
    Assert.assertEquals("smallImage", featureCardComponentModel.getSmallImage());

    MemberModifier.field(FeatureCardComponentModel.class, "image").set(featureCardComponentModel,
        "image");
    Assert.assertEquals("image", featureCardComponentModel.getImage());

    MemberModifier.field(FeatureCardComponentModel.class, "textAlign")
        .set(featureCardComponentModel, "textAlign");
    Assert.assertEquals("textAlign", featureCardComponentModel.getTextAlign());

    MemberModifier.field(FeatureCardComponentModel.class, "position").set(featureCardComponentModel,
        "position");
    Assert.assertEquals("position", featureCardComponentModel.getPosition());

  }

  @Test
  public void testInit() throws IllegalArgumentException, IllegalAccessException {
    @SuppressWarnings("unchecked")
    Map.Entry<String, ValueMap> entry = Mockito.mock(Map.Entry.class);
    when(entry.getValue()).thenReturn(valueMap);
    entryset.add(entry);
    when(map.entrySet()).thenReturn(entryset);
    when(multifieldReader.propertyReader(any())).thenReturn(map);
    Mockito.doNothing().when(ctaPojo).setCtaLabel("Target URL");
    Mockito.doNothing().when(ctaPojo).setCtaLink("ctaLink");
    Mockito.doNothing().when(ctaPojo).setCtaStyle("ctaStyle");
    Mockito.doNothing().when(ctaPojo).setCtaType("ctaType");
    featureCardComponentModel.setMultifieldReader(multifieldReader);
    featureCardComponentModel.setCtaGroupDetails(ctaPojos);
    featureCardComponentModel.getCtaGroupDetails();
    featureCardComponentModel.init();

  }

}
