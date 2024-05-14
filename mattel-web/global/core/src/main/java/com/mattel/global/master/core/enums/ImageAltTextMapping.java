package com.mattel.global.master.core.enums;

public enum ImageAltTextMapping {
	
	IMAGEALTTEXT("image"), 
	MOBILEIMAGEALTTEXT("mobileImage"), 
	HOVERIMAGEALTTEXT("hoverImage"), 
	MOBILEHOVERIMAGEALTTEXT("mobileHoverImage");
	
	 private String altTextMapping;
	 
	 ImageAltTextMapping(String altTextMapping) {
	        this.altTextMapping = altTextMapping;
	    }
	 
	    public String getAltTextMapping() {
	        return altTextMapping;
	    }
	

}
