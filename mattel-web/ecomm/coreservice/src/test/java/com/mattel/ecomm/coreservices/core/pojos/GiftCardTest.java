
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GiftCardTest {

    private GiftCard impl = null;
    private GiftMessageTemplate messagejson = new GiftMessageTemplate();

    @Before
    public void setUp()
        throws Exception
    {
        impl = new GiftCard();
        impl.setGiftPartNumber("dummy_string");
        impl.setQuantity1("dummy_string");
        impl.setPrice("dummy_string");
        impl.setMessageJson(messagejson);
        impl.setGiftMsgText("dummy_string");
        impl.setGiftMsgTextFrom("dummy_string");
        impl.setGiftMsgTextTo("dummy_string");
        impl.setGiftMsgText1("dummy_string");
        impl.setGiftMsgText2("dummy_string");
        impl.setGiftMsgText3("dummy_string");
        impl.setValidateType("dummy_string");
        impl.setRequesttype("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetGiftPartNumber()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getGiftPartNumber());
    }

    @Test
    public void testGetQuantity1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getQuantity1());
    }

    @Test
    public void testGetGiftMsgText1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getGiftMsgText1());
    }

    @Test
    public void testGetGiftMsgText2()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getGiftMsgText2());
    }

    @Test
    public void testGetRequesttype()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getRequesttype());
    }

    @Test
    public void testGetGiftMsgText3()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getGiftMsgText3());
    }

    @Test
    public void testGetValidateType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getValidateType());
    }

    @Test
    public void testGetGiftMsgTextFrom()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getGiftMsgTextFrom());
    }

    @Test
    public void testGetGiftMsgTextTo()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getGiftMsgTextTo());
    }

    @Test
    public void testGetPrice()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPrice());
    }

    @Test
    public void testGetMessageJson()
        throws Exception
    {
        Assert.assertEquals(messagejson, impl.getMessageJson());
    }

    @Test
    public void testGetGiftMsgText()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getGiftMsgText());
    }

}
