package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChildInformation extends BaseRequest {

    @JsonProperty
    private String addressId;

    @JsonProperty
    private String addressType;

    @JsonProperty
    private String dateOfBirth;

    @JsonProperty
    private String firstName;

    @JsonProperty
    private String gender;

    @JsonProperty
    private String lastName;

    @JsonProperty
    private String nickName;

    @JsonProperty
    private String relationship;

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        builder.append("Child Information [addressId =");
        builder.append(addressId);
        builder.append(", addressType=");
        builder.append(addressType);
        builder.append(", dateOfBirth=");
        builder.append(dateOfBirth);
        builder.append(", firstName=");
        builder.append(firstName);
        builder.append(", lastName=");
        builder.append(lastName);
        builder.append(", gender=");
        builder.append(gender);
        builder.append(", nickName=");
        builder.append(nickName);
        builder.append(", relationship=");
        builder.append(relationship);
        builder.append("]");
        return builder.toString();
    }
}
