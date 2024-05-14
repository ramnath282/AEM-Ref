package com.mattel.ecomm.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.Cookie;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mattel.ecomm.core.pojos.DHTreatmentServiceUIResponse;
import com.mattel.ecomm.core.utils.DHTreatmentServiceUIAdapter;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.DHTreatmentViewService;
import com.mattel.ecomm.coreservices.core.pojos.DHService;
import com.mattel.ecomm.coreservices.core.pojos.DHTreatmentViewResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DHTreatmentViewModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(DHTreatmentViewModel.class);
	@Inject
	DHTreatmentViewService dhTreatmentViewService;
	@SlingObject
	SlingHttpServletRequest request;
	@Inject
	PropertyReaderService propertyReaderService;
	private ObjectWriter objWriter = null;
	private List<DHTreatmentServiceUIResponse> dhTreatmentServiceUIResponseList = null;
	private String scene7Url=null;
	/**
	 * The init method to fetch the product information from Commerce
	 */
	@PostConstruct
	protected void init() {
		LOGGER.info("DHTreatmentViewModel init method  ----> Start");
		DHTreatmentViewResponse dhCategoryViewResponse = null;
	    Cookie[] cookies = null;
		scene7Url=propertyReaderService.getScene7Url();
		objWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		cookies = request.getCookies();
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put(Constant.DOMAIN_KEY, "ag_en");
		requestMap.put(Constant.STORE_KEY, "ag_en");
		requestMap.put(Constant.REQUEST_COOKIES_KEY, cookies);
		try {
			dhCategoryViewResponse = dhTreatmentViewService.fetch(requestMap);
			this.formatProduct(dhCategoryViewResponse);
		} catch (ServiceException e) {
			LOGGER.error("Into DH Model Service Exception",e); 
		}
		LOGGER.info("DHTreatmentViewModel init method  ----> end");
	}

	private List<DHTreatmentServiceUIResponse> formatProduct(DHTreatmentViewResponse dhTreatmentViewResponse) {
		final List<DHService> catalogView = dhTreatmentViewResponse.getCatalogEntryView();
		dhTreatmentServiceUIResponseList = new ArrayList<>();
		DHTreatmentServiceUIAdapter dhTreatmentServiceUIAdapter = new DHTreatmentServiceUIAdapter();
		for (DHService dhService : catalogView) {
			dhTreatmentServiceUIResponseList
					.add(dhTreatmentServiceUIAdapter.transformTreatmentServiceToSignleSKU(dhService));
		}
		return dhTreatmentServiceUIResponseList;
	}

	/**
	 * @return the dhTreatmentServiceUIResponseList
	 * @throws JsonProcessingException
	 */
	public String getDhTreatmentServiceUIResponseList() throws JsonProcessingException {
		return objWriter.writeValueAsString(dhTreatmentServiceUIResponseList);
	}

	/**
	 * @return the scene7url
	 */
	public String getScene7Url() {
		return scene7Url;
	}
}