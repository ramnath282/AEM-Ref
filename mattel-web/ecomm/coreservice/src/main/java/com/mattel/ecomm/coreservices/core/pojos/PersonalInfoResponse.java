package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonalInfoResponse extends BaseResponse {
	
	@JsonProperty
	private String lastName;
	
	@JsonProperty
	private String registrationStatus;

	@JsonProperty
	private String resourceId;
	
	@JsonProperty
	private String preferredCurrency;
	
	@JsonProperty
	private String distinguishedName;
	
	@JsonProperty
	private String orgizationId;
	
	@JsonProperty
	private String addressId;
	
	@JsonProperty
	private String phone1;
	
	@JsonProperty
	private String accountStatus;
	
	@JsonProperty
	private String email1;
	
	@JsonProperty
	private String profileType;
	
	@JsonProperty
	private List<Contact> contact;
	
	@JsonProperty
	private String challengeQuestion;
	
	@JsonProperty
	private String nickName;
	
	@JsonProperty
	private String addressType;
	
	@JsonProperty
	private String resourceName;
	
	@JsonProperty
	private String userId;
	
	@JsonProperty
	private String registrationDateTime;
	
	@JsonProperty
	private String organizationDistinguishedName;
	
	@JsonProperty
	private String firstName;
	
	@JsonProperty
	private String logonId;
	
	@JsonProperty
	private List<ReceivePreference> receiveEmailPreference;
	
	@JsonProperty
	private String lastUpdate;
	
	@JsonProperty
	private String registrationApprovalStatus;
	
	@JsonProperty
	private List<Attribute> attributes;
	
	@JsonProperty
	private String passwordExpired;
	
	@JsonProperty
	private String primary;
	
	@JsonProperty
	private String defaultShipMethod;
	
	@JsonProperty
	private List<ReceivePreference> receiveSMSPreference;
	

}


