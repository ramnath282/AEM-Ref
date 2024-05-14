
package com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidateAddressResponseTypeTest {

    private ValidateAddressResponseType impl = null;
    private RowsResponseType rows = new RowsResponseType();

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ValidateAddressResponseType();
        impl.setError("dummy_string");
        impl.setRows(rows);
        impl.toString();
    }

    @Test
    public void testGetError()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getError());
    }

    @Test
    public void testGetRows()
        throws Exception
    {
        Assert.assertEquals(rows, impl.getRows());
    }

}
