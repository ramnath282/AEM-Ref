package com.mattel.ag.explore.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class FooterLinksExplorePojoTest {
	
	FooterLinksExplorePojo footerLinksExplorePojo;

	@Before
	public void setUp() {
		footerLinksExplorePojo = new FooterLinksExplorePojo();
	}
	
	@Test
	public void getHelpRenderOption() {
		footerLinksExplorePojo.getHelpRenderOption();
	}
    
	@Test
	public void setHelpRenderOption() {
		footerLinksExplorePojo.setHelpRenderOption("");
	}
    
	@Test
	public void getHearRenderOption() {
		footerLinksExplorePojo.getHearRenderOption();
	}
    
	@Test
	public void setHearRenderOption() {
		footerLinksExplorePojo.setHearRenderOption("");
	}
    
	@Test
	public void getVisitRenderOption() {
		footerLinksExplorePojo.getVisitRenderOption();
	}
    
	@Test
	public void setVisitRenderOption() {
		footerLinksExplorePojo.setVisitRenderOption("");
	}
    
	@Test
	public void getFindRenderOption() {
		footerLinksExplorePojo.getFindRenderOption();
	}
    
	@Test
	public void setFindRenderOption() {
		footerLinksExplorePojo.setFindRenderOption("");
	}
    
	@Test
	public void getHelpLinkText() {
		footerLinksExplorePojo.getHelpLinkText();
	}
    
	@Test
	public void setHelpLinkText() {
		footerLinksExplorePojo.setHelpLinkText("");
	}
    
	@Test
	public void getHelpLinkUrl() {
		footerLinksExplorePojo.getHelpLinkUrl();
	}
    
	@Test
	public void setHelpLinkUrl() {
		footerLinksExplorePojo.setHelpLinkUrl("");
	}
    
	@Test
	public void getHearLinkText() {
		footerLinksExplorePojo.getHearLinkText();
	}
    
	@Test
	public void setHearLinkText() {
		footerLinksExplorePojo.setHearLinkText("");
	}
    
	@Test
	public void getHearLinkUrl() {
		footerLinksExplorePojo.getHearLinkUrl();
	}
    
	@Test
	public void setHearLinkUrl() {
		footerLinksExplorePojo.setHearLinkUrl("hearLinkUrl");
	}
    
    @Test
	public void getVisitLinkText() {
    	footerLinksExplorePojo.getVisitLinkText();
	}
     
    @Test
	public void setVisitLinkText() {
    	footerLinksExplorePojo.setVisitLinkText("");
	}
    @Test
	public void getVisitLinkUrl() {
    	footerLinksExplorePojo.getVisitLinkUrl();
	}
    @Test
	public void setVisitLinkUrl() {
    	footerLinksExplorePojo.setVisitLinkUrl("visitLinkUrl");
	}
    @Test
	public void getCantFindLinkText() {
    	footerLinksExplorePojo.getCantFindLinkText();
	}
    @Test
	public void setCantFindLinkText() {
    	footerLinksExplorePojo.setCantFindLinkText("cantFindLinkText");
	}
    @Test
	public void getCantFindLinkUrl() {
    	footerLinksExplorePojo.getCantFindLinkUrl();
	}
    @Test
	public void setCantFindLinkUrl() {
    	footerLinksExplorePojo.setCantFindLinkUrl("cantFindLinkUrl");
	}
	@Test 
	public void toStringTest(){
	footerLinksExplorePojo.toString();
		
	}
	
	
	
	
	
}
