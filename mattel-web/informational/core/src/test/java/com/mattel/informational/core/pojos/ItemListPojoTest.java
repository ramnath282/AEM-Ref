package com.mattel.informational.core.pojos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class ItemListPojoTest {
	@InjectMocks
	private ItemListPojo itemListPojo;
	
	@Test
	public void testGetterSetter(){
		itemListPojo.setTitle("title");
		itemListPojo.setDescription("description");
		assertEquals("title", itemListPojo.getTitle());
		assertEquals("description", itemListPojo.getDescription());
		itemListPojo.toString();
	}
}
