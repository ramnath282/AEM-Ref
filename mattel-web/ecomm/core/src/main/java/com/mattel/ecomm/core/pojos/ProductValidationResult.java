package com.mattel.ecomm.core.pojos;

import java.util.ArrayList;
import java.util.List;

public class ProductValidationResult {
  private String partnumber;
  private String startDesc;
  private String endDesc;
  private String productType;
  private final List<String> errors = new ArrayList<>();
  private final List<String> warnings = new ArrayList<>();
  private final List<String> allmessages = new ArrayList<>();

  public void addError(String message) {
    errors.add(message);
    allmessages.add(message);
  }

  public void addMessage(String message) {
    allmessages.add(message);
  }

  public void removeMessage(int index) {
    allmessages.remove(index);
  }

  public int msgsize() {
    return allmessages.size();
  }

  public boolean isError() {
    return !errors.isEmpty();
  }

  public boolean isWarning() {
    return !warnings.isEmpty();
  }

  public void addWarning(String message) {
    warnings.add(message);
    allmessages.add(message);
  }

  public String getPartnumber() {
    return partnumber;
  }

  public void setPartnumber(String partnumber) {
    this.partnumber = partnumber;
  }

  public List<String> getErrors() {
    return errors;
  }

  public List<String> getWarnings() {
    return warnings;
  }

  public List<String> getAllmessages() {
    return allmessages;
  }

  public String getStartDesc() {
    return startDesc;
  }

  public void setStartDesc(String startDesc) {
    this.startDesc = startDesc;
  }

  public String getEndDesc() {
    return endDesc;
  }

  public void setEndDesc(String endDesc) {
    this.endDesc = endDesc;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }
}
