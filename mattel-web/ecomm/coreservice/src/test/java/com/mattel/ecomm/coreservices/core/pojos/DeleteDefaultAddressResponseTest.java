
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DeleteDefaultAddressResponseTest {

    private DeleteDefaultAddressResponse impl = null;
    private List<String> addressid;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new DeleteDefaultAddressResponse();
        addressid = new java.util.ArrayList<java.lang.String>();
        impl.setAddressId(addressid);
        impl.toString();
    }

    @Test
    public void testGetAddressId()
        throws Exception
    {
        Assert.assertEquals(addressid, impl.getAddressId());
    }

}
