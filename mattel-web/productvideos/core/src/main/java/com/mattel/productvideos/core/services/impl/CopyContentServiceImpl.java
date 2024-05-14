package com.mattel.productvideos.core.services.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Workspace;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.i18n.ResourceBundleProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.i18n.I18n;
import com.day.cq.wcm.api.Page;
import com.mattel.productvideos.core.constants.Constants;

/**
 * @author CTS
 *
 */
@Component(service = CopyContentServiceImpl.class)
public class CopyContentServiceImpl {
	private static final Logger LOGGER = LoggerFactory.getLogger(CopyContentServiceImpl.class);

	@Reference
	private MultifieldReader multifieldReader;

	@Reference(target = "(component.name=org.apache.sling.i18n.impl.JcrResourceBundleProvider)")
	private ResourceBundleProvider resourceBundleProvider;

	/**
	 * @param jwRootPath
	 *            jwRootPath where locale list in configured
	 * @param payLoadPath
	 *            payload path
	 * @param payloadType
	 *            payload type - asset / content
	 * @param resourceResolver
	 *            resource resolver object
	 */
	public void copyContent(String jwRootPath, String payLoadPath, String payloadType,
			ResourceResolver resourceResolver) {
		CopyContentServiceImpl.LOGGER.info("createLocaleFolders -> Start, jwRootPath : {} , payLoadPath : {}",
				jwRootPath, payLoadPath);
		CopyContentServiceImpl.LOGGER.info("execute -> start : {}", payloadType);
		List<String> localeList = new LinkedList<>();

		try {
			if (Objects.nonNull(resourceResolver)) {
				Resource jwRootResource = resourceResolver.resolve(jwRootPath + Constants.LOCALE_ABS_PATH);
				CopyContentServiceImpl.LOGGER.debug(jwRootResource.getPath());
				Map<String, ValueMap> localeMap = multifieldReader.propertyReader(jwRootResource.adaptTo(Node.class),
						resourceResolver);

				for (Map.Entry<String, ValueMap> mapEntry : localeMap.entrySet()) {
					Object locale = mapEntry.getValue().get(Constants.LOCALE);
					if (null != locale) {
						localeList.add(locale.toString());
					}
				}
			}
			CopyContentServiceImpl.LOGGER.debug("Locale List : {}", localeList);
			createLocaleSpecificFolderAndCopyContent(localeList, payLoadPath, payloadType, resourceResolver);
		} catch (Exception ex) {
			CopyContentServiceImpl.LOGGER.error("Exception Occured : {}", ex);
		}

		LOGGER.info("execute -> stop : {}");
	}

	/**
	 * @param localeList
	 *            locale list
	 * @param payLoadPath
	 *            payload path
	 * @param payloadType
	 *            payload type - asset / content
	 * @param resourceResolver
	 *            resource resolver object
	 */
	private void createLocaleSpecificFolderAndCopyContent(List<String> localeList, String payLoadPath,
			String payloadType, ResourceResolver resourceResolver) {
		CopyContentServiceImpl.LOGGER.info("createLocaleSpecificFolderAndCopyCF -> Start");
		final Session session = resourceResolver.adaptTo(Session.class);
		try {
			String payloadParenttPath = payLoadPath.substring(0, payLoadPath.lastIndexOf('/'));
			Resource payloadPageResource = resourceResolver.resolve(payloadParenttPath);
			String nodeName = payLoadPath.substring(payLoadPath.lastIndexOf(Constants.SLASH), payLoadPath.length());

			if (payloadType.equals(Constants.ASSETS)) {
				localeList.forEach(locale -> {
					String destinationPath = payloadParenttPath + Constants.SLASH + locale + nodeName;
					createLocaleSpecificFolder(payloadPageResource, locale, session);
					copyContenttoLocaleFolder(session, payLoadPath, destinationPath);
					updateLocalizedProperty(destinationPath, locale, Constants.CF_MASTER_NODE_PATH,
							Constants.CF_DESCRIPTION_PROPERTY, resourceResolver);
				});
			} else {
				localeList.forEach(locale -> executeWorkflowForPage(payLoadPath, resourceResolver, session,
						payloadPageResource, nodeName, locale));
			}
			saveSession(session);
		} finally {
			CopyContentServiceImpl.LOGGER.info("Done");
		}
		CopyContentServiceImpl.LOGGER.info("createLocaleSpecificFolderAndCopyCF -> End");
	}

