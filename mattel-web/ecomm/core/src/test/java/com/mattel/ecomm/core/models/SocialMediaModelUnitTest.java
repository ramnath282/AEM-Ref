
package com.mattel.ecomm.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
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

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@RunWith(PowerMockRunner.class)
public class SocialMediaModelUnitTest {

    @InjectMocks
    private SocialMediaModel socialMediaModel;
    @Mock
    Resource resource;
    @Mock
    ResourceResolver resolver;
    @Mock
    PageManager pagemanager;
    @Mock
    Page page;
    @Mock
    Page parentProductPage;
    @Mock
    ValueMap valueMap;

    @Before
    public void setUp() throws Exception {
        MemberModifier.field(SocialMediaModel.class, "resource").set(socialMediaModel, resource);
        Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
        Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pagemanager);
        Mockito.when(pagemanager.getContainingPage(resource)).thenReturn(page);
        Mockito.when(page.getAbsoluteParent(5)).thenReturn(parentProductPage);
        valueMap.put("addThisPubId", "addThisPubId");
        valueMap.put("addthisInlineId", "addthisInlineId");
        Mockito.when(parentProductPage.getProperties()).thenReturn(valueMap);
        Mockito.when(valueMap.get("addThisPubId", String.class)).thenReturn("addThisPubId");
        Mockito.when(valueMap.get("addthisInlineId", String.class)).thenReturn("addthisInlineId");
    }

    @Test
    public void testInit() throws Exception {
        socialMediaModel.init();
    }

    @Test
    public void testGetAddthisInlineId() throws Exception {
        socialMediaModel.setAddthisInlineId("dummy_string");
        socialMediaModel.getAddthisInlineId();
    }

    @Test
    public void testGetAddThisPubId() throws Exception {
        socialMediaModel.setAddThisPubId("addthisInlineId");
        socialMediaModel.getAddThisPubId();
    }

    @Test
    public void testObj() throws Exception {
        Assert.assertNotNull(socialMediaModel);
    }

}
