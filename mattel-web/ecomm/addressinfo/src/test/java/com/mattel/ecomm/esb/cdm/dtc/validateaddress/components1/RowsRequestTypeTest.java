
package com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RowsRequestTypeTest {

    private RowsRequestType impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new RowsRequestType();
        impl.toString();
    }

    @Test
    public void testGetRow() {
      Assert.assertNotNull(impl.getRow());
  }
}
