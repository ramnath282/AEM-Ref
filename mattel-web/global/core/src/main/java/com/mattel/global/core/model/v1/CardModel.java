package com.mattel.global.core.model.v1;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.model.ButtonDetails;
import com.mattel.global.core.utils.GlobalUtils;

@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CardModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(CardModel.class);

	@Inject
	private String title;
	
	@Inject
	private String titleTag;


	@Inject
	private String description;

	@Inject
	private String cardVariant;

	@Inject
	private String position;

	@Inject
	private String textAlign;

	@Inject
	private String smallImage;

	@Inject
	private String image;

	@Inject
	private String imgAltText;

	@ChildResource	
	private Resource buttonList;

	@Self
	private Resource resource;	
	
	private List<ButtonDetails> buttonDetailslist;

	@PostConstruct
	protected void init() {
		LOGGER.info("Card -> Init Method :: Start");
		if (Optional.ofNullable(buttonList).isPresent()) {		
			buttonDetailslist = GlobalUtils.buttonDetails(buttonList, resource);
		}
		LOGGER.info("Card -> Init Method - End");
	}	

	public String getPosition() {
		return position;
	}

	public String getTextAlign() {
		return textAlign;
	}

	public String getImage() {
		return image;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public String getImgAltText() {
		return imgAltText;
	}

	public String getCardVariant() {
		return cardVariant;
	}

	public String getTitle() {
		return title;
	}

	public String getTitleTag() {
		return titleTag;
	}
	
	public String getDescription() {
		if(description != null && !description.startsWith("<p>")){
			description="<p>"+description+"</p>";	
		}
		return description;	
	}


	public Resource getButtonList() {
		return buttonList;
	}

	public List<ButtonDetails> getButtonDetailslist() {
		return buttonDetailslist;
	}		

}
