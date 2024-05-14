package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ProductAttributes {

	@JsonProperty
	private Map<String, Object> descripitiveAttributes;
	@JsonProperty
	private Map<String, Object> definingAttributes;

	 @Override
	  public String toString() {
	    final StringBuilder builder = new StringBuilder();
	    builder.append("ProductAttributes [descripitiveAttributes=");
	    builder.append(descripitiveAttributes);
	    builder.append(", definingAttributes=");
	    builder.append(definingAttributes);
	    builder.append("]");
	    return builder.toString();
	  }
}
