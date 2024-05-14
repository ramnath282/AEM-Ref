package com.mattel.fisherprice.core.schedulers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.fisherprice.core.constants.Constants;
import com.mattel.fisherprice.core.services.UpdateCSVFile;
import com.mattel.fisherprice.core.utils.ArticleFeedConfigurationUtils;

@Designate(ocd = ArticleFeedScheduler.Config.class)
@Component(service = Runnable.class, immediate = true)
public class ArticleFeedScheduler implements Runnable {

	@ObjectClassDefinition(name = "FisherPrice Article Feed Generator Scheduler", description = "A Scheduler to generate and store the article feed for FP")
	public @interface Config {
		@AttributeDefinition(name = "Cron-job expression", description = "Expression stands for sec min hour monthDay month weekday year")
		String scheduler_expression() default "0 0 0 1/1 * ? *";

		@AttributeDefinition(name = "Concurrent task", description = "Whether or not to schedule this task concurrently")
		boolean scheduler_concurrent() default false;
	}

	@Reference
	private UpdateCSVFile updateCSVFile;

	public void setUpdateCSVFile(UpdateCSVFile updateCSVFile) {
		this.updateCSVFile = updateCSVFile;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(ArticleFeedScheduler.class);

	@Activate
	protected void activate(final Config config) {
		/**
		 * Have kept this method empty as we need it
		 */
	}

	@Override
	public void run() {
		LOGGER.info("run() method of ArticleFeedScheduler class --> started");
		Map<String, List<String>> articleFeedFiltersMap = new HashMap<>();
		addFiltersToMap(ArticleFeedConfigurationUtils.getDamPathToUploadArticleFeed(), Constants.DAM_PATH_TO_UPLOAD_ARTILCE_FEED, articleFeedFiltersMap);
		addFiltersToMap(ArticleFeedConfigurationUtils.getArticleFeedFileInitialName(), Constants.ARTICLE_FEED_FILE_INITIAL_NAME, articleFeedFiltersMap);
		addFiltersToMap(ArticleFeedConfigurationUtils.getHeaderLinesForCSV(), Constants.HEADER_LINES_FOR_CSV, articleFeedFiltersMap);
		addFiltersToMap(ArticleFeedConfigurationUtils.getArticleCompResourceType(), Constants.ARTICLE_COMP_RESOURCE_TYPE, articleFeedFiltersMap);
		addFiltersToMap(ArticleFeedConfigurationUtils.getBrandBasePath(), Constants.BRAND_BASE_PATH, articleFeedFiltersMap);
		addFiltersToMap(ArticleFeedConfigurationUtils.getArticleDetailTemplatePath(), Constants.ARTICLE_DETAIL_TEMPLATE_PATH, articleFeedFiltersMap);
		addFiltersToMap(ArticleFeedConfigurationUtils.getArticleBasePath(), Constants.ARTICLE_BASE_PATH, articleFeedFiltersMap);
		String[] localesForArticleFeed =  ArticleFeedConfigurationUtils.getLocalesForArticleFeed();
		LOGGER.debug("countryLocalesArray is {}", localesForArticleFeed);
		for(String locale : localesForArticleFeed) {
			if(StringUtils.isNoneBlank(locale)) {
				addFiltersToMap(locale, Constants.LOCALES_FOR_ARTICLE_FEED, articleFeedFiltersMap);
				LOGGER.debug("queryMap generate from ArticleFeedScheduler is {}", articleFeedFiltersMap);				
			}		
			try {
				updateCSVFile.generateArticleFeedCSV(articleFeedFiltersMap);
			} catch (JSONException e) {
				LOGGER.error("JSONException occurred while calling generateArticleFeedCSV() from ArticleFeedScheduler class");
			}				
		}

		LOGGER.info("run() method of ArticleFeedScheduler class --> ended");
	}

	/**
	 * adds filter to Map when the value to be added is of type String
	 * 
	 * @param String:filterConfigValue
	 * 				node of the existing file which needs to be updated
	 * @param String:filterMapKey
	 * 				path of the file
	 * @param Session:session
	 * 				current session object
	 * @param Map<String, List<String>>:articleFeedFiltersMap
	 * 				feed with which the file needs be populated
	 * 
	 */	
	private void addFiltersToMap(String filterConfigValue, String filterMapKey, Map<String, List<String>> articleFeedFiltersMap) {
		LOGGER.info("addFiltersToMap() method of ArticleFeedScheduler class --> started");
		LOGGER.debug("filterConfigValue is {}", filterConfigValue);
		LOGGER.debug("filterMapKey is {}", filterMapKey);
		if(Objects.nonNull(filterConfigValue)) {
			List<String> filterConfigValueList = new ArrayList<>();
			filterConfigValueList.add(filterConfigValue);
			articleFeedFiltersMap.put(filterMapKey, filterConfigValueList);
		}	
		LOGGER.info("addFiltersToMap() method of ArticleFeedScheduler class --> ended");	
	}	

	/**
	 * adds filter to Map when the value to be added is of type String[]
	 * 
	 * @param String[]:filterConfigValue
	 * 				node of the existing file which needs to be updated
	 * @param String:filterMapKey
	 * 				path of the file
	 * @param Session:session
	 * 				current session object
	 * @param Map<String, List<String>>:articleFeedFiltersMap
	 * 				feed with which the file needs be populated
	 * 
	 */		
	private void addFiltersToMap(String[] filterConfigValue, String filterMapKey, Map<String, List<String>> articleFeedFiltersMap) {
		LOGGER.info("addFiltersToMap() method of ArticleFeedScheduler class --> started");
		LOGGER.debug("filterConfigValue is {}", filterConfigValue);
		LOGGER.debug("filterMapKey is {}", filterMapKey);
		if(Objects.nonNull(filterConfigValue)) {
			List<String> filterConfigValueList = Arrays.asList(filterConfigValue);
			articleFeedFiltersMap.put(filterMapKey, filterConfigValueList);
		}
		LOGGER.info("addFiltersToMap() method of ArticleFeedScheduler class --> ended");
	}

}
