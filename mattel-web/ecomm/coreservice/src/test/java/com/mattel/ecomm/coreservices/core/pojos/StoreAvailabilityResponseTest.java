
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StoreAvailabilityResponseTest {

    private StoreAvailabilityResponse impl = null;
    private List<AvailableItem> itemavailabilitydetailslist;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new StoreAvailabilityResponse();
        itemavailabilitydetailslist = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.AvailableItem>();
        impl.setItemAvailabilityDetailsList(itemavailabilitydetailslist);
        impl.toString();
    }

    @Test
    public void testGetItemAvailabilityDetailsList()
        throws Exception
    {
        Assert.assertEquals(itemavailabilitydetailslist, impl.getItemAvailabilityDetailsList());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
