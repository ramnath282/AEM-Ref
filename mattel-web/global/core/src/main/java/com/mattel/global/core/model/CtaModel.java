package com.mattel.global.core.model;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import com.mattel.global.core.utils.PathUtils;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CtaModel {

	@Inject
	private String ctaUrl;
	
	boolean linkStatus;

	public boolean isLinkStatus() {
		linkStatus = PathUtils.isExternal(ctaUrl);
		return linkStatus;
	}
	
}
