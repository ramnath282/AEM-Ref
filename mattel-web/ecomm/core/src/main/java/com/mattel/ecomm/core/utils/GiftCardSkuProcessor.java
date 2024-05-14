package com.mattel.ecomm.core.utils;

import com.mattel.ecomm.coreservices.core.constants.Constant;

import org.apache.commons.lang3.StringUtils;

/**
 * To process Gift card part number which contains special characters, for e.g:
 * {@link Constant#PERIOD_PLACEHOLDER}, which is a reserve character in adobe cq.
 */
public class GiftCardSkuProcessor {
  /**
   * To replace {@link Constant#PERIOD_PLACEHOLDER} symbol, replace with
   * {@link Constant#GIFT_CARD_PART_NUMBER_NEW_DELIMITER}, {@link Constant#PERIOD_PLACEHOLDER} is
   * selector separator and a reserve character in adobe cq.
   *
   * @param partNumber
   *          The Gift Card part number containing {@link Constant#PERIOD_PLACEHOLDER}.
   * @return The transformed Gift Card part number.
   */
  public static String checkAndEscapeDelimiter(String partNumber) {
    if (StringUtils.contains(partNumber, Constant.PERIOD_PLACEHOLDER)) {
      return StringUtils.replaceAll(partNumber, Constant.PERIOD_ESC_DELIMITER,
          Constant.GIFT_CARD_PART_NUMBER_NEW_DELIMITER);
    }

    return partNumber;
  }

  /**
   * To replace {@link Constant#GIFT_CARD_PART_NUMBER_NEW_DELIMITER} with
   * {@link Constant#PERIOD_PLACEHOLDER} to generate Gift Card part number.
   *
   * @param partNumber
   *          The input part number.
   * @return The Gift Card part number.
   */
  public static String checkAndUnescapeDelimiter(String partNumber) {
    if (partNumber.contains(Constant.GIFT_CARD_PART_NUMBER_NEW_DELIMITER)) {
      return StringUtils.replace(partNumber, Constant.GIFT_CARD_PART_NUMBER_NEW_DELIMITER,
          Constant.PERIOD_PLACEHOLDER);
    }

    return partNumber;
  }

  private GiftCardSkuProcessor() {
    // no-op
  }
}
