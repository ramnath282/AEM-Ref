
package com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RowResponseTypeTest {

  private RowResponseType impl = null;
  private UserFieldsType userfields = new UserFieldsType();

  @Before
  public void setUp() throws Exception {
    impl = new RowResponseType();
    impl.setConfidence("dummy_string");
    impl.setRecordType("dummy_string");
    impl.setRecordTypeDefault("dummy_string");
    impl.setMultipleMatches("dummy_string");
    impl.setCouldNotValidate("dummy_string");
    impl.setCountryLevel("dummy_string");
    impl.setProcessedBy("dummy_string");
    impl.setAddressFormat("dummy_string");
    impl.setMatchScore("dummy_string");
    impl.setMultimatchCount("dummy_string");
    impl.setAddressLine1("dummy_string");
    impl.setAddressLine2("dummy_string");
    impl.setAddressLine3("dummy_string");
    impl.setAddressLine4("dummy_string");
    impl.setCity("dummy_string");
    impl.setCityResult("dummy_string");
    impl.setStateProvince("dummy_string");
    impl.setStateProvinceResult("dummy_string");
    impl.setPostalCode("dummy_string");
    impl.setPostalCodeResult("dummy_string");
    impl.setPostalCodeCityResult("dummy_string");
    impl.setAddressRecordResult("dummy_string");
    impl.setPostalCodeSource("dummy_string");
    impl.setPostalCodeType("dummy_string");
    impl.setPostalCodeBase("dummy_string");
    impl.setPostalCodeAddOn("dummy_string");
    impl.setCountry("dummy_string");
    impl.setCountryResult("dummy_string");
    impl.setFirmName("dummy_string");
    impl.setFirmNameResult("dummy_string");
    impl.setAdditionalInputData("dummy_string");
    impl.setHouseNumber("dummy_string");
    impl.setHouseNumberResult("dummy_string");
    impl.setLeadingDirectional("dummy_string");
    impl.setLeadingDirectionalResult("dummy_string");
    impl.setStreetName("dummy_string");
    impl.setStreetResult("dummy_string");
    impl.setStreetNameResult("dummy_string");
    impl.setStreetNameAliasType("dummy_string");
    impl.setStreetNamePreferredAliasResult("dummy_string");
    impl.setStreetNameAbbreviatedAliasResult("dummy_string");
    impl.setStreetSuffix("dummy_string");
    impl.setStreetSuffixResult("dummy_string");
    impl.setTrailingDirectional("dummy_string");
    impl.setTrailingDirectionalResult("dummy_string");
    impl.setApartmentLabel("dummy_string");
    impl.setApartmentLabelResult("dummy_string");
    impl.setApartmentNumber("dummy_string");
    impl.setApartmentNumberResult("dummy_string");
    impl.setApartmentLabel2("dummy_string");
    impl.setApartmentLabel2Result("dummy_string");
    impl.setApartmentNumber2("dummy_string");
    impl.setApartmentNumber2Result("dummy_string");
    impl.setPrivateMailbox("dummy_string");
    impl.setPrivateMailboxType("dummy_string");
    impl.setAbbreviatedAliasResult("dummy_string");
    impl.setPreferredAliasResult("dummy_string");
    impl.setCanadianDeliveryInstallationAreaName("dummy_string");
    impl.setCanadianDeliveryInstallationType("dummy_string");
    impl.setCanadianDeliveryInstallationQualifierName("dummy_string");
    impl.setHouseNumberInput("dummy_string");
    impl.setLeadingDirectionalInput("dummy_string");
    impl.setStreetNameInput("dummy_string");
    impl.setStreetSuffixInput("dummy_string");
    impl.setTrailingDirectionalInput("dummy_string");
    impl.setApartmentLabelInput("dummy_string");
    impl.setApartmentNumberInput("dummy_string");
    impl.setPrivateMailboxInput("dummy_string");
    impl.setPrivateMailboxTypeInput("dummy_string");
    impl.setCanadianDeliveryInstallationAreaNameInput("dummy_string");
    impl.setCanadianDeliveryInstallationTypeInput("dummy_string");
    impl.setCanadianDeliveryInstallationQualifierNameInput("dummy_string");
    impl.setCityInput("dummy_string");
    impl.setStateProvinceInput("dummy_string");
    impl.setPostalCodeInput("dummy_string");
    impl.setCountryInput("dummy_string");
    impl.setFirmNameInput("dummy_string");
    impl.setPostalBarCode("dummy_string");
    impl.setCanadianSERPCode("dummy_string");
    impl.setUsImDestinationDATF("dummy_string");
    impl.setUsImOriginDATF("dummy_string");
    impl.setUsImGenericDATF("dummy_string");
    impl.setUsImDestinationRaw("dummy_string");
    impl.setUsImOriginRaw("dummy_string");
    impl.setUsImGenericRaw("dummy_string");
    impl.setSuiteLinkReturnCode("dummy_string");
    impl.setSuiteLinkMatchCode("dummy_string");
    impl.setSuiteLinkFidelity("dummy_string");
    impl.setAddressBlock1("dummy_string");
    impl.setAddressBlock2("dummy_string");
    impl.setAddressBlock3("dummy_string");
    impl.setAddressBlock4("dummy_string");
    impl.setAddressBlock5("dummy_string");
    impl.setAddressBlock6("dummy_string");
    impl.setAddressBlock7("dummy_string");
    impl.setAddressBlock8("dummy_string");
    impl.setAddressBlock9("dummy_string");
    impl.setVeriMoveDataBlock("dummy_string");
    impl.setIntINSEECode("dummy_string");
    impl.setIntHexaviaCode("dummy_string");
    impl.setStatus("dummy_string");
    impl.setStatusCode("dummy_string");
    impl.setStatusDescription("dummy_string");
    impl.setUserFields(userfields);
    impl.setDPVFootnote("dummy_string");
    impl.setCMRA("dummy_string");
    impl.setDPV("dummy_string");
    impl.setDPVNoStat("dummy_string");
    impl.setDPVVacant("dummy_string");
    impl.setPOBox("dummy_string");
    impl.setPOBoxInput("dummy_string");
    impl.setPOBoxResult("dummy_string");
    impl.setRDI("dummy_string");
    impl.setRRHC("dummy_string");
    impl.setRRHCInput("dummy_string");
    impl.setRRHCResult("dummy_string");
    impl.setRRHCType("dummy_string");
    impl.setUSAltAddr("dummy_string");
    impl.setUSBCCheckDigit("dummy_string");
    impl.setUSCarrierRouteCode("dummy_string");
    impl.setUSCongressionalDistrict("dummy_string");
    impl.setUSCountyName("dummy_string");
    impl.setUSFinanceNumber("dummy_string");
    impl.setUSFIPSCountyNumber("dummy_string");
    impl.setUSLACS("dummy_string");
    impl.setUSLACSReturnCode("dummy_string");
    impl.setUSLastLineNumber("dummy_string");
    impl.setUSLOTCode("dummy_string");
    impl.setUSLOTHex("dummy_string");
    impl.setUSLOTSequence("dummy_string");
    impl.setUSUrbanName("dummy_string");
    impl.setUSUrbanNameInput("dummy_string");
    impl.setUSUrbanNameResult("dummy_string");
    impl.toString();
  }

  @Test
  public void testGetCMRA() throws Exception {
    Assert.assertEquals("dummy_string", impl.getCMRA());
  }

  @Test
  public void testGetDPVFootnote() throws Exception {
    Assert.assertEquals("dummy_string", impl.getDPVFootnote());
  }

  @Test
  public void testGetDPVNoStat() throws Exception {
    Assert.assertEquals("dummy_string", impl.getDPVNoStat());
  }

  @Test
  public void testGetDPVVacant() throws Exception {
    Assert.assertEquals("dummy_string", impl.getDPVVacant());
  }

  @Test
  public void testGetPOBox() throws Exception {
    Assert.assertEquals("dummy_string", impl.getPOBox());
  }

  @Test
  public void testGetPOBoxInput() throws Exception {
    Assert.assertEquals("dummy_string", impl.getPOBoxInput());
  }

  @Test
  public void testGetPOBoxResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getPOBoxResult());
  }

  @Test
  public void testGetRDI() throws Exception {
    Assert.assertEquals("dummy_string", impl.getRDI());
  }

  @Test
  public void testGetRRHC() throws Exception {
    Assert.assertEquals("dummy_string", impl.getRRHC());
  }

  @Test
  public void testGetRRHCInput() throws Exception {
    Assert.assertEquals("dummy_string", impl.getRRHCInput());
  }

  @Test
  public void testGetRRHCType() throws Exception {
    Assert.assertEquals("dummy_string", impl.getRRHCType());
  }

  @Test
  public void testGetUSAltAddr() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUSAltAddr());
  }

  @Test
  public void testGetUSBCCheckDigit() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUSBCCheckDigit());
  }

  @Test
  public void testGetUSCarrierRouteCode() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUSCarrierRouteCode());
  }

  @Test
  public void testGetUSCongressionalDistrict() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUSCongressionalDistrict());
  }

  @Test
  public void testGetUSCountyName() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUSCountyName());
  }

  @Test
  public void testGetUSFinanceNumber() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUSFinanceNumber());
  }

  @Test
  public void testGetUSFIPSCountyNumber() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUSFIPSCountyNumber());
  }

  @Test
  public void testGetUSLACS() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUSLACS());
  }

  @Test
  public void testGetUSLACSReturnCode() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUSLACSReturnCode());
  }

  @Test
  public void testGetUSLOTCode() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUSLOTCode());
  }

  @Test
  public void testGetUSLOTHex() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUSLOTHex());
  }

  @Test
  public void testGetUSLOTSequence() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUSLOTSequence());
  }

  @Test
  public void testGetUSUrbanName() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUSUrbanName());
  }

  @Test
  public void testGetUSUrbanNameInput() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUSUrbanNameInput());
  }

  @Test
  public void testGetUSUrbanNameResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUSUrbanNameResult());
  }

  @Test
  public void testGetAddressLine4() throws Exception {
    Assert.assertEquals("dummy_string", impl.getAddressLine4());
  }

  @Test
  public void testGetAddressLine2() throws Exception {
    Assert.assertEquals("dummy_string", impl.getAddressLine2());
  }

  @Test
  public void testGetSuiteLinkReturnCode() throws Exception {
    Assert.assertEquals("dummy_string", impl.getSuiteLinkReturnCode());
  }

  @Test
  public void testGetAddressLine3() throws Exception {
    Assert.assertEquals("dummy_string", impl.getAddressLine3());
  }

  @Test
  public void testGetPostalCodeType() throws Exception {
    Assert.assertEquals("dummy_string", impl.getPostalCodeType());
  }

  @Test
  public void testGetPrivateMailbox() throws Exception {
    Assert.assertEquals("dummy_string", impl.getPrivateMailbox());
  }

  @Test
  public void testGetStatus() throws Exception {
    Assert.assertEquals("dummy_string", impl.getStatus());
  }

  @Test
  public void testGetUserFields() throws Exception {
    Assert.assertEquals(userfields, impl.getUserFields());
  }

  @Test
  public void testGetApartmentNumberResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getApartmentNumberResult());
  }

  @Test
  public void testGetCanadianDeliveryInstallationQualifierNameInput() throws Exception {
    Assert.assertEquals("dummy_string", impl.getCanadianDeliveryInstallationQualifierNameInput());
  }

  @Test
  public void testGetCanadianDeliveryInstallationQualifierName() throws Exception {
    Assert.assertEquals("dummy_string", impl.getCanadianDeliveryInstallationQualifierName());
  }

  @Test
  public void testGetCityResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getCityResult());
  }

  @Test
  public void testGetStreetName() throws Exception {
    Assert.assertEquals("dummy_string", impl.getStreetName());
  }

  @Test
  public void testGetRecordTypeDefault() throws Exception {
    Assert.assertEquals("dummy_string", impl.getRecordTypeDefault());
  }

  @Test
  public void testGetTrailingDirectionalResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getTrailingDirectionalResult());
  }

  @Test
  public void testGetMultipleMatches() throws Exception {
    Assert.assertEquals("dummy_string", impl.getMultipleMatches());
  }

  @Test
  public void testGetStreetNameInput() throws Exception {
    Assert.assertEquals("dummy_string", impl.getStreetNameInput());
  }

  @Test
  public void testGetApartmentLabel2() throws Exception {
    Assert.assertEquals("dummy_string", impl.getApartmentLabel2());
  }

  @Test
  public void testGetCountryLevel() throws Exception {
    Assert.assertEquals("dummy_string", impl.getCountryLevel());
  }

  @Test
  public void testGetStreetSuffix() throws Exception {
    Assert.assertEquals("dummy_string", impl.getStreetSuffix());
  }

  @Test
  public void testGetCityInput() throws Exception {
    Assert.assertEquals("dummy_string", impl.getCityInput());
  }

  @Test
  public void testGetConfidence() throws Exception {
    Assert.assertEquals("dummy_string", impl.getConfidence());
  }

  @Test
  public void testGetRecordType() throws Exception {
    Assert.assertEquals("dummy_string", impl.getRecordType());
  }

  @Test
  public void testGetApartmentNumber2() throws Exception {
    Assert.assertEquals("dummy_string", impl.getApartmentNumber2());
  }

  @Test
  public void testGetLeadingDirectional() throws Exception {
    Assert.assertEquals("dummy_string", impl.getLeadingDirectional());
  }

  @Test
  public void testGetStreetNameAbbreviatedAliasResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getStreetNameAbbreviatedAliasResult());
  }

  @Test
  public void testGetUsImOriginRaw() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUsImOriginRaw());
  }

  @Test
  public void testGetPostalCodeSource() throws Exception {
    Assert.assertEquals("dummy_string", impl.getPostalCodeSource());
  }

  @Test
  public void testGetLeadingDirectionalResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getLeadingDirectionalResult());
  }

  @Test
  public void testGetUsImGenericDATF() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUsImGenericDATF());
  }

  @Test
  public void testGetAddressLine1() throws Exception {
    Assert.assertEquals("dummy_string", impl.getAddressLine1());
  }

  @Test
  public void testGetStateProvinceResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getStateProvinceResult());
  }

  @Test
  public void testGetUsImDestinationRaw() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUsImDestinationRaw());
  }

  @Test
  public void testGetApartmentLabel() throws Exception {
    Assert.assertEquals("dummy_string", impl.getApartmentLabel());
  }

  @Test
  public void testGetCountryInput() throws Exception {
    Assert.assertEquals("dummy_string", impl.getCountryInput());
  }

  @Test
  public void testGetVeriMoveDataBlock() throws Exception {
    Assert.assertEquals("dummy_string", impl.getVeriMoveDataBlock());
  }

  @Test
  public void testGetMultimatchCount() throws Exception {
    Assert.assertEquals("dummy_string", impl.getMultimatchCount());
  }

  @Test
  public void testGetSuiteLinkMatchCode() throws Exception {
    Assert.assertEquals("dummy_string", impl.getSuiteLinkMatchCode());
  }

  @Test
  public void testGetUsImDestinationDATF() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUsImDestinationDATF());
  }

  @Test
  public void testGetCanadianSERPCode() throws Exception {
    Assert.assertEquals("dummy_string", impl.getCanadianSERPCode());
  }

  @Test
  public void testGetStreetSuffixInput() throws Exception {
    Assert.assertEquals("dummy_string", impl.getStreetSuffixInput());
  }

  @Test
  public void testGetPrivateMailboxInput() throws Exception {
    Assert.assertEquals("dummy_string", impl.getPrivateMailboxInput());
  }

  @Test
  public void testGetAddressBlock8() throws Exception {
    Assert.assertEquals("dummy_string", impl.getAddressBlock8());
  }

  @Test
  public void testGetAddressBlock7() throws Exception {
    Assert.assertEquals("dummy_string", impl.getAddressBlock7());
  }

  @Test
  public void testGetApartmentLabel2Result() throws Exception {
    Assert.assertEquals("dummy_string", impl.getApartmentLabel2Result());
  }

  @Test
  public void testGetAddressBlock9() throws Exception {
    Assert.assertEquals("dummy_string", impl.getAddressBlock9());
  }

  @Test
  public void testGetAddressBlock4() throws Exception {
    Assert.assertEquals("dummy_string", impl.getAddressBlock4());
  }

  @Test
  public void testGetLeadingDirectionalInput() throws Exception {
    Assert.assertEquals("dummy_string", impl.getLeadingDirectionalInput());
  }

  @Test
  public void testGetAddressBlock3() throws Exception {
    Assert.assertEquals("dummy_string", impl.getAddressBlock3());
  }

  @Test
  public void testGetCanadianDeliveryInstallationType() throws Exception {
    Assert.assertEquals("dummy_string", impl.getCanadianDeliveryInstallationType());
  }

  @Test
  public void testGetAddressBlock6() throws Exception {
    Assert.assertEquals("dummy_string", impl.getAddressBlock6());
  }

  @Test
  public void testGetAddressBlock5() throws Exception {
    Assert.assertEquals("dummy_string", impl.getAddressBlock5());
  }

  @Test
  public void testGetPostalCode() throws Exception {
    Assert.assertEquals("dummy_string", impl.getPostalCode());
  }

  @Test
  public void testGetCountryResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getCountryResult());
  }

  @Test
  public void testGetHouseNumberResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getHouseNumberResult());
  }

  @Test
  public void testGetPostalBarCode() throws Exception {
    Assert.assertEquals("dummy_string", impl.getPostalBarCode());
  }

  @Test
  public void testGetAddressBlock2() throws Exception {
    Assert.assertEquals("dummy_string", impl.getAddressBlock2());
  }

  @Test
  public void testGetAddressBlock1() throws Exception {
    Assert.assertEquals("dummy_string", impl.getAddressBlock1());
  }

  @Test
  public void testGetPostalCodeCityResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getPostalCodeCityResult());
  }

  @Test
  public void testGetApartmentNumberInput() throws Exception {
    Assert.assertEquals("dummy_string", impl.getApartmentNumberInput());
  }

  @Test
  public void testGetStatusDescription() throws Exception {
    Assert.assertEquals("dummy_string", impl.getStatusDescription());
  }

  @Test
  public void testGetApartmentLabelInput() throws Exception {
    Assert.assertEquals("dummy_string", impl.getApartmentLabelInput());
  }

  @Test
  public void testGetMatchScore() throws Exception {
    Assert.assertEquals("dummy_string", impl.getMatchScore());
  }

  @Test
  public void testGetPrivateMailboxTypeInput() throws Exception {
    Assert.assertEquals("dummy_string", impl.getPrivateMailboxTypeInput());
  }

  @Test
  public void testGetCountry() throws Exception {
    Assert.assertEquals("dummy_string", impl.getCountry());
  }

  @Test
  public void testGetStreetSuffixResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getStreetSuffixResult());
  }

  @Test
  public void testGetUsImGenericRaw() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUsImGenericRaw());
  }

  @Test
  public void testGetStreetResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getStreetResult());
  }

  @Test
  public void testGetApartmentNumber2Result() throws Exception {
    Assert.assertEquals("dummy_string", impl.getApartmentNumber2Result());
  }

  @Test
  public void testGetCanadianDeliveryInstallationTypeInput() throws Exception {
    Assert.assertEquals("dummy_string", impl.getCanadianDeliveryInstallationTypeInput());
  }

  @Test
  public void testGetPostalCodeInput() throws Exception {
    Assert.assertEquals("dummy_string", impl.getPostalCodeInput());
  }

  @Test
  public void testGetStreetNamePreferredAliasResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getStreetNamePreferredAliasResult());
  }

  @Test
  public void testGetStateProvince() throws Exception {
    Assert.assertEquals("dummy_string", impl.getStateProvince());
  }

  @Test
  public void testGetHouseNumberInput() throws Exception {
    Assert.assertEquals("dummy_string", impl.getHouseNumberInput());
  }

  @Test
  public void testGetPostalCodeAddOn() throws Exception {
    Assert.assertEquals("dummy_string", impl.getPostalCodeAddOn());
  }

  @Test
  public void testGetAddressFormat() throws Exception {
    Assert.assertEquals("dummy_string", impl.getAddressFormat());
  }

  @Test
  public void testGetAddressRecordResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getAddressRecordResult());
  }

  @Test
  public void testGetPostalCodeBase() throws Exception {
    Assert.assertEquals("dummy_string", impl.getPostalCodeBase());
  }

  @Test
  public void testGetUsImOriginDATF() throws Exception {
    Assert.assertEquals("dummy_string", impl.getUsImOriginDATF());
  }

  @Test
  public void testGetStreetNameAliasType() throws Exception {
    Assert.assertEquals("dummy_string", impl.getStreetNameAliasType());
  }

  @Test
  public void testGetCity() throws Exception {
    Assert.assertEquals("dummy_string", impl.getCity());
  }

  @Test
  public void testGetPreferredAliasResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getPreferredAliasResult());
  }

  @Test
  public void testGetIntINSEECode() throws Exception {
    Assert.assertEquals("dummy_string", impl.getIntINSEECode());
  }

  @Test
  public void testGetSuiteLinkFidelity() throws Exception {
    Assert.assertEquals("dummy_string", impl.getSuiteLinkFidelity());
  }

  @Test
  public void testGetFirmNameResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getFirmNameResult());
  }

  @Test
  public void testGetHouseNumber() throws Exception {
    Assert.assertEquals("dummy_string", impl.getHouseNumber());
  }

  @Test
  public void testGetPrivateMailboxType() throws Exception {
    Assert.assertEquals("dummy_string", impl.getPrivateMailboxType());
  }

  @Test
  public void testGetAdditionalInputData() throws Exception {
    Assert.assertEquals("dummy_string", impl.getAdditionalInputData());
  }

  @Test
  public void testGetStreetNameResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getStreetNameResult());
  }

  @Test
  public void testGetStateProvinceInput() throws Exception {
    Assert.assertEquals("dummy_string", impl.getStateProvinceInput());
  }

  @Test
  public void testGetProcessedBy() throws Exception {
    Assert.assertEquals("dummy_string", impl.getProcessedBy());
  }

  @Test
  public void testGetCanadianDeliveryInstallationAreaNameInput() throws Exception {
    Assert.assertEquals("dummy_string", impl.getCanadianDeliveryInstallationAreaNameInput());
  }

  @Test
  public void testGetApartmentNumber() throws Exception {
    Assert.assertEquals("dummy_string", impl.getApartmentNumber());
  }

  @Test
  public void testGetCouldNotValidate() throws Exception {
    Assert.assertEquals("dummy_string", impl.getCouldNotValidate());
  }

  @Test
  public void testGetIntHexaviaCode() throws Exception {
    Assert.assertEquals("dummy_string", impl.getIntHexaviaCode());
  }

  @Test
  public void testGetApartmentLabelResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getApartmentLabelResult());
  }

  @Test
  public void testGetPostalCodeResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getPostalCodeResult());
  }

  @Test
  public void testGetTrailingDirectionalInput() throws Exception {
    Assert.assertEquals("dummy_string", impl.getTrailingDirectionalInput());
  }

  @Test
  public void testGetFirmName() throws Exception {
    Assert.assertEquals("dummy_string", impl.getFirmName());
  }

  @Test
  public void testGetCanadianDeliveryInstallationAreaName() throws Exception {
    Assert.assertEquals("dummy_string", impl.getCanadianDeliveryInstallationAreaName());
  }

  @Test
  public void testGetStatusCode() throws Exception {
    Assert.assertEquals("dummy_string", impl.getStatusCode());
  }

  @Test
  public void testGetTrailingDirectional() throws Exception {
    Assert.assertEquals("dummy_string", impl.getTrailingDirectional());
  }

  @Test
  public void testGetAbbreviatedAliasResult() throws Exception {
    Assert.assertEquals("dummy_string", impl.getAbbreviatedAliasResult());
  }

  @Test
  public void testGetFirmNameInput() throws Exception {
    Assert.assertEquals("dummy_string", impl.getFirmNameInput());
  }

}
