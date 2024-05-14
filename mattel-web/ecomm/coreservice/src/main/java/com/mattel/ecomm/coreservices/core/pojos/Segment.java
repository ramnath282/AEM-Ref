package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Segment {

	@JsonProperty("SegmentID")
	private String segmentID;

	@JsonProperty("SegmentOptIn")
	private String segmentOptIn;

	@JsonProperty("SegmentType")
	private String segmentType;

	@JsonProperty("SegmentName")
	private String segmentName;
	
	@JsonProperty("SegmentCode")
	private String segmentCode;
	
	@JsonProperty("StartDate")
	private String startDate;

	@JsonProperty("EndDate")
	private String endDate;

	@Override
	public String toString() {
	  StringBuilder builder = new StringBuilder();
	  builder.append("Segment [SegmentID=");
	  builder.append(segmentID);
	  builder.append(", SegmentOptIn=");
	  builder.append(segmentOptIn);
	  builder.append(", SegmentType=");
	  builder.append(segmentType);
	  builder.append(", SegmentName=");
	  builder.append(segmentName);
	  builder.append(", SegmentCode=");
	  builder.append(segmentCode);
	  builder.append(", StartDate=");
	  builder.append(startDate);
	  builder.append(", EndDate=");
	  builder.append(endDate);
	  builder.append(", toString()=");
	  builder.append(super.toString());
	  builder.append("]");
	  return builder.toString();
	}
	
}
