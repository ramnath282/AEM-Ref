package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author CTS
 *
 */
@Getter
@Setter
public class ContactPrefSEQMap {

	@JsonProperty("CONT_PREF_FRQ")
	private List<String> contPrefFrq;

	@JsonProperty("CONT_PREF_LOY")
	private List<String> contPrefLoy;

	@JsonProperty("CONT_PREF_CAT")
	private List<String> contPrefCat;

	@JsonProperty("CONT_PREF_CTL")
	private List<String> contPrefCtl;

	@Override
	public String toString() {
		return "ContactPrefSEQMap [contPrefFrq=" + contPrefFrq + ", contPrefLoy=" + contPrefLoy + ", contPrefCat="
				+ contPrefCat + ", contPrefCtl=" + contPrefCtl + "]";
	}
}
