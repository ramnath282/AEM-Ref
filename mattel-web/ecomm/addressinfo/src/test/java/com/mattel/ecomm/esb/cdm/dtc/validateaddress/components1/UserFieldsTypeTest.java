
package com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserFieldsTypeTest {

    private UserFieldsType impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new UserFieldsType();
        impl.toString();
    }
    
    @Test
    public void testGetUserField()
    {
      Assert.assertNotNull(impl.getUserField());
    }

}
