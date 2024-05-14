
package com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OptionsTypeTest {

    private OptionsType impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new OptionsType();
        impl.setPerformUSProcessing("dummy_string");
        impl.setOutputAddressBlocks("dummy_string");
        impl.setPerformCanadianProcessing("dummy_string");
        impl.setPerformInternationalProcessing("dummy_string");
        impl.setOutputFormattedOnFail("dummy_string");
        impl.setKeepMultimatch("dummy_string");
        impl.setMaximumResults("dummy_string");
        impl.setPerformDPV("dummy_string");
        impl.setPerformRDI("dummy_string");
        impl.setPerformLACSLink("dummy_string");
        impl.setPerformLOT("dummy_string");
        impl.setPerformESM("dummy_string");
        impl.setEnableSERP("dummy_string");
        impl.setPerformASM("dummy_string");
        impl.setPerformEWS("dummy_string");
        impl.setExtractFirm("dummy_string");
        impl.setExtractUrb("dummy_string");
        impl.setOutputFieldLevelReturnCodes("dummy_string");
        impl.setOutputPostalCodeSeparator("dummy_string");
        impl.setOutputCasing("dummy_string");
        impl.setOutputRecordType("dummy_string");
        impl.setOutputCountryFormat("dummy_string");
        impl.setOutputMultinationalCharacters("dummy_string");
        impl.setStandardAddressFormat("dummy_string");
        impl.setStandardAddressPMBLine("dummy_string");
        impl.setOutputShortCityName("dummy_string");
        impl.setOutputReport3553("dummy_string");
        impl.setOutputReportSERP("dummy_string");
        impl.setOutputReportSummary("dummy_string");
        impl.setStreetMatchingStrictness("dummy_string");
        impl.setFirmMatchingStrictness("dummy_string");
        impl.setDirectionalMatchingStrictness("dummy_string");
        impl.setDualAddressLogic("dummy_string");
        impl.setFailOnCMRAMatch("dummy_string");
        impl.setReportListProcessorName("dummy_string");
        impl.setReportListFileName("dummy_string");
        impl.setReportListNumber("dummy_string");
        impl.setReportMailerName("dummy_string");
        impl.setReportMailerAddress("dummy_string");
        impl.setReportMailerCityLine("dummy_string");
        impl.setCanReportMailerCPCNumber("dummy_string");
        impl.setCanReportMailerName("dummy_string");
        impl.setCanReportMailerAddress("dummy_string");
        impl.setCanReportMailerCityLine("dummy_string");
        impl.setInternationalCityStreetSearching("dummy_string");
        impl.setAddressLineSearchOnFail("dummy_string");
        impl.setHomeCountry("dummy_string");
        impl.setPerformSuiteLink("dummy_string");
        impl.setOutputStreetNameAlias("dummy_string");
        impl.setOutputVeriMoveDataBlock("dummy_string");
        impl.setOutputAbbreviatedAlias("dummy_string");
        impl.setOutputPreferredAlias("dummy_string");
        impl.setCanStandardAddressFormat("dummy_string");
        impl.setCanEnglishApartmentLabel("dummy_string");
        impl.setCanFrenchApartmentLabel("dummy_string");
        impl.setCanFrenchFormat("dummy_string");
        impl.setCanOutputCityFormat("dummy_string");
        impl.setCanOutputCityAlias("dummy_string");
        impl.setCanDualAddressLogic("dummy_string");
        impl.setCanPreferHouseNum("dummy_string");
        impl.setCanRuralRouteFormat("dummy_string");
        impl.setCanNonCivicFormat("dummy_string");
        impl.setCanDeliveryOfficeFormat("dummy_string");
        impl.setDatabaseUS("dummy_string");
        impl.setDatabaseCanada("dummy_string");
        impl.setDatabaseInternational("dummy_string");
        impl.setDPVSuccessfulStatusCondition("dummy_string");
        impl.setUSImDestEnable("dummy_string");
        impl.setUSImDestBarcodeID("12345");
        impl.setUSImDestSpecSvcCode("12345");
        impl.setUSImDestSubID("12345");
        impl.setUSImDestDftMailingID("sampleMailId");
        impl.setUSImDestEnableInc("dummy_string");
        impl.setUSImOrgEnable("dummy_string");
        impl.setUSImOrgBarcodeID("dummy_string");
        impl.setUSImOrgSpecSvcCode("dummy_string");
        impl.setUSImOrgCustID("dummy_string");
        impl.setUSImOrgRouteZip("dummy_string");
        impl.setUSImGenEnable("dummy_string");
        impl.setUSImGenBarcodeID("dummy_string");
        impl.setUSImGenSpecSvcCode("dummy_string");
        impl.setUSImGenSubID("dummy_string");
        impl.setUSImGenCustID("dummy_string");
        impl.setUSImGenEnableInc("dummy_string");
        impl.setUSImErrorHandle("dummy_string");
        impl.toString();
    }
    @Test
    public void testGetUSImOrgRouteZip()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUSImOrgRouteZip());
    }
    @Test
    public void testGetUSImOrgCustID()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUSImOrgCustID());
    }
    @Test
    public void testGetUSImOrgSpecSvcCode()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUSImOrgSpecSvcCode());
    }
    @Test
    public void testGetUSImOrgBarcodeID()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUSImOrgBarcodeID());
    }
    @Test
    public void testGetUSImGenEnable()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUSImGenEnable());
    }
    @Test
    public void testGetUSImGenBarcodeID()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUSImGenBarcodeID());
    }
    @Test
    public void testGetUSImGenSpecSvcCode()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUSImGenSpecSvcCode());
    }
    @Test
    public void testGetUSImGenSubID()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUSImGenSubID());
    }
    @Test
    public void testSetUSImGenCustID()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUSImGenCustID());
    }
    @Test
    public void testGetUSImGenEnableInc()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUSImGenEnableInc());
    }
    @Test
    public void testGetUSImErrorHandle()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUSImErrorHandle());
    }
    @Test
    public void testGetUSImOrgEnable()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUSImOrgEnable());
    }
    @Test
    public void testGetUSImDestEnableInc()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUSImDestEnableInc());
    }
    @Test
    public void testGetUSImDestDftMailingID()
        throws Exception
    {
        Assert.assertEquals("sampleMailId", impl.getUSImDestDftMailingID());
    }
    @Test
    public void testGetUSImDestSubID()
        throws Exception
    {
        Assert.assertEquals("12345", impl.getUSImDestSubID());
    }
    @Test
    public void testGetUSImDestEnable()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUSImDestEnable());
    }
    @Test
    public void testGetUSImDestSpecSvcCode()
        throws Exception
    {
        Assert.assertEquals("12345", impl.getUSImDestSpecSvcCode());
    }
    @Test
    public void testGetUSImDestBarcodeID()
        throws Exception
    {
        Assert.assertEquals("12345", impl.getUSImDestBarcodeID());
    }
    @Test
    public void testGetDPVSuccessfulStatusCondition()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDPVSuccessfulStatusCondition());
    }
    @Test
    public void testGetOutputPreferredAlias()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOutputPreferredAlias());
    }

    @Test
    public void testGetPerformRDI()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPerformRDI());
    }

    @Test
    public void testGetDirectionalMatchingStrictness()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDirectionalMatchingStrictness());
    }

    @Test
    public void testGetCanOutputCityAlias()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCanOutputCityAlias());
    }

    @Test
    public void testGetOutputShortCityName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOutputShortCityName());
    }

    @Test
    public void testGetCanDualAddressLogic()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCanDualAddressLogic());
    }

    @Test
    public void testGetPerformInternationalProcessing()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPerformInternationalProcessing());
    }

    @Test
    public void testGetFirmMatchingStrictness()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getFirmMatchingStrictness());
    }

    @Test
    public void testGetOutputFormattedOnFail()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOutputFormattedOnFail());
    }

    @Test
    public void testGetPerformEWS()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPerformEWS());
    }

    @Test
    public void testGetOutputCasing()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOutputCasing());
    }

    @Test
    public void testGetCanReportMailerCityLine()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCanReportMailerCityLine());
    }

    @Test
    public void testGetEnableSERP()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getEnableSERP());
    }

    @Test
    public void testGetPerformESM()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPerformESM());
    }

    @Test
    public void testGetPerformASM()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPerformASM());
    }

    @Test
    public void testGetExtractUrb()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getExtractUrb());
    }

    @Test
    public void testGetFailOnCMRAMatch()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getFailOnCMRAMatch());
    }

    @Test
    public void testGetOutputAbbreviatedAlias()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOutputAbbreviatedAlias());
    }

    @Test
    public void testGetCanEnglishApartmentLabel()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCanEnglishApartmentLabel());
    }

    @Test
    public void testGetOutputRecordType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOutputRecordType());
    }

    @Test
    public void testGetReportMailerName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getReportMailerName());
    }

    @Test
    public void testGetCanReportMailerName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCanReportMailerName());
    }

    @Test
    public void testGetOutputFieldLevelReturnCodes()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOutputFieldLevelReturnCodes());
    }

    @Test
    public void testGetPerformSuiteLink()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPerformSuiteLink());
    }

    @Test
    public void testGetDatabaseUS()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDatabaseUS());
    }

    @Test
    public void testGetCanPreferHouseNum()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCanPreferHouseNum());
    }

    @Test
    public void testGetPerformUSProcessing()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPerformUSProcessing());
    }

    @Test
    public void testGetOutputPostalCodeSeparator()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOutputPostalCodeSeparator());
    }

    @Test
    public void testGetKeepMultimatch()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getKeepMultimatch());
    }

    @Test
    public void testGetPerformLACSLink()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPerformLACSLink());
    }

    @Test
    public void testGetDualAddressLogic()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDualAddressLogic());
    }

    @Test
    public void testGetCanRuralRouteFormat()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCanRuralRouteFormat());
    }

    @Test
    public void testGetAddressLineSearchOnFail()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAddressLineSearchOnFail());
    }

    @Test
    public void testGetOutputAddressBlocks()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOutputAddressBlocks());
    }

    @Test
    public void testGetReportMailerCityLine()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getReportMailerCityLine());
    }

    @Test
    public void testGetOutputMultinationalCharacters()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOutputMultinationalCharacters());
    }

    @Test
    public void testGetCanStandardAddressFormat()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCanStandardAddressFormat());
    }

    @Test
    public void testGetCanFrenchApartmentLabel()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCanFrenchApartmentLabel());
    }

    @Test
    public void testGetStreetMatchingStrictness()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getStreetMatchingStrictness());
    }

    @Test
    public void testGetCanFrenchFormat()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCanFrenchFormat());
    }

    @Test
    public void testGetOutputStreetNameAlias()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOutputStreetNameAlias());
    }

    @Test
    public void testGetCanDeliveryOfficeFormat()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCanDeliveryOfficeFormat());
    }

    @Test
    public void testGetReportListFileName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getReportListFileName());
    }

    @Test
    public void testGetReportListNumber()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getReportListNumber());
    }

    @Test
    public void testGetOutputReportSummary()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOutputReportSummary());
    }

    @Test
    public void testGetDatabaseInternational()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDatabaseInternational());
    }

    @Test
    public void testGetCanReportMailerCPCNumber()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCanReportMailerCPCNumber());
    }

    @Test
    public void testGetReportMailerAddress()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getReportMailerAddress());
    }

    @Test
    public void testGetPerformCanadianProcessing()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPerformCanadianProcessing());
    }

    @Test
    public void testGetExtractFirm()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getExtractFirm());
    }

    @Test
    public void testGetPerformLOT()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPerformLOT());
    }

    @Test
    public void testGetCanReportMailerAddress()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCanReportMailerAddress());
    }

    @Test
    public void testGetStandardAddressFormat()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getStandardAddressFormat());
    }

    @Test
    public void testGetHomeCountry()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getHomeCountry());
    }

    @Test
    public void testGetMaximumResults()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getMaximumResults());
    }

    @Test
    public void testGetOutputCountryFormat()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOutputCountryFormat());
    }

    @Test
    public void testGetOutputReport3553()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOutputReport3553());
    }

    @Test
    public void testGetOutputReportSERP()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOutputReportSERP());
    }

    @Test
    public void testGetStandardAddressPMBLine()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getStandardAddressPMBLine());
    }

    @Test
    public void testGetCanOutputCityFormat()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCanOutputCityFormat());
    }

    @Test
    public void testGetDatabaseCanada()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDatabaseCanada());
    }

    @Test
    public void testGetOutputVeriMoveDataBlock()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOutputVeriMoveDataBlock());
    }

    @Test
    public void testGetInternationalCityStreetSearching()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getInternationalCityStreetSearching());
    }

    @Test
    public void testGetReportListProcessorName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getReportListProcessorName());
    }

    @Test
    public void testGetCanNonCivicFormat()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCanNonCivicFormat());
    }

    @Test
    public void testGetPerformDPV()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPerformDPV());
    }

}
