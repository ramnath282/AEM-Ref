package com.mattel.global.core.model;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Assert;
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

import com.adobe.cq.wcm.core.components.models.List;
import com.adobe.cq.wcm.core.components.models.ListItem;
import com.adobe.cq.wcm.core.components.models.Page;
import com.mattel.global.core.pojo.SiteNavigationPojo;
import com.mattel.global.core.utils.GlobalUtils;
import com.mattel.global.core.utils.NavigationUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GlobalUtils.class)
public class ListModelTest {
  @InjectMocks
  private ListModel listModel;

  @Mock
  private Resource resource;

  @Mock
  private ResourceResolver resourceResolver;

  @Mock
  private List list;

  @Mock
  private NavigationUtil navigationService;

  @Before
  public void setup() throws Exception {
    PowerMockito.mockStatic(GlobalUtils.class);
    MemberModifier.field(ListModel.class, "resource").set(listModel, resource);
    MemberModifier.field(ListModel.class, "maxItems").set(listModel, "2");
    MemberModifier.field(ListModel.class, "navigationService").set(listModel, navigationService);
    MemberModifier.field(ListModel.class, "resource").set(listModel, resource);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testInit_WithChildPages() throws IllegalArgumentException, IllegalAccessException {
    Collection<ListItem> listItems = Mockito.mock(Collection.class);
    Iterator<ListItem> listItemsItr = Mockito.mock(Iterator.class);
    Resource imageResource = Mockito.mock(Resource.class);
    Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
    MemberModifier.field(ListModel.class, "contentPages").set(listModel, null);
    ListItem item = Mockito.mock(ListItem.class);

    Mockito.when(list.getListItems()).thenReturn(listItems);
    Mockito.when(listItems.iterator()).thenReturn(listItemsItr);
    Mockito.when(listItemsItr.hasNext()).thenReturn(true, false);
    Mockito.when(listItemsItr.next()).thenReturn(item);
    Mockito.when(item.getPath()).thenReturn("/content/fisher-price/de/de/fisher-price-go-live");
    Mockito.when(resourceResolver.resolve(Mockito.anyString())).thenReturn(imageResource);
    listModel.init();
  }

  @Test
  public void testInitWithStaticPagesAscByName()
      throws IllegalArgumentException, IllegalAccessException {
    MemberModifier.field(ListModel.class, "sortOrder").set(listModel, "asc");
    MemberModifier.field(ListModel.class, "orderBy").set(listModel, "title");
    runTest();
    listModel.init();
  }

  @Test
  public void testInitWithStaticPagesDescByName()
      throws IllegalArgumentException, IllegalAccessException {
    MemberModifier.field(ListModel.class, "sortOrder").set(listModel, "desc");
    MemberModifier.field(ListModel.class, "orderBy").set(listModel, "title");
    runTest();

    listModel.init();
  }

  @Test
  public void testInitWithStaticPagesDescByDateAsc()
      throws IllegalArgumentException, IllegalAccessException {
    MemberModifier.field(ListModel.class, "sortOrder").set(listModel, "asc");
    MemberModifier.field(ListModel.class, "orderBy").set(listModel, "modified");
    runTest();
    listModel.init();
  }

  @Test
  public void testInitWithStaticPagesDescByDateDesc()
      throws IllegalArgumentException, IllegalAccessException {
    MemberModifier.field(ListModel.class, "sortOrder").set(listModel, "desc");
    MemberModifier.field(ListModel.class, "orderBy").set(listModel, "modified");
    runTest();
    listModel.init();
  }

  private void runTest() throws IllegalArgumentException, IllegalAccessException {
    String[] paths = { "/content/fisher-price/de/de/fisher-price-go-live",
        "/content/fisher-price/de/de/news_emailing_thankyou" };
    @SuppressWarnings("unchecked")
    Collection<ListItem> listItems = Mockito.mock(Collection.class);
    @SuppressWarnings("unchecked")
    Iterator<ListItem> listItemsItr = Mockito.mock(Iterator.class);
    Page page = Mockito.mock(Page.class);
    Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
    MemberModifier.field(ListModel.class, "contentPages").set(listModel, paths);
    ListItem item = Mockito.mock(ListItem.class);
    Mockito.when(list.getListItems()).thenReturn(listItems);
    Mockito.when(listItems.iterator()).thenReturn(listItemsItr);
    Mockito.when(listItemsItr.hasNext()).thenReturn(true, false);
    Mockito.when(listItemsItr.next()).thenReturn(item);
    Mockito.when(resourceResolver.resolve(Mockito.anyString())).thenReturn(resource);
    Mockito.when(resource.adaptTo(Page.class)).thenReturn(page);

    listModel.init();
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testGetters() throws IllegalArgumentException, IllegalAccessException {
    java.util.List<SiteNavigationPojo> staticPagesList = Mockito.mock(java.util.List.class);
    MemberModifier.field(ListModel.class, "staticPagesList").set(listModel, staticPagesList);
    listModel.getListItems();
    listModel.getChildPagesList();
    listModel.getStaticPagesList();

    MemberModifier.field(ListModel.class, "showMoreText").set(listModel, "<p>test</p>");
    MemberModifier.field(ListModel.class, "showLessText").set(listModel, "<p>test</p>");
    MemberModifier.field(ListModel.class, "linkText").set(listModel, "<p>test</p>");
    MemberModifier.field(ListModel.class, "backgroundImagePath").set(listModel,
        "backgroundImagePath");

    Mockito.when(GlobalUtils.removeTags("<p>test</p>", new String[] { "<p>", "</p>" },
        new String[] { StringUtils.EMPTY, StringUtils.EMPTY })).thenReturn("test");

    Assert.assertNotNull(listModel.getBackgroundImagePath());
    Assert.assertNotNull(listModel.getShowMoreText());
    Assert.assertNotNull(listModel.getShowLessText());
    Assert.assertNotNull(listModel.getLinkText());
  }
}
