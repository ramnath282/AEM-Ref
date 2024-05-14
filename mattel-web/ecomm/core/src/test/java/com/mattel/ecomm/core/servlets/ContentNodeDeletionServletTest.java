package com.mattel.ecomm.core.servlets;

import com.mattel.ecomm.core.services.DeleteNodeServiceImpl;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.jcr.RepositoryException;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@RunWith(PowerMockRunner.class)
public class ContentNodeDeletionServletTest {

    @Mock
    SlingHttpServletRequest request;
    @Mock
    SlingHttpServletResponse response;
    @Mock
    ResourceResolver resolver;
    @Mock
    Resource resource;
    @Mock
    DeleteNodeServiceImpl deleteNodeServiceImpl;
    @Mock
    PrintWriter printWriter;
    
    ContentNodeDeletionServlet contentNodeDeletionServlet;
    
    @Mock
    Map<String, Object> serviceResponse;
    
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws RepositoryException, IOException {
    contentNodeDeletionServlet = new ContentNodeDeletionServlet();
    contentNodeDeletionServlet.setDeleteNodeServiceImpl(deleteNodeServiceImpl);
    Mockito.when(request.getParameter("deleteprop")).thenReturn("true");
    Mockito.when(request.getParameter("contentpath")).thenReturn("contentpath");
    Mockito.when(deleteNodeServiceImpl.deleteNodes(Mockito.anyString())).thenReturn(serviceResponse);
    Mockito.when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    public void doGetTestWithAuthValAsFalse() throws IOException, ServletException {
        Mockito.when(request.getParameter("deleteprop")).thenReturn("false");
        contentNodeDeletionServlet.doGet(request, response);
    }
}
