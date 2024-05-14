package com.mattel.global.master.core.model;

import org.apache.sling.api.resource.Resource;
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

@RunWith(PowerMockRunner.class)
public class AccordionItemsModelTest {
    
    @InjectMocks
    AccordionItemsModel accordionItemsModel;
    
    @Mock
    Resource resource;
    
    @Mock
    ValueMap nodeProperties;
    
    @Before
    public void setup() throws Exception {
      MemberModifier.field(AccordionItemsModel.class, "resource").set(accordionItemsModel, resource);
      MemberModifier.field(AccordionItemsModel.class, "title").set(accordionItemsModel,"title");
      MemberModifier.field(AccordionItemsModel.class, "description").set(accordionItemsModel,"description");
      MemberModifier.field(AccordionItemsModel.class, "subTitle").set(accordionItemsModel,"subTitle");
      MemberModifier.field(AccordionItemsModel.class, "trackThisCta").set(accordionItemsModel,"trackThisCta");
      MemberModifier.field(AccordionItemsModel.class, "trackingText").set(accordionItemsModel,"trackingText");
      MemberModifier.field(AccordionItemsModel.class, "anchorName").set(accordionItemsModel,"anchorName");
      MemberModifier.field(AccordionItemsModel.class, "trackThisAnchor").set(accordionItemsModel,"trackThisAnchor");
      MemberModifier.field(AccordionItemsModel.class, "trackingTextAnchor").set(accordionItemsModel, "trackingTextAnchor");    
      
    }

    @Test
    public void testToVerifyAccordionItemsInit() throws IllegalArgumentException, IllegalAccessException {
        Mockito.when(resource.getParent()).thenReturn(resource);
        Mockito.when(resource.getValueMap()).thenReturn(nodeProperties);
        accordionItemsModel.init();
    }
    
    @Test
    public void testToVerifyAccordionItemsFields() throws IllegalArgumentException, IllegalAccessException {
        Assert.assertEquals("anchorName", accordionItemsModel.getAnchorName());
        Assert.assertEquals("description", accordionItemsModel.getDescription());
        Assert.assertEquals("subTitle", accordionItemsModel.getSubTitle());
        Assert.assertEquals("title", accordionItemsModel.getTitle());
        Assert.assertEquals("trackingText", accordionItemsModel.getTrackingText());
        Assert.assertEquals("trackThisAnchor", accordionItemsModel.getTrackThisAnchor());
        Assert.assertEquals("trackThisCta", accordionItemsModel.getTrackThisCta());
        Assert.assertEquals("trackingTextAnchor", accordionItemsModel.getTrackingTextAnchor());
    }

}
