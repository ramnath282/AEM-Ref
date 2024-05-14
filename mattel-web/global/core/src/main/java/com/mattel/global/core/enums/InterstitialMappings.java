package com.mattel.global.core.enums;

public enum InterstitialMappings {
	
	INTERSTITIALMODAL("mattel/ecomm/fisher-price/components/content/interstitialLeavingSite","mattel/informational/components/content/interstitialLeavingSite"), 
	INTERSTITIALRETAILERMODAL("mattel/ecomm/fisher-price/components/content/retailerInterstitial","mattel/informational/components/content/retailerInterstitial"), 
	LIGHTBOXCONTAINER("mattel/global/components/content/lightBoxContainer/v1/lightBoxContainer"),
	INTERSTITIALGAMERETAILERMODAL("mattel/ecomm/fisher-price/components/content/interstitialApp","mattel/informational/components/content/interstitialApp");	
	
	 private String[] interstitialMapping;
	 
	 InterstitialMappings(String... interstitialMapping) {
	    this.setInterstitialMapping(interstitialMapping);
	 }
	 
	 private void setInterstitialMapping(String[] interstitialMapping) {
		 this.interstitialMapping = interstitialMapping;
	 }
	 
	 public String[] getInterstitialMapping() {
        return interstitialMapping;
    }
    
}
