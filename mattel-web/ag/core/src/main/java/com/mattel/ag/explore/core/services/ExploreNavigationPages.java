package com.mattel.ag.explore.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.mattel.ag.explore.core.pojos.ExploreNavigationPojo;

/**
 * @author CTS. Service for getting all the card properties..
 */

@Component(service = ExploreNavigationPages.class, immediate = true)
@Designate(ocd = ExploreNavigationPages.Config.class)
public class ExploreNavigationPages {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExploreNavigationPages.class);

	@Reference
	ResourceResolverFactory resolverFactory;

	@Reference
	QueryBuilder queryBuilder;

	public ResourceResolverFactory getResolverFactory() {
		return resolverFactory;
	}

	public void setResolverFactory(ResourceResolverFactory resolverFactory) {
		this.resolverFactory = resolverFactory;
	}

	public QueryBuilder getQueryBuilder() {
		return queryBuilder;
	}

	public void setQueryBuilder(QueryBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}

	private String templatePath;

	public List<ExploreNavigationPojo> getpages(String pagePath) {
		LOGGER.info("Start of get pages method in ExploreNavigation");
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
		ResourceResolver resolver = null;
		Session session = null;
		List<ExploreNavigationPojo> exploreNavigationPojos = new ArrayList<>();
		try {
			if (resolverFactory != null) {
				LOGGER.debug("resolver factory is not null");
				resolver = resolverFactory.getServiceResourceResolver(map);
				LOGGER.debug("Resolver is not null");
				session = resolver.adaptTo(Session.class);
				Map<String, String> querymap = new HashMap<>();
				querymap.put("path", pagePath);
				querymap.put("type", "cq:Page");
				querymap.put("orderby", "@jcr:content/cq:lastModified");
				querymap.put("orderby.sort", "desc");
				querymap.put("property", "jcr:content/cq:template");
				querymap.put("property.value", templatePath);
				querymap.put("p.limit", "-1");
				LOGGER.debug("Query map is {}", querymap);
				Query query = queryBuilder.createQuery(PredicateGroup.create(querymap), session);
				SearchResult result = query.getResult();
				if (null != result) {
					LOGGER.debug("Search result is not null {}", result.getHitsPerPage());
					for (Hit hit : result.getHits()) {
						String pagePaths = null;
						ExploreNavigationPojo exploreNavigationPojo = new ExploreNavigationPojo();
						exploreNavigationPojo.setPageTitle(hit.getProperties().get("jcr:title", String.class));
						LOGGER.debug("hit.getPath() {}", hit.getPath());
						if (null == hit.getProperties().get("sling:vanityPath", String.class)) {
							pagePaths = hit.getPath();
							exploreNavigationPojo.setPageUrl(resolver.map(pagePaths + ".html"));
						} else {
							pagePaths = hit.getProperties().get("sling:vanityPath", String.class);
							exploreNavigationPojo.setPageUrl(pagePaths);
						}
						LOGGER.debug("pagePaths {}", pagePaths);
						exploreNavigationPojos.add(exploreNavigationPojo);
					}
					closeResourceresolver(result);
				}
			}
		} catch (LoginException | RepositoryException e) {
			LOGGER.error("Exception caused in Child page properties Service", e);
		} finally {
			if (resolver != null && resolver.isLive()) {
				resolver.close();
			}
		}
		LOGGER.info("End of get pages method");
		return exploreNavigationPojos;
	}

	private void closeResourceresolver(SearchResult result) {
		Iterator<Resource> resources = result.getResources();
		if (resources.hasNext()) {
			resources.next().getResourceResolver().close();
		}
	}

	@ObjectClassDefinition(name = "Explorer Navigation properties")
	public @interface Config {
		@AttributeDefinition(name = "Template Name", description = "Please provide the name of the Template for the selection of the Landing Pages")
		String rootPath() default "/conf/ag/settings/wcm/templates/article-template-landing-page";
	}

	@Activate
	public void activate(final Config config) {
		templatePath = config.rootPath();
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
}
