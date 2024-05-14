
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GiftMessageTemplateTest {

    private GiftMessageTemplate impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new GiftMessageTemplate();
        impl.setGiftMsgTo("dummy_string");
        impl.setGiftMsgFrom("dummy_string");
        impl.setGiftMsg1("dummy_string");
        impl.setGiftMsg2("dummy_string");
        impl.setGiftMsg3("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetGiftMsg2()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getGiftMsg2());
    }

    @Test
    public void testGetGiftMsg1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getGiftMsg1());
    }

    @Test
    public void testGetGiftMsgTo()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getGiftMsgTo());
    }

    @Test
    public void testGetGiftMsgFrom()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getGiftMsgFrom());
    }

    @Test
    public void testGetGiftMsg3()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getGiftMsg3());
    }

}
