package com.mattel.ecomm.core.models;

import org.apache.sling.api.resource.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class LinkComponentTest {

    @InjectMocks
    LinkComponent linkComponent;
    @Mock
    Resource resource;

    @Before
    public void setUp() throws IllegalAccessException {
        MemberModifier.field(LinkComponent.class, "resource").set(linkComponent, resource);
        MemberModifier.field(LinkComponent.class, "text").set(linkComponent, "title");
        MemberModifier.field(LinkComponent.class, "linkType").set(linkComponent, true);
        MemberModifier.field(LinkComponent.class, "link").set(linkComponent, "linkPath");
        MemberModifier.field(LinkComponent.class, "ctaType").set(linkComponent, "ctaType");
        MemberModifier.field(LinkComponent.class, "ctaStyle").set(linkComponent, "ctaStyle");

    }

    @Test
    public void testLinkComponentModel() {
        Assert.assertNotNull(linkComponent.getCtaStyle());
        Assert.assertNotNull(linkComponent.getText());
        Assert.assertEquals("title", linkComponent.getText());
        Assert.assertNotNull(linkComponent.getLink());
        Assert.assertTrue(linkComponent.isLinkType());
        Assert.assertNotNull(linkComponent.getCtaType());
        Assert.assertNotNull(linkComponent.getCtaStyle());
        Assert.assertEquals("ctaStyle", linkComponent.getCtaStyle());

    }

}
