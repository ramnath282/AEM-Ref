package com.mattel.ecomm.core.models;
import java.util.List;

import javax.annotation.PostConstruct;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.wcm.core.components.models.Carousel;
import com.adobe.cq.wcm.core.components.models.ListItem;


@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ImageFacetContainerModel {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageFacetContainerModel.class);

	@Self
	@Via(type = ResourceSuperType.class)
	private Carousel carousel;

	@PostConstruct
	protected void init() {
		LOGGER.debug("Image Facet container Init Method - Start");
		
	}
	
	public List<ListItem> getItems() {
		return carousel.getItems();
	}

}
