package com.mattel.ecomm.esb.cdm.dtc.validateaddress.core1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ObjectFactoryTest {
  private ObjectFactory impl = null;

  @Before
  public void setUp() throws Exception {
    impl = new ObjectFactory();
  }

  @Test
  public void testUSImGenSpecSvcCode() {
    Assert.assertNotNull(impl.createUSImGenSpecSvcCode("test"));
  }

  @Test
  public void testPOBox() {
    Assert.assertNotNull(impl.createPOBox("test"));
  }

  @Test
  public void testCanNonCivicFormat() {
    Assert.assertNotNull(impl.createCanNonCivicFormat("test"));
  }

  @Test
  public void testCountryResult() {
    Assert.assertNotNull(impl.createCountryResult("test"));
  }

  @Test
  public void testReportMailerName() {
    Assert.assertNotNull(impl.createReportMailerName("test"));
  }

  @Test
  public void testError() {
    Assert.assertNotNull(impl.createError("test"));
  }

  @Test
  public void testOutputCountryFormat() {
    Assert.assertNotNull(impl.createOutputCountryFormat("test"));
  }

  @Test
  public void testCityInput() {
    Assert.assertNotNull(impl.createCityInput("test"));
  }

  @Test
  public void testCouldNotValidate() {
    Assert.assertNotNull(impl.createCouldNotValidate("test"));
  }

  @Test
  public void testSuiteLinkMatchCode() {
    Assert.assertNotNull(impl.createSuiteLinkMatchCode("test"));
  }

  @Test
  public void testCanEnglishApartmentLabel() {
    Assert.assertNotNull(impl.createCanEnglishApartmentLabel("test"));
  }

  @Test
  public void testCanadianSERPCode() {
    Assert.assertNotNull(impl.createCanadianSERPCode("test"));
  }

  @Test
  public void testUSImOrgEnable() {
    Assert.assertNotNull(impl.createUSImOrgEnable("test"));
  }

  @Test
  public void testOutputPreferredAlias() {
    Assert.assertNotNull(impl.createOutputPreferredAlias("test"));
  }

  @Test
  public void testUSLOTSequence() {
    Assert.assertNotNull(impl.createUSLOTSequence("test"));
  }

  @Test
  public void testOutputFormattedOnFail() {
    Assert.assertNotNull(impl.createOutputFormattedOnFail("test"));
  }

  @Test
  public void testPreferredAliasResult() {
    Assert.assertNotNull(impl.createPreferredAliasResult("test"));
  }

  @Test
  public void testUSImDestEnable() {
    Assert.assertNotNull(impl.createUSImDestEnable("test"));
  }

  @Test
  public void testCanadianDeliveryInstallationAreaNameInput() {
    Assert.assertNotNull(impl.createCanadianDeliveryInstallationAreaNameInput("test"));
  }

  @Test
  public void testPerformEWS() {
    Assert.assertNotNull(impl.createPerformEWS("test"));
  }

  @Test
  public void testUSImOrgCustID() {
    Assert.assertNotNull(impl.createUSImOrgCustID("test"));
  }

  @Test
  public void testAddressRecordResult() {
    Assert.assertNotNull(impl.createAddressRecordResult("test"));
  }

  @Test
  public void testConfidence() {
    Assert.assertNotNull(impl.createConfidence("test"));
  }

  @Test
  public void testExtractUrb() {
    Assert.assertNotNull(impl.createExtractUrb("test"));
  }

  @Test
  public void testOutputFieldLevelReturnCodes() {
    Assert.assertNotNull(impl.createOutputFieldLevelReturnCodes("test"));
  }

  @Test
  public void testStreetSuffixResult() {
    Assert.assertNotNull(impl.createStreetSuffixResult("test"));
  }

  @Test
  public void testPostalCodeSource() {
    Assert.assertNotNull(impl.createPostalCodeSource("test"));
  }

  @Test
  public void testDatabaseUS() {
    Assert.assertNotNull(impl.createDatabaseUS("test"));
  }

  @Test
  public void testStreetResult() {
    Assert.assertNotNull(impl.createStreetResult("test"));
  }

  @Test
  public void testUsImDestinationDATF() {
    Assert.assertNotNull(impl.createUsImDestinationDATF("test"));
  }

  @Test
  public void testStreetNameInput() {
    Assert.assertNotNull(impl.createStreetNameInput("test"));
  }

  @Test
  public void testMaximumResults() {
    Assert.assertNotNull(impl.createMaximumResults("test"));
  }

  @Test
  public void testRRHCType() {
    Assert.assertNotNull(impl.createRRHCType("test"));
  }

  @Test
  public void testRRHCResult() {
    Assert.assertNotNull(impl.createRRHCResult("test"));
  }

  @Test
  public void testUSLACS() {
    Assert.assertNotNull(impl.createUSLACS("test"));
  }

  @Test
  public void testDPVSuccessfulStatusCondition() {
    Assert.assertNotNull(impl.createDPVSuccessfulStatusCondition("test"));
  }

  @Test
  public void testMultipleMatches() {
    Assert.assertNotNull(impl.createMultipleMatches("test"));
  }

  @Test
  public void testApartmentLabel2() {
    Assert.assertNotNull(impl.createApartmentLabel2("test"));
  }

  @Test
  public void testOutputMultinationalCharacters() {
    Assert.assertNotNull(impl.createOutputMultinationalCharacters("test"));
  }

  @Test
  public void testDPVVacant() {
    Assert.assertNotNull(impl.createDPVVacant("test"));
  }

  @Test
  public void testCanadianDeliveryInstallationQualifierName() {
    Assert.assertNotNull(impl.createCanadianDeliveryInstallationQualifierName("test"));
  }

  @Test
  public void testFirmNameInput() {
    Assert.assertNotNull(impl.createFirmNameInput("test"));
  }

  @Test
  public void testAddressLine4() {
    Assert.assertNotNull(impl.createAddressLine4("test"));
  }

  @Test
  public void testApartmentNumber2() {
    Assert.assertNotNull(impl.createApartmentNumber2("test"));
  }

  @Test
  public void testAddressLine1() {
    Assert.assertNotNull(impl.createAddressLine1("test"));
  }

  @Test
  public void testAddressLine2() {
    Assert.assertNotNull(impl.createAddressLine2("test"));
  }

  @Test
  public void testAddressLine3() {
    Assert.assertNotNull(impl.createAddressLine3("test"));
  }

  @Test
  public void testCanDeliveryOfficeFormat() {
    Assert.assertNotNull(impl.createCanDeliveryOfficeFormat("test"));
  }

  @Test
  public void testPrivateMailbox() {
    Assert.assertNotNull(impl.createPrivateMailbox("test"));
  }

  @Test
  public void testPostalCodeType() {
    Assert.assertNotNull(impl.createPostalCodeType("test"));
  }

  @Test
  public void testOutputCasing() {
    Assert.assertNotNull(impl.createOutputCasing("test"));
  }

  @Test
  public void testStateProvince() {
    Assert.assertNotNull(impl.createStateProvince("test"));
  }

  @Test
  public void testCanDualAddressLogic() {
    Assert.assertNotNull(impl.createCanDualAddressLogic("test"));
  }

  @Test
  public void testUSImErrorHandle() {
    Assert.assertNotNull(impl.createUSImErrorHandle("test"));
  }

  @Test
  public void testPrivateMailboxTypeInput() {
    Assert.assertNotNull(impl.createPrivateMailboxTypeInput("test"));
  }

  @Test
  public void testPostalCodeResult() {
    Assert.assertNotNull(impl.createPostalCodeResult("test"));
  }

  @Test
  public void testApartmentLabelResult() {
    Assert.assertNotNull(impl.createApartmentLabelResult("test"));
  }

  @Test
  public void testCanRuralRouteFormat() {
    Assert.assertNotNull(impl.createCanRuralRouteFormat("test"));
  }

  @Test
  public void testMatchScore() {
    Assert.assertNotNull(impl.createMatchScore("test"));
  }

  @Test
  public void testHomeCountry() {
    Assert.assertNotNull(impl.createHomeCountry("test"));
  }

  @Test
  public void testMultimatchCount() {
    Assert.assertNotNull(impl.createMultimatchCount("test"));
  }

  @Test
  public void testApartmentNumber() {
    Assert.assertNotNull(impl.createApartmentNumber("test"));
  }

  @Test
  public void testLeadingDirectionalInput() {
    Assert.assertNotNull(impl.createLeadingDirectionalInput("test"));
  }

  @Test
  public void testStreetSuffix() {
    Assert.assertNotNull(impl.createStreetSuffix("test"));
  }

  @Test
  public void testCity() {
    Assert.assertNotNull(impl.createCity("test"));
  }

  @Test
  public void testPostalCodeBase() {
    Assert.assertNotNull(impl.createPostalCodeBase("test"));
  }

  @Test
  public void testUSAltAddr() {
    Assert.assertNotNull(impl.createUSAltAddr("test"));
  }

  @Test
  public void testUsImGenericDATF() {
    Assert.assertNotNull(impl.createUsImGenericDATF("test"));
  }

  @Test
  public void testPerformDPV() {
    Assert.assertNotNull(impl.createPerformDPV("test"));
  }

  @Test
  public void testPrivateMailboxInput() {
    Assert.assertNotNull(impl.createPrivateMailboxInput("test"));
  }

  @Test
  public void testDPV() {
    Assert.assertNotNull(impl.createDPV("test"));
  }

  @Test
  public void testCanStandardAddressFormat() {
    Assert.assertNotNull(impl.createCanStandardAddressFormat("test"));
  }

  @Test
  public void testUSImDestSubID() {
    Assert.assertNotNull(impl.createUSImDestSubID("test"));
  }

  @Test
  public void testPostalCodeAddOn() {
    Assert.assertNotNull(impl.createPostalCodeAddOn("test"));
  }

  @Test
  public void testStreetNameAliasType() {
    Assert.assertNotNull(impl.createStreetNameAliasType("test"));
  }

  @Test
  public void testUSFinanceNumber() {
    Assert.assertNotNull(impl.createUSFinanceNumber("test"));
  }

  @Test
  public void testRecordTypeDefault() {
    Assert.assertNotNull(impl.createRecordTypeDefault("test"));
  }

  @Test
  public void testCanReportMailerAddress() {
    Assert.assertNotNull(impl.createCanReportMailerAddress("test"));
  }

  @Test
  public void testStateProvinceInput() {
    Assert.assertNotNull(impl.createStateProvinceInput("test"));
  }

  @Test
  public void testPerformESM() {
    Assert.assertNotNull(impl.createPerformESM("test"));
  }

  @Test
  public void testUSLastLineNumber() {
    Assert.assertNotNull(impl.createUSLastLineNumber("test"));
  }

  @Test
  public void testPerformCanadianProcessing() {
    Assert.assertNotNull(impl.createPerformCanadianProcessing("test"));
  }

  @Test
  public void testStreetMatchingStrictness() {
    Assert.assertNotNull(impl.createStreetMatchingStrictness("test"));
  }

  @Test
  public void testDualAddressLogic() {
    Assert.assertNotNull(impl.createDualAddressLogic("test"));
  }

  @Test
  public void testOutputShortCityName() {
    Assert.assertNotNull(impl.createOutputShortCityName("test"));
  }

  @Test
  public void testCanFrenchFormat() {
    Assert.assertNotNull(impl.createCanFrenchFormat("test"));
  }

  @Test
  public void testCanadianDeliveryInstallationType() {
    Assert.assertNotNull(impl.createCanadianDeliveryInstallationType("test"));
  }

  @Test
  public void testIntHexaviaCode() {
    Assert.assertNotNull(impl.createIntHexaviaCode("test"));
  }

  @Test
  public void testPerformInternationalProcessing() {
    Assert.assertNotNull(impl.createPerformInternationalProcessing("test"));
  }

  @Test
  public void testReportMailerCityLine() {
    Assert.assertNotNull(impl.createReportMailerCityLine("test"));
  }

  @Test
  public void testKeepMultimatch() {
    Assert.assertNotNull(impl.createKeepMultimatch("test"));
  }

  @Test
  public void testStandardAddressFormat() {
    Assert.assertNotNull(impl.createStandardAddressFormat("test"));
  }

  @Test
  public void testHouseNumberResult() {
    Assert.assertNotNull(impl.createHouseNumberResult("test"));
  }

  @Test
  public void testDPVDetermineNoStat() {
    Assert.assertNotNull(impl.createDPVDetermineNoStat("test"));
  }

  @Test
  public void testUsImGenericRaw() {
    Assert.assertNotNull(impl.createUsImGenericRaw("test"));
  }

  @Test
  public void testReportMailerAddress() {
    Assert.assertNotNull(impl.createReportMailerAddress("test"));
  }

  @Test
  public void testCanOutputCityFormat() {
    Assert.assertNotNull(impl.createCanOutputCityFormat("test"));
  }

  @Test
  public void testRRHC() {
    Assert.assertNotNull(impl.createRRHC("test"));
  }

  @Test
  public void testPerformSuiteLink() {
    Assert.assertNotNull(impl.createPerformSuiteLink("test"));
  }

  @Test
  public void testCanadianDeliveryInstallationTypeInput() {
    Assert.assertNotNull(impl.createCanadianDeliveryInstallationTypeInput("test"));
  }

  @Test
  public void testHouseNumberInput() {
    Assert.assertNotNull(impl.createHouseNumberInput("test"));
  }

  @Test
  public void testAdditionalInputData() {
    Assert.assertNotNull(impl.createAdditionalInputData("test"));
  }

  @Test
  public void testPOBoxInput() {
    Assert.assertNotNull(impl.createPOBoxInput("test"));
  }

  @Test
  public void testFirmNameResult() {
    Assert.assertNotNull(impl.createFirmNameResult("test"));
  }

  @Test
  public void testReportListProcessorName() {
    Assert.assertNotNull(impl.createReportListProcessorName("test"));
  }

  @Test
  public void testIntINSEECode() {
    Assert.assertNotNull(impl.createIntINSEECode("test"));
  }

  @Test
  public void testLeadingDirectionalResult() {
    Assert.assertNotNull(impl.createLeadingDirectionalResult("test"));
  }

  @Test
  public void testPerformLOT() {
    Assert.assertNotNull(impl.createPerformLOT("test"));
  }

  @Test
  public void testUSLACSReturnCode() {
    Assert.assertNotNull(impl.createUSLACSReturnCode("test"));
  }

  @Test
  public void testUSImDestDftMailingID() {
    Assert.assertNotNull(impl.createUSImDestDftMailingID("test"));
  }

  @Test
  public void testOutputAddressBlocks() {
    Assert.assertNotNull(impl.createOutputAddressBlocks("test"));
  }

  @Test
  public void testStatus() {
    Assert.assertNotNull(impl.createStatus("test"));
  }

  @Test
  public void testFirmName() {
    Assert.assertNotNull(impl.createFirmName("test"));
  }

  @Test
  public void testEnableSERP() {
    Assert.assertNotNull(impl.createEnableSERP("test"));
  }

  @Test
  public void testStreetNamePreferredAliasResult() {
    Assert.assertNotNull(impl.createStreetNamePreferredAliasResult("test"));
  }

  @Test
  public void testOutputRecordType() {
    Assert.assertNotNull(impl.createOutputRecordType("test"));
  }

  @Test
  public void testCanReportMailerCityLine() {
    Assert.assertNotNull(impl.createCanReportMailerCityLine("test"));
  }

  @Test
  public void testPostalBarCode() {
    Assert.assertNotNull(impl.createPostalBarCode("test"));
  }

  @Test
  public void testUSLOTCode() {
    Assert.assertNotNull(impl.createUSLOTCode("test"));
  }

  @Test
  public void testCountryInput() {
    Assert.assertNotNull(impl.createCountryInput("test"));
  }

  @Test
  public void testUSImGenBarcodeID() {
    Assert.assertNotNull(impl.createUSImGenBarcodeID("test"));
  }

  @Test
  public void testDPVDetermineVacancy() {
    Assert.assertNotNull(impl.createDPVDetermineVacancy("test"));
  }

  @Test
  public void testFailOnCMRAMatch() {
    Assert.assertNotNull(impl.createFailOnCMRAMatch("test"));
  }

  @Test
  public void testOutputAbbreviatedAlias() {
    Assert.assertNotNull(impl.createOutputAbbreviatedAlias("test"));
  }

  @Test
  public void testUSLOTHex() {
    Assert.assertNotNull(impl.createUSLOTHex("test"));
  }

  @Test
  public void testStreetName() {
    Assert.assertNotNull(impl.createStreetName("test"));
  }

  @Test
  public void testStreetNameAbbreviatedAliasResult() {
    Assert.assertNotNull(impl.createStreetNameAbbreviatedAliasResult("test"));
  }

  @Test
  public void testDPVFootnote() {
    Assert.assertNotNull(impl.createDPVFootnote("test"));
  }

  @Test
  public void testDPVNoStat() {
    Assert.assertNotNull(impl.createDPVNoStat("test"));
  }

  @Test
  public void testApartmentNumberResult() {
    Assert.assertNotNull(impl.createApartmentNumberResult("test"));
  }

  @Test
  public void testDirectionalMatchingStrictness() {
    Assert.assertNotNull(impl.createDirectionalMatchingStrictness("test"));
  }

  @Test
  public void testCanReportMailerName() {
    Assert.assertNotNull(impl.createCanReportMailerName("test"));
  }

  @Test
  public void testRecordType() {
    Assert.assertNotNull(impl.createRecordType("test"));
  }

  @Test
  public void testApartmentLabel() {
    Assert.assertNotNull(impl.createApartmentLabel("test"));
  }

  @Test
  public void testReportListNumber() {
    Assert.assertNotNull(impl.createReportListNumber("test"));
  }

  @Test
  public void testUsImOriginDATF() {
    Assert.assertNotNull(impl.createUsImOriginDATF("test"));
  }

  @Test
  public void testInternationalCityStreetSearching() {
    Assert.assertNotNull(impl.createInternationalCityStreetSearching("test"));
  }

  @Test
  public void testUSBCCheckDigit() {
    Assert.assertNotNull(impl.createUSBCCheckDigit("test"));
  }

  @Test
  public void testCanOutputCityAlias() {
    Assert.assertNotNull(impl.createCanOutputCityAlias("test"));
  }

  @Test
  public void testStatusDescription() {
    Assert.assertNotNull(impl.createStatusDescription("test"));
  }

  @Test
  public void testUSImDestEnableInc() {
    Assert.assertNotNull(impl.createUSImDestEnableInc("test"));
  }

  @Test
  public void testTrailingDirectional() {
    Assert.assertNotNull(impl.createTrailingDirectional("test"));
  }

  @Test
  public void testUSImGenEnable() {
    Assert.assertNotNull(impl.createUSImGenEnable("test"));
  }

  @Test
  public void testExtractFirm() {
    Assert.assertNotNull(impl.createExtractFirm("test"));
  }

  @Test
  public void testUSImDestBarcodeID() {
    Assert.assertNotNull(impl.createUSImDestBarcodeID("test"));
  }

  @Test
  public void testCanPreferHouseNum() {
    Assert.assertNotNull(impl.createCanPreferHouseNum("test"));
  }

  @Test
  public void testPostalCode() {
    Assert.assertNotNull(impl.createPostalCode("test"));
  }

  @Test
  public void testStandardAddressPMBLine() {
    Assert.assertNotNull(impl.createStandardAddressPMBLine("test"));
  }

  @Test
  public void testUSUrbanNameInput() {
    Assert.assertNotNull(impl.createUSUrbanNameInput("test"));
  }

  @Test
  public void testUSImGenCustID() {
    Assert.assertNotNull(impl.createUSImGenCustID("test"));
  }

  @Test
  public void testPOBoxResult() {
    Assert.assertNotNull(impl.createPOBoxResult("test"));
  }

  @Test
  public void testUSImGenSubID() {
    Assert.assertNotNull(impl.createUSImGenSubID("test"));
  }

  @Test
  public void testOutputReport3553() {
    Assert.assertNotNull(impl.createOutputReport3553("test"));
  }

  @Test
  public void testPerformUSProcessing() {
    Assert.assertNotNull(impl.createPerformUSProcessing("test"));
  }

  @Test
  public void testUSImOrgRouteZip() {
    Assert.assertNotNull(impl.createUSImOrgRouteZip("test"));
  }

  @Test
  public void testUsImOriginRaw() {
    Assert.assertNotNull(impl.createUsImOriginRaw("test"));
  }

  @Test
  public void testReportListFileName() {
    Assert.assertNotNull(impl.createReportListFileName("test"));
  }

  @Test
  public void testUSCountyName() {
    Assert.assertNotNull(impl.createUSCountyName("test"));
  }

  @Test
  public void testApartmentNumberInput() {
    Assert.assertNotNull(impl.createApartmentNumberInput("test"));
  }

  @Test
  public void testOutputVeriMoveDataBlock() {
    Assert.assertNotNull(impl.createOutputVeriMoveDataBlock("test"));
  }

  @Test
  public void testPostalCodeCityResult() {
    Assert.assertNotNull(impl.createPostalCodeCityResult("test"));
  }

  @Test
  public void testCountryLevel() {
    Assert.assertNotNull(impl.createCountryLevel("test"));
  }

  @Test
  public void testStateProvinceResult() {
    Assert.assertNotNull(impl.createStateProvinceResult("test"));
  }

  @Test
  public void testOutputReportSummary() {
    Assert.assertNotNull(impl.createOutputReportSummary("test"));
  }

  @Test
  public void testDatabaseInternational() {
    Assert.assertNotNull(impl.createDatabaseInternational("test"));
  }

  @Test
  public void testHouseNumber() {
    Assert.assertNotNull(impl.createHouseNumber("test"));
  }

  @Test
  public void testUSCongressionalDistrict() {
    Assert.assertNotNull(impl.createUSCongressionalDistrict("test"));
  }

  @Test
  public void testFirmMatchingStrictness() {
    Assert.assertNotNull(impl.createFirmMatchingStrictness("test"));
  }

  @Test
  public void testRDI() {
    Assert.assertNotNull(impl.createRDI("test"));
  }

  @Test
  public void testPerformRDI() {
    Assert.assertNotNull(impl.createPerformRDI("test"));
  }

  @Test
  public void testProcessedBy() {
    Assert.assertNotNull(impl.createProcessedBy("test"));
  }

  @Test
  public void testCanReportMailerCPCNumber() {
    Assert.assertNotNull(impl.createCanReportMailerCPCNumber("test"));
  }

  @Test
  public void testPerformASM() {
    Assert.assertNotNull(impl.createPerformASM("test"));
  }

  @Test
  public void testUSCarrierRouteCode() {
    Assert.assertNotNull(impl.createUSCarrierRouteCode("test"));
  }

  @Test
  public void testPerformLACSLink() {
    Assert.assertNotNull(impl.createPerformLACSLink("test"));
  }

  @Test
  public void testSuiteLinkReturnCode() {
    Assert.assertNotNull(impl.createSuiteLinkReturnCode("test"));
  }

  @Test
  public void testTrailingDirectionalResult() {
    Assert.assertNotNull(impl.createTrailingDirectionalResult("test"));
  }

  @Test
  public void testCanadianDeliveryInstallationAreaName() {
    Assert.assertNotNull(impl.createCanadianDeliveryInstallationAreaName("test"));
  }

  @Test
  public void testUSUrbanName() {
    Assert.assertNotNull(impl.createUSUrbanName("test"));
  }

  @Test
  public void testAddressFormat() {
    Assert.assertNotNull(impl.createAddressFormat("test"));
  }

  @Test
  public void testApartmentNumber2Result() {
    Assert.assertNotNull(impl.createApartmentNumber2Result("test"));
  }

  @Test
  public void testPrivateMailboxType() {
    Assert.assertNotNull(impl.createPrivateMailboxType("test"));
  }

  @Test
  public void testPostalCodeInput() {
    Assert.assertNotNull(impl.createPostalCodeInput("test"));
  }

  @Test
  public void testAddressLineSearchOnFail() {
    Assert.assertNotNull(impl.createAddressLineSearchOnFail("test"));
  }

  @Test
  public void testCityResult() {
    Assert.assertNotNull(impl.createCityResult("test"));
  }

  @Test
  public void testLeadingDirectional() {
    Assert.assertNotNull(impl.createLeadingDirectional("test"));
  }

  @Test
  public void testApartmentLabelInput() {
    Assert.assertNotNull(impl.createApartmentLabelInput("test"));
  }

  @Test
  public void testOutputStreetNameAlias() {
    Assert.assertNotNull(impl.createOutputStreetNameAlias("test"));
  }

  @Test
  public void testDatabaseCanada() {
    Assert.assertNotNull(impl.createDatabaseCanada("test"));
  }

  @Test
  public void testOutputPostalCodeSeparator() {
    Assert.assertNotNull(impl.createOutputPostalCodeSeparator("test"));
  }

  @Test
  public void testCMRA() {
    Assert.assertNotNull(impl.createCMRA("test"));
  }

  @Test
  public void testAddressBlock9() {
    Assert.assertNotNull(impl.createAddressBlock9("test"));
  }

  @Test
  public void testUSFIPSCountyNumber() {
    Assert.assertNotNull(impl.createUSFIPSCountyNumber("test"));
  }

  @Test
  public void testApartmentLabel2Result() {
    Assert.assertNotNull(impl.createApartmentLabel2Result("test"));
  }

  @Test
  public void testUSImOrgSpecSvcCode() {
    Assert.assertNotNull(impl.createUSImOrgSpecSvcCode("test"));
  }

  @Test
  public void testTrailingDirectionalInput() {
    Assert.assertNotNull(impl.createTrailingDirectionalInput("test"));
  }

  @Test
  public void testRRHCInput() {
    Assert.assertNotNull(impl.createRRHCInput("test"));
  }

  @Test
  public void testUSImDestSpecSvcCode() {
    Assert.assertNotNull(impl.createUSImDestSpecSvcCode("test"));
  }

  @Test
  public void testUSImOrgBarcodeID() {
    Assert.assertNotNull(impl.createUSImOrgBarcodeID("test"));
  }

  @Test
  public void testCanLanguage() {
    Assert.assertNotNull(impl.createCanLanguage("test"));
  }

  @Test
  public void testUSImGenEnableInc() {
    Assert.assertNotNull(impl.createUSImGenEnableInc("test"));
  }

  @Test
  public void testCanFrenchApartmentLabel() {
    Assert.assertNotNull(impl.createCanFrenchApartmentLabel("test"));
  }

  @Test
  public void testStreetSuffixInput() {
    Assert.assertNotNull(impl.createStreetSuffixInput("test"));
  }

  @Test
  public void testUsImDestinationRaw() {
    Assert.assertNotNull(impl.createUsImDestinationRaw("test"));
  }

  @Test
  public void testVeriMoveDataBlock() {
    Assert.assertNotNull(impl.createVeriMoveDataBlock("test"));
  }

  @Test
  public void testAddressBlock2() {
    Assert.assertNotNull(impl.createAddressBlock2("test"));
  }

  @Test
  public void testAddressBlock1() {
    Assert.assertNotNull(impl.createAddressBlock1("test"));
  }

  @Test
  public void testAddressBlock4() {
    Assert.assertNotNull(impl.createAddressBlock4("test"));
  }

  @Test
  public void testAddressBlock3() {
    Assert.assertNotNull(impl.createAddressBlock3("test"));
  }

  @Test
  public void testAbbreviatedAliasResult() {
    Assert.assertNotNull(impl.createAbbreviatedAliasResult("test"));
  }

  @Test
  public void testAddressBlock6() {
    Assert.assertNotNull(impl.createAddressBlock6("test"));
  }

  @Test
  public void testCanadianDeliveryInstallationQualifierNameInput() {
    Assert.assertNotNull(impl.createCanadianDeliveryInstallationQualifierNameInput("test"));
  }

  @Test
  public void testAddressBlock5() {
    Assert.assertNotNull(impl.createAddressBlock5("test"));
  }

  @Test
  public void testAddressBlock8() {
    Assert.assertNotNull(impl.createAddressBlock8("test"));
  }

  @Test
  public void testAddressBlock7() {
    Assert.assertNotNull(impl.createAddressBlock7("test"));
  }

  @Test
  public void testOutputReportSERP() {
    Assert.assertNotNull(impl.createOutputReportSERP("test"));
  }

  @Test
  public void testStreetNameResult() {
    Assert.assertNotNull(impl.createStreetNameResult("test"));
  }

  @Test
  public void testUSUrbanNameResult() {
    Assert.assertNotNull(impl.createUSUrbanNameResult("test"));
  }

  @Test
  public void testStatusCode() {
    Assert.assertNotNull(impl.createStatusCode("test"));
  }

  @Test
  public void testSuiteLinkFidelity() {
    Assert.assertNotNull(impl.createSuiteLinkFidelity("test"));
  }
}
