package com.mattel.ecomm.coreservices.core.pojos;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class SearchAndPromoteRequest extends BaseRequest {
    private String searchString;
    private String category;

    @Override
    public String toString() {
        return "SearchAndPromoteRequest{" +
                "search='" + searchString + '\'' +
                ", catalogId='" + category + '\'' +
                '}';
    }
}
