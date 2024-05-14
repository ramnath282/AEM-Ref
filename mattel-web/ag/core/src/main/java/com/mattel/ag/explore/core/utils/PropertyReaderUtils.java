package com.mattel.ag.explore.core.utils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CTS. Service for properties configuration of Explore.
 */
@Component(service = PropertyReaderUtils.class, immediate = true)
@Designate(ocd = PropertyReaderUtils.Config.class)
public class PropertyReaderUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyReaderUtils.class);
	private String explorePagePath;
	private String articleImagePath;
	private String facebookUrl;
	private String twitterUrl;
	private String pinterestUrl;
	
	public void setArticleImagePath(String articleImagePath) {
		this.articleImagePath = articleImagePath;
	}

	public void setExplorePagePath(String explorePagePath) {
		this.explorePagePath = explorePagePath;
	}
	

	@Activate
	public void activate(final Config config) {
		explorePagePath = config.explorePagePath();
		articleImagePath = config.articleImagePath();
		facebookUrl = config.facebookUrl();
		twitterUrl = config.twitterUrl();
		pinterestUrl = config.pinterestUrl();
	}

	@ObjectClassDefinition(name = "PropertyReaderUtils Properties Configuration")
	public @interface Config {
		@AttributeDefinition(name = "Content Path for Articles", description = "Please provide the content path for article Pages")
		String explorePagePath() default "/content/ag/en/explore";
		
		@AttributeDefinition(name = "Image Path for Articles", description = "Please provide the Image path for article Pages")
		String articleImagePath() default "/content/dam/sightlyMF/e84fe0c6d77c6b12e040cdd76f4d139bx.jpg";
	
		@AttributeDefinition(name = "Facebook URL", description = "Please enter facebook URL domain")
		String facebookUrl () default "https://www.facebook.com";
		@AttributeDefinition(name = "Twitter URL", description = "Please enter twitter URL domain")
		String twitterUrl () default "https://twitter.com";
		@AttributeDefinition(name = "Pinterest URL", description = "Please enter pinterest URL domain")
		String pinterestUrl () default "http://www.pinterest.com";
	}

	/**
	 * @return ExplorePagePath
	 */

	public String getExplorePagePath() {
		LOGGER.debug("Explore Page Path configured is{}", explorePagePath);
		return explorePagePath;
	}
	/**
	 * @return ArticleImagePath
	 */
	public String getArticleImagePath() {
		LOGGER.debug("Article Image Path configured is{}", articleImagePath);
		return articleImagePath;
	}

  public String getFacebookUrl() {
    return facebookUrl;
  }

  public String getPinterestUrl() {
    return pinterestUrl;
  }

  public String getTwitterUrl() {
    return twitterUrl;
  }
}
