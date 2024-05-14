package com.mattel.ecomm.coreservices.core.pojos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardsMembershipRequest extends BaseRequest {
    private String emailId;
    private String membershipId;

    @Override
    public String toString() {
        return "RewardsMembershipRequest [emailId=" + emailId + ", membership_Id=" + membershipId + "]";
    }
}
