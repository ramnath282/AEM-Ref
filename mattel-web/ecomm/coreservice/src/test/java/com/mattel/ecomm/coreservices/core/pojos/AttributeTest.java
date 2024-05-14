package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AttributeTest {
    private final String pProfileAttrKey = "testKey";

    private final String pProfileAttrValue = "testValue";
    private Attribute attribute;

    @Before
    public void createAttribute() throws Exception {
        attribute = new Attribute();
        attribute.setPProfileAttrKey(pProfileAttrKey);
        attribute.setPProfileAttrValue(pProfileAttrValue);
    }

    @Test
    public void testGetPProfileAttrKey() throws Exception {
        Assert.assertEquals("testKey", attribute.getPProfileAttrKey());
    }

    @Test
    public void testGetPProfileAttrValue() throws Exception {
        Assert.assertEquals("testValue", attribute.getPProfileAttrValue());
    }
}
