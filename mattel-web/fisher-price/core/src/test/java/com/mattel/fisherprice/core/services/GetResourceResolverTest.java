package com.mattel.fisherprice.core.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetResourceResolverTest {
	@InjectMocks
	GetResourceResolver getResourceResolver;
	@Mock
	ResourceResolverFactory resourceResolverFactory;
	@Mock
	ResourceResolver resourceResolver;
	Map<String, Object> map = new HashMap<>();

	@Before
	public void setUp() throws LoginException, IllegalArgumentException, IllegalAccessException {
		MemberModifier.field(GetResourceResolver.class, "resourceResolverFactory").set(getResourceResolver,
				resourceResolverFactory);
		map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
		map.put(ResourceResolverFactory.USER, "fisherpriceserviceuser");
		Mockito.when(resourceResolverFactory.getServiceResourceResolver(map)).thenReturn(resourceResolver);
	}

	@Test
	public void getResourceResolver() {
		getResourceResolver.getResourceResolver();
	}
}
