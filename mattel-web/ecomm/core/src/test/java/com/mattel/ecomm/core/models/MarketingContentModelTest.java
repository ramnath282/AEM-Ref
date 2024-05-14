package com.mattel.ecomm.core.models;

import java.util.ArrayList;
import java.util.List;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
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

import com.mattel.ecomm.core.interfaces.MarketingContentProviderService;

@RunWith(PowerMockRunner.class)
public class MarketingContentModelTest {

	@InjectMocks
	private MarketingContentModel marketingContentModel;

	@Mock
	SlingHttpServletRequest request;

	@Mock
	private RequestPathInfo pathInfo;

	@Mock
	MarketingContentProviderService marketingContentProviderService;

	private String[] selectors = { "ag_en", "GBM03" };

	private List<String> experienceFragmentPath;

	@Before
	public void setUp() throws Exception {
		experienceFragmentPath = new ArrayList<String>();
		MemberModifier.field(MarketingContentModel.class, "position").set(marketingContentModel, "default");
		MemberModifier.field(MarketingContentModel.class, "request").set(marketingContentModel, request);
		MemberModifier.field(MarketingContentModel.class, "marketingContentProviderService").set(marketingContentModel,
				marketingContentProviderService);
		Mockito.when(marketingContentProviderService.getContentFromTags(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(experienceFragmentPath);
		Mockito.when(request.getRequestPathInfo()).thenReturn(pathInfo);
		Mockito.when(pathInfo.getSelectors()).thenReturn(selectors);
	}

	@Test
	public void getPositionTest() {
		Assert.assertEquals("default", marketingContentModel.getPosition());
	}
	@Test
	public void getExperienceFragmentPathTest() throws JSONException {
		marketingContentModel.init();
		Assert.assertNotNull(marketingContentModel.getExperienceFragmentPath());
	}

}
