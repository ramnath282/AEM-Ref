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
public class RetetailItemModelTest {
  @InjectMocks
  private RetailItemModel retetailItemModel;

  @Mock
  private RetailerDetailService retailerDetailService;

  @Before
  public void setup() throws Exception {
	    MemberModifier.field(RetailItemModel.class, "selectAppOrRetailer").set(retetailItemModel, "selectAppOrRetailer");
	    MemberModifier.field(RetailItemModel.class, "appOrRetailPath").set(retetailItemModel, "https://appOrRetailPath");
	    MemberModifier.field(RetailItemModel.class, "retailerDetailService").set(retetailItemModel, retailerDetailService);
	    MemberModifier.field(RetailItemModel.class, "interstitialType").set(retetailItemModel, "interstitialType");
	    MemberModifier.field(RetailItemModel.class, "interstitialSelectionFragmentPath").set(retetailItemModel, "interstitialSelectionFragmentPath");
	    MemberModifier.field(RetailItemModel.class, "interstitialPath").set(retetailItemModel, "interstitialPath");
	    MemberModifier.field(RetailItemModel.class, "isFirstCTA").set(retetailItemModel, false);
	    MemberModifier.field(RetailItemModel.class, "desktopLogo").set(retetailItemModel, "desktopLogo");
	    MemberModifier.field(RetailItemModel.class, "mobileLogo").set(retetailItemModel, "mobileLogo");
	    MemberModifier.field(RetailItemModel.class, "retailerAltName").set(retetailItemModel, "retailerAltName");
	    MemberModifier.field(RetailItemModel.class, "retailerName").set(retetailItemModel, "retailerName");
	    Map<String, String> retailerDetail = new HashMap<>();
	    retailerDetail.put(Constants.RETAILER_DESKTOP_LOGO, Constants.RETAILER_DESKTOP_LOGO);
	    retailerDetail.put(Constants.RETAILER_MOBILE_LOGO, Constants.RETAILER_MOBILE_LOGO);
	    retailerDetail.put(Constants.RETAILER_ALT_NAME, Constants.RETAILER_ALT_NAME);
	    retailerDetail.put(Constants.RETAILER_NAME, Constants.RETAILER_NAME);
	    retailerDetail.put(Constants.RETAILER_STEM_URL, Constants.RETAILER_STEM_URL);
	    Mockito.when(retailerDetailService.getRetailerDetails("selectAppOrRetailer")).thenReturn(retailerDetail);
  }

  @Test
  public void testInit() {
	    retetailItemModel.init();
	    assertEquals("interstitialType", retetailItemModel.getInterstitialType());
	    assertEquals("interstitialSelectionFragmentPath", retetailItemModel.getInterstitialSelectionFragmentPath());
	    assertEquals(false, retetailItemModel.getIsFirstCTA());
	    assertEquals("interstitialPath", retetailItemModel.getInterstitialPath());
	    assertEquals("selectAppOrRetailer", retetailItemModel.getSelectAppOrRetailer());
	    assertEquals("https://appOrRetailPath", retetailItemModel.getAppOrRetailPath());
	    assertEquals(Constants.RETAILER_DESKTOP_LOGO, retetailItemModel.getDesktopLogo());
	    assertEquals(Constants.RETAILER_MOBILE_LOGO, retetailItemModel.getMobileLogo());
	    assertEquals(Constants.RETAILER_ALT_NAME, retetailItemModel.getRetailerAltName());
	    assertEquals(Constants.RETAILER_NAME, retetailItemModel.getRetailerName());
  }

  @Test
  public void testInitRetaailPathUnMatch() throws IllegalArgumentException, IllegalAccessException {
	    MemberModifier.field(RetailItemModel.class, "appOrRetailPath").set(retetailItemModel, "appOrRetailPath");
	    retetailItemModel.init();
  }
}
