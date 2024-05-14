package com.mattel.productvideos.core.utils;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.productvideos.core.constants.Constants;

/**
 * @author CTS. Service for properties configuration.
 */

public class ProductVideosUtil {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductVideosUtil.class);
  
  /* Added Private Constructor for Sonar */
  private ProductVideosUtil() {
  }
  
  /**
   * 
   * @param node
   * @param propertyName
   * @return
   */
  public static String getPropertyValue(Node node, String propertyName) {
    LOGGER.debug("Start of getPropertyValue method");
    String propValue = "";
    try {
      if (node.hasProperty(propertyName)) {
        Property property = node.getProperty(propertyName);
        propValue = property.getValue().toString();
      }
    } catch (RepositoryException e) {
      LOGGER.error("Exception occured in getPropertyValue method", e.getMessage());
    }
    LOGGER.debug("End of getPropertyValue method");
    return propValue;
  }
  
  /**
   * This method read the scene7 properties from the node and returns 
   * the formed scene7 URL according to the Asset.
   * @param node
   * @return scene7Path
   */
  public static String getAssetScene7URL(Node assetNode) {
    LOGGER.debug("Start of getAssetScene7URL method");
    String scene7Path = "";
    try {
        if (areSecene7PropsPresent(assetNode)) {
            LOGGER.debug("All scene7 properties are present in asset node");
            String scene7Name = "";
            if (assetNode.hasProperty("cq:name"))
              scene7Name = assetNode.getName();
            String scene7File = ProductVideosUtil.getPropertyValue(assetNode,Constants.DYNAMIC_MEDIA_FILE);
            String scene7Domain = ProductVideosUtil.getPropertyValue(assetNode,Constants.DYNAMIC_MEDIA_DOMAIN);
            String scene7Folder = ProductVideosUtil.getPropertyValue(assetNode,Constants.DYNAMIC_MEDIA_FOLDER);
            if (assetNode.hasProperty(Constants.DYNAMIC_MEDIA_TYPE)) {
              Property scene7TypeProperty = assetNode.getProperty(Constants.DYNAMIC_MEDIA_TYPE);
              String scene7Type = scene7TypeProperty.getValue().toString();
              if (scene7Type.equals(Constants.IMAGE)) {
                scene7Path = scene7Domain + Constants.IMAGE_STRING + scene7File;
              } else if (scene7Type.equals(Constants.VIDEO_TYPE)) {
                scene7Path = scene7Domain + Constants.CONTENT_STRING + scene7File
                    + Constants.DYNAMIC_MEDIA_FILE_EXTENSION;
              } else {
                scene7Path = scene7Domain + Constants.CONTENT_STRING + scene7Folder + scene7Name;
              }
              LOGGER.debug("scene7Path is : {}", scene7Path);
            }
          }
    } catch (RepositoryException e) {
        LOGGER.error("RepositoryException ocured in getAssetScene7URL method: " + e.getMessage());
    }
    LOGGER.debug("End of getAssetScene7URL method");
    return scene7Path;
  }
  
  /**
   * This method checks of all the scnen7 properties are present on the node.
   * 
   * @param dataPathNode
   * @return
   * @throws RepositoryException
   */
  private static boolean areSecene7PropsPresent(Node dataPathNode) throws RepositoryException {
    return dataPathNode.hasProperty(Constants.DYNAMIC_MEDIA_FILE)
        && dataPathNode.hasProperty(Constants.DYNAMIC_MEDIA_DOMAIN)
        && dataPathNode.hasProperty(Constants.DYNAMIC_MEDIA_TYPE)
        && dataPathNode.hasProperty(Constants.DYNAMIC_MEDIA_FILE_NAME)
        && dataPathNode.hasProperty(Constants.DYNAMIC_MEDIA_FOLDER);
  }
}
