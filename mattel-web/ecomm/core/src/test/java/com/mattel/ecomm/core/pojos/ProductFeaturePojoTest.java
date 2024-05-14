package com.mattel.ecomm.core.pojos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductFeaturePojoTest {
	private ProductFeaturePojo productFeaturePojo = null;
	List<String> prodFeatureBulletItems = new ArrayList<String>();

	@Before
	public void setUp() throws Exception {
		productFeaturePojo = new ProductFeaturePojo();
		productFeaturePojo.setProdFeatureTitle("prodFeatureTitle");
		productFeaturePojo.setProdFeatureDescription("prodFeatureDescription");
		prodFeatureBulletItems.add("bulletOne");
		prodFeatureBulletItems.add("bulletTwo");
		productFeaturePojo.setProdFeatureBulletItems(prodFeatureBulletItems);
	}

	@Test
	public void getProdFeatureTitle() {
		Assert.assertEquals("prodFeatureTitle", productFeaturePojo.getProdFeatureTitle());
	}

	@Test
	public void getProdFeatureDescription() {
		Assert.assertEquals("prodFeatureDescription", productFeaturePojo.getProdFeatureDescription());
	}

	@Test
	public void getProdFeatureBulletItems() {
		Assert.assertEquals(prodFeatureBulletItems, productFeaturePojo.getProdFeatureBulletItems());
	}

}
