
package com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RowsResponseTypeTest {

    private RowsResponseType impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new RowsResponseType();
        impl.toString();
    }
    @Test
    public void testGetRow() {
      Assert.assertNotNull(impl.getRow());
  }
}
