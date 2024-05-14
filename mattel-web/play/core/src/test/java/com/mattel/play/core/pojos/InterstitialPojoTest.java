package com.mattel.play.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class InterstitialPojoTest {

	InterstitialPojo interstitialPojo;
	
	@Before
	public void setUp() {

		interstitialPojo = new InterstitialPojo();
			
	}
	
	@Test
	public void getInterstitialLogoSrc() {
		interstitialPojo.getInterstitialLogoSrc();
	}
	@Test
	public void setInterstitialLogoSrc() {
		interstitialPojo.setInterstitialLogoSrc("");
	}
	@Test
	public void getInterstitialLogoAlt() {
		interstitialPojo.getInterstitialLogoAlt();
	}
	@Test
	public void setInterstitialLogoAlt() {
		interstitialPojo.setInterstitialLogoAlt("");
	}
	@Test
	public void getInterstitialUrl() {
		interstitialPojo.getInterstitialUrl();
	}
	@Test
	public void setInterstitialUrl() {
		interstitialPojo.setInterstitialUrl("");
	}
	@Test
	public void getInterstitialTarget() {
		interstitialPojo.getInterstitialTarget();
	}
	@Test
	public void setInterstitialTarget() {
		interstitialPojo.setInterstitialTarget("");
		interstitialPojo.toString();
	}
}
