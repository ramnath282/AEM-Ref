package com.mattel.play.core.model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.play.core.helper.PlayHelper;
import com.mattel.play.core.services.MultifieldReader;
import com.mattel.play.core.utils.PropertyReaderUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PlayHelper.class, PropertyReaderUtils.class })
public class CountryDropDownModelTest {

	@InjectMocks
	CountryDropDownModel countryDropDownModel;

	@Mock
	private SlingHttpServletRequest request;

	@Mock
	private MultifieldReader multifieldReader;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws RepositoryException, IllegalArgumentException, IllegalAccessException {
		PowerMockito.mockStatic(PlayHelper.class);
		PowerMockito.mockStatic(PropertyReaderUtils.class);

		Node countryDetails = Mockito.mock(Node.class);
		MemberModifier.field(CountryDropDownModel.class, "pagePath").set(countryDropDownModel, "/content/play/us");
		MemberModifier.field(CountryDropDownModel.class, "countryDetails").set(countryDropDownModel, countryDetails);

		Resource resource = Mockito.mock(Resource.class);

		Mockito.when(request.getResource()).thenReturn(resource);
		ValueMap valueMap = Mockito.mock(ValueMap.class);
		ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
		Session session = Mockito.mock(Session.class);
		QueryBuilder queryBuilder = Mockito.mock(QueryBuilder.class);

		Mockito.when(resource.adaptTo(ValueMap.class)).thenReturn(valueMap);
		Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		Mockito.when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		Mockito.when(PlayHelper.checkIfSiteisMattelPlay("/content/play/us")).thenReturn(true);

		Mockito.when(PropertyReaderUtils.getPlayPath()).thenReturn("/content/play/us");
		Mockito.when(PlayHelper.getBrandName("/content/play/us")).thenReturn("/fp");
		Mockito.when(valueMap.get("target")).thenReturn("value");
		SearchResult searchResult = Mockito.mock(SearchResult.class);
		Mockito.when(PlayHelper.getCountryNodesByLanguage("/content/play/us/fp", session, queryBuilder))
				.thenReturn(searchResult);

		List<Hit> hitList = Mockito.mock(List.class);
		Mockito.when(searchResult.getHits()).thenReturn(hitList);
		Iterator<Hit> lstItr = Mockito.mock(Iterator.class);
		Mockito.when(hitList.iterator()).thenReturn(lstItr);
		Hit hit = Mockito.mock(Hit.class);
		Mockito.when(lstItr.hasNext()).thenReturn(true, false);
		Mockito.when(lstItr.next()).thenReturn(hit);
		Mockito.when(hit.getPath()).thenReturn("/content/play/us/fp/jcr:content");

		PageManager pageManager = Mockito.mock(PageManager.class);
		Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		Page page = Mockito.mock(Page.class);
		Mockito.when(pageManager.getPage("/content/play/us/fp/")).thenReturn(page);
		Mockito.when(page.getTitle()).thenReturn("page-title");
		Mockito.when(PlayHelper.checkLink("/content/play/us/fp/home", resource)).thenReturn("/content/play/us/fp/home");
		ValueMap propValueMap = Mockito.mock(ValueMap.class);
		Mockito.when(page.getProperties()).thenReturn(propValueMap);
		Mockito.when(propValueMap.get("cq:redirectTarget", String.class)).thenReturn("redirect-target");
		Mockito.when(PlayHelper.checkLink("redirect-target", resource)).thenReturn(null);

		Iterator<Resource> resources = Mockito.mock(Iterator.class);
		Mockito.when(searchResult.getResources()).thenReturn(resources);
		Mockito.when(resources.hasNext()).thenReturn(true, false);
		Resource resourceObj = Mockito.mock(Resource.class);
		Mockito.when(resources.next()).thenReturn(resourceObj);
		Mockito.when(resourceObj.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(resourceResolver.isLive()).thenReturn(true, false);
		
		Map<String, ValueMap> brandsMap = Mockito.mock(Map.class);
		Mockito.when(multifieldReader.propertyReader(countryDetails)).thenReturn(brandsMap);
		Set<Entry<String, ValueMap>> brandEntrySet = Mockito.mock(Set.class);
		Mockito.when(brandsMap.entrySet()).thenReturn(brandEntrySet);
		Iterator<Entry<String, ValueMap>> entryItr = Mockito.mock(Iterator.class);
		Mockito.when(brandEntrySet.iterator()).thenReturn(entryItr);
		Mockito.when(entryItr.hasNext()).thenReturn(true, false);
		Entry<String, ValueMap> entry = Mockito.mock(Entry.class);
		Mockito.when(entryItr.next()).thenReturn(entry);
		ValueMap entryvalue = Mockito.mock(ValueMap.class);
		Mockito.when(entry.getValue()).thenReturn(entryvalue);
		Mockito.when(entryvalue.get("countrySiteUrl", String.class)).thenReturn("/content/play/us/");
	}

	@Test
	public void testInit() {
		countryDropDownModel.init();
	}
	
	@Test
	public void testGetterSetter(){
		countryDropDownModel.getListSize();
		countryDropDownModel.getCountryItemsList();
		ResourceResolverFactory resourceResolverFactory = Mockito.mock(ResourceResolverFactory.class);
		countryDropDownModel.setResourceResolverFactory(resourceResolverFactory );
		countryDropDownModel.setPagePath("testpath");
		countryDropDownModel.getCountryItemsManualList();
	}

}
