
package com.mattel.ecomm.coreservices.core.pojos.productdetailspojos;

import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddPackageToCartServiceRequestTest {

    private AddPackageToCartServiceRequest impl = null;
    private Map<String, String> parentpartnumberlist;
    private Map<String, String> childpartnumberlist;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new AddPackageToCartServiceRequest();
        impl.setOrderId("dummy_string");
        impl.setInventoryValidation("dummy_string");
        impl.setCalculateOrder("dummy_string");
        parentpartnumberlist = new java.util.HashMap<java.lang.String, java.lang.String>();
        impl.setParentPartnumberList(parentpartnumberlist);
        childpartnumberlist = new java.util.HashMap<java.lang.String, java.lang.String>();
        impl.setChildPartnumberList(childpartnumberlist);
        impl.setQty1("dummy_string");
        impl.setCalculationUsage("-1,-2,-5,-6,-7,-21");
        impl.toString();
    }

    @Test
    public void testGetParentPartnumberList()
        throws Exception
    {
        Assert.assertEquals(parentpartnumberlist, impl.getParentPartnumberList());
    }

    @Test
    public void testGetChildPartnumberList()
        throws Exception
    {
        Assert.assertEquals(childpartnumberlist, impl.getChildPartnumberList());
    }

    @Test
    public void testGetInventoryValidation()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getInventoryValidation());
    }

    @Test
    public void testGetQty1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getQty1());
    }

    @Test
    public void testGetOrderId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderId());
    }

    @Test
    public void testGetCalculateOrder()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCalculateOrder());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

    
    @Test
    public void testGetCalculationUsage()
        throws Exception
    {
        Assert.assertEquals("-1,-2,-5,-6,-7,-21", impl.getCalculationUsage());
    }
}
