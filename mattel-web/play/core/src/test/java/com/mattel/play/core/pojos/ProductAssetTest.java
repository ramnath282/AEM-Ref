package com.mattel.play.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class ProductAssetTest {
	
	ProductAsset productAsset;
	@Before
	public void setUp () {
		productAsset = new ProductAsset();
	}
	@Test
	public void getProductImage() {
		productAsset.getProductImage();
	}
	@Test
	public void setProductImage() {
		productAsset.setProductImage("");
	}
	@Test
	public void getProductAltText() {
		productAsset.getProductAltText();
	}
	@Test
	public void setProductAltText() {
		productAsset.setProductAltText("");
	}
	@Test
	public void isVideo() {
		productAsset.isVideo();
	}
	@Test
	public void setVideo() {
		productAsset.setVideo(true);
	}
	@Test
	public void getOoyalaId() {
		productAsset.getOoyalaId();
	}
	@Test
	public void setOoyalaId() {
		productAsset.setOoyalaId("");
	}
}
