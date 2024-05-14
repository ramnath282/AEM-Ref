package com.mattel.global.core.pojo;

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
		promoImagePojo.setAwningImageOption("awningImageOption");
		promoImagePojo.setPromoHoverTopImagePath("promoHoverTopImagePath");
		promoImagePojo.setPromoHoverMiddleImagePath("promoHoverMiddleImagePath");
		promoImagePojo.setPromoHoverBottomImagePath("promoHoverBottomImagePath");
		promoImagePojo.setPromoHoverImagePath("promoHoverImagePath");

	}

	@Test
	public void testGetter() {
		Assert.assertEquals("promoImagePath", promoImagePojo.getPromoImagePath());
		Assert.assertEquals("promoImageAltText", promoImagePojo.getPromoImageAltText());
		Assert.assertEquals("promoUrl", promoImagePojo.getPromoUrl());
		Assert.assertEquals("promoTarget", promoImagePojo.getPromoTarget());
		Assert.assertEquals("promoHeader", promoImagePojo.getPromoHeader());
		Assert.assertEquals("promoCTAText", promoImagePojo.getPromoCTAText());
		Assert.assertEquals("promoDescription", promoImagePojo.getPromoDescription());
		Assert.assertEquals("alwaysEnglish", promoImagePojo.getAlwaysEnglish());
		Assert.assertEquals("imageCrop", promoImagePojo.getImageCrop());
		Assert.assertEquals("imageRotate", promoImagePojo.getImageRotate());
		Assert.assertEquals("gridNum", promoImagePojo.getGridNum());
		Assert.assertEquals(Boolean.TRUE, promoImagePojo.getCheckBoxLink());
		Assert.assertEquals("titleColourType", promoImagePojo.getTitleColourType());
		Assert.assertEquals("awningImageOption", promoImagePojo.getAwningImageOption());
		Assert.assertEquals("promoHoverTopImagePath", promoImagePojo.getPromoHoverTopImagePath());
		Assert.assertEquals("promoHoverMiddleImagePath", promoImagePojo.getPromoHoverMiddleImagePath());
		Assert.assertEquals("promoHoverBottomImagePath", promoImagePojo.getPromoHoverBottomImagePath());
		Assert.assertEquals("promoHoverImagePath", promoImagePojo.getPromoHoverImagePath());

		promoImagePojo.toString();
	}


}
