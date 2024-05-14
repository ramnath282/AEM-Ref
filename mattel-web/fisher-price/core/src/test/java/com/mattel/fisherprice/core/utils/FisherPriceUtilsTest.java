package com.mattel.fisherprice.core.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.mattel.fisherprice.core.pojos.TagsPojo;
import com.mattel.fisherprice.core.services.RelatedArticleService;

@RunWith(PowerMockRunner.class)
public class FisherPriceUtilsTest {

  @InjectMocks
  private FisherPriceUtils fisherPriceUtils;

  @Mock
  private RelatedArticleService relatedArticleService;

  @Before
  public void setup() throws Exception {

  }

  @Test
  public void testGetTagDataWithTitle() {
    Page page = Mockito.mock(Page.class);
    Locale locale = Locale.US;
    ValueMap properties = Mockito.mock(ValueMap.class);
    Mockito.when(page.getProperties()).thenReturn(properties);
    String[] tags = { "pt1", "pt2" };
    Mockito.when(properties.get("primarytag", String[].class)).thenReturn(tags);
    List<TagsPojo> tagPojoList = new ArrayList<TagsPojo>();
    TagsPojo tagPojo = new TagsPojo();
    tagPojoList.add(tagPojo);
    Mockito.when(relatedArticleService.getTagRelatedData(tags, locale)).thenReturn(tagPojoList);
    fisherPriceUtils.getTagData(page, "primarytag", "tagtitle", ",", locale);
  }
 
  @Test
  public void testGetTagDataWithId() {
    Page page = Mockito.mock(Page.class);
    Locale locale = Locale.US;
    ValueMap properties = Mockito.mock(ValueMap.class);
    Mockito.when(page.getProperties()).thenReturn(properties);
    String[] tags = { "pt1", "pt2" };
    Mockito.when(properties.get("primarytag", String[].class)).thenReturn(tags);
    List<TagsPojo> tagPojoList = new ArrayList<TagsPojo>();
    TagsPojo tagPojo = new TagsPojo();
    tagPojoList.add(tagPojo);
    Mockito.when(relatedArticleService.getTagRelatedData(tags, locale)).thenReturn(tagPojoList);
    fisherPriceUtils.getTagData(page, "primarytag", "tagid", ",", locale);
  }
  
  @SuppressWarnings("static-access")
  @Test
  public void testGetUnixDate(){
    String dateFormat = "E MMM dd HH:mm:ss Z yyyy";
    String timeStamp = "Wed Oct 16 00:00:00 CEST 2013";
    fisherPriceUtils.getUnixDate(timeStamp, dateFormat);
  }
  
  @SuppressWarnings("static-access")
  @Test
  public void testGetUnixDateWithException(){
    String dateFormat = "E MMM dd HH:mm:ss Z yyyy";
    String timeStamp = new Timestamp(System.currentTimeMillis()).toString();
    fisherPriceUtils.getUnixDate(timeStamp, dateFormat);
  }
  
  @SuppressWarnings("static-access")
  @Test
  public void testFormatDate(){
    String dateFormat = "E MMM dd HH:mm:ss Z yyyy";
    String timeStamp = "Wed Oct 16 00:00:00 CEST 2013";
    fisherPriceUtils.formatDate(timeStamp, dateFormat, "MMMM dd, yyyy hh:mm:ss z");
  }
  
  @SuppressWarnings("static-access")
  @Test
  public void testFormatDateWithException(){
    String dateFormat = "E MMM dd HH:mm:ss Z yyyy";
    String timeStamp = new Timestamp(System.currentTimeMillis()).toString();
    fisherPriceUtils.formatDate(timeStamp, dateFormat, "MMMM dd, yyyy hh:mm:ss z");
  }
  
  @SuppressWarnings("static-access")
  @Test
  public void testGetPageLocaleForLanguagePages(){
    Page page = Mockito.mock(Page.class);
    Page parentPage = Mockito.mock(Page.class);
    Mockito.when(page.getAbsoluteParent(3)).thenReturn(parentPage);
    Mockito.when(parentPage.getPath()).thenReturn("/content/fisher-price/en-us");
    fisherPriceUtils.getPageLocale(page);
  }
  
  @SuppressWarnings("static-access")
  @Test
  public void testGetPageLocaleForLanguagemaster(){
    Page page = Mockito.mock(Page.class);
    Page parentPage = Mockito.mock(Page.class);
    Mockito.when(page.getAbsoluteParent(3)).thenReturn(parentPage);
    Mockito.when(parentPage.getPath()).thenReturn("/content/fisher-price/language-masters/en_us");
    fisherPriceUtils.getPageLocale(page);
  }
  
}
