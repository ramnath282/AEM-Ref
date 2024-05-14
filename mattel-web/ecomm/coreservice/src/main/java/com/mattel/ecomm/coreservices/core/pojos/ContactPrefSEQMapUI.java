package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author CTS
 *
 */
@Getter
@Setter
public class ContactPrefSEQMapUI {

	@JsonProperty("CONT_PREF_FRQ")
	private List<String> contPrefFrq;

	@JsonProperty("CONT_PREF_LOY")
	private List<Map<String, Object>> contPrefLoy;

	@JsonProperty("CONT_PREF_CAT")
	private List<Map<String, Object>> contPrefCat;

	@JsonProperty("CONT_PREF_CTL")
	private List<Map<String, Object>> contPrefCtl;

	@Override
	public String toString() {
		return "ContactPrefSEQMapUI [contPrefFrq=" + contPrefFrq + ", contPrefLoy=" + contPrefLoy + ", contPrefCat="
				+ contPrefCat + ", contPrefCtl=" + contPrefCtl + "]";
	}
}
