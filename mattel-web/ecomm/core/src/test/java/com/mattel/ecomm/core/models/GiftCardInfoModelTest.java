package com.mattel.ecomm.core.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;

import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.core.pojos.shopify.ProductUIResponse;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.core.utils.EcommConfigurationUtils;
import com.mattel.ecomm.core.utils.ProductSchemaBuilder;
import com.mattel.ecomm.core.utils.shopify.ProductUIAdapter;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAvailability;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Core;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Product;
import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ProductSchemaBuilder.class, EcommConfigurationUtils.class })
public class GiftCardInfoModelTest {

	@Mock
	ProductAvailability productAvailability;

	@Mock
	Resource resource;

	@InjectMocks
	GiftCardInfoModel giftCardInfoModel;

	@Mock
	EcommConfigurationUtils ecommConfigurationUtils;

	@Mock
	SlingHttpServletRequest request;

	@Mock
	private RequestPathInfo pathInfo;

	private ProductServiceResponse productServiceResponse;
	Node giftMessage;
	MultifieldReader multifieldReader;
	private ValueMap valueMap;

	@Test
	public void test() throws Exception {
		String[] selectors = { "ag_en", "GBM03" };
		giftMessage = Mockito.mock(Node.class);
		multifieldReader = Mockito.mock(MultifieldReader.class);
		MemberMatcher.field(GiftCardInfoModel.class, "giftMessage").set(giftCardInfoModel, giftMessage);
		MemberMatcher.field(GiftCardInfoModel.class, "multifieldReader").set(giftCardInfoModel, multifieldReader);
		valueMap = Mockito.mock(ValueMap.class);
		final Map<String, ValueMap> multifieldProperty = new HashMap<>();
		multifieldProperty.put("page", valueMap);
		multifieldProperty.put("page", valueMap);
		multifieldProperty.put("page", valueMap);
		Mockito.when(multifieldReader.propertyReader(giftMessage)).thenReturn(multifieldProperty);
		giftCardInfoModel.setMultifieldReader(multifieldReader);

		PowerMockito.mockStatic(ProductSchemaBuilder.class);
		createProductServiceResponse();
		MemberModifier.field(GiftCardInfoModel.class, "request").set(giftCardInfoModel, request);
		Mockito.when(request.getRequestPathInfo()).thenReturn(pathInfo);
		Mockito.when(pathInfo.getSelectors()).thenReturn(selectors);
		Mockito.when(productAvailability.fetch(Mockito.any())).thenReturn(productServiceResponse);
		ProductUIResponse productUIResponse = ProductUIAdapter.transformProduct(productServiceResponse.getProduct(),
				null);
		giftCardInfoModel.setGiftCardSeoSchema("seo");
		Mockito.when(ProductSchemaBuilder.buildCompositeProductSchema(productUIResponse))
				.thenReturn(giftCardInfoModel.getGiftCardSeoSchema());
		PowerMockito.mockStatic(EcommConfigurationUtils.class);
		Mockito.when(EcommConfigurationUtils.getAtPropertyTarget()).thenReturn("atPropertyTarget");
		giftCardInfoModel.init();
		Assert.assertNotNull(giftCardInfoModel.getGiftCardUIResponse());
		Assert.assertNotNull(giftCardInfoModel.getAtPropertyTarget());
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
		shopify.setCore(core);
		productServiceResponse.setProduct(shopify);
	}

	@Test
	public void test_oneSelector() throws Exception {
		String[] selectors = { "ag_en" };
		MemberModifier.field(GiftCardInfoModel.class, "request").set(giftCardInfoModel, request);
		Mockito.when(request.getRequestPathInfo()).thenReturn(pathInfo);
		Mockito.when(pathInfo.getSelectors()).thenReturn(selectors);
		giftCardInfoModel.init();
	}
}
