package com.mattel.ecomm.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PromoImagePojoTest {
	private PromoImagePojo promoImagePojo = null;

	@Before
	public void setUp() throws Exception {
		promoImagePojo = new PromoImagePojo();
		promoImagePojo.setPromoImagePath("promoImagePath");
		promoImagePojo.setPromoImageAltText("promoImageAltText");
		promoImagePojo.setPromoUrl("promoUrl");
		promoImagePojo.setPromoTarget("promoTarget");
		promoImagePojo.setPromoHeader("promoHeader");
		promoImagePojo.setPromoCTAText("promoCTAText");
		promoImagePojo.setPromoDescription("promoDescription");
		promoImagePojo.setAlwaysEnglish("alwaysEnglish");
		promoImagePojo.setImageCrop("imageCrop");
		promoImagePojo.setImageRotate("imageRotate");
		promoImagePojo.setGridNum("gridNum");
		promoImagePojo.setCheckBoxLink(Boolean.TRUE);
		promoImagePojo.setTitleColourType("titleColourType");
	}

	@Test
	public void getPromoImagePath() {
		Assert.assertEquals("promoImagePath", promoImagePojo.getPromoImagePath());
	}

	@Test
	public void getPromoImageAltText() {
		Assert.assertEquals("promoImageAltText", promoImagePojo.getPromoImageAltText());
	}

	@Test
	public void getPromoUrl() {
		Assert.assertEquals("promoUrl", promoImagePojo.getPromoUrl());
	}

	@Test
	public void getPromoTarget() {
		Assert.assertEquals("promoTarget", promoImagePojo.getPromoTarget());
	}

	@Test
	public void getPromoHeader() {
		Assert.assertEquals("promoHeader", promoImagePojo.getPromoHeader());
	}

	@Test
	public void getPromoCTAText() {
		Assert.assertEquals("promoCTAText", promoImagePojo.getPromoCTAText());
	}

	@Test
	public void getPromoDescription() {
		Assert.assertEquals("promoDescription", promoImagePojo.getPromoDescription());
	}

	@Test
	public void getAlwaysEnglish() {
		Assert.assertEquals("alwaysEnglish", promoImagePojo.getAlwaysEnglish());
	}

	@Test
	public void getImageCrop() {
		Assert.assertEquals("imageCrop", promoImagePojo.getImageCrop());
	}

	@Test
	public void getImageRotate() {
		Assert.assertEquals("imageRotate", promoImagePojo.getImageRotate());
	}

	@Test
	public void getGridNum() {
		Assert.assertEquals("gridNum", promoImagePojo.getGridNum());
	}

	@Test
	public void getCheckBoxLink() {
		Assert.assertEquals(Boolean.TRUE, promoImagePojo.getCheckBoxLink());
	}

	@Test
	public void getTitleColourType() {
		Assert.assertEquals("titleColourType", promoImagePojo.getTitleColourType());
	}


}
