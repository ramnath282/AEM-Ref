package com.mattel.global.master.core.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class AccordionContainerModelTest {

    @InjectMocks
    AccordionContainerModel accordionContainerModel;
    
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
      MemberModifier.field(AccordionContainerModel.class, "autoClose").set(accordionContainerModel, "autoClose");
      MemberModifier.field(AccordionContainerModel.class, "title").set(accordionContainerModel, "title");
      MemberModifier.field(AccordionContainerModel.class, "description").set(accordionContainerModel,"description");
      MemberModifier.field(AccordionContainerModel.class, "subTitle").set(accordionContainerModel, "subTitle");
      MemberModifier.field(AccordionContainerModel.class, "ctaOpenText").set(accordionContainerModel, "ctaOpenText");
      MemberModifier.field(AccordionContainerModel.class, "ctaCloseText").set(accordionContainerModel, "ctaCloseText");
    }
    @Test
    public void testForVerifyingAccordionModelFields() {
        assertEquals("autoClose", accordionContainerModel.getAutoClose());
        assertEquals("description", accordionContainerModel.getDescription());
        assertEquals("subTitle", accordionContainerModel.getSubTitle());
        assertEquals("title", accordionContainerModel.getTitle());
        assertEquals("ctaOpenText", accordionContainerModel.getCtaOpenText());
        assertEquals("ctaCloseText", accordionContainerModel.getCtaCloseText());
    }

}
