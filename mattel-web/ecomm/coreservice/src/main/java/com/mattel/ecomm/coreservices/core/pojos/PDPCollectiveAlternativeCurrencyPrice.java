package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author CTS
 *
 */
@Getter
@Setter
public class PDPCollectiveAlternativeCurrencyPrice {
  @JsonProperty
  private Map<String, String> price;
}
