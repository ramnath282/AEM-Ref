package com.mattel.global.core.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {

    @JsonProperty("errorKey")
    private String errorKey;

    @JsonProperty("errorMessage")
    private String errorMessage;

    @JsonProperty("errorCode")
    private String errorCode;

    @Override
    public String toString() {
        return "errors{" + "errorKey='" + errorKey + '\'' + ", errorMessage='" + errorMessage + '\'' + ", errorCode='"
                + errorCode + '\'' + '}';
    }
}
