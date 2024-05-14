package com.mattel.ag.retail.core.services;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.ag.retail.core.services.GoogleApiConfiguration;
import com.mattel.ag.retail.core.services.GoogleApiConfiguration.Config;

/**
 * 
 * @author CTS. Service for fetching Google API Key.
 *
 */

public class GoogleApiConfigurationTest {

	Config config;
	GoogleApiConfiguration googleApiConfiguration;

	@Before
	public void setUp(){
		googleApiConfiguration = new GoogleApiConfiguration();
		googleApiConfiguration.setGoogleApiKey("Key");
		config = Mockito.mock(GoogleApiConfiguration.Config.class);
	}
	
	@Test
	public void getGoogleApiKeyTest() {
		when(config.googleAPIKey()).thenReturn(googleApiConfiguration.getGoogleApiKey());
		googleApiConfiguration.activate(config);
		assertSame("Key", googleApiConfiguration.getGoogleApiKey());
	}

}
