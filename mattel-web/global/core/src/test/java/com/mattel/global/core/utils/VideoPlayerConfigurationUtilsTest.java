package com.mattel.global.core.utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.global.core.utils.VideoPlayerConfigurationUtils;
import com.mattel.global.core.utils.VideoPlayerConfigurationUtils.Config;

public class VideoPlayerConfigurationUtilsTest {
	VideoPlayerConfigurationUtils videoPlayerConfigurationUtils;
	Config config;

	@Before
	public void setUp() {
		videoPlayerConfigurationUtils = new VideoPlayerConfigurationUtils();
		config = Mockito.mock(Config.class);
	}

	@Test
	public void activate() {
		videoPlayerConfigurationUtils.activate(config);
	}	

	@Test
	public void getCountryMapping() {
		videoPlayerConfigurationUtils.getPlayerHostName();
		videoPlayerConfigurationUtils.getPlayerType();
}

}
