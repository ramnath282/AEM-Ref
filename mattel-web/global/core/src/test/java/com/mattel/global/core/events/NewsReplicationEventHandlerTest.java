package com.mattel.global.core.events;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.sling.settings.SlingSettingsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.replication.ReplicationAction;
import com.mattel.global.core.utils.PropertyReaderUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ReplicationAction.class)
@PowerMockIgnore("javax.net.ssl.*")
public class NewsReplicationEventHandlerTest {
  
    NewsReplicationEventHandler newsReplicationEventHandler;
    
    Event event;
    SlingSettingsService slingSettingsService;
    ReplicationAction replicationAction;
    PropertyReaderUtils propertyReaderUtils;    
    BundleContext bundleContext;
    
    @Before
    public void setup() throws IllegalArgumentException, IllegalAccessException{
      
        bundleContext = Mockito.mock(BundleContext.class);
        slingSettingsService = Mockito.mock(SlingSettingsService.class);
        propertyReaderUtils = Mockito.mock(PropertyReaderUtils.class);
        event = Mockito.mock(Event.class);
        replicationAction = Mockito.mock(ReplicationAction.class);
        newsReplicationEventHandler = new NewsReplicationEventHandler();

        MemberModifier.field(NewsReplicationEventHandler.class, "bundleContext").set(newsReplicationEventHandler, bundleContext);
        MemberModifier.field(NewsReplicationEventHandler.class, "slingSettingsService").set(newsReplicationEventHandler, slingSettingsService);
        MemberModifier.field(NewsReplicationEventHandler.class, "propertyReaderUtils").set(newsReplicationEventHandler, propertyReaderUtils);
        PowerMockito.mockStatic(ReplicationAction.class);
        Mockito.when(ReplicationAction.fromEvent(event)).thenReturn(replicationAction);
    }
    
    @Test
    public void test_WithoutAuthor_And_WithSiteKeyVariantOne(){
      Set<String> list = new HashSet<>(Arrays.asList("publish"));
      Mockito.when(slingSettingsService.getRunModes()).thenReturn(list);
      Mockito.when(replicationAction.getPath()).thenReturn("/content/mattel/mattel-corporate/language-masters/en/home/news"); 
      newsReplicationEventHandler.handleEvent(event);
    }
    
    @Test
    public void test_WithAuthor_And_WithSiteKeyVariantTwo(){
      Set<String> list = new HashSet<>(Arrays.asList("author", "publish"));
      Mockito.when(slingSettingsService.getRunModes()).thenReturn(list);
      Mockito.when(replicationAction.getPath()).thenReturn("/content/mattel/mattel-corporate/us/en-us/home/news");
      newsReplicationEventHandler.handleEvent(event);
    }
    
}
