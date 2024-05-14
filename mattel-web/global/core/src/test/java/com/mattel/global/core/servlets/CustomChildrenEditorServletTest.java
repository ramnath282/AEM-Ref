package com.mattel.global.core.servlets;

import static org.mockito.Mockito.doNothing;

import java.io.IOException;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CustomChildrenEditorServletTest {
  private static final String PARAM_DELETED_CHILDREN = "delete";
  private static final String PARAM_ORDERED_CHILDREN = "order";
  @Mock
  CustomChildrenEditorServlet customChildrenEditorServlet;
  @Mock
  SlingHttpServletRequest request;
  @Mock
  SlingHttpServletResponse response;
  @Mock
  ResourceResolver resolver;
  @Mock
  Resource container;
  @Mock
  Resource child;
  @Mock
  Node containerNode;

  String[] deletedChildrenNames = { "item_1577790367695", "item_1577790367696" };

  @Before
  public void setUp() throws RepositoryException, PersistenceException {
    customChildrenEditorServlet = new CustomChildrenEditorServlet();

    Mockito.when(request.getResourceResolver()).thenReturn(resolver);
    Mockito.when(request.getResource()).thenReturn(container);
    Mockito.when(request.getParameterValues(PARAM_DELETED_CHILDREN))
        .thenReturn(deletedChildrenNames);
    Mockito.when(container.getChild("item_1577790367695")).thenReturn(child);
    Mockito.when(container.getChild("item_1577790367696")).thenReturn(child);

    Mockito.when(request.getParameterValues(PARAM_ORDERED_CHILDREN))
        .thenReturn(deletedChildrenNames);

    Mockito.when(container.adaptTo(Node.class)).thenReturn(containerNode);

    Mockito.when(containerNode.hasNode("item_1577790367695")).thenReturn(true);

    doNothing().when(resolver).delete(child);
    doNothing().when(resolver).commit();
  }

  @Test
  public void testToVerifyValidChilderenNodeDeletion() throws ServletException, IOException {
    customChildrenEditorServlet.doPost(request, response);
  }

  @Test
  public void testToVerifyChilderenNodeDeletionForNullResponse()
      throws ServletException, IOException {
    Mockito.when(request.getParameterValues(PARAM_DELETED_CHILDREN)).thenReturn(null);
    customChildrenEditorServlet.doPost(request, response);
  }

  @Test
  public void testToVerifyChilderenNodeDeletionForNullResource()
      throws ServletException, IOException {
    Mockito.when(container.getChild("item_1577790367695")).thenReturn(null);
    customChildrenEditorServlet.doPost(request, response);
  }

  @Test
  public void testToVerifyChilderenNodeOrderingForNullResponse()
      throws ServletException, IOException {
    Mockito.when(request.getParameterValues(PARAM_ORDERED_CHILDREN)).thenReturn(null);
    customChildrenEditorServlet.doPost(request, response);
  }

  @Test
  public void testToVerifyChilderenNodeOrderingForNullObject()
      throws ServletException, IOException {
    Mockito.when(container.adaptTo(Node.class)).thenReturn(null);
    customChildrenEditorServlet.doPost(request, response);
  }
}