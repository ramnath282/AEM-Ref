package com.mattel.global.core.events;

import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationAction;
import com.mattel.global.core.utils.GlobalUtils;
import com.mattel.global.core.utils.PropertyReaderUtils;

@Component(service = EventHandler.class, immediate = true, property = {
    Constants.SERVICE_DESCRIPTION + "=Handles replication events for news articles on author instance",
    EventConstants.EVENT_TOPIC + "=" + ReplicationAction.EVENT_TOPIC,
    EventConstants.EVENT_FILTER + "=(paths=/content/mattel/mattel-corporate/*/home/news/*)" })
@Designate(ocd = NewsReplicationEventHandler.Config.class)
public class NewsReplicationEventHandler implements EventHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(NewsReplicationEventHandler.class);
  private BundleContext bundleContext;

  @Reference
  private SlingSettingsService slingSettingsService;

  @Reference
  private PropertyReaderUtils propertyReaderUtils;

  private int connectionTimeout;

  @Override
  public void handleEvent(Event event) {
    LOGGER.debug("Processing event {} with bundle context {}", event, bundleContext);
    if (isAuthor()) {
      LOGGER.debug("Event is triggered on author enviornment");
      ReplicationAction action = ReplicationAction.fromEvent(event);
      LOGGER.debug("Replication action {} is performed on path {}", action.getType(), action.getPath());
      String siteKey = getSiteKey(action.getPath());
      if (Objects.nonNull(connectionTimeout) && StringUtils.isNotBlank(siteKey)) {
        LOGGER.debug("configured connectionTimeout for siteky: {} is: {}", siteKey, connectionTimeout);
        GlobalUtils.triggerSnpIndexing(connectionTimeout,siteKey,propertyReaderUtils);
      }
    }
  }

  /**
   * This method reads the action path and calculates the 
   * siteKey by doing substring manipulation
   * @param path action path
   * @return siteKey
   */
  private String getSiteKey(String path) {
    LOGGER.debug("Start of getSiteKey method");
    String sitekey = StringUtils.EMPTY;
    if(StringUtils.isNotBlank(path)){
        String keystr = path.substring("/content/mattel/mattel-corporate/".length(),path.indexOf("/home/news"));
        if(StringUtils.isNotBlank(keystr) && !keystr.contains("language-masters") && keystr.split("/").length > 1){
            sitekey = "corporate_".concat(keystr.split("/")[1]);
        }
    }
    LOGGER.debug("End of getSiteKey method");
    return sitekey;
  }

  @Activate
  protected void activate(ComponentContext componentContext, final Config config) {
    bundleContext = componentContext.getBundleContext();
    connectionTimeout = config.connectionTimeout();
  }

  @Deactivate
  protected void deactivate() {
    bundleContext = null;
  }

  @ObjectClassDefinition(name = "News Replication Event Handler Configuration")
  public @interface Config {
    @AttributeDefinition(name = "Connection Timeout", description = "Please connection timeout for S&P Indexing Remote trigger")
    int connectionTimeout();
  }

  /**
   * this method checks if the run-mode of the instance
   * and returns true if it is author
   * @return
   */
  private boolean isAuthor() {
    LOGGER.info("Start of isAuthor method of NewsReplicationEventHandler");
    if (slingSettingsService != null) {
      Set<String> runModes = slingSettingsService.getRunModes();
      for (String runMode : runModes) {
        if (runMode.equalsIgnoreCase("author")) {
          LOGGER.info("End of isAuthor method of NewsReplicationEventHandler");
          return true;
        }
      }
    }
    LOGGER.info("End of isAuthor method of NewsReplicationEventHandler");
    return false;
  }

}