package com.mattel.play.core.commerce;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import com.adobe.cq.commerce.common.AbstractJcrProduct;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

public class PlayProductImpl extends AbstractJcrProduct {
	public static final String PN_IDENTIFIER = "identifier";
	public static final String PN_PRICE = "price";

	protected final ResourceResolver resourceResolver;
	protected final PageManager pageManager;
	protected Page productPage = null;
	protected String brand = null;

	public PlayProductImpl(Resource resource) {
		super(resource);

		resourceResolver = resource.getResourceResolver();
		pageManager = resourceResolver.adaptTo(PageManager.class);
		if (null != pageManager) {
			productPage = pageManager.getContainingPage(resource);
		}
	}

	public String getSKU() {
		return "todo-get-sku-method";
	}
}