package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsumerSegments {

	private List<Segment> segmentList;

	@Override
	public String toString() {
	  StringBuilder builder = new StringBuilder();
	  builder.append("ConsumerSegments [SegmentList=");
	  builder.append(segmentList);
	  builder.append(", toString()=");
	  builder.append(super.toString());
	  builder.append("]");
	  return builder.toString();
	}
	
}
