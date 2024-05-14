package com.mattel.global.core.enums;

import org.junit.Test;

public class InterstitialMappingsTest {

	@Test
	public void testGetInterstitialMapping1() {
		InterstitialMappings interstitialMappings = InterstitialMappings.INTERSTITIALMODAL;
		interstitialMappings.getInterstitialMapping();
	}
	
	@Test
	public void testGetInterstitialMapping2() {
		InterstitialMappings interstitialMappings = InterstitialMappings.INTERSTITIALRETAILERMODAL;
		interstitialMappings.getInterstitialMapping();
	}
	
	@Test
	public void testGetInterstitialMapping3() {
		InterstitialMappings interstitialMappings = InterstitialMappings.LIGHTBOXCONTAINER;
		interstitialMappings.getInterstitialMapping();
	}
	
	@Test
	public void testGetInterstitialMapping4() {
		InterstitialMappings interstitialMappings = InterstitialMappings.INTERSTITIALGAMERETAILERMODAL;
		interstitialMappings.getInterstitialMapping();
	}

}
