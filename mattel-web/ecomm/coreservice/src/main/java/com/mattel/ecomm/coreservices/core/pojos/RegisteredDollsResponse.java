package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisteredDollsResponse extends BaseResponse {
    @JsonProperty
    private List<Doll> dolls;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RegisteredDollsResponse [dolls=");
        builder.append(dolls);
        builder.append("]");
        return builder.toString();
    }
}
