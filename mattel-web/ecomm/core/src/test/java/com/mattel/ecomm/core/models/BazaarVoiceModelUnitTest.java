
package com.mattel.ecomm.core.models;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.utils.GiftCardSkuProcessor;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GiftCardSkuProcessor.class, EcommHelper.class })
public class BazaarVoiceModelUnitTest {
  @InjectMocks
  private BazaarVoiceModel bazaarVoiceModel;

  @Mock
  private Resource resource;

  @Mock
  private SlingHttpServletRequest request;

  @Mock
  private Page currentPage;

  private ValueMap nodeProperties;

  @Before
  public void setUp() throws Exception {
    PowerMockito.mockStatic(EcommHelper.class);
    PowerMockito.mockStatic(GiftCardSkuProcessor.class);
    nodeProperties = Mockito.mock(ValueMap.class);
    RequestPathInfo requestPathInfo = Mockito.mock(RequestPathInfo.class);
    Mockito.when(resource.getPath())
        .thenReturn("/content/ag/en/shop/product-pages/giftcardpage/jcr:content/root/bazaarvoice");
    Mockito.when(resource.getValueMap()).thenReturn(nodeProperties);
    Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
    String[] dummySelectors = { "ag_en", "GGH49~AGCAR", "snowflake-gift-card-ggh49agcar" };
    Mockito.when(requestPathInfo.getSelectors()).thenReturn(dummySelectors);
    Mockito.when(request.getRequestURI())
        .thenReturn("/shop/p/snowflake-gift-card-ggh49agcar.ag_en.ggh49agcar.html");
    MemberModifier.field(BazaarVoiceModel.class, "extractPartNumberFromSelector")
        .set(bazaarVoiceModel, "true");
    Mockito.when(GiftCardSkuProcessor.checkAndUnescapeDelimiter("GGH49~AGCAR"))
        .thenReturn("GGH49.AGCAR");
    Mockito.when(
        EcommHelper.getBooleanValuefromValueMap(nodeProperties, "enableCustReview", Boolean.FALSE))
        .thenReturn(true);
  }

  @Test
  public void testInitwithCusomerReview() {
    Mockito.when(EcommHelper.getValueMapNodeValue(nodeProperties, "bazarVoiceFor"))
        .thenReturn("customerReview");
    bazaarVoiceModel.init();
  }

  public void testInit() {
    Mockito.when(EcommHelper.getValueMapNodeValue(nodeProperties, "bazarVoiceFor"))
        .thenReturn("quetionAnswers");
    bazaarVoiceModel.init();
  }

  @Test
  public void testGetters() {
    Assert.assertEquals(bazaarVoiceModel.getEnableCustomerReview(), Boolean.FALSE);
    Assert.assertEquals(bazaarVoiceModel.getDisableCustReviewMobile(), Boolean.FALSE);
    Assert.assertEquals(bazaarVoiceModel.getEnableQuestAnswer(), Boolean.FALSE);
    Assert.assertEquals(bazaarVoiceModel.getDisableQuenAnsMobile(), Boolean.FALSE);
    Assert.assertEquals(bazaarVoiceModel.getCustReviewTitle(), StringUtils.EMPTY);
    Assert.assertEquals(bazaarVoiceModel.getQuestAnswTitle(), StringUtils.EMPTY);
    Assert.assertEquals(bazaarVoiceModel.getProductSKUId(), StringUtils.EMPTY);
    Assert.assertEquals(bazaarVoiceModel.getCustReviewScript(), StringUtils.EMPTY);
    Assert.assertEquals(bazaarVoiceModel.getQuestAnsScript(), StringUtils.EMPTY);
    Assert.assertEquals(bazaarVoiceModel.getPageCustReviewTitle(), StringUtils.EMPTY);
    Assert.assertEquals(bazaarVoiceModel.getPageEnablecustReview(), Boolean.FALSE);
    Assert.assertEquals(bazaarVoiceModel.getPageDisablecustReviewMobile(), Boolean.FALSE);
    Assert.assertEquals(bazaarVoiceModel.getPageQuestAnswTitle(), StringUtils.EMPTY);
    Assert.assertEquals(bazaarVoiceModel.getEnableCustomerReview(), Boolean.FALSE);
    Assert.assertEquals(bazaarVoiceModel.getPageEnablequestAnswer(), Boolean.FALSE);
    Assert.assertEquals(bazaarVoiceModel.getPageCustReviewScript(), StringUtils.EMPTY);
    Assert.assertEquals(bazaarVoiceModel.getPageQuestAnswScript(), StringUtils.EMPTY);
    Assert.assertEquals(bazaarVoiceModel.getIsCustRatingsEnabled(), Boolean.FALSE);
    Assert.assertEquals(bazaarVoiceModel.getIsQuesAnsEnabled(), Boolean.FALSE);
    Assert.assertEquals(bazaarVoiceModel.getShowPageCRRSpinner(), Boolean.FALSE);
    Assert.assertEquals(bazaarVoiceModel.getShowPageQnSSpinner(), Boolean.FALSE);
    Assert.assertEquals(bazaarVoiceModel.getShowDialogCRRSpinner(), Boolean.FALSE);
    Assert.assertEquals(bazaarVoiceModel.getShowDialogQnsSpinner(), Boolean.FALSE);
    Assert.assertEquals(bazaarVoiceModel.getPagedisableQuenAnsMobile(), Boolean.FALSE);
  }

}
