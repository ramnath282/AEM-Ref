package com.mattel.global.core.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsumerPreferences {

    @JsonProperty("PreferenceList")
    private List<PreferenceList> preferenceList;

    @Override public String toString() {
        return "ConsumerPreferences{" + "PreferenceList=" + preferenceList + '}';
    }
}
