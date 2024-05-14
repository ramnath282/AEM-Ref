package com.mattel.ecomm.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LeftNavLinkPojoTest {

  private LeftNavLinkPojo leftNavLinkPojo = null;

  @Before
  public void setUp() throws Exception {
    leftNavLinkPojo = new LeftNavLinkPojo();
    leftNavLinkPojo.setCurrentPage(true);
    leftNavLinkPojo.setPageLink("pageLink");
    leftNavLinkPojo.setPageName("pageName");
  }

  @Test
  public void testGetPageName() {
    Assert.assertEquals("pageName", leftNavLinkPojo.getPageName());
  }

  @Test
  public void testGetPageLink() {
    Assert.assertEquals("pageLink", leftNavLinkPojo.getPageLink());
  }

  @Test
  public void testIsCurrentPage() {
    Assert.assertEquals(true, leftNavLinkPojo.isCurrentPage());
  }

}
