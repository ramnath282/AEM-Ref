package com.mattel.productvideos.core.utils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CTS. Service for properties configuration.
 */
@Component(service = ProductVideosPropertyUtils.class)
@Designate(ocd = ProductVideosPropertyUtils.Config.class)
public class ProductVideosPropertyUtils {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductVideosPropertyUtils.class);

  private String sitePath;
  private String rootAssetsPath;

  @Activate
  public void activate(final Config config) {
    sitePath = config.sitePath();
    rootAssetsPath = config.rootAssetsPath();
    LOGGER.debug("Site Root Path is: {} and root Asset path is: {}", sitePath,rootAssetsPath);
  }

  @ObjectClassDefinition(name = "JW Product Videos Properties Configuration") public @interface Config {

    @AttributeDefinition(name = "Root Site Path", description = "Please Enter root site path")
    String sitePath();
    
    @AttributeDefinition(name = "Root Assets Path", description = "Please Enter Root Asset Path")
    String rootAssetsPath();
  }

  public String getSitePath() {
    return sitePath;
  }
  
  public String getRootAssetsPath() {
    return rootAssetsPath;
  }
  
}
