package com.mattel.ecomm.coreservices.core.pojos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DHAddToCartRequest extends BaseRequest {

    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("orderItem")
    private List<DHOrderItemRequest> orderItem = null;
    @JsonProperty("x_inventoryValidation")
    private String xInventoryValidation;
    @JsonProperty("aosItemPartNumber")
    private List<String> aosItemPartNumber = null;
    @JsonProperty("calculationUsage")
    private String calculationUsage;
    @JsonProperty("addFlag")
    private String addFlag;
    @JsonProperty("updateFlag")
    private String updateFlag;
    @JsonProperty("aosProdPartNumber")
    private List<String> aosProdPartNumber = null;
    @JsonProperty("calculateOrder")
    private String calculateOrder;
    @JsonProperty("retailStoreId")
    private String retailStoreId;
    @JsonProperty("notes")
    private List<Map<String, String>> notes;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @Override
    public String toString() {
        return "DHAddToCartRequest [orderId=" + orderId + ", orderItem=" + orderItem + ", xInventoryValidation="
                + xInventoryValidation + ", aosItemPartNumber=" + aosItemPartNumber + ", calculationUsage="
                + calculationUsage + ", addFlag=" + addFlag + ", updateFlag=" + updateFlag + ", aosProdPartNumber="
                + aosProdPartNumber + ", calculateOrder=" + calculateOrder + ", retailStoreId=" + retailStoreId
                + ", notes=" + notes + ", additionalProperties=" + additionalProperties + "]";
    }
}