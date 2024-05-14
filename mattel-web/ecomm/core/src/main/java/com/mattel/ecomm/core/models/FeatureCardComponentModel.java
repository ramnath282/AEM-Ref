package com.mattel.ecomm.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.pojos.CTAPojo;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.core.utils.EcomUtil;

@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FeatureCardComponentModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(FeatureCardComponentModel.class);

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

	@Inject
	private Node buttonList;

	@Self
	private Resource resource;

	@Inject
	private MultifieldReader multifieldReader;

	private List<CTAPojo> ctaGroupDetails;

	@PostConstruct
	protected void init() {
		LOGGER.debug("Init Method Featured card - Start");
		if (buttonList != null) {
			ctaGroupDetails = listCTA();
		}
		LOGGER.debug("Init Method - End");
	}

    /**
     * Method for setting all the values in dialog field into Pojo.
     *
     * @return ArrayList.
     */
    private List<CTAPojo> listCTA() {
        Map<String, ValueMap> ctaGroupMap = multifieldReader.propertyReader(buttonList);
        List<CTAPojo> ctaList = new ArrayList<>();
        for (Map.Entry<String, ValueMap> entry : ctaGroupMap.entrySet()) {
            CTAPojo ctaPojo = new CTAPojo();
            ctaPojo.setCtaLabel(entry.getValue().get("text", String.class));
            ctaPojo.setCtaType(entry.getValue().get("ctaType", String.class));
            ctaPojo.setCtaStyle(entry.getValue().get("ctaStyle", String.class));
            String navLink = entry.getValue().get("link", String.class);
            if (StringUtils.isNotBlank(navLink)) {
                ctaPojo.setCtaLink(EcomUtil.checkLink(navLink, resource));
            }
            LOGGER.debug("Featured card CTA Pojo  {}", ctaPojo);
            ctaList.add(ctaPojo);
        }
        LOGGER.debug("Featured card listCTA - End  : CTA List{}", ctaList);
        return ctaList;
    }

	public void setCtaGroupDetails(List<CTAPojo> ctaGroupDetails) {
		this.ctaGroupDetails = ctaGroupDetails;
	}

	public List<CTAPojo> getCtaGroupDetails() {
		return ctaGroupDetails;
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

	public void setMultifieldReader(MultifieldReader multifieldReader) {
		this.multifieldReader = multifieldReader;
	}

}
