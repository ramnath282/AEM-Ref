package com.mattel.ecomm.core.pojos;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mattel.ecomm.coreservices.core.pojos.BaseDHService;

/**
 * @author CTS
 *
 */
public class DHTreatmentServiceUIResponse extends BaseDHService {
	
	@JsonProperty
	private String name;
	@JsonProperty
	private String partNumber;
	@JsonProperty
	private String catalogEntryTypeCode;
	@JsonProperty
	private Map<String, Object> attributes;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the partNumber
	 */
	public String getPartNumber() {
		return partNumber;
	}

	/**
	 * @param partNumber the partNumber to set
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	/**
	 * @return the catalogEntryTypeCode
	 */
	public String getCatalogEntryTypeCode() {
		return catalogEntryTypeCode;
	}

	/**
	 * @param catalogEntryTypeCode the catalogEntryTypeCode to set
	 */
	public void setCatalogEntryTypeCode(String catalogEntryTypeCode) {
		this.catalogEntryTypeCode = catalogEntryTypeCode;
	}

	/**
	 * @return the attributes
	 */
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
}
