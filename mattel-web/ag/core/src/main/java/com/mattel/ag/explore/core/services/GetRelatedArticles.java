package com.mattel.ag.explore.core.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;
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

/**
 * @author CTS GetRelatedArticles Service.
 *
 */

@Component(service = GetRelatedArticles.class, immediate = true)

public class GetRelatedArticles {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetRelatedArticles.class);

	@Reference
	ResourceResolverFactory resolverFactory;

	@Reference
	QueryBuilder queryBuilder;


	private static final String HTML_EXTENSION = ".html";

	/**
	 * Generic Method for getting Related Articles list.
	 *
	 * @param queryDataMap
	 * @return List<RelatedArticlePojo>
	 * @throws ParseException
	 */

	public List<RelatedArticlePojo> getRelatedArticles(Map<String, List<String>> queryDataMap) {

		LOGGER.info("Start of getRelatedArticles() method in GetRelatedArticles");
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, Constants.READWRITESERVICE);
		ResourceResolver resolver = null;
		List<RelatedArticlePojo> relatedArticlePojos = new ArrayList<>();
		try {
			if (null != resolverFactory) {
				resolver = resolverFactory.getServiceResourceResolver(map);
				Map<String, String> querymap = new HashMap<>();
				querymap.put("type", "cq:Page");
				querymap.put("p.limit", "-1");
				querymap.put("orderby", "@jcr:content/datepickerarticle");
				querymap.put("orderby.sort", "desc");
				setQueryMapTemplateTypes(queryDataMap, querymap);
				setQueryMapPath(queryDataMap, querymap);
				if (!queryDataMap.isEmpty()) {
					Collection<String> keyCollection = queryDataMap.keySet();
					Iterator<String> keyIterator = keyCollection.iterator();
					LOGGER.debug("keyIterator is {} ", keyIterator);
					while (keyIterator.hasNext()) {
						String current = keyIterator.next();
						createQuery(queryDataMap, querymap, current);
					}
					querymap.put("group.1_group.p.or", "true");
					querymap.put("group.2_group.p.or", "true");
					querymap.put("group.p.or", "true");
					LOGGER.debug("Query map is {}", querymap);
					Query query = queryBuilder.createQuery(PredicateGroup.create(querymap),
							resolver.adaptTo(Session.class));
					SearchResult result = query.getResult();
					if (null != result) {
            try {
              setRelatedArticleData(relatedArticlePojos, result, resolver);
            } finally {
              closeResourceResolver(result);
            }
          }
				}
			}

		} catch (LoginException | RepositoryException e) {
			LOGGER.error("Exception caused in Child page properties Service", e);
		} finally {
			LOGGER.info("start of finally in getRelatedArticles() Method");
			if (resolver != null && resolver.isLive()) {
				resolver.close();
			}
			LOGGER.info("End of finally in getRelatedArticles() Method");
		}
		LOGGER.info("End of getRelatedArticles() method in GetRelatedArticles");
		return relatedArticlePojos;
	}

	/**
	 * Generic Method for closing resource resolver.
	 * @param result
	 */
	private void closeResourceResolver(SearchResult result) {
		Iterator<Resource> resources = result.getResources();
		if (resources.hasNext()) {
			resources.next().getResourceResolver().close();
		}

	}

	/**
	 * This method will set the query results to the respective fields.
	 * @param relatedArticlePojos
	 * @param result
	 * @param resolver 
	 * @throws RepositoryException
	 */
	private void setRelatedArticleData(List<RelatedArticlePojo> relatedArticlePojos,
									   SearchResult result, ResourceResolver resolver) throws RepositoryException {

		LOGGER.info("start of setRelatedArticleData() Method");
		LOGGER.debug("Search result is not null {}", result.getHitsPerPage());
		for (Hit hit : result.getHits()) {
			RelatedArticlePojo relatedArticlePojo = new RelatedArticlePojo();
			ValueMap valueMap = hit.getProperties();
			if (!valueMap.isEmpty()) {
          relatedArticleValue(relatedArticlePojos,hit,relatedArticlePojo,valueMap, resolver);
			}
		}
		LOGGER.info("end of setRelatedArticleData() Method");

	}

	/**
	 * This method will set the query results to the respective fields.
	 * @param relatedArticlePojos
	 * @param hit
	 * @param relatedArticlePojo
	 * @param valueMap
	 * @param resolver 
	 * @throws RepositoryException
	 */
	private void relatedArticleValue(List<RelatedArticlePojo> relatedArticlePojos, Hit hit,
									 RelatedArticlePojo relatedArticlePojo, ValueMap valueMap, ResourceResolver resolver) throws RepositoryException {

		if (null != hit.getPath()) {
			pathVal(hit, relatedArticlePojo, resolver);
		}

		setCommonPojo(relatedArticlePojo, valueMap, resolver);


		if (valueMap.containsKey(Constants.CQ_TAGS)) {
			List<TagsPojo> cqtagsPojoList = getTagRelatedData(
					valueMap.get(Constants.CQ_TAGS, String[].class), resolver);
			relatedArticlePojo.setCqTag(cqtagsPojoList);
		}
		if (valueMap.containsKey(Constants.ARTICLE_CREATED_DATE)) {
			Date articleCreatedDate = valueMap.get(Constants.ARTICLE_CREATED_DATE, Date.class);
			String articleCreatedDateString = convertToCqDateFormat(articleCreatedDate);
			relatedArticlePojo.setArticleCreatedDate(articleCreatedDateString);
		}

		if (valueMap.containsKey(Constants.ARTICLE_UPDATED_DATE)) {
			Date articleUpdatedDate = valueMap.get(Constants.ARTICLE_UPDATED_DATE, Date.class);
			String articleUpdatedDateString = convertToCqDateFormat(articleUpdatedDate);
			relatedArticlePojo.setArticleUpdatedDate(articleUpdatedDateString);
		}

		if (valueMap.containsKey(Constants.ARTICLE_DISPLAY_DATE)) {
			formattedDate(valueMap, relatedArticlePojo);
		}
		if (valueMap.containsKey(Constants.HIDE_ARTICLE_DATE)) {
			hideArticle(relatedArticlePojo, valueMap);
		}
		relatedArticlePojos.add(relatedArticlePojo);



	}

	/**
	 * This Method will create the query to get all the Related Articles.
	 * With the predicates like path , primaryTags , secondaryTags , articleDate etc.
	 * @param queryDataMap
	 * @param querymap for querying
	 * @param current
	 * item in the queryDataMap
	 */

	private void createQuery(Map<String, List<String>> queryDataMap, Map<String, String> querymap, String current) {
		LOGGER.debug("Inside createQuery() Method ");
		String valueString = "_value";
		if (Constants.PATH.equals(current)) {
			setQueryMapPath(queryDataMap, querymap);
		}

		else if (Constants.DISPLAY_HOME_PAGE.equals(current)) {
			querymap.put("7_group.property", "@jcr:content/displayhomepage");
			List<String> displayHomePage = queryDataMap.get(Constants.DISPLAY_HOME_PAGE);
			querymap.put("7_group.property.1_value", displayHomePage.get(0));
		}

		else if (Constants.DISPLAY_SITE_HOME.equals(current)) {
			querymap.put("8_group.property", "@jcr:content/displaysitehome");
			List<String> displaySiteHome = queryDataMap.get(Constants.DISPLAY_SITE_HOME);
			querymap.put("8_group.property.1_value", displaySiteHome.get(0));
		}

		else if (Constants.DEFAULT_LIMIT.equals(current)) {
			List<String> limits = queryDataMap.get(Constants.DEFAULT_LIMIT);
			for (String limit : limits) {
				querymap.put("p.limit", limit);
			}
		} else if (Constants.PRIMARY_TAGS.equals(current)) {
			List<String> primarytags = queryDataMap.get(Constants.PRIMARY_TAGS);
			int indexPrimary = 1;
			querymap.put("group.1_group.1_tagid.property", "@jcr:content/primaryTags");
			for (String tag : primarytags) {
				querymap.put("group.1_group.1_tagid.property." + indexPrimary + valueString, tag);
				indexPrimary++;
			}
		} else if (Constants.SECONDARY_TAGS.equals(current)) {
			List<String> secondarytags = queryDataMap.get(Constants.SECONDARY_TAGS);
			secondaryTagVal(querymap, valueString, secondarytags);
		}

		else if (Constants.TEMPLATE_TYPES.equals(current)) {
			setQueryMapTemplateTypes(queryDataMap, querymap);
		}

		else if (Constants.ARTICLE_DATE.equals(current)) {
			setQueryMapDateRange(queryDataMap, querymap, valueString);
		}

		else if (Constants.ALL_TAGS.equals(current)) {
			createQueryAllTag(queryDataMap, querymap, valueString);
		}
	}

	/**
	 * This Method will create the query for all tags.
	 * @param queryDataMap
	 * @param querymap for querying
	 * @param valueString
	 */
	private void createQueryAllTag(Map<String, List<String>> queryDataMap, Map<String, String> querymap,
								   String valueString) {
		List<String> alltags = queryDataMap.get(Constants.ALL_TAGS);
		int indexPrimary = 1;
		secondaryTagVal(querymap, valueString, alltags);
		querymap.put("group.1_group.1_tagid.property", "@jcr:content/primaryTags");
		for (String tag : alltags) {
			querymap.put("group.1_group.1_tagid.property." + indexPrimary + valueString, tag);
			indexPrimary++;
		}

	}



	private void secondaryTagVal(Map<String, String> querymap, String valueString, List<String> secondarytags) {
		int indexSecondary = 1;
		querymap.put("group.2_group.2_tagid.property", "@jcr:content/secondaryTags");
		for (String tag : secondarytags) {
			querymap.put("group.2_group.2_tagid.property." + indexSecondary + valueString, tag);
			indexSecondary++;
		}
	}

	/**
	 * @param queryDataMap
	 * @param querymap
	 */

	private void setQueryMapPath(Map<String, List<String>> queryDataMap, Map<String, String> querymap) {
		LOGGER.info("start of setQueryMapPath() Method");
		List<String> pathValue = new ArrayList<>();
		if (!queryDataMap.containsKey(Constants.PATH)) {
			pathValue.add(Constants.DEFAULT_EXPLORE_CONTENT_PATH);
		} else {
			pathValue = queryDataMap.get(Constants.PATH);
		}
		for (String path : pathValue) {
			querymap.put(Constants.PATH, path);
		}
		LOGGER.info("end of setQueryMapPath() Method");
	}

	/**
	 * This method will set the predicate to query in a Date Range
	 *
	 * @param queryDataMap
	 * @param querymap
	 * @param valueString
	 */

	private void setQueryMapDateRange(Map<String, List<String>> queryDataMap, Map<String, String> querymap,
									  String valueString) {
		LOGGER.info("start of setQueryMapDateRange() Method");
		List<String> articleDates = queryDataMap.get(Constants.ARTICLE_DATE);
		int indexArticleDatePicker = 1;
		int indexArticleDateCreated = 1;
		if (articleDates.size() == 2) {
			querymap.put("4_group.4_daterange.property", "jcr:content/datepickerarticle");
			querymap.put("4_group.4_daterange.lowerBound", articleDates.get(0));
			querymap.put("4_group.4_daterange.upperBound", articleDates.get(1));
			querymap.put("5_group.5_daterange.property", "jcr:content/jcr:created");
			querymap.put("5_group.5_daterange.lowerBound", articleDates.get(0));
			querymap.put("5_group.5_daterange.upperBound", articleDates.get(1));
		} else if (articleDates.size() == 1) {
			querymap.put("4_group.property", "jcr:content/datepickerarticle");
			for (String articleDate : articleDates) {
				querymap.put("4_group.property." + indexArticleDatePicker + valueString, articleDate);
				indexArticleDatePicker++;
			}
			querymap.put("5_group.property", "jcr:content/jcr:created");
			for (String articleDate : articleDates) {
				querymap.put("5_group.property." + indexArticleDateCreated + valueString, articleDate);
				indexArticleDateCreated++;
			}
		}
		LOGGER.info("end of setQueryMapDateRange() Method");
	}

	/**
	 * This method will set the predicate to query for a particular Template
	 * Type
	 *
	 * @param queryDataMap
	 * @param querymap
	 */

	private void setQueryMapTemplateTypes(Map<String, List<String>> queryDataMap, Map<String, String> querymap) {

		LOGGER.info("start of setQueryMapTemplateTypes() Method");
		List<String> templateTypes = new ArrayList<>();
		String valueString = "_value";
		if (!queryDataMap.containsKey(Constants.TEMPLATE_TYPES)) {
			templateTypes.add("/conf/ag/settings/wcm/templates/article-template-page");
			LOGGER.debug("templateTypes {}", templateTypes);
		} else {
			templateTypes = queryDataMap.get(Constants.TEMPLATE_TYPES);

		}
		int indexTemplate = 1;
		querymap.put("3_group.property", "jcr:content/cq:template");
		for (String templateType : templateTypes) {
			querymap.put("3_group.property." + indexTemplate + valueString, templateType);
			indexTemplate++;
		}
		LOGGER.info("end of setQueryMapTemplateTypes() Method");
	}

	private void hideArticle(RelatedArticlePojo relatedArticlePojo, ValueMap valueMap) {
		String hideDateVal = valueMap.get(Constants.HIDE_ARTICLE_DATE, String.class);
		boolean hideDate = false;
		if ("true".equals(hideDateVal)) {
			hideDate = true;
		}
		relatedArticlePojo.setHideArticleDate(hideDate);
	}

	private void pathVal(Hit hit, RelatedArticlePojo relatedArticlePojo, ResourceResolver resolver) throws RepositoryException {
		String resPath = hit.getPath();
		if (null != resolverFactory) {
			LOGGER.debug("Resolver factory in pathVal is not null");
			try {
				Resource res = resolver.getResource(resPath + Constants.ARTICLE_BANNER_COMPONENT);
				if (null != res) {
					setArticleImagePath(relatedArticlePojo, resPath);
				} else {
					LOGGER.info("resource is null in relatedArticleValue");
				}
			} catch (Exception e) {
				LOGGER.error("Exception caused in pathVal", e);
			} finally {
				LOGGER.info("End of finally in pathVal() Method");
			}
		}
		String pagePaths = hit.getPath().replaceAll(Constants.BACKSLASH + JcrConstants.JCR_CONTENT, HTML_EXTENSION);
		relatedArticlePojo.setPagePath(pagePaths + HTML_EXTENSION);
	}

	/**
	 * This method will set the query results for article Images.
	 *
	 * @param relatedArticlePojo
	 * @param resPath
	 */

	private void setArticleImagePath(RelatedArticlePojo relatedArticlePojo, String resPath) {
		LOGGER.info("start of setArticleImagePath() Method");
		ValueMap vMap = null;
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, Constants.READWRITESERVICE);
		ResourceResolver resolver = null;
		if (null != resolverFactory) {
			LOGGER.debug("resolver factory in setArticleImagePath is not null");
			try {
				resolver = resolverFactory.getServiceResourceResolver(map);
				Resource res = resolver.getResource(resPath + Constants.ARTICLE_BANNER_COMPONENT);
				if (null != res) {
					LOGGER.debug("Resource Not Null");
					vMap = res.getValueMap();
					LOGGER.debug("ValueMap is {}", vMap);
					String articleHeroImagePath = vMap.get("articleHeroImagePath", String.class);
					String articleGridImagepath = vMap.get("articleGridImagePath", String.class);
					String arttilceLandingHeroImagePath = vMap.get("articleLandingHeroImagePath", String.class);
					relatedArticlePojo.setArticleHeroImage(articleHeroImagePath);
					relatedArticlePojo.setArticleGridImage(articleGridImagepath);
					relatedArticlePojo.setArticleLandingHero(arttilceLandingHeroImagePath);

				} else {
					LOGGER.info("resource is null in relatedArticleValue");
				}
			} catch (LoginException e) {
				LOGGER.error("Exception caused in setArticleImagePath", e);
			} finally {
				LOGGER.info("start of finally in setArticleImagePath() Method");
				if (resolver != null && resolver.isLive()) {
					resolver.close();
				}
				LOGGER.info("End of finall in setArticleImagePath() Method");

			}
		}

		LOGGER.info("end of setArticleImagePath() Method");
	}

	/** * Converts a date to the CQ DateField format. */
	public static String convertToCqDateFormat(Date date) {
		final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:00.00'Z'");
		return formatter.format(date);
	}

	/**
	 * This method provides all the data related to a tag.
	 *
	 * @param primaryTags
	 * @return
	 */

	public List<TagsPojo> getTagRelatedData(String[] primaryTags) {
		LOGGER.info("start of setTagRelatedData() Method");
		List<TagsPojo> tagsPojoList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, Constants.READWRITESERVICE);
		ResourceResolver resolver=null;
		if (null != resolverFactory) {
			LOGGER.debug("resolver factory in getTagRelatedData is not null");
			try {
				resolver = resolverFactory.getServiceResourceResolver(map);
				TagManager tagManager =resolver.adaptTo(TagManager.class);
				createtagPojoList(primaryTags,tagManager,tagsPojoList);
			} catch (LoginException e) {
				LOGGER.error("Exception caused in setArticleImagePath", e);
			} finally {
				LOGGER.info("start of finally in setArticleImagePath() Method");
				if (resolver != null && resolver.isLive()) {
					resolver.close();
				}
				LOGGER.info("End of finall in setArticleImagePath() Method");

			}
		}
		LOGGER.info("End of setTagRelatedData() Method");
		return tagsPojoList;
	}

  /**
   * This method provides all the data related to a tag.
   * Overloaded method.
   *
   * @param primaryTags
   * @param resolver
   *          The resource resolver.
   * @return
   */
  public List<TagsPojo> getTagRelatedData(String[] primaryTags, ResourceResolver resolver) {
    LOGGER.info("start of setTagRelatedData() Method");
    List<TagsPojo> tagsPojoList = new ArrayList<>();

    if (Objects.nonNull(resolver)) {
      LOGGER.debug("resolver factory in getTagRelatedData is not null");
      try {
        TagManager tagManager =resolver.adaptTo(TagManager.class);
        createtagPojoList(primaryTags,tagManager,tagsPojoList);
      } catch (Exception e) {
        LOGGER.error("Exception caused in getTagRelatedData", e);
      } finally {
        LOGGER.info("End of finall in getTagRelatedData() Method");
      }
    }

    LOGGER.info("End of getTagRelatedData() Method");
    return tagsPojoList;
  }

	private void createtagPojoList(String[] primaryTags, TagManager tagManager, List<TagsPojo> tagsPojoList) {
		if (null != primaryTags && null != tagManager) {
			for (int i = 0; i < primaryTags.length; i++) {
				TagsPojo tagsPojo = new TagsPojo();
				String tagNameSpace = primaryTags[i];
				Tag tag = tagManager.resolve(tagNameSpace);
				LOGGER.debug("tagNameSpace is {}", tagNameSpace);
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
		}

	}

	/**
	 * This method get all the Related Articles data when only path (Content
	 * Hierarchy) is available.
	 *
	 * @param queryDataMap
	 * @return
	 */
	public List<RelatedArticlePojo> getRelatedArticleDataUsingPathValue(Map<String, List<String>> queryDataMap) {
		LOGGER.info("start of getRelatedArticleDataUsingPathValue() Method");
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
		ResourceResolver resolver = null;
		RelatedArticlePojo relatedArticlePojo = new RelatedArticlePojo();
		List<RelatedArticlePojo> relatedArticlePojos = new ArrayList<>();
		if (null != resolverFactory) {
			LOGGER.debug("resolver factory is not null");
			try {
				resolver = resolverFactory.getServiceResourceResolver(map);
				if (!queryDataMap.isEmpty()) {
					Collection<String> keyCollection = queryDataMap.keySet();
					Iterator<String> keyIterator = keyCollection.iterator();
					List<String> pathValue = new ArrayList<>();
					LOGGER.debug("keyIterator is {} ", keyIterator);
					while (keyIterator.hasNext()) {
						currentElementInDataMap(queryDataMap, relatedArticlePojo, relatedArticlePojos, keyIterator,
								pathValue);

					}
				}
			} catch (LoginException e) {
				LOGGER.error("Exception caused in getRelatedArticleDataUsingPathValue ", e);
			} finally {
				LOGGER.info("start of finally in getRelatedArticleDataUsingPathValue() Method");
				if (resolver != null && resolver.isLive()) {
					resolver.close();
				}
				LOGGER.info("end of finally in getRelatedArticleDataUsingPathValue() Method");

			}
		}

		LOGGER.info("End of getRelatedArticleDataUsingPathValue() Method");

		return relatedArticlePojos;

	}

	/**
	 * This method iterates to the current element in the Map which has to be
	 * the content path that has got all the Articles Created.
	 *
	 * @param queryDataMap
	 * @param relatedArticlePojo
	 * @param relatedArticlePojos
	 * @param keyIterator
	 * @param pathValue
	 */
	private void currentElementInDataMap(Map<String, List<String>> queryDataMap, RelatedArticlePojo relatedArticlePojo,
										 List<RelatedArticlePojo> relatedArticlePojos, Iterator<String> keyIterator, List<String> pathValue) {
		String current = keyIterator.next();
		if (Constants.PATH.equals(current)) {
			pathValue = queryDataMap.get(Constants.PATH);
		} else {
			pathValue.add(Constants.DEFAULT_EXPLORE_CONTENT_PATH);
		}
		for (String path : pathValue) {

			setArticleRelatedDataUsingValueMap(relatedArticlePojo, relatedArticlePojos, path);

		}
	}

	/**
	 * This Method is used to set the Article Related Data when only content
	 * path was available.
	 *
	 * @param relatedArticlePojo
	 * @param relatedArticlePojos
	 * @param path
	 */
	public void setArticleRelatedDataUsingValueMap(RelatedArticlePojo relatedArticlePojo,
												   List<RelatedArticlePojo> relatedArticlePojos, String path) {
		ValueMap valueMap;
		Resource res;
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
		ResourceResolver resolver = null;
		if (null != resolverFactory) {
			LOGGER.debug("resolver factory in setArticleRelatedDataUsingValueMap is not null");
			try {
				resolver = resolverFactory.getServiceResourceResolver(map);
				res = resolver.getResource(path + Constants.ARTICLE_BANNER_COMPONENT);
				if (null != res) {
					setArticleImagePath(relatedArticlePojo, path);
					valueMap = res.getValueMap();
					setCommonPojo(relatedArticlePojo, valueMap, resolver);
					if (valueMap.containsKey(Constants.ARTICLE_DISPLAY_DATE)) {
						formattedDate(valueMap, relatedArticlePojo);
					}
					relatedArticlePojo.setPagePath(path + HTML_EXTENSION);
					if (valueMap.containsKey(Constants.CQ_TAGS)) {
						List<TagsPojo> cqtagsPojoList = getTagRelatedData(
								valueMap.get(Constants.CQ_TAGS, String[].class), resolver);
						relatedArticlePojo.setCqTag(cqtagsPojoList);
					}

					relatedArticlePojos.add(relatedArticlePojo);
				}
			} catch (LoginException e) {
				LOGGER.error("Exception caused in setArticleRelatedDataUsingValueMap", e);
			} finally {
				LOGGER.info("start of finally in setArticleRelatedDataUsingValueMap() Method");
				if (resolver != null && resolver.isLive()) {
					resolver.close();
				}
				LOGGER.info("end of finally in setArticleRelatedDataUsingValueMap() Method");
			}
		}
	}

	private void formattedDate(ValueMap valueMap, RelatedArticlePojo relatedArticlePojo) {

		try {
			String articleDisplayDate = valueMap.get(Constants.ARTICLE_DISPLAY_DATE, String.class);
			OffsetDateTime odt = OffsetDateTime.parse(articleDisplayDate);
			SimpleDateFormat format = new SimpleDateFormat(Constants.NODE_DATE_FORMAT);
			Date date = format.parse(articleDisplayDate);
			Calendar currentDate = Calendar.getInstance();
			currentDate.setTime(date);
			currentDate.setTimeZone(TimeZone.getTimeZone(odt.getOffset()));
			Formatter fmt = new Formatter();
			String articleDate = fmt.format("%tB", currentDate).toString() + " " + currentDate.get(Calendar.DATE);
			relatedArticlePojo.setArticleDate(articleDate);
			fmt.close();

		} catch (ParseException pe) {
			LOGGER.error("Parse Exception {}", pe);
		}

	}

	public void setResolverFactory(ResourceResolverFactory resolverFactory) {
		this.resolverFactory = resolverFactory;
	}

	public void setQueryBuilder(QueryBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}
	private void setCommonPojo(RelatedArticlePojo relatedArticlePojo, ValueMap valueMap, ResourceResolver resolver) {
		if (valueMap.containsKey(Constants.TITLE)) {
			relatedArticlePojo.setArticleTitle(valueMap.get(Constants.TITLE, String.class));
		}
		if (valueMap.containsKey(Constants.KEYWORDS)) {
			relatedArticlePojo.setKeywords(valueMap.get(Constants.KEYWORDS, String.class));
		}
		if (valueMap.containsKey(Constants.PRIMARY_TAGS)) {
			List<TagsPojo> ptagsPojoList = getTagRelatedData(
					valueMap.get(Constants.PRIMARY_TAGS, String[].class), resolver);
			relatedArticlePojo.setPrimaryTag(ptagsPojoList);
		}
		if (valueMap.containsKey(Constants.SECONDARY_TAGS)) {
			List<TagsPojo> stagsPojoList = getTagRelatedData(
					valueMap.get(Constants.SECONDARY_TAGS, String[].class), resolver);
			relatedArticlePojo.setSecondaryTag(stagsPojoList);
		}
		if (valueMap.containsKey(Constants.ARTICLE_ID)) {
			relatedArticlePojo.setArticleID(valueMap.get(Constants.ARTICLE_ID, String.class));
		}

		if (valueMap.containsKey(Constants.DISPLAY_HOME_PAGE)) {
			relatedArticlePojo.setDisplayhomepage(valueMap.get(Constants.DISPLAY_HOME_PAGE, String.class));
		}

		if (valueMap.containsKey(Constants.DISPLAY_SITE_HOME)) {
			relatedArticlePojo.setDisplaysitehome(valueMap.get(Constants.DISPLAY_SITE_HOME, String.class));
		}

		if (valueMap.containsKey(Constants.ARTICLE_SOCIAL_SHARING)) {
			relatedArticlePojo.setEnableSocialMediaSharing(valueMap.get(Constants.ARTICLE_SOCIAL_SHARING, String.class));
		}
		if (valueMap.containsKey(Constants.SLING_VANITY_PATH)) {
			relatedArticlePojo.setVanityUrl(valueMap.get("sling:vanityPath", String.class));
		}
		if (valueMap.containsKey(Constants.ARTICLE_DESCRIPTION)) {
			relatedArticlePojo
					.setArticleDescription(valueMap.get(Constants.ARTICLE_DESCRIPTION, String.class));
		}
	}

	public List<RelatedArticlePojo> removeDuplicateArticles(List<String> featuredArticles, int count, List<RelatedArticlePojo> allArticlesPojos) {
		LOGGER.info("Start of removeDuplicateArticles() method");
		List<RelatedArticlePojo> updatedList;
		if (featuredArticles.isEmpty()) {
			updatedList = allArticlesPojos.subList(count, allArticlesPojos.size());
		} else {
      final List<RelatedArticlePojo> toFilterOut = new ArrayList<>();

    allArticlesPojos.forEach(relatedArticlePojo -> featuredArticles.forEach(featuredArticle -> {
      String articlePath = featuredArticle + HTML_EXTENSION;
      if (articlePath.equals(relatedArticlePojo.getPagePath())) {
        toFilterOut.add(relatedArticlePojo);
      }
    }));

      updatedList = allArticlesPojos.stream().filter(x -> !toFilterOut.contains(x))
          .collect(Collectors.toList());
		}
		LOGGER.info("End of removeDuplicateArticles() method");
		return updatedList;
	}
}
