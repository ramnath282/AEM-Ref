package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author CTS
 *
 */
@Getter
@Setter
public class ContactPreferencesResponse extends BaseResponse {

	private ContactPrefSEQMap contactPrefSEQMap;
	private String partyId;
	private String userContactPrefCTL;
	private List<String> contactPrefGLList;
	private String loyaltyRequest;
	private String userContactPrefSF;
	private String userExistingContactPref;
	private String userContactPrefFRQ;
	private String hash;
	private String userContactPrefRTL;
	private String loyaltyEmail;
	private String loyaltyStatus;
	private String userLocationPrefSL;
	private String email;
	private String userContactPrefGL;
	private String userContactPrefLOY;
	private String userContactPrefCAT;
	
	@Override
	public String toString() {
		return "ContactPreferencesResponse [contactPrefSEQMap=" + contactPrefSEQMap + ", partyId=" + partyId
				+ ", userContactPrefCTL=" + userContactPrefCTL + ", contactPrefGLList=" + contactPrefGLList
				+ ", loyaltyRequest=" + loyaltyRequest + ", userContactPrefSF=" + userContactPrefSF
				+ ", userExistingContactPref=" + userExistingContactPref + ", userContactPrefFRQ=" + userContactPrefFRQ
				+ ", hash=" + hash + ", userContactPrefRTL=" + userContactPrefRTL + ", loyaltyEmail=" + loyaltyEmail
				+ ", loyaltyStatus=" + loyaltyStatus + ", userLocationPrefSL=" + userLocationPrefSL + ", email=" + email
				+ ", userContactPrefGL=" + userContactPrefGL + ", userContactPrefLOY=" + userContactPrefLOY
				+ ", userContactPrefCAT=" + userContactPrefCAT + "]";
	}
}
