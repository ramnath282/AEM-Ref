package com.mattel.play.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.play.core.pojos.DownloadImageGalleryPojo;
import com.mattel.play.core.pojos.InterstitialPojo;
import com.mattel.play.core.services.MultifieldReader;

public class DownloadImageGalleryModelTest {

	DownloadImageGalleryModel downloadImageGalleryModel;
	MultifieldReader multifieldReader;
	Node downloadImage;
	Resource resource;
	List<DownloadImageGalleryPojo> downloadImageGalleryList;
	List<InterstitialPojo> interstitialDetailsList;
	Map<String, ValueMap> multifieldProperty;
	ValueMap valueMap;
	DownloadImageGalleryPojo downloadfieldList;
	Map.Entry<String, ValueMap> entry;
	ResourceResolver res;
	
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws RepositoryException {
		downloadImageGalleryModel = new DownloadImageGalleryModel();
		multifieldReader = Mockito.mock(MultifieldReader.class);
		downloadImage = Mockito.mock(Node.class);
		resource = Mockito.mock(Resource.class);
		downloadImageGalleryList = new ArrayList<>();
		interstitialDetailsList = new ArrayList<>();
		multifieldProperty = new HashMap <>();
		valueMap = Mockito.mock(ValueMap.class);
		downloadfieldList = new DownloadImageGalleryPojo();
		res = Mockito.mock(ResourceResolver.class);
		downloadImageGalleryModel.setDownloadImage(downloadImage);
		downloadImageGalleryModel.setMultifieldReader(multifieldReader);
		downloadImageGalleryModel.setResource(resource);
		multifieldProperty.put("thumbnailImage", valueMap);
		multifieldProperty.put("thumbnailImage", valueMap);
		multifieldProperty.put("thumbnailImage", valueMap);
		downloadfieldList.setAltTextThumbnail("altTexTthumbnail");
		downloadfieldList.setAlwaysEnglish("alwaysEnglish");
		downloadfieldList.setDownloadCtaLabel("downloadCtaLabel");
		downloadfieldList.setDownloadCtaLink("downloadCtaLink");
		downloadfieldList.setInterstitialDetailsList(new ArrayList<>());
		downloadfieldList.setOpenCtaLinksIn("openCtaLinksIn");
		downloadfieldList.setThumbnailDescription("thumbnailDescription");
		downloadfieldList.setThumbnailImage("thumbnailImage");
		downloadfieldList.setThumbnailTitle("thumbnailTitle");
		downloadImageGalleryList.add(downloadfieldList);
		entry = Mockito.mock(Map.Entry.class);
		entry.setValue(valueMap);
		
		Mockito.when(multifieldReader.propertyReader(downloadImage)).thenReturn(multifieldProperty);
		Mockito.when(entry.getValue()).thenReturn(valueMap);
		Mockito.when(entry.getValue().get("openCtaLinksIn", String.class)).thenReturn("interstitial");
		Mockito.when(downloadImage.hasNodes()).thenReturn(true);
		Mockito.when(resource.getResourceResolver()).thenReturn(res);
		Mockito.when(downloadImage.getPath()).thenReturn("");
		Mockito.when(res.getResource("/thumbnailImage")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(downloadImage);
		Mockito.when(res.getResource("/interstitialDetailList")).thenReturn(resource);
		
		
	}
	
	@Test
	public void init() {
		downloadImageGalleryModel.init();
	}
	@Test
	public void setDownloadImageGalleryList() {
		downloadImageGalleryModel.setDownloadImageGalleryList(downloadImageGalleryList);
	}
	@Test
	public void setInterstitialDetailsList() {
		downloadImageGalleryModel.setInterstitialDetailsList(interstitialDetailsList);
	}
	@Test
	public void getDownloadImageGalleryList() {
		downloadImageGalleryModel.getDownloadImageGalleryList();
	}
	
}
