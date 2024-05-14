package com.mattel.fisherprice.core.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.fisherprice.core.constants.Constants;
import com.mattel.fisherprice.core.pojos.ArticlePojo;

/**
 * Utility class to convert feed for articles detail from List<ArticlePojo> to CSV format .
 */
public class ConvertArticleFeedToCSV {

	private ConvertArticleFeedToCSV() {
		throw new IllegalStateException("ConvertArticleFeedToCSV class");
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(ConvertArticleFeedToCSV.class);

	/**
	 * Converts feed for articles detail from List<ArticlePojo> to CSV compatible String
	 *
	 * @param List<ArticlePojo>:articlePojos
	 *          article details in POJOs
	 * @return String
	 * 			article details converted in CSV compatible String
	 * @throws JSONException
	 */
	public static String convertToCSVString(List<ArticlePojo> articlePojos) {
		LOGGER.info("convertToCSVString() method of ConvertArticleFeedToCSV class --> started");
		String[] csvEntityTitles = {"## RECSentity.id", "entity.name", "entity.categoryId", "entity.message", "entity.thumbnailUrl", "entity.value", "entity.pageUrl", 
				"entity.inventory", "entity.margin", "entity.categoryName", "entity.subCategoryName", "entity.categoryTitle", "entity.subCategoryTitle", 
				"entity.categoryLink", "entity.language", "entity.region", "entity.created", "entity.lastPublished", "entity.articleAge", "entity.isPublished"};
		LOGGER.debug("csvEntityTitles is {}", csvEntityTitles);
		String[] articlePojoFieldsForCSV = {"id", "name", "categoryId", "message", "thumbnailUrl", "value", "pageUrl", 
				"inventory", "margin", "categoryName", "subCategoryName", "categoryTitle", "subCategoryTitle", 
				"categoryLink", "language", "region", "created", "lastPublished", "articleAge", "isPublished"};
		LOGGER.debug("articlePojoFieldsForCSV is {}", articlePojoFieldsForCSV);
		StringBuilder articleFeedSb = new StringBuilder();
		setArticleFeedHeader(articleFeedSb);
		populateCSVRow(csvEntityTitles, articleFeedSb);
		for(ArticlePojo articlePojo : articlePojos) {
			String[] articleFieldValuesInArray = convertToCSVEntryStringArray(articlePojo, articlePojoFieldsForCSV);
			LOGGER.debug("articleFieldValuesInArray is {}", articleFieldValuesInArray);
			populateCSVRow(articleFieldValuesInArray, articleFeedSb);
		}
		String articleFeedStr = articleFeedSb.toString();
		LOGGER.debug("articleFeedStr is {}", articleFeedStr);
		LOGGER.info("convertToCSVString() method of ConvertArticleFeedToCSV class --> Ended");
		return articleFeedStr;
	}

	/**
	 * Generates the header lines for CSV file, taken from ArticleFeedConfigurationUtils
	 *
	 * @param StringBuilder:articleFeedSb
	 *          running StringBuilder for the article feed
	 */
	private static void setArticleFeedHeader(StringBuilder articleFeedSb){
		LOGGER.info("setArticleFeedHeader() method of ConvertArticleFeedToCSV class --> started");
		String[] headerLinesForCSV = ArticleFeedConfigurationUtils.getHeaderLinesForCSV();
		LOGGER.debug("headerLinesForCSV is {}", headerLinesForCSV);
		for(String headerLine : headerLinesForCSV) {
			headerLine = headerLine.replace(Constants.NEW_LINE_CHARACTER , StringUtils.EMPTY);
			LOGGER.debug("headerLine is {}", headerLine);
			articleFeedSb.append(headerLine).append(Constants.NEW_LINE_CHARACTER);
		}
		LOGGER.debug("articleFeedSb at the end of setArticleFeedHeader() method is {}", articleFeedSb);
		LOGGER.info("setArticleFeedHeader() method of ConvertArticleFeedToCSV class --> Ended");
	}

	/**
	 * populates a single row of CSV as per the entries in singleRowEntries
	 *
	 * @param String[]:singleRowEntries
	 *          containing array of String for row entries
	 * @param StringBuilder:articleFeedSb
	 *          running StringBuilder for the article feed
	 */
	private static void populateCSVRow(String[] singleRowEntries, StringBuilder articleFeedSb){
		LOGGER.info("populateCSVRow() method of ConvertArticleFeedToCSV class --> started");
		if(Objects.nonNull(singleRowEntries) && Objects.nonNull(articleFeedSb)) {
			for(String singleRowEntry : singleRowEntries) {
				articleFeedSb.append(singleRowEntry).append(Constants.CSV_DELIMITER);
				LOGGER.debug("singleRowEntry is {}", singleRowEntry);
			}
			articleFeedSb.append(Constants.NEW_LINE_CHARACTER);
		}
		LOGGER.debug("articleFeedSb at the end of populateCSVRow() method is {}", articleFeedSb);	
		LOGGER.info("populateCSVRow() method of ConvertArticleFeedToCSV class --> Ended");
	}

	/**
	 * converts ArticlePojo into String[] for CSV entries
	 *
	 * @param ArticlePojo:articlePojo
	 *          containing article details
	 * @param String[]:articlePojoFieldsForCSV
	 *          list of fields of ArticlePojo, the values of which needs to be added in CSV
	 * @return String[]:articleFieldValuesInArray
	 *          fields of ArticlePojo converted into String array
	 */
	private static String[] convertToCSVEntryStringArray(ArticlePojo articlePojo, String[] articlePojoFieldsForCSV) {
		LOGGER.info("convertToCSVEntryStringArray() method of ConvertArticleFeedToCSV class --> started");
		Class<ArticlePojo> articlePojoClass = ArticlePojo.class;
		String[] articleFieldValuesInArray = new String[articlePojoFieldsForCSV.length];
		if(Objects.nonNull(articlePojo)) {
			for(int fieldIndex = 0; fieldIndex<(articlePojoFieldsForCSV.length); fieldIndex++) {
				String fieldValue = StringUtils.EMPTY;
				String articlePojoField = articlePojoFieldsForCSV[fieldIndex];
				LOGGER.debug("articlePojoField is {}", articlePojoField);
				try {
					Method method = articlePojoClass.getMethod(Constants.GET + articlePojoField.substring(0,1).toUpperCase() + articlePojoField.substring(1));
					Object propertyValueObj = method.invoke(articlePojo);
					if(Objects.nonNull(propertyValueObj)) {
						fieldValue = refactorArticleProp(propertyValueObj.toString());	
						LOGGER.debug("fieldValue is {}", fieldValue);												
					} 
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					LOGGER.error("Exception caused in convertToCSVEntryStringArray() method for ConvertArticleFeedToCSV class {}", e);
				} finally {
					articleFieldValuesInArray[fieldIndex] = fieldValue;
				}
			}
		}
		LOGGER.info("convertToCSVEntryStringArray() method of ConvertArticleFeedToCSV class --> Ended");	
		LOGGER.debug("articleFieldValuesInArray is {}", articleFieldValuesInArray);					
		return articleFieldValuesInArray;
	}

	/**
	 * Makes the String compatible for CSV entry
	 *
	 * @param String:propertyVal
	 *          property value/any string
	 * @return String
	 * 			non null string
	 */
	private static String refactorArticleProp(String propertyVal){
		LOGGER.info("refactorArticleProp() method of ConvertArticleFeedToCSV class --> Started");
		if(StringUtils.isNoneBlank(propertyVal)) {
			propertyVal = propertyVal.replaceAll("[\r\n]+","");	
			LOGGER.debug("propertyVal is {}",propertyVal);
			propertyVal = handleCommas(propertyVal);
		}	
		LOGGER.debug("refactored propertyVal is {}",propertyVal);
		LOGGER.info("refactorArticleProp() method of ConvertArticleFeedToCSV class --> Ended");
		return Objects.nonNull(propertyVal) ? propertyVal : StringUtils.EMPTY;
	}

	/**
	 * Encloses the input String with "" if it contains ','
	 *
	 * @param String:propertyVal
	 *          property value/any string
	 * @return String
	 * 			formatted string
	 */
	private static String handleCommas(String propertyVal){
		LOGGER.info("handleCommas() method of ConvertArticleFeedToCSV class --> Started");
		StringBuilder propertyValHCSb = new StringBuilder();
		if(propertyVal.contains(",")) {
			propertyValHCSb.append(Constants.DOUBLE_QUOTES).append(propertyVal).append(Constants.DOUBLE_QUOTES);	
			LOGGER.debug("propertyValSb is {}",propertyValHCSb);
			return propertyValHCSb.toString();
		}
		LOGGER.info("handleCommas() method of ConvertArticleFeedToCSV class --> Ended");
		return propertyVal;
	}
}
