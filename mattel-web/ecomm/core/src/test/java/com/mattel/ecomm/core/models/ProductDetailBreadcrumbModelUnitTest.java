
package com.mattel.ecomm.core.models;

import java.util.List;
import com.mattel.ecomm.core.pojos.BreadcrumbDetails;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductDetailBreadcrumbModelUnitTest {

    private ProductDetailBreadcrumbModel impl = null;
    private List<BreadcrumbDetails> breadcrumblinks;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ProductDetailBreadcrumbModel();
        breadcrumblinks = new java.util.ArrayList<com.mattel.ecomm.core.pojos.BreadcrumbDetails>();
        impl.setBreadcrumbLinks(breadcrumblinks);
        impl.toString();
    }

    @Test
    public void testGetBreadcrumbLinks()
        throws Exception
    {
        Assert.assertEquals(breadcrumblinks, impl.getBreadcrumbLinks());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
