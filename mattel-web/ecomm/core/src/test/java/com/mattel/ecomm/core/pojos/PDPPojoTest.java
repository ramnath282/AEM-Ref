package com.mattel.ecomm.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PDPPojoTest {
	private PDPPojo pdpPojo = null;

	@Before
	public void setUp() throws Exception {
		pdpPojo = new PDPPojo();
		pdpPojo.setProductTitle("productTitle");
		pdpPojo.setProductDescription("productDescription");
		pdpPojo.setProductPrice("productPrice");
		pdpPojo.setProdBrandTitle("prodBrandTitle");
		pdpPojo.setProdBrandTitleLink("prodBrandTitleLink");
		pdpPojo.setWarningMessage("warningMessage");
		pdpPojo.setProductAgeGrade("productAgeGrade");
		pdpPojo.setRatingAvg("ratingAvg");
		pdpPojo.setProductReviewCount("productReviewCount");
		pdpPojo.setPartNumber("partNumber");
		pdpPojo.setAnalyticsRatingAvg("analyticsRatingAvg");
	}

	@Test
	public void getProductTitle() {
		Assert.assertEquals("productTitle", pdpPojo.getProductTitle());
	}

	@Test
	public void getProductDescription() {
		Assert.assertEquals("productDescription", pdpPojo.getProductDescription());
	}

	@Test
	public void getProductPrice() {
		Assert.assertEquals("productPrice", pdpPojo.getProductPrice());
	}

	@Test
	public void getProdBrandTitle() {
		Assert.assertEquals("prodBrandTitle", pdpPojo.getProdBrandTitle());
	}

	@Test
	public void getProdBrandTitleLink() {
		Assert.assertEquals("prodBrandTitleLink", pdpPojo.getProdBrandTitleLink());
	}

	@Test
	public void getWarningMessage() {
		Assert.assertEquals("warningMessage", pdpPojo.getWarningMessage());
	}

	@Test
	public void getProductAgeGrade() {
		Assert.assertEquals("productAgeGrade", pdpPojo.getProductAgeGrade());
	}

	@Test
	public void getRatingAvg() {
		Assert.assertEquals("ratingAvg", pdpPojo.getRatingAvg());
	}

	@Test
	public void getProductReviewCount() {
		Assert.assertEquals("productReviewCount", pdpPojo.getProductReviewCount());
	}

	@Test
	public void getPartNumber() {
		Assert.assertEquals("partNumber", pdpPojo.getPartNumber());
	}

	@Test
	public void getAnalyticsRatingAvg() {
		Assert.assertEquals("analyticsRatingAvg", pdpPojo.getAnalyticsRatingAvg());
	}

}
