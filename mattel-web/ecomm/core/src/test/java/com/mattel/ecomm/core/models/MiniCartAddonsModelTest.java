package com.mattel.ecomm.core.models;

import org.apache.sling.api.resource.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.core.helper.EcommHelper;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EcommHelper.class })
public class MiniCartAddonsModelTest {

	private static final String internalUrl = "/content/en/ag/home";
	private static final String externalUrl = "https://www.americangirl.com";
	@Mock
	private EcommHelper ecomHelper;
	@Mock
	private Resource resource;

	@InjectMocks
	private MiniCartAddonsModel miniCartAddonsModel;

	@Test
	public void testToVerifyShoppingButtonInternallink() throws IllegalArgumentException, IllegalAccessException {
		MemberModifier.field(MiniCartAddonsModel.class, "shoppingButtonlink").set(miniCartAddonsModel, internalUrl);
		Assert.assertEquals(internalUrl, miniCartAddonsModel.getShoppingButtonlink());
	}

	@Test
	public void testToVerifyShoppingButtonExternallink() throws IllegalArgumentException, IllegalAccessException {
		MemberModifier.field(MiniCartAddonsModel.class, "shoppingButtonlink").set(miniCartAddonsModel, externalUrl);
		Assert.assertEquals(externalUrl, miniCartAddonsModel.getShoppingButtonlink());
	}
}
