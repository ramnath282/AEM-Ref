package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;


public class GiftCardMessageValidity extends BaseResponse {
    @JsonProperty("isNameValid")
    private boolean isNameValid;

    public boolean isIsNameValid() {
        return isNameValid;
    }

    public void setIsNameValid(boolean isNameValid) {
        this.isNameValid = isNameValid;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("GiftCardMessageValidity [isNameValid=");
      builder.append(isNameValid);
      builder.append("]");
      return builder.toString();
    }
}
