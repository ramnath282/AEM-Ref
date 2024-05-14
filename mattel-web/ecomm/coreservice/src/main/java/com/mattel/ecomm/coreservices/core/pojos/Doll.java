package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * POJO for holding doll details.
 */
@Getter
@Setter
public class Doll {
    @JsonProperty
    private String boughtFor;
    @JsonProperty
    private String character;
    @JsonProperty
    private String productID;
    @JsonProperty
    private String purchasedDate;
    @JsonProperty
    private String reason;
    @JsonProperty
    private String store;

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        builder.append("Doll [boughtFor=");
        builder.append(boughtFor);
        builder.append(", character=");
        builder.append(character);
        builder.append(", productID=");
        builder.append(productID);
        builder.append(", purchasedDate=");
        builder.append(purchasedDate);
        builder.append(", reason=");
        builder.append(reason);
        builder.append(", store=");
        builder.append(store);
        builder.append("]");
        return builder.toString();
    }
}
