package com.mattel.ag.explore.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class CarouselPojoTest {
	
	CarouselPojo carouselPojo;
	
	@Before
	public void setUp() {
		carouselPojo = new CarouselPojo();
		carouselPojo.setArrows("arrows");
		carouselPojo.setAutoPlay("autoPlay");
		carouselPojo.setCenterMode("centerMode");
		carouselPojo.setDescription("description");
		carouselPojo.setDots("dots");
		carouselPojo.setImageAlttext("imageAlttext");
		carouselPojo.setImgUrl("imgUrl");
		carouselPojo.setSlideScroll("slideScroll");
		carouselPojo.setSlideShow("slideShow");
		carouselPojo.setExternal(true);
	}
	
	@Test
	public void testAllGetters(){
		carouselPojo.getArrows();
		carouselPojo.getAutoPlay();
		carouselPojo.getImageAlttext();
		carouselPojo.getClass();
		carouselPojo.getDescription();
		carouselPojo.getDots();
		carouselPojo.getImageAlttext();
		carouselPojo.getImgUrl();
		carouselPojo.getSlideScroll();
		carouselPojo.getSlideShow();
		carouselPojo.isExternal();
		carouselPojo.getCenterMode();
	}
}
