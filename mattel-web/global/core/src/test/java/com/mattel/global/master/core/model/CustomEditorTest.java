package com.mattel.global.master.core.model;

import java.util.Iterator;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.support.membermodification.MemberModifier;

import com.day.cq.wcm.api.components.Component;
import com.day.cq.wcm.api.components.ComponentManager;

@RunWith(MockitoJUnitRunner.class)
public class CustomEditorTest {
  @InjectMocks
  CustomEditor customEditor;
  @Mock
  SlingHttpServletRequest request;

  @Mock
  RequestPathInfo requestPathInfo;
  @Mock
  ResourceResolver resolver;

  @Mock
  Resource resource;
  @Mock
  ComponentManager componentManager;

  @Mock
  Iterable<Resource> childrenList;

  @Mock
  Iterator<Resource> childrenListItr;

  @Mock
  Resource childResource;
  @Mock
  Component component;

  @Before
  public void setUp() throws Exception {
    MemberModifier.field(CustomEditor.class, "container").set(customEditor, resource);

    Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
    Mockito.when(requestPathInfo.getSuffix())
        .thenReturn("/content/corp/79/jcr:content/root/gridcontainer/content_1578930377430");
    Mockito.when(request.getResourceResolver()).thenReturn(resolver);
    Mockito
        .when(resolver
            .getResource("/content/corp/79/jcr:content/root/gridcontainer/content_1578930377430"))
        .thenReturn(resource);
    Mockito.when(resolver.adaptTo(ComponentManager.class)).thenReturn(componentManager);
    Mockito.when(resource.getChildren()).thenReturn(childrenList);
    Mockito.when(childrenList.iterator()).thenReturn(childrenListItr);
    Mockito.when(childrenListItr.hasNext()).thenReturn(true, false);
    Mockito.when(childrenListItr.next()).thenReturn(childResource);
    Mockito.when(childResource.getName()).thenReturn("content_12345");
  }

  @Test
  public void testToVerifyCustomEditor() {
    customEditor.init();
    customEditor.getContainer();
    customEditor.getContentItems();
    customEditor.getCtaItems();
    System.out.println(customEditor.getCtaItems() + "HII" + customEditor.getContentItems());

  }

}
