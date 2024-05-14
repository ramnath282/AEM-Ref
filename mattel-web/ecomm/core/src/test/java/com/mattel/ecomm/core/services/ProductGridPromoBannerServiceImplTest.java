package com.mattel.ecomm.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.adobe.cq.commerce.common.ValueMapDecorator;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.mattel.ecomm.core.helper.EcommHelper;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PredicateGroup.class, EcommHelper.class })
public class ProductGridPromoBannerServiceImplTest {

	@InjectMocks
	ProductGridPromoBannerServiceImpl productGridPromoBannerServiceImpl;

	private String _PAGEPATH = "/content/ag/en/shop/categories/dolls/doll-care";

	@Mock
	GetResourceResolver getResourceResolver;
	@Mock
	Session session;
	@Mock
	ResourceResolver _resourceResolver;
	@Mock
	Hit hit;
	@Mock
	PredicateGroup predicateGroup;
	@Mock
	QueryBuilder queryBuilder;
	@Mock
	Query query;
	@Mock
	SearchResult result;

	private ValueMap hitProperties;

	@Before
	public void setUp() throws RepositoryException {
		/* Local data */
		Map<String, String> querrymap = new HashMap<>();
		querrymap.put("path", _PAGEPATH + "/jcr:content");
		querrymap.put("type", "nt:unstructured");
		querrymap.put("property", "sling:resourceType");
		querrymap.put("property.value", "mattel/ecomm/ag/components/content/ecomm/productGridPromoBanner");
		querrymap.put("orderby", "@jcr:lastModified");
		querrymap.put("orderby.sort", "desc");
		querrymap.put("p.limit", "-1");

		List<Hit> hits = new ArrayList<Hit>();
		hits.add(hit);

		Long totalMatch = 2L;

		Map<String, Object> hitMap = new HashMap<String, Object>();
		hitMap.put("desktopRowNo", "2");
		hitMap.put("desktopColumnNo", "2");
		hitMap.put("desktopSpanLenth", "2");

		hitMap.put("tabletRowNo", "2");
		hitMap.put("tabletColumnNo", "2");
		hitMap.put("tabletSpanLenth", "2");

		hitMap.put("mobileRowNo", "2");
		hitMap.put("mobileColumnNo", "2");
		hitMap.put("mobileSpanLength", "2");

		hitMap.put("desktopMediaType", "Image");
		hitMap.put("desktopPromoImage", "promoImage");
		hitMap.put("desktopImageAltText", "desktopImageAltText");
		hitMap.put("desktopPromoText", "desktopPromoText");
		hitMap.put("desktopVideoUrl", "mattel/ecomm/ag/components/content/ecomm/productGridPromoBanner");
		hitMap.put("desktopVideoType", "html5");
		hitMap.put("autoPlayVideoDesktop", "true");
		hitMap.put("playVidInModalDesktop", "true");

		hitMap.put("tabletMediaType", "Image");
		hitMap.put("tabletPromoImage", "promoImage");
		hitMap.put("tabletImageAltText", "tabletImageAltText");
		hitMap.put("tabletPromoText", "tabletPromoText");
		hitMap.put("tabletVideoUrl", "mattel/ecomm/ag/components/content/ecomm/productGridPromoBanner");
		hitMap.put("tabletVideoType", "html5");
		hitMap.put("autoPlayVideoTablet", "true");
		hitMap.put("playVidInModalTablet", "true");

		hitMap.put("mobileMediaType", "Image");
		hitMap.put("mobilePromoImage", "promoImage");
		hitMap.put("mobileImageAltText", "mobileImageAltText");
		hitMap.put("mobilePromoText", "mattel/ecomm/ag/components/content/ecomm/productGridPromoBanner");
		hitMap.put("mobileVideoType", "html5");
		hitMap.put("autoPlayVideoMobile", "true");
		hitMap.put("playVidInModalMobile", "true");
		hitProperties = new ValueMapDecorator(hitMap);

		/* Mocks */
		Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(_resourceResolver);
		Mockito.when(_resourceResolver.adaptTo(Session.class)).thenReturn(session);
		PowerMockito.mockStatic(PredicateGroup.class);
		Mockito.when(PredicateGroup.create(querrymap)).thenReturn(predicateGroup);
		Mockito.when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);
		Mockito.when(query.getResult()).thenReturn(result);
		Mockito.when(result.getHits()).thenReturn(hits);
		Mockito.when(result.getTotalMatches()).thenReturn(totalMatch);
		Mockito.when(hit.getProperties()).thenReturn(hitProperties);
		PowerMockito.mockStatic(EcommHelper.class);
	}

	@Test
	public void getProductGridPromoBannerJsonTest() {
		productGridPromoBannerServiceImpl.getProductGridPromoBannerJson(_PAGEPATH);
	}

	@Test
	public void getProductGridPromoBannerJsonTest_withRepositoryException() throws RepositoryException {
		Mockito.when(hit.getProperties()).thenThrow(RepositoryException.class);
		productGridPromoBannerServiceImpl.getProductGridPromoBannerJson(_PAGEPATH);
	}

}
