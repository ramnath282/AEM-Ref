package com.mattel.ag.explore.core.model;

import com.mattel.ag.explore.core.utils.PathUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mattel.ag.explore.core.pojos.CharacterPojo;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author CTS.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CharacterModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(CharacterModel.class);

	@Inject
	private Resource resource;

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	private CharacterPojo characterPojo;

	public CharacterPojo getCharacterPojo() {
		return characterPojo;
	}

	@PostConstruct
	protected void init() {
		LOGGER.info("Start of in it method in Character Model");
		if (null != resource) {
			ValueMap valueMap = resource.getValueMap();
			characterPojo = new CharacterPojo();
			if (null != valueMap.get("imagelink", String.class)) {
				characterPojo.setImagelink(valueMap.get("imagelink", String.class));
			}
			if (null != valueMap.get("heading", String.class)) {
				String heading = valueMap.get("heading", String.class);
				if (null != heading) {
					characterPojo.setHeading(heading.replaceAll("\\<[^>]*>", ""));
				}
			}
			if (null != characterPojo.getImagelink()) {
				characterPojo.setExternal(PathUtils.isExternal(characterPojo.getImagelink()));
			}
		}

	}

}