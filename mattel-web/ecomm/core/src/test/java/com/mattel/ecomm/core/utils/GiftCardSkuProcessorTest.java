package com.mattel.ecomm.core.utils;

import org.junit.Assert;
import org.junit.Test;

public class GiftCardSkuProcessorTest {
  @Test
  public void testCheckAndEscapeDelimiter() throws Exception {
    Assert.assertEquals("ggh49~agcar", GiftCardSkuProcessor.checkAndEscapeDelimiter("ggh49.agcar"));
  }

  @Test
  public void testCheckAndUnescapeDelimiter() throws Exception {
    Assert.assertEquals("ggh49.agcar",
        GiftCardSkuProcessor.checkAndUnescapeDelimiter("ggh49~agcar"));
  }
}
