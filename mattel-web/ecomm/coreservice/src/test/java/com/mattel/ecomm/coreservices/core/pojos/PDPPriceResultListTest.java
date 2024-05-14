
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PDPPriceResultListTest {

    private PDPPriceResultList impl = null;
    private  List<Map<String, Object>> unitprice;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new PDPPriceResultList();
        unitprice = new java.util.ArrayList<>();
        impl.setUnitPrice(unitprice);
        impl.setCatalogEntryId("dummy_string");
        impl.setPartNumber("dummy_string");
        impl.setPriceRuleName("dummy_string");
        impl.setPriceRuleId("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetPriceRuleName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPriceRuleName());
    }

    @Test
    public void testGetPartNumber()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPartNumber());
    }

    @Test
    public void testGetUnitPrice()
        throws Exception
    {
        Assert.assertEquals(unitprice, impl.getUnitPrice());
    }

    @Test
    public void testGetPriceRuleId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPriceRuleId());
    }

    @Test
    public void testGetCatalogEntryId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCatalogEntryId());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
