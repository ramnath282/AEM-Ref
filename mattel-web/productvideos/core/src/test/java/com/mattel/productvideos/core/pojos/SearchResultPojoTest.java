package com.mattel.productvideos.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SearchResultPojoTest {

    SearchResultPojo searchResultPojo;

	@Before
	public void setup() {
	    searchResultPojo = new SearchResultPojo();
	    searchResultPojo.setAssetName("assetName");
	    searchResultPojo.setCqtag("cqtag");
	    searchResultPojo.setDescValue("descValue");
	    searchResultPojo.setPublishedDate("publishedDate");
	    searchResultPojo.setScene7ImageUrl("scene7ImageUrl");
	    searchResultPojo.setScene7Url("scene7Url");
	    searchResultPojo.setThumbnailValue("thumbnailValue");
	    searchResultPojo.setVideoDuration("videoDuration");
	    searchResultPojo.setVideoId("videoId");
	    searchResultPojo.setVideoValue("videoValue");
	}

	@Test
	public void testGetter() {
		Assert.assertEquals(searchResultPojo.getAssetName(), "assetName");
		Assert.assertEquals(searchResultPojo.getCqtag(), "cqtag");
		Assert.assertEquals(searchResultPojo.getDescValue(), "descValue");
		Assert.assertEquals(searchResultPojo.getPublishedDate(), "publishedDate");
		Assert.assertEquals(searchResultPojo.getScene7ImageUrl(), "scene7ImageUrl");
		Assert.assertEquals(searchResultPojo.getScene7Url(), "scene7Url");
		Assert.assertEquals(searchResultPojo.getThumbnailValue(), "thumbnailValue");
		Assert.assertEquals(searchResultPojo.getVideoDuration(), "videoDuration");
		Assert.assertEquals(searchResultPojo.getVideoId(), "videoId");
		Assert.assertEquals(searchResultPojo.getVideoValue(), "videoValue");
	}

}
