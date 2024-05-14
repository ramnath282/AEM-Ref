package com.mattel.global.core.pojo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class AssetFilterPojoTest {

	@InjectMocks
	AssetFilterPojo assetFilterPojo;

	@Test
	public void testGetterSetter() {
		assetFilterPojo.setFilterId("filterId");
		assetFilterPojo.setFilterTitle("filterTitle");
		assetFilterPojo.setFilterValue("filterValue");
		assetFilterPojo.setFilterType("filterType");
		assetFilterPojo.setName("name");
		List<AssetFilterPojo> filters = new ArrayList<>();
		filters.add(assetFilterPojo);

		Assert.assertEquals("filterId", assetFilterPojo.getFilterId());
		Assert.assertEquals("filterTitle", assetFilterPojo.getFilterTitle());
		Assert.assertEquals("filterValue", assetFilterPojo.getFilterValue());
		Assert.assertEquals("filterType", assetFilterPojo.getFilterType());
		Assert.assertEquals("name", assetFilterPojo.getName());
		assetFilterPojo.toString();
		
		AssetFilterPojo.generateFilterId("filter:tag-1");
	}

}
