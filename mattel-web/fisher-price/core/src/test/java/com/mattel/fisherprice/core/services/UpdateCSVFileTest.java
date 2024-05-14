package com.mattel.fisherprice.core.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.dam.api.AssetManager;
import com.day.cq.replication.Replicator;
import com.mattel.fisherprice.core.utils.ArticleFeedConfigurationUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ArticleFeedConfigurationUtils.class)
public class UpdateCSVFileTest {

  @InjectMocks
  private UpdateCSVFile updateCSVFile;

  @Mock
  ResourceResolverFactory resolverFactory;

  @Mock
  private ArticleDetailPagesData articleDetailPagesDataWrapper;

  @Mock
  private Replicator replicator;

  @Mock
  Session session;

  Map<String, List<String>> articleFeedFiltersMap;

  @SuppressWarnings("unchecked")
  @Before
  public void setup() throws Exception {
    PowerMockito.mockStatic(ArticleFeedConfigurationUtils.class);
    articleFeedFiltersMap = Mockito.mock(Map.class);
    Mockito.when(articleDetailPagesDataWrapper.getArticleFeedInCSV(articleFeedFiltersMap))
        .thenReturn("articleFeedInCSV");
    Mockito.when(ArticleFeedConfigurationUtils.getDamPathToUploadArticleFeed())
        .thenReturn("/content/dam/fp-dam/articlefeed");
    Mockito.when(ArticleFeedConfigurationUtils.getArticleFeedFileInitialName())
        .thenReturn("articleFeed");
    List<String> localeLst = new ArrayList<String>();
    localeLst.add("en_us");
    Mockito.when(articleFeedFiltersMap.get("localesForArticleFeed")).thenReturn(localeLst);
    ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
    Mockito.when(resolverFactory.getServiceResourceResolver(Mockito.anyMap()))
        .thenReturn(resourceResolver);
    Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
    Mockito.when(session.nodeExists("/content/dam/fp-dam/articlefeed")).thenReturn(true);
    AssetManager assetManager = Mockito.mock(AssetManager.class);
    Mockito.when(resourceResolver.adaptTo(AssetManager.class)).thenReturn(assetManager);
  }

  @Test
  public void testGenerateArticleFeedCSV() throws JSONException, RepositoryException {
    Mockito.when(session.nodeExists("/content/dam/fp-dam/articlefeed/articleFeed_en_us.csv"))
        .thenReturn(true);
    Node existingOriginalNode = Mockito.mock(Node.class);
    Mockito
        .when(session.getNode(
            "/content/dam/fp-dam/articlefeed/articleFeed_en_us.csv/jcr:content/renditions/original/jcr:content"))
        .thenReturn(existingOriginalNode);
    ValueFactory factory = Mockito.mock(ValueFactory.class);
    Mockito.when(session.getValueFactory()).thenReturn(factory);
    Binary binary = Mockito.mock(Binary.class);
    Mockito.when(factory.createBinary(Mockito.any(InputStream.class))).thenReturn(binary);
    Value value = Mockito.mock(Value.class);
    Mockito.when(factory.createValue(binary)).thenReturn(value);
    updateCSVFile.generateArticleFeedCSV(articleFeedFiltersMap);
  }

  @Test
  public void testGenerateArticleFeedcsvNofileExists() throws JSONException, RepositoryException {
    Mockito.when(session.nodeExists("/content/dam/fp-dam/articlefeed/articleFeed_en_us.csv"))
        .thenReturn(false);
    updateCSVFile.generateArticleFeedCSV(articleFeedFiltersMap);
  }
}
