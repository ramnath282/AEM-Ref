package com.mattel.ecomm.pdp.core.servicesimpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ HttpRequestHandler.class })
public class ProductAvailabilityImplTest2 {
  private final String endPointUrl = "http://localhost:PORT_NUMBER/a/product/PART_NUMBER.json";

  @Mock
  private PropertyReaderService propertyReaderService;
  @InjectMocks
  private ProductAvailabilityImpl productAvailabilityImpl;

  @Test(expected=ServiceException.class)
  public void testFetchInternalError() throws Exception {
    final ProductAvailabilityImpl.Config config = Mockito.mock(ProductAvailabilityImpl.Config.class);
    final Map<String, Object> requestMap = new HashMap<>();

    requestMap.put(Constant.STORE_KEY, "AG");
    requestMap.put(Constant.DOMAIN_KEY, "AG");
    requestMap.put(Constant.PART_NUMBER, "fgd39");
    Mockito.when(config.endPoint()).thenReturn(endPointUrl);
    PowerMockito.mockStatic(HttpRequestHandler.class);
    PowerMockito
        .when(HttpRequestHandler.get(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any(),
            ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
        .thenThrow(new IOException("Internal error"));
    productAvailabilityImpl.activate(config);
    productAvailabilityImpl.fetch(requestMap);
  }
}
