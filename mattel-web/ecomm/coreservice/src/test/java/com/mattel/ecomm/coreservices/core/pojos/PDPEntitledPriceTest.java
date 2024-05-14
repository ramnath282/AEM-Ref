
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PDPEntitledPriceTest {

    private PDPEntitledPrice impl = null;
    private List<Map<String, Object>> unitprice;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new PDPEntitledPrice();
        unitprice = new java.util.ArrayList<>();
        impl.setUnitPrice(unitprice);
        impl.setProductId("dummy_string");
        impl.setContractId("dummy_string");
        impl.setPartNumber("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetPartNumber()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPartNumber());
    }

    @Test
    public void testGetContractId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getContractId());
    }

    @Test
    public void testGetUnitPrice()
        throws Exception
    {
        Assert.assertEquals(unitprice, impl.getUnitPrice());
    }

    @Test
    public void testGetProductId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getProductId());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
