package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationRequest extends BaseRequest {
    private String firstName;
    private String lastName;
    private String storeId;
    private String catalogId;
    private String email1;
    private String logonId;
    private String logonIdVerify;
    private String logonPassword;
    private String logonPasswordVerify;
    private String phone1;
    @JsonProperty("birth_month")
    private String birthMonth;
    @JsonProperty("birth_date")
    private String birthDate;
    @JsonProperty("birth_year")
    private String birthYear;
    private String demographicField5;
    private String registrationFromRewards;
    private String sourceName;
    private String receiveEmail;
    private String usersId;

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", storeId='" + storeId + '\'' +
                ", catalogId='" + catalogId + '\'' +
                ", email1='" + email1 + '\'' +
                ", logonId='" + logonId + '\'' +
                ", logonIdVerify='" + logonIdVerify + '\'' +
                ", phone1='" + phone1 + '\'' +
                ", birthMonth='" + birthMonth + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", birthYear='" + birthYear + '\'' +
                ", demographicField5='" + demographicField5 + '\'' +
                ", registrationFromRewards='" + registrationFromRewards + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", receiveEmail='" + receiveEmail + '\'' +
                ", usersId='" + usersId + '\'' +
                '}';
    }
}
