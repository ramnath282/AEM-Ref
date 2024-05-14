package com.mattel.global.core.model.v2;

import static org.junit.Assert.assertEquals;

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



@RunWith(PowerMockRunner.class)
public class ParallaxModelTest {
	
	@InjectMocks
	ParallaxModel parallaxModel;
	
	@Mock
	ResourceResolver resourceResolver;
	
	@Mock
    Resource resource;
	
	private static final String IMAGE = "/content/dam/fp-smart-crop/verticle-image.jpg";
	private static final String BACKGROUND_IMAGE_PATH = "/content/dam/fp-smart-crop/verticle-image.jpg/jcr:content/renditions/original";
	
	@Before
	public void  setup() throws IllegalArgumentException, IllegalAccessException {
		
		MemberModifier.field(ParallaxModel.class, "title").set(parallaxModel, "title");
		MemberModifier.field(ParallaxModel.class, "description").set(parallaxModel, "description");
		MemberModifier.field(ParallaxModel.class, "image").set(parallaxModel, IMAGE);
		Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(resourceResolver.resolve(IMAGE + "/jcr:content/renditions/original")).thenReturn(resource);
		Mockito.when(resource.getPath()).thenReturn(BACKGROUND_IMAGE_PATH);
	}

	@Test
	public void testParallaxFunctionality() {
		parallaxModel.init();
		assertEquals(BACKGROUND_IMAGE_PATH, parallaxModel.getBackgroundImagePath());
		assertEquals("title", parallaxModel.getTitle());
		assertEquals("description", parallaxModel.getDescription());

	}

}
