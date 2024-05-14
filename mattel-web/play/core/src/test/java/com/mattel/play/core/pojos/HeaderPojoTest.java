package com.mattel.play.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class HeaderPojoTest {
	HeaderPojo headerPojo;
	
	@Before
	public void setUp() {
		headerPojo = new HeaderPojo();
		
	}
	@Test
	public void getAlwaysEnglish() {
		headerPojo.getAlwaysEnglish();
	}
	@Test
	public void setAlwaysEnglish() {
		headerPojo.setAlwaysEnglish("");
	}
	@Test
	public void getNavAltText() {
		headerPojo.getNavAltText();
	}
	@Test
	public void setNavAltText() {
		headerPojo.setNavAltText("");
	}
	@Test
	public void getNavImage() {
		headerPojo.getNavImage();
	}
	@Test
	public void setNavImage() {
		headerPojo.setNavImage("");
	}
	@Test
	public void getNavLabel() {
		headerPojo.getNavLabel();
	}
	@Test
	public void setNavLabel() {
		headerPojo.setNavLabel("");
	}
	@Test
	public void getNavLink() {
		headerPojo.getNavLink();
	}
	@Test
	public void setNavLink() {
		headerPojo.setNavLink("");
	}
	@Test
	public void getNavTarget() {
		headerPojo.getNavTarget();
	}
	@Test
	public void setNavTarget() {
		headerPojo.setNavTarget("");
	}
	@Test
	public void getNavDesktopHoverImage() {
		headerPojo.getNavDesktopHoverImage();
	}
	@Test
	public void getNavMobileImage() {
		headerPojo.getNavMobileImage();
	}
}
