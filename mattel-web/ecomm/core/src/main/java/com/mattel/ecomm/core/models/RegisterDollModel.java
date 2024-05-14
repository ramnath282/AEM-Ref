package com.mattel.ecomm.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class RegisterDollModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterDollModel.class);

	@Inject
	private MultifieldReader multifieldReader;

	@Inject
	private Node productlines;

	@Inject
	private Node retailers;

	@Inject
	private Node purchaseReasons;

	List<String> productlineList = new ArrayList<>();
	List<String> retailerList = new ArrayList<>();
	List<String> purchaseReasonList = new ArrayList<>();

	@PostConstruct
	protected void init() {
		LOGGER.info("RegisterDollModel Init Start");
		long startTime = System.currentTimeMillis();
		if (productlines != null) {
			LOGGER.debug("Reading productlines start");
			Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(productlines);
			for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
				productlineList.add(entry.getValue().get("productline", String.class));
			}
			LOGGER.debug("Reading productlines end");
		}

		if (retailers != null) {
			LOGGER.debug("Reading retailers start");
			Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(retailers);
			for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
				retailerList.add(entry.getValue().get("retailer", String.class));
			}
			LOGGER.debug("Reading retailers end");
		}

		if (purchaseReasons != null) {
			LOGGER.debug("Reading purchase reasons start");
			Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(purchaseReasons);
			for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
				purchaseReasonList.add(entry.getValue().get("purchaseReason", String.class));
			}
			LOGGER.debug("Reading purchase reasons end");
		}

		long endTime = System.currentTimeMillis();
		LOGGER.debug(Constant.EXECUTION_TIME_LOG, "init", endTime - startTime);
		LOGGER.info("RegisterDollModel Init end");
	}

	public List<String> getProductlineList() {
		return productlineList;
	}

	public List<String> getRetailerList() {
		return retailerList;
	}

	public List<String> getPurchaseReasonList() {
		return purchaseReasonList;
	}
}
