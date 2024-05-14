package com.mattel.fisherprice.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.fisherprice.core.pojos.ArticlePojo;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ArticleFeedConfigurationUtils.class)
public class ConvertArticleFeedToCSVTest {

  @Test
  public void testConvertToCSVString() throws Exception {
    PowerMockito.mockStatic(ArticleFeedConfigurationUtils.class);
    List<ArticlePojo> articlePojos = new ArrayList<ArticlePojo>();
    ArticlePojo articlePojo = new ArticlePojo();
    articlePojo.setArticleAge("");
    articlePojo.setBrandLinkUrl("/brandurl");
    articlePojo.setCategoryId("categoryId");
    articlePojo.setCategoryLink("categoryLink");
    articlePojo.setCategoryName("categoryName");
    articlePojo.setCategoryTitle("categoryTitle");
    articlePojo.setCreated("admin");
    articlePojo.setId("11");
    articlePojo.setInventory("available");
    articlePojo.setIsPublished("true");
    articlePojo.setLanguage("en");
    articlePojo.setName("name");
    articlePojos.add(articlePojo);
    String[] headerLinesForCSV = {"id","title","category"};
    Mockito.when(ArticleFeedConfigurationUtils.getHeaderLinesForCSV()).thenReturn(headerLinesForCSV);
    ConvertArticleFeedToCSV.convertToCSVString(articlePojos);
  }
}
