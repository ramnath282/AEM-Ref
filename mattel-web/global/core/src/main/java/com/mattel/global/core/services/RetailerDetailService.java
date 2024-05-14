package com.mattel.global.core.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ResourceUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.mattel.global.master.core.constants.Constants;

@Component(service = RetailerDetailService.class, immediate = true)
public class RetailerDetailService {

	@Reference
	private ResourceResolverFactory resolverFactory;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RetailerDetailService.class);

	public Map<String,String> getRetailerDetails(String retailer) {
		LOGGER.info("Method :: getRetailerDetails Start");
		Map<String,String> retailerDetail = null;
		ResourceResolver resourceResolver = null;	      
		try {
			Map<String, Object> map = new HashMap<>();
			map.put(ResourceResolverFactory.SUBSERVICE, Constants.READ_WRITE);  
			resourceResolver= resolverFactory.getServiceResourceResolver(map);
			Resource retailerResource = resourceResolver.resolve(retailer);
			if(! ResourceUtil.isNonExistingResource(retailerResource)) {
				LOGGER.debug(retailerResource.getPath());
				ContentFragment retailerContentFragment= retailerResource.adaptTo(ContentFragment.class);
				if(Objects.nonNull(retailerContentFragment)) {
					LOGGER.debug("Retailer Content Fragment :: {}",retailerContentFragment.getName()); 
					retailerDetail = readRetailerContentFragment(retailerContentFragment);
				}
			}
		} catch (LoginException e) {			
			LOGGER.error("Exception :: {}",e);
		}	
		LOGGER.info("Method :: getRetailerDetails End");  
		return retailerDetail;
	}

	private Map<String,String> readRetailerContentFragment(ContentFragment retailerContentFragment) {
		LOGGER.info("Method :: readRetailerContentFragment Start");
		Map<String,String> retailerDetail = new HashMap<>();
		Iterator<ContentElement> retailerElement= retailerContentFragment.getElements();
		retailerElement.forEachRemaining(ce -> {retailerDetail.put(ce.getName(), ce.getContent());
		LOGGER.debug("Retailer info :: {} {}",ce.getName(),ce.getContent());
		});
		LOGGER.info("Method :: readRetailerContentFragment End");
		return retailerDetail;

	}

}
