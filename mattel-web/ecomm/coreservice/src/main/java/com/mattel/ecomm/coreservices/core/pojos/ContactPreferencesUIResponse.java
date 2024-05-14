package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * @author CTS
 *
 */
@Getter @Setter
public class ContactPreferencesUIResponse extends BaseResponse{

	private ContactPrefSEQMapUI contactPrefSEQMap;
	private String partyId;
	private String userContactPrefCTL;
	private List<Map<String, Object>> contactPrefGLList;
	private String loyaltyRequest;
	private String loyaltyStatus;
	private String userContactPrefSF;
	private String userExistingContactPref;
	private String userContactPrefFRQ;
	private String hash;
	private String userContactPrefRTL;
	private String loyaltyEmail;
	private String userLocationPrefSL;
	private String email;
	private String userContactPrefGL;
	private String userContactPrefLOY;
	private String userContactPrefCAT;
	
	@Override
	public String toString() {
		return "ContactPreferencesUIResponse [contactPrefSEQMap=" + contactPrefSEQMap + ", partyId=" + partyId
				+ ", userContactPrefCTL=" + userContactPrefCTL + ", contactPrefGLList=" + contactPrefGLList
				+ ", loyaltyRequest=" + loyaltyRequest + ", loyaltyStatus=" + loyaltyStatus + ", userContactPrefSF="
				+ userContactPrefSF + ", userExistingContactPref=" + userExistingContactPref + ", userContactPrefFRQ="
				+ userContactPrefFRQ + ", hash=" + hash + ", userContactPrefRTL=" + userContactPrefRTL
				+ ", loyaltyEmail=" + loyaltyEmail + ", userLocationPrefSL=" + userLocationPrefSL + ", email=" + email
				+ ", userContactPrefGL=" + userContactPrefGL + ", userContactPrefLOY=" + userContactPrefLOY
				+ ", userContactPrefCAT=" + userContactPrefCAT + "]";
	}
	
}
