package com.mattel.ecomm.core.models;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.api.support.membermodification.MemberMatcher;

public class ProductCarouselTest {

  @Mock
  Resource resource;

  private ProductCarousel productCarousel;

  @Before
  public void setUp() throws RepositoryException, IllegalArgumentException, IllegalAccessException {
    productCarousel = new ProductCarousel();
    MemberMatcher.field(ProductCarousel.class, "categoryQP").set(productCarousel,
        "collections_save");
    MemberMatcher.field(ProductCarousel.class, "productOrder").set(productCarousel, "123");
    MemberMatcher.field(ProductCarousel.class, "maxProductCount").set(productCarousel, "3");
  }

  @Test
  public void test_CheckForCollection() throws IllegalArgumentException, IllegalAccessException {
    productCarousel.init();
  }

  @Test
  public void test_getSnpQueryParam() {
    productCarousel.getSnpQueryParam();
  }
}