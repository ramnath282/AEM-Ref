package com.mattel.fisherprice.core.pojos;

import static org.junit.Assert.*;

import org.junit.Test;

public class EntityParametersPojoTest {

	EntityParametersPojo entityParametersPojo = new EntityParametersPojo();
	@Test
	public void testArticleId() {
		entityParametersPojo.setArticleId("1");
		assertSame("1", entityParametersPojo.getArticleId());
	}
	
	@Test
	public void testEntityValue() {
		entityParametersPojo.setEntityValue("values");
		assertSame("values", entityParametersPojo.getEntityValue());
	}
	
	@Test
	public void testEntityKey() {
		entityParametersPojo.setEntityKey("1");
		assertSame("1", entityParametersPojo.getEntityKey());
	}
	
	@Test
	public void testAtProperty() {
		entityParametersPojo.setAtProperty("property");
		assertSame("property", entityParametersPojo.getAtProperty());
	}

}
