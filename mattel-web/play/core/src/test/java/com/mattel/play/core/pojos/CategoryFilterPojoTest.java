package com.mattel.play.core.pojos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CategoryFilterPojoTest {

	CategoryFilterPojo categoryFilterPojo;
	List<String> categoryTag;
	@Before
	public void setUp() {

		categoryFilterPojo = new CategoryFilterPojo();
		categoryTag = new ArrayList<>();
		
	}

	@Test
	public void getCategoryTitle() {
		categoryFilterPojo.getCategoryTitle();
	}
	@Test
	public void setCategoryPath() {
		categoryFilterPojo.setCategoryPath("");
	}
	@Test
	public void setCategoryTitle() {
		categoryFilterPojo.setCategoryTitle("");
	}
	@Test
	public void getCategoryTag() {
		categoryFilterPojo.getCategoryTag();
	}
	@Test
	public void setCategoryTag() {
		categoryFilterPojo.setCategoryTag(categoryTag);
	}
	@Test
	public void getCategoryPath() {
		categoryFilterPojo.getCategoryPath();
	}
	
}
