package com.mattel.ecomm.coreservices.core.pojos.productdetailspojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddAddOnServiceRequest extends BaseRequest {
    @JsonProperty
    private String numAddOnServicesItems;
    @JsonProperty
    private String numAddOnServicesProducts;
    @JsonProperty
    private String parentPartNumber;
    @JsonProperty
    private String productoperationType;
    @JsonProperty ("URL")
    private String url;
    @JsonProperty
    private String storeId;
    @JsonProperty
    private String catalogId;
    @JsonProperty
    private List<String> aosItemPartNumber;
    @JsonProperty
    private String aosItemQuantitytoBeAddedArray;
    @JsonProperty
    private String parentQuantity;
    @JsonProperty
    private String parCatentryId;
    @JsonProperty
    private String parentOrderItemId;
    @JsonProperty
    private String sourcePage;
    @JsonProperty
    private String mattelOrderType;
    @JsonProperty
    private String calculationUsage;
    @JsonProperty
    private String addFlag;
    @JsonProperty
    private String updateFlag;
    @JsonProperty
    private String calculateOrder;
    @JsonProperty
    private List<String> aosProdPartNumber;
    @JsonProperty
    private String aosProdQuantitytoBeAddedArray;
}
