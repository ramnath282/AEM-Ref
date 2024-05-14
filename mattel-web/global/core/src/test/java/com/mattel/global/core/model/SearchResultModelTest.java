package com.mattel.global.core.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.global.core.utils.GlobalPropertyReaderUtils;

@RunWith(PowerMockRunner.class)
public class SearchResultModelTest {	
	
	@InjectMocks
	private SearchResultModel searchResultModel;
	
	@Mock
	GlobalPropertyReaderUtils globalPropertyReaderUtils;
	
	@Test
	public void testToVerifyValidSnpUrlReturnFromModel() throws IllegalArgumentException, IllegalAccessException {
		MemberModifier.field(SearchResultModel.class, "globalPropertyReaderUtils").set(searchResultModel,
				globalPropertyReaderUtils);
		MemberModifier.field(SearchResultModel.class, "siteName").set(searchResultModel, "corp_en");
		Mockito.when(globalPropertyReaderUtils.getSnpUrl("corp_en"))
				.thenReturn("//sp10056c97.guided.ss-omtrdc.net/");
		searchResultModel.init();
		assertEquals("//sp10056c97.guided.ss-omtrdc.net/", searchResultModel.getSnpUrl());
	}

	@Test
	public void testToVerifyIfGlobalPropertyReaderUtilIsNull() throws IllegalArgumentException, IllegalAccessException {
		MemberModifier.field(SearchResultModel.class, "globalPropertyReaderUtils").set(searchResultModel, null);
		MemberModifier.field(SearchResultModel.class, "siteName").set(searchResultModel, "corp_en");
		Mockito.when(globalPropertyReaderUtils.getSnpUrl("corp_en"))
				.thenReturn("//sp10056c97.guided.ss-omtrdc.net/");
		searchResultModel.init();
		assertNotEquals("//sp10056c97.guided.ss-omtrdc.net/", searchResultModel.getSnpUrl());
	}	
}
