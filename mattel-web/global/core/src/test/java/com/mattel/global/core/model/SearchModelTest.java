package com.mattel.global.core.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.global.core.utils.GlobalPropertyReaderUtils;
import com.mattel.global.core.utils.PropertyReaderUtils;

@RunWith(PowerMockRunner.class)
public class SearchModelTest {
	
	@InjectMocks
	private SearchModel searchModel;
	
	@Mock
	GlobalPropertyReaderUtils globalPropertyReaderUtils;
	
	@Mock
	private PropertyReaderUtils propertyReaderUtils;
	
	@Test
	public void testSearchModelProperties() throws IllegalArgumentException, IllegalAccessException{
		MemberModifier.field(SearchModel.class, "globalPropertyReaderUtils").set(searchModel,
				globalPropertyReaderUtils);
		MemberModifier.field(SearchModel.class, "placeholderText").set(searchModel, "Search");
		MemberModifier.field(SearchModel.class, "searchResultPageLink").set(searchModel, "/content/mattel/corporate/home/searchResult");
		MemberModifier.field(SearchModel.class, "characterLimit").set(searchModel, 2);
		MemberModifier.field(SearchModel.class, "suggestionLimit").set(searchModel, 5);
		MemberModifier.field(SearchModel.class, "siteName").set(searchModel, "corp_en");		
		Mockito.when(globalPropertyReaderUtils.getTypeAheadUrl("corp_en"))
				.thenReturn("//sp10056c97.guided.ss-omtrdc.net/?search=corporate&q=news");
		Mockito.when(propertyReaderUtils.getSiteDomainMapping()).thenReturn(Mockito.anyMap());
		searchModel.init();
		assertEquals("//sp10056c97.guided.ss-omtrdc.net/?search=corporate&q=news", searchModel.getTypeAheadUrl());
		assertEquals("Search", searchModel.getPlaceholderText());
		assertEquals((long)2, (long)searchModel.getCharacterLimit());
		assertEquals((long)5, (long)searchModel.getSuggestionLimit());
	}
	
}
