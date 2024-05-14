
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductTest {

    private Product impl = null;
    private ProductAttributes attributes = new ProductAttributes();
    private List<ProductAssociation> associations;
    private List<PDPProductComponent> components;
    private List<ChildProduct> childproducts;
    private List<Map<String, String>> giftcardmessages;
    private Map<String, Price> price;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new Product();
        impl.setBuyable("dummy_string");
        impl.setSeoImageAltText("dummy_string");
        impl.setThumbnail("dummy_string");
        impl.setCode("dummy_string");
        impl.setInvStatus("dummy_string");
        impl.setAuxDescription1("dummy_string");
        impl.setBackorderDate("dummy_string");
        impl.setProductStatus("dummy_string");
        impl.setDescription("dummy_string");
        impl.setLanguage("dummy_string");
        impl.setAuxDescription2("dummy_string");
        impl.setAvailability("dummy_string");
        impl.setCustSegExcl("dummy_string");
        impl.setSeoMetaDescription("dummy_string");
        impl.setImageLink("dummy_string");
        impl.setProductType("dummy_string");
        impl.setFullimage("dummy_string");
        impl.setPartNumber("dummy_string");
        impl.setSeoPageTitle("dummy_string");
        impl.setReasonCode("dummy_string");
        impl.setRegion("dummy_string");
        impl.setCanonicalCat("dummy_string");
        impl.setPublished("dummy_string");
        impl.setSeoUrlKeyword("dummy_string");
        impl.setAttributes(attributes);
        associations = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.ProductAssociation>();
        impl.setAssociations(associations);
        components = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.PDPProductComponent>();
        impl.setComponents(components);
        childproducts = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.ChildProduct>();
        impl.setChildProducts(childproducts);
        giftcardmessages = new java.util.ArrayList<>();
        impl.setGiftCardMessages(giftcardmessages);
        price = new java.util.HashMap<java.lang.String, com.mattel.ecomm.coreservices.core.pojos.Price>();
        impl.setPrice(price);
        impl.setAffirmIneligible("dummy_string");
        impl.setNewOverrideFlag("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetBackorderDate()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getBackorderDate());
    }

    @Test
    public void testGetProductStatus()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getProductStatus());
    }

    @Test
    public void testGetSeoUrlKeyword()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSeoUrlKeyword());
    }

    @Test
    public void testGetBuyable()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getBuyable());
    }

    @Test
    public void testGetCanonicalCat()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCanonicalCat());
    }

    @Test
    public void testGetPublished()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPublished());
    }

    @Test
    public void testGetReasonCode()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getReasonCode());
    }

    @Test
    public void testGetThumbnail()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getThumbnail());
    }

    @Test
    public void testGetDescription()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDescription());
    }

    @Test
    public void testGetAssociations()
        throws Exception
    {
        Assert.assertEquals(associations, impl.getAssociations());
    }

    @Test
    public void testGetAffirmIneligible()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAffirmIneligible());
    }

    @Test
    public void testGetAvailability()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAvailability());
    }

    @Test
    public void testGetComponents()
        throws Exception
    {
        Assert.assertEquals(components, impl.getComponents());
    }

    @Test
    public void testGetCode()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCode());
    }

    @Test
    public void testGetInvStatus()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getInvStatus());
    }

    @Test
    public void testGetCustSegExcl()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCustSegExcl());
    }

    @Test
    public void testGetLanguage()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLanguage());
    }

    @Test
    public void testGetAttributes()
        throws Exception
    {
        Assert.assertEquals(attributes, impl.getAttributes());
    }

    @Test
    public void testGetSeoMetaDescription()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSeoMetaDescription());
    }

    @Test
    public void testGetNewOverrideFlag()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getNewOverrideFlag());
    }

    @Test
    public void testGetSeoImageAltText()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSeoImageAltText());
    }

    @Test
    public void testGetChildProducts()
        throws Exception
    {
        Assert.assertEquals(childproducts, impl.getChildProducts());
    }

    @Test
    public void testGetGiftCardMessages()
        throws Exception
    {
        Assert.assertEquals(giftcardmessages, impl.getGiftCardMessages());
    }

    @Test
    public void testGetImageLink()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getImageLink());
    }

    @Test
    public void testGetPrice()
        throws Exception
    {
        Assert.assertEquals(price, impl.getPrice());
    }

    @Test
    public void testGetAuxDescription1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAuxDescription1());
    }

    @Test
    public void testGetSeoPageTitle()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSeoPageTitle());
    }

    @Test
    public void testGetFullimage()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getFullimage());
    }

    @Test
    public void testGetPartNumber()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPartNumber());
    }

    @Test
    public void testGetRegion()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getRegion());
    }

    @Test
    public void testGetAuxDescription2()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAuxDescription2());
    }

    @Test
    public void testGetProductType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getProductType());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
