
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UpdateContactPreferencesResponseTest {

    private UpdateContactPreferencesResponse impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new UpdateContactPreferencesResponse();
        impl.setResponse("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetResponse()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getResponse());
    }

}
