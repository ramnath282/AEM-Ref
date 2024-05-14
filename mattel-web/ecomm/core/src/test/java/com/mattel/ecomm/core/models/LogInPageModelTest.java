package com.mattel.ecomm.core.models;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;

import org.apache.sling.api.resource.ValueMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.core.pojos.LogInPojo;
import com.mattel.ecomm.core.services.MultifieldReader;

import org.junit.Assert;

@RunWith(PowerMockRunner.class)
public class LogInPageModelTest {

  @InjectMocks
  private LogInPageModel logInPageModel;
  @Mock
  private Node descriptionMultifield;
  @Mock
  private MultifieldReader multifieldReader;
  @Mock
  private ValueMap loginValueMap;
  private List<LogInPojo> descriptionPointsList;

  @Before
  public void setUp() throws Exception {
    Map<String, ValueMap> multiFieldMap = new HashMap<String, ValueMap>();
    multiFieldMap.put("loginValueMap", loginValueMap);
    MemberModifier.field(LogInPageModel.class, "descriptionMultifield").set(logInPageModel,
        descriptionMultifield);
    MemberModifier.field(LogInPageModel.class, "multifieldReader").set(logInPageModel,
        multifieldReader);
    Mockito.when(multifieldReader.propertyReader(descriptionMultifield)).thenReturn(multiFieldMap);
    Mockito.when(loginValueMap.get("descriptionPoint", String.class)).thenReturn("dummyPoint");
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testGetDescriptionMultifield() {
    logInPageModel.init();
    Assert.assertNotNull(logInPageModel.getDescriptionMultifield());
  }

  @Test
  public void testSetDescriptionMultifield() {
    logInPageModel.setDescriptionPointsList(descriptionPointsList);
  }

  @Test
  public void testInit() throws IllegalArgumentException, IllegalAccessException {
    logInPageModel.init();
    descriptionPointsList = logInPageModel.getDescriptionPointsList();
    Assert.assertEquals("dummyPoint", descriptionPointsList.get(0).getDescriptionPointStr());
  }

  @Test
  public void testGetDescriptionPointsList() {
    logInPageModel.init();
    descriptionPointsList = logInPageModel.getDescriptionPointsList();
    Assert.assertEquals("dummyPoint", descriptionPointsList.get(0).getDescriptionPointStr());
  }

  @Test
  public void testSetDescriptionPointsList() {
    logInPageModel.setDescriptionMultifield(descriptionMultifield);
  }

}
