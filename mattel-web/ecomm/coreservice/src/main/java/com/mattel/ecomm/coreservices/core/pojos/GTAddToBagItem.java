package com.mattel.ecomm.coreservices.core.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GTAddToBagItem {
        
    @JsonProperty("SKU")
    private String sku;
    @JsonProperty("Instructions")
    private String instructions;
    @JsonProperty("GIFT_TRUNK_LETTER_TEXT")
    private String giftTrunkLetterText;
	@JsonProperty("GIFT_TRUNK_LETTER_EDITED")
    private String giftTrunkLetterEdited;
	@JsonProperty("GIFT_TRUNK_RECIPIENT")
    private String giftTrunkRecipient;
	
	@Override
	  public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("GTAddToBagItem [SKU=");
	    builder.append(sku);
	    builder.append(", Instructions=");
	    builder.append(instructions);
	    builder.append(", GIFT_TRUNK_LETTER_TEXT=");
	    builder.append(giftTrunkLetterText);
	    builder.append(", GIFT_TRUNK_LETTER_EDITED=");
	    builder.append(giftTrunkLetterEdited);
	    builder.append(", GIFT_TRUNK_RECIPIENT=");
	    builder.append(giftTrunkRecipient);
	    builder.append("]"); 
	    return builder.toString();
		
	  }
    
}
