package com.mattel.global.core.model;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;

import com.adobe.cq.wcm.core.components.models.Carousel;
import com.adobe.cq.wcm.core.components.models.ListItem;

import javax.inject.Inject;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CarouselContainerModel {

	@Self
	@Via(type = ResourceSuperType.class)
	private Carousel carousel;

	@Inject
	@Via("resource")
	private String title;

	@Inject
	@Via("resource")
	private String description;

	@Inject
	@Via("resource")
	private String textPosition;

	@Inject
	@Via("resource")
	private boolean centerMode;

	@Inject
	@Via("resource")
	private String slideToShow;

	@Inject
	@Via("resource")
	private String slidetoscroll;
	
	@Inject
	@Via("resource")
	private String slideRotationSpeed;

	@Inject
	@Via("resource")
	private boolean arrows;
	@Inject
	@Via("resource")
	private boolean dots;
	@Inject
	@Via("resource")
	private boolean outerArrows;

	@Inject
	@Via("resource")
	private boolean autoPlay;

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public boolean isCenterMode() {
		return centerMode;
	}

	public String getTextPosition() {
		return textPosition;
	}

	public String getSlideToShow() {
		return slideToShow;
	}

	public String getSlidetoscroll() {
		return slidetoscroll;
	}
	
	public String getSlideRotationSpeed() {
		return slideRotationSpeed;
	}

	public boolean isAutoPlay() {
		return autoPlay;
	}

	public boolean isArrows() {
		return arrows;
	}

	public boolean isDots() {
		return dots;
	}

	public boolean isOuterArrows() {
		return outerArrows;
	}

	public List<ListItem> getItems() {
		return carousel.getItems();
	}
}
