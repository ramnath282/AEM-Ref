package com.mattel.global.core.servlets;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.mattel.global.core.services.AnalyticsDynamicDropdownService;

@PrepareForTest(DropdownParamValuePopulator.class)
public class DropdownParamValuePopulatorTest {
	DropdownParamValuePopulator dropdownParamValuePopulator;

	@Test
	public void testDoGet() throws Exception {
		dropdownParamValuePopulator = new DropdownParamValuePopulator();
		AnalyticsDynamicDropdownService analyticsDynamicDropdown = Mockito.mock(AnalyticsDynamicDropdownService.class); 
		dropdownParamValuePopulator.setAnalyticsDynamicDropdown(analyticsDynamicDropdown);
		SlingHttpServletRequest request = Mockito.mock(SlingHttpServletRequest.class);
		SlingHttpServletResponse response = Mockito.mock(SlingHttpServletResponse.class);
		PrintWriter printWritter = Mockito.mock(PrintWriter.class);
		RequestParameter requestParameter = Mockito.mock(RequestParameter.class);
		RequestParameter requestParameter1 = Mockito.mock(RequestParameter.class);
		Mockito.when(request.getRequestParameter("contentPath")).thenReturn(requestParameter);
		Mockito.when(requestParameter.toString()).thenReturn("item=/content/fisher-price/in/en/deactivation-page");
		Mockito.when(request.getRequestParameter("fieldType")).thenReturn(requestParameter1);
		Mockito.when(requestParameter1.toString()).thenReturn("page_type");
		Map<String, String> attrValueList = new HashMap<>();
		attrValueList.put("val", "val");
		attrValueList.put("val1", "val1");
		Mockito.when(requestParameter1.toString()).thenReturn("anyString");
		Mockito.when(analyticsDynamicDropdown.getAnalyticsPropertyValue(Mockito.anyString(), Mockito.anyString())).thenReturn(attrValueList);
		Mockito.when(response.getWriter()).thenReturn(printWritter);
		dropdownParamValuePopulator.doGet(request, response);
	}
}
