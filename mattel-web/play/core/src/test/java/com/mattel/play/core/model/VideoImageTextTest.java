package com.mattel.play.core.model;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.play.core.pojos.VideoTile;
import com.mattel.play.core.services.VideoGalleryService;

public class VideoImageTextTest {
	VideoImageText videoImageText = new VideoImageText();
	VideoGalleryService videoGalleryService;
	VideoTile videoDetails;
	Resource resource;
	ResourceResolver resolver;
	
	@Before
	public void setUp() {
		resource = Mockito.mock(Resource.class);
		resolver = Mockito.mock(ResourceResolver.class);
		videoGalleryService = Mockito.mock(VideoGalleryService.class);
		videoDetails = Mockito.mock(VideoTile.class);
		
		videoImageText.setResource(resource);
		videoImageText.setImageFileReferenceMainImageDesktop("");
		videoImageText.setVideoGalleryService(videoGalleryService);
		
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource("")).thenReturn(resource);
		Mockito.when(videoGalleryService.prepareVideoTile(resource)).thenReturn(videoDetails);
		Mockito.when(videoDetails.getVideoId()).thenReturn("");
		
	}
	@Test
	public void init() {
		videoImageText.init();
	}
	@Test
	public void getVideoDetails() {
		videoImageText.getVideoDetails();
	}
	@Test
	public void setVideoDetails() {
		videoImageText.setVideoDetails(videoDetails);
	}
	@Test
	public void getOoyalaId() {
		videoImageText.getOoyalaId();
	}
	@Test
	public void setOoyalaId() {
		videoImageText.setOoyalaId("");
	}
}
