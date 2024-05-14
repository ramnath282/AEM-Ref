package com.mattel.fisherprice.core.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.mattel.fisherprice.core.pojos.TagsPojo;
import com.mattel.fisherprice.core.utils.ArticleFeedConfigurationUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ArticleFeedConfigurationUtils.class)
public class ArticleDetailPagesDataTest {

  @InjectMocks
  private ArticleDetailPagesData articleDetailPagesData;

  @Mock
  private ResourceResolverFactory resolverFactory;

  @Mock
  private RelatedArticleService relatedArticleServiceWrapper;

  @Mock
  private QueryBuilder queryBuilder;

  @Mock
  ResourceResolver resourceResolver;

  @SuppressWarnings("unchecked")
  @Before
  public void setup() throws Exception {
    PowerMockito.mockStatic(ArticleFeedConfigurationUtils.class);
    Mockito.when(ArticleFeedConfigurationUtils.getBrandBasePath())
        .thenReturn("/content/fisher-price");
    Mockito.when(ArticleFeedConfigurationUtils.getArticleBasePath()).thenReturn("/home/articles");
    Mockito.when(resolverFactory.getServiceResourceResolver(Mockito.anyMap()))
        .thenReturn(resourceResolver);
    Resource articleBasePageResource = Mockito.mock(Resource.class);
    Mockito.when(resourceResolver.getResource("/content/fisher-price/us/en-us/home/articles"))
        .thenReturn(articleBasePageResource);
    Page articleBasePage = Mockito.mock(Page.class);
    Mockito.when(articleBasePageResource.adaptTo(Page.class)).thenReturn(articleBasePage);
    Iterator<Page> articleCategoryLandingPages = Mockito.mock(Iterator.class);
    Mockito.when(articleBasePage.listChildren()).thenReturn(articleCategoryLandingPages);
    Mockito.when(articleCategoryLandingPages.hasNext()).thenReturn(true, false);
    Page articleCategoryLandingPage = Mockito.mock(Page.class);
    Mockito.when(articleCategoryLandingPages.next()).thenReturn(articleCategoryLandingPage);
    Iterator<Page> childrenOfCategoryLandingPages = Mockito.mock(Iterator.class);
    Mockito.when(articleCategoryLandingPage.listChildren())
        .thenReturn(childrenOfCategoryLandingPages);
    Mockito.when(childrenOfCategoryLandingPages.hasNext()).thenReturn(true, false);
    Page currentPage = Mockito.mock(Page.class);
    Mockito.when(childrenOfCategoryLandingPages.next()).thenReturn(currentPage);
    ValueMap currentPageValueMap = Mockito.mock(ValueMap.class);
    Mockito.when(currentPage.getProperties()).thenReturn(currentPageValueMap);
    Mockito.when(currentPageValueMap.get("cq:template", String.class))
        .thenReturn("/conf/fisher-price/settings/wcm/templates/article-details-page");
    Mockito.when(currentPageValueMap.get("articleCompResourceType", String.class))
        .thenReturn("mattel/ecomm/fisher-price/components/content/articleComponent");
    Mockito.when(currentPage.getPath()).thenReturn(
        "/content/fisher-price/us/en-us/home/articles/early-child-development/us-article/"
            + JcrConstants.JCR_CONTENT);
    
    Session session = Mockito.mock(Session.class);
    Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);

    Map<String, String> queryMapForArticleComp = new HashMap<>();
    queryMapForArticleComp.put("path",
        "/content/fisher-price/us/en-us/home/articles/early-child-development/us-article/"
            + JcrConstants.JCR_CONTENT);
    queryMapForArticleComp.put("1_property", "sling:resourceType");
    queryMapForArticleComp.put("1_property.value",
        "mattel/ecomm/fisher-price/components/content/articleComponent");
    queryMapForArticleComp.put("p.limit", "1");
    
    PredicateGroup predicates = PredicateGroup.create(queryMapForArticleComp);
    Query queryForArticleComp = Mockito.mock(Query.class);
    
