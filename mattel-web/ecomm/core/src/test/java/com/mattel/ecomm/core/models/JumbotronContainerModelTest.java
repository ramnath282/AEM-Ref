package com.mattel.ecomm.core.models;

import java.util.Iterator;

import javax.jcr.Node;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.adobe.cq.wcm.core.components.models.Carousel;
import com.mattel.ecomm.core.services.MultifieldReader;

@RunWith(PowerMockRunner.class)
public class JumbotronContainerModelTest {

  @InjectMocks
  JumbotronContainerModel jumbotronContainerModel;

  @Mock
  SlingHttpServletRequest request;

  @Mock
  Node linkList;

  @Mock
  Resource resource;

  @Mock
  MultifieldReader multifieldReader;

  @Mock
  Carousel carousel;

  Iterable<Resource> childrenList;
  Iterator<Resource> childrenListItr;
  Resource childResource;

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws Exception {
    MemberModifier.field(JumbotronContainerModel.class, "request").set(jumbotronContainerModel,
        request);
    MemberModifier.field(JumbotronContainerModel.class, "linkList").set(jumbotronContainerModel,
        linkList);
    MemberModifier.field(JumbotronContainerModel.class, "resource").set(jumbotronContainerModel,
        resource);
    MemberModifier.field(JumbotronContainerModel.class, "multifieldReader")
        .set(jumbotronContainerModel, multifieldReader);
    MemberModifier.field(JumbotronContainerModel.class, "carousel").set(jumbotronContainerModel,
        carousel);

    childrenList = Mockito.mock(Iterable.class);
    childrenListItr = Mockito.mock(Iterator.class);
    childResource = Mockito.mock(Resource.class);

    Mockito.when(request.getResource()).thenReturn(resource);
    Mockito.when(resource.getResourceType()).thenReturn("wcm/foundation/components/responsivegrid");
    Mockito.when(resource.getChildren()).thenReturn(childrenList);
    Mockito.when(childrenList.iterator()).thenReturn(childrenListItr);
    Mockito.when(childrenListItr.hasNext()).thenReturn(true, false);
    Mockito.when(childrenListItr.next()).thenReturn(childResource);
    Mockito.when(childResource.getResourceType())
        .thenReturn("wcm/foundation/components/responsivegrid");
    Mockito.when(request.getAttribute("jumbotron-id")).thenReturn("123");
  }

  @Test
  public void testInit() {
    jumbotronContainerModel.postConsctruct();
  }
}
