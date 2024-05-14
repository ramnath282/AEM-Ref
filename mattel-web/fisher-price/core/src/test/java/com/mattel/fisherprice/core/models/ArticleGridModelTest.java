package com.mattel.fisherprice.core.models;

import static org.junit.Assert.assertEquals;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.Template;

@RunWith(PowerMockRunner.class)
public class ArticleGridModelTest {

	@InjectMocks
	ArticleGridModel articleGridModel;
	
	@Mock
	SlingHttpServletRequest request;
	
	@Mock
	ResourceResolver resolver;
	
	@Mock
	PageManager pageManager;
	
	@Mock
	Page page;
	
	@Mock
	Template template;
	
	@Mock
	TagManager tagManager;
	
	@Mock
	ValueMap map;
	
	@Mock
	Tag tag;
	
	String[] tags = {"primary","secondary"};
	
	private static final String CATEGORY_LANDING_PAGE = "/conf/fisher-price/settings/wcm/templates/category-landing-page";
    private static final String PRIMARY_TAG = "primaryTags";
    private static final String PAGE_PATH="/content/fisher-price/language-masters/en/home/articles/early-child-development/us-articleq1";
	
	@Before
	public void  setup() throws IllegalArgumentException, IllegalAccessException {
		
		MemberModifier.field(ArticleGridModel.class, "seeMoreLabel").set(articleGridModel, "seeMoreLabel");
		MemberModifier.field(ArticleGridModel.class, "defaultImage").set(articleGridModel, "defaultImage");
		MemberModifier.field(ArticleGridModel.class, "searchType").set(articleGridModel, "searchType");
		MemberModifier.field(ArticleGridModel.class, "heading").set(articleGridModel, "heading");
		MemberModifier.field(ArticleGridModel.class, "initialLoadCount").set(articleGridModel, new Integer("101"));
		MemberModifier.field(ArticleGridModel.class, "viewAllLabel").set(articleGridModel, "viewAllLabel");
		MemberModifier.field(ArticleGridModel.class, "productLimit").set(articleGridModel, new Integer("100"));
		Mockito.when(page.getTemplate()).thenReturn(template);
		Mockito.when(template.getPath()).thenReturn(CATEGORY_LANDING_PAGE);
		Mockito.when(page.getAbsoluteParent(3)).thenReturn(page);
		Mockito.when(page.getPath()).thenReturn(PAGE_PATH);
		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		Mockito.when(page.getProperties()).thenReturn(map);
		Mockito.when(map.get(PRIMARY_TAG, String[].class)).thenReturn(tags);
		Mockito.when(tagManager.resolve(articleGridModel.getCategoryId())).thenReturn(tag);
	
	}
	
	@Test
	public void testCategoryTagTitle() throws IllegalArgumentException, IllegalAccessException {
		MemberModifier.field(ArticleGridModel.class, "categoryId").set(articleGridModel, "1001");
		articleGridModel.init();
		assertEquals(null, articleGridModel.getCategoryTagTitle());
		 Assert.assertEquals("seeMoreLabel", articleGridModel.getSeeMoreLabel());
		 Assert.assertEquals("defaultImage", articleGridModel.getDefaultImage());
		 Assert.assertEquals("searchType", articleGridModel.getSearchType());
		 Assert.assertEquals("heading",articleGridModel.getHeading());
		 Assert.assertEquals("101", articleGridModel.getInitialLoadCount().toString());
		 Assert.assertEquals("viewAllLabel", articleGridModel.getViewAllLabel());
		 Assert.assertEquals("100", articleGridModel.getProductLimit().toString());
	}
	
	@Test
	public void testCategoryTagTitleForEmpty() throws IllegalArgumentException, IllegalAccessException {
		MemberModifier.field(ArticleGridModel.class, "categoryId").set(articleGridModel, "");
		articleGridModel.init();
		assertEquals(null, articleGridModel.getCategoryTagTitle());
		
	}

}
