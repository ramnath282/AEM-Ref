package com.mattel.ecomm.core.interfaces;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.pojos.ProductFeaturePojo;

public interface ProductService {
	ValueMap productProperties(String productSKUId, Resource resource);

	Page getCurrentPagePath(PageManager pageManager, Resource resource, SlingHttpServletRequest request);

	List<ProductFeaturePojo> getProductFeatures(ValueMap prodProperties, List<ProductFeaturePojo> prodFeaturePojoList,
			String productAccordTitle);

	String checkLocale(Page currentPage, Resource resource);

	String getPageLocaleFromMappings(String path, String pageLocale);
	
	ValueMap fetchProductProperties(Page parentProductPage, Resource resource, String productSKUId);

}
