package com.mattel.ecomm.core.pojos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.core.pojos.shopify.ComponentUIResponse;
import com.mattel.ecomm.core.pojos.shopify.ProductUIResponse;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Association;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Core;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Option;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Variant;

@RunWith(PowerMockRunner.class)
public class ShopifyProductUIResponseTest {
	@InjectMocks
	ProductUIResponse shopifyProductUIResponse;

	@Before
	public void setup() throws Exception {
		shopifyProductUIResponse = new ProductUIResponse();
	}

	@Test
	public void testGettters() throws IllegalArgumentException, IllegalAccessException {
		List<Map<String, Object>> images = new ArrayList<>();
		Map<String, Object> attributes = new HashMap<>();
		Core core = Mockito.mock(Core.class);
		List<String> experienceFragmentPath = new ArrayList<>();
		List<Variant> variants = new ArrayList<>();
		List<Option> options = new ArrayList<>();
		List<Association> associations = new ArrayList<>();
		List<ComponentUIResponse> components = new ArrayList<>();
		Map<String, String[]> storeMap = new HashMap<>();

		shopifyProductUIResponse.setId(1234);
		shopifyProductUIResponse.setProduct_id(123456);
		shopifyProductUIResponse.setImages(images);
		shopifyProductUIResponse.setAttributes(attributes);
		shopifyProductUIResponse.setCore(core);
		shopifyProductUIResponse.setExperienceFragmentPath(experienceFragmentPath);
		shopifyProductUIResponse.setVariants(variants);
		shopifyProductUIResponse.setOptions(options);
		shopifyProductUIResponse.setAssociations(associations);
		shopifyProductUIResponse.setComponents(components);
		shopifyProductUIResponse.setStoreMap(storeMap);
		shopifyProductUIResponse.setCanonicalUrl("canonicalUrl");
		
		Assert.assertEquals(1234, shopifyProductUIResponse.getId());
		Assert.assertEquals(123456, shopifyProductUIResponse.getProduct_id());
		Assert.assertEquals(images, shopifyProductUIResponse.getImages());
		Assert.assertEquals(attributes, shopifyProductUIResponse.getAttributes());
		Assert.assertEquals(core, shopifyProductUIResponse.getCore());
		Assert.assertEquals(experienceFragmentPath, shopifyProductUIResponse.getExperienceFragmentPath());
		Assert.assertEquals(variants, shopifyProductUIResponse.getVariants());
		Assert.assertEquals(options, shopifyProductUIResponse.getOptions());
		Assert.assertEquals(associations, shopifyProductUIResponse.getAssociations());
		Assert.assertEquals(components, shopifyProductUIResponse.getComponents());
		Assert.assertEquals(storeMap, shopifyProductUIResponse.getStoreMap());
		Assert.assertEquals("canonicalUrl", shopifyProductUIResponse.getCanonicalUrl());
		shopifyProductUIResponse.toString();
	}
}
