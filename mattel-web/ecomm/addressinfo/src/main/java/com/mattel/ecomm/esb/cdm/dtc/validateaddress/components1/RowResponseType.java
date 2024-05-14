
package com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rowResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rowResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}confidence" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}recordType" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}recordTypeDefault" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}multipleMatches" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}couldNotValidate" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}countryLevel" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}processedBy" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}addressFormat" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}matchScore" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}multimatchCount" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}addressLine1" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}addressLine2" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}addressLine3" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}addressLine4" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/common/core1}city" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}cityResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}stateProvince" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}stateProvinceResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/common/core1}postalCode" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}postalCodeResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}postalCodeCityResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}addressRecordResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}postalCodeSource" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}postalCodeType" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}postalCodeBase" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}postalCodeAddOn" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/common/core1}country" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}countryResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}firmName" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}firmNameResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}additionalInputData" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}houseNumber" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}houseNumberResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}leadingDirectional" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}leadingDirectionalResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}streetName" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}streetResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}streetNameResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}streetNameAliasType" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}streetNamePreferredAliasResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}streetNameAbbreviatedAliasResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}streetSuffix" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}streetSuffixResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}trailingDirectional" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}trailingDirectionalResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}apartmentLabel" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}apartmentLabelResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}apartmentNumber" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}apartmentNumberResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}apartmentLabel2" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}apartmentLabel2Result" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}apartmentNumber2" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}apartmentNumber2Result" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}RRHC" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}RRHCResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}RRHCType" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}POBox" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}POBoxResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}privateMailbox" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}privateMailboxType" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}abbreviatedAliasResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}preferredAliasResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canadianDeliveryInstallationAreaName" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canadianDeliveryInstallationType" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canadianDeliveryInstallationQualifierName" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USUrbanName" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USUrbanNameResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}houseNumberInput" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}leadingDirectionalInput" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}streetNameInput" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}streetSuffixInput" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}trailingDirectionalInput" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}apartmentLabelInput" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}apartmentNumberInput" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}RRHCInput" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}POBoxInput" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}privateMailboxInput" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}privateMailboxTypeInput" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canadianDeliveryInstallationAreaNameInput" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canadianDeliveryInstallationTypeInput" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canadianDeliveryInstallationQualifierNameInput" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}cityInput" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}stateProvinceInput" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}postalCodeInput" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}countryInput" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}firmNameInput" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USUrbanNameInput" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USBCCheckDigit" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}postalBarCode" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USCarrierRouteCode" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USFIPSCountyNumber" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USCountyName" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USCongressionalDistrict" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USLOTCode" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USLOTSequence" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USLOTHex" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USAltAddr" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USLastLineNumber" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USFinanceNumber" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USLACS" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USLACSReturnCode" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}DPV" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}DPVFootnote" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}CMRA" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}DPVNoStat" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}DPVVacant" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canadianSERPCode" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}RDI" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}UsImDestinationDATF" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}UsImOriginDATF" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}UsImGenericDATF" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}UsImDestinationRaw" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}UsImOriginRaw" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}UsImGenericRaw" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}suiteLinkReturnCode" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}suiteLinkMatchCode" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}suiteLinkFidelity" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}addressBlock1" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}addressBlock2" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}addressBlock3" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}addressBlock4" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}addressBlock5" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}addressBlock6" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}addressBlock7" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}addressBlock8" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}addressBlock9" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}veriMoveDataBlock" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}intINSEECode" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}intHexaviaCode" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}status" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}statusCode" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}statusDescription" minOccurs="0"/>
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
@XmlType(name = "rowResponseType", propOrder = {
    "confidence",
    "recordType",
    "recordTypeDefault",
    "multipleMatches",
    "couldNotValidate",
    "countryLevel",
    "processedBy",
    "addressFormat",
    "matchScore",
    "multimatchCount",
    "addressLine1",
    "addressLine2",
    "addressLine3",
    "addressLine4",
    "city",
    "cityResult",
    "stateProvince",
    "stateProvinceResult",
    "postalCode",
    "postalCodeResult",
    "postalCodeCityResult",
    "addressRecordResult",
    "postalCodeSource",
    "postalCodeType",
    "postalCodeBase",
    "postalCodeAddOn",
    "country",
    "countryResult",
    "firmName",
    "firmNameResult",
    "additionalInputData",
    "houseNumber",
    "houseNumberResult",
    "leadingDirectional",
    "leadingDirectionalResult",
    "streetName",
    "streetResult",
    "streetNameResult",
    "streetNameAliasType",
    "streetNamePreferredAliasResult",
    "streetNameAbbreviatedAliasResult",
    "streetSuffix",
    "streetSuffixResult",
    "trailingDirectional",
    "trailingDirectionalResult",
    "apartmentLabel",
    "apartmentLabelResult",
    "apartmentNumber",
    "apartmentNumberResult",
    "apartmentLabel2",
    "apartmentLabel2Result",
    "apartmentNumber2",
    "apartmentNumber2Result",
    "rrhc",
    "rrhcResult",
    "rrhcType",
    "poBox",
    "poBoxResult",
    "privateMailbox",
    "privateMailboxType",
    "abbreviatedAliasResult",
    "preferredAliasResult",
    "canadianDeliveryInstallationAreaName",
    "canadianDeliveryInstallationType",
    "canadianDeliveryInstallationQualifierName",
    "usUrbanName",
    "usUrbanNameResult",
    "houseNumberInput",
    "leadingDirectionalInput",
    "streetNameInput",
    "streetSuffixInput",
    "trailingDirectionalInput",
    "apartmentLabelInput",
    "apartmentNumberInput",
    "rrhcInput",
    "poBoxInput",
    "privateMailboxInput",
    "privateMailboxTypeInput",
    "canadianDeliveryInstallationAreaNameInput",
    "canadianDeliveryInstallationTypeInput",
    "canadianDeliveryInstallationQualifierNameInput",
    "cityInput",
    "stateProvinceInput",
    "postalCodeInput",
    "countryInput",
    "firmNameInput",
    "usUrbanNameInput",
    "usbcCheckDigit",
    "postalBarCode",
    "usCarrierRouteCode",
    "usfipsCountyNumber",
    "usCountyName",
    "usCongressionalDistrict",
    "uslotCode",
    "uslotSequence",
    "uslotHex",
    "usAltAddr",
    "usLastLineNumber",
    "usFinanceNumber",
    "uslacs",
    "uslacsReturnCode",
    "dpv",
    "dpvFootnote",
    "cmra",
    "dpvNoStat",
    "dpvVacant",
    "canadianSERPCode",
    "rdi",
    "usImDestinationDATF",
    "usImOriginDATF",
    "usImGenericDATF",
    "usImDestinationRaw",
    "usImOriginRaw",
    "usImGenericRaw",
    "suiteLinkReturnCode",
    "suiteLinkMatchCode",
    "suiteLinkFidelity",
    "addressBlock1",
    "addressBlock2",
    "addressBlock3",
    "addressBlock4",
    "addressBlock5",
    "addressBlock6",
    "addressBlock7",
    "addressBlock8",
    "addressBlock9",
    "veriMoveDataBlock",
    "intINSEECode",
    "intHexaviaCode",
    "status",
    "statusCode",
    "statusDescription",
    "userFields"
})
public class RowResponseType {

    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String confidence;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String recordType;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String recordTypeDefault;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String multipleMatches;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String couldNotValidate;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String countryLevel;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String processedBy;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String addressFormat;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String matchScore;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String multimatchCount;
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
    protected String cityResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String stateProvince;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String stateProvinceResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/common/core1")
    protected String postalCode;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String postalCodeResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String postalCodeCityResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String addressRecordResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String postalCodeSource;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String postalCodeType;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String postalCodeBase;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String postalCodeAddOn;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/common/core1")
    protected String country;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String countryResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String firmName;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String firmNameResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String additionalInputData;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String houseNumber;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String houseNumberResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String leadingDirectional;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String leadingDirectionalResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String streetName;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String streetResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String streetNameResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String streetNameAliasType;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String streetNamePreferredAliasResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String streetNameAbbreviatedAliasResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String streetSuffix;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String streetSuffixResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String trailingDirectional;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String trailingDirectionalResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String apartmentLabel;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String apartmentLabelResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String apartmentNumber;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String apartmentNumberResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String apartmentLabel2;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String apartmentLabel2Result;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String apartmentNumber2;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String apartmentNumber2Result;
    @XmlElement(name = "RRHC", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String rrhc;
    @XmlElement(name = "RRHCResult", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String rrhcResult;
    @XmlElement(name = "RRHCType", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String rrhcType;
    @XmlElement(name = "POBox", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String poBox;
    @XmlElement(name = "POBoxResult", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String poBoxResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String privateMailbox;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String privateMailboxType;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String abbreviatedAliasResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String preferredAliasResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canadianDeliveryInstallationAreaName;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canadianDeliveryInstallationType;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canadianDeliveryInstallationQualifierName;
    @XmlElement(name = "USUrbanName", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usUrbanName;
    @XmlElement(name = "USUrbanNameResult", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usUrbanNameResult;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String houseNumberInput;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String leadingDirectionalInput;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String streetNameInput;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String streetSuffixInput;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String trailingDirectionalInput;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String apartmentLabelInput;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String apartmentNumberInput;
    @XmlElement(name = "RRHCInput", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String rrhcInput;
    @XmlElement(name = "POBoxInput", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String poBoxInput;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String privateMailboxInput;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String privateMailboxTypeInput;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canadianDeliveryInstallationAreaNameInput;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canadianDeliveryInstallationTypeInput;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canadianDeliveryInstallationQualifierNameInput;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String cityInput;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String stateProvinceInput;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String postalCodeInput;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String countryInput;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String firmNameInput;
    @XmlElement(name = "USUrbanNameInput", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usUrbanNameInput;
    @XmlElement(name = "USBCCheckDigit", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usbcCheckDigit;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String postalBarCode;
    @XmlElement(name = "USCarrierRouteCode", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usCarrierRouteCode;
    @XmlElement(name = "USFIPSCountyNumber", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usfipsCountyNumber;
    @XmlElement(name = "USCountyName", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usCountyName;
    @XmlElement(name = "USCongressionalDistrict", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usCongressionalDistrict;
    @XmlElement(name = "USLOTCode", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String uslotCode;
    @XmlElement(name = "USLOTSequence", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String uslotSequence;
    @XmlElement(name = "USLOTHex", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String uslotHex;
    @XmlElement(name = "USAltAddr", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usAltAddr;
    @XmlElement(name = "USLastLineNumber", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usLastLineNumber;
    @XmlElement(name = "USFinanceNumber", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usFinanceNumber;
    @XmlElement(name = "USLACS", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String uslacs;
    @XmlElement(name = "USLACSReturnCode", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String uslacsReturnCode;
    @XmlElement(name = "DPV", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String dpv;
    @XmlElement(name = "DPVFootnote", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String dpvFootnote;
    @XmlElement(name = "CMRA", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String cmra;
    @XmlElement(name = "DPVNoStat", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String dpvNoStat;
    @XmlElement(name = "DPVVacant", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String dpvVacant;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canadianSERPCode;
    @XmlElement(name = "RDI", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String rdi;
    @XmlElement(name = "UsImDestinationDATF", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImDestinationDATF;
    @XmlElement(name = "UsImOriginDATF", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImOriginDATF;
    @XmlElement(name = "UsImGenericDATF", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImGenericDATF;
    @XmlElement(name = "UsImDestinationRaw", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImDestinationRaw;
    @XmlElement(name = "UsImOriginRaw", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImOriginRaw;
    @XmlElement(name = "UsImGenericRaw", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImGenericRaw;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String suiteLinkReturnCode;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String suiteLinkMatchCode;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String suiteLinkFidelity;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String addressBlock1;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String addressBlock2;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String addressBlock3;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String addressBlock4;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String addressBlock5;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String addressBlock6;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String addressBlock7;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String addressBlock8;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String addressBlock9;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String veriMoveDataBlock;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String intINSEECode;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String intHexaviaCode;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String status;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String statusCode;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String statusDescription;
    @XmlElement(name = "user_fields")
    protected UserFieldsType userFields;

    /**
     * Gets the value of the confidence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfidence() {
        return confidence;
    }

    /**
     * Sets the value of the confidence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfidence(String value) {
        this.confidence = value;
    }

    /**
     * Gets the value of the recordType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordType() {
        return recordType;
    }

    /**
     * Sets the value of the recordType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordType(String value) {
        this.recordType = value;
    }

    /**
     * Gets the value of the recordTypeDefault property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordTypeDefault() {
        return recordTypeDefault;
    }

    /**
     * Sets the value of the recordTypeDefault property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordTypeDefault(String value) {
        this.recordTypeDefault = value;
    }

    /**
     * Gets the value of the multipleMatches property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMultipleMatches() {
        return multipleMatches;
    }

    /**
     * Sets the value of the multipleMatches property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMultipleMatches(String value) {
        this.multipleMatches = value;
    }

    /**
     * Gets the value of the couldNotValidate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCouldNotValidate() {
        return couldNotValidate;
    }

    /**
     * Sets the value of the couldNotValidate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCouldNotValidate(String value) {
        this.couldNotValidate = value;
    }

    /**
     * Gets the value of the countryLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryLevel() {
        return countryLevel;
    }

    /**
     * Sets the value of the countryLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryLevel(String value) {
        this.countryLevel = value;
    }

    /**
     * Gets the value of the processedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessedBy() {
        return processedBy;
    }

    /**
     * Sets the value of the processedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessedBy(String value) {
        this.processedBy = value;
    }

    /**
     * Gets the value of the addressFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressFormat() {
        return addressFormat;
    }

    /**
     * Sets the value of the addressFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressFormat(String value) {
        this.addressFormat = value;
    }

    /**
     * Gets the value of the matchScore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatchScore() {
        return matchScore;
    }

    /**
     * Sets the value of the matchScore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatchScore(String value) {
        this.matchScore = value;
    }

    /**
     * Gets the value of the multimatchCount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMultimatchCount() {
        return multimatchCount;
    }

    /**
     * Sets the value of the multimatchCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMultimatchCount(String value) {
        this.multimatchCount = value;
    }

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
     * Gets the value of the cityResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityResult() {
        return cityResult;
    }

    /**
     * Sets the value of the cityResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityResult(String value) {
        this.cityResult = value;
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
     * Gets the value of the stateProvinceResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateProvinceResult() {
        return stateProvinceResult;
    }

    /**
     * Sets the value of the stateProvinceResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateProvinceResult(String value) {
        this.stateProvinceResult = value;
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
     * Gets the value of the postalCodeResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCodeResult() {
        return postalCodeResult;
    }

    /**
     * Sets the value of the postalCodeResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCodeResult(String value) {
        this.postalCodeResult = value;
    }

    /**
     * Gets the value of the postalCodeCityResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCodeCityResult() {
        return postalCodeCityResult;
    }

    /**
     * Sets the value of the postalCodeCityResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCodeCityResult(String value) {
        this.postalCodeCityResult = value;
    }

    /**
     * Gets the value of the addressRecordResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressRecordResult() {
        return addressRecordResult;
    }

    /**
     * Sets the value of the addressRecordResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressRecordResult(String value) {
        this.addressRecordResult = value;
    }

    /**
     * Gets the value of the postalCodeSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCodeSource() {
        return postalCodeSource;
    }

    /**
     * Sets the value of the postalCodeSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCodeSource(String value) {
        this.postalCodeSource = value;
    }

    /**
     * Gets the value of the postalCodeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCodeType() {
        return postalCodeType;
    }

    /**
     * Sets the value of the postalCodeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCodeType(String value) {
        this.postalCodeType = value;
    }

    /**
     * Gets the value of the postalCodeBase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCodeBase() {
        return postalCodeBase;
    }

    /**
     * Sets the value of the postalCodeBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCodeBase(String value) {
        this.postalCodeBase = value;
    }

    /**
     * Gets the value of the postalCodeAddOn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCodeAddOn() {
        return postalCodeAddOn;
    }

    /**
     * Sets the value of the postalCodeAddOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCodeAddOn(String value) {
        this.postalCodeAddOn = value;
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
     * Gets the value of the countryResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryResult() {
        return countryResult;
    }

    /**
     * Sets the value of the countryResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryResult(String value) {
        this.countryResult = value;
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
     * Gets the value of the firmNameResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirmNameResult() {
        return firmNameResult;
    }

    /**
     * Sets the value of the firmNameResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirmNameResult(String value) {
        this.firmNameResult = value;
    }

    /**
     * Gets the value of the additionalInputData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalInputData() {
        return additionalInputData;
    }

    /**
     * Sets the value of the additionalInputData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalInputData(String value) {
        this.additionalInputData = value;
    }

    /**
     * Gets the value of the houseNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * Sets the value of the houseNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHouseNumber(String value) {
        this.houseNumber = value;
    }

    /**
     * Gets the value of the houseNumberResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHouseNumberResult() {
        return houseNumberResult;
    }

    /**
     * Sets the value of the houseNumberResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHouseNumberResult(String value) {
        this.houseNumberResult = value;
    }

    /**
     * Gets the value of the leadingDirectional property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeadingDirectional() {
        return leadingDirectional;
    }

    /**
     * Sets the value of the leadingDirectional property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeadingDirectional(String value) {
        this.leadingDirectional = value;
    }

    /**
     * Gets the value of the leadingDirectionalResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeadingDirectionalResult() {
        return leadingDirectionalResult;
    }

    /**
     * Sets the value of the leadingDirectionalResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeadingDirectionalResult(String value) {
        this.leadingDirectionalResult = value;
    }

    /**
     * Gets the value of the streetName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Sets the value of the streetName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetName(String value) {
        this.streetName = value;
    }

    /**
     * Gets the value of the streetResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetResult() {
        return streetResult;
    }

    /**
     * Sets the value of the streetResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetResult(String value) {
        this.streetResult = value;
    }

    /**
     * Gets the value of the streetNameResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetNameResult() {
        return streetNameResult;
    }

    /**
     * Sets the value of the streetNameResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetNameResult(String value) {
        this.streetNameResult = value;
    }

    /**
     * Gets the value of the streetNameAliasType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetNameAliasType() {
        return streetNameAliasType;
    }

    /**
     * Sets the value of the streetNameAliasType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetNameAliasType(String value) {
        this.streetNameAliasType = value;
    }

    /**
     * Gets the value of the streetNamePreferredAliasResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetNamePreferredAliasResult() {
        return streetNamePreferredAliasResult;
    }

    /**
     * Sets the value of the streetNamePreferredAliasResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetNamePreferredAliasResult(String value) {
        this.streetNamePreferredAliasResult = value;
    }

    /**
     * Gets the value of the streetNameAbbreviatedAliasResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetNameAbbreviatedAliasResult() {
        return streetNameAbbreviatedAliasResult;
    }

    /**
     * Sets the value of the streetNameAbbreviatedAliasResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetNameAbbreviatedAliasResult(String value) {
        this.streetNameAbbreviatedAliasResult = value;
    }

    /**
     * Gets the value of the streetSuffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetSuffix() {
        return streetSuffix;
    }

    /**
     * Sets the value of the streetSuffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetSuffix(String value) {
        this.streetSuffix = value;
    }

    /**
     * Gets the value of the streetSuffixResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetSuffixResult() {
        return streetSuffixResult;
    }

    /**
     * Sets the value of the streetSuffixResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetSuffixResult(String value) {
        this.streetSuffixResult = value;
    }

    /**
     * Gets the value of the trailingDirectional property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrailingDirectional() {
        return trailingDirectional;
    }

    /**
     * Sets the value of the trailingDirectional property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrailingDirectional(String value) {
        this.trailingDirectional = value;
    }

    /**
     * Gets the value of the trailingDirectionalResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrailingDirectionalResult() {
        return trailingDirectionalResult;
    }

    /**
     * Sets the value of the trailingDirectionalResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrailingDirectionalResult(String value) {
        this.trailingDirectionalResult = value;
    }

    /**
     * Gets the value of the apartmentLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApartmentLabel() {
        return apartmentLabel;
    }

    /**
     * Sets the value of the apartmentLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApartmentLabel(String value) {
        this.apartmentLabel = value;
    }

    /**
     * Gets the value of the apartmentLabelResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApartmentLabelResult() {
        return apartmentLabelResult;
    }

    /**
     * Sets the value of the apartmentLabelResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApartmentLabelResult(String value) {
        this.apartmentLabelResult = value;
    }

    /**
     * Gets the value of the apartmentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApartmentNumber() {
        return apartmentNumber;
    }

    /**
     * Sets the value of the apartmentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApartmentNumber(String value) {
        this.apartmentNumber = value;
    }

    /**
     * Gets the value of the apartmentNumberResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApartmentNumberResult() {
        return apartmentNumberResult;
    }

    /**
     * Sets the value of the apartmentNumberResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApartmentNumberResult(String value) {
        this.apartmentNumberResult = value;
    }

    /**
     * Gets the value of the apartmentLabel2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApartmentLabel2() {
        return apartmentLabel2;
    }

    /**
     * Sets the value of the apartmentLabel2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApartmentLabel2(String value) {
        this.apartmentLabel2 = value;
    }

    /**
     * Gets the value of the apartmentLabel2Result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApartmentLabel2Result() {
        return apartmentLabel2Result;
    }

    /**
     * Sets the value of the apartmentLabel2Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApartmentLabel2Result(String value) {
        this.apartmentLabel2Result = value;
    }

    /**
     * Gets the value of the apartmentNumber2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApartmentNumber2() {
        return apartmentNumber2;
    }

    /**
     * Sets the value of the apartmentNumber2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApartmentNumber2(String value) {
        this.apartmentNumber2 = value;
    }

    /**
     * Gets the value of the apartmentNumber2Result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApartmentNumber2Result() {
        return apartmentNumber2Result;
    }

    /**
     * Sets the value of the apartmentNumber2Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApartmentNumber2Result(String value) {
        this.apartmentNumber2Result = value;
    }

    /**
     * Gets the value of the rrhc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRRHC() {
        return rrhc;
    }

    /**
     * Sets the value of the rrhc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRRHC(String value) {
        this.rrhc = value;
    }

    /**
     * Gets the value of the rrhcResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRRHCResult() {
        return rrhcResult;
    }

    /**
     * Sets the value of the rrhcResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRRHCResult(String value) {
        this.rrhcResult = value;
    }

    /**
     * Gets the value of the rrhcType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRRHCType() {
        return rrhcType;
    }

    /**
     * Sets the value of the rrhcType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRRHCType(String value) {
        this.rrhcType = value;
    }

    /**
     * Gets the value of the poBox property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOBox() {
        return poBox;
    }

    /**
     * Sets the value of the poBox property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOBox(String value) {
        this.poBox = value;
    }

    /**
     * Gets the value of the poBoxResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOBoxResult() {
        return poBoxResult;
    }

    /**
     * Sets the value of the poBoxResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOBoxResult(String value) {
        this.poBoxResult = value;
    }

    /**
     * Gets the value of the privateMailbox property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrivateMailbox() {
        return privateMailbox;
    }

    /**
     * Sets the value of the privateMailbox property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrivateMailbox(String value) {
        this.privateMailbox = value;
    }

    /**
     * Gets the value of the privateMailboxType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrivateMailboxType() {
        return privateMailboxType;
    }

    /**
     * Sets the value of the privateMailboxType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrivateMailboxType(String value) {
        this.privateMailboxType = value;
    }

    /**
     * Gets the value of the abbreviatedAliasResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAbbreviatedAliasResult() {
        return abbreviatedAliasResult;
    }

    /**
     * Sets the value of the abbreviatedAliasResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAbbreviatedAliasResult(String value) {
        this.abbreviatedAliasResult = value;
    }

    /**
     * Gets the value of the preferredAliasResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferredAliasResult() {
        return preferredAliasResult;
    }

    /**
     * Sets the value of the preferredAliasResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferredAliasResult(String value) {
        this.preferredAliasResult = value;
    }

    /**
     * Gets the value of the canadianDeliveryInstallationAreaName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanadianDeliveryInstallationAreaName() {
        return canadianDeliveryInstallationAreaName;
    }

    /**
     * Sets the value of the canadianDeliveryInstallationAreaName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanadianDeliveryInstallationAreaName(String value) {
        this.canadianDeliveryInstallationAreaName = value;
    }

    /**
     * Gets the value of the canadianDeliveryInstallationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanadianDeliveryInstallationType() {
        return canadianDeliveryInstallationType;
    }

    /**
     * Sets the value of the canadianDeliveryInstallationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanadianDeliveryInstallationType(String value) {
        this.canadianDeliveryInstallationType = value;
    }

    /**
     * Gets the value of the canadianDeliveryInstallationQualifierName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanadianDeliveryInstallationQualifierName() {
        return canadianDeliveryInstallationQualifierName;
    }

    /**
     * Sets the value of the canadianDeliveryInstallationQualifierName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanadianDeliveryInstallationQualifierName(String value) {
        this.canadianDeliveryInstallationQualifierName = value;
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
     * Gets the value of the usUrbanNameResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSUrbanNameResult() {
        return usUrbanNameResult;
    }

    /**
     * Sets the value of the usUrbanNameResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSUrbanNameResult(String value) {
        this.usUrbanNameResult = value;
    }

    /**
     * Gets the value of the houseNumberInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHouseNumberInput() {
        return houseNumberInput;
    }

    /**
     * Sets the value of the houseNumberInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHouseNumberInput(String value) {
        this.houseNumberInput = value;
    }

    /**
     * Gets the value of the leadingDirectionalInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeadingDirectionalInput() {
        return leadingDirectionalInput;
    }

    /**
     * Sets the value of the leadingDirectionalInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeadingDirectionalInput(String value) {
        this.leadingDirectionalInput = value;
    }

    /**
     * Gets the value of the streetNameInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetNameInput() {
        return streetNameInput;
    }

    /**
     * Sets the value of the streetNameInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetNameInput(String value) {
        this.streetNameInput = value;
    }

    /**
     * Gets the value of the streetSuffixInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetSuffixInput() {
        return streetSuffixInput;
    }

    /**
     * Sets the value of the streetSuffixInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetSuffixInput(String value) {
        this.streetSuffixInput = value;
    }

    /**
     * Gets the value of the trailingDirectionalInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrailingDirectionalInput() {
        return trailingDirectionalInput;
    }

    /**
     * Sets the value of the trailingDirectionalInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrailingDirectionalInput(String value) {
        this.trailingDirectionalInput = value;
    }

    /**
     * Gets the value of the apartmentLabelInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApartmentLabelInput() {
        return apartmentLabelInput;
    }

    /**
     * Sets the value of the apartmentLabelInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApartmentLabelInput(String value) {
        this.apartmentLabelInput = value;
    }

    /**
     * Gets the value of the apartmentNumberInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApartmentNumberInput() {
        return apartmentNumberInput;
    }

    /**
     * Sets the value of the apartmentNumberInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApartmentNumberInput(String value) {
        this.apartmentNumberInput = value;
    }

    /**
     * Gets the value of the rrhcInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRRHCInput() {
        return rrhcInput;
    }

    /**
     * Sets the value of the rrhcInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRRHCInput(String value) {
        this.rrhcInput = value;
    }

    /**
     * Gets the value of the poBoxInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOBoxInput() {
        return poBoxInput;
    }

    /**
     * Sets the value of the poBoxInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOBoxInput(String value) {
        this.poBoxInput = value;
    }

    /**
     * Gets the value of the privateMailboxInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrivateMailboxInput() {
        return privateMailboxInput;
    }

    /**
     * Sets the value of the privateMailboxInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrivateMailboxInput(String value) {
        this.privateMailboxInput = value;
    }

    /**
     * Gets the value of the privateMailboxTypeInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrivateMailboxTypeInput() {
        return privateMailboxTypeInput;
    }

    /**
     * Sets the value of the privateMailboxTypeInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrivateMailboxTypeInput(String value) {
        this.privateMailboxTypeInput = value;
    }

    /**
     * Gets the value of the canadianDeliveryInstallationAreaNameInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanadianDeliveryInstallationAreaNameInput() {
        return canadianDeliveryInstallationAreaNameInput;
    }

    /**
     * Sets the value of the canadianDeliveryInstallationAreaNameInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanadianDeliveryInstallationAreaNameInput(String value) {
        this.canadianDeliveryInstallationAreaNameInput = value;
    }

    /**
     * Gets the value of the canadianDeliveryInstallationTypeInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanadianDeliveryInstallationTypeInput() {
        return canadianDeliveryInstallationTypeInput;
    }

    /**
     * Sets the value of the canadianDeliveryInstallationTypeInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanadianDeliveryInstallationTypeInput(String value) {
        this.canadianDeliveryInstallationTypeInput = value;
    }

    /**
     * Gets the value of the canadianDeliveryInstallationQualifierNameInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanadianDeliveryInstallationQualifierNameInput() {
        return canadianDeliveryInstallationQualifierNameInput;
    }

    /**
     * Sets the value of the canadianDeliveryInstallationQualifierNameInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanadianDeliveryInstallationQualifierNameInput(String value) {
        this.canadianDeliveryInstallationQualifierNameInput = value;
    }

    /**
     * Gets the value of the cityInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityInput() {
        return cityInput;
    }

    /**
     * Sets the value of the cityInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityInput(String value) {
        this.cityInput = value;
    }

    /**
     * Gets the value of the stateProvinceInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateProvinceInput() {
        return stateProvinceInput;
    }

    /**
     * Sets the value of the stateProvinceInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateProvinceInput(String value) {
        this.stateProvinceInput = value;
    }

    /**
     * Gets the value of the postalCodeInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCodeInput() {
        return postalCodeInput;
    }

    /**
     * Sets the value of the postalCodeInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCodeInput(String value) {
        this.postalCodeInput = value;
    }

    /**
     * Gets the value of the countryInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryInput() {
        return countryInput;
    }

    /**
     * Sets the value of the countryInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryInput(String value) {
        this.countryInput = value;
    }

    /**
     * Gets the value of the firmNameInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirmNameInput() {
        return firmNameInput;
    }

    /**
     * Sets the value of the firmNameInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirmNameInput(String value) {
        this.firmNameInput = value;
    }

    /**
     * Gets the value of the usUrbanNameInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSUrbanNameInput() {
        return usUrbanNameInput;
    }

    /**
     * Sets the value of the usUrbanNameInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSUrbanNameInput(String value) {
        this.usUrbanNameInput = value;
    }

    /**
     * Gets the value of the usbcCheckDigit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSBCCheckDigit() {
        return usbcCheckDigit;
    }

    /**
     * Sets the value of the usbcCheckDigit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSBCCheckDigit(String value) {
        this.usbcCheckDigit = value;
    }

    /**
     * Gets the value of the postalBarCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalBarCode() {
        return postalBarCode;
    }

    /**
     * Sets the value of the postalBarCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalBarCode(String value) {
        this.postalBarCode = value;
    }

    /**
     * Gets the value of the usCarrierRouteCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSCarrierRouteCode() {
        return usCarrierRouteCode;
    }

    /**
     * Sets the value of the usCarrierRouteCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSCarrierRouteCode(String value) {
        this.usCarrierRouteCode = value;
    }

    /**
     * Gets the value of the usfipsCountyNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSFIPSCountyNumber() {
        return usfipsCountyNumber;
    }

    /**
     * Sets the value of the usfipsCountyNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSFIPSCountyNumber(String value) {
        this.usfipsCountyNumber = value;
    }

    /**
     * Gets the value of the usCountyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSCountyName() {
        return usCountyName;
    }

    /**
     * Sets the value of the usCountyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSCountyName(String value) {
        this.usCountyName = value;
    }

    /**
     * Gets the value of the usCongressionalDistrict property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSCongressionalDistrict() {
        return usCongressionalDistrict;
    }

    /**
     * Sets the value of the usCongressionalDistrict property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSCongressionalDistrict(String value) {
        this.usCongressionalDistrict = value;
    }

    /**
     * Gets the value of the uslotCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSLOTCode() {
        return uslotCode;
    }

    /**
     * Sets the value of the uslotCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSLOTCode(String value) {
        this.uslotCode = value;
    }

    /**
     * Gets the value of the uslotSequence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSLOTSequence() {
        return uslotSequence;
    }

    /**
     * Sets the value of the uslotSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSLOTSequence(String value) {
        this.uslotSequence = value;
    }

    /**
     * Gets the value of the uslotHex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSLOTHex() {
        return uslotHex;
    }

    /**
     * Sets the value of the uslotHex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSLOTHex(String value) {
        this.uslotHex = value;
    }

    /**
     * Gets the value of the usAltAddr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSAltAddr() {
        return usAltAddr;
    }

    /**
     * Sets the value of the usAltAddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSAltAddr(String value) {
        this.usAltAddr = value;
    }

    /**
     * Gets the value of the usLastLineNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSLastLineNumber() {
        return usLastLineNumber;
    }

    /**
     * Sets the value of the usLastLineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSLastLineNumber(String value) {
        this.usLastLineNumber = value;
    }

    /**
     * Gets the value of the usFinanceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSFinanceNumber() {
        return usFinanceNumber;
    }

    /**
     * Sets the value of the usFinanceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSFinanceNumber(String value) {
        this.usFinanceNumber = value;
    }

    /**
     * Gets the value of the uslacs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSLACS() {
        return uslacs;
    }

    /**
     * Sets the value of the uslacs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSLACS(String value) {
        this.uslacs = value;
    }

    /**
     * Gets the value of the uslacsReturnCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSLACSReturnCode() {
        return uslacsReturnCode;
    }

    /**
     * Sets the value of the uslacsReturnCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSLACSReturnCode(String value) {
        this.uslacsReturnCode = value;
    }

    /**
     * Gets the value of the dpv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDPV() {
        return dpv;
    }

    /**
     * Sets the value of the dpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDPV(String value) {
        this.dpv = value;
    }

    /**
     * Gets the value of the dpvFootnote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDPVFootnote() {
        return dpvFootnote;
    }

    /**
     * Sets the value of the dpvFootnote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDPVFootnote(String value) {
        this.dpvFootnote = value;
    }

    /**
     * Gets the value of the cmra property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCMRA() {
        return cmra;
    }

    /**
     * Sets the value of the cmra property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCMRA(String value) {
        this.cmra = value;
    }

    /**
     * Gets the value of the dpvNoStat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDPVNoStat() {
        return dpvNoStat;
    }

    /**
     * Sets the value of the dpvNoStat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDPVNoStat(String value) {
        this.dpvNoStat = value;
    }

    /**
     * Gets the value of the dpvVacant property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDPVVacant() {
        return dpvVacant;
    }

    /**
     * Sets the value of the dpvVacant property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDPVVacant(String value) {
        this.dpvVacant = value;
    }

    /**
     * Gets the value of the canadianSERPCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanadianSERPCode() {
        return canadianSERPCode;
    }

    /**
     * Sets the value of the canadianSERPCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanadianSERPCode(String value) {
        this.canadianSERPCode = value;
    }

    /**
     * Gets the value of the rdi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRDI() {
        return rdi;
    }

    /**
     * Sets the value of the rdi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRDI(String value) {
        this.rdi = value;
    }

    /**
     * Gets the value of the usImDestinationDATF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsImDestinationDATF() {
        return usImDestinationDATF;
    }

    /**
     * Sets the value of the usImDestinationDATF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsImDestinationDATF(String value) {
        this.usImDestinationDATF = value;
    }

    /**
     * Gets the value of the usImOriginDATF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsImOriginDATF() {
        return usImOriginDATF;
    }

    /**
     * Sets the value of the usImOriginDATF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsImOriginDATF(String value) {
        this.usImOriginDATF = value;
    }

    /**
     * Gets the value of the usImGenericDATF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsImGenericDATF() {
        return usImGenericDATF;
    }

    /**
     * Sets the value of the usImGenericDATF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsImGenericDATF(String value) {
        this.usImGenericDATF = value;
    }

    /**
     * Gets the value of the usImDestinationRaw property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsImDestinationRaw() {
        return usImDestinationRaw;
    }

    /**
     * Sets the value of the usImDestinationRaw property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsImDestinationRaw(String value) {
        this.usImDestinationRaw = value;
    }

    /**
     * Gets the value of the usImOriginRaw property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsImOriginRaw() {
        return usImOriginRaw;
    }

    /**
     * Sets the value of the usImOriginRaw property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsImOriginRaw(String value) {
        this.usImOriginRaw = value;
    }

    /**
     * Gets the value of the usImGenericRaw property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsImGenericRaw() {
        return usImGenericRaw;
    }

    /**
     * Sets the value of the usImGenericRaw property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsImGenericRaw(String value) {
        this.usImGenericRaw = value;
    }

    /**
     * Gets the value of the suiteLinkReturnCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuiteLinkReturnCode() {
        return suiteLinkReturnCode;
    }

    /**
     * Sets the value of the suiteLinkReturnCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuiteLinkReturnCode(String value) {
        this.suiteLinkReturnCode = value;
    }

    /**
     * Gets the value of the suiteLinkMatchCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuiteLinkMatchCode() {
        return suiteLinkMatchCode;
    }

    /**
     * Sets the value of the suiteLinkMatchCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuiteLinkMatchCode(String value) {
        this.suiteLinkMatchCode = value;
    }

    /**
     * Gets the value of the suiteLinkFidelity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuiteLinkFidelity() {
        return suiteLinkFidelity;
    }

    /**
     * Sets the value of the suiteLinkFidelity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuiteLinkFidelity(String value) {
        this.suiteLinkFidelity = value;
    }

    /**
     * Gets the value of the addressBlock1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressBlock1() {
        return addressBlock1;
    }

    /**
     * Sets the value of the addressBlock1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressBlock1(String value) {
        this.addressBlock1 = value;
    }

    /**
     * Gets the value of the addressBlock2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressBlock2() {
        return addressBlock2;
    }

    /**
     * Sets the value of the addressBlock2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressBlock2(String value) {
        this.addressBlock2 = value;
    }

    /**
     * Gets the value of the addressBlock3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressBlock3() {
        return addressBlock3;
    }

    /**
     * Sets the value of the addressBlock3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressBlock3(String value) {
        this.addressBlock3 = value;
    }

    /**
     * Gets the value of the addressBlock4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressBlock4() {
        return addressBlock4;
    }

    /**
     * Sets the value of the addressBlock4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressBlock4(String value) {
        this.addressBlock4 = value;
    }

    /**
     * Gets the value of the addressBlock5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressBlock5() {
        return addressBlock5;
    }

    /**
     * Sets the value of the addressBlock5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressBlock5(String value) {
        this.addressBlock5 = value;
    }

    /**
     * Gets the value of the addressBlock6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressBlock6() {
        return addressBlock6;
    }

    /**
     * Sets the value of the addressBlock6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressBlock6(String value) {
        this.addressBlock6 = value;
    }

    /**
     * Gets the value of the addressBlock7 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressBlock7() {
        return addressBlock7;
    }

    /**
     * Sets the value of the addressBlock7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressBlock7(String value) {
        this.addressBlock7 = value;
    }

    /**
     * Gets the value of the addressBlock8 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressBlock8() {
        return addressBlock8;
    }

    /**
     * Sets the value of the addressBlock8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressBlock8(String value) {
        this.addressBlock8 = value;
    }

    /**
     * Gets the value of the addressBlock9 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressBlock9() {
        return addressBlock9;
    }

    /**
     * Sets the value of the addressBlock9 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressBlock9(String value) {
        this.addressBlock9 = value;
    }

    /**
     * Gets the value of the veriMoveDataBlock property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVeriMoveDataBlock() {
        return veriMoveDataBlock;
    }

    /**
     * Sets the value of the veriMoveDataBlock property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVeriMoveDataBlock(String value) {
        this.veriMoveDataBlock = value;
    }

    /**
     * Gets the value of the intINSEECode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntINSEECode() {
        return intINSEECode;
    }

    /**
     * Sets the value of the intINSEECode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntINSEECode(String value) {
        this.intINSEECode = value;
    }

    /**
     * Gets the value of the intHexaviaCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntHexaviaCode() {
        return intHexaviaCode;
    }

    /**
     * Sets the value of the intHexaviaCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntHexaviaCode(String value) {
        this.intHexaviaCode = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the statusCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusCode(String value) {
        this.statusCode = value;
    }

    /**
     * Gets the value of the statusDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusDescription() {
        return statusDescription;
    }

    /**
     * Sets the value of the statusDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusDescription(String value) {
        this.statusDescription = value;
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
