package com.mattel.ecomm.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductBadgePojoTest {
	private ProductBadgePojo productBadgePojo = null;

	@Before
	public void setUp() throws Exception {
		productBadgePojo = new ProductBadgePojo();
		productBadgePojo.setBadgeColour("badgeColour");
		productBadgePojo.setBadgeDisplayValue("badgeDisplayValue");
		productBadgePojo.setBadge("badge");
		productBadgePojo.setBadgeIcon("badgeIcon");
		productBadgePojo.setTextColour("textColour");
	}

	@Test
	public void getBadgeColour() {
		Assert.assertEquals("badgeColour", productBadgePojo.getBadgeColour());
	}

	@Test
	public void getBadgeDisplayValue() {
		Assert.assertEquals("badgeDisplayValue", productBadgePojo.getBadgeDisplayValue());
	}

	@Test
	public void getBadge() {
		Assert.assertEquals("badge", productBadgePojo.getBadge());
	}

	@Test
	public void getBadgeIcon() {
		Assert.assertEquals("badgeIcon", productBadgePojo.getBadgeIcon());
	}

	@Test
	public void getTextColour() {
		Assert.assertEquals("textColour", productBadgePojo.getTextColour());
	}

}
