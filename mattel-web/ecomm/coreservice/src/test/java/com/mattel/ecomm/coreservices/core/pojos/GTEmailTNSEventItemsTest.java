package com.mattel.ecomm.coreservices.core.pojos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class GTEmailTNSEventItemsTest {

	private GTEmailTNSEventItems gTEmailTNSEventItems;
	
	@Mock
	GTEmailTNSSkuItem gTEmailTNSSkuItem;
	List<GTEmailTNSSkuItem> list = new ArrayList<GTEmailTNSSkuItem>();
	
	
	@Test
	public void testItem() {
		Assert.assertNotNull(gTEmailTNSEventItems.getItem());
	}

	@Before
	public void setUp() throws Exception {
		gTEmailTNSEventItems = new GTEmailTNSEventItems();
		gTEmailTNSEventItems.setItem(list);

	}
}
