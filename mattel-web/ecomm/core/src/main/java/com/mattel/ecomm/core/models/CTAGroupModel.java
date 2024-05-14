package com.mattel.ecomm.core.models;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.pojos.CTAPojo;
import com.mattel.ecomm.core.services.MultifieldReader;

/**
 * @author CTS
 *
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CTAGroupModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(CTAGroupModel.class);

	@Inject
	private Node ctaGroup;

	@Inject
	private boolean dropShadow;

	@Self
	private Resource resource;

	@Inject
	private MultifieldReader multifieldReader;

	private List<CTAPojo> ctaGroupDetails;

	/**
	 * init method.
	 */
	@PostConstruct
	protected void init() {
		LOGGER.debug("Init Method - Start");
		if (ctaGroup != null) {
			ctaGroupDetails = BuildCtaModel.listCTA(multifieldReader, ctaGroup, resource, "ctaLabel", "ctaLink");
		}
		LOGGER.debug("Init Method - End");
	}

	public void setCtaGroupDetails(List<CTAPojo> ctaGroupDetails) {
		this.ctaGroupDetails = ctaGroupDetails;
	}

	public List<CTAPojo> getCtaGroupDetails() {
		return ctaGroupDetails;
	}

	public boolean isDropShadow() {
		return dropShadow;
	}

	public void setCtaGroup(Node ctaGroup) {
		this.ctaGroup = ctaGroup;
	}

	public void setMultifieldReader(MultifieldReader multifieldReader) {
		this.multifieldReader = multifieldReader;
	}
	
}