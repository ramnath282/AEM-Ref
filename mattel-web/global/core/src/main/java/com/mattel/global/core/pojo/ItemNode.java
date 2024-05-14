package com.mattel.global.core.pojo;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ItemNode {

    private String guid;
    private JsonNode jsonNode;
    private Map<String, String> properties;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((guid == null) ? 0 : guid.hashCode());
        return result;
    }

    public ItemNode(String partNumber, JsonNode jsonNode) {
        super();
        this.guid = partNumber;
        this.jsonNode = jsonNode;
        this.properties = new HashMap<>();
    }

    public String getGuid() {
        return guid;
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
        ItemNode other = (ItemNode) obj;
        if (guid == null) {
            if (other.guid != null)
                return false;
        } else if (!guid.equals(other.guid)) {
            return false;
        }
        return true;
    }

    public void setProperty(String key, String value) {
        properties.put(key, value);
    }

    public void setGuid(String guid) {
      this.guid = guid;
    }

    public void setJsonNode(JsonNode jsonNode) {
      this.jsonNode = jsonNode;
    }

    public void setProperties(Map<String, String> properties) {
      this.properties = properties;
    }

    @Override
    public String toString() {
        return "ProductJsonNode [guid=" + guid + ", jsonNode=" + jsonNode + ", properties="
                + properties + "]";
    }
}
