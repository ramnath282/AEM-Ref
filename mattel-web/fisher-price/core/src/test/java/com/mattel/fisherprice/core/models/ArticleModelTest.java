package com.mattel.fisherprice.core.models;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.fisherprice.core.constants.Constants;
import com.mattel.fisherprice.core.utils.FisherPriceUtils;

@RunWith(PowerMockRunner.class)
public class ArticleModelTest {

	@InjectMocks
	ArticleModel articleModel;

	@Mock
	Resource resource;

	@Mock
	FisherPriceUtils fisherPriceUtils;

	@Mock
	ResourceResolver resolver;

	@Mock
	PageManager pageManager;

	@Mock
	Page page;


	Locale lr;

	private static final String TAG_TITLE = "tagtitle";
	private static final String TAG_ID = "tagid";
	private static final String PAGE_PATH="/content/fisher-price/language-masters/en/home/articles/early-child-development/us-articleq1";

	@Before
	public void  setup() {
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(page.getAbsoluteParent(3)).thenReturn(page);
		Mockito.when(page.getPath()).thenReturn(PAGE_PATH);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
		Mockito.when(fisherPriceUtils.getTagData(page, Constants.PRIMARY_TAG, TAG_TITLE,",",lr)).thenReturn("primary Tag");
		Mockito.when(fisherPriceUtils.getTagData(page, Constants.SECONDARY_TAG, TAG_TITLE,", ",lr)).thenReturn("secondary Tag");
		Mockito.when(fisherPriceUtils.getTagData(page, Constants.PRIMARY_TAG, TAG_ID,",",lr)).thenReturn("category/new toys");
		Mockito.when(fisherPriceUtils.getTagData(page, Constants.SECONDARY_TAG, TAG_ID,",",lr)).thenReturn("category/dolls");
		Mockito.when(page.getTitle()).thenReturn("New Toys");
	}

	@Test
	public void testInit() {
		articleModel.init();
		assertEquals("primary Tag", articleModel.getPrimaryTagTitle());
		assertEquals("category/new toys", articleModel.getPrimaryTagIds());
		assertEquals("secondary Tag", articleModel.getSecondaryTagTitle());
		assertEquals("category/dolls", articleModel.getSecondaryTagIds());
		assertEquals("New Toys",articleModel.getPageTitle());
	}

}
