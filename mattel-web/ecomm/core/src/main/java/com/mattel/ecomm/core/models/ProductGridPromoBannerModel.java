package com.mattel.ecomm.core.models;

import java.util.Random;

import javax.annotation.PostConstruct;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.coreservices.core.constants.Constant;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductGridPromoBannerModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductGridPromoBannerModel.class);

	@Self
	Resource resource;

	@PostConstruct
	protected void init() {
		LOGGER.debug("ProductGridPromoBannerModel Init Start");
		long startTime = System.currentTimeMillis();
		final String METHOD_NAME = "init";
		try {
			if (null != resource) {
				Node currentNode = resource.adaptTo(Node.class);
				if (null != currentNode && !currentNode.hasProperty("bannerId")) {
					Random randomNum = new Random(1000);
					String bannerId = "banner"+ randomNum.nextInt();
					LOGGER.info("bannerId is {}",bannerId);
					currentNode.setProperty("bannerId", bannerId);
					currentNode.getSession().save();
				}

			}
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException Occured in init method {}",e);
		}
		long endTime = System.currentTimeMillis();
		LOGGER.debug(Constant.EXECUTION_TIME_LOG, METHOD_NAME, endTime - startTime);
		LOGGER.info("ProductGridPromoBannerModel Init end");

	}

}