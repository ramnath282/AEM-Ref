package com.mattel.ag.explore.core.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.ag.explore.core.utils.PropertyReaderUtils;

public class SocialShareTemplateModelTest {

	SocialShareTemplateModel socialShareTemplateModel;
	PropertyReaderUtils propertyReaderUtils;

	@Before
	public void setUp() {
		socialShareTemplateModel = new SocialShareTemplateModel();
		propertyReaderUtils = Mockito.mock(PropertyReaderUtils.class);
		socialShareTemplateModel.setPropertyReaderUtils(propertyReaderUtils);
		Mockito.when(propertyReaderUtils.getFacebookUrl()).thenReturn("facebookUrl");
		Mockito.when(propertyReaderUtils.getTwitterUrl()).thenReturn("twitterUrl");
		Mockito.when(propertyReaderUtils.getPinterestUrl()).thenReturn("pinterestUrl");

	}

	@Test
	public void init() {
		socialShareTemplateModel.init();
	}
@Test
	public void getTwitterUrl() {
		socialShareTemplateModel.getTwitterUrl();
	}
@Test
	public void getPinterestUrl() {
		socialShareTemplateModel.getPinterestUrl();
	}
@Test
	public void getFacebookUrl() {
		socialShareTemplateModel.getFacebookUrl();
	}

}
