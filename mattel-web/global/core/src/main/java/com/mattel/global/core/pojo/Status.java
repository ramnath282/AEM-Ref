package com.mattel.global.core.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Status {
    private String statusCode;
    private String message;

    public String toString() {
        return "Status{" + "statusCode='" + statusCode + '\'' + ", message='" + message + '\''
            + '}';
    }
}
