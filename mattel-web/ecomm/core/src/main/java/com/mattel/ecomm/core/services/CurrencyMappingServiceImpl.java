package com.mattel.ecomm.core.services;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.interfaces.CurrencyMappingService;
import com.mattel.ecomm.core.pojos.CurrencyMappingPojo;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Component(service = CurrencyMappingService.class)
public class CurrencyMappingServiceImpl implements CurrencyMappingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyMappingServiceImpl.class);
	@Reference
	private MultifieldReader multifieldReader;

	@Override
	public String getCurrencyDetails(String currencyType, Resource resource) {
		String currencyCode = StringUtils.EMPTY;
		LOGGER.debug("getCurrencyDetails with currency type Method Started");
		Resource currencyResource = resource.getResourceResolver().getResource(Constant.CURRENCY_MAPPING);
		Map<String, ValueMap> currencyLinkedHashMap;
		currencyLinkedHashMap = fetchCurrencyNodeMap(currencyResource);
		for (Map.Entry<String, ValueMap> mapEntry : currencyLinkedHashMap.entrySet()) {
			String tempType = mapEntry.getValue().get("currencyType").toString();
			if (currencyType.equals(tempType)) {
				currencyCode = mapEntry.getValue().get("currencyCode").toString();
			}
		}
		LOGGER.debug("getCurrencyDetails with currency type Method End");
		return currencyCode;

	}

	@Override
	public List<CurrencyMappingPojo> getCurrencyDetails(Resource resource) {
		LOGGER.debug("getCurrencyDetails method Started");
		Resource currencyResource = resource.getResourceResolver().getResource(Constant.CURRENCY_MAPPING);
		Map<String, ValueMap> currencyLinkedHashMap;
		currencyLinkedHashMap = fetchCurrencyNodeMap(currencyResource);
		List<CurrencyMappingPojo> currencyMapList = new LinkedList<>();
		for (Map.Entry<String, ValueMap> mapEntry : currencyLinkedHashMap.entrySet()) {
			CurrencyMappingPojo currency = new CurrencyMappingPojo();
			currency.setCurrencyType(mapEntry.getValue().get("currencyType").toString());
			currency.setCurrencySymbol(mapEntry.getValue().get("currencyCode").toString());
			currencyMapList.add(currency);
		}
		LOGGER.debug("getCurrencyDetails method Ended");
		return currencyMapList;
	}

	private Map<String, ValueMap> fetchCurrencyNodeMap(Resource currencyResource) {
		LOGGER.debug("fetchCurrencyNodeMap method Started");
		Map<String, ValueMap> currencyNodeMap = new HashMap<>();
		if (null != currencyResource) {
			Node currencyNode = currencyResource.adaptTo(Node.class);
			if (null != currencyNode) {
				currencyNodeMap = multifieldReader.propertyReader(currencyNode);
			}
		}
		LOGGER.debug("fetchCurrencyNodeMap method End");
		return currencyNodeMap;
	}

}
