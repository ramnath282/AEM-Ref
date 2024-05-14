package com.mattel.global.core.model.v1;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.mattel.global.core.model.ButtonDetails;

import io.wcm.testing.mock.aem.junit.AemContext;

public class CTAGroupModelTest {
	
	@Rule
	public final AemContext context = new AemContext();
	
	CTAGroupModel ctaGroupModel;
	
	@Before
	public void setUp() {
		context.addModelsForClasses(CTAGroupModel.class);
		context.addModelsForClasses(ButtonDetails.class);
		
		Map<String, String> buttonDetails = new ImmutableMap.Builder<String, String>().put("dropShadow", "true").build();
		
		Map<String, String> buttonItem = new ImmutableMap.Builder<String, String>().put("ctaLink","/content/ag/home/buy")
				.put("ctaLabel","Buy").build();
		
		Map<String, String> buttonItem1 = new ImmutableMap.Builder<String, String>().put("ctaLink","/content/ag/home/buy/buyLater")
				.put("ctaLabel","Buy Later").build();
		
		context.build().resource("/content/ag/home/jcr:content/ctagroupcomponent",buttonDetails).hierarchyMode()
		.resource("ctaGroup").siblingsMode().resource("item0",buttonItem).resource("item1",buttonItem1);	
	}
	
	@Test
	public void test() {
		Resource res = context.currentResource("/content/ag/home/jcr:content/ctagroupcomponent");
		ctaGroupModel = res.adaptTo(CTAGroupModel.class);
		assertEquals(true,ctaGroupModel.isDropShadow());
		assertEquals("Buy",ctaGroupModel.getButtonDetailslist().get(0).getCtaLabel());
	}

}
