
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
public class CardComponentUnitTest {

    @InjectMocks
    private CardComponent cardComponent;
    @Mock
    Resource resource;

    @Before
    public void setUp() throws Exception {
        MemberModifier.field(CardComponent.class, "resource").set(cardComponent, resource);
        MemberModifier.field(CardComponent.class, "title").set(cardComponent, "title");
        MemberModifier.field(CardComponent.class, "image").set(cardComponent, "imagePath");
        MemberModifier.field(CardComponent.class, "ctaText").set(cardComponent, "text");
        MemberModifier.field(CardComponent.class, "ctaLink").set(cardComponent, "ctaLink");
        cardComponent.toString();
    }

    @Test
    public void testCardComponent() throws Exception {
        Assert.assertNotNull(cardComponent.getTitle());
        Assert.assertEquals("title", cardComponent.getTitle());
        Assert.assertNotNull(cardComponent.getImage());
        Assert.assertNotNull(cardComponent.getCtaText());
        Assert.assertNotNull(cardComponent.getCtaLink());
    }

    @Test
    public void testObj() throws Exception {
        Assert.assertNotNull(cardComponent);
    }

}
