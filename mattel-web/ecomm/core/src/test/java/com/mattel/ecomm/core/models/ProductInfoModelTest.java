package com.mattel.ecomm.core.models;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.jetbrains.annotations.TestOnly;
import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.ecomm.core.interfaces.GetProductTypeService;
import com.mattel.ecomm.core.interfaces.StoreInterest;
import com.mattel.ecomm.core.pojos.shopify.ProductUIResponse;
import com.mattel.ecomm.core.utils.EcommConfigurationUtils;
import com.mattel.ecomm.core.utils.ProductSchemaBuilder;
import com.mattel.ecomm.core.utils.shopify.ProductUIAdapter;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAvailability;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Core;
import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Product;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

//@PrepareForTest(ProductUIAdapter.class)

@RunWith(PowerMockRunner.class)
public class ProductInfoModelTest {

	@InjectMocks
	private ProductInfoModel productInfoModel;

	@Mock
	private SlingHttpServletRequest request;

	@Mock
	private Resource resource;

	@Mock
	private RequestPathInfo pathInfo;

	@Mock
	EcommConfigurationUtils ecommConfigurationUtils;

	@Mock
	StoreInterest storeInterest;

	@Mock
	PropertyReaderService propertyReaderService;

	@Mock
	GetProductTypeService getProductTypeService;

	@Mock
	ProductAvailability productAvailability;

	ValueMap properties;
	private ProductServiceResponse productServiceResponse;

	@Test
	public void test() throws Exception {

		String[] selectors = { "ag_en", "GBM03" };
		createProductServiceResponse();
		// PowerMockito.mockStatic(ProductUIAdapter.class);
		MemberModifier.field(ProductInfoModel.class, "request").set(productInfoModel, request);
		Mockito.when(request.getRequestPathInfo()).thenReturn(pathInfo);
		Mockito.when(pathInfo.getSelectors()).thenReturn(selectors);
		Map<String, String> seoPropertiesMap = new HashMap<>();
		seoPropertiesMap.put("key_1", "value_1");
		Mockito.when(getProductTypeService.getProductType(selectors[1], selectors[0])).thenReturn(seoPropertiesMap);
		Mockito.when(propertyReaderService.getBvPassKey(selectors[0])).thenReturn(selectors[0]);
		Mockito.when(productAvailability.fetch(Mockito.any())).thenReturn(productServiceResponse);
		properties = Mockito.mock(ValueMap.class);
		Mockito.when(resource.adaptTo(ValueMap.class)).thenReturn(properties);
		productInfoModel.init();
		Assert.assertNotNull(productServiceResponse.getProduct());
		Assert.assertEquals(selectors[0], productInfoModel.getBazarVoicePassKey());
		Assert.assertNotNull(productInfoModel.getProductUIResponse());
		Assert.assertNotNull(productInfoModel.getAffirmInEligibleKey());
		Assert.assertNotNull(productInfoModel.getCanonicalUrl());
	}

	public void createProductServiceResponse() throws IOException {
		productServiceResponse = new ProductServiceResponse();
		Core core = new Core();
		Product shopify = new Product();
		Map<String, Object> attributes = new HashMap<>();
		List<String> keyList1 = new ArrayList<>();
		keyList1.add("Offer valid through 1/31/19  or until limited promotional quantities are exhausted.");
		attributes.put("MarketingDescription", keyList1);
		List<String> keyList2 = new ArrayList<>();
		keyList2.add("8+");
		attributes.put("LegalAge", keyList2);
		String key3 = "3+";
		attributes.put("MarketingAge", key3);
		shopify.setAttributes(attributes);
		core.setTitle("Core_Title");
		core.setProduct_affirmIneligible("affirmIneligible");
		System.out.println(core);
		shopify.setCore(core);
		System.out.println("This is " + shopify);
		productServiceResponse.setProduct(shopify);
	}

	@Test
	public void test_ifLessThanTwoSelectors() throws Exception {

		String[] selectors = { "ag_en" };
		Mockito.when(request.getRequestPathInfo()).thenReturn(pathInfo);
		Mockito.when(pathInfo.getSelectors()).thenReturn(selectors);
		productInfoModel.init();
	}
}