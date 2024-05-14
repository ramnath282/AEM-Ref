package com.mattel.play.core.commerce;

import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.play.core.commerce.PlayCommerceServiceFactory.Config;

public class PlayCommerceServiceFactoryTest {
	PlayCommerceServiceFactory playCommerceServiceFactory;
	@Before
	public void setUp() {
		playCommerceServiceFactory = new PlayCommerceServiceFactory();
	}
	@Test
	public void getCommerceProvider() {
		PlayCommerceServiceFactory.getCommerceProvider();
	}
	@Test
    public void getCommerceService() {
        playCommerceServiceFactory.getCommerceService(Mockito.mock(Resource.class));
    }
	@Test
	public void activate() {
		playCommerceServiceFactory.activate(Mockito.mock(Config.class));
	}
}
