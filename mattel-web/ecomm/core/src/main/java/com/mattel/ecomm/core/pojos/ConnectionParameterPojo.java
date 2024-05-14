package com.mattel.ecomm.core.pojos;

import org.json.JSONObject;

/**
 * @author CTS
 *
 */
public class ConnectionParameterPojo {

  private JSONObject requestHeaders;
  private String endPoint;
  private String methodType;
  private String domainUrl;

  public JSONObject getRequestHeaders() {
    return requestHeaders;
  }

  public void setRequestHeaders(JSONObject requestHeaders) {
    this.requestHeaders = requestHeaders;
  }

  public String getEndPoint() {
    return endPoint;
  }

  public void setEndPoint(String endPoint) {
    this.endPoint = endPoint;
  }

  public void setMethodType(String methodType) {
    this.methodType = methodType;
  }

  public String getMethodType() {
    return methodType;
  }

  public void setDomainUrl(String domainUrl) {
    this.domainUrl = domainUrl;
  }

  public String getDomainUrl() {
    return domainUrl;
  }

  @Override
  public String toString() {
    return "ConnectionParameterPojo [requestHeaders=" + requestHeaders + ", endPoint=" + endPoint
        + ", methodType=" + methodType + ", domainUrl=" + domainUrl + "]";
  }
}
