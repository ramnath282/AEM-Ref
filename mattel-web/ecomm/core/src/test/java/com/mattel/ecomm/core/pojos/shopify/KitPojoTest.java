package com.mattel.ecomm.core.pojos.shopify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class KitPojoTest {

	@Test
	public void test_ClassKitPojo() {

		KitPojo kitPojo = new KitPojo();
		SleepersPojo sleepersPojo1 = Mockito.mock(SleepersPojo.class);
		SleepersPojo sleepersPojo2 = Mockito.mock(SleepersPojo.class);
		SleepersPojo sleepersPojo3 = Mockito.mock(SleepersPojo.class);
		SleepersPojo sleepersPojo4 = Mockito.mock(SleepersPojo.class);

		List<SleepersPojo> sleepers = new ArrayList<>();
		sleepers.add(sleepersPojo1);
		sleepers.add(sleepersPojo2);
		sleepers.add(sleepersPojo3);
		sleepers.add(sleepersPojo4);

		kitPojo.setSleepers(sleepers);
		kitPojo.toString();

		Assert.assertEquals(sleepers, kitPojo.getSleepers());
		Assert.assertNotNull(kitPojo);
	}

}