    Mockito.when(queryBuilder.createQuery(predicates, session)).thenReturn(queryForArticleComp);
    SearchResult resultForArticleComp = Mockito.mock(SearchResult.class);
    Mockito.when(queryForArticleComp.getResult()).thenReturn(resultForArticleComp);
    List<Hit> hits = new ArrayList<>();
    Hit hit = Mockito.mock(Hit.class);
    hits.add(hit);
    Mockito.when(resultForArticleComp.getHits()).thenReturn(hits);
    Resource articleComponentRes = Mockito.mock(Resource.class);
    Mockito.when(hit.getResource()).thenReturn(articleComponentRes);

    ValueMap valueMapArticleComp = Mockito.mock(ValueMap.class);
    Mockito.when(articleComponentRes.getValueMap()).thenReturn(valueMapArticleComp);

    Mockito.when(valueMapArticleComp.containsKey("imageserverurl")).thenReturn(true);
    Mockito.when(valueMapArticleComp.containsKey("assetIDimage")).thenReturn(true);
    Mockito.when(valueMapArticleComp.containsKey("assetIDmobileImage")).thenReturn(true);
    Mockito.when(valueMapArticleComp.containsKey("articleTitle")).thenReturn(true);
    Mockito.when(valueMapArticleComp.containsKey("articleShortDescription")).thenReturn(true);

    Mockito.when(valueMapArticleComp.get("imageserverurl", String.class))
        .thenReturn("imageserverurl");
    Mockito.when(valueMapArticleComp.get("assetIDimage", String.class)).thenReturn("assetIDimage");
    Mockito.when(valueMapArticleComp.get("assetIDmobileImage", String.class))
        .thenReturn("assetIDmobileImage");
    Mockito.when(valueMapArticleComp.get("articleTitle", String.class)).thenReturn("");
    Mockito.when(valueMapArticleComp.get("articleShortDescription", String.class))
        .thenReturn("This is Article1 ShortDescription");

    Mockito
        .when(resourceResolver
            .map("/content/fisher-price/us/en-us/home/articles/early-child-development/us-article.html"))
        .thenReturn(
            "/content/fisher-price/us/en-us/home/articles/early-child-development/us-article");
    
    Page parentPage = Mockito.mock(Page.class);
    Mockito.when(currentPage.getParent()).thenReturn(parentPage);
    Mockito.when(parentPage.getTitle()).thenReturn("Early Child Development");
    Mockito.when(parentPage.getName()).thenReturn("early-child-development");
    Mockito.when(parentPage.getPath()).thenReturn("/content/fisher-price/us/en-us/home/articles/early-child-development");
    
    Mockito
    .when(resourceResolver
        .map("/content/fisher-price/us/en-us/home/articles/early-child-development" + JcrConstants.JCR_CONTENT))
    .thenReturn(
        "/content/fisher-price/us/en-us/home/articles/early-child-development");
    
    Mockito.when(currentPageValueMap.containsKey("jcr:title")).thenReturn(true);
    //Mockito.when(currentPageValueMap.containsKey("articleId")).thenReturn(true);
    Mockito.when(currentPageValueMap.containsKey("primaryTags")).thenReturn(true);
    Mockito.when(currentPageValueMap.containsKey("secondaryTags")).thenReturn(true);
    Mockito.when(currentPageValueMap.containsKey("sling:vanityPath")).thenReturn(true);
    Mockito.when(currentPageValueMap.containsKey("cq:lastReplicationAction")).thenReturn(true);
    Mockito.when(currentPageValueMap.containsKey("jcr:created")).thenReturn(true);
    Mockito.when(currentPageValueMap.containsKey("cq:lastReplicated")).thenReturn(true);
    Mockito.when(currentPageValueMap.containsKey("sling:redirect")).thenReturn(true);
    
