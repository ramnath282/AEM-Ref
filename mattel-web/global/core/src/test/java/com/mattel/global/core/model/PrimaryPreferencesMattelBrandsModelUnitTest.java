
package com.mattel.global.core.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.Node;

import com.mattel.global.core.pojo.PrimaryPreferencesMattelBrandsPojo;
import com.mattel.global.core.services.MultifieldReader;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class PrimaryPreferencesMattelBrandsModelUnitTest {
    @InjectMocks
    private PrimaryPreferencesMattelBrandsModel primaryPreferencesMattelBrandsModel;
    @Mock
    Resource resource;
    @Mock
    Node brandsList;

    MultifieldReader multifieldReader;
    Map<String, ValueMap> multifieldProperty;
    ValueMap valueMap;
    Map.Entry<String, ValueMap> entry;

    private List<PrimaryPreferencesMattelBrandsPojo> mattelbrandslist;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        multifieldReader = Mockito.mock(MultifieldReader.class);
        valueMap = Mockito.mock(ValueMap.class);
        entry = Mockito.mock(Entry.class);
        primaryPreferencesMattelBrandsModel.setMultifieldReader(multifieldReader);
        primaryPreferencesMattelBrandsModel.setBrandsList(brandsList);
        primaryPreferencesMattelBrandsModel.setResource(resource);
        multifieldProperty = new HashMap<>();
        multifieldProperty.put("document", valueMap);
        multifieldProperty.put("document", valueMap);
        Mockito.when(entry.getValue()).thenReturn(valueMap);
        mattelbrandslist = new java.util.ArrayList<com.mattel.global.core.pojo.PrimaryPreferencesMattelBrandsPojo>();
        primaryPreferencesMattelBrandsModel.setMattelBrandsList(mattelbrandslist);
        primaryPreferencesMattelBrandsModel.toString();
    }

    @Test
    public void testInit() throws Exception {
        Mockito.when(resource.getPath()).thenReturn("/content/ag");
        Mockito.when(multifieldReader.propertyReader(brandsList)).thenReturn(multifieldProperty);
        Mockito.when(entry.getValue().get("brandTitle", String.class)).thenReturn("title");
        Mockito.when(entry.getValue().get("brandDescription", String.class)).thenReturn("description");
        Mockito.when(entry.getValue().get("brandPreferenceID", String.class)).thenReturn("preferenceID");
        Mockito.when(entry.getValue().get("alwaysEnglish", String.class)).thenReturn("Y");

        primaryPreferencesMattelBrandsModel.init();
        Assert.assertEquals(mattelbrandslist, primaryPreferencesMattelBrandsModel.getMattelBrandsList());
    }

    @Test
    public void testObj() throws Exception {
        Assert.assertNotNull(primaryPreferencesMattelBrandsModel);
    }

}
