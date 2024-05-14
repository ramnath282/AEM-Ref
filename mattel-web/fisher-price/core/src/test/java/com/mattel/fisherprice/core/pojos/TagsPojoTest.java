package com.mattel.fisherprice.core.pojos;

import static org.junit.Assert.*;

import org.junit.Test;

public class TagsPojoTest {

	TagsPojo tagsPojo = new TagsPojo();
	
	@Test
	public void testTagTitle() {
		tagsPojo.setTagTitle("Title");
		assertSame("Title", tagsPojo.getTagTitle());
	}
	
	@Test
	public void testTagID() {
		tagsPojo.setTagID("1");
		assertSame("1", tagsPojo.getTagID());
	}
	
	@Test
	public void testTagName() {
		tagsPojo.setTagName("tagName");
		assertSame("tagName", tagsPojo.getTagName());
	}
	
	@Test
	public void testLocaleBasedTitle() {
		tagsPojo.setLocaleBasedTitle("Local Title");
		assertSame("Local Title", tagsPojo.getLocaleBasedTitle());
	}

}
