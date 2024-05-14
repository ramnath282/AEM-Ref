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

/**
 * @author CTS
 *
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CTAGroupModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(CTAGroupModel.class);

	@ChildResource
	private Resource ctaGroup;

	@Inject
	private boolean dropShadow;

	@Self
	private Resource resource;
	
	private List<ButtonDetails> buttonDetailslist;

	/**
	 * init method.
	 */
	@PostConstruct
	protected void init() {
		LOGGER.debug("Init Method - Start");		
		if (Optional.ofNullable(ctaGroup).isPresent()) {		
			buttonDetailslist = GlobalUtils.buttonDetails(ctaGroup, resource);
		}
		LOGGER.debug("Init Method - End");
	}	
	
	public boolean isDropShadow() {
		return dropShadow;
	}

	public List<ButtonDetails> getButtonDetailslist() {
		return buttonDetailslist;
	}
	
}