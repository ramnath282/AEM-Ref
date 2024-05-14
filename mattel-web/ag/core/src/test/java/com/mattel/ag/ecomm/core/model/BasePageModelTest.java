package com.mattel.ag.ecomm.core.model;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class BasePageModelTest {

    @InjectMocks
    private BasePageModel basePageModel;
    @Mock
    private SlingHttpServletRequest request;
    @Mock
    private RequestPathInfo pathInfo;

    @Test
    public void testBasePageModel() throws Exception {
        String[] selectors = { "ag_en", "nocloudconfigs" };
        MemberModifier.field(BasePageModel.class, "request").set(basePageModel, request);
        Mockito.when(request.getRequestPathInfo()).thenReturn(pathInfo);
        Mockito.when(pathInfo.getSelectors()).thenReturn(selectors);
        Assert.assertTrue(basePageModel.checkClientLibsSelector(request));
        Assert.assertTrue(basePageModel.isDisableClientLibs());
        Assert.assertFalse(basePageModel.isMobileRequest());
        Assert.assertNotNull(basePageModel.imageId());
    }

}
