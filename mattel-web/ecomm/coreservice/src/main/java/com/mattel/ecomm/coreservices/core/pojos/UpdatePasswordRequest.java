package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdatePasswordRequest extends BaseRequest {
    @JsonProperty
    private String logonPassword;

    @JsonProperty
    private String logonPasswordVerify;

    @JsonProperty
    private String logonPasswordOld;

    @JsonProperty
    private String logonId;

    @Override
    public String toString() {
        return "UpdatePasswordRequest [logonPassword=" + logonPassword + ", logonPasswordVerify=" + logonPasswordVerify + ", logonPasswordOld=" + logonPasswordOld
                + ", logonId=" + logonId + "]";
    }

}