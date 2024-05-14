
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductAssociationTest {

    private ProductAssociation impl = null;
    private Map<String, Object> attributes;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ProductAssociation();
        impl.setAssociationType("dummy_string");
        impl.setName("dummy_string");
        attributes = new java.util.HashMap<java.lang.String, java.lang.Object>();
        impl.setAttributes(attributes);
        impl.toString();
    }

    @Test
    public void testGetName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getName());
    }

    @Test
    public void testGetAttributes()
        throws Exception
    {
        Assert.assertEquals(attributes, impl.getAttributes());
    }

    @Test
    public void testGetAssociationType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAssociationType());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
