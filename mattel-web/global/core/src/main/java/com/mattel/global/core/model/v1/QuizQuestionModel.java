package com.mattel.global.core.model.v1;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.tagging.TagManager;
import com.mattel.global.core.constants.Constant;
import com.mattel.global.core.pojo.TagPojo;
import com.mattel.global.core.utils.GlobalUtils;

@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class QuizQuestionModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(QuizQuestionModel.class);
	
	@Inject
	private String title;
	
	@Inject
	private String subTitle;
	
	@Inject
	private String description;
	
	@Inject
	private String [] selectedTags;
	
	@Inject
	private String answerSelectionType;
	
	@SlingObject
	private ResourceResolver resourceResolver;

	private List<TagPojo> tagDetails;	
	
	@Self
	private Resource resource;

	private long totalCount;

	private int count;

	private String bannerText;
	private String bannerImage;
	private String backwardButtonLabel;
	private String trackPreviousCTA;
	private String previousCTATrackingText;
	private String forwardButtonLabel;
	private String trackNextCTA;
	private String nextCTATrackingText;
	private String tagParentDetails;
	private String submitButtonLabel;
	private String trackSubmitCTA;
	private String submitCTATrackingText;
	
	@PostConstruct
	void init() {
		TagManager tagManager;
		ValueMap  parentValueMap;
		int i = 0;
		LOGGER.info("Question Question init start");
		if(Objects.nonNull(selectedTags)) {
			tagManager = resourceResolver.adaptTo(TagManager.class);
			tagDetails	= Arrays.stream(selectedTags).map(tag -> GlobalUtils.getTagDetails(tagManager,tag)).collect(Collectors.toList());
			if(! tagDetails.isEmpty() && Objects.nonNull(tagDetails.get(0).getParent())) {
				tagParentDetails = tagDetails.get(0).getParent();
				LOGGER.debug("Tag parent : {}", tagParentDetails);
			}
		}	
		Resource parentResource = resource.getParent().getParent();
		if(Objects.nonNull(parentResource) && StringUtils.contains(parentResource.getResourceType(),Constant.QUIZ_CONTAINER)){
			parentValueMap = GlobalUtils.getParentDetails(parentResource);
			if(Objects.nonNull(parentValueMap)) {
				bannerText = parentValueMap.get("bannerText", String.class);
				bannerImage = parentValueMap.get("bannerImage", String.class);
				backwardButtonLabel = parentValueMap.get("backwardButtonLabel", String.class);
				trackPreviousCTA = parentValueMap.get("trackPreviousCTA", String.class);
				previousCTATrackingText = parentValueMap.get("previousCTATrackingText", String.class);
				forwardButtonLabel = parentValueMap.get("forwardButtonLabel", String.class);
				trackNextCTA = parentValueMap.get("trackNextCTA", String.class);
				nextCTATrackingText = parentValueMap.get("nextCTATrackingText", String.class);
				submitButtonLabel =  parentValueMap.get("submitButtonLabel", String.class);
				trackSubmitCTA =  parentValueMap.get("trackSubmitCTA", String.class);
				submitCTATrackingText = parentValueMap.get("submitCTATrackingText", String.class);
				LOGGER.debug("bannerText : {}" ,bannerText);
				LOGGER.debug("bannerImage : {}" ,bannerImage);
				LOGGER.debug("backwardButtonLabel : {}" ,backwardButtonLabel);
				LOGGER.debug("trackPreviousCTA : {}" ,trackPreviousCTA);
				LOGGER.debug("previousCTATrackingText : {}" ,previousCTATrackingText);
				LOGGER.debug("forwardButtonLabel : {}" ,forwardButtonLabel);
				LOGGER.debug("trackNextCTA: {}" ,trackNextCTA);
				LOGGER.debug("nextCTATrackingText : {}" ,nextCTATrackingText);
				LOGGER.debug("submitButtonLabel: {}" ,submitButtonLabel);
				LOGGER.debug("trackSubmitCTA: {}" ,trackSubmitCTA);
				LOGGER.debug("submitCTATrackingText: {}" ,submitCTATrackingText);
				LOGGER.info("Question Question init End");
			}
		}
		Resource parent = resource.getParent();
	 if(Objects.nonNull(parent)) {
	    totalCount = StreamSupport.stream(parent.getChildren().spliterator(), false).count();
	    Iterator<Resource> iterator = parent.getChildren().iterator();
	    while(iterator.hasNext()) {
		   i++;
		   if(StringUtils.equals(iterator.next().getName(),resource.getName())){
			  count = i;
		   }		
		}
	  }
	}

	public String getTitle() {
		return title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public String getDescription() {
		return description;
	}

	public List<TagPojo> getTagDetails() {
		return tagDetails;
	}

	public String getBannerText() {
		return bannerText;
	}

	public String getBannerImage() {
		return bannerImage;
	}

	public String getBackwardButtonLabel() {
		return backwardButtonLabel;
	}

	public String getTrackPreviousCTA() {
		return trackPreviousCTA;
	}

	public String getPreviousCTATrackingText() {
		return previousCTATrackingText;
	}

	public String getForwardButtonLabel() {
		return forwardButtonLabel;
	}

	public String getTrackNextCTA() {
		return trackNextCTA;
	}

	public String getNextCTATrackingText() {
		return nextCTATrackingText;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public int getCount() {
		return count;
	}	
	
	public String getAnswerSelectionType() {
		return answerSelectionType;
	}

	public String getTagParentDetails() {
		return tagParentDetails;
	}

	public String getSubmitButtonLabel() {
		return submitButtonLabel;
	}

	public String getTrackSubmitCTA() {
		return trackSubmitCTA;
	}

	public String getSubmitCTATrackingText() {
		return submitCTATrackingText;
	}
		
}
