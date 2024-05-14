package com.mattel.ag.retail.core.model;

import org.junit.Before;
import org.junit.Test;

import com.mattel.ag.retail.core.utils.PropertyReaderUtils;

public class CustomerReviewsModelTest {
	
	CustomerReviewsModel customerReviewsModel;
	PropertyReaderUtils propertyReaderUtils;
	
	@Before
	public void setUp(){
		customerReviewsModel = new CustomerReviewsModel();
		propertyReaderUtils = new PropertyReaderUtils();
	}
	
	@Test
	public void testInit(){
		customerReviewsModel.init();
	}
	
	@Test
	public void testSetPropertyReaderUtils(){
		customerReviewsModel.setPropertyReaderUtils(propertyReaderUtils);
		customerReviewsModel.getBazaarVoiceBaseURL();
	}
	
}
