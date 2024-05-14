package com.mattel.ecomm.core.interfaces;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.mattel.ecomm.coreservices.core.interfaces.DataImporterService;
import com.mattel.ecomm.coreservices.core.pojos.ProductDataImporterResponse;

public interface ProductDataImporterService extends DataImporterService<InputStream, ProductDataImporterResponse> {

	URL getProductFeedEndPointURL() throws MalformedURLException;
	URL getDeleteProductFeedEndPointURL() throws MalformedURLException;
}
