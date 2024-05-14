package com.mattel.fisherprice.core.models;

import static org.junit.Assert.assertEquals;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import org.junit.Ignore;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.jcr.MockJcr;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.adobe.granite.taskmanagement.Task;
import com.adobe.granite.taskmanagement.TaskManager;
import com.adobe.granite.taskmanagement.TaskManagerException;
import com.adobe.granite.taskmanagement.TaskManagerFactory;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.adobe.granite.workflow.metadata.SimpleMetaDataMap;
import com.day.cq.dam.api.Asset;
import com.mattel.fisherprice.core.services.GetResourceResolver;

import io.wcm.testing.mock.aem.junit.AemContext;

@RunWith(PowerMockRunner.class)
public class ProductFeederProcessStepTest {
	
	@Rule
	private final AemContext context = new AemContext();

	@Mock
	GetResourceResolver getResourceResolver;

	@InjectMocks
	ProductFeederProcessStep productFeederProcessStep;

	@Mock
	WorkItem workItem;

	@Mock
	WorkflowSession workflowSession;

	@Mock
	WorkflowData workflowData;

	MetaDataMap metaDataMap;

	@Mock
	ResourceResolver resourceResolver;

	Session session;

	@Mock
	Asset asset;

	private Resource resource;
	
	private Node node;
	
	@Mock
	TaskManager taskManager;
	
	@Mock
	Task task;
	
	@Mock
	TaskManagerFactory taskManagerFactory;
	
	@Before
	public void setUp() throws PathNotFoundException, RepositoryException, TaskManagerException {
		metaDataMap = new SimpleMetaDataMap();
		Mockito.when(workItem.getWorkflowData()).thenReturn(workflowData);
		Mockito.when(workflowData.getPayload()).thenReturn(workflowData);
		Mockito.when(workflowData.getPayload().toString()).thenReturn("productfeed_it-it_2019_7_16_6287");	
		Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
		resource = context.create()
				.asset("/content/resources", "/productfeed_it-it_2019_7_16_6287.json", "application/json")
				.adaptTo(Resource.class);
		
		Mockito.when(resourceResolver.getResource("productfeed_it-it_2019_7_16_6287")).thenReturn(resource);
		session = MockJcr.newSession();
		node = session.getRootNode().addNode("/var/commerce/products/fisher-price/it-it");
		node.addNode("product_FXX69").addNode("assets").addNode("asset");
		node.getNode("/var/commerce/products/fisher-price/it-it/product_FXX69/assets").addNode("asset0");
		
		Mockito.when(workflowSession.adaptTo(Session.class)).thenReturn(session);
		Mockito.when(resourceResolver.adaptTo(TaskManager.class)).thenReturn(taskManager);
		Mockito.when(taskManager.getTaskManagerFactory()).thenReturn(taskManagerFactory);
		Mockito.when(taskManager.getTaskManagerFactory().newTask("Notification")).thenReturn(task);
	}
	
	@Test
	public void testExecute() throws WorkflowException, PathNotFoundException, RepositoryException {
		productFeederProcessStep.execute(workItem, workflowSession, metaDataMap);
		Node n1 = node.getNode("/var/commerce/products/fisher-price/it-it/product_GDV43");		
		String price = n1.getProperty("price").getString();
		assertEquals("69.99", price);
	}
}
