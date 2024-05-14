package com.mattel.global.core.workflow;

import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
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
import com.day.cq.replication.Replicator;
import com.mattel.global.core.services.GetResourceResolver;
import com.mattel.global.core.utils.GlobalUtils;
import com.mattel.global.core.utils.PropertyReaderUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GlobalUtils.class, EntityUtils.class})
public class NewsFeedItemReplicatorProcessTest {

	@InjectMocks
	NewsFeedItemReplicatorProcess newsFeedItemReplicatorProcess;
	@Mock
	private GetResourceResolver getResourceResolver;
	@Mock
	private PropertyReaderUtils propertyReaderUtils;
	@Mock
	private Replicator replicator;
	@Mock
	WorkItem workItem;
	@Mock
	WorkflowSession workflowSession;
	@Mock
	MetaDataMap metaDataMap;
	@Mock
	WorkflowData workflowData;
	@Mock
	MetaDataMap workflowMetaDataMap;
	@Mock
	ResourceResolver resourceResolver;
	
	HttpEntity httpEntity;

	@Before
	public void setup() throws Exception {
		PowerMockito.mockStatic(GlobalUtils.class);
		PowerMockito.mockStatic(EntityUtils.class);
		when(workItem.getWorkflowData()).thenReturn(workflowData);
		CloseableHttpClient httpClient = Mockito.mock(CloseableHttpClient.class);
		when(GlobalUtils.createCustom(Mockito.anyInt())).thenReturn(httpClient);
		when(propertyReaderUtils.getSnpIndexUrl("corporate")).thenReturn("SnpIndexUrl");
		CloseableHttpResponse httpResponse = Mockito.mock(CloseableHttpResponse.class);
		when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
		StatusLine statusLine = Mockito.mock(StatusLine.class);
		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		when(statusLine.getStatusCode()).thenReturn(200);
		httpEntity = Mockito.mock(HttpEntity.class);
		when(httpResponse.getEntity()).thenReturn(httpEntity);
		when(workflowData.getMetaDataMap()).thenReturn(workflowMetaDataMap);
		when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
	}

	@Test
	public void testExecuteWithActivateArg() throws WorkflowException, ParseException, IOException {
		when(workflowData.getPayload()).thenReturn("payloadpath");
		when(metaDataMap.containsKey("PROCESS_ARGS")).thenReturn(true);
		when(EntityUtils.toString(httpEntity)).thenReturn("OK");
		when(metaDataMap.get("PROCESS_ARGS", String.class)).thenReturn("Activate,10000,corporate");
		newsFeedItemReplicatorProcess.execute(workItem, workflowSession, metaDataMap);
	}
	
	@Test
	public void testExecuteWithActivateArgEmptyPayload() throws WorkflowException, ParseException, IOException {
		when(workflowData.getPayload()).thenReturn("");
		when(metaDataMap.containsKey("PROCESS_ARGS")).thenReturn(true);
		when(EntityUtils.toString(httpEntity)).thenReturn("OK");
		when(metaDataMap.get("PROCESS_ARGS", String.class)).thenReturn("Activate,10000,corporate");
		newsFeedItemReplicatorProcess.execute(workItem, workflowSession, metaDataMap);
	}
	
	@Test
	public void testExecuteWithEmptyProcessArgs() throws WorkflowException, ParseException, IOException {
		when(workflowData.getPayload()).thenReturn("");
		when(metaDataMap.containsKey("PROCESS_ARGS")).thenReturn(false);
		when(EntityUtils.toString(httpEntity)).thenReturn("OK");
		when(metaDataMap.get("PROCESS_ARGS", String.class)).thenReturn("Activate,10000,corporate");
		newsFeedItemReplicatorProcess.execute(workItem, workflowSession, metaDataMap);
	}

	@Test
	public void testExecuteWithDeactivateArg() throws WorkflowException, ParseException, IOException {
		when(workflowData.getPayload()).thenReturn("payloadpath");
		when(metaDataMap.containsKey("PROCESS_ARGS")).thenReturn(true);
		when(EntityUtils.toString(httpEntity)).thenReturn("OK");
		when(metaDataMap.get("PROCESS_ARGS", String.class)).thenReturn("Deactivate,10000,corporate");
		newsFeedItemReplicatorProcess.execute(workItem, workflowSession, metaDataMap);
	}
	
	@Test
	public void testExecutewithFailEntiryValue() throws WorkflowException, ParseException, IOException {
		when(workflowData.getPayload()).thenReturn("payloadpath");
		when(metaDataMap.containsKey("PROCESS_ARGS")).thenReturn(true);
		when(EntityUtils.toString(httpEntity)).thenReturn("ERROR");
		when(metaDataMap.get("PROCESS_ARGS", String.class)).thenReturn("Deactivate,10000,corporate");
		newsFeedItemReplicatorProcess.execute(workItem, workflowSession, metaDataMap);
	}

}
