package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComponentInventoryDataTest {

    private String parentPartNumber = "FRL93";
    private final List<InventoryData> skuArr = Arrays.asList();
    private final List<InventoryData> quickSellAssociation = Arrays.asList();
    private ComponentInventoryData componentInventoryData;

    @Before
    public void createComponentInventoryData() throws Exception {
        componentInventoryData = new ComponentInventoryData();
        componentInventoryData.setSkuArr(skuArr);
        componentInventoryData.setQuickSellAssociation(quickSellAssociation);
        componentInventoryData.setParentPartNumber(parentPartNumber);
        componentInventoryData.toString();
    }

    

    @Test
    public void testGetSkuArr() throws Exception {
        Assert.assertEquals(skuArr, componentInventoryData.getSkuArr());
    }

    @Test
    public void testGetQuickSellAssociation() throws Exception {
        Assert.assertEquals(quickSellAssociation, componentInventoryData.getQuickSellAssociation());
    }

    @Test
    public void testGetParentPartNumber() throws Exception {
        Assert.assertEquals(parentPartNumber, componentInventoryData.getParentPartNumber());
    }

}
