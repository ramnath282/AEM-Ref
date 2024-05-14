package com.mattel.ecomm.core.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
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

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.services.MultifieldReader;

@RunWith(PowerMockRunner.class)
public class BasicInformationModelTest {
    @InjectMocks
    private BasicInformationModel basicInformationModel;
    @Mock
    private Resource resource,pageResource;
    @Mock
    private ResourceResolver resolver;
    @Mock
    TagManager tagManager;
    @Mock
    Tag tagObject;
    @Mock
    PageManager pageManager;
    @Mock
    Page page;
    MultifieldReader multifieldReader;
    Node dolls;
    Map<String, ValueMap> multifieldProperty;
    ValueMap valueMap;
    Map.Entry<String, ValueMap> entry;
    
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws IllegalAccessException {
        String[] eyeColorCategories = { "eyeCategory1" };
        String[] hairColorCategories = { "hairCategory1" };
        MemberModifier.field(BasicInformationModel.class, "resource").set(basicInformationModel, resource);
        MemberModifier.field(BasicInformationModel.class, "hairColorCategories").set(basicInformationModel,
                hairColorCategories);
        MemberModifier.field(BasicInformationModel.class, "eyeColorCategories").set(basicInformationModel,
                eyeColorCategories);
        MemberModifier.field(BasicInformationModel.class, "type").set(basicInformationModel, "hairColorScreen");
        multifieldReader = Mockito.mock(MultifieldReader.class);
        entry = Mockito.mock(Entry.class);
        valueMap = Mockito.mock(ValueMap.class);
        dolls = Mockito.mock(Node.class);
        basicInformationModel.setDolls(dolls);
        basicInformationModel.setMultifieldReader(multifieldReader);
        multifieldProperty = new HashMap<>();
        multifieldProperty.put("document", valueMap);
        multifieldProperty.put("document", valueMap);
        Mockito.when(entry.getValue()).thenReturn(valueMap);
        Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
        Mockito.when(resolver.adaptTo(TagManager.class)).thenReturn(tagManager);
    }

    @Test
    public void testInit() throws JSONException {
        String[] dollCategories = { "dollCategory1" };
        Mockito.when(multifieldReader.propertyReader(dolls)).thenReturn(multifieldProperty);
        Mockito.when(entry.getValue().get("dollsCategories", String[].class)).thenReturn(dollCategories);
        Mockito.when(entry.getValue().get("dollImage", String.class)).thenReturn("dollImage");
        Mockito.when(entry.getValue().get("dollDescription", String.class)).thenReturn("dollDescription");
        Mockito.when(tagManager.resolve("dollCategory1")).thenReturn(tagObject);
        Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
        Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
        Mockito.when(page.getContentResource()).thenReturn(pageResource);
        Mockito.when(pageResource.getValueMap()).thenReturn(valueMap);
        Mockito.when(valueMap.get("productName", String.class)).thenReturn("wellnessProduct");
        Mockito.when(valueMap.get("dollsType", String.class)).thenReturn("18 inch");
        Mockito.when(valueMap.get("treatmentProductId", String.class)).thenReturn("BWR");
        Mockito.when(tagManager.resolve("dollsType")).thenReturn(tagObject);
        Mockito.when(tagManager.resolve("BWR")).thenReturn(tagObject);
        Mockito.when(tagObject.getTitle()).thenReturn("tagTitle");
        
        basicInformationModel.init();
        Assert.assertNotNull(basicInformationModel.getDolls());
        Assert.assertNotNull(basicInformationModel.getMultifieldReader());
        Assert.assertNotNull(basicInformationModel.getDolls());
        Assert.assertNotNull(basicInformationModel.getKeyList());
        Assert.assertNotNull(basicInformationModel.getTreatmentSkuId());
        Assert.assertNotNull(basicInformationModel.getDollType());

    }

    @Test
    public void testEyeColorTagList() throws IllegalAccessException {
        MemberModifier.field(BasicInformationModel.class, "type").set(basicInformationModel, "eyeColorScreen");
        Mockito.when(tagManager.resolve("eyeCategory1")).thenReturn(tagObject);
        List<Tag> tagList = basicInformationModel.getEyeColorTagList();
        Assert.assertNotNull(tagList);
        Assert.assertEquals(1, tagList.size());
    }

    @Test
    public void testHairColorTagList() throws IllegalAccessException {
        MemberModifier.field(BasicInformationModel.class, "type").set(basicInformationModel, "hairColorScreen");
        Mockito.when(tagManager.resolve("hairCategory1")).thenReturn(tagObject);
        List<Tag> tagList = basicInformationModel.getHairColorTagList();
        Assert.assertNotNull(tagList);
        Assert.assertEquals(1, tagList.size());
    }

}
