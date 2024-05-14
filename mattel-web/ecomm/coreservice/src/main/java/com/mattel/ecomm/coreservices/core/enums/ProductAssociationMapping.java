package com.mattel.ecomm.coreservices.core.enums;

import lombok.Getter;
import lombok.Setter;

public enum ProductAssociationMapping {
  ASSOCIATIONS("GIFTWRAP", "GIFTMSG", ProductAssociationMapping.QUICK_TYPE, "ADDONSERVICES", "COMPONENT");
  public static final String QUICK_TYPE = "QUICK";
  public static final String COMPONENT_TYPE = "COMPONENT";

  @Getter
  @Setter
  private String[] associationTypes;

  private ProductAssociationMapping(String... associationTypes) {
    this.setAssociationTypes(associationTypes);
  }
}
