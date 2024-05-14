package com.mattel.global.core.model;

import com.mattel.global.master.core.model.VideoGalleryModel;
import com.mattel.global.master.core.pojos.VideoTilePojo;
import com.mattel.global.master.core.services.VideoGalleryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

@RunWith(PowerMockRunner.class)
public class VideoGalleryModelTest {

	@InjectMocks VideoGalleryModel videoGalleryModel;
	
	@Mock
	List<VideoTilePojo> videoManualList;
	
	@Mock
	private List<VideoTilePojo> videoByDateList;
	
	@Mock
	private List<VideoTilePojo> videoByCategoryList;
	
	@Mock VideoGalleryService videoGalleryService;
	
	
	
	String [] manualVideos = {"test-1","test-2","test-3"};
	
	
	
	
	

	@Before
	public void setup() throws Exception {
		videoGalleryModel.setCategory("category");
		videoGalleryModel.setVideoByCategoryList(videoByCategoryList);
		videoGalleryModel.setVideoByDateList(videoByDateList);
		videoGalleryModel.setVideoManualList(videoManualList);
		MemberModifier.field(VideoGalleryModel.class, "videoSourcePath").set(videoGalleryModel, "videoSourcePath");
		MemberModifier.field(VideoGalleryModel.class, "category").set(videoGalleryModel, "category");
		MemberModifier.field(VideoGalleryModel.class, "showMoreText").set(videoGalleryModel, "showMoreText");
		MemberModifier.field(VideoGalleryModel.class, "showLessText").set(videoGalleryModel, "showLessText");
		MemberModifier.field(VideoGalleryModel.class, "manualVideos").set(videoGalleryModel, manualVideos);
		
		


	}

	@Test
	public void testGetVideoManualList() {
		Mockito.when(videoGalleryService.getManualVideos(manualVideos)).thenReturn(videoManualList);
		videoGalleryModel.getVideoManualList();
	}
	
	@Test
	public void testGetVideoByDateList() {
		Mockito.when(videoGalleryService.getVideosByDateCategory("videoSourcePath", "")).thenReturn(videoByDateList);
		videoGalleryModel.getVideoByDateList();
	}
	
	@Test
	public void testGetVideoByCategoryList() {
		Mockito.when(videoGalleryService.getVideosByDateCategory("videoSourcePath", "")).thenReturn(videoByCategoryList);
		videoGalleryModel.getVideoByCategoryList();
	}
	
	@Test
	public void testGetCategory() {
		videoGalleryModel.getCategory();
	}
	
	@Test
	public void getShowMoreText() {
		videoGalleryModel.getShowMoreText();
	}
	
	
	@Test
	public void getShowLessText() {
		videoGalleryModel.getShowLessText();
	}
	
	
	
}
