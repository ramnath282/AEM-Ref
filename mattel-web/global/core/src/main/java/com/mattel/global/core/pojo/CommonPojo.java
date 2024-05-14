package com.mattel.global.core.pojo;

public class CommonPojo {
	private Boolean external;
	private Boolean ctaExternal;
	
	public Boolean getCtaExternal() {
		return ctaExternal;
	}

	public void setCtaExternal(Boolean ctaExternal) {
		this.ctaExternal = ctaExternal;
	}

	public Boolean getExternal() {
		return external;
	}

	public void setExternal(Boolean external) {
		this.external = external;
	}

  @Override
  public String toString() {
    return "CommonPojo [external=" + external + ", ctaExternal=" + ctaExternal + "]";
  }
	
	
}
