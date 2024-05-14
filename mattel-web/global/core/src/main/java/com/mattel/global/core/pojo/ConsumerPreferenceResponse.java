package com.mattel.global.core.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsumerPreferenceResponse extends BaseResponse{
    @JsonProperty("consumerPreferences")
    private ConsumerPreferences consumerPreferences;

    public String toString() {
        return "ConsumerPreferenceResponse{" + "consumerPreferences=" + consumerPreferences
            + ", status=" + status + '}';
    }
}
