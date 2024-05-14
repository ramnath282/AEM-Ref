package com.mattel.ecomm.coreservices.core.pojos;

import org.apache.sling.models.annotations.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalInfoRequest extends BaseRequest {
    @JsonProperty @Optional
    private String firstName;
    @JsonProperty @Optional
    private String lastName;
    @JsonProperty
    private String logonId;
    @JsonProperty @Optional
    private String logonPassword;
    @JsonProperty @Optional
    private String logonPasswordVerify;
    @JsonProperty @Optional
    private String email1;
    @JsonProperty @Optional
    private String phone1;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PersonalInfoRequest [firstName=");
        builder.append(firstName);
        builder.append(", lastName=");
        builder.append(lastName);
        builder.append(", logonId=");
        builder.append(logonId);
        builder.append(", logonPassword=");
        builder.append(logonPassword);
        builder.append(", logonPasswordVerify=");
        builder.append(logonPasswordVerify);
        builder.append(", email1=");
        builder.append(email1);
        builder.append(", phone1=");
        builder.append(phone1);
        builder.append("]");
        return builder.toString();
    }
}
