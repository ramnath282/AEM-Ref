
package com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidateAddressRequestTypeTest {

    private ValidateAddressRequestType impl = null;
    private OptionsType options = new OptionsType();
    private RowsRequestType rows = new RowsRequestType();

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ValidateAddressRequestType();
        impl.setOptions(options);
        impl.setRows(rows);
        impl.toString();
    }

    @Test
    public void testGetOptions()
        throws Exception
    {
        Assert.assertEquals(options, impl.getOptions());
    }

    @Test
    public void testGetRows()
        throws Exception
    {
        Assert.assertEquals(rows, impl.getRows());
    }

}
