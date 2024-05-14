package com.mattel.global.core.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
    @JsonProperty ("Status")
    Status status;

    @JsonIgnore
    private Map<String,String> responseHeaders;

    public String toString()
    {
        return "BaseResponse{" + "status" + status + "responseHeaders=" + responseHeaders + '}';
    }
}
