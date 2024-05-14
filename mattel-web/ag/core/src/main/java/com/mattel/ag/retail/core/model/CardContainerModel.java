package com.mattel.ag.retail.core.model;

import com.mattel.ag.retail.core.services.CardReaderService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.*;

/**
 * @author CTS.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CardContainerModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(CardContainerModel.class);

	@Self
	private Resource resource;

	@Inject
	private CardReaderService cardReaderService;

	@Inject
	private Integer dateOffset;

	@Inject
	private String additionalInformation;

	private String additionalInformationWithDate;

	private List<String> resourcePaths = new ArrayList<>();

	@PostConstruct
	protected void init() {
		LOGGER.info("Card Container Model Starts");
		LOGGER.debug("Resource path is {}", resource.getPath());
		resourcePaths = cardReaderService.resourcePath(resource.getPath());
		LOGGER.debug("Offset date is {}", dateOffset);
		if (null != dateOffset) {

			additionalInformationWithDate = additionalInformation.replace("RESERVATION_DATE", setOffset());
		} else {
			additionalInformationWithDate = additionalInformation;
		}

	}
	private String setOffset() {
		LOGGER.info("Start of Set offset Method");
		try (Formatter formatter = new Formatter()){
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, dateOffset);
			String month = formatter.format("%tB", calendar).toString();
			String dateReturned = month + " " + calendar.get(Calendar.DATE) + ", " + calendar.get(Calendar.YEAR);
			LOGGER.debug("Date Returned to be displayed {}" , dateReturned);
			LOGGER.info("End of Set offset Method");
			return dateReturned ;
		}



	}

	public List<String> getResourcePaths() {
		return resourcePaths;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void setCardReaderService(CardReaderService cardReaderService) {
		this.cardReaderService = cardReaderService;
	}

	public String getAdditionalInformationWithDate() {
		return additionalInformationWithDate;
	}
}
