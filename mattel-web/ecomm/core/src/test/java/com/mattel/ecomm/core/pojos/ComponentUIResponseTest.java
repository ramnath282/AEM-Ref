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
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.core.pojos.shopify.ComponentUIResponse;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Association;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Variant;

@RunWith(PowerMockRunner.class)
public class ComponentUIResponseTest {

	@InjectMocks
	ComponentUIResponse componentUIResponse;

	@Before
	public void setup() throws Exception {
		componentUIResponse = new ComponentUIResponse();
	}

	@Test
	public void testGettterSetters() {
		List<Association> associations = new ArrayList<Association>();
		Map<String, Object> attributes = new HashMap<String, Object>();
		List<Variant> variants = new ArrayList<>();

		componentUIResponse.setAssociations(associations);
		componentUIResponse.setAttributes(attributes);
		componentUIResponse.setHandle("handle");
		componentUIResponse.setId(1234);
		componentUIResponse.setProduct_affirmInEligibleFlag("product_affirmInEligibleFlag");
		componentUIResponse.setProduct_auxdescription("product_auxdescription");
		componentUIResponse.setProduct_buyable("product_buyable");
		componentUIResponse.setProduct_custsegexcl("product_custsegexcl");
		componentUIResponse.setProduct_fullimage("product_fullimage");
		componentUIResponse.setProduct_hasAddOns("product_hasAddOns");
		componentUIResponse.setProduct_hasSwatches("product_hasSwatches");
		componentUIResponse.setProduct_id(123456);
		componentUIResponse.setProduct_imagelink("product_imagelink");
		componentUIResponse.setProduct_isDynamicKit("product_isDynamicKit");
		componentUIResponse.setProduct_isretailinventorycheckenabled("product_isretailinventorycheckenabled");
		componentUIResponse.setProduct_newoverrideflag("product_newoverrideflag");
		componentUIResponse.setProduct_parentPartNumber("product_parentPartNumber");
		componentUIResponse.setProduct_partnumber("product_partnumber");
		componentUIResponse.setProduct_primarybundlesku("product_primarybundlesku");
		componentUIResponse.setProduct_productcallout("product_productcallout");
		componentUIResponse.setProduct_sizeChartLink("product_sizeChartLink");
		componentUIResponse.setProduct_thumnail("product_thumnail");
		componentUIResponse.setProduct_type("product_type");
		componentUIResponse.setTitle("title");
		componentUIResponse.setVariants(variants);

		Assert.assertEquals(associations, componentUIResponse.getAssociations());
		Assert.assertEquals(attributes, componentUIResponse.getAttributes());
		Assert.assertEquals("handle", componentUIResponse.getHandle());
		Assert.assertEquals(1234, componentUIResponse.getId());
		Assert.assertEquals("product_affirmInEligibleFlag", componentUIResponse.getProduct_affirmInEligibleFlag());
		Assert.assertEquals("product_auxdescription", componentUIResponse.getProduct_auxdescription());
		Assert.assertEquals("product_buyable", componentUIResponse.getProduct_buyable());
		Assert.assertEquals("product_custsegexcl", componentUIResponse.getProduct_custsegexcl());
		Assert.assertEquals("product_fullimage", componentUIResponse.getProduct_fullimage());
		Assert.assertEquals("product_hasAddOns", componentUIResponse.getProduct_hasAddOns());
		Assert.assertEquals("product_hasSwatches", componentUIResponse.getProduct_hasSwatches());
		Assert.assertEquals(123456, componentUIResponse.getProduct_id());
		Assert.assertEquals("product_imagelink", componentUIResponse.getProduct_imagelink());
		Assert.assertEquals("product_isDynamicKit", componentUIResponse.getProduct_isDynamicKit());
		Assert.assertEquals("product_isretailinventorycheckenabled",
				componentUIResponse.getProduct_isretailinventorycheckenabled());
		Assert.assertEquals("product_newoverrideflag", componentUIResponse.getProduct_newoverrideflag());
		Assert.assertEquals("product_parentPartNumber", componentUIResponse.getProduct_parentPartNumber());
		Assert.assertEquals("product_partnumber", componentUIResponse.getProduct_partnumber());
		Assert.assertEquals("product_primarybundlesku", componentUIResponse.getProduct_primarybundlesku());
		Assert.assertEquals("product_productcallout", componentUIResponse.getProduct_productcallout());
		Assert.assertEquals("product_sizeChartLink", componentUIResponse.getProduct_sizeChartLink());
		Assert.assertEquals("product_thumnail", componentUIResponse.getProduct_thumnail());
		Assert.assertEquals("product_type", componentUIResponse.getProduct_type());
		Assert.assertEquals("title", componentUIResponse.getTitle());
		Assert.assertEquals(variants, componentUIResponse.getVariants());
		componentUIResponse.toString();
	}
}
