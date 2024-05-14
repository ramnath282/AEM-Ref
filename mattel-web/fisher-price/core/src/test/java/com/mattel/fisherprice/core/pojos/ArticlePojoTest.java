package com.mattel.fisherprice.core.pojos;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

public class ArticlePojoTest {

	ArticlePojo articlePojo;
	Locale ls;
	List<String> listArt = new ArrayList<String>();
	private List<TagsPojo> primaryTag;
	private List<TagsPojo> secondaryTag;

	@Before
	public void setUp() {
		articlePojo = new ArticlePojo();
		listArt.add("string1");
		listArt.add("string2");
	}

	@Test
	public void testArticles() {
		articlePojo.setId("1234");
		articlePojo.setName("Article");
		articlePojo.setArticleAge("24");
		articlePojo.setBrandLinkUrl("www.mokckito_article.com");
		articlePojo.setCategoryId("4321");
		articlePojo.setCategoryLink("www.mokckito_category.com");
		articlePojo.setCategoryName("cat_name");
		articlePojo.setCategoryTitle("cat_title");
		articlePojo.setCreated("just now");
		articlePojo.setInventory("inventory");
		articlePojo.setIsPublished("true");
		articlePojo.setLanguage("english");
		articlePojo.setLastPublished("tomorrow");
		articlePojo.setLocale(ls);
		articlePojo.setMargin("margin");
		articlePojo.setMessage("message");
		articlePojo.setMissingMandatoryFields(listArt);
		articlePojo.setMobileLink("/content/mobile/article");
		articlePojo.setPageUrl("/set/setting/article");
		articlePojo.setParentPageName("parent page name");
		articlePojo.setParentPageTitle("parent page title");
		articlePojo.setPrimaryTag(primaryTag);
		articlePojo.setRegion("regin");
		articlePojo.setSecondaryTag(secondaryTag);
		articlePojo.setSubCategoryName("sub Category Name");
		articlePojo.setSubCategoryTitle("sub Category Title");
		articlePojo.setThumbnailUrl("/content/thumbnile/path");
		articlePojo.setToBeShown(true);
		articlePojo.setValue("value");
		articlePojo.setVanityUrl("/content/set/vanity");
		
		assertEquals(articlePojo.getId(),"1234");
		assertEquals(articlePojo.getName(),"Article");
		assertEquals(articlePojo.getArticleAge(),"24");
		assertEquals(articlePojo.getBrandLinkUrl(),"www.mokckito_article.com");
		assertEquals(articlePojo.getCategoryId(),"4321");
		assertEquals(articlePojo.getCategoryLink(),"www.mokckito_category.com");
		assertEquals(articlePojo.getCategoryName(),"cat_name");
		assertEquals(articlePojo.getCategoryTitle(),"cat_title");
		assertEquals(articlePojo.getCreated(),"just now");
		assertEquals(articlePojo.getInventory(),"inventory");
		assertEquals(articlePojo.getIsPublished(),"true");
		assertEquals(articlePojo.getLanguage(),"english");
		assertEquals(articlePojo.getLastPublished(),"tomorrow");
		assertEquals(articlePojo.getLocale(),ls);
		assertEquals(articlePojo.getMargin(),"margin");
		assertEquals(articlePojo.getMessage(),"message");
		assertEquals(articlePojo.getMissingMandatoryFields(),listArt);
		assertEquals(articlePojo.getMobileLink(),"/content/mobile/article");
		assertEquals(articlePojo.getPageUrl(),"/set/setting/article");
		assertEquals(articlePojo.getParentPageName(),"parent page name");
		assertEquals(articlePojo.getParentPageTitle(),"parent page title");
		assertEquals(articlePojo.getPrimaryTag(),primaryTag);
		assertEquals(articlePojo.getRegion(),"regin");
		assertEquals(articlePojo.getSecondaryTag(),secondaryTag);
		assertEquals(articlePojo.getSubCategoryName(),"sub Category Name");
		assertEquals(articlePojo.getSubCategoryTitle(),"sub Category Title");
		assertEquals(articlePojo.getThumbnailUrl(),"/content/thumbnile/path");
		assertEquals(articlePojo.isToBeShown(),true);
		assertEquals(articlePojo.getValue(),"value");
		assertEquals(articlePojo.getVanityUrl(),"/content/set/vanity");
		assertEquals(articlePojo.toString(), "ArticlePojo [id=1234, name=Article, categoryId=4321, message=message, thumbnailUrl=/content/thumbnile/path, value=value, pageUrl=/set/setting/article, vanityUrl=/content/set/vanity, inventory=inventory, margin=margin, categoryName=cat_name, subCategoryName=sub Category Name, categoryTitle=cat_title, subCategoryTitle=sub Category Title, categoryLink=www.mokckito_category.com, language=english, region=regin, created=just now, lastPublished=tomorrow, articleAge=24, isPublished=true, locale=null, mobileLink=/content/mobile/article, parentPageTitle=parent page title, parentPageName=parent page name, primaryTag=null, secondaryTag=null, brandLinkUrl=www.mokckito_article.com, toBeShown=true, missingMandatoryFields=[string1, string2]]");
	}
	
}
