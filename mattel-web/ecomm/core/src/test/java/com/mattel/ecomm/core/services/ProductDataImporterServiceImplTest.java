package com.mattel.ecomm.core.services;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.mattel.ecomm.core.importer.workflow.interfaces.ProductFeedInboxNotificationService;
import com.mattel.ecomm.coreservices.core.pojos.ProductDataImporterResponse;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductDataImporterServiceImplTest {
  @Mock
  private GetResourceResolver getResourceResolver;
  @Mock
  private ProductFeedInboxNotificationService productFeedInboxNotificationService;
  @InjectMocks
  private ProductDataImporterServiceImpl productDataImporterServiceImpl;

  @Before
  public void setUp() throws Exception {
    final ProductDataImporterServiceImpl.Config config = Mockito
        .mock(ProductDataImporterServiceImpl.Config.class);

    Mockito.when(config.clientIdToDamPath()).thenReturn("ag_en:/content/dam/ag/productfeed");
    Mockito.when(config.fileExtension()).thenReturn("json");
    Mockito.when(config.fileMimeType()).thenReturn("application/json");
    Mockito.when(config.filePrefix()).thenReturn("productdata");
    Mockito.when(config.productFeedEndPointUrl()).thenReturn("http://localhost:4502/");
    productDataImporterServiceImpl.activate(config);
  }

  @Test
  public void testReadInputData() throws Exception {
    try (InputStream in = getClass().getResourceAsStream("product_feed_test.json")) {
      final ResourceResolver resolver = Mockito.mock(ResourceResolver.class);
      final AssetManager assetManager = Mockito.mock(AssetManager.class);

      Mockito.when(assetManager.createAsset(ArgumentMatchers.anyString(),
          ArgumentMatchers.any(InputStream.class), ArgumentMatchers.anyString(),
          ArgumentMatchers.anyBoolean())).thenReturn(Mockito.mock(Asset.class));
      Mockito.when(resolver.adaptTo(AssetManager.class)).thenReturn(assetManager);
      Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resolver);
      final Map<String, Object> requestAttributes = new HashMap<>();
      requestAttributes.put("feedType", "full");
      final ProductDataImporterResponse productDataImporterResponse = productDataImporterServiceImpl
          .readInputData(in, requestAttributes);
      Assert.assertNotNull(productDataImporterResponse.getFilePath());
      Assert.assertTrue(
          productDataImporterResponse.getFilePath().startsWith("/content/dam/ag/productfeed"));
      Assert.assertTrue(productDataImporterResponse.getStatus());
    }
  }

  @Test
  public void testGetProductFeedEndPointURL() throws Exception {
    URL url = productDataImporterServiceImpl.getProductFeedEndPointURL();
    Assert.assertNotNull(url);
  }
}
