
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PDPCollectivePriceDetailsTest {

    private PDPCollectivePriceDetails impl = null;
    private List<PDPCollectiveBundlePriceList> bundlepricelist;
    private List<PDPCollectiveProductPriceList> productpricelist;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new PDPCollectivePriceDetails();
        bundlepricelist = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.PDPCollectiveBundlePriceList>();
        impl.setBundlePriceList(bundlepricelist);
        productpricelist = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.PDPCollectiveProductPriceList>();
        impl.setProductPriceList(productpricelist);
        impl.toString();
    }

    @Test
    public void testGetBundlePriceList()
        throws Exception
    {
        Assert.assertEquals(bundlepricelist, impl.getBundlePriceList());
    }

    @Test
    public void testGetProductPriceList()
        throws Exception
    {
        Assert.assertEquals(productpricelist, impl.getProductPriceList());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
