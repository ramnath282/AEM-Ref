package com.mattel.global.core.pojo;

public class BrandsBannerPojo {

	private String alttext;
	private String title;
	private String logoImage;
	private String renderoption;
	private String brandlogolink;
	private Boolean external;
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getExternal() {
		return external;
	}

	public void setExternal(Boolean external) {
		this.external = external;
	}

	public String getAlttext() {
		return alttext;
	}

	public void setAlttext(String alttext) {
		this.alttext = alttext;
	}

	public String getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}

	public String getRenderoption() {
		return renderoption;
	}

	public void setRenderoption(String renderoption) {
		this.renderoption = renderoption;
	}

	public String getBrandlogolink() {
		return brandlogolink;
	}

	public void setBrandlogolink(String brandlogolink) {
		this.brandlogolink = brandlogolink;
	}
    @Override
    public String toString() {
      return "BrandsBannerPojo [alttext=" + alttext + ", logoImage=" + logoImage
          + ", renderoption=" + renderoption + ", brandlogolink=" + brandlogolink + ", external=" + external
          +  "]";
    }
}
