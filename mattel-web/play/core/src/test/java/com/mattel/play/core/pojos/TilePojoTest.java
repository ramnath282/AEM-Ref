package com.mattel.play.core.pojos;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TilePojoTest {
	TilePojo tilePojo;
	
	@Before
	public void setUp() {
		tilePojo = new TilePojo();
	}
	
	@Test
	public void getTileCategory() {
		tilePojo.getTileCategory();
	}
	@Test
	public void setTileCategory() {
		tilePojo.setTileCategory("");
	}
	@Test
	public void getTilePageName() {
		tilePojo.getTilePageName();
	}
	@Test
	public void setTilePageName() {
		tilePojo.setTilePageName("");
	}
	@Test
	public void getTilePath() {
		tilePojo.getTilePath();
	}
	@Test
	public void setTilePath() {
		tilePojo.setTilePath("");
	}
	@Test
	public void getTileImage() {
		tilePojo.getTileImage();
	}
	@Test
	public void setTileImage() {
		tilePojo.setTileImage("");
	}
	@Test
	public void getDescLineOne() {
		tilePojo.getDescLineOne();
	}
	@Test
	public void setDescLineOne() {
		tilePojo.setDescLineOne("");
	}
	@Test
	public void getDescLineTwo() {
		tilePojo.getDescLineTwo();
	}
	@Test
	public void setDescLineTwo() {
		tilePojo.setDescLineTwo("");
	}
	@Test
	public void getTileImgAltText() {
		tilePojo.getTileImgAltText();
	}
	@Test
	public void setTileImgAltText() {
		tilePojo.setTileImgAltText("");
	}
	@Test
	public void getTileTitle() {
		tilePojo.getTileTitle();
	}
	@Test
	public void setTileTitle() {
		tilePojo.setTileTitle("");
	}
	@Test
	public void getTileTags() {
		tilePojo.getTileTags();
	}
	@Test
	public void setTileTags() {
		tilePojo.setTileTags(new ArrayList<>());
	}
	@Test
	public void getHoverOverImg() {
		tilePojo.getHoverOverImg();
	}
	@Test
	public void setHoverOverImg() {
		tilePojo.setHoverOverImg("");
	}
	@Test
	public void getHoverOverImgAlt() {
		tilePojo.getHoverOverImgAlt();
	}
	@Test
	public void setHoverOverImgAlt() {
		tilePojo.setHoverOverImgAlt("");
	}
	@Test
	public void getAlwaysEnglish() {
		tilePojo.getAlwaysEnglish();
	}
	@Test
	public void setAlwaysEnglish() {
		tilePojo.setAlwaysEnglish("");
		tilePojo.toString();
	}
	
	
}
