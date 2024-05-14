package com.mattel.ecomm.core.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;

import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.core.pojos.ProductTypeTemplateConfigPojo;
import com.mattel.ecomm.core.services.MultifieldReader;

@RunWith(PowerMockRunner.class)
public class ProductTypeTemplateModelTest {

  @InjectMocks
  ProductTypeTemplateModel productTypeTemplateModel;
  @Mock
  private Node productTypeMapping;
  @Mock
  private MultifieldReader multifieldReader;
  @Mock
  private ValueMap stringValueMapMap;
  String testProductType = "Doll";
  String testPagePath = "/content/ag/retail";

  @Before
  public void setUp() throws Exception {
    Map<String, ValueMap> multiFieldMap = new HashMap<String, ValueMap>();
    multiFieldMap.put("stringValueMapMap", stringValueMapMap);
    MemberModifier.field(ProductTypeTemplateModel.class, "productTypeMapping")
        .set(productTypeTemplateModel, productTypeMapping);
    MemberModifier.field(ProductTypeTemplateModel.class, "multifieldReader")
        .set(productTypeTemplateModel, multifieldReader);
    Mockito.when(multifieldReader.propertyReader(productTypeMapping)).thenReturn(multiFieldMap);
    Mockito.when(stringValueMapMap.get("pageUrl", String.class)).thenReturn("/content/ag/retail");
    Mockito.when(stringValueMapMap.get("productType", String.class)).thenReturn("Doll");
  }

  @Test
  public void testProductTypeTemplateModel() {
    productTypeTemplateModel.init();
    List<ProductTypeTemplateConfigPojo> mappingList = productTypeTemplateModel
        .getPagePathMappingList();

    Assert.assertNotNull(mappingList);
    ProductTypeTemplateConfigPojo productTypeTemplateConfigPojo = mappingList.get(0);
    Assert.assertEquals(testPagePath, productTypeTemplateConfigPojo.getPagePath());
    Assert.assertEquals(testProductType, productTypeTemplateConfigPojo.getProductType());
  }
}
