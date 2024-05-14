
package com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rowRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rowRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}addressLine1" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}addressLine2" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}addressLine3" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}addressLine4" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/common/core1}city" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}stateProvince" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/common/core1}postalCode" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/common/core1}country" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}firmName" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USUrbanName" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canLanguage" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/components1}user_fields" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rowRequestType", propOrder = {
    "addressLine1",
    "addressLine2",
    "addressLine3",
    "addressLine4",
    "city",
    "stateProvince",
    "postalCode",
    "country",
    "firmName",
    "usUrbanName",
    "canLanguage",
    "userFields"
})
public class RowRequestType {

    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String addressLine1;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String addressLine2;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String addressLine3;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String addressLine4;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/common/core1")
    protected String city;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String stateProvince;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/common/core1")
    protected String postalCode;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/common/core1")
    protected String country;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String firmName;
    @XmlElement(name = "USUrbanName", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usUrbanName;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canLanguage;
    @XmlElement(name = "user_fields")
    protected UserFieldsType userFields;

    /**
     * Gets the value of the addressLine1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * Sets the value of the addressLine1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine1(String value) {
        this.addressLine1 = value;
    }

    /**
     * Gets the value of the addressLine2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * Sets the value of the addressLine2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine2(String value) {
        this.addressLine2 = value;
    }

    /**
     * Gets the value of the addressLine3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine3() {
        return addressLine3;
    }

    /**
     * Sets the value of the addressLine3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine3(String value) {
        this.addressLine3 = value;
    }

    /**
     * Gets the value of the addressLine4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine4() {
        return addressLine4;
    }

    /**
     * Sets the value of the addressLine4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine4(String value) {
        this.addressLine4 = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the stateProvince property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateProvince() {
        return stateProvince;
    }

    /**
     * Sets the value of the stateProvince property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateProvince(String value) {
        this.stateProvince = value;
    }

    /**
     * Gets the value of the postalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the firmName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirmName() {
        return firmName;
    }

    /**
     * Sets the value of the firmName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirmName(String value) {
        this.firmName = value;
    }

    /**
     * Gets the value of the usUrbanName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSUrbanName() {
        return usUrbanName;
    }

    /**
     * Sets the value of the usUrbanName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSUrbanName(String value) {
        this.usUrbanName = value;
    }

    /**
     * Gets the value of the canLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanLanguage() {
        return canLanguage;
    }

    /**
     * Sets the value of the canLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanLanguage(String value) {
        this.canLanguage = value;
    }

    /**
     * Gets the value of the userFields property.
     * 
     * @return
     *     possible object is
     *     {@link UserFieldsType }
     *     
     */
    public UserFieldsType getUserFields() {
        return userFields;
    }

    /**
     * Sets the value of the userFields property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserFieldsType }
     *     
     */
    public void setUserFields(UserFieldsType value) {
        this.userFields = value;
    }

}
