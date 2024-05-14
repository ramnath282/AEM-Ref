package com.mattel.global.core.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.global.core.utils.GlobalPropertyReaderUtils.Config;

public class GlobalPropertyReaderUtilsTest {

	GlobalPropertyReaderUtils globalPropertyReaderUtils;
	private String placeholder = "dummy_string";

	Config config;

	@Before
	public void setUp() {
		globalPropertyReaderUtils = new GlobalPropertyReaderUtils();
		config = Mockito.mock(GlobalPropertyReaderUtils.Config.class);
		Mockito.when(config.prefAPIKey()).thenReturn(placeholder);
		Mockito.when(config.prefAPIUrl()).thenReturn(placeholder);
	}

	@Test
	public void testConfigForValidSNPUrl() {
		final String[] snpUrls = { "corp_en://stage-sp10056c97.guided.ss-omtrdc.net/" };
		globalPropertyReaderUtils.setSnpUrl(snpUrls);
		when(config.snpUrl()).thenReturn(snpUrls);
		globalPropertyReaderUtils.activate(config);
		assertEquals("//stage-sp10056c97.guided.ss-omtrdc.net/", globalPropertyReaderUtils.getSnpUrl("corp_en"));

	}

	@Test
	public void testConfigForInValidSNPUrl() {
		final String[] snpUrls = { "news_en://stage-sp10056c97.guided.ss-omtrdc.net/" };
		globalPropertyReaderUtils.setSnpUrl(snpUrls);
		when(config.snpUrl()).thenReturn(snpUrls);
		globalPropertyReaderUtils.activate(config);
		assertNotEquals("//stage-sp10056c97.guided.ss-omtrdc.net/", globalPropertyReaderUtils.getSnpUrl("corp_en"));

	}
	
	@Test
	public void testConfigForValidTypheAheadUrl() {
		final String[] typeAheadUrl = { "corp_en://content.atomz.com/autocomplete/sp10/05/6c/97/" };		
		when(config.typeAheadUrl()).thenReturn(typeAheadUrl);
		globalPropertyReaderUtils.activate(config);
		assertEquals("//content.atomz.com/autocomplete/sp10/05/6c/97/", globalPropertyReaderUtils.getTypeAheadUrl("corp_en"));

	}
	
	@Test
	public void testConfigForInValidTypheAheadUrl() {
		final String[] typeAheadUrl = { "fp_en://content.atomz.com/autocomplete/sp10/05/6c/97/" };		
		when(config.typeAheadUrl()).thenReturn(typeAheadUrl);
		globalPropertyReaderUtils.activate(config);
		assertNotEquals("//content.atomz.com/autocomplete/sp10/05/6c/97/", globalPropertyReaderUtils.getTypeAheadUrl("corp_en"));

	}

	@Test
	public void testGetPrefAPIKey() throws Exception {
		globalPropertyReaderUtils.activate(config);
		Assert.assertEquals(placeholder, globalPropertyReaderUtils.getPrefAPIKey());
	}

	@Test
	public void testGetPrefAPIUrl() throws Exception {
		globalPropertyReaderUtils.activate(config);
		Assert.assertEquals(placeholder, globalPropertyReaderUtils.getPrefAPIUrl());
	}


}
