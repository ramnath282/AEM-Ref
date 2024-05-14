
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductAttributesTest {

    private ProductAttributes impl = null;
    private Map<String, Object> descripitiveattributes;
    private Map<String, Object> definingattributes;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ProductAttributes();
        descripitiveattributes = new java.util.HashMap<java.lang.String, java.lang.Object>();
        impl.setDescripitiveAttributes(descripitiveattributes);
        definingattributes = new java.util.HashMap<java.lang.String, java.lang.Object>();
        impl.setDefiningAttributes(definingattributes);
        impl.toString();
    }

    @Test
    public void testGetDescripitiveAttributes()
        throws Exception
    {
        Assert.assertEquals(descripitiveattributes, impl.getDescripitiveAttributes());
    }

    @Test
    public void testGetDefiningAttributes()
        throws Exception
    {
        Assert.assertEquals(definingattributes, impl.getDefiningAttributes());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
