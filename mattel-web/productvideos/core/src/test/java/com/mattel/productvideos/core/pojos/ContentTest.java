package com.mattel.productvideos.core.pojos;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ContentTest {

	Content content;

	@Mock
	List<Item> items;

	@Before
	public void setup() {
		content = new Content();
		content.setTitle("title");
		content.setItems(items);
	}

	@Test
	public void testGetters() {
		Assert.assertEquals(content.getTitle(), "title");
		content.getItems();
	}

}
