package com.mattel.ecomm.coreservices.core.utilities;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;

public class ResponseFormatGetterTest {
    @Test
    public void testGetErrorJson() throws Exception {
        final ServiceException serviceException = new ServiceException(Constant.IO_ERROR_KEY, "IO Exception");
        final JSONObject jsonObject = ResponseFormatGetter.getErrorJson(serviceException);

        Assert.assertEquals(Constant.IO_ERROR_KEY, jsonObject.getString("errorKey"));
        Assert.assertEquals(serviceException.getErrorMessage(), jsonObject.getString("errorMessage"));
        Assert.assertEquals(Constant.IO_ERROR_KEY, jsonObject.getString("errorCode"));
    }
}
