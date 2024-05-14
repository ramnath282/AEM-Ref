package com.mattel.play.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class AccordionPojoTest {

	AccordionPojo accordionPojo;
	@Before
	public void setUp() {

		accordionPojo = new AccordionPojo();
		
	}

	@Test
	public void getContentTitle() {
        accordionPojo.getContentTitle();
	}
	@Test
	public void setContentTitle() {
		accordionPojo.setContentTitle("");
	}
	@Test
	public void getAwalysEnglish() {
		 accordionPojo.getAwalysEnglish();
	}
	@Test
	public void setAwalysEnglish() {
		 accordionPojo.setAwalysEnglish("");
	}
	@Test
	public void getContentDescription() {
		accordionPojo.getContentDescription();
	}
	@Test
	public void setContentDescription() {
		accordionPojo.setContentDescription("");
	}
}
