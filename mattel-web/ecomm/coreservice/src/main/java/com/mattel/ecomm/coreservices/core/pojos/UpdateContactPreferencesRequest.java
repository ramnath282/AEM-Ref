package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class UpdateContactPreferencesRequest extends BaseRequest {
	private String storeId;
	private String catalogId;
	private String partyId;
	private String email;
	private String hash;
	@JsonProperty("URL")
	private String url;
	private String sourceName;
	private String loyaltyEmail;
	private String loyaltyRequest;

	@JsonProperty("CONT_PREF_GL")
	private String contPrefGL;

	@JsonProperty("CONT_PREF_CAT")
	private List<String> contPrefCAT;

	@JsonProperty("CONT_PREF_LOY")
	private List<String> contPrefLOY;

	@JsonProperty("CONT_PREF_CTL")
	private List<String> contPrefCTL;

	@Override
	public String toString() {
		return "UpdateContactPreferencesRequest [storeId=" + storeId + ", catalogId=" + catalogId + ", partyId="
				+ partyId + ", email=" + email + ", hash=" + hash + ", url=" + url + ", sourceName=" + sourceName
				+ ", loyaltyEmail=" + loyaltyEmail + ", loyaltyRequest=" + loyaltyRequest + ", langId=" + langId
				+ ", contPrefGL=" + contPrefGL + ", contPrefCAT=" + contPrefCAT + ", contPrefLOY=" + contPrefLOY
				+ ", contPrefCTL=" + contPrefCTL + "]";
	}

}
