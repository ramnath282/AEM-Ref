package com.mattel.fisherprice.core.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
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
import com.day.cq.wcm.api.Page;
import com.mattel.fisherprice.core.constants.Constants;
import com.mattel.fisherprice.core.pojos.ArticlePojo;
import com.mattel.fisherprice.core.pojos.TagsPojo;
import com.mattel.fisherprice.core.utils.ArticleFeedConfigurationUtils;
import com.mattel.fisherprice.core.utils.ConvertArticleFeedToCSV;

@Component(service = ArticleDetailPagesData.class, immediate = true)
public class ArticleDetailPagesData {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArticleDetailPagesData.class);

	@Reference
	ResourceResolverFactory resolverFactory;

	@Reference
	RelatedArticleService relatedArticleServiceWrapper;
	
	@Reference
	QueryBuilder queryBuilder;

	public void setResolverFactory(ResourceResolverFactory resolverFactory) {
		this.resolverFactory = resolverFactory;
	}

	public void setQueryBuilder(QueryBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}

	public void setRelatedArticleService(RelatedArticleService relatedArticleServiceWrapper) {
		this.relatedArticleServiceWrapper = relatedArticleServiceWrapper;
	}

	/**
	 * gives feed from articles detail pages(filtered by articleFeedFiltersMap) in CSV compatible string
	 * 
	 * @param Map<String, List<String>>:articleFeedFiltersMap
	 * 				containing filters to select article detail pages
	 * @return String:articleFeedInCSV
	 * 				article feed in CSV compatible string
	 */
	public String getArticleFeedInCSV(Map<String, List<String>> articleFeedFiltersMap) {
		LOGGER.info("Start of getArticleFeedInCSV() method in ArticleDetailPagesData class");
		List<ArticlePojo> articlePojos = prepareToGetArticleFeed(articleFeedFiltersMap);
		String articleFeedInCSV = null;
		try {
			articleFeedInCSV = ConvertArticleFeedToCSV.convertToCSVString(articlePojos);
		} catch(Exception e) {
			LOGGER.error("Exception caused in getArticleFeedInCSV() method for ArticleDetailPagesData class", e);
		}
		LOGGER.debug("articleFeedInCSV is {}", articleFeedInCSV);
		LOGGER.info("End of getArticleFeedInCSV() method in ArticleDetailPagesData class");
		return articleFeedInCSV;
	}

	/**
	 * gives feed from articles detail pages(filtered by articleFeedFiltersMap) in List<ArticlePojo> format
	 * 
	 * @param Map<String, List<String>>:articleFeedFiltersMap
	 * 				containing filters to select article detail pages
	 * @return List<ArticlePojo>:articlePojos
	 * 				article feed in List<ArticlePojo>
	 */
	public List<ArticlePojo> prepareToGetArticleFeed(Map<String, List<String>> articleFeedFiltersMap) {
		LOGGER.info("Start of prepareToGetArticleFeed() method in ArticleDetailPagesData class");
		List<ArticlePojo> articlePojos = new ArrayList<>();
		Locale locale = getCountryAndLocale(articleFeedFiltersMap);
		String country = locale.getCountry();
		String language = locale.getLanguage();
		if(Objects.nonNull(country)) {
			country = country.toLowerCase();
		}
		if(Objects.nonNull(language)) {
			language = language.toLowerCase();
		}
		LOGGER.debug("country is {}", country);
		LOGGER.debug("language is {}", language);
		String countryLocale = language + Constants.HYPHEN + country;
		LOGGER.debug("countryLocale is {}", countryLocale);
		String completeArticleBasePath = ArticleFeedConfigurationUtils.getBrandBasePath() + Constants.SLASH + country + Constants.SLASH + countryLocale 
				+ ArticleFeedConfigurationUtils.getArticleBasePath();
		LOGGER.debug("completeArticleBasePath is {}", completeArticleBasePath);
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, Constants.READWRITESERVICE);
		ResourceResolver resolver = null;
		try {
			if (Objects.nonNull(resolverFactory)) {
				resolver = resolverFactory.getServiceResourceResolver(map);
				getArticleFeed(articleFeedFiltersMap, completeArticleBasePath, articlePojos, resolver);
			}

		} catch (Exception e) {
			LOGGER.error("Exception caused in prepareToGetArticleFeed() method for ArticleDetailPagesData class{}", e);
		} finally {
			LOGGER.info("start of finally in prepareToGetArticleFeed() Method for ArticleDetailPagesData class");
			if (resolver != null && resolver.isLive()) {
				resolver.close();
			}
			LOGGER.info("End of finally in prepareToGetArticleFeed() Method for ArticleDetailPagesData class");
		}
		LOGGER.info("End of prepareToGetArticleFeed() method in ArticleDetailPagesData class");
		return articlePojos;
	}

	/**
	 * gives feed from articles detail pages(filtered by articleFeedFiltersMap) in List<ArticlePojo> format
	 * 
	 * @param Map<String, List<String>>:articleFeedFiltersMap
	 * 				containing filters to select article detail pages
	 * @return List<ArticlePojo>:articlePojos
	 * 				article feed in List<ArticlePojo>
	 */
	public List<ArticlePojo> getArticleFeed(Map<String, List<String>> articleFeedFiltersMap,String completeArticleBasePath,
			List<ArticlePojo> articlePojos, ResourceResolver resolver ) {
		LOGGER.info("Start of getArticleFeed() method in ArticleDetailPagesData class");
		Resource articleBasePageResource = resolver.getResource(completeArticleBasePath);
		if(Objects.nonNull(articleBasePageResource)) {
			Page articleBasePage = articleBasePageResource.adaptTo(Page.class);
			if(Objects.nonNull(articleBasePage)) {
				Iterator<Page> articleCategoryLandingPages = articleBasePage.listChildren();
				if(Objects.nonNull(articleCategoryLandingPages)) {
					while(articleCategoryLandingPages.hasNext()) {
						Page articleCategoryLandingPage = articleCategoryLandingPages.next();
						if(Objects.nonNull(articleCategoryLandingPage)) {
							filterArticleDetailPages(articleCategoryLandingPage, articlePojos, articleFeedFiltersMap, resolver);
						}
					}
					removeArticlesWithoutMandarotyFields(articlePojos);
				}	
			}						
		}
		LOGGER.info("Start of getArticleFeed() method in ArticleDetailPagesData class");
		return articlePojos;
	}

	/**
	 * This method will set the query results to the respective fields of ArticlePojo
	 * 
	 * @param Map<String, List<String>>:queryDataMap
	 * 				containing filters to select article detail pages	  
	 * @param List<ArticlePojo> articlePojos
	 * 				PoJo List to save details of each article	 
	 * @param SearchResult result
	 * 				SearchResult object
	 * @param ResourceResolver resolver
	 * 				 ResourceResolver object
	 */
	private void filterArticleDetailPages(Page articleCategoryLandingPage, List<ArticlePojo> articlePojos, 
			Map<String, List<String>> articleFeedFiltersMap, ResourceResolver resolver) {
		LOGGER.info("start of filterArticleDetailPages() method in ArticleDetailPagesData class");
		Iterator<Page> childrenOfCategoryLandingPages = articleCategoryLandingPage.listChildren();
		if(Objects.nonNull(childrenOfCategoryLandingPages)) {
			while(childrenOfCategoryLandingPages.hasNext()) {
				Page currentPage = childrenOfCategoryLandingPages.next();
				if(Objects.nonNull(currentPage)) {
					ValueMap currentPageValueMap = currentPage.getProperties();
					LOGGER.debug("currentPageValueMap is {}", currentPageValueMap);
					String templateType = currentPageValueMap.get(Constants.CQ_TEMPLATE, String.class);
					LOGGER.debug("templateType is {}", templateType);
					if(Objects.nonNull(templateType) && templateType.equals(articleFeedFiltersMap.get(Constants.ARTICLE_DETAIL_TEMPLATE_PATH).get(0))) {
						setArticleProperties(currentPage, currentPageValueMap, articlePojos, articleFeedFiltersMap, resolver);
					}
				}
			}
		}
		LOGGER.info("end of filterArticleDetailPages() method in ArticleDetailPagesData class");
	}

	/**
	 * This method will set the query results to the respective fields.
	 * @param articlePojos
	 * @param hit
	 * @param articlePojo
	 * @param valueMap
	 * @param resolver 
	 */
	private void setArticleProperties(Page articleDetailPage, ValueMap articleDetailPageValueMap, List<ArticlePojo> articlePojos, 
			Map<String, List<String>> articleFeedFiltersMap, ResourceResolver resolver) {
		LOGGER.info("start of setArticleProperties() method in ArticleDetailPagesData class");
		ArticlePojo articlePojo = new ArticlePojo();

		String articleComponentResourceType = articleFeedFiltersMap.get(Constants.ARTICLE_COMP_RESOURCE_TYPE).get(0);
		LOGGER.debug("articleComponentResourceType is {}", articleComponentResourceType);
		setDeeperProp(articleDetailPage, articleComponentResourceType, articlePojo, resolver);		

		Page parentPage = articleDetailPage.getParent();
		String parentPageTitle = parentPage.getTitle();
		LOGGER.debug("parentPageTitle is {}", parentPageTitle);
		articlePojo.setParentPageTitle(parentPageTitle);
		String parentPageName = parentPage.getName();
		LOGGER.debug("parentPageName is {}", parentPageName);
		articlePojo.setParentPageName(parentPageName);
		String parentPagePathBeforeMapping = parentPage.getPath();
		LOGGER.debug("parentPagePathBeforeMapping is {}", parentPagePathBeforeMapping);
		String parentPagePathAfterMapping = resolver.map(parentPagePathBeforeMapping);	
		LOGGER.debug("parentPagePathAfterMapping is {}", parentPagePathAfterMapping);
		if(StringUtils.isNotBlank(parentPagePathAfterMapping) && !parentPagePathAfterMapping.equals(parentPagePathBeforeMapping)) {
			articlePojo.setCategoryLink(parentPagePathAfterMapping);			
		} else if(StringUtils.isNotBlank(parentPagePathBeforeMapping)) {
			articlePojo.setCategoryLink(parentPagePathBeforeMapping + Constants.DOT_HTML);			
		}
		LOGGER.debug("categooryLink value set is {}", articlePojo.getCategoryLink());

		Locale locale = getCountryAndLocale(articleFeedFiltersMap);
		LOGGER.debug("countryAndLocaleMap is {}", locale);
		articlePojo.setLocale(locale);
		String region = locale.getCountry();
		if(Objects.nonNull(region)) {
			region = region.toLowerCase();
		}
		LOGGER.debug("region is {}", region);
		articlePojo.setRegion(region);
		String language = locale.getLanguage();
		if(Objects.nonNull(language)) {
			language = language.toLowerCase();
		}
		LOGGER.debug("language is {}", language);
		String ArticleLanguage = Constants.ARTICLE + Constants.HYPHEN + language + Constants.HYPHEN + region;
		LOGGER.debug("ArticleLanguage is {}", ArticleLanguage);
		articlePojo.setLanguage(ArticleLanguage);

		setCommonPojoFromPageProp(articlePojo, articleDetailPageValueMap);

		setCategoryNameTitle(articlePojo);

		setSubCategoryNameTitle(articlePojo);

		setCategoryId(articlePojo);

		articlePojos.add(articlePojo);

		LOGGER.info("end of setArticleProperties() method in ArticleDetailPagesData class");
	}

	/**
	 * This method will set all the properties of articles which are not dependent on the page properties
	 * 
	 * @param Page:articleDetailPage
	 * 				article detail page	  
	 * @param String:articleComponentResourceType
	 * 				resource type of the article component	 
	 * @param ArticlePojo:articlePojo
	 * 				articlePojo object
	 * @param ResourceResolver resolver
	 * 				 ResourceResolver object
	 */
	private void setDeeperProp(Page articleDetailPage, String articleComponentResourceType, ArticlePojo articlePojo, ResourceResolver resolver) {
		LOGGER.info("start of setDeeperProp() method in ArticleDetailPagesData class");
		String articleDetailPageResourcePath = articleDetailPage.getPath();	
		LOGGER.debug("articleDetailPagePath is {}", articleDetailPageResourcePath);
		if (Objects.nonNull(resolverFactory)) {
			LOGGER.debug("Resolver factory in setDeeperProp is not null");
			try {
				Resource articleComponentResource = getArticleComponentRes(articleDetailPageResourcePath, articleComponentResourceType, resolver);
				ValueMap valueMapArticleComp = null;
				if (Objects.nonNull(articleComponentResource)) {
					LOGGER.debug("articleComponentResource is nonNull");
					valueMapArticleComp = articleComponentResource.getValueMap();
					LOGGER.debug("valueMapArticleComp is {}", valueMapArticleComp);
					setArticleCompRelatedProp(articlePojo, valueMapArticleComp);
				} else {
					LOGGER.info("resource is null in prepareNSetArticleCompRelatedProp()");
				}
			} catch (Exception e) {
				LOGGER.error("Exception caused in setDeeperProp", e);
			} finally {
				LOGGER.info("End of finally in setDeeperProp() Method");
			}
		}
		String pagePathBeforeMapping = articleDetailPageResourcePath.replace(Constants.BACKSLASH + JcrConstants.JCR_CONTENT, Constants.DOT_HTML);
		LOGGER.debug("pagePathBeforeMapping is {}", pagePathBeforeMapping);
		String pagePathAfterMapping = resolver.map(pagePathBeforeMapping);
		LOGGER.debug("pagePathAfterMapping is {}", pagePathAfterMapping);
		if(StringUtils.isNotBlank(pagePathAfterMapping) && !pagePathAfterMapping.equals(pagePathBeforeMapping)) {
			articlePojo.setPageUrl(pagePathAfterMapping);			
		} else if(StringUtils.isNotBlank(pagePathBeforeMapping)) {
			articlePojo.setPageUrl(pagePathBeforeMapping + Constants.DOT_HTML);			
		}
		LOGGER.debug("pageUrl value set is {}", articlePojo.getPageUrl());
		LOGGER.info("end of setDeeperProp() method in ArticleDetailPagesData class");
	}

	/**
	 * This method will return the article component resource present any where inside article detail page
	 * 
	 * @param String:articleDetailPageResourcePath
	 * 				resource path of article detail page	  
	 * @param String:articleComponentResourceType
	 * 				resource type of the article component		
	 * @param ResourceResolver resolver
	 * 				 ResourceResolver object
	 * @return Resource:articleComponentRes
	 * 				article component resource present inside article detail page
	 */
	private Resource getArticleComponentRes(String articleDetailPageResourcePath, String articleComponentResourceType, ResourceResolver resolver) {
		LOGGER.info("start of getArticleComponentRes() method in ArticleDetailPagesData class");
		Resource articleComponentRes = null;
		try{
			Map<String, String> queryMapForArticleComp = new HashMap<>();
			queryMapForArticleComp.put("path", articleDetailPageResourcePath);
			queryMapForArticleComp.put("1_property", "sling:resourceType");
			queryMapForArticleComp.put("1_property.value", articleComponentResourceType);
			queryMapForArticleComp.put("p.limit", "1");
			LOGGER.debug("queryMapForArticleComp is {}", queryMapForArticleComp);
			Query queryForArticleComp = queryBuilder.createQuery(PredicateGroup.create(queryMapForArticleComp),	resolver.adaptTo(Session.class));
			SearchResult resultForArticleComp = queryForArticleComp.getResult();
			try {
				if (Objects.nonNull(resultForArticleComp)) {
					LOGGER.debug("resultForArticleComp is nonNull");
					for (Hit hitForArticleComp : resultForArticleComp.getHits()) {
						LOGGER.debug("hitForArticleComp path is {}", hitForArticleComp.getPath());
						articleComponentRes = hitForArticleComp.getResource();
						if(Objects.nonNull(articleComponentRes)) {
							LOGGER.debug("articleComponentRes is nonNull");
							return articleComponentRes;
						} 
					}
				}
			} finally {
				closeResourceResolver(resultForArticleComp);
			}   
		} catch(Exception e) {
			LOGGER.error("Exception caused in getArticleComponentRes {}", e);
		}
		LOGGER.info("end of getArticleComponentRes() method in ArticleDetailPagesData class");
		return articleComponentRes;
	}

	/**
	 * This method will set all the properties of article which needs to be fetched from the article component
	 * 	
	 * @param ArticlePojo:articlePojo
	 * 				articlePojo object
	 * @param ValueMap:valueMapArticleComp
	 * 				valueMap of article component
	 */
	public void setArticleCompRelatedProp(ArticlePojo articlePojo, ValueMap valueMapArticleComp) {
		LOGGER.info("start of setArticleCompRelatedProp() method in ArticleDetailPagesData class");
		if (valueMapArticleComp.containsKey(Constants.ARTICLE_COMP_PROP_IMAGESERVERURL)) {
			String imageServerURL = valueMapArticleComp.get(Constants.ARTICLE_COMP_PROP_IMAGESERVERURL, String.class);
			LOGGER.debug("imageServerURL is {}", imageServerURL);
			if (valueMapArticleComp.containsKey(Constants.ARTICLE_COMP_PROP_ASSETIDIMAGE)) {
				String thumbnailUrl = imageServerURL + valueMapArticleComp.get(Constants.ARTICLE_COMP_PROP_ASSETIDIMAGE, String.class);
				LOGGER.debug("thumbnailUrl is {}", thumbnailUrl);
				articlePojo.setThumbnailUrl(thumbnailUrl);
			}
			if (valueMapArticleComp.containsKey(Constants.ARTICLE_COMP_PROP_ASSETIDMOBILEIMAGE)) {
				String mobileLink = imageServerURL + valueMapArticleComp.get(Constants.ARTICLE_COMP_PROP_ASSETIDMOBILEIMAGE, String.class);
				LOGGER.debug("mobileLink is {}", mobileLink);
				articlePojo.setMobileLink(mobileLink);
			}
		}
		if (valueMapArticleComp.containsKey(Constants.ARTICLE_COMP_PROP_ARTICLETITLE)) {
			String name = valueMapArticleComp.get(Constants.ARTICLE_COMP_PROP_ARTICLETITLE, String.class);
			LOGGER.debug("name is {}", name);
			articlePojo.setName(name);
		}
		if (valueMapArticleComp.containsKey(Constants.ARTICLE_COMP_PROP_ARTICLESHORTDESC)) {
			String message = valueMapArticleComp.get(Constants.ARTICLE_COMP_PROP_ARTICLESHORTDESC, String.class);
			LOGGER.debug("message is {}", message);
			articlePojo.setMessage(message);
		}
		LOGGER.info("end of setArticleCompRelatedProp() method in ArticleDetailPagesData class");
	}

	/**
	 * Generic Method for closing resource resolver.
	 * @param SearchResult:result	
	 * 				SearchResult object	
	 */
	private void closeResourceResolver(SearchResult result) {
		LOGGER.info("start of closeResourceResolver() method in ArticleDetailPagesData class");
		Iterator<Resource> resources = result.getResources();
		if (resources.hasNext()) {
			resources.next().getResourceResolver().close();
		}
		LOGGER.info("end of closeResourceResolver() method in ArticleDetailPagesData class");
	}

	/**
	 * This method will return the values for country and countryLocale in hashMap. Will return default values as "us","en-us"
	 *
	 * @param Map<String, String>:articleFeedFiltersMap
	 * 				containing filters to select article detail pages	 * 				
	 * @return Map<String, String>
	 * 				containing map of country and conutryLocale
	 */
	private Locale getCountryAndLocale(Map<String, List<String>> articleFeedFiltersMap) {
		LOGGER.info("start of getCountryAndLocale() method in ArticleDetailPagesData class");
		String language = Constants.LANGUAGE_EN;
		LOGGER.debug("default language is {}", language);
		String country = Constants.COUNTRY_US;
		LOGGER.debug("default country is {}", country);
		if (articleFeedFiltersMap.containsKey(Constants.LOCALES_FOR_ARTICLE_FEED)) {
			List<String> countryLocales = articleFeedFiltersMap.get(Constants.LOCALES_FOR_ARTICLE_FEED);
			LOGGER.debug("countryLocales is {}", countryLocales);
			String countryLocale = countryLocales.get(0);
			LOGGER.debug("countryLocale is {}", countryLocale);
			language = countryLocale.substring(0, countryLocale.lastIndexOf(Constants.HYPHEN));
			LOGGER.debug("language is {}", language);
			country = countryLocale.substring(countryLocale.lastIndexOf(Constants.HYPHEN) + 1);
			LOGGER.debug("country is {}", country);
		}
		LOGGER.debug("final language for getCountryAndLocale() is {}", language);
		LOGGER.debug("final country for getCountryAndLocale() is {}", country);
		Locale locale = new Locale(language, country);
		LOGGER.debug("locale is {}", locale);
		LOGGER.info("end of getCountryAndLocale() method in ArticleDetailPagesData class");
		return locale;				
	}

	/**
	 * This method will set all the properties of articles which are to be fetched from the page properties
	 * 
	 * @param ArticlePojo:articlePojo
	 * 				articlePojo object
	 * @param ValueMap:valueMap
	 * 				valueMap of article detail page
	 * @param ResourceResolver resolver
	 * 				 ResourceResolver object 
	 */
	private void setCommonPojoFromPageProp(ArticlePojo articlePojo, ValueMap valueMap) {
		LOGGER.info("start of setCommonPojoFromPageProp() method in ArticleDetailPagesData class");
		if (valueMap.containsKey(Constants.JCR_TITLE) && StringUtils.isAllBlank(articlePojo.getName())) {
			String pageTitle = valueMap.get(Constants.JCR_TITLE, String.class);
			LOGGER.debug("pageTitle is {}", pageTitle);
			articlePojo.setName(pageTitle);
		}
		if (valueMap.containsKey(Constants.ARTICLE_PAGE_PROP_ARTICLE_ID)) {
			String pageId = valueMap.get(Constants.ARTICLE_PAGE_PROP_ARTICLE_ID, String.class);
			LOGGER.debug("ArtilcePage ID is {}", pageId);
			articlePojo.setId(pageId);
		}
		if (valueMap.containsKey(Constants.PRIMARY_TAG)) {
			List<TagsPojo> ptagsPojoList = relatedArticleServiceWrapper.getTagRelatedData(valueMap.get(Constants.PRIMARY_TAG, String[].class), articlePojo.getLocale());
			LOGGER.debug("ptagsPojoList is {}", ptagsPojoList);
			articlePojo.setPrimaryTag(ptagsPojoList);
		}
		if (valueMap.containsKey(Constants.SECONDARY_TAG)) {
			List<TagsPojo> stagsPojoList = relatedArticleServiceWrapper.getTagRelatedData(valueMap.get(Constants.SECONDARY_TAG, String[].class), articlePojo.getLocale());
			LOGGER.debug("stagsPojoList is {}", stagsPojoList);
			articlePojo.setSecondaryTag(stagsPojoList);
		}
		if (valueMap.containsKey(Constants.SLING_VANITY_PATH)) {
			verifyAndSetVanityUrl(valueMap, articlePojo);
		}
		if (valueMap.containsKey(Constants.CQ_LAST_REPLICATION_ACTION)) {
			String lastReplicationActionStr = valueMap.get(Constants.CQ_LAST_REPLICATION_ACTION, String.class);
			LOGGER.debug("lastReplicationActionStr is {}", lastReplicationActionStr);
			if(Objects.nonNull(lastReplicationActionStr) && lastReplicationActionStr.equals("Activate")) {
				articlePojo.setIsPublished("1");
				LOGGER.debug("value of isPublished is {}", articlePojo.getIsPublished());
			} 
		}
		if (valueMap.containsKey(Constants.JCR_CREATED)) {
			Date articleCreatedDate = valueMap.get(Constants.JCR_CREATED, Date.class);
			String articleCreatedDateString = convertToUnixFormat(articleCreatedDate);
			LOGGER.debug("articleCreatedDateString is {}", articleCreatedDateString);
			articlePojo.setCreated(articleCreatedDateString);
		}
		if (valueMap.containsKey(Constants.CQ_LAST_REPLICATED)) {
			Date articleLastReplicatedDate = valueMap.get(Constants.CQ_LAST_REPLICATED, Date.class);
			String articleLastReplicatedDateStr = convertToUnixFormat(articleLastReplicatedDate);
			LOGGER.debug("articleLastReplicatedDateStr is {}", articleLastReplicatedDateStr);
			articlePojo.setLastPublished(articleLastReplicatedDateStr);
			String isPublished = articlePojo.getIsPublished();
			LOGGER.debug("isPublished is {}", isPublished);
			if(Objects.nonNull(isPublished) && isPublished.equals("1")) {
				String articleAge = getArticleAge(articleLastReplicatedDate);
				LOGGER.debug("articleAge is {}", articleAge);
				articlePojo.setArticleAge(articleAge);				
			}
		}
		LOGGER.info("end of setCommonPojoFromPageProp() method in ArticleDetailPagesData class");
	}

	/** * Converts a date into UNIX timeStamp format. */
	public static String convertToUnixFormat(Date date) {
		LOGGER.info("start of convertToUnixFormat() method in ArticleDetailPagesData class");
		long dateInUnixTimeStamp = 0l; 
		if(Objects.nonNull(date)) {
			dateInUnixTimeStamp = (date.getTime())/1000; 
		}
		String dateInUnixTimeStampStr = dateInUnixTimeStamp>0l ? String.valueOf(dateInUnixTimeStamp) : StringUtils.EMPTY;
		LOGGER.debug("dateInUnixTimeStampStr is {}", dateInUnixTimeStampStr);
		LOGGER.info("end of convertToUnixFormat() method in ArticleDetailPagesData class");
		return dateInUnixTimeStampStr;
	}

	/**
	 * This method returns the age of article in days
	 *
	 * @param Date:articleLastReplicatedDate
	 * 				 Date object for articleLastReplicatedDate
	 * @return String:ageOfArticleInDaysStr
	 * 				age of article in days
	 */
	private String getArticleAge(Date articleLastReplicatedDate) {
		LOGGER.info("start of getArticleAge() method in ArticleDetailPagesData class");
		String ageOfArticleInDaysStr = StringUtils.EMPTY;
		if(Objects.nonNull(articleLastReplicatedDate)) {
			Date currentDate = new Date();
			float ageOfArticleInDaysLong = ((float)currentDate.getTime()-(float)articleLastReplicatedDate.getTime())/86400000;
			LOGGER.debug("ageOfArticleInDaysLong is {}", ageOfArticleInDaysLong);
			int ageOfArticleInDaysRoundInt = Math.round(ageOfArticleInDaysLong);
			LOGGER.debug("ageOfArticleInDaysRoundInt is {}", ageOfArticleInDaysRoundInt);
			ageOfArticleInDaysStr = String.valueOf(ageOfArticleInDaysRoundInt);
		}
		LOGGER.debug("ageOfArticleInDaysStr is {}", ageOfArticleInDaysStr);
		LOGGER.info("end of getArticleAge() method in ArticleDetailPagesData class");
		return ageOfArticleInDaysStr;
	}

	/**
	 * This method sets category name and title
	 *
	 * @param ArticlePojo:articlePojo
	 * 				articlePojo object
	 */
	private void setCategoryNameTitle(ArticlePojo articlePojo) {
		LOGGER.info("start of setCategoryNameTitle() method in ArticleDetailPagesData class");
		List<TagsPojo> primaryTagList = articlePojo.getPrimaryTag();
		String primaryTagCategoryName  = null;
		String primaryTagCategoryTitle  = null;
		if(Objects.nonNull(primaryTagList)) {
			primaryTagCategoryName = primaryTagList.get(0).getTagName();
			LOGGER.debug("primaryTagCategoryName is {}", primaryTagCategoryName);
			primaryTagCategoryTitle = primaryTagList.get(0).getLocaleBasedTitle();
			LOGGER.debug("primaryTagCategoryTitle is {}", primaryTagCategoryTitle);
		}
		String categoryName = StringUtils.isNotBlank(primaryTagCategoryName) ? primaryTagCategoryName : articlePojo.getParentPageName();
		String categoryTitle = StringUtils.isNotBlank(primaryTagCategoryTitle) ? primaryTagCategoryTitle : articlePojo.getParentPageTitle();
		LOGGER.debug("categoryName is {}", categoryName);
		LOGGER.debug("categoryTitle is {}", categoryTitle);
		articlePojo.setCategoryName(categoryName);
		articlePojo.setCategoryTitle(categoryTitle);
		LOGGER.info("end of setCategoryNameTitle() method in ArticleDetailPagesData class");		
	}

	/**
	 * This method sets sub category name and title
	 *
	 * @param ArticlePojo:articlePojo
	 * 				articlePojo object
	 */
	private void setSubCategoryNameTitle(ArticlePojo articlePojo) {
		LOGGER.info("start of setSubCategoryNameTitle() method in ArticleDetailPagesData class");
		List<TagsPojo> secondaryTagList = articlePojo.getSecondaryTag();
		if(Objects.nonNull(secondaryTagList)) {
			StringBuilder subCategoryNamesSb = new StringBuilder();
			StringBuilder subCategoryTitlesSb = new StringBuilder();
			int secondaryTagListSize = secondaryTagList.size();
			LOGGER.debug("secondaryTagListSize is {}", secondaryTagListSize);
			if(secondaryTagListSize>0) {
				subCategoryNamesSb.append(Constants.SQUARE_BRACKET_BEGIN);
				subCategoryTitlesSb.append(Constants.SQUARE_BRACKET_BEGIN);
			}
			for(int secondaryTagCounter=0; secondaryTagCounter<secondaryTagListSize-1; secondaryTagCounter++) {
				subCategoryNamesSb.append(secondaryTagList.get(secondaryTagCounter).getTagName()).append(Constants.CSV_DELIMITER);
				subCategoryTitlesSb.append(secondaryTagList.get(secondaryTagCounter).getLocaleBasedTitle()).append(Constants.CSV_DELIMITER);
			}
			if(secondaryTagListSize>0) {
				subCategoryNamesSb.append(secondaryTagList.get(secondaryTagListSize-1).getTagName());
				subCategoryNamesSb.append(Constants.SQUARE_BRACKET_END);
				subCategoryTitlesSb.append(secondaryTagList.get(secondaryTagListSize-1).getLocaleBasedTitle());
				subCategoryTitlesSb.append(Constants.SQUARE_BRACKET_END);
			}
			LOGGER.debug("subCategoryNamesSb is {}", subCategoryNamesSb);
			articlePojo.setSubCategoryName(subCategoryNamesSb.toString());
			LOGGER.debug("subCategoryTitlesSb is {}", subCategoryTitlesSb);
			articlePojo.setSubCategoryTitle(subCategoryTitlesSb.toString());
		}
		LOGGER.info("end of setSubCategoryNameTitle() method in ArticleDetailPagesData class");		
	}

	/**
	 * This method sets category Id
	 *
	 * @param ArticlePojo:articlePojo
	 * 				articlePojo object
	 */
	private void setCategoryId(ArticlePojo articlePojo) {
		LOGGER.info("start of setCategoryId() method in ArticleDetailPagesData class");
		String primaryTagCategory  = articlePojo.getCategoryTitle();
		LOGGER.debug("primaryTagCategory is {}", primaryTagCategory);
		String secondaryTagsSubCategory = articlePojo.getSubCategoryTitle();
		secondaryTagsSubCategory = Objects.isNull(secondaryTagsSubCategory) ? "" : secondaryTagsSubCategory.replace(Constants.SQUARE_BRACKET_BEGIN, StringUtils.EMPTY).replace(Constants.SQUARE_BRACKET_END, StringUtils.EMPTY);
		LOGGER.debug("secondaryTagsSubCategory is {}", secondaryTagsSubCategory);
		StringBuilder categoryIdSb = new StringBuilder();
		if(StringUtils.isNotBlank(primaryTagCategory)) {
			categoryIdSb.append(primaryTagCategory);
			if(StringUtils.isNotBlank(secondaryTagsSubCategory)) {
				categoryIdSb.append(Constants.CSV_DELIMITER);
			}
		}
		if(StringUtils.isNotBlank(secondaryTagsSubCategory)) {
			categoryIdSb.append(secondaryTagsSubCategory);
		}
		LOGGER.debug("categoryIdSb is {}", categoryIdSb);
		articlePojo.setCategoryId(categoryIdSb.toString());
		LOGGER.info("end of setCategoryId() method in ArticleDetailPagesData class");
	}

	/**
	 * This method checks and sets vanity URL
	 *
	 * @param ValueMap:valueMap
	 * 				valueMap of article detail page
	 * @param ArticlePojo:articlePojo
	 * 				articlePojo object
	 */
	private void verifyAndSetVanityUrl(ValueMap valueMap, ArticlePojo articlePojo) {
		LOGGER.info("start of verifyAndSetVanityUrl() method in ArticleDetailPagesData class");
		String[] vanityUrlArray = valueMap.get(Constants.SLING_VANITY_PATH, String[].class);
		LOGGER.debug("vanityUrl from page properties is {}", vanityUrlArray);
		if(Objects.nonNull(vanityUrlArray) && valueMap.containsKey(Constants.SLING_REDIRECT)) {
			String isSlingRediectEnabled = valueMap.get(Constants.SLING_REDIRECT, String.class);
			if(Objects.nonNull(isSlingRediectEnabled) && isSlingRediectEnabled.equals("true")) {
				String vanityUrl = vanityUrlArray[0];
				LOGGER.debug("vanityUrl is {}", vanityUrl);
				vanityUrl = vanityUrl.substring(0,1).equals(Constants.SLASH) ? vanityUrl : Constants.SLASH + vanityUrl;
				articlePojo.setVanityUrl(vanityUrl);
				LOGGER.debug("vanityUrl set is {}", vanityUrl);
				articlePojo.setPageUrl(vanityUrl);
				LOGGER.debug("new pageUrl is {}", vanityUrl);					
			}
		}
		LOGGER.info("end of verifyAndSetVanityUrl() method in ArticleDetailPagesData class");
	}

	/**
	 * This method removes articles from articlePojos list, which are missing one or more mandatory fields
	 *
	 * @param List<ArticlePojo>:articlePojos
	 * 				list of articlePojo 
	 */
	private void removeArticlesWithoutMandarotyFields(List<ArticlePojo> articlePojos) {
		LOGGER.info("start of removeArticlesWithoutMandarotyFields() method in ArticleDetailPagesData class");
		if(Objects.nonNull(articlePojos)) {
			Iterator<ArticlePojo> articlePojosIterator = articlePojos.iterator();
			while(articlePojosIterator.hasNext()) {
				ArticlePojo articlePojo = articlePojosIterator.next();
				try {
					checkForMandatoryFields(articlePojo);
				} catch (Exception e) {
					LOGGER.error("Exception caused in removeArticlesWithoutMandarotyProp() method for ArticleDetailPagesData {}", e);
				}
				if(Objects.nonNull(articlePojo) && !articlePojo.isToBeShown() && shouldArticleBeRemoved(articlePojo)) {
					articlePojosIterator.remove();		
				}
			}
		}
		LOGGER.info("end of removeArticlesWithoutMandarotyFields() method in ArticleDetailPagesData class");
	}

	/**
	 * This method checks whether a particular article needs to be removed because of missing mandatory values or not
	 *
	 * @param List<ArticlePojo>:articlePojos
	 * 				list of articlePojo 
	 * @return boolean:toBeRemoved
	 * 				boolean value of whether this article needs to be removed or not
	 */			
	private boolean shouldArticleBeRemoved(ArticlePojo articlePojo) {
		LOGGER.info("start of shouldArticleBeRemoved() method in ArticleDetailPagesData class");
		boolean toBeRemoved = Boolean.FALSE;
		List<String> missingMandatoryFields = articlePojo.getMissingMandatoryFields();
		LOGGER.debug("missingMandatoryFields are {}", missingMandatoryFields);
		String articleDetailPagePath = articlePojo.getPageUrl();
		LOGGER.debug("articleDetailPagePath are {}", articleDetailPagePath);
		if(Objects.nonNull(missingMandatoryFields) && Objects.nonNull(articleDetailPagePath)) {
			try {
				throw new MandatotyFieldsMisssedException();
			} catch (Exception e) {
				LOGGER.error(String.format("articleDetailPage with the path %s is missing these mandatory properties %s", articleDetailPagePath, missingMandatoryFields));
			} finally {
				toBeRemoved = Boolean.TRUE;
			}
		}
		LOGGER.info("end of shouldArticleBeRemoved() method in ArticleDetailPagesData class");
		return toBeRemoved;
	}

	/**
	 * This method checks whether a particular article has all the mandatory fields or not
	 *
	 * @param List<ArticlePojo>:articlePojos
	 * 				list of articlePojo
	 */		
	private void checkForMandatoryFields(ArticlePojo articlePojo) throws NoSuchMethodException {
		LOGGER.info("start of checkForMandatoryFields() method in ArticleDetailPagesData class");
		String[] mandatoryFields = {"id", "name", "categoryId", "thumbnailUrl", "pageUrl", "categoryTitle", "categoryLink", "language", "created"};
		if(Objects.nonNull(articlePojo)) {
			List<String> missingMandatoryFields = new ArrayList<>();
			for(String field : mandatoryFields) {
				Class<ArticlePojo> articlePojoClass = ArticlePojo.class;
				try {
					Method method = articlePojoClass.getMethod(Constants.GET + field.substring(0,1).toUpperCase() + field.substring(1));
					Object propertyValueObj = method.invoke(articlePojo);
					if(Objects.nonNull(propertyValueObj)) {
						String fieldValue = propertyValueObj.toString();
						LOGGER.debug("fieldValue are {}", fieldValue);
						if(StringUtils.isBlank(fieldValue)) {
							articlePojo.setToBeShown(Boolean.FALSE);
							missingMandatoryFields.add(field);
						}						
					} else {
						articlePojo.setToBeShown(Boolean.FALSE);	
						missingMandatoryFields.add(field);				
					}
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					LOGGER.error("Exception caused in checkForMandatoryValues() method for ArticleDetailPagesData {}", e);
				}
			}
			articlePojo.setMissingMandatoryFields(missingMandatoryFields);
		}
		LOGGER.info("end of checkForMandatoryFields() method in ArticleDetailPagesData class");
	}
}

class MandatotyFieldsMisssedException extends Exception 
{ 
	private static final long serialVersionUID = 1L;
	public MandatotyFieldsMisssedException() 
	{ 
		super(); 
	} 
} 
