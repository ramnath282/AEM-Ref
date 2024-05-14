package com.mattel.ecomm.core.pojos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccordianPojoTest {

  AccordianPojo accordianPojo;

  @Before
  public void setUp() {
    accordianPojo = new AccordianPojo();
    accordianPojo.setAccordianTitle("Accordion Title");
    accordianPojo.setFlag(true);
    List<String> accordionKeyList = new ArrayList<>();
    accordionKeyList.add("MarketingDescription");
    accordianPojo.setAccordionKeyList(accordionKeyList);
  }

  @Test
  public void testAccordionPojo() {
    Assert.assertEquals("Accordion Title", accordianPojo.getAccordianTitle());
    Assert.assertTrue(accordianPojo.isFlag());
    Assert.assertNotNull(accordianPojo.getAccordionKeyList());
    Assert.assertNotNull(accordianPojo.toString());
    Assert.assertEquals("MarketingDescription", accordianPojo.getAccordionKeyList().get(0));
  }

}
