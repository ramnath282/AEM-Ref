package com.mattel.fisherprice.core.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;

import com.day.cq.dam.api.AssetManager;
import com.day.cq.replication.AccessDeniedException;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.Replicator;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.json.JSONException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.fisherprice.core.constants.Constants;
import com.mattel.fisherprice.core.utils.ArticleFeedConfigurationUtils;

/**
 * service which creates locale specific article feed files in CSV format
 */
@Component(service = UpdateCSVFile.class, immediate = true)
public class UpdateCSVFile {

	@Reference
	ResourceResolverFactory resolverFactory;

	@Reference
	private ArticleDetailPagesData articleDetailPagesDataWrapper;	

	@Reference
	private Replicator replicator;

	Session session;	

	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateCSVFile.class);

	public void setArticleDetailPagesDataWrapper(ArticleDetailPagesData articleDetailPagesDataWrapper) {
		this.articleDetailPagesDataWrapper = articleDetailPagesDataWrapper;
	}

	public void setResolverFactory(ResourceResolverFactory resolverFactory) {
		this.resolverFactory = resolverFactory;
	}

	/**
	 * generates a CSV file containing article details
	 * 
	 * @param Map<String, List<String>>:articleFeedFiltersMap
	 * 				Map containing the parameters to select the articles to show the feed
	 * @throws JSONException 
	 * @
	 */
	public void generateArticleFeedCSV(Map<String, List<String>> articleFeedFiltersMap) throws JSONException {
		LOGGER.info("generateArticleFeedCSV() method of UpdateCSVFile class --> started");
		try {
			String articleFeedInCSV = articleDetailPagesDataWrapper.getArticleFeedInCSV(articleFeedFiltersMap);
			LOGGER.debug("articleFeedInCSV is {}",articleFeedInCSV);
			String csvFolderPath = ArticleFeedConfigurationUtils.getDamPathToUploadArticleFeed();
			LOGGER.debug("csvFolderPath is {}",csvFolderPath);
			String csvFilePath = csvFolderPath + Constants.SLASH + ArticleFeedConfigurationUtils.getArticleFeedFileInitialName();
			LOGGER.debug("csvFilePath before adding the locales is {}",csvFilePath);
			List<String> countryLocalesList = articleFeedFiltersMap.get(Constants.LOCALES_FOR_ARTICLE_FEED);
			LOGGER.debug("countryLocalesList is {}",countryLocalesList);
			StringBuilder csvFilePathSB = new StringBuilder(csvFilePath);
			boolean anyLocalePresent = Boolean.FALSE;
			for(String locale : countryLocalesList) {
				csvFilePathSB.append(Constants.UNDERSCORE).append(locale);
				anyLocalePresent = Boolean.TRUE;
			}
			if(anyLocalePresent) {
				csvFilePathSB.append(Constants.DOT_CSV);	
				csvFilePath = csvFilePathSB.toString();
				LOGGER.debug("final csvFilePath after adding the locales is {}",csvFilePath);
				prepareToUploadFile(csvFilePath, csvFolderPath, articleFeedInCSV, Constants.CSV_MIME_TYPE);				
			}
		} catch(Exception e) {
			LOGGER.error("Exception occurred in generateArticleFeedCSV() method for UpdateCSVFile class {}", e);
		} 	
		LOGGER.info("generateArticleFeedCSV() method of UpdateCSVFile class --> ended");		
	}

	/**
	 * checks whether the folder path exists. If folder path exists, checks if the file already exists or not; based on that either calls 
	 * a method to update the file with the new feed or calls a method to create and populate the file with feed
	 * 
	 * @param String:filePath
	 * 				path of the file
	 * @param String:folderPath
	 * 				path of the folder on which file needs to be uploaded
	 * @param String:feed
	 * 				feed with which the file needs be populated
	 * @param String:mimeType
	 * 				mimeType of the new file(if) to be created
	 * @throws RepositoryException
	 */
	public void prepareToUploadFile(String filePath, String folderPath, String feed, String mimeType) throws RepositoryException {
		LOGGER.info("prepareToUploadFile() method of UpdateCSVFile class --> started");
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, Constants.READWRITESERVICE);
		try (ResourceResolver resourceResolver = resolverFactory.getServiceResourceResolver(map)) {
			session = resourceResolver.adaptTo(Session.class);
			if (Objects.nonNull(session)) {
				if (session.nodeExists(folderPath)) {
					if (session.nodeExists(filePath)) {
						LOGGER.debug("node for file exists");
						Node existingOriginalNode = session.getNode(filePath + Constants.FILE_JCR_DATA);
						updateReplFile(existingOriginalNode,filePath,session,feed);
					} else {
						LOGGER.debug("node for file does not exist");
						AssetManager assetManager = resourceResolver.adaptTo(AssetManager.class);
						createReplFile(filePath,assetManager,feed,mimeType);
					}
				}
			} else {
				LOGGER.error("Folder to upload the article feed files does not exist in DAM");
			}
		} catch(Exception e) {
			LOGGER.error("Exception occurred in prepareToUploadFile() method for UpdateCSVFile class {}", e);
		} finally {
			if(Objects.nonNull(session) && session.isLive()) {
				session.logout();
				LOGGER.debug("Session Logged Out");
			}				
		}
		LOGGER.info("prepareToUploadFile() method of UpdateCSVFile class --> ended");
	}

	/**
	 * updates the file (identified by its node) with the feed and then replicates it
	 * 
	 * @param Node:originalNode
	 * 				node of the existing file which needs to be updated
	 * @param String:filePath
	 * 				path of the file
	 * @param Session:session
	 * 				current session object
	 * @param String:feed
	 * 				feed with which the file needs be populated
	 * @throws RepositoryException
	 * @throws IOException
	 */
	public void updateReplFile(Node originalNode, String filePath, Session session, String feed) throws RepositoryException, IOException {
		LOGGER.info("updateReplFile() method of UpdateCSVFile class --> started");
		ValueFactory factory = session.getValueFactory();
		try (InputStream is = new ByteArrayInputStream(feed.getBytes(StandardCharsets.UTF_8))) {
			Binary binary = factory.createBinary(is);
			Value value = factory.createValue(binary);
			LOGGER.debug("value is {}", value);
			originalNode.setProperty(Constants.JCR_DATA, value);
			session.save();  
			try {
				replicator.replicate(session, ReplicationActionType.ACTIVATE, filePath);
				LOGGER.debug("Replication for file {}",filePath);  				
			} catch (AccessDeniedException e) {
				LOGGER.error("AccessDeniedException occurred in updateReplFile() method  for UpdateCSVFile class {}", e);
			}
		} catch(Exception e) {
			LOGGER.error("Exception occurred in updateReplFile() method for UpdateCSVFile class {}", e);        	
		}   
		LOGGER.info("updateReplFile() method of UpdateCSVFile class --> ended");
	}   

	/**
	 * creates a file(at the path provided),populates it with the feed and then replicates it
	 * 
	 * @param String:filePath
	 * 				path of the file
	 * @param AssetManager:assetManager
	 * 				assetManager object
	 * @param String:feed
	 * 				feed with which the file needs be populated
	 * @param String:mimeType
	 * 				mimeType of the new file to be created
	 * @throws RepositoryException
	 * @throws IOException
	 */
	public void createReplFile(String filePath, AssetManager assetManager, String feed, String mimeType) throws RepositoryException, IOException {
		LOGGER.info("createReplFile() method of UpdateCSVFile class --> started");
		try (InputStream is = new ByteArrayInputStream(feed.getBytes(StandardCharsets.UTF_8))) {
			assetManager.createAsset(filePath, is, mimeType, Boolean.TRUE);
			session.save(); 
			try {
				replicator.replicate(session, ReplicationActionType.ACTIVATE, filePath);
				LOGGER.debug("Replication for file {}",filePath);	
			} catch (AccessDeniedException e) {
				LOGGER.error("AccessDeniedException occurred in createReplFile() method  for UpdateCSVFile class {}", e);
			}
		} catch(Exception e) {
			LOGGER.error("Exception occurred in createReplFile() method for UpdateCSVFile class {}", e);        	
		}   
		LOGGER.info("createReplFile() method of UpdateCSVFile class --> ended");
	}  

}
