package com.mattel.global.core.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import com.mattel.global.core.constants.Constant;

public class PropertyUtilsTest {
	
	@Test
	public void testGetBrandDetails() {		
		Map<String,String> map;
		String brandSiteMapConfig = "corp=/content/dam/corporate-sites/sitemap/rootsitemap.xml_/content/mattel/mattel-corporate/_http://localhost:4502/_http://localhost:4502/";
		map = PropertyUtils.getBrandDetails(brandSiteMapConfig);
		assertEquals("/content/dam/corporate-sites/sitemap/rootsitemap.xml", map.get(Constant.ROOT_SITE_MAP_PATH));
	}
	
	@Test
	public void testGetBrandDetailsIFEmpty() {
		Map<String,String> map;
		String brandSiteMapConfig = StringUtils.EMPTY;
		map = PropertyUtils.getBrandDetails(brandSiteMapConfig);
		assertNull(map.get(Constant.ROOT_SITE_MAP_PATH));
	}

}
