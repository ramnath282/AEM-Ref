
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InventoryCheckResponseTest {

    private InventoryCheckResponse impl = null;
    private List<InventoryCheckChildResponse> response;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new InventoryCheckResponse();
        response = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.InventoryCheckChildResponse>();
        impl.setResponse(response);
        impl.toString();
    }

    @Test
    public void testGetResponse()
        throws Exception
    {
        Assert.assertEquals(response, impl.getResponse());
    }

}
