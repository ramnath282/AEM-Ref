
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InventoryCheckRequestTest {

    private InventoryCheckRequest impl = null;
    private List<String> partnumbers;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new InventoryCheckRequest();
        partnumbers = new java.util.ArrayList<java.lang.String>();
        impl.setPartNumbers(partnumbers);
        impl.toString();
    }

    @Test
    public void testGetPartNumbers()
        throws Exception
    {
        Assert.assertEquals(partnumbers, impl.getPartNumbers());
    }

}
