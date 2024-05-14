package com.mattel.ecomm.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.pojos.LeftNavLinkPojo;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MyAccountLeftNavModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyAccountLeftNavModel.class);
	@SlingObject
	private SlingHttpServletRequest request;
	List<LeftNavLinkPojo> accountDetailList;
	List<LeftNavLinkPojo> orderDetailList;
	private String pagetitleprop = "pageTitle";
	private String pagelinkprop = "pageLink";
	@Inject
	@Via("resource")
	private Node accountDetailsList;
	@Inject
	@Via("resource")
	private Node orderDetailsList;
	@Inject
	private MultifieldReader multifieldReader;
	@Inject
	Resource resource;

	/**
	 * The init method
	 */
	@PostConstruct
	protected void init() {
		long startTime = System.currentTimeMillis();
		String initMethodName = "init";
		LOGGER.debug("MyAccountLeftNav Init Start");
		String currentPageLink = request.getPathInfo();

		if (accountDetailsList != null) {
			accountDetailList = fetchLinkDetails(accountDetailsList, currentPageLink);
		}
		if (orderDetailsList != null) {
			orderDetailList = fetchLinkDetails(orderDetailsList, currentPageLink);
		}
		long endTime = System.currentTimeMillis();
		LOGGER.debug(Constant.EXECUTION_TIME_LOG, initMethodName, endTime - startTime);
	}

	/**
	 * Method to handle the multifield with links
	 * 
	 * @param linkDetailNode
	 * @return
	 */
	private List<LeftNavLinkPojo> fetchLinkDetails(Node linkDetailNode, String currentPageLink) {
		List<LeftNavLinkPojo> tempLinkList = new ArrayList<>();
		Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(linkDetailNode);

		for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
			LeftNavLinkPojo leftNavLink = new LeftNavLinkPojo();
			String pageName = entry.getValue().get(pagetitleprop, String.class);
			String pageLink = entry.getValue().get(pagelinkprop, String.class);
			if (null != pageLink && pageLink.startsWith("/content")) {
				leftNavLink.setCurrentPage(currentPageLink.contains(pageLink));
				fetchPageProperties(pageLink,leftNavLink);
			}
			if (pageName != null) {
				leftNavLink.setPageName(pageName);
			}
			tempLinkList.add(leftNavLink);
		}
		return tempLinkList;
	}

	/**
	 * Method to handle the Page name if no title was authored
	 * 
	 * @param pageLink
	 * @return
	 */
	private LeftNavLinkPojo fetchPageProperties(String pageLink,LeftNavLinkPojo leftNavLink) {
		ResourceResolver resourceResolver = request.getResourceResolver();
		Resource currentResource = resourceResolver.getResource(pageLink);
		if (currentResource != null) {
			PageManager pageManager = currentResource.getResourceResolver().adaptTo(PageManager.class);
			if (pageManager != null) {
				Page currentPage = pageManager.getPage(pageLink);
				String navigationTitle = currentPage.getNavigationTitle();
				if (navigationTitle == null) {
					leftNavLink.setPageName(currentPage.getTitle());
				}
			}
		}
		pageLink = checkLink(pageLink, resource);
		leftNavLink.setPageLink(pageLink);
		return leftNavLink;
	}

	/**
	 * Method to handle the Internal and External Links
	 * 
	 * @param pageLink
	 * @param resource
	 * @return
	 */
	private String checkLink(String pageLink, Resource resource) {
		ResourceResolver resolver = null;
		if (null != resource) {
			resolver = resource.getResourceResolver();
			pageLink = resolver.map(pageLink);
			if (pageLink.startsWith("/content")) {
				pageLink = pageLink + ".html";
			}
		}
		return pageLink;
	}

	public List<LeftNavLinkPojo> getAccountDetailList() {
		return accountDetailList;
	}

	public List<LeftNavLinkPojo> getOrderDetailList() {
		return orderDetailList;
	}

	public void setMultifieldReader(MultifieldReader multifieldReader) {
		this.multifieldReader = multifieldReader;
	}

	public void setAccountDetailsList(Node accountDetailsList) {
		this.accountDetailsList = accountDetailsList;
	}

	public void setOrderDetailsList(Node orderDetailsList) {
		this.orderDetailsList = orderDetailsList;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void setAccountDetailList(List<LeftNavLinkPojo> accountDetailList) {
		this.accountDetailList = accountDetailList;
	}

	public void setOrderDetailList(List<LeftNavLinkPojo> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public void setRequest(SlingHttpServletRequest request) {
		this.request = request;
	}

}
