package com.mattel.ecomm.core.pojos.shopify;

import java.util.List;

/**
 * @author CTS
 *
 */
public class KitPojo {

  private List<SleepersPojo> sleepers;
  
  public List<SleepersPojo> getSleepers() {
	return sleepers;
  }

  public void setSleepers(List<SleepersPojo> sleepers) {
	this.sleepers = sleepers;
  }

@Override
  public String toString() {
    return "KitPojo [sleepers=" + sleepers + "]";
  }

}