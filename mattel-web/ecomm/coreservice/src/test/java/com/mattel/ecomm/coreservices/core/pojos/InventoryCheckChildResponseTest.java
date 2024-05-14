package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InventoryCheckChildResponseTest {
    private String partNumber = "FRL93";
    private final List<ComponentInventoryData> componentInventory = Arrays.asList();
    private final List<InventoryData> inventory = Arrays.asList();
    private final List<InventoryData> headerInventoryDetail = Arrays.asList();
    private final List<InventoryData> quickSellAssociation = Arrays.asList();
    InventoryCheckChildResponse inventoryCheckChildResponse;

    @Before
    public void createInventoryCheckChildResponse() throws Exception {
        inventoryCheckChildResponse = new InventoryCheckChildResponse();
        inventoryCheckChildResponse.setPartNumber(partNumber);
        inventoryCheckChildResponse.setComponentInventory(componentInventory);
        inventoryCheckChildResponse.setInventory(inventory);
        inventoryCheckChildResponse.setHeaderInventoryDetail(headerInventoryDetail);
        inventoryCheckChildResponse.setQuickSellAssociation(quickSellAssociation);
        inventoryCheckChildResponse.toString();
    }

    @Test
    public void testGetPartNumber() throws Exception {
        Assert.assertEquals(partNumber, inventoryCheckChildResponse.getPartNumber());
    }

    @Test
    public void testGetComponentInventory() throws Exception {
        Assert.assertEquals(componentInventory, inventoryCheckChildResponse.getComponentInventory());
    }

    @Test
    public void testGetInventory() throws Exception {
        Assert.assertEquals(inventory, inventoryCheckChildResponse.getInventory());
    }

    @Test
    public void testGetHeaderInventoryDetail() throws Exception {
        Assert.assertEquals(headerInventoryDetail, inventoryCheckChildResponse.getHeaderInventoryDetail());
    }

    @Test
    public void testGetQuickSellAssociation() throws Exception {
        Assert.assertEquals(quickSellAssociation, inventoryCheckChildResponse.getQuickSellAssociation());
    }
}
