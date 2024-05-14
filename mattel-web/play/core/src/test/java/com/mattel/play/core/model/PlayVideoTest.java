package com.mattel.play.core.model;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.day.cq.dam.api.Asset;
import com.mattel.play.core.pojos.VideoTile;
import com.mattel.play.core.services.VideoGalleryService;

public class PlayVideoTest {
	PlayVideo playVideo;
	Resource resource;
	VideoGalleryService videoGalleryService;
	ResourceResolver resolver;
	Asset videoAsset = null;
	VideoTile videoDetails;
	
	@Before
	public void setUp() {
		
		playVideo = new PlayVideo();
		resource = Mockito.mock(Resource.class);
		videoGalleryService = Mockito.mock(VideoGalleryService.class);
		resolver = Mockito.mock(ResourceResolver.class);
		videoAsset = Mockito.mock(Asset.class);
		videoDetails = Mockito.mock(VideoTile.class);
		playVideo.setResource(resource);
		playVideo.setVideoGalleryService(videoGalleryService);
		playVideo.setVideoDetails(videoDetails);
		playVideo.setVideoThumbnail("");
		
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource("")).thenReturn(resource);
		Mockito.when(videoGalleryService.prepareVideoTile(resource)).thenReturn(videoDetails);
		
	}
	
	@Test
	public void init() {
		playVideo.init();
	}
	@Test
	public void getVideoDetails() {
		playVideo.getVideoDetails();
	}
}
