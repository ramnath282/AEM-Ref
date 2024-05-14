package com.mattel.play.core.pojos;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ProductTilePojoTest {

	ProductTilePojo productTilePojo;
	@Before
	public void setUp() {
		productTilePojo = new ProductTilePojo();
	}
	@Test
	public void getProductId() {
		productTilePojo.getProductId();
	}
	@Test
	public void setProductId() {
		productTilePojo.setProductId("");
	}
	@Test
	public void getAge() {
		productTilePojo.getAge();
	}
	@Test
	public void setage() {
		productTilePojo.setage("");
	}
	@Test
	public void getDescription() {
		productTilePojo.getDescription();
	}
	@Test
	public void setDescription() {
		productTilePojo.setDescription("");
	}
	@Test
	public void getProductImages() {
		productTilePojo.getProductImages();
	}
	@Test
	public void setProductImages() {
		productTilePojo.setProductImages(new ArrayList<>());
	}
	@Test
	public void getProductCategory() {
		productTilePojo.getProductCategory();
	}
	@Test
	public void setProductCategory() {
		productTilePojo.setProductCategory("");
	}
	@Test
	public void getProductPageName() {
		productTilePojo.getProductPageName();
	}
	@Test
	public void setProductPageName() {
		productTilePojo.setProductPageName("");
	}
	@Test
	public void getProductPagePath() {
		productTilePojo.getProductPagePath();
	}
	@Test
	public void setProductPagePath() {
		productTilePojo.setProductPagePath("");
	}
	@Test
	public void getProductThumbnail() {
		productTilePojo.getProductThumbnail();
	}
	@Test
	public void setProductThumbnail() {
		productTilePojo.setProductThumbnail("");
	}
	@Test
	public void getThumbnailAltTxt() {
		productTilePojo.getThumbnailAltTxt();
	}
	@Test
	public void setThumbnailAltTxt() {
		productTilePojo.setThumbnailAltTxt("");
	}
	@Test
	public void getProductTitle() {
		productTilePojo.getProductTitle();
	}
	@Test
	public void setProductTitle() {
		productTilePojo.setProductTitle("");
	}
	@Test
	public void getProductTags() {
		productTilePojo.getProductTags();
	}
	@Test
	public void setProductTags() {
		productTilePojo.setProductTags(new ArrayList<>());
	}
	@Test
	public void getProductThumbnailHover() {
		productTilePojo.getProductThumbnailHover();
	}
	@Test
	public void setProductThumbnailHover() {
		productTilePojo.setProductThumbnailHover("");
	}
	@Test
	public void getThumbnailHoverAltTxt() {
		productTilePojo.getThumbnailHoverAltTxt();
	}
	@Test
	public void setThumbnailHoverAltTxt() {
		productTilePojo.setThumbnailHoverAltTxt("");
	}
	@Test
	public void isSelected() {
		productTilePojo.isSelected();
	}
	@Test
	public void setSelected() {
		productTilePojo.setSelected(true);
		productTilePojo.toString();
	}
	@Test
	public void getAlwaysEnglish() {
		productTilePojo.getAlwaysEnglish();
	}
	@Test
	public void setAlwaysEnglish() {
		productTilePojo.setAlwaysEnglish("");
	}
}
