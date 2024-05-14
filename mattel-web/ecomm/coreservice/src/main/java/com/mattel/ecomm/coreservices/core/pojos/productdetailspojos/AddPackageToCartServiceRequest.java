package com.mattel.ecomm.coreservices.core.pojos.productdetailspojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddPackageToCartServiceRequest extends BaseRequest{

    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("inventoryValidation")
    private String inventoryValidation;
    @JsonProperty("calculateOrder")
    private String calculateOrder;
    @JsonProperty("calculationUsage")
    private String calculationUsage;
    @JsonProperty("parentPartnumberList")
    private Map<String, String> parentPartnumberList;
    @JsonProperty("childPartnumberList")
    private Map<String, String> childPartnumberList;
    @JsonProperty("qty_1")
    private String qty1;

    @Override
    public String toString() {
      return "AddPackageToCartServiceRequest [orderId=" + orderId + ", inventoryValidation="
          + inventoryValidation + ", calculateOrder=" + calculateOrder + ", calculationUsage="
          + calculationUsage + ", parentPartnumberList=" + parentPartnumberList
          + ", childPartnumberList=" + childPartnumberList + ", qty1=" + qty1 + "]";
    }
}
