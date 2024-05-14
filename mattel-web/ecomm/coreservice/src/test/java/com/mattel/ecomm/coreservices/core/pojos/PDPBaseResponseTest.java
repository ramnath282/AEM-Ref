
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PDPBaseResponseTest {

    private PDPBaseResponse impl = null;
    private Map<String, String> metadata;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new PDPBaseResponse();
        impl.setRecordSetCount("dummy_string");
        metadata = new java.util.HashMap<java.lang.String, java.lang.String>();
        impl.setMetaData(metadata);
        impl.setResourceId("dummy_string");
        impl.setRecordSetStartNumber("dummy_string");
        impl.setResourceName("dummy_string");
        impl.setRecordSetTotal("dummy_string");
        impl.setRecordSetTotalMatches("dummy_string");
        impl.setRecordSetComplete("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetRecordSetCount()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getRecordSetCount());
    }

    @Test
    public void testGetRecordSetTotalMatches()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getRecordSetTotalMatches());
    }

    @Test
    public void testGetResourceId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getResourceId());
    }

    @Test
    public void testGetResourceName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getResourceName());
    }

    @Test
    public void testGetMetaData()
        throws Exception
    {
        Assert.assertEquals(metadata, impl.getMetaData());
    }

    @Test
    public void testGetRecordSetStartNumber()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getRecordSetStartNumber());
    }

    @Test
    public void testGetRecordSetComplete()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getRecordSetComplete());
    }

    @Test
    public void testGetRecordSetTotal()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getRecordSetTotal());
    }

}
