package com.mattel.play.core.model;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.play.core.pojos.TilePojo;
import com.mattel.play.core.services.TileGalleryAndLandingService;
import com.mattel.play.core.utils.CategoryFilterSlidesUtils;
import com.mattel.play.core.utils.PropertyReaderUtils;

public class CharacterDetailsModelTest {

  CharacterDetailsModel characterDetailsModel = new CharacterDetailsModel();

  TileGalleryAndLandingService tileGalleryAndLandingService;
  String homePagePath = "/content/mattel-play/polly-pocket/language-masters/en/home/characters/jcr:content/root/characterlandinggrid/";
  String prevCharacter = "";
  String nextCharacter = "";
  String categoryName = "";
  String manual = "";
  String automatic = "";
  String rootPath = "/content/mattel-play/polly-pocket/language-masters/en/home/characters";
  String orderCharacter = "";
  List<TilePojo> tileList = new ArrayList<>();
  Resource resource;
  PageManager pageManager;
  Page page;
  ResourceResolver resolver;
  String tempPath = "";
  Boolean checkPath = true;
  int currentIndex;
  int lastIndex;
  String tileType = "characters";
  String tilePage = "landing";
  Node detailNode;
  Node node;
  Property property;
  String slideCount = "6";

  @Before
  public void setUp() throws PathNotFoundException, RepositoryException {

    manual = "manual";
    automatic = "automatic";
    pageManager = Mockito.mock(PageManager.class);
    page = Mockito.mock(Page.class);
    resource = Mockito.mock(Resource.class);
    node = Mockito.mock(Node.class);
    resolver = Mockito.mock(ResourceResolver.class);
    final TilePojo characterPojo = new TilePojo();
    property = Mockito.mock(Property.class);
    tileType = "characters";
    tilePage = "landing";
    tileGalleryAndLandingService = Mockito.mock(TileGalleryAndLandingService.class);
    detailNode = Mockito.mock(Node.class);
    orderCharacter = "manual";
    characterDetailsModel.setNode(node);
    characterDetailsModel.setResource(resource);
    characterDetailsModel.setTileGalleryAndLandingService(tileGalleryAndLandingService);
    characterDetailsModel.setAutomatic(automatic);
    characterDetailsModel.setCategoryName(categoryName);
    characterDetailsModel.setManual(manual);
    characterDetailsModel.setNextCharacter(nextCharacter);
    characterDetailsModel.setNode(detailNode);
    characterDetailsModel.setOrderCharacter(orderCharacter);
    characterDetailsModel.setPrevCharacter(prevCharacter);
    characterDetailsModel.setHomePagePath(homePagePath);
    characterDetailsModel.setRootPagePath(rootPath);
    characterDetailsModel.setCurrentPath("currentpath");
    characterDetailsModel.setRootPath(rootPath);
    characterDetailsModel.setSlideCount("6");
    characterPojo.setTilePageName("page");
    characterPojo.setTilePath("");
    characterPojo.setAlwaysEnglish("adfsdf");
    characterPojo.setHoverOverImg("adfsdf");
    characterPojo.setHoverOverImgAlt("adfsdf");
    characterPojo.setTileCategory("adfsdf");
    characterPojo.setTileImage("adfsdf");
    characterPojo.setTileImgAltText("adfsdf");
    characterPojo.setTileTags(new ArrayList<>());
    characterPojo.setTileTitle("adfsdf");
    tileList.add(characterPojo);
    tileList.add(characterPojo);
    tileList.add(characterPojo);

    Mockito.when(resource.getResourceResolver()).thenReturn(resolver);

    Mockito.when(resource.getResourceResolver().adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);

    Mockito.when(page.getPath()).thenReturn(".html");
    Mockito.when(page.getParent()).thenReturn(page);
    Mockito.when(page.getParent().getTitle()).thenReturn(categoryName);
    Mockito.when(page.getAbsoluteParent(6)).thenReturn(page);
    Mockito.when(page.getAbsoluteParent(6).getPath()).thenReturn("");
    Mockito.when(resolver.getResource(homePagePath)).thenReturn(resource);
    Mockito.when(resource.adaptTo(Node.class)).thenReturn(detailNode);
    Mockito.when(detailNode.getProperty("orderCharacter")).thenReturn(property);
    Mockito.when(detailNode.getProperty("orderCharacter").getString()).thenReturn(orderCharacter);
    Mockito.when(tileGalleryAndLandingService.getAllTiles(homePagePath, tileType, tilePage, true))
        .thenReturn(tileList);
    Mockito.when(tileGalleryAndLandingService.getTilesByDate(rootPath, tileType, null,
        resource.getResourceResolver(), true)).thenReturn(tileList);

  }

  @Test
  public void init() {

    characterDetailsModel.init();

  }

  @Test
  public void getTileList() {
    characterDetailsModel.getTileList();
  }

  @Test
  public void getHomePagePath() {
    characterDetailsModel.getHomePagePath();
  }

  @Test
  public void getCategoryName() {
    characterDetailsModel.getCategoryName();
  }

  @Test
  public void getPrevCharacter() {
    characterDetailsModel.getPrevCharacter();
  }

  @Test
  public void getNextCharacter() {
    characterDetailsModel.getNextCharacter();
  }

  @Test
  public void getResource() {
    characterDetailsModel.getResource();
  }

  @Test
  public void testGetSlideCount() {
    characterDetailsModel.getSlideCount();
  }
  
  @Test
  public void testSetTileList(){
    characterDetailsModel.setTileList(tileList);
  }

  @Test
  public void testGetCharacterCarouselSlidesMappings() throws Exception {
    final CategoryFilterSlidesUtils utils1 = new CategoryFilterSlidesUtils();
    final CategoryFilterSlidesUtils.Config config1 = Mockito
        .mock(CategoryFilterSlidesUtils.Config.class);

    Mockito.when(config1.characterSlidesValueMapping())
        .thenReturn(new String[] { "play:core:content" });
    Mockito.when(config1.slideShowValueMapping()).thenReturn(new String[] { "play:core:content" });
    utils1.activate(config1);

    final PropertyReaderUtils utils2 = new PropertyReaderUtils();
    final PropertyReaderUtils.Config config2 = Mockito.mock(PropertyReaderUtils.Config.class);

    Mockito.when(config2.playPath()).thenReturn("/play");
    utils2.activate(config2);

    Assert.assertEquals("1",
        characterDetailsModel.getCharacterCarouselSlidesMappings("/play/play", "1"));
  }
}
