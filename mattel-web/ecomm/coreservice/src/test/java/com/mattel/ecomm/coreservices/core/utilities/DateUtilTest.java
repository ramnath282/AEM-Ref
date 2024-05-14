package com.mattel.ecomm.coreservices.core.utilities;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author CTS
 *
 */
public class DateUtilTest {
  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testGetFormattedDate() {
    Date date = new Date();
    DateUtil.getFormattedDate(DateUtil.BACK_ORDER_REQUIRED_DATE_FORMAT, date);
  }

  @Test
  public void testGetFormattedDateWithDateNull() {
    DateUtil.getFormattedDate(DateUtil.BACK_ORDER_REQUIRED_DATE_FORMAT, null);
  }

  @Test
  public void testGetParsedDate() {
    DateUtil.getParsedDate(DateUtil.BACK_ORDER_ORIGINAL_DATE_FORMAT, "1991-11-11 14:58:00.000000");
  }

  @Test
  public void testGetParsedDateWithInvalidDate() {
    DateUtil.getParsedDate(DateUtil.BACK_ORDER_ORIGINAL_DATE_FORMAT, "1991-11-11");
  }
}
