package com.mattel.global.core.utils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

/**
 * @author CTS. Service for video player configuration.
 */
@Component(service = VideoPlayerConfigurationUtils.class)
@Designate(ocd = VideoPlayerConfigurationUtils.Config.class)
public class VideoPlayerConfigurationUtils {
  private String playerType;
  private String playerHostName;

  @Activate
  public void activate(final Config config) {
	playerType = config.playerType();
	playerHostName = config.playerHostName();
  }
  
  @ObjectClassDefinition(name = "Mattel Global Video Player Configurations")
  public @interface Config {
    @AttributeDefinition(name = "Video Player Type", 
    		description = "Please select Video Player Type",
    		options = {
    				@Option(label = "Deluxe", value = "deluxe"), @Option(label = "Brightcove", value = "brightcove") })
    String playerType();
    
    @AttributeDefinition(name = "Player Host Name", description = "Please enter player host name")
	String playerHostName();
  }

 public String getPlayerType() {
	return playerType;
 }


  public String getPlayerHostName() {
	return playerHostName;
  }

  
}
