package com.mattel.ecomm.core.utils.shopify;

import java.io.IOException;
import java.io.InputStream;

import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mattel.ecomm.coreservices.core.pojos.shopify.Product;
import com.mattel.ecomm.core.pojos.shopify.ProductUIResponse;

public class BittyTwinsProductUIAdapterTest {

	@Test
	public void testTransformProductToSignleSKU() throws JsonParseException, JsonMappingException, IOException {
		try (InputStream is = getClass().getResourceAsStream("gly16.json")) {
			ObjectMapper mapper = new ObjectMapper();
			ProductServiceResponse serviceResponse = mapper.readValue(is, ProductServiceResponse.class);
			Product product = serviceResponse.getProduct();
			ProductUIResponse pDPProductUIResponse = new ProductUIResponse();
			BittyTwinsProductUIAdapter.transformProductToBittyTwins(product, pDPProductUIResponse);
			 
	}}

}
