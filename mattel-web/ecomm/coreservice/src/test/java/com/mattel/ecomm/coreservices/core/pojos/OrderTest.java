
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderTest {

    private Order impl = null;
    private Channel channel = new Channel();
    private List<Adjustment> adjustment;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new Order();
        impl.setOrderTypeCode("dummy_string");
        impl.setTotalShippingCharge("dummy_string");
        impl.setResourceId("dummy_string");
        impl.setOrderId("dummy_string");
        impl.setLastUpdateDate("dummy_string");
        impl.setChannel(channel);
        impl.setOrderStatus("dummy_string");
        impl.setIsPurchaseOrderNumberRequired("dummy_string");
        impl.setTotalShippingChargeCurrency("dummy_string");
        impl.setBuyerId("dummy_string");
        impl.setGrandTotalCurrency("dummy_string");
        impl.setBuyerDistinguishedName("dummy_string");
        impl.setIsPersonalAddressesAllowedForShipping("dummy_string");
        impl.setStoreNameIdentifier("dummy_string");
        impl.setTotalProductPriceCurrency("dummy_string");
        impl.setTotalProductPrice("dummy_string");
        impl.setLocked("dummy_string");
        impl.setField1("dummy_string");
        impl.setPlacedDate("dummy_string");
        impl.setTotalAdjustmentCurrency("dummy_string");
        impl.setTotalSalesTaxCurrency("dummy_string");
        impl.setTotalSalesTax("dummy_string");
        impl.setGrandTotal("dummy_string");
        impl.setStoreUniqueID("dummy_string");
        impl.setShipAsComplete("dummy_string");
        impl.setTrackingIds("dummy_string");
        impl.setTotalShippingTax("dummy_string");
        impl.setPrepareIndicator("dummy_string");
        impl.setTotalShippingTaxCurrency("dummy_string");
        adjustment = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.Adjustment>();
        impl.setAdjustment(adjustment);
        impl.setTotalAdjustment("dummy_string");
        impl.setStoreFrontId("dummy_string");
        impl.setOrderNoHash("dummy_string");
        impl.setOriginalSystem("dummy_string");
        impl.setOrderType("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetOrderNoHash()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderNoHash());
    }

    @Test
    public void testGetTotalProductPriceCurrency()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getTotalProductPriceCurrency());
    }

    @Test
    public void testGetX_field1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getField1());
    }

    @Test
    public void testGetOrderId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderId());
    }

    @Test
    public void testGetTotalSalesTax()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getTotalSalesTax());
    }

    @Test
    public void testGetTotalShippingTaxCurrency()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getTotalShippingTaxCurrency());
    }

    @Test
    public void testGetTotalShippingTax()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getTotalShippingTax());
    }

    @Test
    public void testGetTotalProductPrice()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getTotalProductPrice());
    }

    @Test
    public void testGetTotalShippingChargeCurrency()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getTotalShippingChargeCurrency());
    }

    @Test
    public void testGetStoreUniqueID()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getStoreUniqueID());
    }

    @Test
    public void testGetChannel()
        throws Exception
    {
        Assert.assertEquals(channel, impl.getChannel());
    }

    @Test
    public void testGetOrderStatus()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderStatus());
    }

    @Test
    public void testGetBuyerId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getBuyerId());
    }

    @Test
    public void testGetPlacedDate()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPlacedDate());
    }

    @Test
    public void testGetTotalAdjustment()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getTotalAdjustment());
    }

    @Test
    public void testGetGrandTotalCurrency()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getGrandTotalCurrency());
    }

    @Test
    public void testGetLastUpdateDate()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLastUpdateDate());
    }

    @Test
    public void testGetOriginalSystem()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOriginalSystem());
    }

    @Test
    public void testGetTotalShippingCharge()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getTotalShippingCharge());
    }

    @Test
    public void testGetOrderType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderType());
    }

    @Test
    public void testGetOrderTypeCode()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderTypeCode());
    }

    @Test
    public void testGetPrepareIndicator()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPrepareIndicator());
    }

    @Test
    public void testGetTotalAdjustmentCurrency()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getTotalAdjustmentCurrency());
    }

    @Test
    public void testGetStoreFrontId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getStoreFrontId());
    }

    @Test
    public void testGetStoreNameIdentifier()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getStoreNameIdentifier());
    }

    @Test
    public void testGetLocked()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLocked());
    }

    @Test
    public void testGetGrandTotal()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getGrandTotal());
    }

    @Test
    public void testGetX_trackingIds()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getTrackingIds());
    }

    @Test
    public void testGetBuyerDistinguishedName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getBuyerDistinguishedName());
    }

    @Test
    public void testGetResourceId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getResourceId());
    }

    @Test
    public void testGetX_isPersonalAddressesAllowedForShipping()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getIsPersonalAddressesAllowedForShipping());
    }

    @Test
    public void testGetX_isPurchaseOrderNumberRequired()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getIsPurchaseOrderNumberRequired());
    }

    @Test
    public void testGetAdjustment()
        throws Exception
    {
        Assert.assertEquals(adjustment, impl.getAdjustment());
    }

    @Test
    public void testGetTotalSalesTaxCurrency()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getTotalSalesTaxCurrency());
    }

    @Test
    public void testGetShipAsComplete()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getShipAsComplete());
    }

}
