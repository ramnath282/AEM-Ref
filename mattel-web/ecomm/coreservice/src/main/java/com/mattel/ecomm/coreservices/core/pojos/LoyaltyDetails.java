package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoyaltyDetails {
	@JsonProperty
	private String loyaltySegment;

	@JsonProperty
	private XMLGregorianCalendar endDate;

	@JsonProperty
	private String pdsErrorResponse;

	@JsonProperty
	private long loyaltyPoints;

	@JsonProperty
	private int loyaltySourceChannel;

	@JsonProperty
	private Date tierExpirationDate;

	@JsonProperty
	private String sourceAttributeID;

	@JsonProperty
	private String emailAddress;

	@JsonProperty
	private long loyaltyPointsToNextTier;

	@JsonProperty
	private String comment;

	@JsonProperty
	private String loyaltyStatus;

	@JsonProperty
	private XMLGregorianCalendar startDate;

	@JsonProperty
	private String syncStatus;

	@JsonProperty
	private int loyaltyNumber;

	@Override
	public String toString() {
		return "LoyaltyDetails [loyaltySegment : " + loyaltySegment + "endDate : " + endDate.toString() + "pdsErrorResponse : "
				+ pdsErrorResponse + "loyaltyPoints : " + loyaltyPoints + "loyaltySourceChannel : "
				+ loyaltySourceChannel + "tierExpirationDate : " + tierExpirationDate.toString()
				+ "sourceAttributeID : " + sourceAttributeID + "emailAddress : " + emailAddress
				+ "loyaltyPointsToNextTier : " + loyaltyPointsToNextTier + "comment : " + comment + "loyaltyStatus : "
				+ loyaltyStatus + "startDate : " + startDate.toString() + "syncStatus : " + syncStatus
				+ "loyaltyNumber : " + loyaltyNumber + "]";
	}
}
