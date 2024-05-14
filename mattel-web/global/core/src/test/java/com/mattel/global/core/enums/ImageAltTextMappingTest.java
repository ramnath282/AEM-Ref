package com.mattel.global.core.enums;

import com.mattel.global.master.core.enums.ImageAltTextMapping;
import org.junit.Assert;
import org.junit.Test;


public class ImageAltTextMappingTest {
  @Test
  public void testGetAltTextMapping1() throws Exception {
    ImageAltTextMapping mapping = ImageAltTextMapping.IMAGEALTTEXT;
    
    Assert.assertEquals("image", mapping.getAltTextMapping());
  }
  
  @Test
  public void testGetAltTextMapping2() throws Exception {
    ImageAltTextMapping mapping = ImageAltTextMapping.HOVERIMAGEALTTEXT;
    
    Assert.assertEquals("hoverImage", mapping.getAltTextMapping());
  }
  
  @Test
  public void testGetAltTextMapping3() throws Exception {
    ImageAltTextMapping mapping = ImageAltTextMapping.MOBILEIMAGEALTTEXT;
    
    Assert.assertEquals("mobileImage", mapping.getAltTextMapping());
  }
  
  @Test
  public void testGetAltTextMapping4() throws Exception {
    ImageAltTextMapping mapping = ImageAltTextMapping.MOBILEHOVERIMAGEALTTEXT;
    
    Assert.assertEquals("mobileHoverImage", mapping.getAltTextMapping());
  }
  
  @Test
  public void testValues() throws Exception {
    ImageAltTextMapping[] mappings = ImageAltTextMapping.values();
    
    Assert.assertEquals(4, mappings.length);
  }
  
  @Test
  public void testValueOf() throws Exception {
    ImageAltTextMapping mapping = ImageAltTextMapping.valueOf("MOBILEHOVERIMAGEALTTEXT");
    
    Assert.assertEquals("mobileHoverImage", mapping.getAltTextMapping());
  }
}
