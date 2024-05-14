package com.mattel.productvideos.core.workflow;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.mattel.productvideos.core.services.impl.CopyContentServiceImpl;
import com.mattel.productvideos.core.utils.CopyContentWorkflowUtils;
import com.mattel.productvideos.core.workflows.CopyContentProcess;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CopyContentWorkflowUtils.class})
public class CopyContentProcessTest {

	@InjectMocks
	CopyContentProcess copyContentProcess;
	
	@Mock
	CopyContentServiceImpl copyCFServiceImpl;

	@Mock
	WorkItem workItem;

	@Mock
	WorkflowSession workflowSession;

	@Mock
	MetaDataMap metaDataMap;

	@Before
	public void setUp() {
		PowerMockito.mockStatic(CopyContentWorkflowUtils.class);
		WorkflowData workflowData = Mockito.mock(WorkflowData.class);
		Mockito.when(workItem.getWorkflowData()).thenReturn(workflowData);
		Mockito.when(workflowData.getPayloadType()).thenReturn("JRC_PATH");
		String processArgs = "jwrootpath=/content/mobile-apps/JurassicWorldFacts"; 
		Mockito.when(workflowData.getPayload()).thenReturn("/content/dam/digital-fragments/jurassic-world/test-cf-folder/test-cf-folder");
		Mockito.when( metaDataMap.get("PROCESS_ARGS", "string")).thenReturn(processArgs);
		Map<String, String> argsMap = new HashMap<>();
		argsMap.put("jwrootpath", "/content/mobile-apps/JurassicWorldFacts");
		Mockito.when(CopyContentWorkflowUtils.getProcessArgsList(processArgs)).thenReturn(argsMap);
		ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
		Mockito.when(workflowSession.adaptTo(ResourceResolver.class)).thenReturn(resourceResolver);
	}
	
	@Test
	public void testExecute() throws WorkflowException{
		copyContentProcess.execute(workItem, workflowSession, metaDataMap);
	}

}
