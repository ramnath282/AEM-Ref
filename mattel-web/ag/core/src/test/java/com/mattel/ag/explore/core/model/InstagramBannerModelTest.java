package com.mattel.ag.explore.core.model;

import org.junit.Before;
import org.junit.Test;

import com.mattel.ag.explore.core.pojos.CommonPojo;

public class InstagramBannerModelTest {

	CommonPojo commonPojo;
	InstagramBannerModel instagramBannerModel;

	@Before
	public void setUp() {

		commonPojo = new CommonPojo();
		instagramBannerModel = new InstagramBannerModel();
		instagramBannerModel.setImgUrl("imgUrl");
		instagramBannerModel.setCtaLink("ctaLink");

	}

	@Test
	public void init() {
		instagramBannerModel.init();
	}
}
