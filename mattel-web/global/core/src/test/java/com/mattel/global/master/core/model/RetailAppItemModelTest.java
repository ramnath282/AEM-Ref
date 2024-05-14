package com.mattel.global.master.core.model;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.global.core.services.RetailerDetailService;
import com.mattel.global.master.core.constants.Constants;

@RunWith(PowerMockRunner.class)
public class RetailAppItemModelTest {

  @InjectMocks
  private RetailAppItemModel retailAppItemModel;

  @Mock
  private RetailerDetailService retailerDetailService;

  @Before
  public void setup() throws Exception {
    MemberModifier.field(RetailAppItemModel.class, "selectAppOrRetailer").set(retailAppItemModel,
        "selectAppOrRetailer");
    MemberModifier.field(RetailAppItemModel.class, "appOrRetailPath").set(retailAppItemModel,
        "appOrRetailPath");
    Map<String, String> retailerDetails = new HashMap<>();
    retailerDetails.put(Constants.RETAILER_STEM_URL, "retailerstemurl");
    Mockito.when(retailerDetailService.getRetailerDetails("selectAppOrRetailer"))
        .thenReturn(retailerDetails);
    
    MemberModifier.field(RetailAppItemModel.class, "interstitialType").set(retailAppItemModel,
        "interstitialType");
    MemberModifier.field(RetailAppItemModel.class, "interstitialSelectionFragmentPath").set(retailAppItemModel,
        "interstitialSelectionFragmentPath");
    MemberModifier.field(RetailAppItemModel.class, "isFirstCTA").set(retailAppItemModel,
        true);
    MemberModifier.field(RetailAppItemModel.class, "appOrRetailPath").set(retailAppItemModel,
        "appOrRetailPath");
    MemberModifier.field(RetailAppItemModel.class, "desktopLogo").set(retailAppItemModel,
        "desktopLogo");
    MemberModifier.field(RetailAppItemModel.class, "mobileLogo").set(retailAppItemModel,
        "mobileLogo");
    MemberModifier.field(RetailAppItemModel.class, "retailerAltName").set(retailAppItemModel,
        "retailerAltName");
    MemberModifier.field(RetailAppItemModel.class, "retailerName").set(retailAppItemModel,
        "retailerName");
  }

  @Test
  public void testInit() throws Exception {
    retailAppItemModel.init();
  }
  
  @Test
  public void testGetterSetters(){
    assertEquals("interstitialType", retailAppItemModel.getInterstitialType());
    assertEquals("interstitialSelectionFragmentPath", retailAppItemModel.getInterstitialSelectionFragmentPath());
    assertEquals(true, retailAppItemModel.getIsFirstCTA());
    assertEquals("appOrRetailPath", retailAppItemModel.getAppOrRetailPath());
    assertEquals("desktopLogo", retailAppItemModel.getDesktopLogo());
    assertEquals("mobileLogo", retailAppItemModel.getMobileLogo());
    assertEquals("retailerAltName", retailAppItemModel.getRetailerAltName());
    assertEquals("retailerName", retailAppItemModel.getRetailerName());
    assertEquals("selectAppOrRetailer", retailAppItemModel.getSelectAppOrRetailer());
  }
}
