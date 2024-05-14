package com.mattel.ag.retail.core.pojos;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class StoreBistroHoursPojoTest {

	StoreBistroHoursPojo storeBistroHoursPojo;

	@Before
	public void setUp() {
		storeBistroHoursPojo = new StoreBistroHoursPojo();
	}

	@Test
	public void setStoreStartTime() {
		storeBistroHoursPojo.setStoreStartTime(new Date().toString());
	}

	@Test
	public void setStoreEndDate() {
		storeBistroHoursPojo.setStoreEndDate(12);
	}

	@Test
	public void setBistroEndDate() {
		storeBistroHoursPojo.setBistroEndDate(12);
	}

	@Test
	public void setStoreEndTime() {
		storeBistroHoursPojo.setStoreEndTime(new Date().toString());
	}

	@Test
	public void setBistroStartTime() {
		storeBistroHoursPojo.setBistroStartTime(new Date().toString());
	}

	@Test
	public void setBistroEndTime() {
		storeBistroHoursPojo.setBistroEndTime(new Date().toString());
	}

	@Test
	public void setMonth() {
		storeBistroHoursPojo.setMonth(12 + "");
	}

	@Test
	public void setDate() {
		storeBistroHoursPojo.setDate(12);
	}

	@Test
	public void getStoreStartTime() {
		storeBistroHoursPojo.getStoreStartTime();
	}

	@Test
	public void getStoreEndDate() {
		storeBistroHoursPojo.getStoreEndDate();
	}

	@Test
	public void getStoreEndTime() {
		storeBistroHoursPojo.getStoreEndTime();
	}

	@Test
	public void getBistroEndDate() {
		storeBistroHoursPojo.getBistroEndDate();
	}

	@Test
	public void getBistroStartTime() {
		storeBistroHoursPojo.getBistroStartTime();
	}

	@Test
	public void getBistroEndTime() {
		storeBistroHoursPojo.getBistroEndTime();
	}

	@Test
	public void getMonth() {
		storeBistroHoursPojo.getMonth();
	}

	@Test
	public void getDate() {
		storeBistroHoursPojo.getDate();
	}

	@Test
	public void setBistroStoreClosed() {
		storeBistroHoursPojo.setBistroStoreClosed(true);
	}

	@Test
	public void isBistroStoreClosed() {
		storeBistroHoursPojo.isBistroStoreClosed();
	}

	@Test
	public void setStoreStoreClosed() {
		storeBistroHoursPojo.setStoreStoreClosed(true);
	}

	@Test
	public void isStoreStoreClosed() {
		storeBistroHoursPojo.isStoreStoreClosed();
	}

	@Test
	public void setStoreEndMonth() {
		storeBistroHoursPojo.setStoreEndMonth(12 + "");
	}

	@Test
	public void getStoreEndMonth() {
		storeBistroHoursPojo.getStoreEndMonth();
	}

	@Test
	public void setBistroEndMonth() {
		storeBistroHoursPojo.setBistroEndMonth(12 + "");
	}

	@Test
	public void getBistroEndMonth() {
		storeBistroHoursPojo.getBistroEndMonth();
	}
	
	@Test 
	public void toStringTest(){
		storeBistroHoursPojo.toString();
	}

}