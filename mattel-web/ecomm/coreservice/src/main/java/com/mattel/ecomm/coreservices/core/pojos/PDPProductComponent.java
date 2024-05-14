package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonInclude(Include.NON_NULL)
public class PDPProductComponent extends ProductAssociation{
  @JsonProperty
  private String parentPartnumber;

    @JsonProperty
    private String language;
    
    @JsonProperty
    private String region;
    
    @JsonProperty
    private String name;
    
    @JsonProperty(value="RelatedSizingChart")
    private String relatedSizingChart;
    
    @JsonProperty
    private String isRetailInventoryCheckEnabled;

    @JsonProperty
    private String primaryBundleSku;
    
    @JsonProperty
    private Map<String, Price> price;
    
    @JsonProperty
    private List<ProductAssociation> associations;
    
    @JsonProperty
    private List<ChildProduct> childProducts;
    
    @JsonProperty
    private String affirmIneligible;
    
    @JsonProperty(value="seo_urlKeyword")
    private String seoUrlKeyword;

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("PDPProductComponent [parentPartnumber=");
      builder.append(parentPartnumber);
      builder.append(", relatedSizingChart=");
      builder.append(relatedSizingChart);
      builder.append(", language=");
      builder.append(language);
      builder.append(", region=");
      builder.append(region);
      builder.append(", name=");
      builder.append(name);
      builder.append(", isRetailInventoryCheckEnabled=");
      builder.append(isRetailInventoryCheckEnabled);
      builder.append(", primaryBundleSku=");
      builder.append(primaryBundleSku);
      builder.append(", price=");
      builder.append(price);
      builder.append(", associations=");
      builder.append(associations);
      builder.append(", childProducts=");
      builder.append(childProducts);
      builder.append(", affirmIneligible=");
      builder.append(affirmIneligible);
      builder.append(", seoUrlKeyword=");
      builder.append(seoUrlKeyword);
      builder.append(", toString()=");
      builder.append(super.toString());
      builder.append("]");
      return builder.toString();
    }
}
