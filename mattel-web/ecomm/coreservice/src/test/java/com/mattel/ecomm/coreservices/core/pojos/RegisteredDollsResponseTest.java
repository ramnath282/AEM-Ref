
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RegisteredDollsResponseTest {

    private RegisteredDollsResponse impl = null;
    private List<Doll> dolls;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new RegisteredDollsResponse();
        dolls = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.Doll>();
        impl.setDolls(dolls);
        impl.toString();
    }

    @Test
    public void testGetDolls()
        throws Exception
    {
        Assert.assertEquals(dolls, impl.getDolls());
    }

}
