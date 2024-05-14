package com.mattel.ag.explore.core.services;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.mattel.ag.explore.core.constants.Constants;
import com.mattel.ag.explore.core.pojos.RelatedArticlePojo;
import com.mattel.ag.explore.core.pojos.TagsPojo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.SlingException;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author CTS GetRelatedArticles Service.
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PredicateGroup.class)
public class GetRelatedArticlesTest {

  ResourceResolverFactory resolverFactory;

  QueryBuilder queryBuilder;

  TagsPojo tagsPojo;

  GetResourceResolver getResourceResolver;

  List<TagsPojo> tagsPojos;

  ResourceResolver resolver;

  GetRelatedArticles getRelatedArticles;

  Session session;

  PredicateGroup predicateGroup;

  Query query;

  ValueMap valueMap;

  SearchResult result;

  Hit hit;

  TagManager tagManager;

  Tag tag;

  Date date;

  String path = "/content/ag/en/explore";

  Resource resource;

  List<Hit> hits;

  Iterator<Resource> resourceIterator;

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws LoginException, RepositoryException {
    final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:00.00'Z'");
    date = new Date();
    query = Mockito.mock(Query.class);
    result = Mockito.mock(SearchResult.class);
    resourceIterator = Mockito.mock(Iterator.class);
    hit = Mockito.mock(Hit.class);
    hits = new ArrayList<Hit>();
    hits.add(hit);
    getRelatedArticles = new GetRelatedArticles();
    final RelatedArticlePojo relatedArticlePojo = new RelatedArticlePojo();
    tagsPojos = new ArrayList<>();
    tagsPojo = new TagsPojo();
    tagsPojo.setTagName("abc");
    tagsPojos.add(tagsPojo);
    relatedArticlePojo.setArticleTitle("articleTitle");
    relatedArticlePojo.setArticleDescription("articleDescription");
    relatedArticlePojo.setArticleLandingHero("articleLandingHero");
    relatedArticlePojo.setPagePath("pagePath");
    valueMap = Mockito.mock(ValueMap.class);
    relatedArticlePojo.setEnableSocialMediaSharing("enableSocialMediaSharing");
    relatedArticlePojo.setPrimaryTag(tagsPojos);
    resolverFactory = Mockito.mock(ResourceResolverFactory.class);
    resolver = Mockito.mock(ResourceResolver.class);
    session = Mockito.mock(Session.class);
    getRelatedArticles.setResolverFactory(resolverFactory);
    queryBuilder = Mockito.mock(QueryBuilder.class);
    getRelatedArticles.setQueryBuilder(queryBuilder);
    getResourceResolver = Mockito.mock(GetResourceResolver.class);

    final Map<String, Object> map = new HashMap<>();
    final String[] primaryTags = { "italian", "american" };
    map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
    Mockito.when(resolverFactory.getServiceResourceResolver(map)).thenReturn(resolver);
    Mockito.when(resolver.adaptTo(Session.class)).thenReturn(session);
    Mockito.when(valueMap.containsKey(Constants.TITLE)).thenReturn(true);
    Mockito.when(valueMap.containsKey(Constants.KEYWORDS)).thenReturn(true);
    Mockito.when(valueMap.containsKey(Constants.PRIMARY_TAGS)).thenReturn(true);
    Mockito.when(valueMap.containsKey(Constants.SECONDARY_TAGS)).thenReturn(true);
    Mockito.when(valueMap.containsKey(Constants.CQ_TAGS)).thenReturn(true);
    Mockito.when(valueMap.containsKey(Constants.ARTICLE_CREATED_DATE)).thenReturn(true);
    Mockito.when(valueMap.containsKey(Constants.ARTICLE_UPDATED_DATE)).thenReturn(true);
    Mockito.when(valueMap.containsKey(Constants.ARTICLE_DISPLAY_DATE)).thenReturn(true);
    Mockito.when(valueMap.containsKey(Constants.ARTICLE_DESCRIPTION)).thenReturn(true);
    Mockito.when(valueMap.containsKey(Constants.ARTICLE_ID)).thenReturn(true);
    Mockito.when(valueMap.containsKey(Constants.DISPLAY_HOME_PAGE)).thenReturn(true);
    Mockito.when(valueMap.containsKey(Constants.DISPLAY_SITE_HOME)).thenReturn(true);
    Mockito.when(valueMap.containsKey(Constants.ARTICLE_SOCIAL_SHARING)).thenReturn(true);
    Mockito.when(valueMap.containsKey(Constants.HIDE_ARTICLE_DATE)).thenReturn(true);
    Mockito.when(valueMap.get(Constants.TITLE, String.class)).thenReturn("Title");
    Mockito.when(valueMap.get(Constants.KEYWORDS, String.class)).thenReturn("keywords");
    Mockito.when(valueMap.get(Constants.PRIMARY_TAGS, String[].class)).thenReturn(primaryTags);
    Mockito.when(valueMap.get(Constants.SECONDARY_TAGS, String[].class)).thenReturn(primaryTags);
    Mockito.when(valueMap.get(Constants.CQ_TAGS, String[].class)).thenReturn(primaryTags);
    Mockito.when(valueMap.get(Constants.ARTICLE_CREATED_DATE, Date.class)).thenReturn(date);
    Mockito.when(valueMap.get(Constants.ARTICLE_UPDATED_DATE, Date.class)).thenReturn(date);
    Mockito.when(valueMap.get(Constants.ARTICLE_DISPLAY_DATE, String.class))
        .thenReturn(formatter.format(date));
    Mockito.when(valueMap.get(Constants.ARTICLE_DESCRIPTION, String.class)).thenReturn("Title");
    Mockito.when(valueMap.get(Constants.ARTICLE_ID, String.class)).thenReturn("Title");
    Mockito.when(valueMap.get(Constants.DISPLAY_HOME_PAGE, String.class)).thenReturn("Title");
    Mockito.when(valueMap.get(Constants.DISPLAY_SITE_HOME, String.class)).thenReturn("Title");
    Mockito.when(valueMap.get(Constants.ARTICLE_SOCIAL_SHARING, String.class)).thenReturn("Title");
    Mockito.when(valueMap.get(Constants.HIDE_ARTICLE_DATE, String.class)).thenReturn("Title");
    tag = Mockito.mock(Tag.class);
    tagManager = Mockito.mock(TagManager.class);
    Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resolver);
    Mockito.when(getResourceResolver.getResourceResolver().adaptTo(TagManager.class))
        .thenReturn(tagManager);
    Mockito.when(tagManager.resolve(primaryTags[0])).thenReturn(tag);
    Mockito.when(tagManager.resolve(primaryTags[1])).thenReturn(tag);
    Mockito.when(tag.getTitle()).thenReturn("Italian");
    Mockito.when(tag.getLocalTagID()).thenReturn("Italian");
    Mockito.when(tag.getName()).thenReturn("Italian");
    resource = Mockito.mock(Resource.class);
    Mockito.when(resolver.getResource(path)).thenReturn(resource);
    Mockito.when(resolver.getResource(path + Constants.ARTICLE_BANNER_COMPONENT))
        .thenReturn(resource);
    Mockito.when(resource.getValueMap()).thenReturn(valueMap);
    PowerMockito.mockStatic(PredicateGroup.class);
    Mockito.when(result.getResources()).thenReturn(resourceIterator);
    Mockito.when(resourceIterator.hasNext()).thenReturn(true).thenReturn(false);
    Mockito.when(resourceIterator.next()).thenReturn(resource);
    Mockito.when(resourceIterator.next().getResourceResolver()).thenReturn(resolver);
  }

  @Test
  public void getRelatedArticlesTest0() throws RepositoryException {
    final List<String> articlePaths = new ArrayList<>();
    predicateGroup = Mockito.mock(PredicateGroup.class);
    articlePaths.add("3");
    final Map<String, List<String>> queryDataMap = new HashMap<>();
    queryDataMap.put(Constants.DEFAULT_LIMIT, articlePaths);
    final Map<String, String> querymap = new HashMap<>();
    querymap.put("type", "cq:Page");
    querymap.put("path", "/content/ag/en/explore");
    querymap.put("p.limit", "3");
    querymap.put("orderby", "@jcr:content/datepickerarticle");
    querymap.put("orderby.sort", "desc");
    querymap.put("group.1_group.p.or", "true");
    querymap.put("group.2_group.p.or", "true");
    // querymap.put("group.6_group.p.or", "true");
    querymap.put("group.p.or", "true");
    querymap.put("3_group.property", "jcr:content/cq:template");
    querymap.put("3_group.property." + "1" + "_value",
        "/conf/ag/settings/wcm/templates/article-template-page");
    Mockito.when(PredicateGroup.create(querymap)).thenReturn(predicateGroup);
    Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querymap), session))
        .thenReturn(query);
    Mockito.when(query.getResult()).thenReturn(result);
    Mockito.when(result.getHits()).thenReturn(hits);
    Mockito.when(hit.getProperties()).thenReturn(valueMap);
    Mockito.when(hit.getPath()).thenReturn("/article/summary-page");
    Mockito.when(resolver.getResource("/article/summary-page" + Constants.ARTICLE_BANNER_COMPONENT))
        .thenReturn(resource);
    Mockito.when(resolver.isLive()).thenReturn(true);
    Mockito.doNothing().when(resolver).close();
    getRelatedArticles.getRelatedArticles(queryDataMap);
  }

  @Test
  public void getRelatedArticlesTestIfResourceIsNull() throws RepositoryException {
    final List<String> articlePaths = new ArrayList<>();
    predicateGroup = Mockito.mock(PredicateGroup.class);
    articlePaths.add("3");
    final Map<String, List<String>> queryDataMap = new HashMap<>();
    queryDataMap.put(Constants.DEFAULT_LIMIT, articlePaths);
    final Map<String, String> querymap = new HashMap<>();
    querymap.put("type", "cq:Page");
    querymap.put("path", "/content/ag/en/explore");
    querymap.put("p.limit", "3");
    querymap.put("orderby", "@jcr:content/datepickerarticle");
    querymap.put("orderby.sort", "desc");
    querymap.put("group.1_group.p.or", "true");
    querymap.put("group.2_group.p.or", "true");
    // querymap.put("group.6_group.p.or", "true");
    querymap.put("group.p.or", "true");
    querymap.put("3_group.property", "jcr:content/cq:template");
    querymap.put("3_group.property." + "1" + "_value",
        "/conf/ag/settings/wcm/templates/article-template-page");
    Mockito.when(PredicateGroup.create(querymap)).thenReturn(predicateGroup);
    Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querymap), session))
        .thenReturn(query);
    Mockito.when(query.getResult()).thenReturn(result);
    Mockito.when(result.getHits()).thenReturn(hits);
    Mockito.when(hit.getProperties()).thenReturn(valueMap);
    Mockito.when(hit.getPath()).thenReturn("/article/summary-page");
    Mockito.when(resolver.getResource("/article/summary-page" + Constants.ARTICLE_BANNER_COMPONENT))
        .thenReturn(null);
    getRelatedArticles.getRelatedArticles(queryDataMap);
  }

  @Test
  public void getRelatedArticlesTestIfResourceError() throws RepositoryException {
    final List<String> articlePaths = new ArrayList<>();
    predicateGroup = Mockito.mock(PredicateGroup.class);
    articlePaths.add("3");
    final Map<String, List<String>> queryDataMap = new HashMap<>();
    queryDataMap.put(Constants.DEFAULT_LIMIT, articlePaths);
    final Map<String, String> querymap = new HashMap<>();
    querymap.put("type", "cq:Page");
    querymap.put("path", "/content/ag/en/explore");
    querymap.put("p.limit", "3");
    querymap.put("orderby", "@jcr:content/datepickerarticle");
    querymap.put("orderby.sort", "desc");
    querymap.put("group.1_group.p.or", "true");
    querymap.put("group.2_group.p.or", "true");
    // querymap.put("group.6_group.p.or", "true");
    querymap.put("group.p.or", "true");
    querymap.put("3_group.property", "jcr:content/cq:template");
    querymap.put("3_group.property." + "1" + "_value",
        "/conf/ag/settings/wcm/templates/article-template-page");
    Mockito.when(PredicateGroup.create(querymap)).thenReturn(predicateGroup);
    Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querymap), session))
        .thenReturn(query);
    Mockito.when(query.getResult()).thenReturn(result);
    Mockito.when(result.getHits()).thenReturn(hits);
    Mockito.when(hit.getProperties()).thenReturn(valueMap);
    Mockito.when(hit.getPath()).thenReturn("/article/summary-page");
    Mockito.when(resolver.getResource("/article/summary-page" + Constants.ARTICLE_BANNER_COMPONENT))
        .thenThrow(new SlingException("exception", new Exception("Unable to get resource")));
    getRelatedArticles.getRelatedArticles(queryDataMap);
  }

  @Test
  public void getRelatedArticlesTest1() throws RepositoryException {
    final List<String> articlePaths = new ArrayList<>();
    predicateGroup = Mockito.mock(PredicateGroup.class);
    articlePaths.add("3");
    final Map<String, List<String>> queryDataMap = new HashMap<>();
    queryDataMap.put(Constants.DEFAULT_LIMIT, articlePaths);
    final Map<String, String> querymap = new HashMap<>();
    querymap.put("type", "cq:Page");
    querymap.put("path", "/content/ag/en/explore");
    querymap.put("p.limit", "3");
    querymap.put("orderby", "@jcr:content/datepickerarticle");
    querymap.put("orderby.sort", "desc");
    querymap.put("group.1_group.p.or", "true");
    querymap.put("group.2_group.p.or", "true");
    // querymap.put("group.6_group.p.or", "true");
    querymap.put("group.p.or", "true");
    querymap.put("3_group.property", "jcr:content/cq:template");
    querymap.put("3_group.property." + "1" + "_value",
        "/conf/ag/settings/wcm/templates/article-template-page");
    Mockito.when(PredicateGroup.create(querymap)).thenReturn(predicateGroup);
    Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querymap), session))
        .thenReturn(query);
    Mockito.when(query.getResult()).thenReturn(result);
    Mockito.when(result.getHits()).thenReturn(hits);
    Mockito.when(hit.getProperties()).thenReturn(valueMap);
    getRelatedArticles.getRelatedArticles(queryDataMap);

  }

  @Test
  public void getRelatedArticlesTest2() throws RepositoryException {
    final Map<String, List<String>> queryDataMap = new HashMap<>();
    final List<String> pathValue = new ArrayList<>();
    pathValue.add(Constants.DEFAULT_EXPLORE_CONTENT_PATH);
    queryDataMap.put(Constants.PATH, pathValue);
    final Map<String, String> querymap = new HashMap<>();
    querymap.put("type", "cq:Page");
    querymap.put("p.limit", "3");
    querymap.put("orderby", "@jcr:content/datepickerarticle");
    querymap.put("orderby.sort", "desc");
    querymap.put("group.1_group.p.or", "true");
    querymap.put("group.2_group.p.or", "true");
    // querymap.put("group.6_group.p.or", "true");
    querymap.put("group.p.or", "true");
    querymap.put("3_group.property", "jcr:content/cq:template");
    querymap.put("3_group.property." + "1" + "_value",
        "/conf/ag/settings/wcm/templates/article-template-page");
    Mockito.when(PredicateGroup.create(querymap)).thenReturn(predicateGroup);
    Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querymap), session))
        .thenReturn(query);
    Mockito.when(query.getResult()).thenReturn(result);
    Mockito.when(result.getHits()).thenReturn(hits);
    Mockito.when(hit.getProperties()).thenReturn(valueMap);
    getRelatedArticles.getRelatedArticles(queryDataMap);

  }

  @Test
  public void getRelatedArticlesTest3() throws RepositoryException {
    final Map<String, List<String>> queryDataMap = new HashMap<>();
    final List<String> displayHomePage = new ArrayList<>();
    displayHomePage.add("true");
    queryDataMap.put(Constants.DISPLAY_HOME_PAGE, displayHomePage);
    final Map<String, String> querymap = new HashMap<>();
    querymap.put("type", "cq:Page");
    querymap.put("p.limit", "3");
    querymap.put("orderby", "@jcr:content/datepickerarticle");
    querymap.put("orderby.sort", "desc");
    querymap.put("group.1_group.p.or", "true");
    querymap.put("group.2_group.p.or", "true");
    // querymap.put("group.6_group.p.or", "true");
    querymap.put("group.p.or", "true");
    querymap.put("3_group.property", "jcr:content/cq:template");
    querymap.put("3_group.property." + "1" + "_value",
        "/conf/ag/settings/wcm/templates/article-template-page");
    querymap.put("7_group.property", "@jcr:content/displayhomepage");
    querymap.put("7_group.property.1_value", "true");
    Mockito.when(PredicateGroup.create(querymap)).thenReturn(predicateGroup);
    Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querymap), session))
        .thenReturn(query);
    Mockito.when(query.getResult()).thenReturn(result);
    Mockito.when(result.getHits()).thenReturn(hits);
    Mockito.when(hit.getProperties()).thenReturn(valueMap);
    getRelatedArticles.getRelatedArticles(queryDataMap);

  }

  @Test
  public void getRelatedArticlesTest4() throws RepositoryException {
    final Map<String, List<String>> queryDataMap = new HashMap<>();
    final List<String> displaySitePage = new ArrayList<>();
    displaySitePage.add("true");
    queryDataMap.put(Constants.DISPLAY_SITE_HOME, displaySitePage);
    final Map<String, String> querymap = new HashMap<>();
    querymap.put("type", "cq:Page");
    querymap.put("path", "/content/ag/en/explore");
    querymap.put("p.limit", "3");
    querymap.put("orderby", "@jcr:content/datepickerarticle");
    querymap.put("orderby.sort", "desc");
    querymap.put("group.1_group.p.or", "true");
    querymap.put("group.2_group.p.or", "true");
    // querymap.put("group.6_group.p.or", "true");
    querymap.put("group.p.or", "true");
    querymap.put("3_group.property", "jcr:content/cq:template");
    querymap.put("3_group.property." + "1" + "_value",
        "/conf/ag/settings/wcm/templates/article-template-page");
    querymap.put("8_group.property", "@jcr:content/displaysitehome");
    querymap.put("8_group.property.1_value", "true");
    Mockito.when(PredicateGroup.create(querymap)).thenReturn(predicateGroup);
    Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querymap), session))
        .thenReturn(query);
    Mockito.when(query.getResult()).thenReturn(result);
    Mockito.when(result.getHits()).thenReturn(hits);
    Mockito.when(hit.getProperties()).thenReturn(valueMap);
    getRelatedArticles.getRelatedArticles(queryDataMap);

  }

  @Test
  public void getRelatedArticlesTest5() throws RepositoryException {
    final Map<String, List<String>> queryDataMap = new HashMap<>();
    final List<String> primaryTa = new ArrayList<>();
    primaryTa.add("italian");
    primaryTa.add("crafts");
    queryDataMap.put(Constants.PRIMARY_TAGS, primaryTa);
    final Map<String, String> querymap = new HashMap<>();
    querymap.put("type", "cq:Page");
    querymap.put("p.limit", "3");
    querymap.put("orderby", "@jcr:content/datepickerarticle");
    querymap.put("orderby.sort", "desc");
    querymap.put("group.1_group.p.or", "true");
    querymap.put("group.2_group.p.or", "true");
    // querymap.put("group.6_group.p.or", "true");
    querymap.put("group.p.or", "true");
    querymap.put("3_group.property", "jcr:content/cq:template");
    querymap.put("3_group.property." + "1" + "_value",
        "/conf/ag/settings/wcm/templates/article-template-page");
    querymap.put("group.1_group.1_tagid.property", "@jcr:content/primaryTags");
    querymap.put("group.1_group.1_tagid.property.1_value", "italian");
    querymap.put("group.1_group.1_tagid.property.2_value", "crafts");
    Mockito.when(PredicateGroup.create(querymap)).thenReturn(predicateGroup);
    Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querymap), session))
        .thenReturn(query);
    Mockito.when(query.getResult()).thenReturn(result);
    Mockito.when(result.getHits()).thenReturn(hits);
    Mockito.when(hit.getProperties()).thenReturn(valueMap);
    getRelatedArticles.getRelatedArticles(queryDataMap);

  }

  @Test
  public void getRelatedArticlesTest6() throws RepositoryException {
    final Map<String, List<String>> queryDataMap = new HashMap<>();
    final List<String> secondaryTa = new ArrayList<>();
    secondaryTa.add("italian");
    secondaryTa.add("crafts");
    queryDataMap.put(Constants.SECONDARY_TAGS, secondaryTa);
    final Map<String, String> querymap = new HashMap<>();
    querymap.put("type", "cq:Page");
    querymap.put("p.limit", "3");
    querymap.put("orderby", "@jcr:content/datepickerarticle");
    querymap.put("orderby.sort", "desc");
    querymap.put("group.1_group.p.or", "true");
    querymap.put("group.2_group.p.or", "true");
    // querymap.put("group.6_group.p.or", "true");
    querymap.put("group.p.or", "true");
    querymap.put("3_group.property", "jcr:content/cq:template");
    querymap.put("3_group.property." + "1" + "_value",
        "/conf/ag/settings/wcm/templates/article-template-page");
    querymap.put("group.2_group.2_tagid.property", "@jcr:content/secondaryTags");
    querymap.put("group.1_group.1_tagid.property.1_value", "italian");
    querymap.put("group.1_group.1_tagid.property.2_value", "crafts");
    Mockito.when(PredicateGroup.create(querymap)).thenReturn(predicateGroup);
    Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querymap), session))
        .thenReturn(query);
    Mockito.when(query.getResult()).thenReturn(result);
    Mockito.when(result.getHits()).thenReturn(hits);
    Mockito.when(hit.getProperties()).thenReturn(valueMap);
    getRelatedArticles.getRelatedArticles(queryDataMap);

  }

  @Test
  public void getRelatedArticlesTest7() throws RepositoryException {
    final Map<String, List<String>> queryDataMap = new HashMap<>();
    final List<String> cqTa = new ArrayList<>();
    cqTa.add("italian");
    cqTa.add("crafts");
    queryDataMap.put(Constants.CQ_TAGS, cqTa);
    final Map<String, String> querymap = new HashMap<>();
    querymap.put("type", "cq:Page");
    querymap.put("p.limit", "3");
    querymap.put("orderby", "@jcr:content/datepickerarticle");
    querymap.put("orderby.sort", "desc");
    querymap.put("group.1_group.p.or", "true");
    querymap.put("group.2_group.p.or", "true");
    // querymap.put("group.6_group.p.or", "true");
    querymap.put("group.p.or", "true");
    querymap.put("3_group.property", "jcr:content/cq:template");
    querymap.put("3_group.property." + "1" + "_value",
        "/conf/ag/settings/wcm/templates/article-template-page");
    // querymap.put("group.6_group.6_tagid.property", "@jcr:content/cq:tags");
    querymap.put("group.1_group.1_tagid.property.1_value", "italian");
    querymap.put("group.1_group.1_tagid.property.2_value", "crafts");
    Mockito.when(PredicateGroup.create(querymap)).thenReturn(predicateGroup);
    Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querymap), session))
        .thenReturn(query);
    Mockito.when(query.getResult()).thenReturn(result);
    Mockito.when(result.getHits()).thenReturn(hits);
    Mockito.when(hit.getProperties()).thenReturn(valueMap);
    getRelatedArticles.getRelatedArticles(queryDataMap);

  }

  @Test
  public void getRelatedArticlesTest8() throws RepositoryException {
    final Map<String, List<String>> queryDataMap = new HashMap<>();
    final List<String> secondaryTa = new ArrayList<>();
    secondaryTa.add("24-10-2020");
    secondaryTa.add("24-10-2021");
    queryDataMap.put(Constants.ARTICLE_DATE, secondaryTa);
    final Map<String, String> querymap = new HashMap<>();
    querymap.put("type", "cq:Page");
    querymap.put("p.limit", "3");
    querymap.put("orderby", "@jcr:content/datepickerarticle");
    querymap.put("orderby.sort", "desc");
    querymap.put("group.1_group.p.or", "true");
    querymap.put("group.2_group.p.or", "true");
    // querymap.put("group.6_group.p.or", "true");
    querymap.put("group.p.or", "true");
    querymap.put("3_group.property", "jcr:content/cq:template");
    querymap.put("3_group.property." + "1" + "_value",
        "/conf/ag/settings/wcm/templates/article-template-page");
    querymap.put("group.2_group.2_tagid.property", "@jcr:content/secondaryTags");
    querymap.put("group.1_group.1_tagid.property.1_value", "italian");
    querymap.put("group.1_group.1_tagid.property.2_value", "crafts");
    Mockito.when(PredicateGroup.create(querymap)).thenReturn(predicateGroup);
    Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querymap), session))
        .thenReturn(query);
    Mockito.when(query.getResult()).thenReturn(result);
    Mockito.when(result.getHits()).thenReturn(hits);
    Mockito.when(hit.getProperties()).thenReturn(valueMap);
    getRelatedArticles.getRelatedArticles(queryDataMap);
  }

  @Test
  public void getRelatedArticlesTest9() throws RepositoryException {
    final Map<String, List<String>> queryDataMap = new HashMap<>();
    final List<String> secondaryTa = new ArrayList<>();
    secondaryTa.add("24-10-2020");
    queryDataMap.put(Constants.ARTICLE_DATE, secondaryTa);
    final Map<String, String> querymap = new HashMap<>();
    querymap.put("type", "cq:Page");
    querymap.put("p.limit", "3");
    querymap.put("orderby", "@jcr:content/datepickerarticle");
    querymap.put("orderby.sort", "desc");
    querymap.put("group.1_group.p.or", "true");
    querymap.put("group.2_group.p.or", "true");
    // querymap.put("group.6_group.p.or", "true");
    querymap.put("group.p.or", "true");
    querymap.put("3_group.property", "jcr:content/cq:template");
    querymap.put("3_group.property." + "1" + "_value",
        "/conf/ag/settings/wcm/templates/article-template-page");
    querymap.put("group.2_group.2_tagid.property", "@jcr:content/secondaryTags");
    querymap.put("group.1_group.1_tagid.property.1_value", "italian");
    querymap.put("group.1_group.1_tagid.property.2_value", "crafts");
    Mockito.when(PredicateGroup.create(querymap)).thenReturn(predicateGroup);
    Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querymap), session))
        .thenReturn(query);
    Mockito.when(query.getResult()).thenReturn(result);
    Mockito.when(result.getHits()).thenReturn(hits);
    Mockito.when(hit.getProperties()).thenReturn(valueMap);
    getRelatedArticles.getRelatedArticles(queryDataMap);
  }

  @Test
  public void getRelatedArticleDataUsingPathValueTest() throws RepositoryException {
    final Map<String, List<String>> queryDataMap = new HashMap<>();
    final List<String> templateTypes = new ArrayList<>();
    templateTypes.add("/conf/ag/settings/wcm/templates/article-template-page");
    queryDataMap.put(Constants.TEMPLATE_TYPES, templateTypes);
    final Map<String, String> querymap = new HashMap<>();
    querymap.put("type", "cq:Page");
    querymap.put("p.limit", "3");
    querymap.put("orderby", "@jcr:content/datepickerarticle");
    querymap.put("orderby.sort", "desc");
    querymap.put("group.1_group.p.or", "true");
    querymap.put("group.2_group.p.or", "true");
    // querymap.put("group.6_group.p.or", "true");
    querymap.put("group.p.or", "true");
    querymap.put("3_group.property", "jcr:content/cq:template");
    querymap.put("3_group.property." + "1" + "_value",
        "/conf/ag/settings/wcm/templates/article-template-page");
    Mockito.when(PredicateGroup.create(querymap)).thenReturn(predicateGroup);
    Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querymap), session))
        .thenReturn(query);
    Mockito.when(query.getResult()).thenReturn(result);
    Mockito.when(result.getHits()).thenReturn(hits);
    Mockito.when(hit.getProperties()).thenReturn(valueMap);
    getRelatedArticles.getRelatedArticleDataUsingPathValue(queryDataMap);
  }

  @Test
  public void testRemoveDuplicateArticles1() throws Exception {
    final List<RelatedArticlePojo> relatedArticlePojos = buildRelatedArticles();
    final List<RelatedArticlePojo>

    outputRelatedArticlePojos = getRelatedArticles.removeDuplicateArticles(Arrays.asList(), 1,
        relatedArticlePojos);

    Assert.assertNotNull(outputRelatedArticlePojos);
    Assert.assertEquals(1, outputRelatedArticlePojos.size());
    Assert.assertEquals("/articles/the-chemistry-of-frozen-treats.html",
        outputRelatedArticlePojos.get(0).getPagePath());
  }

  @Test
  public void testRemoveDuplicateArticles2() throws Exception {
    final List<RelatedArticlePojo> relatedArticlePojos = buildRelatedArticles();
    final List<RelatedArticlePojo>

    outputRelatedArticlePojos = getRelatedArticles.removeDuplicateArticles(
        Arrays.asList("/articles/the-chemistry-of-frozen-treats"), 1, relatedArticlePojos);

    Assert.assertNotNull(outputRelatedArticlePojos);
    Assert.assertEquals(1, outputRelatedArticlePojos.size());
    Assert.assertEquals("/articles/chocolate-chip-cookie-experiment.html",
        outputRelatedArticlePojos.get(0).getPagePath());
  }

  private List<RelatedArticlePojo> buildRelatedArticles() {
    final RelatedArticlePojo relatedArticlePojo1 = new RelatedArticlePojo();
    final RelatedArticlePojo relatedArticlePojo2 = new RelatedArticlePojo();

    relatedArticlePojo1.setPagePath("/articles/chocolate-chip-cookie-experiment.html");
    relatedArticlePojo2.setPagePath("/articles/the-chemistry-of-frozen-treats.html");
    return Arrays.asList(relatedArticlePojo1, relatedArticlePojo2);
  }

  @Test
  public void testGetTagRelatedDataStringArray1() throws Exception {
    final String[] primaryTags = new String[] { "Italian" };
    List<TagsPojo> tagsPojos;

    Mockito.when(tagManager.resolve("Italian")).thenReturn(tag);
    tagsPojos = getRelatedArticles.getTagRelatedData(primaryTags);
    Assert.assertNotNull(tagsPojos);
    Assert.assertEquals(1, tagsPojos.size());
  }

  @Test
  public void testGetTagRelatedDataStringArray2() throws Exception {
    final String[] primaryTags = new String[] { "Italian" };
    List<TagsPojo> tagsPojos;

    resolverFactory = Mockito.mock(ResourceResolverFactory.class);
    Mockito.when(resolverFactory.getServiceResourceResolver(ArgumentMatchers.any()))
        .thenThrow(new LoginException("Unable to find user"));
    tagsPojos = getRelatedArticles.getTagRelatedData(primaryTags);
    Assert.assertNotNull(tagsPojos);
    Assert.assertEquals(0, tagsPojos.size());
  }
}
