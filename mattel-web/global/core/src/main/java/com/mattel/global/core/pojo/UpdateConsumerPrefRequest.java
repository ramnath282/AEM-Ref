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
public class UpdateConsumerPrefRequest {
    @JsonProperty("EmailAddress")
    private String emailAddress;

    @JsonProperty("RequestedBy")
    private String requestedBy;

    @JsonProperty("consumerPreferences")
    private ConsumerPreferences consumerPreferences;

    @Override public String toString() {
        return "EmailAddress=" + emailAddress + '\'' + ", requestedBy=" + requestedBy + '\''
            + ", consumerPreferences=" + consumerPreferences + '}';
    }
}
