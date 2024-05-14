package com.mattel.ecomm.core.pojos.shopify;

import org.junit.Assert;
import org.junit.Test;

public class DollsPojoTest {

	private DollsPojo dollsPojo = null;

	@Test
	public void test_ClassDollsPojo() throws Exception {

		dollsPojo = new DollsPojo();

		dollsPojo.setDollType("dollType");
		dollsPojo.setImageURL("imageURL/");
		dollsPojo.setKitDefaultSequence("kitDefaultSequence");
		dollsPojo.setKitDisplaySequence("kitDisplaySequence");
		dollsPojo.setPartNumber("GDY26");
		dollsPojo.setVariantId(1);
		dollsPojo.setProductType("ProductBean");
		dollsPojo.setAssociationType("BITTY_TWIN_COMP");
		dollsPojo.setProduct_buyable("1");
		dollsPojo.toString();

		Assert.assertEquals("dollType", dollsPojo.getDollType());
		Assert.assertEquals("imageURL/", dollsPojo.getImageURL());
		Assert.assertEquals("kitDefaultSequence", dollsPojo.getKitDefaultSequence());
		Assert.assertEquals("kitDisplaySequence", dollsPojo.getKitDisplaySequence());
		Assert.assertEquals("GDY26", dollsPojo.getPartNumber());
		Assert.assertEquals(1, dollsPojo.getVariantId());
		Assert.assertEquals("ProductBean", dollsPojo.getProductType());
		Assert.assertEquals("BITTY_TWIN_COMP", dollsPojo.getAssociationType());
		Assert.assertEquals("1", dollsPojo.getProduct_buyable());
		Assert.assertNotNull(dollsPojo);

	}
}
