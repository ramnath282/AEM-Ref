package com.mattel.play.core.model;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.mattel.play.core.pojos.VideoTile;
import com.mattel.play.core.services.VideoGalleryService;

@RunWith(PowerMockRunner.class)
public class PLPVideoGalleryModelTest {
	@InjectMocks
	PLPVideoGalleryModel plpVideoGalleryModel;

	@Mock
	private VideoGalleryService videoGalleryService;

	@Test
	public void initTest() {
		plpVideoGalleryModel.init();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testtGetVideoManualLst() throws IllegalArgumentException, IllegalAccessException {
		List<VideoTile> videoManualList = Mockito.mock(List.class);
		List<VideoTile> videoList = Mockito.mock(List.class);
		List<VideoTile> subVideoList = Mockito.mock(List.class);
		String[] plpVideos = {};
		MemberModifier.field(PLPVideoGalleryModel.class, "videoManualList").set(plpVideoGalleryModel, videoManualList);
		MemberModifier.field(PLPVideoGalleryModel.class, "plpVideos").set(plpVideoGalleryModel, plpVideos);
		Mockito.when(videoGalleryService.getPLPVideosManual(plpVideos)).thenReturn(videoList);
		Mockito.when(videoList.size()).thenReturn(13);
		Mockito.when(videoManualList.subList(0, 12)).thenReturn(subVideoList);
		plpVideoGalleryModel.getVideoManualList();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testtGetVideoManualList() throws IllegalArgumentException, IllegalAccessException {
		List<VideoTile> videoManualList = Mockito.mock(List.class);
		List<VideoTile> videoList = Mockito.mock(List.class);
		String[] plpVideos = {};
		MemberModifier.field(PLPVideoGalleryModel.class, "videoManualList").set(plpVideoGalleryModel, videoManualList);
		MemberModifier.field(PLPVideoGalleryModel.class, "plpVideos").set(plpVideoGalleryModel, plpVideos);
		Mockito.when(videoGalleryService.getPLPVideosManual(plpVideos)).thenReturn(videoList);
		Mockito.when(videoManualList.size()).thenReturn(11);
		plpVideoGalleryModel.getVideoManualList();
		plpVideoGalleryModel.setVideoManualList(videoManualList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testtGetVideoByDateList() throws IllegalArgumentException, IllegalAccessException {
		List<VideoTile> videoByDateList = Mockito.mock(List.class);
		List<VideoTile> videoList = Mockito.mock(List.class);
		MemberModifier.field(PLPVideoGalleryModel.class, "plpVideoAssetPath").set(plpVideoGalleryModel,
				"plpVideoAssetPath");
		Mockito.when(videoGalleryService.getVideosByDate("plpVideoAssetPath", null, true)).thenReturn(videoByDateList);
		Mockito.when(videoByDateList.size()).thenReturn(13);
		Mockito.when(videoByDateList.subList(0, 12)).thenReturn(videoList);
		plpVideoGalleryModel.getVideoByDateList();
		plpVideoGalleryModel.setVideoByDateList(videoByDateList);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testtGetVideoByDateLst() throws IllegalArgumentException, IllegalAccessException {
		List<VideoTile> videoByDateList = Mockito.mock(List.class);
		MemberModifier.field(PLPVideoGalleryModel.class, "plpVideoAssetPath").set(plpVideoGalleryModel,
				"plpVideoAssetPath");
		Mockito.when(videoGalleryService.getVideosByDate("plpVideoAssetPath", null, true)).thenReturn(videoByDateList);
		Mockito.when(videoByDateList.size()).thenReturn(11);
		plpVideoGalleryModel.getVideoByDateList();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetVideoByCategoryList() throws IllegalArgumentException, IllegalAccessException{
		String [] plpGalleryCategory = {"test1","test2"};
		Resource mockedResource = Mockito.mock(Resource.class);
		ResourceResolver mockedResourceResolver = Mockito.mock(ResourceResolver.class);
		MemberModifier.field(PLPVideoGalleryModel.class, "resource").set(plpVideoGalleryModel,
				mockedResource);
		Mockito.when(mockedResource.getResourceResolver()).thenReturn(mockedResourceResolver);
		TagManager mockedTagManager =  Mockito.mock(TagManager.class);
		Mockito.when(mockedResourceResolver.adaptTo(TagManager.class)).thenReturn(mockedTagManager);
		MemberModifier.field(PLPVideoGalleryModel.class, "plpGalleryCategory").set(plpVideoGalleryModel, plpGalleryCategory);
		MemberModifier.field(PLPVideoGalleryModel.class, "plpVideoAssetPath").set(plpVideoGalleryModel, "plpVideoAssetPath");
		Tag galleryTag = Mockito.mock(Tag.class);
		Mockito.when(mockedTagManager.resolve(plpGalleryCategory[0])).thenReturn(galleryTag);
		Mockito.when(galleryTag.getTagID()).thenReturn("testTagId");
		List<VideoTile> videoByCategoryList = Mockito.mock(List.class);
		Mockito.when(videoGalleryService.getVideosByDate("plpVideoAssetPath", "testTagId", true)).thenReturn(videoByCategoryList);
		Mockito.when(videoByCategoryList.size()).thenReturn(13);
		List<VideoTile> videoList = Mockito.mock(List.class);
		Mockito.when(videoByCategoryList.subList(0, 12)).thenReturn(videoList);
		plpVideoGalleryModel.getVideoByCategoryList();
		plpVideoGalleryModel.setVideoByCategoryList(videoByCategoryList);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetVideoByCategoryLst() throws IllegalArgumentException, IllegalAccessException{
		String [] plpGalleryCategory = {"test1","test2"};
		Resource mockedResource = Mockito.mock(Resource.class);
		ResourceResolver mockedResourceResolver = Mockito.mock(ResourceResolver.class);
		MemberModifier.field(PLPVideoGalleryModel.class, "resource").set(plpVideoGalleryModel,
				mockedResource);
		Mockito.when(mockedResource.getResourceResolver()).thenReturn(mockedResourceResolver);
		TagManager mockedTagManager =  Mockito.mock(TagManager.class);
		Mockito.when(mockedResourceResolver.adaptTo(TagManager.class)).thenReturn(mockedTagManager);
		MemberModifier.field(PLPVideoGalleryModel.class, "plpGalleryCategory").set(plpVideoGalleryModel, plpGalleryCategory);
		MemberModifier.field(PLPVideoGalleryModel.class, "plpVideoAssetPath").set(plpVideoGalleryModel, "plpVideoAssetPath");
		Tag galleryTag = Mockito.mock(Tag.class);
		Mockito.when(mockedTagManager.resolve(plpGalleryCategory[0])).thenReturn(galleryTag);
		Mockito.when(galleryTag.getTagID()).thenReturn("testTagId");
		List<VideoTile> videoByCategoryList = Mockito.mock(List.class);
		Mockito.when(videoGalleryService.getVideosByDate("plpVideoAssetPath", "testTagId", true)).thenReturn(videoByCategoryList);
		Mockito.when(videoByCategoryList.size()).thenReturn(11);
		plpVideoGalleryModel.getVideoByCategoryList();
	}
	
	@Test
	public void testGetterSetter(){
		String[] plpGalleryCategory = {};
		plpVideoGalleryModel.setPlpGalleryCategory(plpGalleryCategory );
		plpVideoGalleryModel.getPlpGalleryCategory();
	}

}
