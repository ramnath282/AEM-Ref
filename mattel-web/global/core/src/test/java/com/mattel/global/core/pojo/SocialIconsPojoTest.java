package com.mattel.global.core.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SocialIconsPojoTest {

    private SocialIconsPojo socialIconsPojo = null;

    @Before
    public void setUp()
    {
        socialIconsPojo = new SocialIconsPojo();
        socialIconsPojo.setIcons("dummy_string");
        socialIconsPojo.setLinkText("dummy_string");
        socialIconsPojo.setSocialLinkURL("dummy_string");
        socialIconsPojo.setLinkTarget("dummy_string");
        socialIconsPojo.setAlwaysEnglish("dummy_string");
        socialIconsPojo.toString();
    }

    @Test
    public void testLinkText()
    {
        Assert.assertEquals("dummy_string", socialIconsPojo.getLinkText());
    }

    @Test
    public void testIcons()
    {
        Assert.assertEquals("dummy_string", socialIconsPojo.getIcons());
    }

    @Test
    public void testSocialLinkURL()
    {
        Assert.assertEquals("dummy_string", socialIconsPojo.getSocialLinkURL());
    }

    @Test
    public void testLinkTarget()
    {
        Assert.assertEquals("dummy_string", socialIconsPojo.getLinkTarget());
    }

    @Test
    public void testAlwaysEnglish()
    {
        Assert.assertEquals("dummy_string", socialIconsPojo.getAlwaysEnglish());
    }

}
