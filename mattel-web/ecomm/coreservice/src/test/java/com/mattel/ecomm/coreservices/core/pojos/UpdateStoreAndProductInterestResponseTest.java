
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UpdateStoreAndProductInterestResponseTest {

    private UpdateStoreAndProductInterestResponse impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new UpdateStoreAndProductInterestResponse();
        impl.setUpdateStatus(1);
        impl.toString();
    }

    @Test
    public void testGetUpdateStatus()
        throws Exception
    {
        Assert.assertEquals(1, impl.getUpdateStatus());
    }

}
