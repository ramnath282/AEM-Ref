package com.mattel.ecomm.coreservices.core.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
  private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

  public static final String BACK_ORDER_ORIGINAL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
  public static final String BACK_ORDER_REQUIRED_DATE_FORMAT = "MMMM dd, yyyy";
  public static final String STANDARD_CALENDAR_DATE = "MM/dd/yyyy";

  /**
   * CTS
   */
  private DateUtil() {
    // no-op
  }

  /**
   * @checkfornull
   * @param dateFormat
   * @param date
   * @return Formatted date string or else <code>null</code>.
   */
  public static String getFormattedDate(String dateFormat, Date date) {
    DateUtil.LOGGER.info("getFormattedDate -> start");

    if (StringUtils.isNotBlank(dateFormat) && Objects.nonNull(date)) {
      try {
        final SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        return formatter.format(date);
      } catch (final RuntimeException e) {
        DateUtil.LOGGER.error("Date formatter exception : {}", e);
      }
    }

    DateUtil.LOGGER.info("getFormattedDate -> end");
    return null;
  }

  /**
   * @checkfornull
   * @param dateFormat
   * @param date
   * @return Parsed date or else <code>null</code>.
   */
  public static Date getParsedDate(String dateFormat, String date) {
    DateUtil.LOGGER.info("getParsedDate -> start");

    if (StringUtils.isNotBlank(dateFormat) && StringUtils.isNotBlank(date)) {
      try {
        final SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        return formatter.parse(date);
      } catch (final ParseException | RuntimeException e) {
        DateUtil.LOGGER.error("Date parsing exception : {}", e);
      }
    }

    return null;
  }
}
