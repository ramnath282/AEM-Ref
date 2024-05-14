
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ContactAttributeTest {

    private ContactAttribute impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ContactAttribute();
        impl.setKey("dummy_string");
        impl.setValue("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetKey()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getKey());
    }

    @Test
    public void testGetValue()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getValue());
    }

}
