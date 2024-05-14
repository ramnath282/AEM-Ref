package com.mattel.ag.retail.core.services;

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

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.eval.JcrPropertyPredicateEvaluator;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.NameConstants;
import com.mattel.ag.retail.core.constants.Constants;
import com.mattel.ag.retail.core.pojos.StoreDetailsPojo;


/**
 * @author CTS.
 * Service for getting all the page properties.
 */

@Component(service = ChildPageProperties.class, immediate = true)
@Designate(ocd = ChildPageProperties.Config.class)
public class ChildPageProperties {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChildPageProperties.class);

    @Reference
    ResourceResolverFactory resolverFactory;

    @Reference
    QueryBuilder queryBuilder;

    private String rootPagePath;

    /**
     * Method for getting all the page properties inside the retail page.
     *
     * @return List of {@link StoreDetailsPojo}
     */

    public List<StoreDetailsPojo> getpages() {
        LOGGER.info("Start of get pages method");
        Map<String, Object> map = new HashMap<>();
        map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
        ResourceResolver resolver = null;
        Session session = null;
        List<StoreDetailsPojo> storeDetailsPojos = new ArrayList<>();
        try {
            if (resolverFactory != null) {
                LOGGER.debug("resolver factory is not null");
                resolver = resolverFactory.getServiceResourceResolver(map);

            }
            if (resolver != null) {
                LOGGER.debug("Resolver is not null");
                session = resolver.adaptTo(Session.class);
            }
            Map<String, String> querrymap = new HashMap<>();
            querrymap.put("path", rootPagePath);
            querrymap.put("1_property", NameConstants.NN_TEMPLATE);
            querrymap.put("1_property.value", Constants.STOREPAGETEMPLATEPATH);
            querrymap.put("1_property.operation", JcrPropertyPredicateEvaluator.OP_LIKE);
            querrymap.put("p.limit", "-1");
            LOGGER.debug("Query map is {}", querrymap);
            Query query = queryBuilder.createQuery(PredicateGroup.create(querrymap), session);
            SearchResult result = query.getResult();
            if (null != result) {
                LOGGER.debug("Search result is not null {}", result.getHitsPerPage());
                for (Hit hit : result.getHits()) {
                    StoreDetailsPojo storeDetailsPojo = new StoreDetailsPojo();
                    LOGGER.debug("Tilte {}", hit.getProperties().get("jcr:title", String.class));
                    storeDetailsPojo.setPageTitle(hit.getProperties().get("jcr:title", String.class));
                    if (null != hit.getProperties().get("storeLocation", String.class)) {
                        storeDetailsPojo.setLocation(hit.getProperties().get("storeLocation", String.class));
                    }
                    String pagePath = hit.getPath().replaceAll(Constants.SLASH + JcrConstants.JCR_CONTENT, ".html");
                    storeDetailsPojo.setPageUrl(resolver.map(pagePath));
                    if (null != hit.getProperties().get("isInternational", Boolean.class)) {
                        storeDetailsPojo.setInternational(hit.getProperties().get("isInternational", Boolean.class));
                    }
                    storeDetailsPojos.add(storeDetailsPojo);
                }
            }
            Iterator<Resource> resources = result.getResources();
			if (resources.hasNext()) {
				resources.next().getResourceResolver().close();
			}
        } catch (LoginException | RepositoryException e) {
            LOGGER.error("Exception caused in Child page properties Service", e);
        } finally {
            if (resolver!=null && resolver.isLive()) {
                resolver.close();
            }
        }
        LOGGER.debug("Store details Pojo {}", storeDetailsPojos);
        LOGGER.info("End of get pages method");
        return storeDetailsPojos;

    }

    @ObjectClassDefinition(name = "Child Page properties")
    public @interface Config {

        @AttributeDefinition(name = "Root path", description = "Please provide the rootpath of retail homepage. Default is /content/ag/retail-homepage")
        String rootPath() default "/content/ag/retail-homepage";
    }

    @Activate
    public void activate(final Config config) {

        rootPagePath = config.rootPath();
    }

	public void setRootPagePath(String rootPagePath) {
		this.rootPagePath = rootPagePath;
	}

	public void setResolverFactory(ResourceResolverFactory resolverFactory) {
		this.resolverFactory = resolverFactory;
	}

	public void setQueryBuilder(QueryBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}
	
}
