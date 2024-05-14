package com.mattel.productvideos.core.workflow;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.adobe.granite.workflow.model.WorkflowNode;
import com.mattel.productvideos.core.constants.Constants;
import com.mattel.productvideos.core.utils.ProductVideosPropertyUtils;
import com.mattel.productvideos.core.workflows.GenerateId;

@RunWith(PowerMockRunner.class)
public class GenerateIdTest {

	@InjectMocks
	GenerateId generateId;

	@Mock
	WorkItem item;
	@Mock
	WorkflowSession wfsession;
	@Mock
	MetaDataMap args;
	@Mock
	ProductVideosPropertyUtils productVideosPropertyUtils;
	
	@Test
	public void testExecute() throws WorkflowException, RepositoryException{
		WorkflowNode myNode = Mockito.mock(WorkflowNode.class);
		Mockito.when(item.getNode()).thenReturn(myNode);
		Mockito.when(myNode.getTitle()).thenReturn("Workflow Title");
		WorkflowData workflowData = Mockito.mock(WorkflowData.class);
		Mockito.when(item.getWorkflowData()).thenReturn(workflowData);
		String payload = "/testpayload";
		Mockito.when(workflowData.getPayload()).thenReturn(payload);
		
		Session jcrSession = Mockito.mock(Session.class);
		Mockito.when(wfsession.adaptTo(Session.class)).thenReturn(jcrSession);
		Node rootNode = Mockito.mock(Node.class);
		Mockito.when(jcrSession.getRootNode()).thenReturn(rootNode);
		Mockito.when(productVideosPropertyUtils.getRootAssetsPath()).thenReturn("/rootassetpath");
		
		Mockito.when(rootNode.hasNode("rootassetpath")).thenReturn(true);
		Mockito.when(rootNode.hasNode("testpayload")).thenReturn(true);
		Node dataPathNode = Mockito.mock(Node.class);
		Mockito.when(rootNode.getNode("testpayload")).thenReturn(dataPathNode);
		Node iteratorNode = Mockito.mock(Node.class);
		Mockito.when(rootNode.getNode("rootassetpath")).thenReturn(iteratorNode);
		
		Mockito.when(iteratorNode.hasProperty(Constants.ITERATOR)).thenReturn(true);
		Property propertyIterator = Mockito.mock(Property.class);
		Mockito.when(iteratorNode.getProperty(Constants.ITERATOR)).thenReturn(propertyIterator);
		Mockito.when(propertyIterator.getLong()).thenReturn(12344L);
		
		Mockito.when(iteratorNode.hasProperty(Constants.CONTENT_ID)).thenReturn(true);
		generateId.execute(item, wfsession, args);
	}

}
