package com.mattel.ecomm.core.utils.shopify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.mattel.ecomm.coreservices.core.enums.ProductAssociationMapping;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Association;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Variant;
import com.mattel.ecomm.coreservices.core.pojos.shopify.VariantCore;

public class BaseProductUIAdapterTest {

  @Test
  public void testGetArrayAsString_StringInput(){
    String str = "testdata";
    BaseProductUIAdapter.getArrayAsString(str);
    BaseProductUIAdapter.transform(str);
  }
  
  @Test
  public void testGetArrayAsString_NullInput(){
    BaseProductUIAdapter.getArrayAsString(null);
    BaseProductUIAdapter.transform(null); 
  }
  
  @Test
  public void testGetArrayAsString_ListInput(){
    List<String> strLst = new ArrayList<>();
    strLst.add("testdata1");
    strLst.add("testdata2");
    BaseProductUIAdapter.getArrayAsString(strLst);
    BaseProductUIAdapter.transform(strLst);
  }
  
  @Test
  public void testGetArrayAsString_EmptyListInput(){
    List<String> strLst = new ArrayList<>();
    BaseProductUIAdapter.getArrayAsString(strLst);
    BaseProductUIAdapter.transform(strLst);
  }
  
  @Test
  public void testGetArrayAsString_InvalidObject(){
    Map<String, String> map = new HashMap<>();
    BaseProductUIAdapter.getArrayAsString(map);
    BaseProductUIAdapter.transform(map);
  }
  
  @Test
  public void testPopulateInventory(){
    List<Variant> variants = new ArrayList<>();
    Variant variant = new Variant();
    VariantCore core = new VariantCore();
    core.setSku("sku");
    core.setVariant_parentpartnumber("variant_parentpartnumber");
    variant.setCore(core);
    variants.add(variant);
    BaseProductUIAdapter.populateInventory(variants);
  }
  
  @Test
  public void testPopulateInventory_CoreNull(){
    List<Variant> variants = new ArrayList<>();
    Variant variant = new Variant();
    variant.setCore(null);
    BaseProductUIAdapter.populateInventory(variants);
  }
  
  @Test
  public void testFilterAssociations(){
    List<Association> associations = new ArrayList<>();
    Association association = new Association();
    association.setAssociation_type("GIFTWRAP");;
    associations.add(association);
    BaseProductUIAdapter.filterAssociations(associations);
  }
  
  @Test
  public void testFilterAssociations_EmptyAssociationType(){
    List<Association> associations = new ArrayList<>();
    Association association = new Association();
    association.setAssociation_type(null);;
    associations.add(association);
    BaseProductUIAdapter.filterAssociations(associations);
  }
  
  @Test
  public void testFilterAssociations_InvalidAssociationType(){
    List<Association> associations = new ArrayList<>();
    Association association = new Association();
    association.setAssociation_type("INVALID_ASSOCIATION_TYPE");;
    associations.add(association);
    BaseProductUIAdapter.filterAssociations(associations);
  }
  
  @Test
  public void testFilterAssociations_EmptyAssociations(){
    List<Association> associations = new ArrayList<>();
    BaseProductUIAdapter.filterAssociations(associations);
  }
  
  @Test
  public void testFilterAssociations_Nullcheck(){
    BaseProductUIAdapter.filterAssociations(null);
  }

  @Test
  public void testHasQuickSell() {
    List<Association> associations = new ArrayList<>();
    Association association1 = new Association();
    association1.setAssociation_type(ProductAssociationMapping.QUICK_TYPE);
    Association association2 = new Association();
    association2.setAssociation_type(ProductAssociationMapping.COMPONENT_TYPE);
    associations.add(association1);
    associations.add(association2);
    Assert.assertTrue(BaseProductUIAdapter.hasQuickSell(associations));
  }
}
