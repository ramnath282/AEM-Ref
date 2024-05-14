package com.mattel.ecomm.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.core.services.MultifieldReader;

@RunWith(PowerMockRunner.class)
public class RegisterDollModelTest {

  private RegisterDollModel registerDollModel;
  Resource resource;
  MultifieldReader multifieldReader;
  Node productlines;
  Node retailers;
  Map.Entry<String, ValueMap> entry;
  Node purchaseReasons;
  ValueMap valueMap;
  List<String> productlineList = new ArrayList<>();
  List<String> retailerList = new ArrayList<>();
  List<String> purchaseReasonList = new ArrayList<>();

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws RepositoryException, IllegalArgumentException, IllegalAccessException {
    registerDollModel = new RegisterDollModel();
    resource = Mockito.mock(Resource.class);
    multifieldReader = Mockito.mock(MultifieldReader.class);
    retailers = Mockito.mock(Node.class);
    purchaseReasons = Mockito.mock(Node.class);
    productlines = Mockito.mock(Node.class);
    entry = Mockito.mock(Entry.class);
    valueMap = Mockito.mock(ValueMap.class);
    MemberMatcher.field(RegisterDollModel.class, "productlines").set(registerDollModel,
        productlines);
    MemberMatcher.field(RegisterDollModel.class, "purchaseReasons").set(registerDollModel,
        purchaseReasons);
    MemberMatcher.field(RegisterDollModel.class, "retailers").set(registerDollModel, retailers);
    MemberMatcher.field(RegisterDollModel.class, "multifieldReader").set(registerDollModel,
        multifieldReader);
    final Map<String, ValueMap> multifieldProperty = new HashMap<>();
    multifieldProperty.put("page", valueMap);
    multifieldProperty.put("page", valueMap);
    multifieldProperty.put("page", valueMap);
    Mockito.when(multifieldReader.propertyReader(productlines)).thenReturn(multifieldProperty);
    Mockito.when(multifieldReader.propertyReader(retailers)).thenReturn(multifieldProperty);
    Mockito.when(multifieldReader.propertyReader(purchaseReasons)).thenReturn(multifieldProperty);
  }

  @Test
  public void testInit() {
    registerDollModel.init();
  }

  @Test
  public void testGetProductlineList() {
    registerDollModel.getProductlineList();
  }

  @Test
  public void testGetRetailerList() {
    registerDollModel.getRetailerList();
  }

  @Test
  public void testGetPurchaseReasonList() {
    registerDollModel.getPurchaseReasonList();
  }

}
