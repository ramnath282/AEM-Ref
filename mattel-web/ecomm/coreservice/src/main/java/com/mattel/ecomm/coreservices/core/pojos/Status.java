package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Status {

	@JsonProperty
	private String statusCode;

	@JsonProperty
	private String message;
	
	@Override
	public String toString() {
	  StringBuilder builder = new StringBuilder();
	  builder.append("Status [statusCode=");
	  builder.append(statusCode);
	  builder.append(", message=");
	  builder.append(message);
	  builder.append(", toString()=");
	  builder.append(super.toString());
	  builder.append("]");
	  return builder.toString();
	}

}
