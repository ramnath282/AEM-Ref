package com.mattel.global.core.servlets;

import com.mattel.global.core.services.GlobalErrorMessages;

import java.io.PrintWriter;
import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public class ErrorMessagesServletTest {
  ErrorMessagesServlet errorMessagesServlet;

  @SuppressWarnings("unchecked")
  @Test
  public void testDoGet() throws Exception {
    errorMessagesServlet = new ErrorMessagesServlet();
    final SlingHttpServletRequest request = Mockito.mock(SlingHttpServletRequest.class);
    final SlingHttpServletResponse response = Mockito.mock(SlingHttpServletResponse.class);
    final ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
    final GlobalErrorMessages globalErrorMessages = Mockito.mock(GlobalErrorMessages.class);
    final Session session = Mockito.mock(Session.class);
    final Resource resource = Mockito.mock(Resource.class);
    final Iterable<Resource> resources = Mockito.mock(Iterable.class);
    final Iterator<Resource> resourceIterator = Mockito.mock(Iterator.class);
    final NodeIterator itemItr = Mockito.mock(NodeIterator.class);
    final Property prop1 = Mockito.mock(Property.class);
    final Property prop2 = Mockito.mock(Property.class);
    final Node multifieldNode = Mockito.mock(Node.class);
    final Node itemNode = Mockito.mock(Node.class);
    final RequestPathInfo requestPathInfo = Mockito.mock(RequestPathInfo.class);
    final RequestParameter requestParameter = Mockito.mock(RequestParameter.class);
    final ValueMap valueMap = Mockito.mock(ValueMap.class);
    final PrintWriter printWriter = Mockito.mock(PrintWriter.class);
    Mockito.when(globalErrorMessages.getErrorMessagePath()).thenReturn("path of error message");
    Mockito.when(request.getRequestParameter(ArgumentMatchers.anyString()))
        .thenReturn(requestParameter);
    Mockito.when(requestParameter.getString(ArgumentMatchers.anyString()))
        .thenReturn("Locale value");
    Mockito.when(requestParameter.getString()).thenReturn("parameter");
    Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
    Mockito.when(requestPathInfo.getResourcePath()).thenReturn("http://localhost");
    Mockito.when(request.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(resourceResolver.getResource(ArgumentMatchers.anyString())).thenReturn(resource);
    Mockito.when(request.getResourceResolver().adaptTo(Session.class)).thenReturn(session);
    Mockito.when(resource.getValueMap()).thenReturn(valueMap);
    Mockito.when(resource.getValueMap().get(ArgumentMatchers.anyString(),
        ArgumentMatchers.any(String.class))).thenReturn("Error Message");
    Mockito.when(resource.getChildren()).thenReturn(resources);
    Mockito.when(resources.iterator()).thenReturn(resourceIterator);
    Mockito.when(resources.iterator().hasNext()).thenReturn(Boolean.TRUE);
    Mockito.when(resourceIterator.next()).thenReturn(resource);
    Mockito.when(resource.getPath()).thenReturn("/content/dam");
    Mockito.when(session.getNode(ArgumentMatchers.anyString())).thenReturn(multifieldNode);
    Mockito.when(multifieldNode.getPath()).thenReturn("/content/dam");
    Mockito.when(multifieldNode.hasNodes()).thenReturn(true, false);
    Mockito.when(multifieldNode.getNodes()).thenReturn(itemItr);
    Mockito.when(itemItr.hasNext()).thenReturn(true, false);
    Mockito.when(itemItr.nextNode()).thenReturn(itemNode);
    Mockito.when(itemNode.getProperty("errorCode")).thenReturn(prop1);
    Mockito.when(prop1.getString()).thenReturn("ERR400");
    Mockito.when(itemNode.getProperty("errorMessage")).thenReturn(prop2);
    Mockito.when(prop2.getString()).thenReturn("Resource not found");
    Mockito.when(request.getResourceBundle(ArgumentMatchers.any())).thenReturn(null);
    Mockito.when(resource.getPath()).thenReturn("Resource path");
    Mockito.when(response.getWriter()).thenReturn(printWriter);
    errorMessagesServlet.setGlobalErrorMessages(globalErrorMessages);
    errorMessagesServlet.doGet(request, response);
  }
}
