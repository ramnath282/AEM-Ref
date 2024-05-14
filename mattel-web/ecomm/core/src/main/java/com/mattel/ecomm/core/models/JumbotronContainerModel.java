package com.mattel.ecomm.core.models;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.wcm.core.components.models.Carousel;
import com.adobe.cq.wcm.core.components.models.ListItem;
import com.mattel.ecomm.core.pojos.CTAPojo;
import com.mattel.ecomm.core.services.MultifieldReader;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class JumbotronContainerModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(JumbotronContainerModel.class);
	private static final String JUMBOTRON_ID = "jumbotron-id";
	private static final String RESPONSIVE_GRID = "wcm/foundation/components/responsivegrid";
	private static final String JUMBOTRON_RESOURCE_TYPE = "mattel/ecomm/shared/components/content/jumbotronContainerComponent";

	@Self
	private SlingHttpServletRequest request;

	@Inject
	@Via("resource")
	private String title;

	@Inject
	@Via("resource")
	private Node linkList;

	@Self
	@Via("resource")
	private Resource resource;

	@Inject
	private MultifieldReader multifieldReader;

	private List<CTAPojo> ctaGroupDetails;

	@Self
	@Via(type = ResourceSuperType.class)
	private Carousel carousel;

	private String jumbotronId;
	private List<String> jumbotronTitleList;
	private int count;
	private int jumbotronContainerSize = 0;

	@PostConstruct
	public void postConsctruct() {
		LOGGER.info("JumbotronContainerModel:::> Start of postConsctruct");

		if (linkList != null) {
			ctaGroupDetails = BuildCtaModel.listCTA(multifieldReader, linkList, resource, "text", "link");
		}
		jumbotronTitleList = new LinkedList<>();
		Resource currentResource = request.getResource();
		
		  while (Objects.nonNull(currentResource) && !currentResource.getResourceType().equals(RESPONSIVE_GRID)) {
			currentResource = currentResource.getParent();
		  }
		  if(Objects.nonNull(currentResource)){
		    Iterator<Resource> itr = currentResource.getChildren().iterator();
		    while (itr.hasNext()) {
			    Resource res = itr.next();
			    if (res.getResourceType().equals(JUMBOTRON_RESOURCE_TYPE)) {
				    jumbotronContainerSize++;
				    ValueMap prop = res.getValueMap();
				    Object titleObj = prop.get("title");
				    jumbotronTitleList.add(titleObj != null ? titleObj.toString() : res.getName());
			    }
		    }
		}
		// Unique ID Generation for JumbotronComopnents available on page using
		// SlingHttpServletRequest
		jumbotronId = (String) request.getAttribute(JUMBOTRON_ID);
		LOGGER.debug("jumbotronId:::> {}" , jumbotronId);
		if (StringUtils.isNotBlank(jumbotronId)) {

			int jumbotronIdCount = Integer.parseInt(jumbotronId);
			++jumbotronIdCount;
			this.count = jumbotronIdCount;
			this.jumbotronId = Integer.toString(jumbotronIdCount);
			request.setAttribute(JUMBOTRON_ID, this.jumbotronId);
		} else {
			request.setAttribute(JUMBOTRON_ID, Integer.toString(0));
			this.count = 0;
			this.jumbotronId = "0";
		}
		LOGGER.debug("JumbotronContainerModel ::jumbotronId {}" , jumbotronId);
		LOGGER.debug("JumbotronContainerModel ::this.count {}" , this.count);
		LOGGER.debug("JumbotronContainerModel ::count {}" , count);
		LOGGER.debug("JumbotronContainerModel ::jumbotronContainerSize {}" , jumbotronId);
		LOGGER.info("JumbotronContainerModel:::> End of postConsctruct");
	}

	public void setCtaGroupDetails(List<CTAPojo> ctaGroupDetails) {
		this.ctaGroupDetails = ctaGroupDetails;
	}

	public List<CTAPojo> getCtaGroupDetails() {
		return ctaGroupDetails;
	}

	public String getJumbotronId() {
		return jumbotronId;
	}

	public int getCount() {
		return count;
	}

	public String getTitle() {
		return title;
	}

	public List<String> getJumbotronTitleList() {
		return jumbotronTitleList;
	}

	public List<ListItem> getItems() {
		return carousel.getItems();
	}

	public int getJumbotronContainerSize() {
		return jumbotronContainerSize - 1;
	}

}
