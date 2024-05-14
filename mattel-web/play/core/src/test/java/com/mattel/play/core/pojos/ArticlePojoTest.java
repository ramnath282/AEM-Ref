package com.mattel.play.core.pojos;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class ArticlePojoTest {
	@InjectMocks
	ArticlePojo articlePojo;
	
	@Test
	public void testGettersSetters(){
		articlePojo.setAlwaysEnglish("alwaysEnglish");
		articlePojo.setArticlePath("articlePath");
		articlePojo.setArticleTagName("articleTagName");
		articlePojo.setArticleTagNameLowercase("articleTagNameLowercase");
		articlePojo.setArticleThumnnail("articleThumnnail");
		articlePojo.setPageDescription("pageDescription");
		articlePojo.setPageTitile("pageTitile");
		
		articlePojo.toString();
		
		Assert.assertEquals("alwaysEnglish", articlePojo.getAlwaysEnglish());
		Assert.assertEquals("articlePath", articlePojo.getArticlePath());
		Assert.assertEquals("articleTagName", articlePojo.getArticleTagName());
		Assert.assertEquals("articleTagNameLowercase", articlePojo.getArticleTagNameLowercase());
		Assert.assertEquals("articleThumnnail", articlePojo.getArticleThumnnail());
		Assert.assertEquals("pageDescription", articlePojo.getPageDescription());
		Assert.assertEquals("pageTitile", articlePojo.getPageTitile());
	}
}
