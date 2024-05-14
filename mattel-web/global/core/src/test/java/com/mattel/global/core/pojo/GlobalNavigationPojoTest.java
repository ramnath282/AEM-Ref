package com.mattel.global.core.pojo;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class GlobalNavigationPojoTest {

	@InjectMocks
	private GlobalNavigationPojo globalNavigationPojo;

	List<SiteNavigationPojo> lst = new ArrayList<SiteNavigationPojo>();
	CategoryColumnPojo categoryColumnPojo = new CategoryColumnPojo();
	List<PromoImagePojo> promoImageList = new ArrayList<PromoImagePojo>();
	Resource resource = Mockito.mock(Resource.class);
	String[] navLinks = {"navigationalLinks"};

	@Test
	public void testSetters() {
		globalNavigationPojo.setAeForCategoryTitle("aeForCategoryTitle");
		globalNavigationPojo.setAeForPrimaryNavTitle("aeForPrimaryNavTitle");
		globalNavigationPojo.setCategoryNavLinks(lst);
		globalNavigationPojo.setCategorySectionNavLinks(lst);
		globalNavigationPojo.setCategoryTitle("categoryTitle");
		globalNavigationPojo.setCategoryTitleLink("categoryTitleLink");
		globalNavigationPojo.setColumnFourDetails(categoryColumnPojo);
		globalNavigationPojo.setColumnLayout("columnLayout");
		globalNavigationPojo.setColumnOneDetails(categoryColumnPojo);
		globalNavigationPojo.setColumnThreeDetails(categoryColumnPojo);
		globalNavigationPojo.setColumnTwoDetails(categoryColumnPojo);
		globalNavigationPojo.setDefaultBoolean(true);
		globalNavigationPojo.setDefaultTitle("defaultTitle");
		globalNavigationPojo.setDesktop("desktop");
		globalNavigationPojo.setDevice("device");
		globalNavigationPojo.setFeaturedLinksList(lst);
		globalNavigationPojo.setDisplayShopByValue(true);
		globalNavigationPojo.setFeaturedLinksList(lst);
		globalNavigationPojo.setFeaturedTitle("featuredTitle");
		globalNavigationPojo.setImageAlignmentType("imageAlignmentType");
		globalNavigationPojo.setImageSectionList(promoImageList);
		globalNavigationPojo.setLinkTargetCategory("linkTargetCategory");
		globalNavigationPojo.setLinkTargetPrimaryNav("linkTargetPrimaryNav");
		globalNavigationPojo.setNavigationalLinks(navLinks);
		globalNavigationPojo.setNoImage("noImage");
		globalNavigationPojo.setParentLinksList(lst);
		globalNavigationPojo.setPrimaryNavigationTitle("primaryNavigationTitle");
		globalNavigationPojo.setPrimaryNavLinks(lst);
		globalNavigationPojo.setPrimaryNavTitleLink("primaryNavTitleLink");
		globalNavigationPojo.setPromoImage("promoImage");
		globalNavigationPojo.setResource(resource);
		globalNavigationPojo.setSecondaryNavLinks(lst);
		globalNavigationPojo.setTemplateType("templateType");
		globalNavigationPojo.setPromoHoverImagePath("PromoHoverImagePath");

		Assert.assertEquals("PromoHoverImagePath", globalNavigationPojo.getPromoHoverImagePath());
		Assert.assertEquals("aeForCategoryTitle", globalNavigationPojo.getAeForCategoryTitle());
		Assert.assertEquals("aeForPrimaryNavTitle", globalNavigationPojo.getAeForPrimaryNavTitle());
		Assert.assertEquals(lst, globalNavigationPojo.getCategoryNavLinks());
		Assert.assertEquals(lst, globalNavigationPojo.getCategorySectionNavLinks());
		Assert.assertEquals(lst, globalNavigationPojo.getCategoryNavLinks());
		Assert.assertEquals("categoryTitle", globalNavigationPojo.getCategoryTitle());
		Assert.assertEquals("categoryTitleLink", globalNavigationPojo.getCategoryTitleLink());
		Assert.assertEquals(categoryColumnPojo, globalNavigationPojo.getColumnFourDetails());
		Assert.assertEquals("columnLayout", globalNavigationPojo.getColumnLayout());
		Assert.assertEquals(categoryColumnPojo, globalNavigationPojo.getColumnOneDetails());
		Assert.assertEquals(categoryColumnPojo, globalNavigationPojo.getColumnThreeDetails());
		Assert.assertEquals(categoryColumnPojo, globalNavigationPojo.getColumnTwoDetails());
		Assert.assertEquals(true, globalNavigationPojo.getDefaultBoolean());
		Assert.assertEquals("defaultTitle", globalNavigationPojo.getDefaultTitle());
		Assert.assertEquals("desktop", globalNavigationPojo.getDesktop());
		Assert.assertEquals("device", globalNavigationPojo.getDevice());
		Assert.assertEquals(lst, globalNavigationPojo.getFeaturedLinksList());
		Assert.assertEquals(true, globalNavigationPojo.isDisplayShopByValue());
		Assert.assertEquals(lst, globalNavigationPojo.getFeaturedLinksList());
		Assert.assertEquals("featuredTitle", globalNavigationPojo.getFeaturedTitle());
		Assert.assertEquals("imageAlignmentType", globalNavigationPojo.getImageAlignmentType());
		Assert.assertEquals(promoImageList, globalNavigationPojo.getImageSectionList());
		Assert.assertEquals("linkTargetCategory", globalNavigationPojo.getLinkTargetCategory());
		Assert.assertEquals("linkTargetPrimaryNav", globalNavigationPojo.getLinkTargetPrimaryNav());
		Assert.assertNotNull(globalNavigationPojo.getNavigationalLinks());
		Assert.assertEquals("noImage", globalNavigationPojo.getNoImage());
		Assert.assertEquals(lst, globalNavigationPojo.getParentLinksList());
		Assert.assertEquals("primaryNavigationTitle", globalNavigationPojo.getPrimaryNavigationTitle());
		Assert.assertEquals(lst, globalNavigationPojo.getPrimaryNavLinks());
		Assert.assertEquals("primaryNavTitleLink", globalNavigationPojo.getPrimaryNavTitleLink());
		Assert.assertEquals("promoImage", globalNavigationPojo.getPromoImage());
		Assert.assertEquals(resource, globalNavigationPojo.getResource());
		Assert.assertEquals(lst, globalNavigationPojo.getSecondaryNavLinks());
		Assert.assertEquals("templateType", globalNavigationPojo.getTemplateType());
	}
}
