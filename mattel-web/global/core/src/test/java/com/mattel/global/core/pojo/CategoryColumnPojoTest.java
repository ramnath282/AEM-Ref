package com.mattel.global.core.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CategoryColumnPojoTest {
	private CategoryColumnPojo categoryColumnPojo = null;
	String[] pageList = new String[] { "pageList", "pageList" };

	@Before
	public void setUp() throws Exception {
		categoryColumnPojo = new CategoryColumnPojo();
		categoryColumnPojo.setSubCatTitle("subCatTitle");
		categoryColumnPojo.setSubCatLink("subCatLink");
		categoryColumnPojo.setSubCatUrlTarget("subCatUrlTarget");
		categoryColumnPojo.setSubCatAeText("subCatAeText");
		categoryColumnPojo.setParentPage("parentPage");
		categoryColumnPojo.setColumnListFrom("columnListFrom");
		categoryColumnPojo.setViewAllText("viewAll");
		categoryColumnPojo.setViewAllLink("viewAllLink");
		categoryColumnPojo.setLinkTargetForViewAll("linkTargetForViewAll");
		categoryColumnPojo.setAeForViewAll("aeForViewAll");
		categoryColumnPojo.setParentPageList(pageList);
		categoryColumnPojo.toString();
	}

	@Test
	public void getSubCatTitle() {
		Assert.assertEquals("subCatTitle", categoryColumnPojo.getSubCatTitle());
	}

	@Test
	public void getSubCatLink() {
		Assert.assertEquals("subCatLink", categoryColumnPojo.getSubCatLink());
	}

	@Test
	public void getSubCatUrlTarget() {
		Assert.assertEquals("subCatUrlTarget", categoryColumnPojo.getSubCatUrlTarget());
	}

	@Test
	public void getSubCatAeText() {
		Assert.assertEquals("subCatAeText", categoryColumnPojo.getSubCatAeText());
	}

	@Test
	public void getParentPage() {
		Assert.assertEquals("parentPage", categoryColumnPojo.getParentPage());
	}

	@Test
	public void getColumnListFrom() {
		Assert.assertEquals("columnListFrom", categoryColumnPojo.getColumnListFrom());
	}

	@Test
	public void getViewAllText() {
		Assert.assertEquals("viewAll", categoryColumnPojo.getViewAllText());
	}

	@Test
	public void getViewAllLink() {
		Assert.assertEquals("viewAllLink", categoryColumnPojo.getViewAllLink());
	}

	@Test
	public void getLinkTargetForViewAll() {
		Assert.assertEquals("linkTargetForViewAll", categoryColumnPojo.getLinkTargetForViewAll());
	}

	@Test
	public void getAeForViewAll() {
		Assert.assertEquals("aeForViewAll", categoryColumnPojo.getAeForViewAll());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void getParentPageList() {
		Assert.assertEquals(pageList, categoryColumnPojo.getParentPageList());
	}

}
