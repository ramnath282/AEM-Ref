package com.mattel.ecomm.coreservices.core.constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseServiceLogger {
  private BaseServiceLogger() {
    //no-op
  }
  public static final Logger LOG = LoggerFactory.getLogger(BaseServiceLogger.class);
}
