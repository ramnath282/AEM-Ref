package com.mattel.ecomm.core.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GTEmailTnsConfigServiceTest {
	
	private GTEmailTnsConfigService gTEmailTnsConfigService;

	@Before
	public void setUp() throws Exception {
		gTEmailTnsConfigService = new GTEmailTnsConfigService();
		 final GTEmailTnsConfigService.Config config = Mockito
		          .mock(GTEmailTnsConfigService.Config.class);
		 gTEmailTnsConfigService.activate(config);
		gTEmailTnsConfigService.setSource("source");
		gTEmailTnsConfigService.setTarget("target");
		gTEmailTnsConfigService.setEnvironment("environment");
		gTEmailTnsConfigService.setHostName("hostname");
		gTEmailTnsConfigService.setUser("user");
		gTEmailTnsConfigService.setNoticeName("name");
		gTEmailTnsConfigService.setOrganizationId("301");
		gTEmailTnsConfigService.setOriginatingSystemCode("AEM");
		gTEmailTnsConfigService.setSenderName("name");
		gTEmailTnsConfigService.setEndPointURL("URL");
	}


		@Test
		public void testGetSource() {
			Assert.assertEquals("source", gTEmailTnsConfigService.getSource());
		}
	
		@Test
		public void testGetTarget() {
			Assert.assertEquals("target", gTEmailTnsConfigService.getTarget());
		}
	    
	    @Test
	    public void testGetEnv() throws Exception {
			Assert.assertEquals("environment",gTEmailTnsConfigService.getEnvironment());
	    }
	    
	    @Test
	    public void testGethostName() throws Exception {
			Assert.assertEquals("hostname",gTEmailTnsConfigService.getHostName());
	    }
	    
	    @Test
	    public void testGetUser() throws Exception {
			Assert.assertEquals("user",gTEmailTnsConfigService.getUser());
	    }
	    
	    @Test
		public void testGetNotice() {
			Assert.assertEquals("name", gTEmailTnsConfigService.getNoticeName());
		}

		@Test
		public void testGetOrg() {
			Assert.assertEquals("301", gTEmailTnsConfigService.getOrganizationId());
		}
		
		@Test
	    public void testGetSystemCode() throws Exception {
			Assert.assertEquals("AEM",gTEmailTnsConfigService.getOriginatingSystemCode());
	    }
			
		@Test
		public void testEndPoint() {
			Assert.assertEquals("URL", gTEmailTnsConfigService.getEndPointURL());
		}
			
		@Test
	    public void testGetName() throws Exception {
			Assert.assertEquals("name",gTEmailTnsConfigService.getSenderName());
	    }
		
		@Test
	    public void testValues() throws Exception {
			Assert.assertNotNull(gTEmailTnsConfigService.getJSONValues());
	    }

}
