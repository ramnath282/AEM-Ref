
package com.mattel.ecomm.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GiftCardMessageTest {

    private GiftCardMessage impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new GiftCardMessage();
        impl.setMessage1("dummy_string");
        impl.setMessage2("dummy_string");
        impl.setMessage3("dummy_string");
        impl.setSequence("dummy_string");
        impl.setMessageTitle("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetMessage3()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getMessage3());
    }

    @Test
    public void testGetSequence()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSequence());
    }

    @Test
    public void testGetMessageTitle()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getMessageTitle());
    }

    @Test
    public void testGetMessage2()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getMessage2());
    }

    @Test
    public void testGetMessage1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getMessage1());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
