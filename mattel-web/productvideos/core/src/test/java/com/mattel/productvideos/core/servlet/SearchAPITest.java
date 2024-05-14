package com.mattel.productvideos.core.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.productvideos.core.constants.Constants;

public class SearchAPITest {
    Map<String, Object> requestDetailsMap = new HashMap<>();
    SlingHttpServletRequest request; 
    SlingHttpServletResponse response;
    SearchAPI searchAPI;
    
    @Before
    public void setUp() {
        searchAPI = new SearchAPI();
        request = Mockito.mock(SlingHttpServletRequest.class);
        response = Mockito.mock(SlingHttpServletResponse.class);
        requestDetailsMap.put("limit", 20);
        requestDetailsMap.put("offset", 2);
        requestDetailsMap.put("keyword", "jurrasic-world");
        requestDetailsMap.put("filter", "jurrasic-world-filter");
        requestDetailsMap.put("sort", "N");
        requestDetailsMap.put("sortorder", "desc");
        requestDetailsMap.put("limit", 2);
        requestDetailsMap.put("path", "/content/mobile-apps/jurrasic-workd/test");
        
        
        
        
 
    }
    
    @Test
    public void test() throws ServletException, IOException{
        searchAPI.doGet(request, response);
    }
    
    @Test
    public void testWithQueryParams() throws ServletException, IOException{
        Mockito.when(request.getParameter(Constants.KEYWORD_PARAM)).thenReturn("jurrasic-world");
        Mockito.when(request.getParameter(Constants.OFFSET_FIELD)).thenReturn("2");
        Mockito.when(request.getParameter(Constants.LIMIT_FIELD)).thenReturn("20");
        Mockito.when(request.getParameter(Constants.PATH_FIELD)).thenReturn("/content/mobile-apps/jurrasic-workd/test");
        Mockito.when(request.getParameter(Constants.FILTER_FIELD)).thenReturn("jurrasic-world-filter");
        Mockito.when(request.getParameter(Constants.SORT_FIELD)).thenReturn("N");
        Mockito.when(request.getParameter(Constants.SORT_ORDER_FIELD)).thenReturn("desc");
        searchAPI.doGet(request, response);
    }
}
