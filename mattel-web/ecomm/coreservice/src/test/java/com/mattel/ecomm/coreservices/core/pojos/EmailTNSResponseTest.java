
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EmailTNSResponseTest {

    private EmailTNSResponse impl = null;
    private GTEmailTNSResponseEventOutput createnoticeeventreqoutput = new GTEmailTNSResponseEventOutput();

    @Before
    public void setUp()
        throws Exception
    {
        impl = new EmailTNSResponse();
        impl.setCreateNoticeEventReqOutput(createnoticeeventreqoutput);
        impl.toString();
    }

    @Test
    public void testGetCreateNoticeEventReqOutput()
        throws Exception
    {
        Assert.assertEquals(createnoticeeventreqoutput, impl.getCreateNoticeEventReqOutput());
    }

}
