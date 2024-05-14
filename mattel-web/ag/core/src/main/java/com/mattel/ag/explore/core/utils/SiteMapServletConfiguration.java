package com.mattel.ag.explore.core.utils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * @author CTS. Service for properties configuration of SiteMap.
 */
@Component(service = SiteMapServletConfiguration.class, immediate = true)
@Designate(ocd = SiteMapServletConfiguration.Config.class)
public class SiteMapServletConfiguration {
	private String myChangeFreqProperties;

	private String myPriorityProperties;
	private String rootPath;
	private String domain;

	@Activate
	public void activate(final Config config) {
		myChangeFreqProperties = config.myChangeFreqProperties();
		myPriorityProperties = config.myPriorityProperties();
		rootPath = config.rootPath();
		domain = config.domain();
	}

	@ObjectClassDefinition(name = "SiteMapServlet Properties Configuration")
	public @interface Config {
		@AttributeDefinition(name = "Change Frequency Properties", description = "The set of JCR property names which will contain the change frequency value.")
		String myChangeFreqProperties() default "daily";

		@AttributeDefinition(name = "Priority Properties", description = "The set of JCR property names which will contain the priority value.")
		String myPriorityProperties() default "1.0";

		@AttributeDefinition(name = "Root Path", description = "Root Path of the Website")
		String rootPath() default "/content/ag/en";
		
		@AttributeDefinition(name = "Domain", description = "Domain of the Website")
		String domain() default "https://www.americangirl.com";

	}

	public String getMyChangeFreqProperties() {
		return myChangeFreqProperties;
	}

	public String getMyPriorityProperties() {
		return myPriorityProperties;
	}

	public String getRootPath() {
		return rootPath;
	}

	public String getDomain() {
		return domain;
	}

	
	public void setMyChangeFreqProperties(String myChangeFreqProperties) {
		this.myChangeFreqProperties = myChangeFreqProperties;
	}

	public void setMyPriorityProperties(String myPriorityProperties) {
		this.myPriorityProperties = myPriorityProperties;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	

}
