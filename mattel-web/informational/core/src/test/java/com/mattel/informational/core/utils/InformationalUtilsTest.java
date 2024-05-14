package com.mattel.informational.core.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.informational.core.helper.InformationalHelper;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ InformationalConfigurationUtils.class, InformationalHelper.class })
public class InformationalUtilsTest {
  @InjectMocks
  private InformationalUtils informationalUtils;

  @SuppressWarnings("static-access")
  @Test
  public void testGetValueFromKeyMappings() {
    String[] idMappings = { "id1:1", "id1:2" };
    informationalUtils.getValueFromKeyMappings(idMappings, "id1");
  }

  @SuppressWarnings("static-access")
  @Test
  public void testGetHrefLangPropertyList() throws RepositoryException {
    PowerMockito.mockStatic(InformationalHelper.class);
    Resource resource = Mockito.mock(Resource.class);
    Mockito.when(resource.getPath())
        .thenReturn("/mattel/mattel-corporate/language-masters/en/home");
    Mockito
        .when(InformationalHelper
            .getRelativePath("/mattel/mattel-corporate/language-masters/en/home", resource))
        .thenReturn("en");

    ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
    Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
    Session session = Mockito.mock(Session.class);
    Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
    QueryBuilder queryBuilder = Mockito.mock(QueryBuilder.class);
    Mockito.when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
    SearchResult result = Mockito.mock(SearchResult.class);
    Mockito.when(InformationalHelper.getCountryNodesByLanguage("/mattel/mattel-corporate", session,
        queryBuilder)).thenReturn(result);
    Hit hit = Mockito.mock(Hit.class);
    List<Hit> hits = new ArrayList<Hit>();
    hits.add(hit);
    Mockito.when(result.getHits()).thenReturn(hits);
    PageManager pageManager = Mockito.mock(PageManager.class);
    Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(hit.getPath()).thenReturn("pagepath");
    Page currentPage = Mockito.mock(Page.class);
    Page localPage = Mockito.mock(Page.class);
    Page homepage = Mockito.mock(Page.class);
    Mockito.when(pageManager.getPage("pagepath")).thenReturn(currentPage);
    Mockito.when(pageManager.getPage("pagepath/hom")).thenReturn(localPage);
    Mockito.when(pageManager.getPage("pagepath/home")).thenReturn(homepage);
    ValueMap propertis = Mockito.mock(ValueMap.class);
    Mockito.when(homepage.getProperties()).thenReturn(propertis);
    Mockito.when(propertis.containsKey("cq:redirectTarget")).thenReturn(true);
    Mockito.when(propertis.get("cq:redirectTarget")).thenReturn("redirectpath");

    @SuppressWarnings("unchecked")
    Iterator<Resource> resources = Mockito.mock(Iterator.class);
    Mockito.when(result.getResources()).thenReturn(resources);
    Mockito.when(resources.hasNext()).thenReturn(true, false);
    Resource resource1 = Mockito.mock(Resource.class);
    Mockito.when(resources.next()).thenReturn(resource1);
    ResourceResolver leakingResResolver = Mockito.mock(ResourceResolver.class);
    Mockito.when(resource1.getResourceResolver()).thenReturn(leakingResResolver);

    Mockito.when(leakingResResolver.isLive()).thenReturn(false);
    informationalUtils.getHrefLangPropertyList("mattel-corporate", resource);
  }

