package com.mattel.play.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class MallTourPagePojoTest {
	
	MallTourPagePojo mallTourPagePojo;
	
	@Before
	public void setUp() {

		mallTourPagePojo = new MallTourPagePojo();
		
	}
	
	@Test
	public void getDateDetails() {
		mallTourPagePojo.getDateDetails();
	}
	@Test
	public void setDateDetails() {
		mallTourPagePojo.setDateDetails("");
	}
	@Test
	public void getLocationDetails() {
		mallTourPagePojo.getLocationDetails();
	}
	@Test
	public void setLocationDetails() {
		mallTourPagePojo.setLocationDetails("");
	}
	@Test
	public void getCtaButtonText() {
		mallTourPagePojo.getCtaButtonText();
	}
	@Test
	public void setCtaButtonText() {
		mallTourPagePojo.setCtaButtonText("");
	}
	@Test
	public void getCtaButtonUrl() {
		mallTourPagePojo.getCtaButtonUrl();
	}
	@Test
	public void setCtaButtonUrl() {
		mallTourPagePojo.setCtaButtonUrl("");
	}
	@Test
	public void getMallTourTarget() {
		mallTourPagePojo.getMallTourTarget();
	}
	@Test
	public void setMallTourTarget() {
		mallTourPagePojo.setMallTourTarget("");
	}
	@Test
	public void getAwalysEnglish() {
		mallTourPagePojo.getAwalysEnglish();
	}
	@Test
	public void setAwalysEnglish() {
		mallTourPagePojo.setAwalysEnglish("");
	}

}
