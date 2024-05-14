package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateDefaultShippingRequest extends BaseRequest {

    @JsonProperty("shipping_modeId")
    private String shippingModeId;

    @Override
    public String toString() {
        return "UpdateDefaultShippingRequest [shipping_modeId=" + shippingModeId + "]";
    }
}