    Mockito.when(currentPageValueMap.get("jcr:title", String.class)).thenReturn("Article Title");
    Mockito.when(currentPageValueMap.get("articleId", String.class)).thenReturn("Article1");
    String[] primaryTags = {"pt1"};
    Mockito.when(currentPageValueMap.get("primaryTags", String[].class)).thenReturn(primaryTags);
    String[] secoondaryTags = {"st1"};
    Mockito.when(currentPageValueMap.get("secondaryTags", String[].class)).thenReturn(secoondaryTags);
    String[] vanityPaths = {"/early-child-development/article1"};
    Mockito.when(currentPageValueMap.get("sling:vanityPath", String[].class)).thenReturn(vanityPaths);
    Mockito.when(currentPageValueMap.get("cq:lastReplicationAction", String.class)).thenReturn("Activate");
    Mockito.when(currentPageValueMap.get("jcr:created", Date.class)).thenReturn(new Date());
    Mockito.when(currentPageValueMap.get("cq:lastReplicated", Date.class)).thenReturn(new Date());
    Mockito.when(currentPageValueMap.get("sling:redirect", String.class)).thenReturn("true");
        
    List<TagsPojo> primaryTagPojoLst = new ArrayList<>();
    TagsPojo tagsPojo1 = new TagsPojo();
    tagsPojo1.setLocaleBasedTitle("PT1");
    tagsPojo1.setTagID("namespace:pt1");
    tagsPojo1.setTagName("PT-1");
    tagsPojo1.setTagTitle("PT-1");
    primaryTagPojoLst.add(tagsPojo1);
    Mockito.when(relatedArticleServiceWrapper.getTagRelatedData(Mockito.any(String[].class), Mockito.any(Locale.class))).thenReturn(primaryTagPojoLst);
    
    List<TagsPojo> secondaryTagPojoLst = new ArrayList<>();
    TagsPojo tagsPojo2 = new TagsPojo();
    tagsPojo2.setLocaleBasedTitle("PT1");
    tagsPojo2.setTagID("namespace:pt1");
    tagsPojo2.setTagName("PT-1");
    tagsPojo2.setTagTitle("PT-1");
    secondaryTagPojoLst.add(tagsPojo2);
    Mockito.when(relatedArticleServiceWrapper.getTagRelatedData(Mockito.any(String[].class), Mockito.any(Locale.class))).thenReturn(secondaryTagPojoLst);
  }

  @Test
  public void test() {
    Map<String, List<String>> articleFeedFiltersMap = new HashMap<String, List<String>>();
    List<String> localesForArticleFeed = new ArrayList<>();
    localesForArticleFeed.add("en-us");

    List<String> articleDetailTemplatePath = new ArrayList<>();
    articleDetailTemplatePath.add("/conf/fisher-price/settings/wcm/templates/article-details-page");

    List<String> articleCompResourceType = new ArrayList<>();
    articleCompResourceType.add("mattel/ecomm/fisher-price/components/content/articleComponent");

    List<String> articleBasePAth = new ArrayList<>();
    articleBasePAth.add("/home/articles");

    List<String> headerLinesForCSV = new ArrayList<>();
    headerLinesForCSV.add(
        "## RECSUse this file to upload product display information to Recommendations. Each product has its own row. Each line must contain 19 values and if not all are filled a space should be left.");

    List<String> articleFeedFileInitialName = new ArrayList<>();
    articleFeedFileInitialName.add("articlefeed");

    List<String> damPathToUploadArticleFeed = new ArrayList<>();
    damPathToUploadArticleFeed.add("/content/dam/fp-dam/articlefeed");

    List<String> brandBasePath = new ArrayList<>();
    brandBasePath.add("/content/fisher-price");

    articleFeedFiltersMap.put("localesForArticleFeed", localesForArticleFeed);
    articleFeedFiltersMap.put("articleDetailTemplatePath", articleDetailTemplatePath);
    articleFeedFiltersMap.put("articleCompResourceType", articleCompResourceType);
    articleFeedFiltersMap.put("brandBasePath", brandBasePath);
    articleFeedFiltersMap.put("articleBasePath", articleBasePAth);
    articleFeedFiltersMap.put("headerLinesForCSV", headerLinesForCSV);
    articleFeedFiltersMap.put("articleFeedFileInitialName", articleFeedFileInitialName);
    articleFeedFiltersMap.put("damPathToUploadArticleFeed", damPathToUploadArticleFeed);
    articleDetailPagesData.getArticleFeedInCSV(articleFeedFiltersMap);
  }

}
