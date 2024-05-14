package com.mattel.ecomm.coreservices.core.pojos;

import com.mattel.ecomm.coreservices.core.interfaces.UsableShippingMode;

import lombok.Getter;
import lombok.Setter;

public class UsableShippingModePojo implements UsableShippingMode {
	
	@Setter @Getter
    private String field1;
	
	@Setter @Getter
    private String shipModeCode;
	
	@Setter @Getter
    private String shipModeDescription;
	
	@Setter @Getter
    private String shipModeId;
	
	@Setter @Getter
    private String field2;
	
	
}
