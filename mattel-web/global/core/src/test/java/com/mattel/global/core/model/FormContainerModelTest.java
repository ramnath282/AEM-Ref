package com.mattel.global.core.model;

import static org.junit.Assert.*;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.global.core.utils.GlobalUtils;

@RunWith(PowerMockRunner.class)
public class FormContainerModelTest {
	
	@InjectMocks
    FormContainerModel formContainerModel;
	
	@Mock
    Resource resource;
	
	@Mock
	ResourceResolver resourceResolver;
	
	@Mock
	GlobalUtils globalUtils;
	
	@Mock
	PageManager pgMgr;
	
	@Mock
	Page page;
	
	private static final String THANK_YOU_PAGE_URL = "/content/fisher-price/us/en-us/home/emailPreferenceTest/thankyoupage";
	private static final String BACKGROUND_IMAGE_PATH = "/content/dam/fp-smart-crop/verticle-image.jpg/jcr:content/renditions/original";
	private static final String IMAGE = "/content/dam/fp-smart-crop/verticle-image.jpg";

	@Before
	public void  setup() throws IllegalArgumentException, IllegalAccessException {
		Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(resourceResolver.map(THANK_YOU_PAGE_URL)).thenReturn(THANK_YOU_PAGE_URL);
		Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pgMgr);
		formContainerModel.setThankYouPageURL(THANK_YOU_PAGE_URL);
		MemberModifier.field(FormContainerModel.class, "image").set(formContainerModel, IMAGE);
		Mockito.when(page.getVanityUrl()).thenReturn(THANK_YOU_PAGE_URL);
		Mockito.when(resourceResolver.resolve(IMAGE + "/jcr:content/renditions/original")).thenReturn(resource);
		Mockito.when(resource.getPath()).thenReturn(BACKGROUND_IMAGE_PATH);
		
	}
	
	@Test
	public void testFormContainerFunctionality() {
		formContainerModel.init();
		assertEquals(THANK_YOU_PAGE_URL, formContainerModel.getThankYouPageURL());
		assertEquals(BACKGROUND_IMAGE_PATH, formContainerModel.getBackgroundImagePath());
	}

}
