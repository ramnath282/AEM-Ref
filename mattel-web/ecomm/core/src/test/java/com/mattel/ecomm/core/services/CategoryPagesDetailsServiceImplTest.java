package com.mattel.ecomm.core.services;

import java.util.ArrayList;
import java.util.List;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import com.day.cq.search.PredicateGroup;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.Template;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.utils.EcommConfigurationUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PredicateGroup.class, EcommConfigurationUtils.class,EcommHelper.class })
public class CategoryPagesDetailsServiceImplTest {

	@InjectMocks
	CategoryPagesDetailsServiceImpl categoryPagesDetailsServiceImpl;

	private String _PAGEPATH = "/content/ag/en/shop/categories/dolls/doll-care";
	private String _TESTVANITYURL = "/doll-care";
	private String CQ_REDIRECT_TARGET = "cq:redirectTarget";
	private String PAGE_RANKING = "pageRanking";
	private String REDIRECT_TARGET = "/content/ag/en/shop";
	private String ROOTCATGORYPAGEPATH = "/content/ag/en/shop/dolls";
	private String VALIDCATGORYPAGEPATH = "/content/ag/en/shop/dolls/bitty-baby";
	private String PRODGRIDRESOURCEPATH = "mattel/ecomm/shared/components/content/productGrid";
	private String PAGEJCRPATH = "/content/ag/en/shop/dolls/jcr:content";
	private String AGTEMPLATEPATH = "/conf/ag/settings/wcm/templates/ecomm-plp-page";
	

	@Mock
	GetResourceResolver getResourceResolver;
	@Mock
	PredicateGroup predicateGroup;
	@Mock
	ResourceResolver _resourceResolver;
	@Mock
	Session session;
	@Mock
	PageManager _pageManager;
	@Mock
	Resource _resource;
	@Mock
    Resource _resource1;
	@Mock
    Resource _resource1_1;
	@Mock
    Resource _resource1_2;
	@Mock
    Resource _resource2;
	@Mock
	Page _page;
	@Mock
    Page _page1;
	@Mock
    Page _page2;
	@Mock
    Template template;
	@Mock
	ValueMap pagePropertyMap;
	@Mock
	EcommConfigurationUtils ecommConfigurationUtils;
	List<Resource> childrenList;
	List<Page> childrenNodeList1;

	@Before
	public void setUp() throws RepositoryException {
		/* Local data */
		
		childrenList=new ArrayList<>();
		childrenList.add(_resource1);
		childrenList.add(_resource2);
		childrenNodeList1=new ArrayList<>();
		childrenNodeList1.add(_page2);
		

		/* Mocks */
		PowerMockito.mockStatic(PredicateGroup.class);
		PowerMockito.mockStatic(EcommHelper.class);
		Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(_resourceResolver);
		Mockito.when(_resourceResolver.getResource( Mockito.any())).thenReturn(_resource);
		Mockito.when(_resourceResolver.adaptTo(PageManager.class)).thenReturn(_pageManager);
		Mockito.when(_pageManager.getPage(Mockito.any())).thenReturn(_page);
		Mockito.when(_page.getTemplate()).thenReturn(template);
		Mockito.when(_page.getTemplate().getPath()).thenReturn(AGTEMPLATEPATH);
		Mockito.when(_page.getContentResource()).thenReturn(_resource);
		Mockito.when(_resource.listChildren()).thenReturn(childrenList.iterator());
		Mockito.when(_resource1.isResourceType(PRODGRIDRESOURCEPATH)).thenReturn(true);
		Mockito.when(_pageManager.getContainingPage(_resource)).thenReturn(_page1);
		Mockito.when(_page.listChildren()).thenReturn(childrenNodeList1.iterator());
		Mockito.when(_page1.getVanityUrl()).thenReturn(_TESTVANITYURL);
		Mockito.when(_page1.getPath()).thenReturn(_PAGEPATH);
		Mockito.when(_page1.getTitle()).thenReturn("Test Title");
		
		
		Mockito.when(_resource1.getPath()).thenReturn(PAGEJCRPATH);
		Mockito.when(_resource2.getPath()).thenReturn(VALIDCATGORYPAGEPATH);
		
		Mockito.when(_pageManager.getContainingPage(_resource1)).thenReturn(_page1);
		Mockito.when(_page1.getPath()).thenReturn(VALIDCATGORYPAGEPATH);
		Mockito.when(_page1.getProperties()).thenReturn(pagePropertyMap);
		Mockito.when(pagePropertyMap.containsKey(CQ_REDIRECT_TARGET)).thenReturn(true);
		Mockito.when(pagePropertyMap.containsKey(PAGE_RANKING)).thenReturn(true);
		Mockito.when(pagePropertyMap.get(PAGE_RANKING)).thenReturn(12);
		Mockito.when(pagePropertyMap.get(CQ_REDIRECT_TARGET)).thenReturn(REDIRECT_TARGET);
		PowerMockito.mockStatic(EcommConfigurationUtils.class);
		Mockito.when(EcommConfigurationUtils.getRootCatgoryPagePath()).thenReturn(ROOTCATGORYPAGEPATH);
	}

	@Test
	public void getCategoryPagesJsonTest() {
		JSONObject finalJson = categoryPagesDetailsServiceImpl.getCategoryPagesJson(_PAGEPATH,PRODGRIDRESOURCEPATH,AGTEMPLATEPATH);
		Assert.assertNotNull(finalJson);
	}

	@Test
	public void getCategoryPagesJsonTest_withRepositoryException() throws RepositoryException {
		Mockito.when(_resourceResolver.getResource( Mockito.any())).thenThrow(RepositoryException.class);
		categoryPagesDetailsServiceImpl.getCategoryPagesJson(_PAGEPATH,PRODGRIDRESOURCEPATH,AGTEMPLATEPATH);
	}
}
