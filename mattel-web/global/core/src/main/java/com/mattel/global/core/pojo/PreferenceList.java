package com.mattel.global.core.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PreferenceList {
    @JsonProperty("PreferenceID")
    private String preferenceID;

    @JsonProperty("PreferenceOptIn")
    private String preferenceOptIn;

    @JsonProperty("DoubleOptInStatusCD")
    private String doubleOptInStatusCD;

    @JsonProperty("PreferenceType")
    private String preferenceType;

    @JsonProperty("PreferenceName")
    private String preferenceName;

    @JsonProperty("PreferenceCRDTTM")
    private String prefernceCRDTTM;

    @JsonProperty("PreferenceUPDTTM")
    private String preferenceUPDTTM;

    public String toString() {
        return "PreferenceList{" + "preferenceID='" + preferenceID + '\'' + ", preferenceOptIn='"
            + preferenceOptIn + '\'' + ",doubleOptInStatusCD='" +doubleOptInStatusCD + '\''
            + ", preferenceType='" + preferenceType + '\'' + ", preferenceName='" + preferenceName
            + '\'' + ", preferenceCRDTTM='" + prefernceCRDTTM + '\'' + ", preferenceUPDTTM='"
            + preferenceUPDTTM + '\'' + '}';
    }
}
