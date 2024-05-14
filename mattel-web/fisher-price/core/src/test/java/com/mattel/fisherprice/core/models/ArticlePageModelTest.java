package com.mattel.fisherprice.core.models;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Locale;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.Value;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.fisherprice.core.constants.Constants;
import com.mattel.fisherprice.core.utils.FisherPriceUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FisherPriceUtils.class)
public class ArticlePageModelTest {

  @InjectMocks
  private ArticlePageModel articlePageModel;

  @Mock
  private FisherPriceUtils fisherPriceUtils;

  @Mock
  private Resource resource;

  @Mock
  private Page currentPage;

  @Before
  public void setup() throws Exception {
    PowerMockito.mockStatic(FisherPriceUtils.class);
    ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
    Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(resource.getPath()).thenReturn("resourcepath");
    Resource articleResource = Mockito.mock(Resource.class);
    Mockito
        .when(
            resourceResolver.resolve("resourcepath/root/columncontrol/columnone/articlecomponent"))
        .thenReturn(articleResource);
    Node articleNode = Mockito.mock(Node.class);
    Mockito.when(articleResource.adaptTo(Node.class)).thenReturn(articleNode);
    PropertyIterator articleProperties = Mockito.mock(PropertyIterator.class);

    Mockito.when(articleNode.getProperties(Mockito.anyString())).thenReturn(articleProperties);
    Property property = Mockito.mock(Property.class);
    Mockito.when(articleNode.getProperty(Mockito.anyString())).thenReturn(property);
    Value propertyValue = Mockito.mock(Value.class);
    Mockito.when(property.getValue()).thenReturn(propertyValue);
    Mockito.when(propertyValue.getString()).thenReturn("propertyvalue");
    Locale locale = Locale.ENGLISH;
    Mockito.when(FisherPriceUtils.getPageLocale(currentPage)).thenReturn(locale);
    PageManager pageManager = Mockito.mock(PageManager.class);
    Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
    Page page = Mockito.mock(Page.class);
    Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
    ValueMap properties = Mockito.mock(ValueMap.class);
    Mockito.when(page.getProperties()).thenReturn(properties);

    Date createdDate = Mockito.mock(Date.class);
    Mockito.when(properties.get(JcrConstants.JCR_CREATED, Date.class)).thenReturn(createdDate);
    Mockito.when(FisherPriceUtils.formatDate(createdDate.toString(), FisherPriceUtils.DATE_FORMAT,
        "MMMM dd, yyyy hh:mm:ss z")).thenReturn("November 15, 2020 00:00:00 IST");

    Date publishedDate = Mockito.mock(Date.class);
    Mockito.when(properties.get(Constants.LAST_MODIFIED_DATE, Date.class))
        .thenReturn(publishedDate);
    Mockito.when(FisherPriceUtils.formatDate(publishedDate.toString(), FisherPriceUtils.DATE_FORMAT,
        "MMMM dd, yyyy hh:mm:ss z")).thenReturn("November 15, 2020 00:00:00 IST");
    Mockito.when(properties.get("articleId", String.class)).thenReturn("123");
  }

  @Test
  public void testInit() throws Exception {
    articlePageModel.init();
  }

  @Test
  public void testGettersSetters() throws IllegalArgumentException, IllegalAccessException {
    MemberModifier.field(ArticlePageModel.class, "articleDate").set(articlePageModel,
        "Jan 25 2020");
    MemberModifier.field(ArticlePageModel.class, "articleId").set(articlePageModel, "articleId");
    MemberModifier.field(ArticlePageModel.class, "articleImage").set(articlePageModel,
        "articleImage");
    MemberModifier.field(ArticlePageModel.class, "articleShotDescription").set(articlePageModel,
        "articleShotDescription");
    MemberModifier.field(ArticlePageModel.class, "articleTitle").set(articlePageModel,
        "articleTitle");
    MemberModifier.field(ArticlePageModel.class, "articleUpdate").set(articlePageModel,
        "articleUpdate");
    MemberModifier.field(ArticlePageModel.class, "assetIDimage").set(articlePageModel,
        "assetIDimage");
    MemberModifier.field(ArticlePageModel.class, "categoryPage").set(articlePageModel,
        "categoryPage");
    MemberModifier.field(ArticlePageModel.class, "imageserverurl").set(articlePageModel,
        "imageserverurl");
    MemberModifier.field(ArticlePageModel.class, "primaryTagIds").set(articlePageModel,
        "primaryTagIds");
    MemberModifier.field(ArticlePageModel.class, "primaryTagNames").set(articlePageModel,
        "primaryTagNames");
    MemberModifier.field(ArticlePageModel.class, "secondaryTagIds").set(articlePageModel,
        "secondaryTagIds");
    MemberModifier.field(ArticlePageModel.class, "secondaryTagNames").set(articlePageModel,
        "secondaryTagNames");

    assertEquals(articlePageModel.getArticleDate(), "Jan 25 2020");
    assertEquals(articlePageModel.getArticleId(), "articleId");
    assertEquals(articlePageModel.getArticleImage(), "articleImage");
    assertEquals(articlePageModel.getArticleShotDescription(), "articleShotDescription");
    assertEquals(articlePageModel.getArticleTitle(), "articleTitle");
    assertEquals(articlePageModel.getArticleUpdate(), "articleUpdate");
    assertEquals(articlePageModel.getAssetIDimage(), "assetIDimage");
    assertEquals(articlePageModel.getCategoryPage(), "categoryPage");
    assertEquals(articlePageModel.getImageserverurl(), "imageserverurl");
    assertEquals(articlePageModel.getPrimaryTagIds(), "primaryTagIds");
    assertEquals(articlePageModel.getPrimaryTagNames(), "primaryTagNames");
    assertEquals(articlePageModel.getSecondaryTagIds(), "secondaryTagIds");
    assertEquals(articlePageModel.getSecondaryTagNames(), "secondaryTagNames");
  }

}
