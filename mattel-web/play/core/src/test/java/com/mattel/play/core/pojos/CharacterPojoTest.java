package com.mattel.play.core.pojos;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class CharacterPojoTest {

	CharacterPojo characterPojo;
	
	@Before
	public void setUp() {

		characterPojo = new CharacterPojo();
		
	}
	@Test
	public void getCharSubtitle() {
		characterPojo.getCharSubtitle();
	}
	@Test
	public void setCharSubtitle() {
		characterPojo.setCharSubtitle("");
	}
	@Test
	public void getCharDesc() {
		characterPojo.getCharDesc();
	}
	@Test
	public void setCharDesc() {
		characterPojo.setCharDesc("");
	}
	@Test
	public void getCharExtraDesc() {
		characterPojo.getCharExtraDesc();
	}
	@Test
	public void setCharExtraDesc() {
		characterPojo.setCharExtraDesc("");
	}
	@Test
	public void getCharCategory() {
		characterPojo.getCharCategory();
	}
	@Test
	public void setCharCategory() {
		characterPojo.setCharCategory("");
	}
	@Test
	public void getCharImgAltText() {
		characterPojo.getCharImgAltText();
	}
	@Test
	public void setCharImgAltText() {
		characterPojo.setCharImgAltText("");
	}
	@Test
	public void getCharSecondayImage() {
		characterPojo.getCharSecondayImage();
	}
	@Test
	public void setCharSecondayImage() {
		characterPojo.setCharSecondayImage("");
	}
	@Test
	public void getCharSecondayImageAltText() {
		characterPojo.getCharSecondayImageAltText();
	}
	@Test
	public void setCharSecondayImageAltText() {
		characterPojo.setCharSecondayImageAltText("");
	}
	@Test
	public void getCharThumbImage() {
		characterPojo.getCharThumbImage();
	}
	@Test
	public void setCharThumbImage() {
		characterPojo.setCharThumbImage("");
	}
	@Test
	public void getCharThumbImageAltText() {
		characterPojo.getCharThumbImageAltText();
	}
	@Test
	public void setCharThumbImageAltText() {
		characterPojo.setCharThumbImageAltText("");
	}
	@Test
	public void getCharPath() {
		characterPojo.getCharPath();
	}
	@Test
	public void setCharPath() {
		characterPojo.setCharPath("");
	}
	@Test
	public void getCharTitle() {
		characterPojo.getCharTitle();
	}
	@Test
	public void setCharTitle() {
		characterPojo.setCharTitle("");
	}
	@Test
	public void getCharImage() {
		characterPojo.getCharImage();
	}
	@Test
	public void setCharImage() {
		characterPojo.setCharImage("");
	}
	@Test
	public void getCharTagId() {
		characterPojo.getCharTagId();
	}
	@Test
	public void setCharTagId() {
		characterPojo.setCharTagId("");
	}
	@Test
	public void getCharPageName() {
		characterPojo.getCharPageName();
	}
	@Test
	public void setCharPageName() {
		characterPojo.setCharPageName("");
	}
	@Test
	public void getLastModified() {
		characterPojo.getLastModified();
	}
	@Test
	public void setLastModified() {
		characterPojo.setLastModified(new Date());
		characterPojo.toString();
	}
	
	
	
}