	/**
	 * @param payLoadPath
	 *            payload path
	 * @param resourceResolver
	 *            resource resolver object
	 * @param session
	 *            session object
	 * @param payloadPageResource
	 *            payload page resource
	 * @param nodeName
	 *            node name
	 * @param locale
	 *            locale
	 * 
	 *            method will gate called if workflow is executed for page
	 */
	private void executeWorkflowForPage(String payLoadPath, ResourceResolver resourceResolver, final Session session,
			Resource payloadPageResource, String nodeName, String locale) {
		CopyContentServiceImpl.LOGGER.info("executeWorkflowForPage -> Start");
		Page pageParent = payloadPageResource.adaptTo(Page.class);
		if (Objects.nonNull(pageParent)) {
			Page absoluteParent = pageParent.getAbsoluteParent(3);
			String parentPageName = pageParent.getName();

			if (absoluteParent.hasChild(locale)) {
				String destinationPath = absoluteParent.getPath() + Constants.SLASH + locale + Constants.SLASH
						+ parentPageName + nodeName;
				CopyContentServiceImpl.LOGGER.debug("Destination Path : {}", destinationPath);
				copyContenttoLocaleFolder(session, payLoadPath, destinationPath);

				Resource videoComponentResource = resourceResolver
						.resolve(destinationPath + Constants.VIDEO_COMPONENT_PATH);
				Node videoComponentNode = videoComponentResource.adaptTo(Node.class);
				if (!locale.equals(Constants.EN_LOCALE)) {
					updateLocaleSpecificCFPath(videoComponentNode, locale);
					if (StringUtils.isNotEmpty(parentPageName) && parentPageName.equalsIgnoreCase(Constants.GALLERY)) {
						CopyContentServiceImpl.LOGGER.debug("Updating localized Gallery page title");
						updateLocalizedProperty(destinationPath, locale, Constants.JCR_CONTENT_PATH, Constants.TITLE,
								resourceResolver);
					}
				}
			} else {
				CopyContentServiceImpl.LOGGER.debug("Locale : {} does node Exists", locale);
			}
		}
		CopyContentServiceImpl.LOGGER.info("executeWorkflowForPage -> Stop");
	}

	/**
	 * @param payloadParentFolder
	 *            parent folder of payload folder
	 * @param locale
	 *            locale name, using which folder will be created
	 * @param session
	 *            jcr session object
	 */
	private void createLocaleSpecificFolder(Resource payloadParentFolder, String locale, Session session) {
		CopyContentServiceImpl.LOGGER.info("createLocaleSpecificFolder -> Start");
		try {
			Node payloadParentNode = payloadParentFolder.adaptTo(Node.class);

			if (Objects.nonNull(payloadParentNode) && !payloadParentNode.hasNode(locale)) {
				Node localeFolder = payloadParentNode.addNode(locale, Constants.SLING_FOLDER);
				Node jcrContentFolder = localeFolder.addNode(Constants.PAGE_JCR_CONTENT, JcrConstants.NT_UNSTRUCTURED);
				jcrContentFolder.setProperty(Constants.TITLE, locale);
			}
		} catch (Exception e) {
			CopyContentServiceImpl.LOGGER.error("Error Occured while {} Folder Creation", e);
		}
		saveSession(session);
		CopyContentServiceImpl.LOGGER.info("createLocaleSpecificFolder -> End");
	}

