package com.mattel.ag.retail.core.services;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author CTS.
 * Service for fetching Google API Key.
 *
 */
@Component(service = GoogleApiConfiguration.class, immediate = true)
@Designate (ocd = GoogleApiConfiguration.Config.class)
public class GoogleApiConfiguration {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleApiConfiguration.class);

	private String googleApiKey;

	@ObjectClassDefinition(name = "Google API Configuration")
	public @interface Config {

		@AttributeDefinition(name ="Google API Key", description = "Please enter the google API key for Google MAP Configuration")
		String googleAPIKey() default "AIzaSyAigpY4Hxx7nmzjYTCN451dF8CRzKS6X5E" ;

	}

	@Activate
	public void activate(final Config config) {
		googleApiKey = config.googleAPIKey();
	}

	/**
	 * 
	 * @return google API key.
	 */
	public String getGoogleApiKey() {
		LOGGER.info("Get API Key method called");
		return googleApiKey;
	}

	public void setGoogleApiKey(String googleApiKey) {
		this.googleApiKey = googleApiKey;
	}

}
