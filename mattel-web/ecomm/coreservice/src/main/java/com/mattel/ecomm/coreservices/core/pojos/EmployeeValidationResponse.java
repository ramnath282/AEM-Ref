package com.mattel.ecomm.coreservices.core.pojos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmployeeValidationResponse extends BaseResponse {

    private String redirecturl;
    private String catalogId;
    private String viewTaskName;
    private String storeId;
    private String pageId;
    private String isErrorPresent;
    
}
