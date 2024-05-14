package com.mattel.play.core.pojos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DownloadImageGalleryPojoTest {

	
	DownloadImageGalleryPojo downloadImageGalleryPojo;
	List<InterstitialPojo> interstitialDetailsList;
	@Before
	public void setUp() {

		downloadImageGalleryPojo = new DownloadImageGalleryPojo();		
		interstitialDetailsList = new ArrayList<>();
	}
	
	@Test
	public void getInterstitialDetailsList() {
		downloadImageGalleryPojo.getInterstitialDetailsList();
	}
	@Test
	public void setInterstitialDetailsList() {
		downloadImageGalleryPojo.setInterstitialDetailsList(interstitialDetailsList);
	}
	@Test
	public void getThumbnailImage() {
		downloadImageGalleryPojo.getThumbnailImage();
	}
	@Test
	public void setThumbnailImage() {
		downloadImageGalleryPojo.setThumbnailImage("");
	}
	@Test
	public void getThumbnailTitle() {
		downloadImageGalleryPojo.getThumbnailTitle();
	}
	@Test
	public void setThumbnailTitle() {
		downloadImageGalleryPojo.setThumbnailTitle("");
	}
	@Test
	public void getThumbnailDescription() {
		downloadImageGalleryPojo.getThumbnailDescription();
	}
	@Test
	public void setThumbnailDescription() {
		downloadImageGalleryPojo.setThumbnailDescription("");
	}
	@Test
	public void getAltTextThumbnail() {
		downloadImageGalleryPojo.getAltTextThumbnail();
	}
	@Test
	public void setAltTextThumbnail() {
		downloadImageGalleryPojo.setAltTextThumbnail("");
	}
	@Test
	public void getOpenCtaLinksIn() {
		downloadImageGalleryPojo.getOpenCtaLinksIn();
	}
	@Test
	public void setOpenCtaLinksIn() {
		downloadImageGalleryPojo.setOpenCtaLinksIn("");
	}
	@Test
	public void getDownloadCtaLabel() {
		downloadImageGalleryPojo.getDownloadCtaLabel();
	}
	@Test
	public void setDownloadCtaLabel() {
		downloadImageGalleryPojo.setDownloadCtaLabel("");
	}
	@Test
	public void getDownloadCtaLink() {
		downloadImageGalleryPojo.getDownloadCtaLink();
	}
	@Test
	public void setDownloadCtaLink() {
		downloadImageGalleryPojo.setDownloadCtaLink("");
	}
	@Test
	public void getAlwaysEnglish() {
		downloadImageGalleryPojo.getAlwaysEnglish();
	}
	@Test
	public void setAlwaysEnglish() {
		downloadImageGalleryPojo.setAlwaysEnglish("");
	}
	@Test
	public void testSetEnableDownloadFile() {
		downloadImageGalleryPojo.setEnableDownloadFile("enableDownloadFile");
	}
	@Test
	public void testGetEnableDownloadFile() {
		downloadImageGalleryPojo.getEnableDownloadFile();
	}
	
}
