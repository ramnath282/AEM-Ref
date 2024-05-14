package com.mattel.play.core.pojos;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class VideoCategoryTest {

	VideoCategory videoCategory;
	
	@Before
	public void setUp() {

		videoCategory = new VideoCategory();
		
	}
	
	@Test
	public void getCategoryTitle() {
		videoCategory.getCategoryTitle();
	}
	@Test
	public void setCategoryTitle() {
		videoCategory.setCategoryTitle("");
	}
	@Test
	public void getCategoryTag() {
		videoCategory.getCategoryTag();
	}
	@Test
	public void setCategoryTag() {
		videoCategory.setCategoryTag(new ArrayList<>());
	}
	@Test
	public void getCategoryPath() {
		videoCategory.getCategoryName();
	}
	@Test
	public void setCategoryPath() {
		videoCategory.setCategoryName("");
	}
	@Test
	public void getSeotitle() {
		videoCategory.getSeotitle();
	}
	@Test
	public void setSeotitle() {
		videoCategory.setSeotitle("");
	}
	@Test
	public void getSeoUrl() {
		videoCategory.getSeoUrl();
	}
	@Test
	public void setSeoUrl() {
		videoCategory.setSeoUrl("");
	}
	@Test
	public void getMetaKeywords() {
		videoCategory.getMetaKeywords();
	}
	@Test
	public void setMetaKeywords() {
		videoCategory.setMetaKeywords("");
	}
	@Test
	public void getMetaDesc() {
		videoCategory.getMetaDesc();
	}
	@Test
	public void setMetaDesc() {
		videoCategory.setMetaDesc("");
	}
	@Test
	public void isSelected() {
		videoCategory.isSelected();
	}
	@Test
	public void setSelected() {
		videoCategory.setSelected(false);
		videoCategory.toString();
	}
	
	@Test
	public void testGetAnalyticsCategoryName() {
		videoCategory.getAnalyticsCategoryName();
	}
	
	@Test
	public void testSetAnalyticsCategoryName() {
		videoCategory.setAnalyticsCategoryName("analyticsCategoryName");
	}
}
