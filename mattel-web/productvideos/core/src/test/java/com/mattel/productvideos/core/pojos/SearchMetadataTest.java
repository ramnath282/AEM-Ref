package com.mattel.productvideos.core.pojos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SearchMetadataTest {

	SearchMetadata searchMetadata;

	Result result;

	@Before
	public void setup() {
		result = new Result();
		result.setDescription("description");
		result.setFeaturedVideo("featuredVideo");
		result.setImageURL("imageURL");
		result.setPublishDate("publishDate");
		result.setTitle("title");
		result.setVideoDuration("videoDuration");
		result.setVideoId("videoId");
		result.setVideoURL("videoURL");

		List<String> facets = new ArrayList<>();
		searchMetadata = new SearchMetadata();
		searchMetadata.setTitle("title");
		searchMetadata.setQuery("query");
		searchMetadata.setPageSize("pageSize");
		searchMetadata.setNumberOfHits("numberOfHits");
		searchMetadata.setPageNumber("pageNumber");
		searchMetadata.setFacets(facets);

		List<Result> results = new ArrayList<Result>();
		results.add(result);

		searchMetadata.setResults(results);
	}

	@Test
	public void testGetters() {
		Assert.assertEquals(searchMetadata.getTitle(), "title");
		Assert.assertEquals(searchMetadata.getNumberOfHits(), "numberOfHits");
		Assert.assertEquals(searchMetadata.getPageNumber(), "pageNumber");
		Assert.assertEquals(searchMetadata.getPageSize(), "pageSize");
		Assert.assertEquals(searchMetadata.getQuery(), "query");
		Assert.assertNotNull(searchMetadata.getFacets());
		Assert.assertNotNull(searchMetadata.getResults());

		Assert.assertNotNull(result.getDescription());
		Assert.assertNotNull(result.getFeaturedVideo());
		Assert.assertNotNull(result.getImageURL());
		Assert.assertNotNull(result.getPublishDate());
		Assert.assertNotNull(result.getTitle());
		Assert.assertNotNull(result.getVideoDuration());
		Assert.assertNotNull(result.getVideoId());
		Assert.assertNotNull(result.getVideoURL());
	}
}
