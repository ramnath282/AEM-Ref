package com.mattel.ecomm.core.utils.shopify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.ecomm.coreservices.core.pojos.shopify.AssociatedProduct;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Association;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Core;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Option;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Variant;

@RunWith(MockitoJUnitRunner.class)
public class ComponentUIAdpaterTest {

  @Test
  public void testTransformComponentToSingleSKU() {
    final Association association = new Association();
    final AssociatedProduct associatedProduct = new AssociatedProduct();
    association.setAssociation_type("ADDONSERVICES");
    // association.setId(143424342);
    association.setProduct_id(43232432);

    final List<Association> associations = new ArrayList<>();
    final Association association1 = new Association();
    association1.setAssociation_type("COMPONENT");
    associations.add(association1);
    association.setAssociations(associations);

    final Map<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("Tab2Label", "Specifications");
    associatedProduct.setAttributes(attributes);

    final Core core = new Core();
    core.setProduct_affirmInEligibleFlag("true");
    core.setProduct_primarybundlesku("Y");
    core.setProduct_thumnail("FTG00_Bright_Blooms_Earring_Set_Dolls_1");
    core.setProduct_imagelink("https://mattel.scene7.com/is/image/Mattel/WWEP_Viewer");
    associatedProduct.setCore(core);

    final List<Option> options = new ArrayList<>();
    final Option option = new Option();
    options.add(option);
    associatedProduct.setOptions(options);

    final List<Variant> variants = new ArrayList<>();
    final Variant variant = new Variant();
    variants.add(variant);
    associatedProduct.setVariants(variants);
    association.setProduct(associatedProduct);

    BundledComponentUIAdpater.transform(association, "fgd39");
  }
}
