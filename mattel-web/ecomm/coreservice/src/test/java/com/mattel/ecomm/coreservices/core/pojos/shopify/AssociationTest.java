package com.mattel.ecomm.coreservices.core.pojos.shopify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AssociationTest {
	private Association association;

	@Before
	public void setUp() throws Exception {
		association = new Association();
	}
	
	@Test
	public void testGetterSetter(){
		association.toString();
	}
	
	@Test
	public void testGetterSetters(){
		List<Association> associations = new ArrayList<Association>();
		Map<String, Object> attributes = new HashMap<>();
		Core core  = Mockito.mock(Core.class);
		List<Variant> variants = new ArrayList<Variant>();
		List<Option> options = new ArrayList<Option>();
		AssociatedProduct associatedProduct = new AssociatedProduct();
		
		association.setAssociation_type("association_type");
		association.setAssociations(associations);
		associatedProduct.setAttributes(attributes);
		associatedProduct.setCore(core);
		//association.setId(1234);
		associatedProduct.setOptions(options);
		association.setProduct_id(123456);
		associatedProduct.setVariants(variants);
		association.setProduct(associatedProduct);
		
		Assert.assertEquals("association_type", association.getAssociation_type());
		Assert.assertEquals(associations, association.getAssociations());
		Assert.assertEquals(attributes, associatedProduct.getAttributes());
		Assert.assertEquals(core, associatedProduct.getCore());
		//Assert.assertEquals(1234, association.getId());
		Assert.assertEquals(options, associatedProduct.getOptions());
		Assert.assertEquals(123456, association.getProduct_id());
		Assert.assertEquals(variants, associatedProduct.getVariants());
	}

}
