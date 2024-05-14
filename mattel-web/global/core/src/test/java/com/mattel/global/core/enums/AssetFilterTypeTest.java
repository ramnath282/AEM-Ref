package com.mattel.global.core.enums;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.mattel.global.core.pojo.AssetFilterPojo;

public class AssetFilterTypeTest {

	@Test
	public void getRadioButton() {
		AssetFilterType assetFilterType = AssetFilterType.RADIOBUTTON;
		AssetFilterPojo assetFilter1 = new AssetFilterPojo();
		assetFilter1.setFilterId("filterId");
		assetFilter1.setFilterTitle("filterTitle");
		assetFilter1.setFilterType("filterType");
		assetFilter1.setFilterValue("filterValue");
		assetFilter1.setName("name");
		List<AssetFilterPojo> children = new ArrayList<>();
		children.add(assetFilter1);

		AssetFilterPojo parent = new AssetFilterPojo();
		parent.setFilterId("filterId");
		parent.setFilterTitle("filterTitle");
		parent.setFilterType("filterType");
		parent.setFilterValue("filterValue");
		parent.setName("name");

		Assert.assertNotNull(assetFilterType.modifyChildren(children, parent));
	}
}
