package com.mattel.ecomm.coreservices.core.pojos;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class DHTreatmentViewResponse extends BaseResponse{
	@JsonProperty
	private List<DHService> catalogEntryView;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DHTreatmentViewResponse [catalogEntryView=");
		builder.append(catalogEntryView);
		builder.append("]");
		return builder.toString();
	}
}
