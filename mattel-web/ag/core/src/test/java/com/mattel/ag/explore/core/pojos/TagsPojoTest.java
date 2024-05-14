package com.mattel.ag.explore.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class TagsPojoTest {

	TagsPojo TagsPojo;

	@Before
	public void setUp() {

		TagsPojo = new TagsPojo();

	}

	@Test
	public void getTagName() {
		TagsPojo.getTagName();
	}

	@Test
	public void setTagName() {
		TagsPojo.setTagName("tagName");
	}

	@Test
	public void getTagTitle() {
		TagsPojo.getTagTitle();
	}

	@Test
	public void setTagTitle() {
		TagsPojo.setTagTitle("tagTitle");
	}

	@Test
	public void getTagID() {
		TagsPojo.getTagID();
	}

	@Test
	public void setTagID() {
		TagsPojo.setTagID("tagID");
	}

	@Test
	public void toStringTest(){
		TagsPojo.toString();
	}
	}

