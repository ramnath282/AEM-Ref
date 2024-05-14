package com.mattel.ecomm.core.pojos.shopify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class SleepersPojoTest {

	private DollsPojo dollsPojo1;
	private DollsPojo dollsPojo2;
	private DollsPojo dollsPojo3;
	private DollsPojo dollsPojo4;

	@Test
	public void test_ClassSleepersPojo() {

		createDollsPojoObject();
		SleepersPojo sleepers = new SleepersPojo();
		List<DollsPojo> dolls = new ArrayList<>();
		{
			dolls.add(dollsPojo1);
			dolls.add(dollsPojo2);
			dolls.add(dollsPojo3);
			dolls.add(dollsPojo4);
		}
		sleepers.setDolls(dolls);
		sleepers.setHasFirstSequenceDoll(true);
		sleepers.setHasSecondSequenceDoll(false);
		sleepers.setImageURL("imageURL/sleepers");
		sleepers.setSleeperType("sleeperType");

		Assert.assertEquals(dolls, sleepers.getDolls());
		Assert.assertEquals("imageURL/sleepers", sleepers.getImageURL());
		Assert.assertEquals("sleeperType", sleepers.getSleeperType());
		Assert.assertTrue(sleepers.isHasFirstSequenceDoll());
		Assert.assertFalse(sleepers.isHasSecondSequenceDoll());
		Assert.assertNotNull(sleepers.toString());
	}

	public void createDollsPojoObject() {
		dollsPojo1 = new DollsPojo();
		{
			dollsPojo1.setDollType("doll_type_1");
			dollsPojo1.setImageURL("imageURL/doll_type_1");
		}
		dollsPojo2 = new DollsPojo();
		{
			dollsPojo2.setDollType("doll_type_2");
			dollsPojo2.setImageURL("imageURL/doll_type_2");
		}
		dollsPojo3 = new DollsPojo();
		{
			dollsPojo3.setDollType("doll_type_3");
			dollsPojo3.setImageURL("imageURL/doll_type_3");
		}
		dollsPojo4 = new DollsPojo();
		{
			dollsPojo4.setDollType("doll_type_4");
			dollsPojo4.setImageURL("imageURL/doll_type_4");
		}
	}

}
