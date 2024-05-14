package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class UpdateStoreAndProductInterestRequest extends BaseRequest{
    @JsonProperty("PROD_INTER_PI")
    private String[] productInterest;
    @JsonProperty("PROD_INTER_LO")
    private String[] locationInterest;
    @JsonProperty("selectCountry")
    private String selectCountry;
}
