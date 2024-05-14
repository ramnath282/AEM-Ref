package com.mattel.ecomm.coreservices.core.pojos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Price {
  private String usage;
  private String description;
  private String currency;
  private String value;
}
