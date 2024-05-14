
package com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for optionsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="optionsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}performUSProcessing" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}outputAddressBlocks" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}performCanadianProcessing" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}performInternationalProcessing" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}outputFormattedOnFail" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}keepMultimatch" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}maximumResults" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}performDPV" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}performRDI" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}performLACSLink" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}performLOT" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}performESM" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}enableSERP" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}performASM" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}performEWS" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}extractFirm" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}extractUrb" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}outputFieldLevelReturnCodes" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}outputPostalCodeSeparator" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}outputCasing" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}outputRecordType" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}outputCountryFormat" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}outputMultinationalCharacters" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}standardAddressFormat" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}standardAddressPMBLine" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}outputShortCityName" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}outputReport3553" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}outputReportSERP" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}outputReportSummary" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}streetMatchingStrictness" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}firmMatchingStrictness" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}directionalMatchingStrictness" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}dualAddressLogic" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}dPVSuccessfulStatusCondition" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}failOnCMRAMatch" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}reportListProcessorName" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}reportListFileName" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}reportListNumber" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}reportMailerName" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}reportMailerAddress" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}reportMailerCityLine" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canReportMailerCPCNumber" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canReportMailerName" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canReportMailerAddress" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canReportMailerCityLine" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}internationalCityStreetSearching" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}addressLineSearchOnFail" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}homeCountry" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USImDestEnable" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USImDestBarcodeID" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USImDestSpecSvcCode" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USImDestSubID" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USImDestDftMailingID" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USImDestEnableInc" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USImOrgEnable" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USImOrgBarcodeID" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USImOrgSpecSvcCode" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USImOrgCustID" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USImOrgRouteZip" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USImGenEnable" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USImGenBarcodeID" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USImGenSpecSvcCode" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USImGenSubID" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USImGenCustID" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USImGenEnableInc" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}USImErrorHandle" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}performSuiteLink" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}outputStreetNameAlias" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}outputVeriMoveDataBlock" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}DPVDetermineNoStat" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}DPVDetermineVacancy" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}outputAbbreviatedAlias" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}outputPreferredAlias" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canStandardAddressFormat" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canEnglishApartmentLabel" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canFrenchApartmentLabel" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canFrenchFormat" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canOutputCityFormat" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canOutputCityAlias" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canDualAddressLogic" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canPreferHouseNum" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canRuralRouteFormat" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canNonCivicFormat" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}canDeliveryOfficeFormat" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}databaseUS" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}databaseCanada" minOccurs="0"/>
 *         &lt;element ref="{http://www.mattel.com/esb/cdm/dtc/validateaddress/core1}databaseInternational" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "optionsType", propOrder = {
    "performUSProcessing",
    "outputAddressBlocks",
    "performCanadianProcessing",
    "performInternationalProcessing",
    "outputFormattedOnFail",
    "keepMultimatch",
    "maximumResults",
    "performDPV",
    "performRDI",
    "performLACSLink",
    "performLOT",
    "performESM",
    "enableSERP",
    "performASM",
    "performEWS",
    "extractFirm",
    "extractUrb",
    "outputFieldLevelReturnCodes",
    "outputPostalCodeSeparator",
    "outputCasing",
    "outputRecordType",
    "outputCountryFormat",
    "outputMultinationalCharacters",
    "standardAddressFormat",
    "standardAddressPMBLine",
    "outputShortCityName",
    "outputReport3553",
    "outputReportSERP",
    "outputReportSummary",
    "streetMatchingStrictness",
    "firmMatchingStrictness",
    "directionalMatchingStrictness",
    "dualAddressLogic",
    "dpvSuccessfulStatusCondition",
    "failOnCMRAMatch",
    "reportListProcessorName",
    "reportListFileName",
    "reportListNumber",
    "reportMailerName",
    "reportMailerAddress",
    "reportMailerCityLine",
    "canReportMailerCPCNumber",
    "canReportMailerName",
    "canReportMailerAddress",
    "canReportMailerCityLine",
    "internationalCityStreetSearching",
    "addressLineSearchOnFail",
    "homeCountry",
    "usImDestEnable",
    "usImDestBarcodeID",
    "usImDestSpecSvcCode",
    "usImDestSubID",
    "usImDestDftMailingID",
    "usImDestEnableInc",
    "usImOrgEnable",
    "usImOrgBarcodeID",
    "usImOrgSpecSvcCode",
    "usImOrgCustID",
    "usImOrgRouteZip",
    "usImGenEnable",
    "usImGenBarcodeID",
    "usImGenSpecSvcCode",
    "usImGenSubID",
    "usImGenCustID",
    "usImGenEnableInc",
    "usImErrorHandle",
    "performSuiteLink",
    "outputStreetNameAlias",
    "outputVeriMoveDataBlock",
    "dpvDetermineNoStat",
    "dpvDetermineVacancy",
    "outputAbbreviatedAlias",
    "outputPreferredAlias",
    "canStandardAddressFormat",
    "canEnglishApartmentLabel",
    "canFrenchApartmentLabel",
    "canFrenchFormat",
    "canOutputCityFormat",
    "canOutputCityAlias",
    "canDualAddressLogic",
    "canPreferHouseNum",
    "canRuralRouteFormat",
    "canNonCivicFormat",
    "canDeliveryOfficeFormat",
    "databaseUS",
    "databaseCanada",
    "databaseInternational"
})
public class OptionsType {

    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String performUSProcessing;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String outputAddressBlocks;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String performCanadianProcessing;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String performInternationalProcessing;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String outputFormattedOnFail;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String keepMultimatch;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String maximumResults;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String performDPV;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String performRDI;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String performLACSLink;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String performLOT;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String performESM;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String enableSERP;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String performASM;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String performEWS;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String extractFirm;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String extractUrb;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String outputFieldLevelReturnCodes;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String outputPostalCodeSeparator;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String outputCasing;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String outputRecordType;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String outputCountryFormat;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String outputMultinationalCharacters;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String standardAddressFormat;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String standardAddressPMBLine;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String outputShortCityName;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String outputReport3553;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String outputReportSERP;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String outputReportSummary;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String streetMatchingStrictness;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String firmMatchingStrictness;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String directionalMatchingStrictness;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String dualAddressLogic;
    @XmlElement(name = "dPVSuccessfulStatusCondition", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String dpvSuccessfulStatusCondition;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String failOnCMRAMatch;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String reportListProcessorName;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String reportListFileName;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String reportListNumber;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String reportMailerName;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String reportMailerAddress;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String reportMailerCityLine;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canReportMailerCPCNumber;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canReportMailerName;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canReportMailerAddress;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canReportMailerCityLine;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String internationalCityStreetSearching;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String addressLineSearchOnFail;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String homeCountry;
    @XmlElement(name = "USImDestEnable", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImDestEnable;
    @XmlElement(name = "USImDestBarcodeID", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImDestBarcodeID;
    @XmlElement(name = "USImDestSpecSvcCode", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImDestSpecSvcCode;
    @XmlElement(name = "USImDestSubID", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImDestSubID;
    @XmlElement(name = "USImDestDftMailingID", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImDestDftMailingID;
    @XmlElement(name = "USImDestEnableInc", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImDestEnableInc;
    @XmlElement(name = "USImOrgEnable", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImOrgEnable;
    @XmlElement(name = "USImOrgBarcodeID", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImOrgBarcodeID;
    @XmlElement(name = "USImOrgSpecSvcCode", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImOrgSpecSvcCode;
    @XmlElement(name = "USImOrgCustID", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImOrgCustID;
    @XmlElement(name = "USImOrgRouteZip", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImOrgRouteZip;
    @XmlElement(name = "USImGenEnable", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImGenEnable;
    @XmlElement(name = "USImGenBarcodeID", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImGenBarcodeID;
    @XmlElement(name = "USImGenSpecSvcCode", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImGenSpecSvcCode;
    @XmlElement(name = "USImGenSubID", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImGenSubID;
    @XmlElement(name = "USImGenCustID", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImGenCustID;
    @XmlElement(name = "USImGenEnableInc", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImGenEnableInc;
    @XmlElement(name = "USImErrorHandle", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String usImErrorHandle;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String performSuiteLink;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String outputStreetNameAlias;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String outputVeriMoveDataBlock;
    @XmlElement(name = "DPVDetermineNoStat", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String dpvDetermineNoStat;
    @XmlElement(name = "DPVDetermineVacancy", namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String dpvDetermineVacancy;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String outputAbbreviatedAlias;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String outputPreferredAlias;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canStandardAddressFormat;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canEnglishApartmentLabel;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canFrenchApartmentLabel;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canFrenchFormat;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canOutputCityFormat;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canOutputCityAlias;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canDualAddressLogic;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canPreferHouseNum;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canRuralRouteFormat;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canNonCivicFormat;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String canDeliveryOfficeFormat;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String databaseUS;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String databaseCanada;
    @XmlElement(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/core1")
    protected String databaseInternational;

    /**
     * Gets the value of the performUSProcessing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformUSProcessing() {
        return performUSProcessing;
    }

    /**
     * Sets the value of the performUSProcessing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformUSProcessing(String value) {
        this.performUSProcessing = value;
    }

    /**
     * Gets the value of the outputAddressBlocks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputAddressBlocks() {
        return outputAddressBlocks;
    }

    /**
     * Sets the value of the outputAddressBlocks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputAddressBlocks(String value) {
        this.outputAddressBlocks = value;
    }

    /**
     * Gets the value of the performCanadianProcessing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformCanadianProcessing() {
        return performCanadianProcessing;
    }

    /**
     * Sets the value of the performCanadianProcessing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformCanadianProcessing(String value) {
        this.performCanadianProcessing = value;
    }

    /**
     * Gets the value of the performInternationalProcessing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformInternationalProcessing() {
        return performInternationalProcessing;
    }

    /**
     * Sets the value of the performInternationalProcessing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformInternationalProcessing(String value) {
        this.performInternationalProcessing = value;
    }

    /**
     * Gets the value of the outputFormattedOnFail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputFormattedOnFail() {
        return outputFormattedOnFail;
    }

    /**
     * Sets the value of the outputFormattedOnFail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputFormattedOnFail(String value) {
        this.outputFormattedOnFail = value;
    }

    /**
     * Gets the value of the keepMultimatch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeepMultimatch() {
        return keepMultimatch;
    }

    /**
     * Sets the value of the keepMultimatch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeepMultimatch(String value) {
        this.keepMultimatch = value;
    }

    /**
     * Gets the value of the maximumResults property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaximumResults() {
        return maximumResults;
    }

    /**
     * Sets the value of the maximumResults property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaximumResults(String value) {
        this.maximumResults = value;
    }

    /**
     * Gets the value of the performDPV property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformDPV() {
        return performDPV;
    }

    /**
     * Sets the value of the performDPV property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformDPV(String value) {
        this.performDPV = value;
    }

    /**
     * Gets the value of the performRDI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformRDI() {
        return performRDI;
    }

    /**
     * Sets the value of the performRDI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformRDI(String value) {
        this.performRDI = value;
    }

    /**
     * Gets the value of the performLACSLink property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformLACSLink() {
        return performLACSLink;
    }

    /**
     * Sets the value of the performLACSLink property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformLACSLink(String value) {
        this.performLACSLink = value;
    }

    /**
     * Gets the value of the performLOT property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformLOT() {
        return performLOT;
    }

    /**
     * Sets the value of the performLOT property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformLOT(String value) {
        this.performLOT = value;
    }

    /**
     * Gets the value of the performESM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformESM() {
        return performESM;
    }

    /**
     * Sets the value of the performESM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformESM(String value) {
        this.performESM = value;
    }

    /**
     * Gets the value of the enableSERP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnableSERP() {
        return enableSERP;
    }

    /**
     * Sets the value of the enableSERP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnableSERP(String value) {
        this.enableSERP = value;
    }

    /**
     * Gets the value of the performASM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformASM() {
        return performASM;
    }

    /**
     * Sets the value of the performASM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformASM(String value) {
        this.performASM = value;
    }

    /**
     * Gets the value of the performEWS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformEWS() {
        return performEWS;
    }

    /**
     * Sets the value of the performEWS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformEWS(String value) {
        this.performEWS = value;
    }

    /**
     * Gets the value of the extractFirm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtractFirm() {
        return extractFirm;
    }

    /**
     * Sets the value of the extractFirm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtractFirm(String value) {
        this.extractFirm = value;
    }

    /**
     * Gets the value of the extractUrb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtractUrb() {
        return extractUrb;
    }

    /**
     * Sets the value of the extractUrb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtractUrb(String value) {
        this.extractUrb = value;
    }

    /**
     * Gets the value of the outputFieldLevelReturnCodes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputFieldLevelReturnCodes() {
        return outputFieldLevelReturnCodes;
    }

    /**
     * Sets the value of the outputFieldLevelReturnCodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputFieldLevelReturnCodes(String value) {
        this.outputFieldLevelReturnCodes = value;
    }

    /**
     * Gets the value of the outputPostalCodeSeparator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputPostalCodeSeparator() {
        return outputPostalCodeSeparator;
    }

    /**
     * Sets the value of the outputPostalCodeSeparator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputPostalCodeSeparator(String value) {
        this.outputPostalCodeSeparator = value;
    }

    /**
     * Gets the value of the outputCasing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputCasing() {
        return outputCasing;
    }

    /**
     * Sets the value of the outputCasing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputCasing(String value) {
        this.outputCasing = value;
    }

    /**
     * Gets the value of the outputRecordType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputRecordType() {
        return outputRecordType;
    }

    /**
     * Sets the value of the outputRecordType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputRecordType(String value) {
        this.outputRecordType = value;
    }

    /**
     * Gets the value of the outputCountryFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputCountryFormat() {
        return outputCountryFormat;
    }

    /**
     * Sets the value of the outputCountryFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputCountryFormat(String value) {
        this.outputCountryFormat = value;
    }

    /**
     * Gets the value of the outputMultinationalCharacters property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputMultinationalCharacters() {
        return outputMultinationalCharacters;
    }

    /**
     * Sets the value of the outputMultinationalCharacters property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputMultinationalCharacters(String value) {
        this.outputMultinationalCharacters = value;
    }

    /**
     * Gets the value of the standardAddressFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStandardAddressFormat() {
        return standardAddressFormat;
    }

    /**
     * Sets the value of the standardAddressFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStandardAddressFormat(String value) {
        this.standardAddressFormat = value;
    }

    /**
     * Gets the value of the standardAddressPMBLine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStandardAddressPMBLine() {
        return standardAddressPMBLine;
    }

    /**
     * Sets the value of the standardAddressPMBLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStandardAddressPMBLine(String value) {
        this.standardAddressPMBLine = value;
    }

    /**
     * Gets the value of the outputShortCityName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputShortCityName() {
        return outputShortCityName;
    }

    /**
     * Sets the value of the outputShortCityName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputShortCityName(String value) {
        this.outputShortCityName = value;
    }

    /**
     * Gets the value of the outputReport3553 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputReport3553() {
        return outputReport3553;
    }

    /**
     * Sets the value of the outputReport3553 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputReport3553(String value) {
        this.outputReport3553 = value;
    }

    /**
     * Gets the value of the outputReportSERP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputReportSERP() {
        return outputReportSERP;
    }

    /**
     * Sets the value of the outputReportSERP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputReportSERP(String value) {
        this.outputReportSERP = value;
    }

    /**
     * Gets the value of the outputReportSummary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputReportSummary() {
        return outputReportSummary;
    }

    /**
     * Sets the value of the outputReportSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputReportSummary(String value) {
        this.outputReportSummary = value;
    }

    /**
     * Gets the value of the streetMatchingStrictness property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetMatchingStrictness() {
        return streetMatchingStrictness;
    }

    /**
     * Sets the value of the streetMatchingStrictness property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetMatchingStrictness(String value) {
        this.streetMatchingStrictness = value;
    }

    /**
     * Gets the value of the firmMatchingStrictness property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirmMatchingStrictness() {
        return firmMatchingStrictness;
    }

    /**
     * Sets the value of the firmMatchingStrictness property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirmMatchingStrictness(String value) {
        this.firmMatchingStrictness = value;
    }

    /**
     * Gets the value of the directionalMatchingStrictness property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirectionalMatchingStrictness() {
        return directionalMatchingStrictness;
    }

    /**
     * Sets the value of the directionalMatchingStrictness property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirectionalMatchingStrictness(String value) {
        this.directionalMatchingStrictness = value;
    }

    /**
     * Gets the value of the dualAddressLogic property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDualAddressLogic() {
        return dualAddressLogic;
    }

    /**
     * Sets the value of the dualAddressLogic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDualAddressLogic(String value) {
        this.dualAddressLogic = value;
    }

    /**
     * Gets the value of the dpvSuccessfulStatusCondition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDPVSuccessfulStatusCondition() {
        return dpvSuccessfulStatusCondition;
    }

    /**
     * Sets the value of the dpvSuccessfulStatusCondition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDPVSuccessfulStatusCondition(String value) {
        this.dpvSuccessfulStatusCondition = value;
    }

    /**
     * Gets the value of the failOnCMRAMatch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFailOnCMRAMatch() {
        return failOnCMRAMatch;
    }

    /**
     * Sets the value of the failOnCMRAMatch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFailOnCMRAMatch(String value) {
        this.failOnCMRAMatch = value;
    }

    /**
     * Gets the value of the reportListProcessorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportListProcessorName() {
        return reportListProcessorName;
    }

    /**
     * Sets the value of the reportListProcessorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportListProcessorName(String value) {
        this.reportListProcessorName = value;
    }

    /**
     * Gets the value of the reportListFileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportListFileName() {
        return reportListFileName;
    }

    /**
     * Sets the value of the reportListFileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportListFileName(String value) {
        this.reportListFileName = value;
    }

    /**
     * Gets the value of the reportListNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportListNumber() {
        return reportListNumber;
    }

    /**
     * Sets the value of the reportListNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportListNumber(String value) {
        this.reportListNumber = value;
    }

    /**
     * Gets the value of the reportMailerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportMailerName() {
        return reportMailerName;
    }

    /**
     * Sets the value of the reportMailerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportMailerName(String value) {
        this.reportMailerName = value;
    }

    /**
     * Gets the value of the reportMailerAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportMailerAddress() {
        return reportMailerAddress;
    }

    /**
     * Sets the value of the reportMailerAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportMailerAddress(String value) {
        this.reportMailerAddress = value;
    }

    /**
     * Gets the value of the reportMailerCityLine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportMailerCityLine() {
        return reportMailerCityLine;
    }

    /**
     * Sets the value of the reportMailerCityLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportMailerCityLine(String value) {
        this.reportMailerCityLine = value;
    }

    /**
     * Gets the value of the canReportMailerCPCNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanReportMailerCPCNumber() {
        return canReportMailerCPCNumber;
    }

    /**
     * Sets the value of the canReportMailerCPCNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanReportMailerCPCNumber(String value) {
        this.canReportMailerCPCNumber = value;
    }

    /**
     * Gets the value of the canReportMailerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanReportMailerName() {
        return canReportMailerName;
    }

    /**
     * Sets the value of the canReportMailerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanReportMailerName(String value) {
        this.canReportMailerName = value;
    }

    /**
     * Gets the value of the canReportMailerAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanReportMailerAddress() {
        return canReportMailerAddress;
    }

    /**
     * Sets the value of the canReportMailerAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanReportMailerAddress(String value) {
        this.canReportMailerAddress = value;
    }

    /**
     * Gets the value of the canReportMailerCityLine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanReportMailerCityLine() {
        return canReportMailerCityLine;
    }

    /**
     * Sets the value of the canReportMailerCityLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanReportMailerCityLine(String value) {
        this.canReportMailerCityLine = value;
    }

    /**
     * Gets the value of the internationalCityStreetSearching property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternationalCityStreetSearching() {
        return internationalCityStreetSearching;
    }

    /**
     * Sets the value of the internationalCityStreetSearching property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternationalCityStreetSearching(String value) {
        this.internationalCityStreetSearching = value;
    }

    /**
     * Gets the value of the addressLineSearchOnFail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLineSearchOnFail() {
        return addressLineSearchOnFail;
    }

    /**
     * Sets the value of the addressLineSearchOnFail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLineSearchOnFail(String value) {
        this.addressLineSearchOnFail = value;
    }

    /**
     * Gets the value of the homeCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHomeCountry() {
        return homeCountry;
    }

    /**
     * Sets the value of the homeCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHomeCountry(String value) {
        this.homeCountry = value;
    }

    /**
     * Gets the value of the usImDestEnable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSImDestEnable() {
        return usImDestEnable;
    }

    /**
     * Sets the value of the usImDestEnable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSImDestEnable(String value) {
        this.usImDestEnable = value;
    }

    /**
     * Gets the value of the usImDestBarcodeID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSImDestBarcodeID() {
        return usImDestBarcodeID;
    }

    /**
     * Sets the value of the usImDestBarcodeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSImDestBarcodeID(String value) {
        this.usImDestBarcodeID = value;
    }

    /**
     * Gets the value of the usImDestSpecSvcCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSImDestSpecSvcCode() {
        return usImDestSpecSvcCode;
    }

    /**
     * Sets the value of the usImDestSpecSvcCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSImDestSpecSvcCode(String value) {
        this.usImDestSpecSvcCode = value;
    }

    /**
     * Gets the value of the usImDestSubID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSImDestSubID() {
        return usImDestSubID;
    }

    /**
     * Sets the value of the usImDestSubID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSImDestSubID(String value) {
        this.usImDestSubID = value;
    }

    /**
     * Gets the value of the usImDestDftMailingID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSImDestDftMailingID() {
        return usImDestDftMailingID;
    }

    /**
     * Sets the value of the usImDestDftMailingID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSImDestDftMailingID(String value) {
        this.usImDestDftMailingID = value;
    }

    /**
     * Gets the value of the usImDestEnableInc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSImDestEnableInc() {
        return usImDestEnableInc;
    }

    /**
     * Sets the value of the usImDestEnableInc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSImDestEnableInc(String value) {
        this.usImDestEnableInc = value;
    }

    /**
     * Gets the value of the usImOrgEnable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSImOrgEnable() {
        return usImOrgEnable;
    }

    /**
     * Sets the value of the usImOrgEnable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSImOrgEnable(String value) {
        this.usImOrgEnable = value;
    }

    /**
     * Gets the value of the usImOrgBarcodeID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSImOrgBarcodeID() {
        return usImOrgBarcodeID;
    }

    /**
     * Sets the value of the usImOrgBarcodeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSImOrgBarcodeID(String value) {
        this.usImOrgBarcodeID = value;
    }

    /**
     * Gets the value of the usImOrgSpecSvcCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSImOrgSpecSvcCode() {
        return usImOrgSpecSvcCode;
    }

    /**
     * Sets the value of the usImOrgSpecSvcCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSImOrgSpecSvcCode(String value) {
        this.usImOrgSpecSvcCode = value;
    }

    /**
     * Gets the value of the usImOrgCustID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSImOrgCustID() {
        return usImOrgCustID;
    }

    /**
     * Sets the value of the usImOrgCustID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSImOrgCustID(String value) {
        this.usImOrgCustID = value;
    }

    /**
     * Gets the value of the usImOrgRouteZip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSImOrgRouteZip() {
        return usImOrgRouteZip;
    }

    /**
     * Sets the value of the usImOrgRouteZip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSImOrgRouteZip(String value) {
        this.usImOrgRouteZip = value;
    }

    /**
     * Gets the value of the usImGenEnable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSImGenEnable() {
        return usImGenEnable;
    }

    /**
     * Sets the value of the usImGenEnable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSImGenEnable(String value) {
        this.usImGenEnable = value;
    }

    /**
     * Gets the value of the usImGenBarcodeID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSImGenBarcodeID() {
        return usImGenBarcodeID;
    }

    /**
     * Sets the value of the usImGenBarcodeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSImGenBarcodeID(String value) {
        this.usImGenBarcodeID = value;
    }

    /**
     * Gets the value of the usImGenSpecSvcCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSImGenSpecSvcCode() {
        return usImGenSpecSvcCode;
    }

    /**
     * Sets the value of the usImGenSpecSvcCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSImGenSpecSvcCode(String value) {
        this.usImGenSpecSvcCode = value;
    }

    /**
     * Gets the value of the usImGenSubID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSImGenSubID() {
        return usImGenSubID;
    }

    /**
     * Sets the value of the usImGenSubID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSImGenSubID(String value) {
        this.usImGenSubID = value;
    }

    /**
     * Gets the value of the usImGenCustID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSImGenCustID() {
        return usImGenCustID;
    }

    /**
     * Sets the value of the usImGenCustID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSImGenCustID(String value) {
        this.usImGenCustID = value;
    }

    /**
     * Gets the value of the usImGenEnableInc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSImGenEnableInc() {
        return usImGenEnableInc;
    }

    /**
     * Sets the value of the usImGenEnableInc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSImGenEnableInc(String value) {
        this.usImGenEnableInc = value;
    }

    /**
     * Gets the value of the usImErrorHandle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSImErrorHandle() {
        return usImErrorHandle;
    }

    /**
     * Sets the value of the usImErrorHandle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSImErrorHandle(String value) {
        this.usImErrorHandle = value;
    }

    /**
     * Gets the value of the performSuiteLink property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformSuiteLink() {
        return performSuiteLink;
    }

    /**
     * Sets the value of the performSuiteLink property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformSuiteLink(String value) {
        this.performSuiteLink = value;
    }

    /**
     * Gets the value of the outputStreetNameAlias property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputStreetNameAlias() {
        return outputStreetNameAlias;
    }

    /**
     * Sets the value of the outputStreetNameAlias property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputStreetNameAlias(String value) {
        this.outputStreetNameAlias = value;
    }

    /**
     * Gets the value of the outputVeriMoveDataBlock property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputVeriMoveDataBlock() {
        return outputVeriMoveDataBlock;
    }

    /**
     * Sets the value of the outputVeriMoveDataBlock property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputVeriMoveDataBlock(String value) {
        this.outputVeriMoveDataBlock = value;
    }

    /**
     * Gets the value of the dpvDetermineNoStat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDPVDetermineNoStat() {
        return dpvDetermineNoStat;
    }

    /**
     * Sets the value of the dpvDetermineNoStat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDPVDetermineNoStat(String value) {
        this.dpvDetermineNoStat = value;
    }

    /**
     * Gets the value of the dpvDetermineVacancy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDPVDetermineVacancy() {
        return dpvDetermineVacancy;
    }

    /**
     * Sets the value of the dpvDetermineVacancy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDPVDetermineVacancy(String value) {
        this.dpvDetermineVacancy = value;
    }

    /**
     * Gets the value of the outputAbbreviatedAlias property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputAbbreviatedAlias() {
        return outputAbbreviatedAlias;
    }

    /**
     * Sets the value of the outputAbbreviatedAlias property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputAbbreviatedAlias(String value) {
        this.outputAbbreviatedAlias = value;
    }

    /**
     * Gets the value of the outputPreferredAlias property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputPreferredAlias() {
        return outputPreferredAlias;
    }

    /**
     * Sets the value of the outputPreferredAlias property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputPreferredAlias(String value) {
        this.outputPreferredAlias = value;
    }

    /**
     * Gets the value of the canStandardAddressFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanStandardAddressFormat() {
        return canStandardAddressFormat;
    }

    /**
     * Sets the value of the canStandardAddressFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanStandardAddressFormat(String value) {
        this.canStandardAddressFormat = value;
    }

    /**
     * Gets the value of the canEnglishApartmentLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanEnglishApartmentLabel() {
        return canEnglishApartmentLabel;
    }

    /**
     * Sets the value of the canEnglishApartmentLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanEnglishApartmentLabel(String value) {
        this.canEnglishApartmentLabel = value;
    }

    /**
     * Gets the value of the canFrenchApartmentLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanFrenchApartmentLabel() {
        return canFrenchApartmentLabel;
    }

    /**
     * Sets the value of the canFrenchApartmentLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanFrenchApartmentLabel(String value) {
        this.canFrenchApartmentLabel = value;
    }

    /**
     * Gets the value of the canFrenchFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanFrenchFormat() {
        return canFrenchFormat;
    }

    /**
     * Sets the value of the canFrenchFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanFrenchFormat(String value) {
        this.canFrenchFormat = value;
    }

    /**
     * Gets the value of the canOutputCityFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanOutputCityFormat() {
        return canOutputCityFormat;
    }

    /**
     * Sets the value of the canOutputCityFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanOutputCityFormat(String value) {
        this.canOutputCityFormat = value;
    }

    /**
     * Gets the value of the canOutputCityAlias property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanOutputCityAlias() {
        return canOutputCityAlias;
    }

    /**
     * Sets the value of the canOutputCityAlias property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanOutputCityAlias(String value) {
        this.canOutputCityAlias = value;
    }

    /**
     * Gets the value of the canDualAddressLogic property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanDualAddressLogic() {
        return canDualAddressLogic;
    }

    /**
     * Sets the value of the canDualAddressLogic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanDualAddressLogic(String value) {
        this.canDualAddressLogic = value;
    }

    /**
     * Gets the value of the canPreferHouseNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanPreferHouseNum() {
        return canPreferHouseNum;
    }

    /**
     * Sets the value of the canPreferHouseNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanPreferHouseNum(String value) {
        this.canPreferHouseNum = value;
    }

    /**
     * Gets the value of the canRuralRouteFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanRuralRouteFormat() {
        return canRuralRouteFormat;
    }

    /**
     * Sets the value of the canRuralRouteFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanRuralRouteFormat(String value) {
        this.canRuralRouteFormat = value;
    }

    /**
     * Gets the value of the canNonCivicFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanNonCivicFormat() {
        return canNonCivicFormat;
    }

    /**
     * Sets the value of the canNonCivicFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanNonCivicFormat(String value) {
        this.canNonCivicFormat = value;
    }

    /**
     * Gets the value of the canDeliveryOfficeFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanDeliveryOfficeFormat() {
        return canDeliveryOfficeFormat;
    }

    /**
     * Sets the value of the canDeliveryOfficeFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanDeliveryOfficeFormat(String value) {
        this.canDeliveryOfficeFormat = value;
    }

    /**
     * Gets the value of the databaseUS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatabaseUS() {
        return databaseUS;
    }

    /**
     * Sets the value of the databaseUS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatabaseUS(String value) {
        this.databaseUS = value;
    }

    /**
     * Gets the value of the databaseCanada property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatabaseCanada() {
        return databaseCanada;
    }

    /**
     * Sets the value of the databaseCanada property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatabaseCanada(String value) {
        this.databaseCanada = value;
    }

    /**
     * Gets the value of the databaseInternational property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatabaseInternational() {
        return databaseInternational;
    }

    /**
     * Sets the value of the databaseInternational property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatabaseInternational(String value) {
        this.databaseInternational = value;
    }

}
