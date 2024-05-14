package com.mattel.play.core.model;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.mattel.play.core.constants.Constants;
import com.mattel.play.core.helper.PlayHelper;
import com.mattel.play.core.pojos.VideoTile;
import com.mattel.play.core.services.VideoGalleryService;
import com.mattel.play.core.utils.PlaySiteConfigurationUtils;
import com.mattel.play.core.utils.PropertyReaderUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PlayHelper.class, PropertyReaderUtils.class, PlaySiteConfigurationUtils.class})
public class VideoGalleryModelTest {

	VideoGalleryModel videoGalleryModel;
	List<VideoTile> videoListManual;
	List<VideoTile> videoListLanding;
	List<VideoTile> videoListByCategory;
	List<VideoTile> videoListByDate;
	VideoTile videoTile = new VideoTile();
	String videoUrl = "";
	Resource resource;
	Node videoGalleryNode;
	VideoGalleryService videoGalleryService;
	String[] galleryCategory;
	PropertyReaderUtils propertyReaderUtils;
	PlayHelper playHelper;
	Constants constants;
	ResourceResolver resourceResolver;
	TagManager tagManager;
	Tag galleryTag;
	NodeIterator iterator;
	Property property;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws RepositoryException {
		
		videoGalleryModel = new VideoGalleryModel();
		PowerMockito.mockStatic(PlayHelper.class);
		PowerMockito.mockStatic(PropertyReaderUtils.class);
		PowerMockito.mockStatic(PlaySiteConfigurationUtils.class);
		resource = Mockito.mock(Resource.class);
		resourceResolver = Mockito.mock(ResourceResolver.class);
		videoGalleryNode = Mockito.mock(Node.class);
		tagManager = Mockito.mock(TagManager.class);
		galleryTag = Mockito.mock(Tag.class);
		iterator = Mockito.mock(NodeIterator.class);
		videoGalleryService = Mockito.mock(VideoGalleryService.class);
		property = Mockito.mock(Property.class);
		videoGalleryModel.setResource(resource);
		videoGalleryModel.setVideoGalleryNode(videoGalleryNode);
		videoGalleryModel.setVideoGalleryService(videoGalleryService);
		galleryCategory = new String[5]; 
		galleryCategory[0] = "";
		videoGalleryModel.setGalleryCategory(galleryCategory);
		videoTile.setAlwaysEnglish("");
		videoTile.setColLayout("");
		videoTile.setVideoThumbnail("home/content/fisherprice");
		videoListManual = new ArrayList<>();
		videoListManual.add(videoTile);
		videoListManual.add(videoTile);
		videoGalleryModel.setVideoListManual(videoListManual);
		Mockito.when(PropertyReaderUtils.getPlayPath()).thenReturn(videoUrl);
		Mockito.when(PlayHelper.getBrandName(resource.getPath())).thenReturn(videoUrl);
		Mockito.when(PlayHelper.getRelativePath(resource.getPath(),resource)).thenReturn(videoUrl);
		Mockito.when(PropertyReaderUtils.getHomePath()).thenReturn(videoUrl);
		Mockito.when(PropertyReaderUtils.getVideoPath()).thenReturn(videoUrl);
		Mockito.when(PropertyReaderUtils.getPlayPath() + PlayHelper.getBrandName(resource.getPath()) + PlayHelper.getRelativePath(resource.getPath(),resource) + PropertyReaderUtils.getHomePath()+ PropertyReaderUtils.getVideoPath() + ".html" + "/video/").thenReturn(videoUrl);
		Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(resource.getResourceResolver().adaptTo(TagManager.class)).thenReturn(tagManager);
		Mockito.when(tagManager.resolve(galleryCategory[0])).thenReturn(galleryTag);
		Mockito.when(resource.getPath()).thenReturn("/language-masters/");
		Mockito.when(resourceResolver.getResource("nullnull/jcr:content/root/")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(videoGalleryNode);
		Mockito.when(videoGalleryNode.getNodes()).thenReturn(iterator);
		Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(iterator.nextNode()).thenReturn(videoGalleryNode);
		Mockito.when(videoGalleryNode.getProperty("sling:resourceType")).thenReturn(property);
		Mockito.when(videoGalleryNode.getProperty("sling:resourceType").getString()).thenReturn("videoDetail");
		Mockito.when(PlayHelper.getBrandName(resource.getPath())).thenReturn("");
		Mockito.when(PlaySiteConfigurationUtils.getFisherPriceKidsBrandName()).thenReturn("");
		Mockito.when(PropertyReaderUtils.getPlayDamPath()).thenReturn("content");
		Mockito.when(PropertyReaderUtils.getPlayPath()).thenReturn("fisherprice");
		Mockito.when(PlayHelper.checkLink("content/fisherprice",resource)).thenReturn("");
		Mockito.when(PlayHelper.fetchLocaleFromDam("home/content/fisherprice", resource)).thenReturn("");
		Mockito.when(PlayHelper.getRelativePath(resource.getPath(), resource)).thenReturn("");
		Mockito.when(resourceResolver.getResource("fisherprice/jcr:content/root/")).thenReturn(resource);
		//Mockito.when(videoGalleryNode.getProperty("sling:resourceType").getString().contains("videoDetail")).thenReturn(true);
		
	}
	
	@Test
	public void init() {
		videoGalleryModel.init();		
	}
	@Test
	public void getVideoUrl() {
		videoGalleryModel.getVideoUrl();
	}
	
	@Test
	public void getVideoListManual() {
		videoGalleryModel.getVideoListManual();
	}
	
	@Test
	public void getVideoListLanding() {
		videoGalleryModel.getVideoListLanding();
	}
	
	@Test
	public void getVideoListByCategory() {
		videoGalleryModel.getVideoListByCategory();
	}
	
	@Test
	public void getVideoListByDate() {
		videoGalleryModel.getVideoListByDate();
	}
	@Test
	public void checkVideoUrl() {
		videoGalleryModel.checkVideoUrl(videoListManual, resource);
	}
}
