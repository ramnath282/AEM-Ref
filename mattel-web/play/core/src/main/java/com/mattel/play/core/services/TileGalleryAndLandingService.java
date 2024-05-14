package com.mattel.play.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import com.mattel.play.core.constants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.mattel.play.core.helper.PlayHelper;
import com.mattel.play.core.pojos.ArticlePojo;
import com.mattel.play.core.pojos.CategoryFilterPojo;
import com.mattel.play.core.pojos.CategoryPojo;
import com.mattel.play.core.pojos.RetailerPojo;
import com.mattel.play.core.pojos.SiteNavigationPojo;
import com.mattel.play.core.pojos.TagsPojo;
import com.mattel.play.core.pojos.TilePojo;
import com.mattel.play.core.utils.PropertyReaderUtils;

@Component(service = TileGalleryAndLandingService.class, immediate = true)
@Designate(ocd = TileGalleryAndLandingService.Config.class)
public class TileGalleryAndLandingService {

	@Reference
	SlingSettingsService slingSettingsService;
	private static final Logger LOGGER = LoggerFactory.getLogger(TileGalleryAndLandingService.class);
	private static final String NAV_THUMB_IMAGE = "navThumbImage";
	private static final String CQ_REDIRECT_TARGET = "cq:redirectTarget";
	private static final String CQ_REDIRECT_TARGET_OPTIONS = "cq:redirectTargetOption";
	private static final String ADOBE_TRACKING_NAME_FOR_PAGE = "adobeTrackingNameForPage";

	@Reference
	ResourceResolverFactory resolverFactory;
	@Reference
	QueryBuilder queryBuilder;
	ResourceResolver resolver;
	Session session;
	List<TilePojo> masterList = new ArrayList<>();
	List<ArticlePojo> masterArticleList = new LinkedList<>();
	String service = "readwriteservice";
	String serviceUser = "playserviceuser";
	String tileThumbnailProp = "tileThumbnail";
	String hoverOverProp = "hoveroverimg";
	String tileAltTextProp = "tileAltTxt";
	String descLineOneProp = "descLineOne";
	String descLineTwoProp = "descLineTwo";
	String hoverOverAltProp = "hoverOverAlt";
	String alwaysEnglishProp = "alwaysEnglish";
	String gamesTileType = "games";
	String articleTitle = "articleTitle";
	String articleshortDesc = "articleshortDesc";
	String articleThumbnailpath = "articleThumbnailpath";
	String alwaysEnglish = "alwaysEnglish";
	String orderby = "orderby";
	String primaryTags = "primaryTags";

	/**
	 * Method for getting all the Categories
	 *
	 * @return List of {@link CategoryFilterPojo}
	 */

