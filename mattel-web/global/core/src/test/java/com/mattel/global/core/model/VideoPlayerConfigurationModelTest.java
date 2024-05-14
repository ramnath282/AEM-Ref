package com.mattel.global.core.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.global.core.utils.VideoPlayerConfigurationUtils;

@RunWith(PowerMockRunner.class)
public class VideoPlayerConfigurationModelTest {

	@InjectMocks
	private VideoPlayerConfigurationModel videoPlayerConfigurationModel;
	
	@Mock
	private VideoPlayerConfigurationUtils videoPlayerConfigurationUtils;
	
	
	private String playerType = "playerType";
	
	private String playerHostName = "playerHostName";

	@Before
	public void setup() throws Exception {
		Mockito.when(videoPlayerConfigurationUtils.getPlayerType()).thenReturn(playerType);
		Mockito.when(videoPlayerConfigurationUtils.getPlayerHostName()).thenReturn(playerHostName);
		videoPlayerConfigurationModel.init();
	}

	@Test
	public void testGettersSetters() {
		assertEquals(playerType,videoPlayerConfigurationModel.getPlayerType());
		assertEquals(playerHostName, videoPlayerConfigurationModel.getPlayerHostName());
	}
}
