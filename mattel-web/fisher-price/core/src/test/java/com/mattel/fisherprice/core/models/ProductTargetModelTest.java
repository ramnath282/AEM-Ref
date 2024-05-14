package com.mattel.fisherprice.core.models;

import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.mattel.fisherprice.core.utils.FisherPriceConfigurationUtils;

@PrepareForTest(FisherPriceConfigurationUtils.class)
public class ProductTargetModelTest {

	ProductTargetModel productTargetModel = new ProductTargetModel();
	@Test
	public void initTest() {
		productTargetModel.init();
	}
	
	public void getAtPropertyTargetFP() {
		productTargetModel.getAtPropertyTargetFP();
	}
}
