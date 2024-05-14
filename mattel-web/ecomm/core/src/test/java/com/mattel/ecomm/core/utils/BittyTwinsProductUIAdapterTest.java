package com.mattel.ecomm.core.utils;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.ecomm.core.pojos.PDPProductUIResponse;
import com.mattel.ecomm.coreservices.core.pojos.PDPResponse;
import com.mattel.ecomm.coreservices.core.pojos.Product;

public class BittyTwinsProductUIAdapterTest {

	@Test
	public void testTransformProductToSignleSKU() throws JsonParseException, JsonMappingException, IOException {
		try (InputStream is = getClass().getResourceAsStream("btd01.json")) {
			ObjectMapper mapper = new ObjectMapper();

			 PDPResponse serviceResponse = mapper.readValue(is, PDPResponse.class);
			 Product product = serviceResponse.getCatalogEntryView().get(0).get("product");
			 PDPProductUIResponse pDPProductUIResponse = new PDPProductUIResponse();
			 BittyTwinsProductUIAdapter.transformProductToBittyTwins(product, pDPProductUIResponse);
			 
	}}

}
