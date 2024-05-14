package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChildInformationRequest extends ChildInformation {
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        builder.append("Child Information request [addressId =");
        builder.append(getAddressId());
        builder.append(", addressType=");
        builder.append(getAddressType());
        builder.append(", dateOfBirth=");
        builder.append(getDateOfBirth());
        builder.append(", firstName=");
        builder.append(getFirstName());
        builder.append(", gender=");
        builder.append(getGender());
        builder.append(", relationship=");
        builder.append(getRelationship());
        builder.append("]");
        return builder.toString();
    }
}
