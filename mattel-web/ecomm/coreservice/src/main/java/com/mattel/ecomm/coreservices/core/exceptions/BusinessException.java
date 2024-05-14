package com.mattel.ecomm.coreservices.core.exceptions;

import com.mattel.ecomm.coreservices.core.enums.ErrorCodeMapping;
import lombok.Getter;

public class BusinessException extends Exception{
    @Getter
    private final int errorCode;
    @Getter
    private final String errorMessage;

    public BusinessException(ErrorCodeMapping errorCodeMapping) {
        super(errorCodeMapping.getErrorMessage());
        this.errorCode = errorCodeMapping.getErrorCode();
        this.errorMessage = errorCodeMapping.getErrorMessage();
    }

}
