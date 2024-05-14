package com.mattel.ecomm.coreservices.core.pojos.productdetailspojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.CartOrderItem;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddItemToCartRequest extends BaseRequest {
  @JsonProperty
  private String orderId;
  /**
   * Single entry in {@link List} of {@link CartOrderItem} for product of type ItemBean.
   * Multiple entry in {@link List} of {@link CartOrderItem} for product of type BundleBean.
   */
  @JsonProperty
  private List<CartOrderItem> orderItem;
  @JsonProperty("inventoryValidation")
  private String inventoryValidation;
  @JsonProperty("calculateOrder")
  private String calculateOrder;
  @JsonProperty("calculationUsage")
  private String calculationUsage;
}
