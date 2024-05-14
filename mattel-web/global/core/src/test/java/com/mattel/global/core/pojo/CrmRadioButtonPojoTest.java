package com.mattel.global.core.pojo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class CrmRadioButtonPojoTest {

	@InjectMocks
	CrmRadioButtonPojo crmRadioButtonPojo;
	
	@Test
	public void testGetterSetter(){
		crmRadioButtonPojo.setRadioKey("radioKey");
		crmRadioButtonPojo.setRadioValue("radioValue");
		Assert.assertEquals("radioKey", crmRadioButtonPojo.getRadioKey());
		Assert.assertEquals("radioValue", crmRadioButtonPojo.getRadioValue());
		crmRadioButtonPojo.toString();
	}
	
}
