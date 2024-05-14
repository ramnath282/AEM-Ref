
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PDPCollectiveProductPriceListTest {

    private PDPCollectiveProductPriceList impl = null;
    private List<UnitPrice> unitprice;
    private List<UnitPrice> listprice;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new PDPCollectiveProductPriceList();
        unitprice = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.UnitPrice>();
        impl.setUnitPrice(unitprice);
        impl.setPartNumber("dummy_string");
        listprice = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.UnitPrice>();
        impl.setListPrice(listprice);
        impl.toString();
    }

    @Test
    public void testGetPartNumber()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPartNumber());
    }

    @Test
    public void testGetListPrice()
        throws Exception
    {
        Assert.assertEquals(listprice, impl.getListPrice());
    }

    @Test
    public void testGetUnitPrice()
        throws Exception
    {
        Assert.assertEquals(unitprice, impl.getUnitPrice());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
