package com.mattel.ecomm.coreservices.core.enums;

public enum ErrorCodeMapping {

    CREDENTIALSMISMATCH(1003, "Login id or password is incorrect");

    private final int errorCode;
    private final String errorMessage;
     private ErrorCodeMapping(int errorCode, String errorMessage){
         this.errorCode = errorCode;
         this.errorMessage = errorMessage;
     }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
