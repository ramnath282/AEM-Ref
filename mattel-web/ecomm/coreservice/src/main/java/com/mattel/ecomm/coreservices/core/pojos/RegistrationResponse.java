package com.mattel.ecomm.coreservices.core.pojos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationResponse extends BaseResponse {
    private String viewTaskName;
    private String userId;
    private String addressId;

    @Override
    public String toString() {
        return "RegistrationResponse [viewTaskName=" + viewTaskName + ", userId=" + userId + ", addressId=" + addressId
                + "]";
    }
}
