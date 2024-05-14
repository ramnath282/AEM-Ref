package com.mattel.ag.retail.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class PersonalShopperFormPojoTest {

	PersonalShopperFormPojo personalShopperFormPojo;

	@Before
	public void setUp() {
		personalShopperFormPojo = new PersonalShopperFormPojo();
	}

	@Test
	public void setLocation() {
		personalShopperFormPojo.setLocation("Location");
	}

	@Test
	public void setContactOption() {
		personalShopperFormPojo.setContactOption("ContactOption");
	}

	@Test
	public void setFirstVisitOption() {
		personalShopperFormPojo.setFirstVisitOption("FirstVisionOption");
	}

	@Test
	public void getLocation() {
		personalShopperFormPojo.getLocation();
	}

	@Test
	public void getContactOption() {
		personalShopperFormPojo.getContactOption();
	}

	@Test
	public void getFirstVisitOption() {
		personalShopperFormPojo.getFirstVisitOption();
	}
	
	@Test 
	public void toStringTest(){
		personalShopperFormPojo.toString();
	}

}
