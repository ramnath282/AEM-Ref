package com.mattel.ecomm.core.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mattel.ecomm.core.pojos.DHTreatmentServiceUIResponse;
import com.mattel.ecomm.coreservices.core.pojos.DHService;
import com.mattel.ecomm.coreservices.core.pojos.DHTreatmentServiceAttributes;

public class DHTreatmentServiceUIAdapter {

	public DHTreatmentServiceUIResponse transformTreatmentServiceToSignleSKU(DHService dhService) {
		DHTreatmentServiceUIResponse dhTreatmentServiceUIResponse = new DHTreatmentServiceUIResponse();
		dhTreatmentServiceUIResponse.setBuyable(dhService.getBuyable());
		dhTreatmentServiceUIResponse.setResourceId(dhService.getResourceId());
		dhTreatmentServiceUIResponse.setThumbnail(dhService.getThumbnail());
		dhTreatmentServiceUIResponse.setShortDescription(dhService.getShortDescription());
		dhTreatmentServiceUIResponse.setStoreID(dhService.getStoreID());
		dhTreatmentServiceUIResponse.setHasSingleSKU(dhService.getHasSingleSKU());
		dhTreatmentServiceUIResponse.setPartNumber(dhService.getPartNumber());
		dhTreatmentServiceUIResponse.setName(dhService.getName());
		dhTreatmentServiceUIResponse.setCatalogEntryTypeCode(dhService.getCatalogEntryTypeCode());
		Map<String, Object> attributes = buildAttributesList(dhService.getAttributes());
		dhTreatmentServiceUIResponse.setAttributes(attributes);
		return dhTreatmentServiceUIResponse;
	}

	private Map<String, Object> buildAttributesList(List<DHTreatmentServiceAttributes> attributes) {
		Map<String, Object> treatmentAttributes=new HashMap<>();
		
		for(DHTreatmentServiceAttributes dhServiceAttributes : attributes)
		{
			
			List<Map<String, String>> attrValueList = dhServiceAttributes.getValues();	
			treatmentAttributes.put(dhServiceAttributes.getIdentifier(),attrValueList);
			
		}
		return treatmentAttributes;
	}
}
