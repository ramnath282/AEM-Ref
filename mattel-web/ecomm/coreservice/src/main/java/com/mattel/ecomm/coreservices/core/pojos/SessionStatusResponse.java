package com.mattel.ecomm.coreservices.core.pojos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionStatusResponse extends BaseResponse {
    private String status;
    private String type;

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("SessionStatusResponse [status=");
      builder.append(status);
      builder.append(", type=");
      builder.append(type);
      builder.append("]");
      return builder.toString();
    }
}
