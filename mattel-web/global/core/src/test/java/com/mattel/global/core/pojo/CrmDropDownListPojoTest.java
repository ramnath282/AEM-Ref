package com.mattel.global.core.pojo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class CrmDropDownListPojoTest {

	@InjectMocks
	CrmDropDownListPojo crmDropDownListPojo;
	
	@Test
	public void testGetterSetter(){
		crmDropDownListPojo.setDropDownKey("dropDownKey");
		crmDropDownListPojo.setDropDownValue("dropDownValue");
		
		Assert.assertEquals("dropDownKey", crmDropDownListPojo.getDropDownKey());
		Assert.assertEquals("dropDownValue", crmDropDownListPojo.getDropDownValue());
		crmDropDownListPojo.toString();
	}
	
}
