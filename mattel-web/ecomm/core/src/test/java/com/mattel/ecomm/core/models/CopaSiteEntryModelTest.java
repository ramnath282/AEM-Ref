package com.mattel.ecomm.core.models;

import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class CopaSiteEntryModelTest {

	private static final String internalUrl = "/content/en/ag/home";
	private static final String externalUrl = "https://www.americangirl.com";
		
	@Self
  private Resource resource;
	
	@Mock
  Node excludedPages;

  @Mock
  Node excludedPageKeywords;
  
  @Mock
  MultifieldReader multifieldReader;

	@InjectMocks
	private CopaSiteEntryModel copaSiteEntryModel;
	
	Map<String, ValueMap> excludeMap;
  ValueMap valMap;
  Map.Entry<String, ValueMap> entry;
  
  
  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws Exception {
    MemberModifier.field(CopaSiteEntryModel.class, "resource").set(copaSiteEntryModel, resource);
    MemberModifier.field(CopaSiteEntryModel.class, "excludedPages").set(copaSiteEntryModel,
        excludedPages);
    MemberModifier.field(CopaSiteEntryModel.class, "excludedPageKeywords").set(copaSiteEntryModel,
        excludedPageKeywords);
    MemberModifier.field(CopaSiteEntryModel.class, "multifieldReader").set(copaSiteEntryModel,
        multifieldReader);

    excludeMap = new HashMap<>();
    valMap = Mockito.mock(ValueMap.class);
    excludeMap.put("", valMap);
    entry = Mockito.mock(Map.Entry.class);

    Mockito.when(multifieldReader.propertyReader(excludedPages)).thenReturn(excludeMap);
    Mockito.when(entry.getValue()).thenReturn(valMap);
    Mockito.when(entry.getValue().get("pagePath", String.class)).thenReturn("excludepath");
    Mockito.when(multifieldReader.propertyReader(excludedPageKeywords)).thenReturn(excludeMap);
  }
  
  @Test
  public void testInit() throws ServiceException, JSONException, RepositoryException {
    copaSiteEntryModel.init();
  }

	@Test
	public void testToVerifyInternalShopCtaLink() throws IllegalArgumentException, IllegalAccessException {
		MemberModifier.field(CopaSiteEntryModel.class, "shopCtaLink").set(copaSiteEntryModel, internalUrl);
		Assert.assertEquals(internalUrl, copaSiteEntryModel.getShopCtaLink());
	}

	@Test
	public void testToVerifyInternalPlayCtaLink() throws IllegalArgumentException, IllegalAccessException {
		MemberModifier.field(CopaSiteEntryModel.class, "playCtaLink").set(copaSiteEntryModel, internalUrl);
		Assert.assertEquals(internalUrl, copaSiteEntryModel.getPlayCtaLink());
	}

	@Test
	public void testToVerifyExternalUrlShopCtaLink() throws IllegalArgumentException, IllegalAccessException {
		MemberModifier.field(CopaSiteEntryModel.class, "shopCtaLink").set(copaSiteEntryModel, externalUrl);
		Assert.assertEquals(externalUrl, copaSiteEntryModel.getShopCtaLink());
	}

	@Test
	public void testToVerifyExternalUrlPlayCtaLink() throws IllegalArgumentException, IllegalAccessException {
		MemberModifier.field(CopaSiteEntryModel.class, "playCtaLink").set(copaSiteEntryModel, externalUrl);
		Assert.assertEquals(externalUrl, copaSiteEntryModel.getPlayCtaLink());
	}
	
}
