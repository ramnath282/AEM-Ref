package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryCheckRequest extends BaseRequest {
  private List<String> partNumbers;
}
