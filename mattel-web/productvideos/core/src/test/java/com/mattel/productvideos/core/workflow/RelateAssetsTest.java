package com.mattel.productvideos.core.workflow;

import java.util.Collections;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.adobe.granite.asset.api.Asset;
import com.adobe.granite.asset.api.AssetManager;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.adobe.granite.workflow.model.WorkflowNode;
import com.mattel.productvideos.core.utils.ProductVideosPropertyUtils;
import com.mattel.productvideos.core.workflows.RelateAssets;

@RunWith(PowerMockRunner.class)
public class RelateAssetsTest {

	@InjectMocks
	RelateAssets relateAssets;

	@Mock
	ResourceResolverFactory resolverFactory;
	@Mock
	WorkItem item;
	@Mock
	WorkflowSession wfsession;
	@Mock
	MetaDataMap args;
	@Mock
	ProductVideosPropertyUtils productVideosPropertyUtils;

	@Test
	public void testExecute() throws WorkflowException, LoginException, RepositoryException {
		WorkflowNode node = Mockito.mock(WorkflowNode.class);
		Mockito.when(item.getNode()).thenReturn(node);
		Mockito.when(node.getTitle()).thenReturn("Workflow Title");
		WorkflowData workflowData = Mockito.mock(WorkflowData.class);
		Mockito.when(item.getWorkflowData()).thenReturn(workflowData);
		String payload = "testpayload";
		Mockito.when(workflowData.getPayload()).thenReturn(payload);
		Session session = Mockito.mock(Session.class);
		Mockito.when(wfsession.adaptTo(Session.class)).thenReturn(session);

		ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
		Mockito.when(resolverFactory.getResourceResolver(Collections.singletonMap("user.jcr.session", session)))
				.thenReturn(resourceResolver);

		AssetManager assetMgr = Mockito.mock(AssetManager.class);
		Mockito.when(resourceResolver.adaptTo(AssetManager.class)).thenReturn(assetMgr);

		Node root = Mockito.mock(Node.class);
		Mockito.when(session.getRootNode()).thenReturn(root);
		Node dataPathNode = Mockito.mock(Node.class);
		Mockito.when(root.getNode("testpayload")).thenReturn(dataPathNode);
		Workspace workspace = Mockito.mock(Workspace.class);
		Mockito.when(session.getWorkspace()).thenReturn(workspace);
		QueryManager queryManager = Mockito.mock(QueryManager.class);
		Mockito.when(workspace.getQueryManager()).thenReturn(queryManager);
		Mockito.when(dataPathNode.hasProperty("dc:brand")).thenReturn(true);
		Property brandproperty = Mockito.mock(Property.class);
		Mockito.when(dataPathNode.getProperty("dc:brand")).thenReturn(brandproperty);
		Value propValue = Mockito.mock(Value.class);
		Mockito.when(brandproperty.getValue()).thenReturn(propValue);
		Mockito.when(propValue.toString()).thenReturn("Jurassic World");
		Mockito.when(productVideosPropertyUtils.getRootAssetsPath()).thenReturn("assetpath");

		Property assetTitleproperty = Mockito.mock(Property.class);
		Property languagevalueproperty = Mockito.mock(Property.class);

		Value assetPropValue = Mockito.mock(Value.class);
		Mockito.when(assetTitleproperty.getValue()).thenReturn(assetPropValue);

		Value langPropValue = Mockito.mock(Value.class);
		Mockito.when(languagevalueproperty.getValue()).thenReturn(langPropValue);

		Mockito.when(dataPathNode.hasProperty("dc:assetName")).thenReturn(true);
		Mockito.when(dataPathNode.hasProperty("dc:languageIsoCode")).thenReturn(true);
		Mockito.when(dataPathNode.getProperty("dc:assetName")).thenReturn(assetTitleproperty);
		Mockito.when(dataPathNode.getProperty("dc:languageIsoCode")).thenReturn(languagevalueproperty);

		Query query = Mockito.mock(Query.class);
		Mockito.when(queryManager.createQuery(Mockito.anyString(), Mockito.anyString())).thenReturn(query);
		QueryResult result = Mockito.mock(QueryResult.class);
		Mockito.when(query.execute()).thenReturn(result);
		NodeIterator nIt = Mockito.mock(NodeIterator.class);
		Mockito.when(result.getNodes()).thenReturn(nIt);
		Mockito.when(nIt.hasNext()).thenReturn(true, false);
		Node nodeObj = Mockito.mock(Node.class);
		Mockito.when(nIt.nextNode()).thenReturn(nodeObj);
		Mockito.when(nodeObj.getPath()).thenReturn("node object path");
		Mockito.when(assetMgr.assetExists("node object path")).thenReturn(true);
		Mockito.when(assetMgr.assetExists("testpayload")).thenReturn(true);
		Asset asset = Mockito.mock(Asset.class);
		Mockito.when(assetMgr.getAsset("node object path")).thenReturn(asset);
		relateAssets.execute(item, wfsession, args);
	}

}
