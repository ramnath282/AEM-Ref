package com.mattel.ecomm.core.pojos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EntityParametersPojoTest {

  EntityParametersPojo entityParametersPojo;

  @Before
  public void setUp() {
      entityParametersPojo = new EntityParametersPojo();
      entityParametersPojo.setArticleId("articleId");
      entityParametersPojo.setAtProperty("atProperty");
      entityParametersPojo.setEntityKey("entityKey");
      entityParametersPojo.setEntityValue("entityValue");
  }

  @Test
  public void testAccordionPojo() {
    Assert.assertEquals("articleId", entityParametersPojo.getArticleId());
    Assert.assertEquals("atProperty", entityParametersPojo.getAtProperty());
    Assert.assertEquals("entityKey", entityParametersPojo.getEntityKey());
    Assert.assertEquals("entityValue", entityParametersPojo.getEntityValue());
    Assert.assertNotNull(entityParametersPojo.toString());
  }

}
