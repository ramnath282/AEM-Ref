package com.mattel.ag.retail.core.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ag.retail.core.utils.PropertyReaderUtils;

@RunWith(PowerMockRunner.class)
public class RetailPageModelTest {

    @InjectMocks
    private RetailPageModel retailPageModel;
    @Mock
    private PropertyReaderUtils propertyReaderUtils;

    private static final String TEALIUM_URL = "//tags.tiqcdn.com/utag/mattel/americangirl.aem/dev/utag.js";

    @Test
    public void test() {
        Mockito.when(propertyReaderUtils.getTealiumUrl()).thenReturn(TEALIUM_URL);
        retailPageModel.init();
        Assert.assertEquals(TEALIUM_URL, retailPageModel.getTealiumUrl());
    }

}
