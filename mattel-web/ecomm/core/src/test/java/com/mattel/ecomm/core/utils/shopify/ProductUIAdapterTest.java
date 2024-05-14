package com.mattel.ecomm.core.utils.shopify;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.ecomm.core.pojos.shopify.ProductUIResponse;
import com.mattel.ecomm.core.utils.shopify.ProductUIAdapter;
import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Product;


public class ProductUIAdapterTest {
  @Test
    public void testTransformProductItemBean() throws Exception {
      try (InputStream is = getClass().getResourceAsStream("fgd39.json")) {
        ObjectMapper mapper = new ObjectMapper();
  
        ProductServiceResponse serviceResponse = mapper.readValue(is, ProductServiceResponse.class);
        Product shopifyProduct = serviceResponse.getProduct();
        List<String> xfPaths = Arrays.asList("/content/ag/en/header");
        ProductUIResponse uiResponse = ProductUIAdapter.transformProduct(shopifyProduct,
            xfPaths);
        Assert.assertNotNull(uiResponse);
      }
    }

  @Test
    public void testTransformProductItemBeanVariants() throws Exception {
      try (InputStream is = getClass().getResourceAsStream("fgd39-variants.json")) {
        ObjectMapper mapper = new ObjectMapper();
  
        ProductServiceResponse serviceResponse = mapper.readValue(is, ProductServiceResponse.class);
        Product shopifyProduct = serviceResponse.getProduct();
        List<String> xfPaths = Arrays.asList("/content/ag/en/header");
        ProductUIResponse uiResponse = ProductUIAdapter.transformProduct(shopifyProduct,
            xfPaths);
        Assert.assertNotNull(uiResponse);
      }
    }

  @Test
    public void testTransformProductProductBean() throws Exception {
      try (InputStream is = getClass().getResourceAsStream("gvf40.json")) {
        ObjectMapper mapper = new ObjectMapper();
  
        ProductServiceResponse serviceResponse = mapper.readValue(is, ProductServiceResponse.class);
        Product shopifyProduct = serviceResponse.getProduct();
        List<String> xfPaths = Arrays.asList("/content/ag/en/header");
        ProductUIResponse uiResponse = ProductUIAdapter.transformProduct(shopifyProduct,
            xfPaths);
        Assert.assertNotNull(uiResponse);
      }
    }

  @Test
    public void testTransformProductGiftCard() throws Exception {
      try (InputStream is = getClass().getResourceAsStream("gxw35.agcar.json")) {
        ObjectMapper mapper = new ObjectMapper();
  
        ProductServiceResponse serviceResponse = mapper.readValue(is, ProductServiceResponse.class);
        Product shopifyProduct = serviceResponse.getProduct();
        ProductUIResponse uiResponse = ProductUIAdapter.transformProductToGiftCard(shopifyProduct);
        Assert.assertNotNull(uiResponse);
      }
    }

  @Test
    public void testTransformProductBundleBean() throws Exception {
      try (InputStream is = getClass().getResourceAsStream("06bun210.json")) {
        ObjectMapper mapper = new ObjectMapper();
  
        ProductServiceResponse serviceResponse = mapper.readValue(is, ProductServiceResponse.class);
        Product shopifyProduct = serviceResponse.getProduct();
        List<String> xfPaths = Arrays.asList("/content/ag/en/header");
        ProductUIResponse uiResponse = ProductUIAdapter.transformProduct(shopifyProduct,
            xfPaths);
        Assert.assertNotNull(uiResponse);
      }
    }

  @Test
    public void testTransformProductBundleBeanNewOverrideFlag() throws Exception {
      try (InputStream is = getClass().getResourceAsStream("06bun210.json")) {
        ObjectMapper mapper = new ObjectMapper();
  
        ProductServiceResponse serviceResponse = mapper.readValue(is, ProductServiceResponse.class);
        Product shopifyProduct = serviceResponse.getProduct();
        shopifyProduct.getCore().setProduct_newoverrideflag("Y");
        List<String> xfPaths = Arrays.asList("/content/ag/en/header");
        ProductUIResponse uiResponse = ProductUIAdapter.transformProduct(shopifyProduct,
            xfPaths);
        Assert.assertNotNull(uiResponse);
      }
    }

  @Test
    public void testTransformProductBundleBeanFromShopify() throws Exception {
      try (InputStream is = getClass().getResourceAsStream("05bun51.json")) {
        ObjectMapper mapper = new ObjectMapper();
  
        ProductServiceResponse serviceResponse = mapper.readValue(is, ProductServiceResponse.class);
        Product shopifyProduct = serviceResponse.getProduct();
        List<String> xfPaths = Arrays.asList("/content/ag/en/header");
        ProductUIResponse uiResponse = ProductUIAdapter.transformProduct(shopifyProduct,
            xfPaths);
        Assert.assertNotNull(uiResponse);
      }
    }

  @Test
  public void testTransformProductPackageBean() throws Exception {
    try (InputStream is = getClass().getResourceAsStream("gxx43.json")) {
      ObjectMapper mapper = new ObjectMapper();

      ProductServiceResponse serviceResponse = mapper.readValue(is, ProductServiceResponse.class);
      Product shopifyProduct = serviceResponse.getProduct();
      List<String> xfPaths = Arrays.asList("/content/ag/en/header");
      ProductUIResponse uiResponse = ProductUIAdapter.transformProduct(shopifyProduct,
          xfPaths);
      Assert.assertNotNull(uiResponse);
      Assert.assertNotNull(uiResponse.getComponents());
      Assert.assertEquals(7, uiResponse.getComponents().size());
    }
  }

  @Test
  public void testTransformProductGiftCardNew() throws Exception {
    try (InputStream is = getClass().getResourceAsStream("giftcard.json")) {
      ObjectMapper mapper = new ObjectMapper();

      ProductServiceResponse serviceResponse = mapper.readValue(is, ProductServiceResponse.class);
      Product shopifyProduct = serviceResponse.getProduct();
      ProductUIResponse uiResponse = ProductUIAdapter.transformProductToGiftCard(shopifyProduct);
      Assert.assertNotNull(uiResponse);
      Assert.assertNotNull(mapper.writeValueAsString(uiResponse));
    }
  }
}
