package com.mattel.play.core.pojos;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class VideoTileTest {

	VideoTile videoTile;
	
	@Before
	public void setUp() {

		videoTile = new VideoTile();
		
	}
	
	@Test
	public void getVideoThumbnail() {
		videoTile.getVideoThumbnail();
	}
	@Test
	public void setVideoThumbnail() {
		videoTile.setVideoThumbnail("");
	}
	@Test
	public void getThumbnailAltTxt() {
		videoTile.getThumbnailAltTxt();
	}
	@Test
	public void setThumbnailAltTxt() {
		videoTile.setThumbnailAltTxt("");
	}
	@Test
	public void getVideoTitle() {
		videoTile.getVideoTitle();
	}
	@Test
	public void setVideoTitle() {
		videoTile.setVideoTitle("");
	}
	@Test
	public void getVideoDesc() {
		videoTile.getVideoDesc();
	}
	@Test
	public void setVideoDesc() {
		videoTile.setVideoDesc("");
	}
	@Test
	public void getVideoTags() {
		videoTile.getVideoTags();
	}
	@Test
	public void setVideoTags() {
		videoTile.setVideoTags(new ArrayList<>());
	}
	@Test
	public void getSeotitle() {
		videoTile.getSeotitle();
	}
	@Test
	public void setSeotitle() {
		videoTile.setSeotitle("");
	}
	@Test
	public void getSeoUrl() {
		videoTile.getSeoUrl();
	}	
	@Test
	public void setSeoUrl() {
		videoTile.setSeoUrl("");
	}
	@Test
	public void getMetaKeywords() {
		videoTile.getMetaKeywords();
	}
	@Test
	public void setMetaKeywords() {
		videoTile.setMetaKeywords("");
	}
	@Test
	public void getMetaDesc() {
		videoTile.getMetaDesc();
	}
	@Test
	public void setMetaDesc() {
		videoTile.setMetaDesc("");
	}
	@Test
	public void getVideoCategory() {
		videoTile.getVideoCategory();
	}
	@Test
	public void setVideoCategory() {
		videoTile.setVideoCategory("");
	}
	@Test
	public void getVideoId() {
		videoTile.getVideoId();
	}
	@Test
	public void setVideoId() {
		videoTile.setVideoId("");
	}
	@Test
	public void getTitleAlign() {
		videoTile.getTitleAlign();
	}
	@Test
	public void setTitleAlign() {
		videoTile.setTitleAlign("");
	}
	@Test
	public void getColLayout() {
		videoTile.getColLayout();
	}
	@Test
	public void setColLayout() {
		videoTile.setColLayout("");
	}
	@Test
	public void isSelected() {
		videoTile.isSelected();
	}
	@Test
	public void setSelected() {
		videoTile.setSelected(false);
	}	
	@Test
	public void getVideoName() {
		videoTile.getVideoName();
	}
	@Test
	public void setVideoName() {
		videoTile.setVideoName("");
	}
	@Test
	public void getAlwaysEnglish() {
		videoTile.getAlwaysEnglish();
	}
	@Test
	public void setAlwaysEnglish() {
		videoTile.setAlwaysEnglish("");
		videoTile.toString();
	}
	
}
