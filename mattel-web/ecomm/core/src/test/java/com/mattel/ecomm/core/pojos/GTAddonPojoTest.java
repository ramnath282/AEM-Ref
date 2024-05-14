
package com.mattel.ecomm.core.pojos;

import java.util.List;
import com.mattel.ecomm.coreservices.core.pojos.ChildProduct;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GTAddonPojoTest {

    private GTAddonPojo impl = null;
    private List<ChildProduct> childproducts;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new GTAddonPojo();
        impl.setBuyable("dummy_string");
        impl.setProductType("dummy_string");
        impl.setAssociationType("dummy_string");
        impl.setName("dummy_string");
        impl.setOptionalName("dummy_string");
        impl.setPartNumber("dummy_string");
        impl.setThumbnail("dummy_string");
        impl.setFullimage("dummy_string");
        impl.setKitOption("dummy_string");
        impl.setKitDisplayable("dummy_string");
        impl.setKitDisplaySequence("dummy_string");
        impl.setKitInstructions("dummy_string");
        impl.setTrunkDescription("dummy_string");
        impl.setImageLink("dummy_string");
        childproducts = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.ChildProduct>();
        impl.setChildProducts(childproducts);
        impl.toString();
    }

    @Test
    public void testGetKitDisplaySequence()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getKitDisplaySequence());
    }

    @Test
    public void testGetName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getName());
    }

    @Test
    public void testGetBuyable()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getBuyable());
    }

    @Test
    public void testGetProduct_type()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getProductType());
    }

    @Test
    public void testGetChildProducts()
        throws Exception
    {
        Assert.assertEquals(childproducts, impl.getChildProducts());
    }

    @Test
    public void testGetOptionalName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOptionalName());
    }

    @Test
    public void testGetThumbnail()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getThumbnail());
    }

    @Test
    public void testGetImageLink()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getImageLink());
    }

    @Test
    public void testGetKitOption()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getKitOption());
    }

    @Test
    public void testGetTrunkDescription()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getTrunkDescription());
    }

    @Test
    public void testGetPartNumber()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPartNumber());
    }

    @Test
    public void testGetFullimage()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getFullimage());
    }

    @Test
    public void testGetKitInstructions()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getKitInstructions());
    }

    @Test
    public void testGetKitDisplayable()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getKitDisplayable());
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
