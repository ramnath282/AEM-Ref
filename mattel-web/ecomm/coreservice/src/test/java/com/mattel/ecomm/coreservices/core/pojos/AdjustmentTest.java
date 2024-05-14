package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AdjustmentTest {
    private String amount = "10000";

    private String code = "CODE";

    private String currency = "$";

    private String displayLevel = "INFO";

    private String usage = "High";
    private Adjustment adjustment;

    @Before
    public void createAdjustment() throws Exception {
        adjustment = new Adjustment();
        adjustment.setAmount(amount);
        adjustment.setCode(code);
        adjustment.setCurrency(currency);
        adjustment.setDisplayLevel(displayLevel);
        adjustment.setUsage(usage);
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetAmount() throws Exception {
        Assert.assertEquals(amount, adjustment.getAmount());
    }

    @Test
    public void testGetCode() throws Exception {
        Assert.assertEquals(code, adjustment.getCode());
    }

    @Test
    public void testGetDisplayLevel() throws Exception {
        Assert.assertEquals(displayLevel, adjustment.getDisplayLevel());
    }

    @Test
    public void testGetUsage() throws Exception {
        Assert.assertEquals(usage, adjustment.getUsage());
    }

    @Test
    public void testGetCurrency() throws Exception {
        Assert.assertEquals(currency, adjustment.getCurrency());
    }
}
