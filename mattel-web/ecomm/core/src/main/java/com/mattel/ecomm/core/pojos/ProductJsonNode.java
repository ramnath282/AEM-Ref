package com.mattel.ecomm.core.pojos;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author CTS
 *
 */
public class ProductJsonNode {
  private String partNumber;
  private JsonNode jsonNode;
  private Map<String, String> properties;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((partNumber == null) ? 0 : partNumber.hashCode());
    return result;
  }

  public ProductJsonNode(String partNumber, JsonNode jsonNode) {
    super();
    this.partNumber = partNumber;
    this.jsonNode = jsonNode;
    this.properties = new HashMap<>();
  }

  public String getPartNumber() {
    return partNumber;
  }

  public JsonNode getJsonNode() {
    return jsonNode;
  }

  public Map<String, String> getProperties() {
    return Collections.unmodifiableMap(properties);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ProductJsonNode other = (ProductJsonNode) obj;
    if (partNumber == null) {
      if (other.partNumber != null)
        return false;
    } else if (!partNumber.equals(other.partNumber)) {
      return false;
    }
    return true;
  }

  public void setProperty(String key, String value) {
    properties.put(key, value);
  }

  @Override
  public String toString() {
    return "ProductJsonNode [partNumber=" + partNumber + ", jsonNode=" + jsonNode + ", properties="
        + properties + "]";
  }
}
