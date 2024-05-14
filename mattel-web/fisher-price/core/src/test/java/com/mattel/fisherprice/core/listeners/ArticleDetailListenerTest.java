package com.mattel.fisherprice.core.listeners;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.Workspace;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.ObservationManager;

import org.apache.sling.jcr.api.SlingRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.osgi.service.component.ComponentContext;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class ArticleDetailListenerTest {
  
  @InjectMocks
  private ArticleDetailListener articleDetailListener;
  
  @Mock
  private SlingRepository repository;
  
  @Mock
  private Session adminSession;
  
  @Mock
  private ComponentContext context;
  
  @Test
  public void testOnEvent() throws Exception{
    EventIterator eventIterator = Mockito.mock(EventIterator.class);
    Mockito.when(eventIterator.hasNext()).thenReturn(true,false);
    Event event = Mockito.mock(Event.class);
    Mockito.when(eventIterator.nextEvent()).thenReturn(event);
    Mockito.when(event.getPath()).thenReturn("/articlepath");
    Node pageNode = Mockito.mock(Node.class);
    Mockito.when(adminSession.getNode("/articlepath")).thenReturn(pageNode);
    Property templateProp = Mockito.mock(Property.class);
    Mockito.when(pageNode.getProperty("cq:template")).thenReturn(templateProp);
    Value propValue = Mockito.mock(Value.class);
    Mockito.when(templateProp.getValue()).thenReturn(propValue);
    Mockito.when(propValue.getString()).thenReturn("/conf/fisher-price/settings/wcm/templates/article-details-page");
    Node langMasterNode = Mockito.mock(Node.class);
    Mockito.when(adminSession.getNode("/content/fisher-price/language-masters/jcr:content")).thenReturn(langMasterNode);
    Property maxArticleIdProp = Mockito.mock(Property.class);
    Mockito.when(langMasterNode.getProperty("maxArticleId")).thenReturn(maxArticleIdProp);
    Value maxArticleIdPropValue = Mockito.mock(Value.class);
    Mockito.when(maxArticleIdProp.getValue()).thenReturn(maxArticleIdPropValue);
    long maxArticleValue = 11;
    Mockito.when(maxArticleIdPropValue.getLong()).thenReturn(maxArticleValue);
    articleDetailListener.onEvent(eventIterator);
    Mockito.when(repository.loginService("readwriteservice", null)).thenReturn(adminSession);
    Workspace workspace = Mockito.mock(Workspace.class);
    Mockito.when(adminSession.getWorkspace()).thenReturn(workspace);
    ObservationManager observationManager = Mockito.mock(ObservationManager.class);
    Mockito.when(workspace.getObservationManager()).thenReturn(observationManager);
    articleDetailListener.activate(context);
    articleDetailListener.deactivate();
  }

}