  @SuppressWarnings("static-access")
  @Test
  public void testGetHrefLangPropertyList_1() throws RepositoryException {
    PowerMockito.mockStatic(InformationalHelper.class);
    Resource resource = Mockito.mock(Resource.class);
    Mockito.when(resource.getPath())
        .thenReturn("/mattel/mattel-corporate/language-masters/en/home");
    Mockito
        .when(InformationalHelper
            .getRelativePath("/mattel/mattel-corporate/language-masters/en/home", resource))
        .thenReturn("en");

    ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
    Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
    Session session = Mockito.mock(Session.class);
    Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
    QueryBuilder queryBuilder = Mockito.mock(QueryBuilder.class);
    Mockito.when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
    SearchResult result = Mockito.mock(SearchResult.class);
    Mockito.when(InformationalHelper.getCountryNodesByLanguage("/mattel/mattel-corporate", session,
        queryBuilder)).thenReturn(result);
    Hit hit = Mockito.mock(Hit.class);
    List<Hit> hits = new ArrayList<Hit>();
    hits.add(hit);
    Mockito.when(result.getHits()).thenReturn(hits);
    PageManager pageManager = Mockito.mock(PageManager.class);
    Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(hit.getPath()).thenReturn("pagepath");
    Page localPage = Mockito.mock(Page.class);
    Page homepage = Mockito.mock(Page.class);
    Mockito.when(pageManager.getPage("pagepath")).thenReturn(null);
    Mockito.when(pageManager.getPage("pagepath/hom")).thenReturn(localPage);
    Mockito.when(pageManager.getPage("pagepath/home")).thenReturn(homepage);
    ValueMap propertis = Mockito.mock(ValueMap.class);
    Mockito.when(homepage.getProperties()).thenReturn(propertis);
    Mockito.when(propertis.containsKey("cq:redirectTarget")).thenReturn(true);
    Mockito.when(propertis.get("cq:redirectTarget")).thenReturn("redirectpath");

    @SuppressWarnings("unchecked")
    Iterator<Resource> resources = Mockito.mock(Iterator.class);
    Mockito.when(result.getResources()).thenReturn(resources);
    Mockito.when(resources.hasNext()).thenReturn(true, false);
    Resource resource1 = Mockito.mock(Resource.class);
    Mockito.when(resources.next()).thenReturn(resource1);
    ResourceResolver leakingResResolver = Mockito.mock(ResourceResolver.class);
    Mockito.when(resource1.getResourceResolver()).thenReturn(leakingResResolver);

    Mockito.when(leakingResResolver.isLive()).thenReturn(false);
    informationalUtils.getHrefLangPropertyList("mattel-corporate", resource);
  }

  @SuppressWarnings("static-access")
  @Test
  public void testGetHrefLangPropertyList_2() throws RepositoryException {
    PowerMockito.mockStatic(InformationalHelper.class);
    Resource resource = Mockito.mock(Resource.class);
    Mockito.when(resource.getPath())
        .thenReturn("/mattel/mattel-corporate/language-masters/en/home");
    Mockito
        .when(InformationalHelper
            .getRelativePath("/mattel/mattel-corporate/language-masters/en/home", resource))
        .thenReturn("en");

    ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
    Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
    Session session = Mockito.mock(Session.class);
    Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
    QueryBuilder queryBuilder = Mockito.mock(QueryBuilder.class);
    Mockito.when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
    SearchResult result = Mockito.mock(SearchResult.class);
    Mockito.when(InformationalHelper.getCountryNodesByLanguage("/mattel/mattel-corporate", session,
        queryBuilder)).thenReturn(result);
    Hit hit = Mockito.mock(Hit.class);
    List<Hit> hits = new ArrayList<Hit>();
    hits.add(hit);
    Mockito.when(result.getHits()).thenReturn(hits);
    PageManager pageManager = Mockito.mock(PageManager.class);
    Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(hit.getPath()).thenReturn("pagepath");
    Page currentPage = Mockito.mock(Page.class);
    Page homepage = Mockito.mock(Page.class);
    Mockito.when(pageManager.getPage("pagepath")).thenReturn(currentPage);
    Mockito.when(pageManager.getPage("pagepath/hom")).thenReturn(null);
    Mockito.when(pageManager.getPage("pagepath/home")).thenReturn(homepage);
    ValueMap propertis = Mockito.mock(ValueMap.class);
    Mockito.when(homepage.getProperties()).thenReturn(propertis);
    Mockito.when(propertis.containsKey("cq:redirectTarget")).thenReturn(true);
    Mockito.when(propertis.get("cq:redirectTarget")).thenReturn("redirectpath");

    @SuppressWarnings("unchecked")
    Iterator<Resource> resources = Mockito.mock(Iterator.class);
    Mockito.when(result.getResources()).thenReturn(resources);
    Mockito.when(resources.hasNext()).thenReturn(true, false);
    Resource resource1 = Mockito.mock(Resource.class);
    Mockito.when(resources.next()).thenReturn(resource1);
    ResourceResolver leakingResResolver = Mockito.mock(ResourceResolver.class);
    Mockito.when(resource1.getResourceResolver()).thenReturn(leakingResResolver);

    Mockito.when(leakingResResolver.isLive()).thenReturn(false);
    informationalUtils.getHrefLangPropertyList("mattel-corporate", resource);
  }

