package com.mattel.play.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class PlaySitePojoTest {

	
	PlaySitePojo playSitePojo;
	
	@Before
	public void setUp() {

		playSitePojo = new PlaySitePojo();	
	}
	
	@Test
	public void getSiteLabel() {
		playSitePojo.getSiteLabel();
	}
	@Test
	public void setSiteLabel() {
		playSitePojo.setSiteLabel("");
	}
	@Test
	public void getSiteLink() {
		playSitePojo.getSiteLink();
	}
	@Test
	public void setSiteLink() {
		playSitePojo.setSiteLink("");
	}
	@Test
	public void getSiteTarget() {
		playSitePojo.getSiteTarget();
	}
	@Test
	public void setSiteTarget() {
		playSitePojo.setSiteTarget("");
	}
		
}
