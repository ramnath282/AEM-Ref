package com.mattel.ag.explore.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class CharacterPojoTest {
	
	CharacterPojo characterPojo;
	
	@Before
	public void setUp() {
		characterPojo = new CharacterPojo();
		characterPojo.setExternal(true);
		characterPojo.setHeading("heading");
		characterPojo.setImagelink("imagelink");
	}
	@Test
	public void toStringTest(){
		characterPojo.toString();
	}
}
