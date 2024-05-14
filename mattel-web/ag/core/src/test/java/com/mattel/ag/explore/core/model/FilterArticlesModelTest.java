package com.mattel.ag.explore.core.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.ag.explore.core.pojos.TagsPojo;
import com.mattel.ag.explore.core.services.GetRelatedArticles;

public class FilterArticlesModelTest {

	FilterArticlesModel filterArticlesModel = new FilterArticlesModel();
	TagsPojo TagsPojo = new TagsPojo();
	Resource resource;
	ValueMap valueMap;
	String[] filterTags = { "foo", "crafts" };
	GetRelatedArticles relatedArticlesService;

	@Before
	public void setUp() {
		List<TagsPojo> tagsAuthored = new ArrayList<>();
		resource = Mockito.mock(Resource.class);
		valueMap = Mockito.mock(ValueMap.class);
		relatedArticlesService = Mockito.mock(GetRelatedArticles.class);
		Mockito.when(resource.getValueMap()).thenReturn(valueMap);
		TagsPojo.setTagName("abc");
		TagsPojo.setTagTitle("cde");
		tagsAuthored.add(TagsPojo);
		filterArticlesModel.setTagsAuthored(tagsAuthored);
		Mockito.when(valueMap.get("filterTags", String[].class)).thenReturn(filterTags);
		Mockito.when(relatedArticlesService.getTagRelatedData(filterTags)).thenReturn(filterArticlesModel.getTagsAuthored());
		filterArticlesModel.setResource(resource);
		filterArticlesModel.setRelatedArticlesService(relatedArticlesService);
		

	}

	@Test
	public void init() {
		filterArticlesModel.init();
	}

}
