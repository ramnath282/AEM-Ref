package com.mattel.play.core.commerce;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import com.adobe.cq.commerce.api.CommerceException;
import com.adobe.cq.commerce.api.CommerceQuery;
import com.adobe.cq.commerce.api.CommerceResult;
import com.adobe.cq.commerce.api.CommerceService;
import com.adobe.cq.commerce.api.CommerceSession;
import com.adobe.cq.commerce.api.PaymentMethod;
import com.adobe.cq.commerce.api.Product;
import com.adobe.cq.commerce.api.ShippingMethod;
import com.adobe.cq.commerce.api.promotion.Voucher;
import com.adobe.cq.commerce.common.AbstractJcrCommerceService;
import com.adobe.cq.commerce.common.AbstractJcrProduct;
import com.adobe.cq.commerce.common.ServiceContext;

public class PlayCommerceServiceImpl extends AbstractJcrCommerceService implements CommerceService {

	private Resource resource;

	public PlayCommerceServiceImpl(ServiceContext services, Resource res) {
		super(services, res);
		this.resource = res;
		this.resolver = res.getResourceResolver();
	}

	public CommerceSession login(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws CommerceException {
		return new PlayCommerceSessionImpl(this, request, response, resource);
	}

	public Product getProduct(final String path) throws CommerceException {
		resource = resolver.getResource(path);
		if (resource != null && resource.isResourceType(AbstractJcrProduct.RESOURCE_TYPE_PRODUCT)) {
			return new PlayProductImpl(resource);
		}
		return null;
	}

	public List<String> getCountries() throws CommerceException {
		return new ArrayList<>();
	}

	public List<String> getCreditCardTypes() throws CommerceException {

		return new ArrayList<>();
	}

	public Voucher getVoucher(String arg0) throws CommerceException {

		return null;
	}

	public CommerceResult search(CommerceQuery arg0) throws CommerceException {

		return null;
	}

	@Override
	public List<String> getOrderPredicates() throws CommerceException {

		return new ArrayList<>();
	}

	@Override
	public boolean isAvailable(String serviceType) {
		return "commerce-service".equals(serviceType);
	}

	@Override
	public List<ShippingMethod> getAvailableShippingMethods() throws CommerceException {
		return enumerateMethods("/etc/commerce/shipping-methods/geometrixx-outdoors", ShippingMethod.class);
	}

	@Override
	public List<PaymentMethod> getAvailablePaymentMethods() throws CommerceException {
		return enumerateMethods("/etc/commerce/payment-methods/geometrixx-outdoors", PaymentMethod.class);
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void setResolver(ResourceResolver resolver) {
		this.resolver = resolver;
	}

}