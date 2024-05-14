package com.mattel.global.core.model;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import io.wcm.testing.mock.aem.junit.AemContext;

public class ButtonDetailTest {
	
	@Rule
	public final AemContext context = new AemContext();
	
	ButtonDetails buttonDetails;
	
	@Before
	public void setUp() {		
		context.addModelsForClasses(ButtonDetails.class);
		
		Map<String, String> buttonItem = new ImmutableMap.Builder<String, String>().put("link","/content/ag/home/buy")
				.put("text","Buy")
				.put("ctaType", "button")
				.put("ctaStyle", "#85144B")
				.put("ctaLabel","See More")
				.put("ctaLink","/content/ag/home/see-more").build();
		
		context.build().resource("/content/ag/home/jcr:content/card/buttonList/item0",buttonItem);
	}
	
	@Test
	public void test() {
		Resource res = context.currentResource("/content/ag/home/jcr:content/card/buttonList/item0");
		buttonDetails = res.adaptTo(ButtonDetails.class);
		buttonDetails.setCtaLink("/content/ag/home/see-more");
		buttonDetails.setLink("/content/ag/home/shop/buy");
		assertEquals("/content/ag/home/see-more",buttonDetails.getCtaLink());
		assertEquals("/content/ag/home/shop/buy",buttonDetails.getLink());
		assertEquals("See More",buttonDetails.getCtaLabel());
		assertEquals("Buy",buttonDetails.getText());
		assertEquals("button",buttonDetails.getCtaType());
		assertEquals("#85144B",buttonDetails.getCtaStyle());
		
		
	}

}
