package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddTrunkToCartRequest extends BaseRequest {
    
    @JsonProperty
    private List<GTAddToBagItem> components;
    @JsonProperty
    private String partNumber;
    @JsonProperty
    private String quantity; 
    @JsonProperty(value="FromGT")
    private String fromGT;
    @JsonProperty(value="RetailStoreId")
    private String retailStoreId;
    @JsonProperty(value="ImageURL")
    private String imageURL;
    @JsonProperty(value="PrintImageURL")
    private String printImageURL;
    @JsonProperty(value="DescriptionData")
    private String descriptionData;
}