  @SuppressWarnings("static-access")
  @Test
  public void testGetUnixDate() {
    String dateFormat = "E MMM dd HH:mm:ss Z yyyy";
    String timeStamp = "Wed Oct 16 00:00:00 CEST 2013";
    informationalUtils.getUnixDate(timeStamp, dateFormat);
  }

  @SuppressWarnings("static-access")
  @Test
  public void testGetUnixDateWithException() {
    String dateFormat = "E MMM dd HH:mm:ss Z yyyy";
    String timeStamp = new Timestamp(System.currentTimeMillis()).toString();
    informationalUtils.getUnixDate(timeStamp, dateFormat);
  }

  @SuppressWarnings("static-access")
  @Test
  public void testFormatDate() {
    String dateFormat = "E MMM dd HH:mm:ss Z yyyy";
    String timeStamp = "Wed Oct 16 00:00:00 CEST 2013";
    informationalUtils.formatDate(timeStamp, dateFormat, "MMMM dd, yyyy hh:mm:ss z");
  }

  @SuppressWarnings("static-access")
  @Test
  public void testFormatDateWithException() {
    String dateFormat = "E MM dd HH:mm:ss Z yyyy";
    String timeStamp = "Wed Oct 16 00:00:00 CEST 2013";
    informationalUtils.formatDate(timeStamp, dateFormat, "MMMM dd, yyyy hh:mm:ss z");
  }

  @SuppressWarnings("static-access")
  @Test
  public void testGetPageLocaleForLanguagePages() {
    Page page = Mockito.mock(Page.class);
    Page parentPage = Mockito.mock(Page.class);
    Mockito.when(page.getAbsoluteParent(3)).thenReturn(parentPage);
    Mockito.when(parentPage.getPath()).thenReturn("/content/fisher-price/en-us");
    informationalUtils.getPageLocale(page);
  }

  @SuppressWarnings("static-access")
  @Test
  public void testGetPageLocaleForLanguagemaster() {
    Page page = Mockito.mock(Page.class);
    Page parentPage = Mockito.mock(Page.class);
    Mockito.when(page.getAbsoluteParent(3)).thenReturn(parentPage);
    Mockito.when(parentPage.getPath()).thenReturn("/content/fisher-price/language-masters/en_us");
    informationalUtils.getPageLocale(page);
  }

  @SuppressWarnings("static-access")
  @Test
  public void testGetCurrentBrandExpFragmentRootPath() {
    PowerMockito.mockStatic(InformationalConfigurationUtils.class);
    String[] brandsPath = { "mattel-corporate-new:/content/experience-fragments/mattel/" };
    Mockito.when(InformationalConfigurationUtils.getExpFragmentRootPathArray())
        .thenReturn(brandsPath);
    informationalUtils.getCurrentBrandExpFragmentRootPath("mattel-corporate-new",
        "expOrSiteRootPath",
        "/content/mattel/mattel-corporate/us/en-us/home/about/executive-leadership/jcr:content");
  }

  @SuppressWarnings("static-access")
  @Test
  public void testGetCurrentBrandExpFragmentRootPath_1() {
    PowerMockito.mockStatic(InformationalConfigurationUtils.class);
    String[] brandsPath = { "mattel-corporate-new:/content/experience-fragments/mattel/" };
    Mockito.when(InformationalConfigurationUtils.getExpFragmentRootPathArray())
        .thenReturn(brandsPath);
    informationalUtils.getCurrentBrandExpFragmentRootPath("mattel-corporate-new", null,
        "/content/mattel/mattel-corporate/us/en-us/home/about/executive-leadership/jcr:content");
  }

  @SuppressWarnings({ "static-access", "unused" })
  @Test
  public void testGetCurrentBrandExpFragmentRootPath_2() {
    PowerMockito.mockStatic(InformationalConfigurationUtils.class);
    String[] brandsPath = { "mattel-corporate-new:/content/experience-fragments/mattel/" };
    Mockito.when(InformationalConfigurationUtils.getExpFragmentRootPathArray()).thenReturn(null);
    informationalUtils.getCurrentBrandExpFragmentRootPath("mattel-corporate-new",
        "expOrSiteRootPath",
        "/content/mattel/mattel-corporate/us/en-us/home/about/executive-leadership/jcr:content");
  }
}
