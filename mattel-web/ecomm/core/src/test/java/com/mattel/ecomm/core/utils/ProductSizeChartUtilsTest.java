package com.mattel.ecomm.core.utils;

import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.pojos.ChildProduct;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Core;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Variant;
import com.mattel.ecomm.coreservices.core.pojos.shopify.VariantCore;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class ProductSizeChartUtilsTest {
	@Test
	public void testSortProducts() {
		final ChildProduct childProduct0 = new ChildProduct();
		final ChildProduct childProduct1 = new ChildProduct();
		final ChildProduct childProduct2 = new ChildProduct();
		final ChildProduct childProduct3 = new ChildProduct();

		childProduct0.setSequence(3.01d);
		childProduct1.setSequence(3.02d);
		childProduct2.setSequence(1);
		final List<ChildProduct> sortedProducts = ProductSizeChartUtils
				.sortProducts(Arrays.asList(childProduct0, childProduct1, childProduct2, childProduct3));

		Assert.assertEquals(childProduct3, sortedProducts.get(0));
		Assert.assertEquals(childProduct2, sortedProducts.get(1));
		Assert.assertEquals(childProduct0, sortedProducts.get(2));
		Assert.assertEquals(childProduct1, sortedProducts.get(3));
	}

	@Test
	public void testSortProducts2() throws IOException {
		final String s1 = "{ \"partNumber\" : \"x\"}";
		final String s2 = "{ \"partNumber\" : \"y\", \"sequence\": null}";
		final String s3 = "{ \"partNumber\" : \"z\", \"sequence\": 4.04}";
		final String s4 = "{ \"partNumber\" : \"e\", \"sequence\": 3}";

		Assert.assertEquals("x", ResourceMapper.getInstance()
				.readValue(s1.getBytes(StandardCharsets.UTF_8), ChildProduct.class).getPartNumber());
		Assert.assertEquals(0d, ResourceMapper.getInstance()
				.readValue(s1.getBytes(StandardCharsets.UTF_8), ChildProduct.class).getSequence(), 0d);
		Assert.assertEquals("y", ResourceMapper.getInstance()
				.readValue(s2.getBytes(StandardCharsets.UTF_8), ChildProduct.class).getPartNumber());
		Assert.assertEquals(0d, ResourceMapper.getInstance()
				.readValue(s2.getBytes(StandardCharsets.UTF_8), ChildProduct.class).getSequence(), 0d);
		Assert.assertEquals("z", ResourceMapper.getInstance()
				.readValue(s3.getBytes(StandardCharsets.UTF_8), ChildProduct.class).getPartNumber());
		Assert.assertEquals(4.04d, ResourceMapper.getInstance()
				.readValue(s3.getBytes(StandardCharsets.UTF_8), ChildProduct.class).getSequence(), 0d);
		Assert.assertEquals("e", ResourceMapper.getInstance()
				.readValue(s4.getBytes(StandardCharsets.UTF_8), ChildProduct.class).getPartNumber());
		Assert.assertEquals(3d, ResourceMapper.getInstance()
				.readValue(s4.getBytes(StandardCharsets.UTF_8), ChildProduct.class).getSequence(), 0d);
	}

	@Test
	public void testSortVariants_1() {

		Mockito.mock(Core.class);
		VariantCore varCore0 = new VariantCore();
		VariantCore varCore1 = new VariantCore();
		VariantCore varCore2 = new VariantCore();
		VariantCore varCore3 = new VariantCore();

		varCore0.setPosition(15);
		varCore1.setPosition(20);
		varCore2.setPosition(25);

		final Variant variant0 = new Variant();
		final Variant variant1 = new Variant();
		final Variant variant2 = new Variant();
		final Variant variant3 = new Variant();

		variant0.setCore(varCore0);
		variant1.setCore(varCore1);
		variant2.setCore(varCore2);
		variant3.setCore(varCore3);

		final List<Variant> sortedVariants = ProductSizeChartUtils
				.sortVariants(Arrays.asList(variant0, variant1, variant2, variant3));

		Assert.assertEquals(variant3, sortedVariants.get(0));
		Assert.assertEquals(variant0, sortedVariants.get(1));
		Assert.assertEquals(variant1, sortedVariants.get(2));
		Assert.assertEquals(variant2, sortedVariants.get(3));
	}

	@Test
	public void testSortVariants_WithCoreNull() {
		List<Variant> variants = new ArrayList<>();
		Variant variant = new Variant();
		variant.setCore(null);
		ProductSizeChartUtils.sortVariants(variants);
	}

}
