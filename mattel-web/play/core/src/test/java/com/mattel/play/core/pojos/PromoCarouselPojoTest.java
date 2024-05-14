package com.mattel.play.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class PromoCarouselPojoTest {
	
	PromoCarouselPojo promoCarouselPojo;
	
	@Before
	public void setUp() {
		promoCarouselPojo = new PromoCarouselPojo();
			
	}
	
	@Test
	public void getOpenCtalinksin() {
		promoCarouselPojo.getOpenCtalinksin();
	}
	@Test
	public void setOpenCtalinksin() {
		promoCarouselPojo.setOpenCtalinksin("");
	}
	@Test
	public void getCtaLabel() {
		promoCarouselPojo.getCtaLabel();
	}
	@Test
	public void setCtaLabel() {
		promoCarouselPojo.setCtaLabel("");
	}
	@Test
	public void getCtaLink() {
		promoCarouselPojo.getCtaLink();
	}
	@Test
	public void setCtaLink() {
		promoCarouselPojo.setCtaLink("");
	}
	@Test
	public void getBackgroundColor() {
		promoCarouselPojo.getBackgroundColor();
	}
	@Test
	public void setBackgroundColor() {
		promoCarouselPojo.setBackgroundColor("");
	}
	@Test
	public void getBackgroundoption() {
		promoCarouselPojo.getBackgroundoption();
	}
	@Test
	public void setBackgroundoption() {
		promoCarouselPojo.setBackgroundoption("");
	}
	@Test
	public void getCarouselImage() {
		promoCarouselPojo.getCarouselImage();
	}
	@Test
	public void setCarouselImage() {
		promoCarouselPojo.setCarouselImage("");
	}
	@Test
	public void getImageAltText() {
		promoCarouselPojo.getImageAltText();
	}
	@Test
	public void setImageAltText() {
		promoCarouselPojo.setImageAltText("");
	}
	@Test
	public void getImageUrl() {
		promoCarouselPojo.getImageUrl();
	}
	@Test
	public void setImageUrl() {
		promoCarouselPojo.setImageUrl("");
	}
	@Test
	public void getDescription() {
		promoCarouselPojo.getDescription();
	}
	@Test
	public void setDescription() {
		promoCarouselPojo.setDescription("");
	}	
	@Test
	public void getAlignment() {
		promoCarouselPojo.getAlignment();
	}
	@Test
	public void setAlignment() {
		promoCarouselPojo.setAlignment("");
	}
	@Test
	public void getAwalysEnglish() {
		promoCarouselPojo.getAwalysEnglish();
	}
	@Test
	public void setAwalysEnglish() {
		promoCarouselPojo.setAwalysEnglish("");
	}
	@Test
	public void getBlrImage() {
		promoCarouselPojo.getBlrImage();
	}
	@Test
	public void setBlrImage() {
		promoCarouselPojo.setBlrImage("");
	}
	@Test
	public void getTitle() {
		promoCarouselPojo.getTitle();
	}
	@Test
	public void setTitle() {
		promoCarouselPojo.setTitle("");
	}
	@Test
	public void getOoyalaId() {
		promoCarouselPojo.getOoyalaId();
	}
	
	@Test
	public void testSetOoyalaId(){
		promoCarouselPojo.setOoyalaId("ooyalaId");
	}	
	@Test
	public void testSetAdobeTrackingForCta(){
		promoCarouselPojo.setAdobeTrackingForCta("adobeTrackingForCta");
	}
	
	@Test
	public void testGetAdobeTrackingForCta() {
		promoCarouselPojo.getAdobeTrackingForCta();
	}
}
