package com.mattel.ecomm.coreservices.core.enums;

import lombok.Getter;
import lombok.Setter;

public enum ServiceCookieMapping {

  DEFAULT("JSESSIONID", "WC_ACTIVEPOINTER", "WC_PERSISTENT", "WC_SESSION_ESTABLISHED",
      "WC_USERACTIVITY", "WC_AUTHENTICATION", "MATTEL_WELCOME_MSG", //
      // Adding additional cookies to default list as per service team suggestion(AEMECOM-7982,7877)
      "WC_SHOW_USER_ACTIVATION", //
      "WC_GENERIC_ACTIVITYDATA", //
      "MATTEL_REMEMBER_ME", //
      "MATTEL_CUSTOMER_SEGMENT", //
      "EmployeeSegment","CMID"), //
  LOGOUT("WC_GENERIC_ACTIVITYDATA"), //
  MINICART("MATTEL_CUSTOMER_SEGMENT", "MATTEL_CART_QUANTITY"),
  SHOPIFY_FLOW("SHOPIFY_FLOW");

  @Getter
  @Setter
  private String[] cookieNames;

  private ServiceCookieMapping(String... cookieNames) {
    this.setCookieNames(cookieNames);
  }

}
