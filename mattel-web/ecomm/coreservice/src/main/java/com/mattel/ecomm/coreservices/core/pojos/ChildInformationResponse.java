package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChildInformationResponse extends BaseResponse {

    @JsonProperty("children")
    private List<ChildInformation> childInformation;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ChildInformationResponse [childInformation=");
        builder.append(childInformation);
        builder.append("]");
        return builder.toString();
    }
}

