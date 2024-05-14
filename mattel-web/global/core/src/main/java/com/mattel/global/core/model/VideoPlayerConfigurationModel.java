package com.mattel.global.core.model;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.utils.VideoPlayerConfigurationUtils;

@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class VideoPlayerConfigurationModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(VideoPlayerConfigurationModel.class);
	
	private String playerType;
	
	private String playerHostName;
	
	@OSGiService
	private VideoPlayerConfigurationUtils videoPlayerConfigurationUtils;
	

	@PostConstruct
	protected void init() {
		LOGGER.info("VideoPlayerConfigurationModel Init Method Start");
		playerType = videoPlayerConfigurationUtils.getPlayerType();
		playerHostName = videoPlayerConfigurationUtils.getPlayerHostName();
		LOGGER.debug("Configured playerType is {} and playerHostName is {}",playerType,playerHostName);
		LOGGER.info("VideoPlayerConfigurationModel Init Method End");
	}

	public String getPlayerType() {
		return playerType;
	}

	public String getPlayerHostName() {
		return playerHostName;
	}

}
