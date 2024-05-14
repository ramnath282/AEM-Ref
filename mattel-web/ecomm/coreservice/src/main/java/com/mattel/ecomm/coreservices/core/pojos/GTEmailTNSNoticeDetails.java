package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GTEmailTNSNoticeDetails {

	    @JsonProperty
	    private String noticeName;
	    @JsonProperty
	    private String organizationId;
	    @JsonProperty
	    private String originatingSystemCode;
	    @JsonProperty
	    private String createDate;
	    @JsonProperty
	    private GTEmailTNSEventNoticeData eventNoticeData;
	    
}
