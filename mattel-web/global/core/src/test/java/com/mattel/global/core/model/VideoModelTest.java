package com.mattel.global.core.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class VideoModelTest {

	@InjectMocks
	VideoModel videoModel;

	@Test
	public void testInit() throws IllegalArgumentException, IllegalAccessException {
		MemberModifier.field(VideoModel.class, "embedurl").set(videoModel, "embedurl");	
		videoModel.init();
	}
	
	@Test
	public void testInit1() throws IllegalArgumentException, IllegalAccessException {
		MemberModifier.field(VideoModel.class, "embedurl").set(videoModel, "embedurl?test=1");	
		videoModel.init();
		videoModel.getVideoUrl();
	}
}
