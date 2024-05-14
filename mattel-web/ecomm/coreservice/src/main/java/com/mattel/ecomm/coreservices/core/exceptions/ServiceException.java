package com.mattel.ecomm.coreservices.core.exceptions;

import org.apache.commons.lang3.StringUtils;

import com.mattel.ecomm.coreservices.core.pojos.ResponseBody;

import lombok.Getter;

public class ServiceException extends Exception {
    @Getter
    private final String errorKey;

    @Getter
    private final String errorMessage;
    
    @Getter
    private final boolean propagateError;

    @Getter
    private final ResponseBody responseBody;    
    
    /**
     * Constructor.
     *
     * @param errorKey
     *          The Http status code or error code.
     * @param errorMessage
     *          The Http error message or custom error message
     * @param cause
     *          The exception cause
     */
    public ServiceException(String errorKey, String errorMessage, Throwable cause){
        super(errorMessage,cause);
        this.errorKey = errorKey;
        this.errorMessage= errorMessage;
        this.propagateError = false;
        this.responseBody = null;
    }
    
    /**
     * Constructor.
     *
     * @param errorKey
     *          The Http status code or error code.
     * @param errorMessage
     *          The Http error message or custom error message
     */
    public ServiceException(String errorKey, String errorMessage) {
        super(errorMessage);
        this.errorKey = errorKey;
        this.errorMessage = errorMessage;
        this.propagateError = false;
        this.responseBody = null;
    }
    
    /**
     * Constructor.
     *
     * @param errorKey
     *          The Http status code or error code.
     * @param errorMessage
     *          The Http error message or custom error message
     * @param propagateError
     *          Set true to propagate error to the caller.
     */
    public ServiceException(String errorKey, String errorMessage, boolean propagateError) {
      super(errorMessage);
      this.errorKey = errorKey;
      this.errorMessage = errorMessage;
      this.propagateError = propagateError;
      this.responseBody = null;
    }

    /**
     * Constructor.
     *
     * @param errorKey
     *          The Http status code or error code.
     * @param errorMessage
     *          The Http error message or custom error message
     * @param propagateError
     *          Set true to propagate error to the caller.
     * @param cause
     *          The exception cause
     */
    public ServiceException(String errorKey, String errorMessage, boolean propagateError, Throwable cause) {
      super(errorMessage,cause);
      this.errorKey = errorKey;
      this.errorMessage = errorMessage;
      this.propagateError = propagateError;
      this.responseBody = null;
    }

    /**
     * Constructor.
     *
     * @param errorKey
     *          The Http status code or error code.
     * @param responseBody
     *          The Http error response body.
     * @param propagateError
     *          Set true to propagate error to the caller.
     */
    public ServiceException(String errorKey, ResponseBody responseBody, boolean propogateError) {
      super(responseBody.getContent());
      this.errorKey = errorKey;
      this.errorMessage = StringUtils.EMPTY;
      this.propagateError = propogateError;
      this.responseBody = responseBody;
    }

    /**
     * Constructor.
     *
     * @param errorKey
     *          The Http status code or error code.
     * @param responseBody
     *          The Http error response body.
     * @param propagateError
     *          Set true to propagate error to the caller.
     * @param cause
     *          The exception cause
     */
    public ServiceException(String errorKey, ResponseBody responseBody, boolean propogateError, Throwable cause) {
      super(responseBody.getContent(),cause);
      this.errorKey = errorKey;
      this.errorMessage = StringUtils.EMPTY;
      this.propagateError = propogateError;
      this.responseBody = responseBody;
    }
}
