package com.mattel.ecomm.core.services;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.ecomm.core.services.GTCompositeImageService.Config;

@RunWith(MockitoJUnitRunner.class)
public class GTCompositeImageServiceTest {

	@InjectMocks
	GTCompositeImageService gtCompositeImageService;
	@Mock
	private Config config;
	 
	private String baseImageUrl="https://mattel.scene7.com/is/image/AmericanGirlBrands/GiftTrunkTest?";
    private String[] layer1Url={"&layer=1&src=AmericanGirlBrands/GT-LARGE-T&size=2975;1917&pos=1843;1195&hide=0&wid=600&scl=5","&layer=1&src=AmericanGirlBrands/GT-LARGE-T&size=2975;1917&pos=1843;1195&hide=0&wid=600&scl=5"};
    private String layer2Url="&layer=2&src=AmericanGirlBrands/GT-SS1-GMY73&size=328,467&pos=2584,1412&hide=0";
    private String layer3Url="&layer=3&src=AmericanGirlBrands/GT-SS2-GMY74&size=296,420&pos=2594,663&hide=0";
    private String layer4Url="&layer=4&src=AmericanGirlBrands/GT-GBL30&size=3840,2398&pos=1920,1199&hide=0";
    private String layerCompParams="&layer=comp&fmt=jpeg&qlt=85,0&resMode=sharp2&op_usm=0.9,1.0,8,0";
    private String trulyMeImageParams="&size=296,420&pos=2594,663&hide=0";
    private String nonTMImageParams="&size=296,420&pos=2594,663&hide=0";
	    
	 @Before
	  public void init() throws Exception {
		 Mockito.when(config.layer1Url()).thenReturn(layer1Url);
		 Mockito.when(config.baseImageUrl()).thenReturn(baseImageUrl);
		 Mockito.when(config.layer2Url()).thenReturn(layer2Url);
		 Mockito.when(config.layer3Url()).thenReturn(layer3Url);
		 Mockito.when(config.layer4Url()).thenReturn(layer4Url);
		 Mockito.when(config.layerCompUrl()).thenReturn(layerCompParams);
		 Mockito.when(config.trulyMeImageParams()).thenReturn(trulyMeImageParams);
		 Mockito.when(config.nonTMImageParams()).thenReturn(nonTMImageParams);
		 gtCompositeImageService.activate(config);
		 gtCompositeImageService.setBaseImageUrl(baseImageUrl);
		 gtCompositeImageService.setLayer1Url(layer1Url);
		 gtCompositeImageService.setLayer2Url(layer2Url);
		 gtCompositeImageService.setLayer3Url(layer3Url);
		 gtCompositeImageService.setLayer4Url(layer4Url);
		 gtCompositeImageService.setLayerCompParams(layerCompParams);
		 gtCompositeImageService.setTrulyMeImageParams(trulyMeImageParams);
		 gtCompositeImageService.setNonTMImageParams(nonTMImageParams);
	  }
	 @Test
	 public void testImageService(){
		 Assert.assertEquals(baseImageUrl,gtCompositeImageService.getBaseImageUrl());
		 Assert.assertEquals(layer1Url.length,gtCompositeImageService.getLayer1Url().length);
		 Assert.assertEquals(layer2Url,gtCompositeImageService.getLayer2Url());
		 Assert.assertEquals(layer3Url,gtCompositeImageService.getLayer3Url());
		 Assert.assertEquals(layer4Url,gtCompositeImageService.getLayer4Url());
		 Assert.assertEquals(layerCompParams,gtCompositeImageService.getLayerCompParams());
		 Assert.assertEquals(trulyMeImageParams,gtCompositeImageService.getTrulyMeImageParams());
		 Assert.assertEquals(nonTMImageParams,gtCompositeImageService.getNonTMImageParams());
	 }
	 

}
