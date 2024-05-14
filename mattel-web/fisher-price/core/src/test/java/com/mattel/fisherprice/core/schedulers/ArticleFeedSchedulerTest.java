package com.mattel.fisherprice.core.schedulers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.fisherprice.core.services.UpdateCSVFile;
import com.mattel.fisherprice.core.utils.ArticleFeedConfigurationUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ArticleFeedConfigurationUtils.class)
public class ArticleFeedSchedulerTest {

  @InjectMocks
  private ArticleFeedScheduler articleFeedScheduler;
  
  @Mock
  private UpdateCSVFile updateCSVFile;

  @Before
  public void setup() {
    PowerMockito.mockStatic(ArticleFeedConfigurationUtils.class);
    Mockito.when(ArticleFeedConfigurationUtils.getDamPathToUploadArticleFeed())
        .thenReturn("/content/dam/fp-dam/articlefeed");
    Mockito.when(ArticleFeedConfigurationUtils.getArticleFeedFileInitialName())
        .thenReturn("articleFeed");
    String[] csvHeaders = { "Title", "Category" };
    Mockito.when(ArticleFeedConfigurationUtils.getHeaderLinesForCSV()).thenReturn(csvHeaders);
    Mockito.when(ArticleFeedConfigurationUtils.getArticleCompResourceType())
        .thenReturn("mattel/ecomm/fisher-price/components/content/articleComponent");
    Mockito.when(ArticleFeedConfigurationUtils.getBrandBasePath())
        .thenReturn("/content/fisher-price");
    Mockito.when(ArticleFeedConfigurationUtils.getArticleDetailTemplatePath())
        .thenReturn("/conf/fisher-price/settings/wcm/templates/article-details-page");
    Mockito.when(ArticleFeedConfigurationUtils.getArticleBasePath()).thenReturn("/home/articles");
    String[] localeFeed = {"en_us"};
    Mockito.when(ArticleFeedConfigurationUtils.getLocalesForArticleFeed()).thenReturn(localeFeed);
  }

  @Test
  public void test() {
    articleFeedScheduler.run();
  }

}
