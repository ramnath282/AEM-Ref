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
public class PromoModelTest {

    @InjectMocks
    PromoModel promoModel;
    @Mock
    private Resource resource;

    @Before
    public void setup() throws Exception {
        MemberModifier.field(PromoModel.class, "resource").set(promoModel, resource);
        MemberModifier.field(PromoModel.class, "promotinalText").set(promoModel, "<p>promotionalText</p>");
        MemberModifier.field(PromoModel.class, "link").set(promoModel, "/content/dam/en/doll.pdf");
        MemberModifier.field(PromoModel.class, "ctaStyle").set(promoModel, "horizontal");

    }

    @Test
    public void testGetpromotionalText() {
        String promotionalText = promoModel.getPromotinalText();
        Assert.assertNotNull(promotionalText);
        Assert.assertEquals(promotionalText, "promotionalText");
    }

    @Test
    public void testGetCtaStyle() {
        Assert.assertNotNull(promoModel.getCtaStyle());
    }

    @Test
    public void testGetLink() {
        Assert.assertNotNull(promoModel.getLink());
    }

}
