package com.mattel.ecomm.core.pojos;

public class ProductTypeTemplateConfigPojo {
    private String productType;
    private String pagePath;

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getPagePath() {
        return pagePath;
    }

    public String getProductType() {
        return productType;
    }

    @Override
    public String toString() {
        return "ProductTypeTemplateConfigPojo{" +
                "productType='" + productType + '\'' +
                ", pagePath='" + pagePath + '\'' +
                '}';
    }
}
