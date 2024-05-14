package com.mattel.ecomm.coreservices.core.pojos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDataImporterResponse extends BaseResponse {
    private String filePath;
    private Boolean status;
}
