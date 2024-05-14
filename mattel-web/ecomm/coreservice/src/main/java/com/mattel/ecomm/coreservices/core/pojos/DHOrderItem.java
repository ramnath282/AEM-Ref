package com.mattel.ecomm.coreservices.core.pojos;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"orderItemId",
"partNumber"
})
public class DHOrderItem {

@JsonProperty("orderItemId")
private String orderItemId;
@JsonProperty("partNumber")
private String partNumber;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<>();

@JsonProperty("orderItemId")
public String getOrderItemId() {
return orderItemId;
}

@JsonProperty("orderItemId")
public void setOrderItemId(String orderItemId) {
this.orderItemId = orderItemId;
}

@JsonProperty("partNumber")
public String getPartNumber() {
return partNumber;
}

@JsonProperty("partNumber")
public void setPartNumber(String partNumber) {
this.partNumber = partNumber;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

@Override
public String toString() {
    return "DHOrderItem [orderItemId=" + orderItemId + ", partNumber=" + partNumber + ", additionalProperties="
            + additionalProperties + "]";
}



}