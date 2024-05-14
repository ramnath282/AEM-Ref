package com.mattel.ecomm.coreservices.core.pojos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"orderId",
"orderItem"
})
public class DHAddCartResponse extends BaseResponse{

@JsonProperty("orderId")
private String orderId;
@JsonProperty("orderItem")
private List<DHOrderItem> orderItem = null;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<>();

@JsonProperty("orderId")
public String getOrderId() {
return orderId;
}

@JsonProperty("orderId")
public void setOrderId(String orderId) {
this.orderId = orderId;
}

@JsonProperty("orderItem")
public List<DHOrderItem> getOrderItem() {
return orderItem;
}

@JsonProperty("orderItem")
public void setOrderItem(List<DHOrderItem> orderItem) {
this.orderItem = orderItem;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}