	public List<CategoryPojo> getCategories(List<CategoryFilterPojo> catItemsList) {
		LOGGER.info("Start of getCategories method");
		masterList.clear();
		List<CategoryPojo> categoryItemsList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, service);
		map.put(ResourceResolverFactory.USER, serviceUser);
		try {
			if (resolverFactory != null) {
				resolver = resolverFactory.getServiceResourceResolver(map);
			}

			Iterator<CategoryFilterPojo> iterator = catItemsList.iterator();
			while (iterator.hasNext()) {
				PageManager pageManager = resolver.adaptTo(PageManager.class);
				if (pageManager != null) {
					CategoryFilterPojo catFilPojo;
					catFilPojo = iterator.next();
					Page page = pageManager.getPage(catFilPojo.getCategoryPath());
					if (page != null) {
						CategoryPojo catPojo = new CategoryPojo();
						catPojo.setCatName(page.getTitle());
						catPojo.setCatUrl(page.getPath());
						Tag[] categoryTags = page.getTags();
						if (null != categoryTags && categoryTags.length > 0 && null != categoryTags[0]) {
							Tag categoryTag = categoryTags[0];
							catPojo.setAnalyticsCategoryName(categoryTag.getTitle());
						}
						categoryItemsList.add(catPojo);
					}
				}
			}

			LOGGER.debug("categoryItemsList size {}", categoryItemsList.size());

		} catch (LoginException e) {
			LOGGER.error("Exception caused in Child page properties Service", e);
		} finally {
			if (null != resolver && resolver.isLive()) {
				resolver.close();
			}
		}
		LOGGER.info("End of getCategories method");
		return categoryItemsList;
	}

	public void setSlingSettingsService(SlingSettingsService slingSettingsService) {
		this.slingSettingsService = slingSettingsService;
	}

	public void setResolverFactory(ResourceResolverFactory resolverFactory) {
		this.resolverFactory = resolverFactory;
	}

	public void setQueryBuilder(QueryBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}

	public void setResolver(ResourceResolver resolver) {
		this.resolver = resolver;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	/**
	 * Method for getting all the Characters
	 *
	 * @param linkToPages
	 *            *
	 *
	 * @return List of {@link TilePojo}
	 */
	public List<TilePojo> getAllTiles(String parentPath, String tileType, String tilePage, boolean linkToPages) {

		LOGGER.info("start of getAllTiles method ");
		List<TilePojo> nodeList1 = new ArrayList<>();
		List<TilePojo> nodeList2 = new ArrayList<>();
		List<TilePojo> nodeList3 = new ArrayList<>();
		List<TilePojo> nodeList4 = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, service);
		map.put(ResourceResolverFactory.USER, serviceUser);
		try {
			if (resolverFactory != null) {
				resolver = resolverFactory.getServiceResourceResolver(map);
			}
			String column1Path = null;
			String column2Path = null;
			String column3Path = null;
			String column4Path = null;
			if (tilePage.equals("landing")) {
				column1Path = parentPath + "/columnOne";
				column2Path = parentPath + "/columnTwo";
				column3Path = parentPath + "/columnThree";
				column4Path = parentPath + "/columnFour";
			} else if (tilePage.equals("gallery")) {
				column1Path = parentPath + "/gallerycolumn1";
				column2Path = parentPath + "/gallerycolumn2";
				column3Path = parentPath + "/gallerycolumn3";
				column4Path = parentPath + "/gallerycolumn4";
			}
			nodeList1 = buildNodeList(column1Path, tileType, resolver, linkToPages);
			nodeList2 = buildNodeList(column2Path, tileType, resolver, linkToPages);
			nodeList3 = buildNodeList(column3Path, tileType, resolver, linkToPages);
			if (column4Path != null) {
				nodeList4 = buildNodeList(column4Path, tileType, resolver, linkToPages);
			} else {
				nodeList4 = new ArrayList<>();
			}
			createMasterList(nodeList1, nodeList2, nodeList3, nodeList4);
		} catch (LoginException | RepositoryException e) {
			LOGGER.error("Exception caused in get Tiles By Category", e);
		} finally {
			if (null != resolver && resolver.isLive()) {
				resolver.close();
			}
		}
		LOGGER.info("end of getAllTiles method");

		return masterList;
	}

	/**
	 * This method calls buildMasterList method to build master list based on
	 * conditions
	 *
	 * @param nodeList1
	 *            TilePojo list-1
	 * @param nodeList2
	 *            TilePojo list-2
	 * @param nodeList3
	 *            TilePojo list-3
	 * @param nodeList4
	 *            TilePojo list-4
	 */
	private void createMasterList(List<TilePojo> nodeList1, List<TilePojo> nodeList2, List<TilePojo> nodeList3,
								  List<TilePojo> nodeList4) {
		if (nodeList1.size() >= nodeList2.size() && nodeList1.size() >= nodeList3.size()
				&& nodeList1.size() >= nodeList4.size()) {
			buildMasterList(nodeList1, nodeList2, nodeList3, nodeList4);
		} else if (nodeList2.size() >= nodeList1.size() && nodeList2.size() >= nodeList3.size()
				&& nodeList2.size() >= nodeList4.size()) {
			buildMasterList(nodeList2, nodeList1, nodeList3, nodeList4);
		} else if (nodeList3.size() >= nodeList1.size() && nodeList3.size() >= nodeList2.size()
				&& nodeList3.size() >= nodeList4.size()) {
			buildMasterList(nodeList3, nodeList1, nodeList2, nodeList4);
		} else if (nodeList4.size() >= nodeList1.size() && nodeList4.size() >= nodeList2.size()
				&& nodeList4.size() >= nodeList3.size()) {
			buildMasterList(nodeList4, nodeList1, nodeList2, nodeList3);
		}
	}

	@ObjectClassDefinition(name = "Child Page properties")
	public @interface Config {
		@AttributeDefinition(name = "Root path", description = "Please provide the rootpath of retail homepage. Default is /content/play/pollypocket")
		String rootPath() default "/content/play/pollypocket";
	}

	@Activate
	public void activate(final Config config) {
		// Intentionally left Blank
	}

	/**
	 * Method for building node list for get character by category
	 *
	 * @param linkToPages
	 *
	 * @return List of {@link TilePojo}
	 */
	private List<TilePojo> buildNodeList(String column1Path, String tileType, ResourceResolver resResolver,
										 boolean linkToPages) throws RepositoryException {

		LOGGER.info("buildNodeList start");
		List<TilePojo> tilePojoList = new ArrayList<>();
		try {
			Resource resource = resResolver.getResource(column1Path);
			Node columnNode = null;
			if (resource != null) {
				columnNode = resource.adaptTo(Node.class);
				if (columnNode != null) {
					NodeIterator columnNodeIterater = columnNode.getNodes();
					while (columnNodeIterater.hasNext()) {

						TilePojo tilePojo = new TilePojo();
						Node tilePageNode = columnNodeIterater.nextNode();
						String firstLineOfDesc = PlayHelper.checkForProperty(tilePageNode, descLineOneProp);
						tilePojo.setDescLineOne(firstLineOfDesc);
						String secondLineOfDesc = PlayHelper.checkForProperty(tilePageNode, descLineTwoProp);
						tilePojo.setDescLineTwo(secondLineOfDesc);
						String tileImagePath = PlayHelper.checkForProperty(tilePageNode, tileThumbnailProp);
						String tilePath = PlayHelper.checkForProperty(tilePageNode, "detailpageMapping");
						if (tilePath != null) {
							checkTiletoPageLink(linkToPages, tilePojo, tilePath, resource);
							fetchDetailsFromDetailPage(tilePojo, tilePath, tilePageNode, tileType, tileImagePath,
									resResolver);
						}
						tilePojoList.add(tilePojo);
					}

					LOGGER.debug("tilePojoList size {}", tilePojoList.size());
				}
			}
		} catch (RepositoryException e) {
			LOGGER.error("Repository exception Occured {} ", e);
		}
		LOGGER.info("buildNodeList end");
		return tilePojoList;
	}

	/**
	 * Method to check whether to link Pages to Tiles
	 *
	 * @param linkToPages
	 * @param tilePojo
	 * @param tilePath
	 */
	private void checkTiletoPageLink(boolean linkToPages, TilePojo tilePojo, String tilePath, Resource resource) {
		if (linkToPages) {
			tilePojo.setTilePath(PlayHelper.checkLink(tilePath, resource));
		}
	}

	/**
	 * Method to fetch Tile Details From Tile Detail Page
	 *
	 * @param tilePojo
	 * @param tilePath
	 * @param tilePageNode
	 * @param tileType
	 * @param tileImagePath
	 * @return tilePojo with all the required values
	 * @throws RepositoryException
	 */
	protected TilePojo fetchDetailsFromDetailPage(TilePojo tilePojo, String tilePath, Node tilePageNode,
												  String tileType, String tileImagePath, ResourceResolver resResolver) throws RepositoryException {
		LOGGER.info("fetchDetailsFromDetailPage start");
		PageManager pageManager = resResolver.adaptTo(PageManager.class);
		if (pageManager != null) {
			Page detailPage = pageManager.getPage(tilePath);
			if (detailPage != null) {
				tilePojo = fetchTagsFromPage(tilePojo, detailPage);
				String tileAltTxt = "";
				tileAltTxt = PlayHelper.checkForProperty(tilePageNode, tileAltTextProp);
				if (tileAltTxt.isEmpty()) {
					tileAltTxt = PlayHelper.checkForProperty(detailPage, "navThumbAlt");
				}
				tilePojo.setTileImgAltText(tileAltTxt);
				tilePath = fetchTilePath(tileType, tilePath);
				tilePojo = fetchCharacterDetailsFromNode(tilePath, tilePojo, tileImagePath, resResolver);

				String hoveroverImg = PlayHelper.checkForProperty(tilePageNode, hoverOverProp);
				tilePojo.setHoverOverImg(hoveroverImg);
				String hoveroverImgAlt = PlayHelper.checkForProperty(tilePageNode, hoverOverAltProp);
				tilePojo.setHoverOverImgAlt(hoveroverImgAlt);

				String tileTitle = detailPage.getPageTitle();
				tileTitle = checkTileTitle(tileTitle, detailPage);
				tilePojo.setTileTitle(tileTitle);
			}
		} else {
			tilePojo.setTileImage(tileImagePath);
		}
		LOGGER.info("fetchDetailsFromDetailPage end");
		return null;
	}

	/**
	 *
	 * @param tileTitle
	 * @param detailPage
	 * @return tileTitle based on the check
	 */
	protected String checkTileTitle(String tileTitle, Page detailPage) {
		if (tileTitle == null) {
			tileTitle = PlayHelper.checkForProperty(detailPage, "jcr:title");
		}
		return tileTitle;
	}

	/**
	 *
	 * @param tileType
	 * @param tilePath
	 * @return tilePath based on the titleType
	 */
	protected String fetchTilePath(String tileType, String tilePath) {
		if (tileType.equals("characters")) {
			tilePath = tilePath + Constants.JCR_CHARACTER_NODE;
		} else if (tileType.equals(gamesTileType)) {
			tilePath = tilePath + Constants.JCR_GAME_NODE;
		}
		return tilePath;

	}

	/**
	 *
	 * @param tilePojo
	 * @param detailPage
	 * @return tilePojo with the required values fetched from Page
	 */
	protected TilePojo fetchTagsFromPage(TilePojo tilePojo, Page detailPage) {
		LOGGER.info("fetchTagsFromPage start");
		Tag[] pageTagValues = null;
		String categories = null;
		List<String> tags = new ArrayList<>();
		if (detailPage.getTags() != null) {
			pageTagValues = detailPage.getTags();
			categories = getConcatCategoryNames(pageTagValues);
			for (Tag tag : pageTagValues) {
				tags.add(tag.getTagID());
			}
			tilePojo.setTileCategory(categories);
			tilePojo.setTileTags(tags);
		}
		LOGGER.info("fetchTagsFromPage end");
		return tilePojo;
	}

	/**
	 *
	 * @param tilePath
	 * @param tilePojo
	 * @param tileImagePath
	 * @return TilePojo with the tileImagePath Details
	 * @throws RepositoryException
	 */
	protected TilePojo fetchCharacterDetailsFromNode(String tilePath, TilePojo tilePojo, String tileImagePath,
													 ResourceResolver resResolver) throws RepositoryException {
		LOGGER.info("fetchThumbnailFromNode start");
		Resource tileResource = resResolver.getResource(tilePath);
		if (tileResource != null) {
			Node tileNode = tileResource.adaptTo(Node.class);
			if (tileNode != null) {
				String alwaysEnglishField = PlayHelper.checkForProperty(tileNode, alwaysEnglishProp);
				tilePojo.setAlwaysEnglish(alwaysEnglishField);
				if (tileImagePath.isEmpty()) {
					tileImagePath = tileNode.getProperty(tileThumbnailProp).getString();
					String tileAltTxt = tileNode.getProperty(tileAltTextProp).getString();
					tilePojo.setTileImage(tileImagePath);
					tilePojo.setTileImgAltText(tileAltTxt);
				} else {
					tilePojo.setTileImage(tileImagePath);
				}
			}
		}
		LOGGER.info("fetchThumbnailFromNode end");
		return tilePojo;
	}

	/**
	 * Method for building master list for get character by category
	 *
	 * @return List of void
	 */
	protected void buildMasterList(List<TilePojo> maxLengthNodesList, List<TilePojo> nodeList2,
								   List<TilePojo> nodeList3, List<TilePojo> nodeList4) {
		LOGGER.info("buildMasterList start");
		masterList.clear();
		int count = 0;
		for (int i = 0; i < maxLengthNodesList.size(); i++) {
			if (null != maxLengthNodesList.get(i)) {
				masterList.add(maxLengthNodesList.get(i));
				if (nodeList2.size() > count) {
					masterList.add(nodeList2.get(i));
				}
				if (nodeList3.size() > count) {
					masterList.add(nodeList3.get(i));
				}
				if (!nodeList4.isEmpty() && nodeList4.size() > count) {
					masterList.add(nodeList4.get(i));
				}
			}
			count++;
		}
		LOGGER.info("buildMasterList end");
	}

	/**
	 * Method to build a list of Characters sorting by Date
	 *
	 * @param linkToPages
	 *
	 *
	 **/
	public List<TilePojo> getTilesByDate(String parentPath, String titleType, String selectedTags,
										 ResourceResolver requestResolver, boolean linkToPages) {
		LOGGER.info("getTilesByDate start");
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, service);
		map.put(ResourceResolverFactory.USER, serviceUser);
		String nodeType = "";
		Page parentPage = null;
		nodeType = getNodeType(titleType);
		try {
			getSession(map);
			Map<String, String> querrymap = new HashMap<>();
			querrymap.put("path", parentPath);
			querrymap.put("type", "nt:unstructured");
			querrymap.put("property", "sling:resourceType");
			querrymap.put("property.value", nodeType);
			querrymap.put(orderby, "@jcr:lastModified");
			querrymap.put("orderby.sort", "desc");
			querrymap.put("p.limit", "-1");
			Query pageQuery = queryBuilder.createQuery(PredicateGroup.create(querrymap), session);
			SearchResult result = null;
			if (null != pageQuery) {
				result = pageQuery.getResult();
			}
			if (selectedTags != null && null != result) {
				getCategorySpecificTiles(result, selectedTags, titleType, resolver);
			} else {
				if (null != result) {

					masterList.clear();
					for (Hit hit : result.getHits()) {
						TilePojo tilePojo = new TilePojo();
						tilePojo = fecthHitTileTagDetailsByDate(hit, tilePojo, requestResolver, parentPage, titleType,
								linkToPages);
						tilePojo.setAlwaysEnglish(hit.getProperties().get(alwaysEnglishProp, String.class));
						tilePojo.setTileImage(hit.getProperties().get(tileThumbnailProp, String.class));
						tilePojo.setTileImgAltText(hit.getProperties().get(tileAltTextProp, String.class));
						tilePojo.setTileTitle(hit.getProperties().get("tileTitle", String.class));
						tilePojo.setHoverOverImg(hit.getProperties().get(hoverOverProp, String.class));
						tilePojo.setHoverOverImgAlt(hit.getProperties().get(hoverOverAltProp, String.class));
						masterList.add(tilePojo);
					}
					PlayHelper.getLeakingResResolver(result);

				}
			}
		} catch (RepositoryException | LoginException e) {
			LOGGER.error("Repository Exception Occured {} ", e);
		} finally {
			if (null != resolver && resolver.isLive()) {
				resolver.close();
			}
		}
		LOGGER.info("getTilesByDate end");
		return masterList;
	}

	/**
	 * @param map
	 * 		map containing service user details to get service resource resolver
	 * @throws LoginException
	 */
	private void getSession(Map<String, Object> map) throws LoginException {
		if (resolverFactory != null) {
			resolver = resolverFactory.getServiceResourceResolver(map);
		}
		if (resolver != null) {
			session = resolver.adaptTo(Session.class);
		}
	}

	/**
	 * Get node type based on titleType
	 *
	 * @param titleType
	 *            title path
	 * @return
	 */
	private String getNodeType(String titleType) {
		String nodeType = StringUtils.EMPTY;
		if ("characters".equals(titleType)) {
			nodeType = PropertyReaderUtils.getCharacterResourceType();
		} else if (gamesTileType.equals(titleType)) {
			nodeType = PropertyReaderUtils.getGameResourceType();
		} else if ("products".equals(titleType)) {
			nodeType = PropertyReaderUtils.getProductResourceType();
		}
		return nodeType;
	}


	/**
	 * Method to build a list of Articles sorting by Date & Category
	 *
	 * @param parentPath
	 *
	 *
	 **/
	public List<ArticlePojo> getArticleTilesByDate(String parentPath, String selectedTags,
												   ResourceResolver requestResolver) {
		LOGGER.info("getArticleTilesByDate start");
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, service);
		map.put(ResourceResolverFactory.USER, serviceUser);
		String nodeType = "";
		String articlePath = "";
		String categoryName = "";
		nodeType = PropertyReaderUtils.getArticleResourceType();
		String orderbyProp = PropertyReaderUtils.getArticleSortOrderProperty();
		try {
			getSession(map);
			Map<String, String> querrymap = new HashMap<>();
			querrymap.put("path", parentPath);
			querrymap.put("type", "nt:unstructured");
			querrymap.put("property", "sling:resourceType");
			querrymap.put("property.value", nodeType);
			if (selectedTags != null) {
				querrymap.put("1_property", primaryTags);
				querrymap.put("1_property.value", selectedTags);
			}
			if ("datepickerarticle".equalsIgnoreCase(orderbyProp)) {
				querrymap.put(orderby, "@datepickerarticle");
			} else {
				querrymap.put(orderby, "@jcr:lastModified");
			}
			querrymap.put("orderby.sort", "desc");
			querrymap.put("p.limit", "-1");
			Query pageQuery = queryBuilder.createQuery(PredicateGroup.create(querrymap), session);
			SearchResult result = null;
			if (null != pageQuery) {
				result = pageQuery.getResult();
			}
			if (selectedTags != null && null != result) {
				String[] categoryTags = new String[2];
				categoryTags[0] = selectedTags;
				fetchArticleListByCategory(requestResolver, articlePath, categoryName, result, categoryTags);

			} else {
				if (null != result) {
					getArticleListByDate(requestResolver, articlePath, categoryName, result);

				}
			}
		} catch (RepositoryException | LoginException e) {
			LOGGER.error("repository Exception Occured {} ", e);
		} finally {
			if (null != resolver && resolver.isLive()) {
				resolver.close();
			}
		}
		LOGGER.info("getArticlesByDate end");
		return masterArticleList;
	}

	private void getArticleListByDate(ResourceResolver requestResolver, String articlePath, String categoryName,
									  SearchResult result) throws RepositoryException {
		masterArticleList.clear();
		for (Hit hit : result.getHits()) {
			ArticlePojo articlePojo = new ArticlePojo();
			articlePojo.setPageTitile(hit.getProperties().get(articleTitle, String.class));
			LOGGER.info("getArticleTilesByDate categoryName>>>1 {}", hit.getProperties().get(articleTitle, String.class));
			articlePojo.setPageDescription(hit.getProperties().get(articleshortDesc, String.class));
			LOGGER.info("getArticleTilesByDate articleshortDesc>>>2 {}", hit.getProperties().get(articleshortDesc, String.class));
			articlePojo.setArticleThumnnail(hit.getProperties().get(articleThumbnailpath, String.class));
			LOGGER.info("getArticleTilesByDate articleThumbnailpath>>>3 {}", hit.getProperties().get(articleThumbnailpath, String.class));
			articlePojo.setAlwaysEnglish(hit.getProperties().get(alwaysEnglish, String.class));
			LOGGER.info("getArticleTilesByDate alwaysEnglish>>>4 {}", hit.getProperties().get(alwaysEnglish, String.class));
			List<TagsPojo> categoryTag = getTagRelatedData(hit.getProperties().get(primaryTags, String[].class));
			for (TagsPojo pageTagName : categoryTag) {
				categoryName = pageTagName.getTagTitle();
				LOGGER.info("getArticleTilesByDate categoryName>>>5 {}", categoryName);
			}
			articlePojo.setArticleTagName(categoryName);
			articlePojo.setArticleTagNameLowercase(categoryName.toLowerCase());
			articlePath = getArticlePath(requestResolver, hit, articlePath);
			articlePojo.setArticlePath(articlePath);
			LOGGER.info("getArticleTilesByDate articleTitle>>>6 {}", articlePath);
			masterArticleList.add(articlePojo);
		}
		PlayHelper.getLeakingResResolver(result);
	}

	private void fetchArticleListByCategory(ResourceResolver requestResolver, String articlePath, String categoryName,
											SearchResult result, String[] categoryTags) throws RepositoryException {
		List<TagsPojo> categoryTag = getTagRelatedData(categoryTags);
		for (TagsPojo pageTagName : categoryTag) {
			categoryName = pageTagName.getTagTitle();
			LOGGER.info("getArticleTilesByDate categoryName>>>1 {}", categoryName);
		}
		masterArticleList.clear();
		for (Hit hit : result.getHits()) {
			ArticlePojo articlePojoByCategory = new ArticlePojo();
			articlePojoByCategory.setPageTitile(hit.getProperties().get(articleTitle, String.class));
			LOGGER.info("getArticleTilesByCategory articleTitle>>>2 {}", hit.getProperties().get(articleTitle, String.class));
			articlePojoByCategory.setPageDescription(hit.getProperties().get(articleshortDesc, String.class));
			LOGGER.info("getArticleTilesByCategory articleshortDesc>>>3 {}", hit.getProperties().get(articleshortDesc, String.class));
			articlePojoByCategory.setArticleThumnnail(hit.getProperties().get(articleThumbnailpath, String.class));
			LOGGER.info("getArticleTilesByCategory articleThumbnailpath>>>4 {}", hit.getProperties().get(articleThumbnailpath, String.class));
			articlePojoByCategory.setAlwaysEnglish(hit.getProperties().get(alwaysEnglish, String.class));
			LOGGER.info("getArticleTilesByCategory alwaysEnglish>>>5 {}", hit.getProperties().get(alwaysEnglish, String.class));
			articlePojoByCategory.setArticleTagName(categoryName);
			articlePojoByCategory.setArticleTagNameLowercase(categoryName.toLowerCase());
			articlePath = getArticlePath(requestResolver, hit, articlePath);
			articlePojoByCategory.setArticlePath(articlePath);
			LOGGER.info("getArticleTilesByDate articleTitle>>>6 {}", articlePath);
			masterArticleList.add(articlePojoByCategory);
		}
		PlayHelper.getLeakingResResolver(result);
	}

	private String getArticlePath(ResourceResolver requestResolver, Hit hit, String articlePath)
			throws RepositoryException {
		if (null != hit.getPath()) {
			PageManager pageManager = requestResolver.adaptTo(PageManager.class);
			Page page = null;
			if (pageManager != null) {
				page = pageManager.getContainingPage(hit.getPath());
			}
			if (page != null) {
				articlePath = page.getAbsoluteParent(7).getPath();
				articlePath = PlayHelper.checkResolverMapping(articlePath, requestResolver);
			}
		}
		return articlePath;
	}

	/**
	 *
	 * @param hit
	 * @param tilePojo
	 * @param requestResolver
	 * @param parentPage
	 * @param linkToPages
	 * @return the tilepojo with the page tags
	 * @throws RepositoryException
	 */
	protected TilePojo fecthHitTileTagDetailsByDate(Hit hit, TilePojo tilePojo, ResourceResolver requestResolver,
													Page parentPage, String tileType, boolean linkToPages) throws RepositoryException {
		LOGGER.info("fetchHitTileTagDetailsByDay start");
		if (null != hit.getPath()) {
			PageManager pageManager = requestResolver.adaptTo(PageManager.class);
			String parentPagePath = getContainingPagePath(hit.getPath(), tileType, requestResolver);
			if (null != pageManager && null != parentPagePath) {
				parentPage = pageManager.getPage(parentPagePath);
			}
			if (null != parentPage) {
				tilePojo = fetchTagsFromResultByDate(parentPage, tilePojo, linkToPages,
						parentPage.getContentResource());
			}
		}
		LOGGER.info("fetchHitTileTagDetailsByDay end");
		return tilePojo;
	}

	/**
	 * @param parentPage
	 * @param tilePojo
	 * @param linkToPages
	 * @return the page tags if present as well the concatenated category names in
	 * tilepojo
	 */
	protected TilePojo fetchTagsFromResultByDate(Page parentPage, TilePojo tilePojo, boolean linkToPages,
												 Resource resource) {
		LOGGER.info("fetchTagFromResultByDate start");
		List<String> tags = new ArrayList<>();
		Tag[] pageTags = parentPage.getTags();
		String tagName = getConcatCategoryNames(pageTags);
		for (Tag tag : pageTags) {
			tags.add(tag.getTagID());
		}
		tilePojo.setTileTags(tags);
		tilePojo.setTileCategory(tagName);
		if (linkToPages) {
			tilePojo = fetchTileDetailPath(parentPage, tilePojo, resource);
		}
		LOGGER.info("fetchTagFromResultByDate end");
		return tilePojo;
	}

	/**
	 *
	 * @param parentPage
	 * @param tilePojo
	 * @return the tilepojo with parent page path
	 */
	protected TilePojo fetchTileDetailPath(Page parentPage, TilePojo tilePojo, Resource resource) {
		if (StringUtils.isNoneEmpty(parentPage.getPath())) {
			tilePojo.setTilePath(PlayHelper.checkLink(parentPage.getPath(), resource));
		}
		return tilePojo;
	}

	/**
	 * Method to concat Category Names
	 *
	 * @param pageTags
	 * @return concatenated Tag Names
	 */
	private String getConcatCategoryNames(Tag[] pageTags) {
		LOGGER.info("getConcatCategoryNames start");
		StringBuilder tagName = new StringBuilder();
		LOGGER.debug("pageTags {}", pageTags);
		for (Tag tag : pageTags) {
			if (null == tagName)
				tagName = new StringBuilder(tag.getName());
			else
				tagName = tagName.append("/" + tag.getName());

		}
		LOGGER.info("getConcatCategoryNames end");
		return tagName.toString();
	}

	/**
	 *
	 * @param tileType
	 * @param hitPath
	 * @return the containing page path for the hit
	 */
	private String getContainingPagePath(String hitPath, String tileType, ResourceResolver resResolver) {
		LOGGER.info("getContainingPagePath start");
		String parentPagePath = null;
		Resource resource = resResolver.getResource(hitPath);
		if (null != resource && null != resource.getParent()) {
			Resource parentResource = resource.getParent();
			if (null != parentResource && null != parentResource.getParent()) {
				Resource pageResource = parentResource.getParent();
				if (pageResource != null) {
					parentPagePath = checkTitleTypeContainingPath(tileType, pageResource, parentPagePath);
				}
			}
		}
		LOGGER.info("getContainingPagePath end");
		return parentPagePath;
	}

	/**
	 * Method to return the Containing Page Path when we are in tile details
	 *
	 * @param tileType
	 * @param pageResource
	 * @param parentPagePath
	 * @return
	 */
	private String checkTitleTypeContainingPath(String tileType, Resource pageResource, String parentPagePath) {
		if (gamesTileType.equalsIgnoreCase(tileType)) {
			parentPagePath = pageResource.getPath();
		} else {
			Resource pageParentResource = pageResource.getParent();
			if (null != pageParentResource)
				parentPagePath = pageParentResource.getPath();
		}
		return parentPagePath;
	}

	/**
	 * Method to fetch the tile details based on category
	 *
	 * @param result
	 * @param tagId
	 * @param tileType
	 */
	private void getCategorySpecificTiles(SearchResult result, String tagId, String tileType,
										  ResourceResolver resResolver) {
		LOGGER.info("getCategorySpecificTiles start");
		String tagName = null;
		Page parentPage = null;
		try {
			if (null != result) {
				masterList.clear();
				for (Hit hit : result.getHits()) {
					if (null != resResolver) {
						Resource res = resResolver.getResource(hit.getPath());
						if (null != res) {
							masterList = fetchTileDetailsCategory(hit, masterList, tagName, parentPage, tagId, tileType,
									res);
							LOGGER.debug("masterList size {}", masterList.size());
						}
					}
				}
			}
		} catch (Exception ex) {
			LOGGER.debug(ex.getMessage());
		}
		LOGGER.info("getCategorySpecificTiles end");
	}

	/**
	 * Method to fetch the list of Tile Details Based on Category
	 *
	 * @param hit
	 * @param masterList
	 * @param tagName
	 * @param parentPage
	 * @param tagId
	 * @param tileType
	 * @return
	 * @throws RepositoryException
	 */
	protected List<TilePojo> fetchTileDetailsCategory(Hit hit, List<TilePojo> masterList, String tagName,
													  Page parentPage, String tagId, String tileType, Resource resource) throws RepositoryException {
		LOGGER.info("fetchTileDetailsCategory start");
		TilePojo tilePojo = new TilePojo();
		boolean includePojoFlag = false;
		if (null != hit.getPath()) {
			PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
			String parentPagePath = getContainingPagePath(hit.getPath(), tileType, resource.getResourceResolver());
			if (null != pageManager && null != parentPagePath) {
				parentPage = pageManager.getPage(parentPagePath);
			}
			if (parentPage != null) {
				Tag[] pageTags = parentPage.getTags();
				tagName = getConcatCategoryNames(pageTags);
				includePojoFlag = checkPojoFlag(pageTags, tagId);
				tilePojo = fetchTileDetailPath(parentPage, tilePojo, resource);
			}
		}
		if (includePojoFlag) {
			tilePojo.setTileCategory(tagName);
			tilePojo.setTileImage(hit.getProperties().get(tileThumbnailProp, String.class));
			tilePojo.setTileImgAltText(hit.getProperties().get(tileAltTextProp, String.class));
			tilePojo.setTileTitle(hit.getProperties().get("tileTitle", String.class));
			tilePojo.setHoverOverImg(hit.getProperties().get(hoverOverProp, String.class));
			tilePojo.setHoverOverImgAlt(hit.getProperties().get(hoverOverAltProp, String.class));
			masterList.add(tilePojo);
		}

		LOGGER.info("fetchTileDetailsCategory end");
		return masterList;

	}

	/**
	 * Method to fetch the List of Pages and their Child pages Details based on the
	 * root path given
	 *
	 * @param rootPage
	 * @param collectChilds
	 * @param currentPath
	 * @param collectRootPage
	 * @return the list of Site Navigation details and their children based on the
	 * conditions
	 */
	public List<SiteNavigationPojo> getSiteNavigationDetails(Page rootPage, Boolean collectChilds, String currentPath, Boolean collectRootPage) {
		LOGGER.info("getSiteNavigationDetails start");
		List<SiteNavigationPojo> navItemsList = new ArrayList<>();
		List<SiteNavigationPojo> childItemsList;
		if (rootPage != null) {
			if (collectRootPage) {
				navItemsList.add(getRootPageDetails(rootPage, currentPath));
			}
			Iterator<Page> rootPageIterator = rootPage.listChildren(new PageFilter(), false);
			while (rootPageIterator.hasNext()) {
				childItemsList = new ArrayList<>();
				Page childPage = rootPageIterator.next();
				if (collectChilds) {
					Iterator<Page> childPageIterator = childPage.listChildren(new PageFilter(), false);
					while (childPageIterator.hasNext()) {
						Page l2ChildPage = childPageIterator.next();
						String path = l2ChildPage.getPath();
						String title = l2ChildPage.getTitle();
						String navTitle = l2ChildPage.getNavigationTitle();
						SiteNavigationPojo childPageItem = new SiteNavigationPojo();
						Object thumbnail = l2ChildPage.getProperties().get(TileGalleryAndLandingService.NAV_THUMB_IMAGE);
						String thumbnailImg = checkPropertyObject(thumbnail);
						Object redirectTarget = l2ChildPage.getProperties().get(TileGalleryAndLandingService.CQ_REDIRECT_TARGET);
						String redirectPath = checkPropertyObject(redirectTarget);
						Object redirectTargetOptionObj = l2ChildPage.getProperties().get(TileGalleryAndLandingService.CQ_REDIRECT_TARGET_OPTIONS);
						String redirectTargetOption = checkPropertyObjectForUrlTarget(redirectTargetOptionObj);
						Object adobeTrackingNameForPage = l2ChildPage.getProperties().get(TileGalleryAndLandingService.ADOBE_TRACKING_NAME_FOR_PAGE);
						childPageItem.setAdobeTrackingNameForPage(checkPropertyObject(adobeTrackingNameForPage));
						childPageItem.setThumbnailImg(thumbnailImg);
						HashMap<String, String> childNavMap = new HashMap<>();
						childNavMap.put(Constants.REDIRECTPATH, redirectPath);
						childNavMap.put(Constants.REDIRECTTARGETOPTION, redirectTargetOption);
						childNavMap.put(Constants.PATH, path);
						childNavMap.put(Constants.CURRENTPATH, currentPath);
						childPageItem = checkNavigationDetails(childPageItem, false, title, navTitle, l2ChildPage.getContentResource(), false, childNavMap);
						childItemsList.add(childPageItem);
					}
				}
				String path = childPage.getPath();
				String title = childPage.getTitle();
				String navTitle = childPage.getNavigationTitle();
				Object thumbnail = childPage.getProperties().get(TileGalleryAndLandingService.NAV_THUMB_IMAGE);
				String thumbnailImg = checkPropertyObject(thumbnail);
				SiteNavigationPojo navigationPlayItem = new SiteNavigationPojo();
				Object redirectTarget = childPage.getProperties().get(TileGalleryAndLandingService.CQ_REDIRECT_TARGET);
				String redirectPath = checkPropertyObject(redirectTarget);
				Object adobeTrackingNameForPage = childPage.getProperties().get(TileGalleryAndLandingService.ADOBE_TRACKING_NAME_FOR_PAGE);
				navigationPlayItem.setAdobeTrackingNameForPage(checkPropertyObject(adobeTrackingNameForPage));
				Object redirectTargetOptionObj = childPage.getProperties().get(TileGalleryAndLandingService.CQ_REDIRECT_TARGET_OPTIONS);
				String redirectTargetOption = checkPropertyObjectForUrlTarget(redirectTargetOptionObj);
				navigationPlayItem.setThumbnailImg(thumbnailImg);
				HashMap<String, String> childNavMap = new HashMap<>();
				childNavMap.put(Constants.REDIRECTPATH, redirectPath);
				childNavMap.put(Constants.REDIRECTTARGETOPTION, redirectTargetOption);
				childNavMap.put(Constants.PATH, path);
				childNavMap.put(Constants.CURRENTPATH, currentPath);
				navigationPlayItem = checkNavigationDetails(navigationPlayItem, true, title, navTitle, childPage.getContentResource(), false, childNavMap);
				navigationPlayItem.setName(childPage.getName());
				navigationPlayItem.setLinkingName(childPage.getName());
				if (!childItemsList.isEmpty()) {
					navigationPlayItem.setChildPageList(childItemsList);
				}
				navItemsList.add(navigationPlayItem);
			}

			LOGGER.debug("navItemsList size {}", navItemsList.size());

		}
		LOGGER.info("getSiteNavigationDetails end");
		return navItemsList;
	}

	/**
	 * @param rootPage
	 * @param currentPath
	 * @return
	 */
	private SiteNavigationPojo getRootPageDetails(Page rootPage, String currentPath) {
		String path = rootPage.getPath();
		String title = rootPage.getTitle();
		String navTitle = rootPage.getNavigationTitle();
		Object thumbnail = rootPage.getProperties().get(TileGalleryAndLandingService.NAV_THUMB_IMAGE);
		String thumbnailImg = checkPropertyObject(thumbnail);
		SiteNavigationPojo navigationPlayItem = new SiteNavigationPojo();
		Object redirectTarget = rootPage.getProperties().get(TileGalleryAndLandingService.CQ_REDIRECT_TARGET);
		String redirectPath = checkPropertyObject(redirectTarget);
		Object adobeTrackingNameForPage = rootPage.getProperties().get(TileGalleryAndLandingService.ADOBE_TRACKING_NAME_FOR_PAGE);
		navigationPlayItem.setAdobeTrackingNameForPage(checkPropertyObject(adobeTrackingNameForPage));
		Object redirectTargetOptionObj = rootPage.getProperties().get(TileGalleryAndLandingService.CQ_REDIRECT_TARGET_OPTIONS);
		String redirectTargetOption = checkPropertyObjectForUrlTarget(redirectTargetOptionObj);
		navigationPlayItem.setThumbnailImg(thumbnailImg);
		HashMap<String, String> childNavMap = new HashMap<>();
		childNavMap.put(Constants.REDIRECTPATH, redirectPath);
		childNavMap.put(Constants.REDIRECTTARGETOPTION, redirectTargetOption);
		childNavMap.put(Constants.PATH, path);
		childNavMap.put(Constants.CURRENTPATH, currentPath);
		navigationPlayItem = checkNavigationDetails(navigationPlayItem, true, title, navTitle, rootPage.getContentResource(), true, childNavMap);
		navigationPlayItem.setName(rootPage.getName());
		navigationPlayItem.setLinkingName(rootPage.getName());
		return navigationPlayItem;
	}

	/**
	 * Method to check for the property when an object has been Passed
	 *
	 * @param value
	 * @return
	 */
	protected String checkPropertyObject(Object value) {
		return value != null ? value.toString() : "";
	}

	/**
	 * Method to check for the property when an object has been Passed
	 *
	 * @param value
	 * @return
	 */
	protected String checkPropertyObjectForUrlTarget(Object value) {
		return value != null ? value.toString() : "_self";
	}

	/**
	 * Method to fetch the Redirect Path and title or Navigation Title
	 *
	 * @param isHomeChecked
	 * @param pageItem
	 * @param resource
	 * @param parent
	 * @param childNavMap
	 * @param navTitle
	 * @param title
	 * @return the checked navigation details
	 */
	protected SiteNavigationPojo checkNavigationDetails(SiteNavigationPojo pageItem, boolean parent, String title, String navTitle,
														Resource resource, Boolean isHomeChecked, HashMap<String, String> childNavMap) {

		String redirectPath = childNavMap.containsKey("redirectPath") ? childNavMap.get("redirectPath") : "";
		String path = childNavMap.containsKey("path") ? childNavMap.get("path") : "";
		String currentPath = childNavMap.containsKey("currentPath") ? childNavMap.get("currentPath") : "";
		String redirectTargetOption = childNavMap.containsKey("redirectTargetOption") ? childNavMap.get("redirectTargetOption") : "";

		if (!redirectPath.isEmpty()) {
			if (parent && currentPath.contains(path)) {
				pageItem.setPageActive(true);
			}
			pageItem.setIsRedirect(true);
			pageItem.setUrlTarget(redirectTargetOption);
			pageItem.setPageUrl(PlayHelper.checkLink(redirectPath, resource));
		} else {
			getCurrentPageActiveValue(pageItem, path, parent, currentPath, resource, isHomeChecked);
			pageItem.setPageUrl(PlayHelper.checkLink(path, resource));
		}
		if (navTitle != null) {
			pageItem.setPageName(navTitle);
		} else {
			pageItem.setPageName(title);
		}
		return pageItem;
	}

	/**
	 * @param pageItem
	 * @param path
	 * @param parent
	 * @param currentPath
	 * @param resource
	 * @param isHomeChecked
	 */
	private SiteNavigationPojo getCurrentPageActiveValue(SiteNavigationPojo pageItem, String path, boolean parent, String currentPath,
														 Resource resource, Boolean isHomeChecked) {
		if (isHomeChecked) {
			String rootPagepath = PlayHelper.checkLink(path, resource);
			if (parent && currentPath.equals(rootPagepath)) {
				pageItem.setPageActive(true);
			}
		} else {
			if (parent && currentPath.contains(path)) {
				pageItem.setPageActive(true);
			}
		}
		return pageItem;
	}

	/**
	 *
	 * @param pageTags
	 * @param tagId
	 * @return includePojoFlag value
	 */
	protected boolean checkPojoFlag(Tag[] pageTags, String tagId) {
		boolean includePojoFlag = false;
		for (int i = 0; i < pageTags.length; i++) {
			if (pageTags[i].getTagID().equals(tagId)) {
				includePojoFlag = true;
				break;
			}
		}
		return includePojoFlag;
	}

	/**
	 *
	 * @param multifieldProperty
	 * @return the list of retailer details fetched from multifield
	 */
	public List<RetailerPojo> fetchRetailerDetails(Map<String, ValueMap> multifieldProperty, Resource resource) {
		LOGGER.info("fetchRetailerDetails start");
		List<RetailerPojo> retailerDetailsList = new ArrayList<>();
		for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
			RetailerPojo retailerDetail = new RetailerPojo();
			retailerDetail.setRetailerLogoSrc(entry.getValue().get("retailerLogo", String.class));
			retailerDetail.setRetailLogoAlt(entry.getValue().get("retailLogoAlt", String.class));
			retailerDetail
					.setRetailerUrl(PlayHelper.checkLink(entry.getValue().get("retailerUrl", String.class), resource));
			retailerDetail.setRetailerTarget(entry.getValue().get("retailerTarget", String.class));
			retailerDetailsList.add(retailerDetail);
		}

		LOGGER.debug("retailerDetailsList size {}", retailerDetailsList.size());

		LOGGER.info("fetchRetailerDetails end");
		return retailerDetailsList;

	}

	/**
	 * @param primaryTags
	 * @return
	 */
	public List<TagsPojo> getTagRelatedData(String[] primaryTags) {
		LOGGER.info("start of setTagRelatedData() Method");
		List<TagsPojo> tagsPojoList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, service);
		ResourceResolver resourceResolver = null;
		if (null != resolverFactory) {
			LOGGER.debug("resolver factory is not null");
			try {
				resourceResolver = resolverFactory.getServiceResourceResolver(map);
				TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
				if (null != primaryTags && null != tagManager) {
					for (int i = 0; i < primaryTags.length; i++) {
						TagsPojo tagsPojo = new TagsPojo();
						String tagNameSpace = primaryTags[i];
						Tag tag = tagManager.resolve(tagNameSpace);
						LOGGER.debug("tagNameSpace is {}", tagNameSpace);
						settingTagsPojoList(tagsPojoList, tagsPojo, tag);

					}
				}
			} catch (LoginException e) {
				LOGGER.error("Exception caused in setArticleImagePath", e);
			} finally {
				LOGGER.info("start of finally in setArticleImagePath() Method");
				if (resourceResolver != null && resourceResolver.isLive()) {
					resourceResolver.close();
				}
				LOGGER.info("End of finall in setArticleImagePath() Method");

			}
		}
		LOGGER.info("End of setTagRelatedData() Method");
		return tagsPojoList;
	}

	private void settingTagsPojoList(List<TagsPojo> tagsPojoList, TagsPojo tagsPojo, Tag tag) {
		if (null != tag) {
			String tagTitle = tag.getTitle();
			String tagID = tag.getLocalTagID();
			String tagName = tag.getName();
			tagsPojo.setTagTitle(tagTitle);
			tagsPojo.setTagID(tagID);
			tagsPojo.setTagName(tagName);
			tagsPojoList.add(tagsPojo);
		}
	}

	public ArticlePojo getManualAuthorArticleList(String articlePagePath) throws RepositoryException {
		LOGGER.info("getManualAuthorArticleList-- Start");
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, service);
		ResourceResolver resResolver = null;
		ArticlePojo articlePojo = new ArticlePojo();
		if (null != resolverFactory) {
			LOGGER.debug("resolver factory is not null");
			try {
				resResolver = resolverFactory.getServiceResourceResolver(map);
				Resource resource = resResolver.getResource(articlePagePath);
				if (resource != null) {
					ValueMap contentValueMap = resource.getValueMap();
					LOGGER.info("getManualAuthorArticleList-- resource>>{}", resource);
					Node articleDetailNode = resource.adaptTo(Node.class);
					getArticlePropertiesByManualOrder(articlePagePath, resResolver, articlePojo, contentValueMap,
							articleDetailNode);
				}
			} catch (LoginException e) {
				LOGGER.error("Exception caused in setArticleImagePath", e);
			} finally {
				LOGGER.info("start of finally in setArticleImagePath() Method");
				if (resResolver != null && resResolver.isLive()) {
					resResolver.close();
				}
				LOGGER.info("getManualAuthorArticleList-- End");

			}
		}
		return articlePojo;
	}

	private void getArticlePropertiesByManualOrder(String articlePagePath, ResourceResolver resResolver,
												   ArticlePojo articlePojo, ValueMap contentValueMap, Node articleDetailNode) throws RepositoryException {
		if (articleDetailNode != null) {
			articlePojo.setPageTitile(PlayHelper.checkForProperty(articleDetailNode, articleTitle));
			articlePojo.setPageDescription(PlayHelper.checkForProperty(articleDetailNode, articleshortDesc));
			articlePojo.setArticleThumnnail(PlayHelper.checkForProperty(articleDetailNode, articleThumbnailpath));
			articlePojo.setAlwaysEnglish(PlayHelper.checkForProperty(articleDetailNode, alwaysEnglish));
			String[] subpages = contentValueMap.get(primaryTags, String[].class);
			List<TagsPojo> categoryTag = getTagRelatedData(subpages);
			for (TagsPojo pageTagName : categoryTag) {
				String categoryName = pageTagName.getTagTitle();
				LOGGER.info("getManualAuthorArticleList categoryName>>>1 {}", categoryName);
				articlePojo.setArticleTagName(categoryName);
				articlePojo.setArticleTagNameLowercase(categoryName.toLowerCase());
			}
			PageManager pageManager = resResolver.adaptTo(PageManager.class);
			Page page = null;
			if (pageManager != null) {
				page = pageManager.getContainingPage(articlePagePath);
			}
			if (page != null) {
				String articlePath = page.getAbsoluteParent(7).getPath();
				articlePath = PlayHelper.checkResolverMapping(articlePath, resResolver);
				articlePojo.setArticlePath(articlePath);
			}
		}
	}

}