package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
public class RewardsMembershipResponse extends BaseResponse {
    @JsonProperty(value="membership_id")
    private String[] membershipId;
    private String[] emailId;
    private String firstName;
    private String lastName;
    private boolean membershipAccountFound;
    private String phone;
    private String userType;
    private String usersId;

    @Override
    public String toString() {
        return "RewardsMembershipResponse [membershipId=" + Arrays.toString(membershipId) + ", emailId="
                + Arrays.toString(emailId) + ", firstName=" + firstName + ", lastName=" + lastName
                + ", membershipAccountFound=" + membershipAccountFound + ", phone=" + phone + ", userType=" + userType
                + "]";
    }
}
