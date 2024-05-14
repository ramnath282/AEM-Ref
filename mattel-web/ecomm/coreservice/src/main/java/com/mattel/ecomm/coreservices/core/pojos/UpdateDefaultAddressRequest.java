package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateDefaultAddressRequest extends AddDefaultAddressRequest {
  @JsonProperty("addressId")
  private String addressId;

  @Override
  public String toString() {
    return "UpdateDefaultAddressRequest [firstName=" + getFirstName() + ", lastName="
        + getLastName() + ", addressType=" + getAddressType() + ", zipCode=" + getZipCode()
        + ", addressLine=" + Arrays.toString(getAddressLine()) + ", country=" + getCountry()
        + ", phone1=" + getPhone1() + ", primary=" + getPrimary() + ", city=" + getCity()
        + ", state=" + getState() + ", addressField2=" + getAddressField2() + ", addressId="
        + addressId + ", organizationUnitName=" + getOrganizationUnitName() + ", billingCodeType="
        + getBillingCodeType() + "]";
  }

}
