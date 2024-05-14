package com.mattel.global.core.pojo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class CrmCheckFieldPojoTest {

	@InjectMocks
	CrmCheckFieldPojo crmCheckFieldPojo;
	
	@Test
	public void testGetterSetter(){
		crmCheckFieldPojo.setItemKey("itemKey");
		crmCheckFieldPojo.setItemValue("itemValue");
		Assert.assertEquals("itemKey", crmCheckFieldPojo.getItemKey());
		Assert.assertEquals("itemValue", crmCheckFieldPojo.getItemValue());
		crmCheckFieldPojo.toString();
	}
	
}
