
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductAttributesResponseTest {

    private ProductAttributesResponse impl = null;
    private List<Map<String, Map<String, List<String>>>> catalogentryview;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ProductAttributesResponse();
        catalogentryview = new java.util.ArrayList<>();
        impl.setCatalogEntryView(catalogentryview);
        impl.toString();
    }

    @Test
    public void testGetCatalogEntryView()
        throws Exception
    {
        Assert.assertEquals(catalogentryview, impl.getCatalogEntryView());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
