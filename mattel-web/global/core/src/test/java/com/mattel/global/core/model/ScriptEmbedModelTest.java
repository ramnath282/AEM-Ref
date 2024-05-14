package com.mattel.global.core.model;

import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class ScriptEmbedModelTest {
	
    String[] scriptPathsArray = { "script-1.js","script-2.js" };
    
    @InjectMocks
    ScriptEmbedModel scriptEmbedModel;
    @Mock
    Resource resource;

	@Before
	public void setUp() throws IllegalArgumentException, IllegalAccessException{
       MemberModifier.field(ScriptEmbedModel.class, "resource").set(scriptEmbedModel,resource);
       MemberModifier.field(ScriptEmbedModel.class, "scriptPaths").set(scriptEmbedModel,scriptPathsArray);
	}

	@Test
	public void testInit() {
	    scriptEmbedModel.init();
	    scriptEmbedModel.getScriptPathsList();
	}
}
