
package com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for validateAddressRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="validateAddressRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="options" type="{http://www.mattel.com/esb/cdm/dtc/validateaddress/components1}optionsType" minOccurs="0"/>
 *         &lt;element name="rows" type="{http://www.mattel.com/esb/cdm/dtc/validateaddress/components1}rowsRequestType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateAddressRequestType", propOrder = {
    "options",
    "rows"
})
public class ValidateAddressRequestType {

    protected OptionsType options;
    @XmlElement(required = true)
    protected RowsRequestType rows;

    /**
     * Gets the value of the options property.
     * 
     * @return
     *     possible object is
     *     {@link OptionsType }
     *     
     */
    public OptionsType getOptions() {
        return options;
    }

    /**
     * Sets the value of the options property.
     * 
     * @param value
     *     allowed object is
     *     {@link OptionsType }
     *     
     */
    public void setOptions(OptionsType value) {
        this.options = value;
    }

    /**
     * Gets the value of the rows property.
     * 
     * @return
     *     possible object is
     *     {@link RowsRequestType }
     *     
     */
    public RowsRequestType getRows() {
        return rows;
    }

    /**
     * Sets the value of the rows property.
     * 
     * @param value
     *     allowed object is
     *     {@link RowsRequestType }
     *     
     */
    public void setRows(RowsRequestType value) {
        this.rows = value;
    }

}
