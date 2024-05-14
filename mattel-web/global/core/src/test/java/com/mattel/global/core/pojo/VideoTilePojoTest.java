package com.mattel.global.core.pojo;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.global.master.core.pojos.VideoTilePojo;

@RunWith(PowerMockRunner.class)
public class VideoTilePojoTest {

	@InjectMocks
	VideoTilePojo videoTilePojo;
	
	@Mock
	List<String> videoTags;
	
	@Test
	public void testGetterSetter(){
		videoTilePojo.setVideoUrl("videoUrl");
		videoTilePojo.setAlwaysEnglish("alwaysEnglish");
		videoTilePojo.setColLayout("colLayout");
		videoTilePojo.setMetaDesc("metaDesc");
		videoTilePojo.setMetaKeywords("metaKeywords");
		videoTilePojo.setSelected(true);
		videoTilePojo.setSeotitle("seotitle");
		videoTilePojo.setSeoUrl("seoUrl");
		videoTilePojo.setThumbnailAltTxt("thumbnailAltTxt");
		videoTilePojo.setTitleAlign("titleAlign");
		videoTilePojo.setVideoCategory("videoCategory");
		videoTilePojo.setVideoDesc("videoDesc");
		videoTilePojo.setVideoId("videoId");
		videoTilePojo.setVideoName("videoName");
		videoTilePojo.setVideoThumbnail("videoThumbnail");
		videoTilePojo.setVideoTitle("videoTitle");
		videoTilePojo.setVideoUrl("videoUrl");
		videoTilePojo.setVideoTags(videoTags);
		
		
		assertSame("alwaysEnglish", videoTilePojo.getAlwaysEnglish());
		assertSame("colLayout", videoTilePojo.getColLayout());
		assertSame("metaDesc", videoTilePojo.getMetaDesc());
		assertSame("metaKeywords", videoTilePojo.getMetaKeywords());
		assertSame("seotitle", videoTilePojo.getSeotitle());
		assertSame("seoUrl", videoTilePojo.getSeoUrl());
		assertSame("thumbnailAltTxt", videoTilePojo.getThumbnailAltTxt());
		assertSame("titleAlign", videoTilePojo.getTitleAlign());
		assertSame("videoCategory", videoTilePojo.getVideoCategory());
		assertSame("videoDesc", videoTilePojo.getVideoDesc());
		assertSame("videoId", videoTilePojo.getVideoId());
		assertSame("videoName", videoTilePojo.getVideoName());
		assertSame("videoThumbnail", videoTilePojo.getVideoThumbnail());
		assertSame("videoTitle", videoTilePojo.getVideoTitle());
		assertSame("videoUrl", videoTilePojo.getVideoUrl());
		assertSame(videoTags, videoTilePojo.getVideoTags());
		assertTrue(videoTilePojo.isSelected());
		
		videoTilePojo.toString();
		
	}
	
}