	/**
	 * @param session
	 *            jcr session object
	 * @param srcPath
	 *            content path to be copied
	 * @param destPath
	 *            location to which content to be copied
	 */
	private void copyContenttoLocaleFolder(Session session, String srcPath, String destinationPath) {
		CopyContentServiceImpl.LOGGER.info("copyCFtoLocaleFolder -> Start");
		CopyContentServiceImpl.LOGGER.debug("Copy Path Details : Source Path {} ,  Destination Path {} ", srcPath,
				destinationPath);
		try {
			Workspace workspace = session.getWorkspace();
			workspace.copy(srcPath, destinationPath);
		} catch (RepositoryException e) {
			CopyContentServiceImpl.LOGGER.error("Error Occured while {} copying Content Fragment", e);
		}
		saveSession(session);
		CopyContentServiceImpl.LOGGER.info("copyCFtoLocaleFolder -> End");
	}

	/**
	 * @param videoComponentNode
	 *            node where we needs to update content fragment path
	 * @param locale
	 *            locale name
	 */
	private void updateLocaleSpecificCFPath(Node videoComponentNode, String locale) {
		CopyContentServiceImpl.LOGGER.info("updateLocaleSpecificCFPath -> Start");
		try {
			if (Objects.nonNull(videoComponentNode)) {
				videoComponentNode.hasProperty(Constants.CF_PAGE_PROPERTY);
				String legaCFPath = videoComponentNode.getProperty(Constants.CF_PAGE_PROPERTY).getValue().getString();
				String folderPath = legaCFPath.substring(0, legaCFPath.lastIndexOf(Constants.SLASH));

				String cfName = legaCFPath.substring(legaCFPath.lastIndexOf(Constants.SLASH), legaCFPath.length());
				String newCFPath = folderPath + Constants.SLASH + locale + cfName;

				CopyContentServiceImpl.LOGGER.debug("Legacy CF Path : {} , New CF Path : {}", legaCFPath, newCFPath);

				videoComponentNode.setProperty(Constants.CF_PAGE_PROPERTY, newCFPath);
			}
		} catch (RepositoryException e) {
			CopyContentServiceImpl.LOGGER.error("Exception occured while updating content fragment path", e);
		}
		CopyContentServiceImpl.LOGGER.info("updateLocaleSpecificCFPath -> Start");
	}

	/**
	 * @param destinationPath
	 *            page path on which property to be updated
	 * @param localeName
	 *            locale name
	 * @param nodePath
	 *            node path on which property to be replaced
	 * @param propertyName
	 *            property name
	 * @param resourceResolver
	 *            resource resolver object
	 */
	private void updateLocalizedProperty(String destinationPath, String localeName, String nodePath,
			String propertyName, ResourceResolver resourceResolver) {
		CopyContentServiceImpl.LOGGER.info("updateLocalizedDescription -> Start");
		try {
			Resource masterNodeResource = resourceResolver.resolve(destinationPath + nodePath);
			Node masterNode = masterNodeResource.adaptTo(Node.class);
			if (Objects.nonNull(masterNode)) {
				String enDescription = masterNode.getProperty(propertyName).getValue().getString();
				String localizedDescription = getLocaleSpecificString(localeName, enDescription);
				CopyContentServiceImpl.LOGGER.debug("Localized Description : {}", localizedDescription);
				masterNode.setProperty(propertyName, localizedDescription);
			}
		} catch (RepositoryException e) {
			LOGGER.error("Exception occured while updating localized description ", e);
		}
		CopyContentServiceImpl.LOGGER.info("updateLocalizedDescription -> Start");
	}

	/**
	 * @param session
	 *            jcr session object
	 */
	private void saveSession(Session session) {
		if (Objects.nonNull(session)) {
			try {
				session.save();
			} catch (RepositoryException e) {
				LOGGER.error("Exception while saving session ", e);
			}
		}
	}

	/**
	 * @param localeName
	 *            locale name using which we can get Locale instance
	 * @param str
	 *            String which we want to in localized version
	 * @return
	 */
	private String getLocaleSpecificString(String localeName, String str) {
		CopyContentServiceImpl.LOGGER.info("getLocaleSpecificString -> Start");
		Locale locale = new Locale(localeName);
		ResourceBundle resourceBundle = resourceBundleProvider.getResourceBundle(locale);
		I18n i18n = new I18n(resourceBundle);
		CopyContentServiceImpl.LOGGER.info("getLocaleSpecificString -> End");
		return i18n.get(str.trim());
	}

}
