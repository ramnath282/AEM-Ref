package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Optional;

public class BaseRequest  {
  String langId = "";

  public String getLangId() {
    return langId;
  }

  public void setLangId(String langId) {
    this.langId = Optional.ofNullable(langId).orElse("");
  }
}
