
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EmailTNSRequestTest {

    private EmailTNSRequest impl = null;
    private GTEmailTNSNoticeEvent createnoticeeventreqinput = new GTEmailTNSNoticeEvent();

    @Before
    public void setUp()
        throws Exception
    {
        impl = new EmailTNSRequest();
        impl.setCreateNoticeEventReqInput(createnoticeeventreqinput);
        impl.toString();
    }

    @Test
    public void testGetCreateNoticeEventReqInput()
        throws Exception
    {
        Assert.assertEquals(createnoticeeventreqinput, impl.getCreateNoticeEventReqInput());
    }

}
