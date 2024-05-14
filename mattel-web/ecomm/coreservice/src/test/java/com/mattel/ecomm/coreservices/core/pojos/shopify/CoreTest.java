package com.mattel.ecomm.coreservices.core.pojos.shopify;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CoreTest {
	private Core core;
	
	@Before
	public void setUp() throws Exception {
		core = new Core();
	}

	@Test
	public void testGetterSetter() {
		core.setBody_html("body_html");
		core.setCreated_at("created_at");
		core.setGlobal_description_tag("global_description_tag");
		core.setGlobal_title_tag("global_title_tag");
		core.setHandle("handle");
		core.setProduct_affirmInEligibleFlag("product_affirmInEligibleFlag");
		core.setProduct_auxdescription("product_auxdescription");
		core.setProduct_availabilityStatus("product_availabilityStatus");
		core.setProduct_backordarableText("product_backordarableText");
		core.setProduct_buyable("product_buyable");
		core.setProduct_character("product_character");
		core.setProduct_custsegexcl("product_custsegexcl");
		core.setProduct_description("product_description");
		core.setProduct_disclaimer("product_disclaimer");
		core.setProduct_excludeShippingCountriesFlag("product_excludeShippingCountriesFlag");
		core.setProduct_fullimage("product_fullimage");
		core.setProduct_giftGuide("product_giftGuide");
		core.setProduct_hasAddOns("product_hasAddOns");
		core.setProduct_hasSwatches("product_hasSwatches");
		core.setProduct_imagelink("product_imagelink");
		core.setProduct_isAltCanadaSKU("product_isAltCanadaSKU");
		core.setProduct_isDynamicKit("product_isDynamicKit");
		core.setProduct_isretailinventorycheckenabled("product_isretailinventorycheckenabled");
		core.setProduct_largeTrunkLowPrice("product_largeTrunkLowPrice");
		core.setProduct_marketingAge("product_marketingAge");
		core.setProduct_newoverrideflag("product_newoverrideflag");
		core.setProduct_partnumber("product_partnumber");
		core.setProduct_primarybundlesku("product_primarybundlesku");
		core.setProduct_productcallout("product_productcallout");
		core.setProduct_productCategory("product_productCategory");
		core.setProduct_productGTIN("product_productGTIN");
		core.setProduct_productStatus("product_productStatus");
		core.setProduct_promoCategory("product_promoCategory");
		core.setProduct_releaseDateWeb("product_releaseDateWeb");
		core.setProduct_reviewCount("product_reviewCount");
		core.setProduct_reviewEnabled("product_reviewEnabled");
		core.setProduct_reviewRating("product_reviewRating");
		core.setProduct_reviewRatingOriginal("product_reviewRatingOriginal");
		core.setProduct_safetyMessage("product_safetyMessage");
		core.setProduct_sizeChartLink("product_sizeChartLink");
		core.setProduct_smallTrunkLowPrice("product_smallTrunkLowPrice");
		core.setProduct_subBrand("product_subBrand");
		core.setProduct_subCategory("product_subCategory");
		core.setProduct_thumnail("product_thumnail");
		core.setProduct_type("product_type");
		core.setProduct_viewerVideo("product_viewerVideo");
		core.setProduct_disableQuickView("true");

		Assert.assertEquals("body_html", core.getBody_html());
		Assert.assertEquals("created_at", core.getCreated_at());
		Assert.assertEquals("global_description_tag", core.getGlobal_description_tag());
		Assert.assertEquals("global_title_tag", core.getGlobal_title_tag());
		Assert.assertEquals("handle", core.getHandle());
		Assert.assertEquals("product_affirmInEligibleFlag", core.getProduct_affirmInEligibleFlag());
		Assert.assertEquals("product_auxdescription", core.getProduct_auxdescription());
		Assert.assertEquals("product_availabilityStatus", core.getProduct_availabilityStatus());
		Assert.assertEquals("product_backordarableText", core.getProduct_backordarableText());
		Assert.assertEquals("product_buyable", core.getProduct_buyable());
		Assert.assertEquals("product_character", core.getProduct_character());
		Assert.assertEquals("product_custsegexcl", core.getProduct_custsegexcl());
		Assert.assertEquals("product_description", core.getProduct_description());
		Assert.assertEquals("product_disclaimer", core.getProduct_disclaimer());
		Assert.assertEquals("product_excludeShippingCountriesFlag", core.getProduct_excludeShippingCountriesFlag());
		Assert.assertEquals("product_fullimage", core.getProduct_fullimage());
		Assert.assertEquals("product_giftGuide", core.getProduct_giftGuide());
		Assert.assertEquals("product_hasAddOns", core.getProduct_hasAddOns());
		Assert.assertEquals("product_hasSwatches", core.getProduct_hasSwatches());
		Assert.assertEquals("product_imagelink", core.getProduct_imagelink());
		Assert.assertEquals("product_isAltCanadaSKU", core.getProduct_isAltCanadaSKU());
		Assert.assertEquals("product_isDynamicKit", core.getProduct_isDynamicKit());
		Assert.assertEquals("product_isretailinventorycheckenabled", core.getProduct_isretailinventorycheckenabled());
		Assert.assertEquals("product_largeTrunkLowPrice", core.getProduct_largeTrunkLowPrice());
		Assert.assertEquals("product_marketingAge", core.getProduct_marketingAge());
		Assert.assertEquals("product_newoverrideflag", core.getProduct_newoverrideflag());
		Assert.assertEquals("product_partnumber", core.getProduct_partnumber());
		Assert.assertEquals("product_primarybundlesku", core.getProduct_primarybundlesku());
		Assert.assertEquals("product_productcallout", core.getProduct_productcallout());
		Assert.assertEquals("product_productCategory", core.getProduct_productCategory());
		Assert.assertEquals("product_productGTIN", core.getProduct_productGTIN());
		Assert.assertEquals("product_productStatus", core.getProduct_productStatus());
		Assert.assertEquals("product_promoCategory", core.getProduct_promoCategory());
		Assert.assertEquals("product_releaseDateWeb", core.getProduct_releaseDateWeb());
		Assert.assertEquals("product_reviewCount", core.getProduct_reviewCount());
		Assert.assertEquals("product_reviewEnabled", core.getProduct_reviewEnabled());
		Assert.assertEquals("product_reviewRating", core.getProduct_reviewRating());
		Assert.assertEquals("product_reviewRatingOriginal", core.getProduct_reviewRatingOriginal());
		Assert.assertEquals("product_safetyMessage", core.getProduct_safetyMessage());
		Assert.assertEquals("product_sizeChartLink", core.getProduct_sizeChartLink());
		Assert.assertEquals("product_smallTrunkLowPrice", core.getProduct_smallTrunkLowPrice());
		Assert.assertEquals("product_subBrand", core.getProduct_subBrand());
		Assert.assertEquals("product_subCategory", core.getProduct_subCategory());
		Assert.assertEquals("product_thumnail", core.getProduct_thumnail());
		Assert.assertEquals("product_type", core.getProduct_type());
		Assert.assertEquals("product_viewerVideo", core.getProduct_viewerVideo());	
		Assert.assertEquals("true", core.getProduct_disableQuickView());
		core.toString();
	}
}
