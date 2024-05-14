package com.mattel.ecomm.core.models;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.utils.EcomUtil;

@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MiniCartAddonsModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(MiniCartAddonsModel.class);

	@Inject
	private String shoppingButtonlink;

	@Self
	private Resource resource;

	public String getShoppingButtonlink() {
		checkCTAType();
		LOGGER.debug("MiniCartAddonsModel  shoppingButtonlink URL{}", shoppingButtonlink);
		return shoppingButtonlink;
	}

	private void checkCTAType() {

		LOGGER.info("MiniCartAddonsModel -checkCTAType- Start");
		if (StringUtils.isNotBlank(shoppingButtonlink)) {
			shoppingButtonlink = EcomUtil.checkLink(shoppingButtonlink, resource);
		}
		LOGGER.info("MiniCartAddonsModel -checkCTAType- End");
	}

}
