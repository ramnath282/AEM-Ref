
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DeleteChildRequestTest {

    private DeleteChildRequest impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new DeleteChildRequest();
        impl.setNickName("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetNickName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getNickName());
    }

}
