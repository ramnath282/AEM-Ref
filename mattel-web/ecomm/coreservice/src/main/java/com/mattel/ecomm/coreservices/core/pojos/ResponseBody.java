package com.mattel.ecomm.coreservices.core.pojos;

import com.mattel.ecomm.coreservices.core.constants.Constant;

public class ResponseBody {
  private String content;
  private String contentType = Constant.CONTENT_TYPE_APPLICATION_JSON;
 
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public String getContentType() {
    return contentType;
  }
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  @Override
  public String toString() {
    return "ResponseBody [content=" + content + ", contentType=" + contentType + "]";
  }
}
