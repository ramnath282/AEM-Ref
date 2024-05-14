package com.mattel.ecomm.core.services;

import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 710203
 *
 */
/**
 * @author 710203
 *
 */
@Component(service = GTEmailTnsConfigService.class)
@Designate(ocd = GTEmailTnsConfigService.Config.class)
public class GTEmailTnsConfigService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GTEmailTnsConfigService.class);
		
	    private String senderName;
	    private String noticeName;
	    private String organizationId;
	    private String originatingSystemCode;
	    private String environment;
	    private String hostName;
	    private String user;
	    private String source;
	    private String target;
	    private String endPointURL;
		
	    
		public String getEndPointURL() {
			return endPointURL;
		}

		public void setEndPointURL(String endPointURL) {
			this.endPointURL = endPointURL;
		}

		public String getSenderName() {
			return senderName;
		}

		public void setSenderName(String senderName) {
			this.senderName = senderName;
		}

		public String getNoticeName() {
			return noticeName;
		}

		public void setNoticeName(String noticeName) {
			this.noticeName = noticeName;
		}

		public String getOrganizationId() {
			return organizationId;
		}

		public void setOrganizationId(String organizationId) {
			this.organizationId = organizationId;
		}

		public String getOriginatingSystemCode() {
			return originatingSystemCode;
		}

		public void setOriginatingSystemCode(String originatingSystemCode) {
			this.originatingSystemCode = originatingSystemCode;
		}

		public String getEnvironment() {
			return environment;
		}

		public void setEnvironment(String environment) {
			this.environment = environment;
		}

		public String getHostName() {
			return hostName;
		}

		public void setHostName(String hostName) {
			this.hostName = hostName;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getTarget() {
			return target;
		}

		public void setTarget(String target) {
			this.target = target;
		}
		
		public JSONObject getJSONValues() throws JSONException {
		JSONObject 	configValues = new JSONObject();
		configValues.append("senderName", senderName);
		configValues.append("noticeName", noticeName);
		configValues.append("organizationId", organizationId);
		configValues.append("originatingSystemCode", originatingSystemCode);
		configValues.append("environment", environment);
		configValues.append("hostName", hostName);
		configValues.append("user", user);
		configValues.append("source", source);
		configValues.append("target", target);
		configValues.append("endPointURL", endPointURL);
		return configValues;
		}
		
		@Activate
	    public void activate(Config config){
			senderName = config.senderName();
		    noticeName = config.noticeName();
		    organizationId = config.organizationId();
		    originatingSystemCode = config.originatingSystemCode();
		    environment = config.environment();
		    hostName = config.hostName();
		    user = config.user();
		    source = config.originatingSystemCode();
		    target = config.target();
		    endPointURL = config.endPointURL();
			LOGGER.debug("End Point URL Configured: {},", endPointURL);
	    }
		
		@ObjectClassDefinition(name="TNS Email Configurations", description = "This service contains " +
	            "all TNS Email Related  OSGI Configuration")
	    public @interface Config {
	        @AttributeDefinition(name = "Sender Name" , description = "Please enter Email Sender Name")
	        String senderName() default "American Girl";
	        @AttributeDefinition(name = "TNS Notice Name" , description = "Please enter TNS Notice Name")
	        String noticeName() default "GIFT_TRUNK_RETAIL_ORDER";
	        @AttributeDefinition(name = "Organization Id" , description = "Please enter Organization ID")
	        String organizationId() default "301";
	        @AttributeDefinition(name = "Originating System Code" , description = "Please enter Originating System Code")
	        String originatingSystemCode() default "AEM";
	        @AttributeDefinition(name = "Environment" , description = "Please enter the environment")
	        String environment() default "PROD";
	        @AttributeDefinition(name = "TNS Host Name" , description = "Please enter TNS Host Name")
	        String hostName() default "esb-enterprise.corp.mattel.com:33021";
	        @AttributeDefinition(name = "Target" , description = "Please enter the Target Name")
	        String target() default "TNS";
	        @AttributeDefinition(name = "User" , description = "Please enter the User")
	        String user() default "APPS";
	        @AttributeDefinition(name = "End Point URL" , description = "Please enter the TNS endpoint URL")
	        String endPointURL() default "https://api.dev.mattel.com/Dev/createNoticeEvent?api_key=5aqfpyz6ym7gyw2kacfugeu7";

	    }	
	
}
