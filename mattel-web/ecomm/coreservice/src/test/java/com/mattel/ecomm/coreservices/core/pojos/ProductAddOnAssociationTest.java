
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductAddOnAssociationTest {

    private ProductAddOnAssociation impl = null;
    private Map<String, Map<String, String>> price;
    private List<ChildProduct> childproducts;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ProductAddOnAssociation();
        impl.setDescription("dummy_string");
        price = new java.util.HashMap<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>();
        impl.setPrice(price);
        childproducts = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.ChildProduct>();
        impl.setChildProducts(childproducts);
        impl.toString();
    }

    @Test
    public void testGetChildProducts()
        throws Exception
    {
        Assert.assertEquals(childproducts, impl.getChildProducts());
    }

    @Test
    public void testGetDescription()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDescription());
    }

    @Test
    public void testGetPrice()
        throws Exception
    {
        Assert.assertEquals(price, impl.getPrice());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
