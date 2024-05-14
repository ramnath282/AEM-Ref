package com.mattel.ecomm.core.models;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.pojos.TopResultsPojo;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SearchModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchModel.class);
	@Inject
	@Via("resource")
	List<Node> topResultsList;
	@Inject
	Resource resource;
	@Inject
	@Via("resource")
	private String viewMoreLink;
	@Inject
	PropertyReaderService propertyReaderService;
	List<TopResultsPojo> topResultList = new LinkedList<>();
	boolean isNotAEM = false;
	String snpAccountUrl = StringUtils.EMPTY;

	/**
	 * The init method
	 */
	@PostConstruct
	protected void init() {
		long startTime = System.currentTimeMillis();
		LOGGER.debug("MyAccountLeftNav Init Start");
		if (topResultsList != null) {
			try {
				topResultList.clear();
				topResultList = fetchLinkDetails();
				viewMoreLink = StringUtils.isNotBlank(viewMoreLink) ? EcommHelper.checkLink(viewMoreLink, resource)
						: "";
				checkCurrentPagePath();
			} catch (RepositoryException e) {
				LOGGER.error("Repository Exception Occoured {}", e);
			}
		}

		long endTime = System.currentTimeMillis();
		LOGGER.debug(Constant.EXECUTION_TIME_LOG, "init", endTime - startTime);
	}

	private void checkCurrentPagePath() {
		String currentPath = resource.getPath();
		if (!currentPath.startsWith("/content")) {
			isNotAEM = true;
			final InheritanceValueMap inheritanceValueMap = new HierarchyNodeInheritanceValueMap(resource);
			String siteKey = inheritanceValueMap.getInherited("siteKey", String.class);
			if (StringUtils.isNotEmpty(siteKey)) {
				snpAccountUrl = propertyReaderService.getSnpAccountURL(siteKey);
			}
		}
	}

	private List<TopResultsPojo> fetchLinkDetails() throws RepositoryException {
		for (Node node : topResultsList) {
			TopResultsPojo topResult = new TopResultsPojo();
			topResult.setLabel(EcommHelper.checkForProperty(node, "topResultLabel"));
			topResult.setCategoryId(EcommHelper.checkForProperty(node, "labelCategoryId"));
			topResultList.add(topResult);
		}
		return topResultList;
	}

	public List<TopResultsPojo> getTopResultList() {
		return topResultList;
	}

	public String getViewMoreLink() {
		return viewMoreLink;
	}

	public void setTopResultsList(List<Node> topResultsList) {
		this.topResultsList = topResultsList;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void setTopResultList(List<TopResultsPojo> topResultList) {
		this.topResultList = topResultList;
	}

	public boolean isNotAEM() {
		return isNotAEM;
	}

	public String getSnpAccountUrl() {
		return snpAccountUrl;
	}
}
