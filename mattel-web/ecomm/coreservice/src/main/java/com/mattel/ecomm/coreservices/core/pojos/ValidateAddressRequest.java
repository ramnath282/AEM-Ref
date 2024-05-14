package com.mattel.ecomm.coreservices.core.pojos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateAddressRequest extends BaseRequest {
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String organizationUnitName;
}
