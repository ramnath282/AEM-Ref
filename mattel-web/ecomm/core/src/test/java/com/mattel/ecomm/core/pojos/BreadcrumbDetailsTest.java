package com.mattel.ecomm.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BreadcrumbDetailsTest {

  private BreadcrumbDetails plpBreadcrumbPojo = null;

  @Before
  public void setUp() throws Exception {
    plpBreadcrumbPojo = new BreadcrumbDetails();
    plpBreadcrumbPojo.setAdobeTrackingName("adobeTrackingName");
    plpBreadcrumbPojo.setPageLink("pageLink");
    plpBreadcrumbPojo.setPagePath("pagePath");
    plpBreadcrumbPojo.setTitle("title");
  }

  @Test
  public void testGetTitle() {
    Assert.assertEquals("title", plpBreadcrumbPojo.getTitle());
  }

  @Test
  public void testGetPageLink() {
    Assert.assertEquals("pageLink", plpBreadcrumbPojo.getPageLink());
  }

  @Test
  public void testGetAdobeTrackingName() {
    Assert.assertEquals("adobeTrackingName", plpBreadcrumbPojo.getAdobeTrackingName());
  }

  @Test
  public void testGetPagePath() {
    Assert.assertEquals("pagePath", plpBreadcrumbPojo.getPagePath());
  }

}
