package com.mattel.play.core.pojos;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CategoryPojoTest {

	CategoryPojo categoryPojo;
	List<String> categoryTag;
	@Before
	public void setUp() {

		categoryPojo = new CategoryPojo();
		
	}
	@Test
	public void getCatName() {
		categoryPojo.getCatName();
	}
	@Test
	public void setCatName() {
		categoryPojo.setCatName("");
	}
	@Test
	public void getCatThumbnail() {
		categoryPojo.getCatThumbnail();
	}
	@Test
	public void setCatThumbnail() {
		categoryPojo.setCatThumbnail("");
	}
	@Test
	public void getCatAlt() {
		categoryPojo.getCatAlt();
	}
	@Test
	public void setCatAlt() {
		categoryPojo.setCatAlt("");
	}
	@Test
	public void getCatUrl() {
		categoryPojo.getCatUrl();
	}
	@Test
	public void setCatUrl() {
		categoryPojo.setCatUrl("");
	}
	
	@Test
	public void getCatTarget() {
		categoryPojo.getCatTarget();
	}
	
	@Test
	public void setCatTarget() {
		categoryPojo.setCatTarget("");
	}
	
	@Test
	public void setAnalyticsCategoryName() {
		categoryPojo.setAnalyticsCategoryName("analyticsCategoryName");
	}
	
	@Test
	public void getAnalyticsCategoryName() {
		categoryPojo.getAnalyticsCategoryName();
	}

}
